/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.reports.engine.console.service.impl;

import com.liferay.document.library.kernel.store.DLStoreUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.reports.engine.console.exception.DefinitionFileException;
import com.liferay.portal.reports.engine.console.exception.DefinitionNameException;
import com.liferay.portal.reports.engine.console.model.Definition;
import com.liferay.portal.reports.engine.console.service.base.DefinitionLocalServiceBaseImpl;

import java.io.InputStream;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Gavin Wan
 */
@Component(
	property = "model.class.name=com.liferay.portal.reports.engine.console.model.Definition",
	service = AopService.class
)
public class DefinitionLocalServiceImpl extends DefinitionLocalServiceBaseImpl {

	@Override
	public Definition addDefinition(
			long userId, long groupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, long sourceId,
			String reportParameters, String fileName, InputStream inputStream,
			ServiceContext serviceContext)
		throws PortalException {

		// Definition

		User user = userLocalService.getUser(userId);
		Date now = new Date();

		validate(nameMap);

		long definitionId = counterLocalService.increment();

		Definition definition = definitionPersistence.create(definitionId);

		definition.setUuid(serviceContext.getUuid());
		definition.setGroupId(groupId);
		definition.setCompanyId(user.getCompanyId());
		definition.setUserId(user.getUserId());
		definition.setUserName(user.getFullName());
		definition.setCreateDate(serviceContext.getCreateDate(now));
		definition.setModifiedDate(serviceContext.getModifiedDate(now));
		definition.setNameMap(nameMap);
		definition.setDescriptionMap(descriptionMap);
		definition.setSourceId(sourceId);
		definition.setReportName(
			StringUtil.extractFirst(fileName, StringPool.PERIOD));
		definition.setReportParameters(reportParameters);

		definitionPersistence.update(definition);

		// Resources

		resourceLocalService.addModelResources(definition, serviceContext);

		// Attachments

		if (Validator.isNotNull(fileName) && (inputStream != null)) {
			addDefinitionFile(
				user.getCompanyId(), definition, fileName, inputStream);
		}
		else {
			throw new DefinitionFileException.InvalidDefinitionFile(
				fileName, inputStream == null);
		}

		return definition;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public Definition deleteDefinition(Definition definition)
		throws PortalException {

		// Definition

		definitionPersistence.remove(definition);

		// Resources

		resourceLocalService.deleteResource(
			definition.getCompanyId(), Definition.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, definition.getDefinitionId());

		// Attachments

		deleteDefinitionTemplates(
			definition.getCompanyId(), definition.getAttachmentsDir());

		return definition;
	}

	@Override
	public Definition deleteDefinition(long definitionId)
		throws PortalException {

		Definition definition = definitionPersistence.findByPrimaryKey(
			definitionId);

		return definitionLocalService.deleteDefinition(definition);
	}

	@Override
	public void deleteDefinitions(long groupId) throws PortalException {
		List<Definition> definitions = definitionPersistence.findByGroupId(
			groupId);

		for (Definition definition : definitions) {
			definitionLocalService.deleteDefinition(definition);
		}
	}

	@Override
	public void deleteDefinitionTemplates(
			long companyId, String attachmentsDirectory)
		throws PortalException {

		DLStoreUtil.deleteDirectory(
			companyId, CompanyConstants.SYSTEM, attachmentsDirectory);
	}

	@Override
	public List<Definition> getDefinitions(
		long groupId, String definitionName, String description,
		String sourceId, String reportName, boolean andSearch, int start,
		int end, OrderByComparator orderByComparator) {

		return definitionFinder.findByG_S_N_D_RN(
			groupId, definitionName, description, GetterUtil.getLong(sourceId),
			reportName, andSearch, start, end, orderByComparator);
	}

	@Override
	public int getDefinitionsCount(
		long groupId, String definitionName, String description,
		String sourceId, String reportName, boolean andSearch) {

		return definitionFinder.countByG_S_N_D_RN(
			groupId, definitionName, description, GetterUtil.getLong(sourceId),
			reportName, andSearch);
	}

	@Override
	public Definition updateDefinition(
			long definitionId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, long sourceId,
			String reportParameters, String fileName, InputStream inputStream,
			ServiceContext serviceContext)
		throws PortalException {

		// Definition

		Definition definition = definitionPersistence.findByPrimaryKey(
			definitionId);

		validate(nameMap);

		definition.setModifiedDate(serviceContext.getModifiedDate(null));
		definition.setNameMap(nameMap);
		definition.setDescriptionMap(descriptionMap);
		definition.setSourceId(sourceId);

		if (Validator.isNotNull(fileName)) {
			definition.setReportName(
				StringUtil.extractFirst(fileName, StringPool.PERIOD));
		}

		definition.setReportParameters(reportParameters);

		definitionPersistence.update(definition);

		// Resources

		ModelPermissions modelPermissions =
			serviceContext.getModelPermissions();

		if (modelPermissions == null) {
			updateDefinitionResources(
				definition, StringPool.EMPTY_ARRAY, StringPool.EMPTY_ARRAY);
		}
		else {
			String[] groupPermissions = modelPermissions.getActionIds(
				RoleConstants.PLACEHOLDER_DEFAULT_GROUP_ROLE);

			String[] guestPermissions = modelPermissions.getActionIds(
				RoleConstants.GUEST);

			updateDefinitionResources(
				definition, groupPermissions, guestPermissions);
		}

		// Attachments

		if (Validator.isNotNull(fileName) && (inputStream != null)) {
			long companyId = definition.getCompanyId();

			DLStoreUtil.deleteDirectory(
				companyId, CompanyConstants.SYSTEM,
				definition.getAttachmentsDir());

			addDefinitionFile(companyId, definition, fileName, inputStream);
		}

		return definition;
	}

	@Override
	public void updateDefinitionResources(
			Definition definition, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException {

		resourceLocalService.updateResources(
			definition.getCompanyId(), definition.getGroupId(),
			Definition.class.getName(), definition.getDefinitionId(),
			communityPermissions, guestPermissions);
	}

	protected void addDefinitionFile(
			long companyId, Definition definition, String fileName,
			InputStream inputStream)
		throws PortalException {

		String directoryName = definition.getAttachmentsDir();

		String fileLocation = directoryName.concat(
			StringPool.SLASH
		).concat(
			fileName
		);

		DLStoreUtil.addFile(
			companyId, CompanyConstants.SYSTEM, fileLocation, false,
			inputStream);
	}

	protected void validate(Map<Locale, String> nameMap)
		throws PortalException {

		Locale locale = LocaleUtil.getDefault();

		String name = nameMap.get(locale);

		if (Validator.isNull(name)) {
			throw new DefinitionNameException.NullDefinitionFileName();
		}
	}

}