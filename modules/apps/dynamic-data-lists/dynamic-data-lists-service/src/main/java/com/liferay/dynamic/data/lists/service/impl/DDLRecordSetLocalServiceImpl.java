/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.dynamic.data.lists.service.impl;

import com.liferay.dynamic.data.lists.exception.RecordSetDDMStructureIdException;
import com.liferay.dynamic.data.lists.exception.RecordSetDuplicateRecordSetKeyException;
import com.liferay.dynamic.data.lists.exception.RecordSetNameException;
import com.liferay.dynamic.data.lists.exception.RecordSetSettingsException;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.base.DDLRecordSetLocalServiceBaseImpl;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLink;
import com.liferay.dynamic.data.mapping.service.DDMStructureLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the local service for accessing, adding, deleting, and updating
 * dynamic data list (DDL) record sets.
 *
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 */
public class DDLRecordSetLocalServiceImpl
	extends DDLRecordSetLocalServiceBaseImpl {

	@Override
	public DDLRecordSet addRecordSet(
			long userId, long groupId, long ddmStructureId, String recordSetKey,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			int minDisplayRows, int scope, ServiceContext serviceContext)
		throws PortalException {

		// Record set

		User user = userPersistence.findByPrimaryKey(userId);

		if (Validator.isNull(recordSetKey)) {
			recordSetKey = String.valueOf(counterLocalService.increment());
		}

		validate(groupId, ddmStructureId, recordSetKey, nameMap);

		long recordSetId = counterLocalService.increment();

		DDLRecordSet recordSet = ddlRecordSetPersistence.create(recordSetId);

		recordSet.setUuid(serviceContext.getUuid());
		recordSet.setGroupId(groupId);
		recordSet.setCompanyId(user.getCompanyId());
		recordSet.setUserId(user.getUserId());
		recordSet.setUserName(user.getFullName());
		recordSet.setDDMStructureId(ddmStructureId);
		recordSet.setRecordSetKey(recordSetKey);
		recordSet.setNameMap(nameMap);
		recordSet.setDescriptionMap(descriptionMap);
		recordSet.setMinDisplayRows(minDisplayRows);
		recordSet.setScope(scope);

		ddlRecordSetPersistence.update(recordSet);

		// Resources

		if (serviceContext.isAddGroupPermissions() ||
			serviceContext.isAddGuestPermissions()) {

			addRecordSetResources(
				recordSet, serviceContext.isAddGroupPermissions(),
				serviceContext.isAddGuestPermissions());
		}
		else {
			addRecordSetResources(
				recordSet, serviceContext.getGroupPermissions(),
				serviceContext.getGuestPermissions());
		}

		// Dynamic data mapping structure link

		long classNameId = classNameLocalService.getClassNameId(
			DDLRecordSet.class);

		ddmStructureLinkLocalService.addStructureLink(
			classNameId, recordSetId, ddmStructureId);

		return recordSet;
	}

	@Override
	public void addRecordSetResources(
			DDLRecordSet recordSet, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		resourceLocalService.addResources(
			recordSet.getCompanyId(), recordSet.getGroupId(),
			recordSet.getUserId(), DDLRecordSet.class.getName(),
			recordSet.getRecordSetId(), false, addGroupPermissions,
			addGuestPermissions);
	}

	@Override
	public void addRecordSetResources(
			DDLRecordSet recordSet, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException {

		resourceLocalService.addModelResources(
			recordSet.getCompanyId(), recordSet.getGroupId(),
			recordSet.getUserId(), DDLRecordSet.class.getName(),
			recordSet.getRecordSetId(), groupPermissions, guestPermissions);
	}

	@Override
	@SystemEvent(
		action = SystemEventConstants.ACTION_SKIP,
		type = SystemEventConstants.TYPE_DELETE
	)
	public void deleteRecordSet(DDLRecordSet recordSet) throws PortalException {

		// Record set

		ddlRecordSetPersistence.remove(recordSet);

		// Resources

		resourceLocalService.deleteResource(
			recordSet.getCompanyId(), DDLRecordSet.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, recordSet.getRecordSetId());

		// Records

		ddlRecordLocalService.deleteRecords(recordSet.getRecordSetId());

		// Dynamic data mapping structure link

		ddmStructureLinkLocalService.deleteStructureLinks(
			classNameLocalService.getClassNameId(DDLRecordSet.class),
			recordSet.getRecordSetId());

		// Workflow

		workflowDefinitionLinkLocalService.deleteWorkflowDefinitionLink(
			recordSet.getCompanyId(), recordSet.getGroupId(),
			DDLRecordSet.class.getName(), recordSet.getRecordSetId(), 0);
	}

	@Override
	public void deleteRecordSet(long recordSetId) throws PortalException {
		DDLRecordSet recordSet = ddlRecordSetPersistence.findByPrimaryKey(
			recordSetId);

		ddlRecordSetLocalService.deleteRecordSet(recordSet);
	}

	@Override
	public void deleteRecordSet(long groupId, String recordSetKey)
		throws PortalException {

		DDLRecordSet recordSet = ddlRecordSetPersistence.findByG_R(
			groupId, recordSetKey);

		ddlRecordSetLocalService.deleteRecordSet(recordSet);
	}

	@Override
	public void deleteRecordSets(long groupId) throws PortalException {
		List<DDLRecordSet> recordSets = ddlRecordSetPersistence.findByGroupId(
			groupId);

		for (DDLRecordSet recordSet : recordSets) {
			ddlRecordSetLocalService.deleteRecordSet(recordSet);
		}
	}

	@Override
	public DDLRecordSet fetchRecordSet(long recordSetId) {
		return ddlRecordSetPersistence.fetchByPrimaryKey(recordSetId);
	}

	@Override
	public DDLRecordSet fetchRecordSet(long groupId, String recordSetKey) {
		return ddlRecordSetPersistence.fetchByG_R(groupId, recordSetKey);
	}

	@Override
	public DDLRecordSet getRecordSet(long recordSetId) throws PortalException {
		return ddlRecordSetPersistence.findByPrimaryKey(recordSetId);
	}

	@Override
	public DDLRecordSet getRecordSet(long groupId, String recordSetKey)
		throws PortalException {

		return ddlRecordSetPersistence.findByG_R(groupId, recordSetKey);
	}

	@Override
	public List<DDLRecordSet> getRecordSets(long groupId) {
		return ddlRecordSetPersistence.findByGroupId(groupId);
	}

	@Override
	public int getRecordSetsCount(long groupId) {
		return ddlRecordSetPersistence.countByGroupId(groupId);
	}

	@Override
	public List<DDLRecordSet> search(
		long companyId, long groupId, String keywords, int scope, int start,
		int end, OrderByComparator<DDLRecordSet> orderByComparator) {

		return ddlRecordSetFinder.findByKeywords(
			companyId, groupId, keywords, scope, start, end, orderByComparator);
	}

	@Override
	public List<DDLRecordSet> search(
		long companyId, long groupId, String name, String description,
		int scope, boolean andOperator, int start, int end,
		OrderByComparator<DDLRecordSet> orderByComparator) {

		return ddlRecordSetFinder.findByC_G_N_D_S(
			companyId, groupId, name, description, scope, andOperator, start,
			end, orderByComparator);
	}

	@Override
	public int searchCount(
		long companyId, long groupId, String keywords, int scope) {

		return ddlRecordSetFinder.countByKeywords(
			companyId, groupId, keywords, scope);
	}

	@Override
	public int searchCount(
		long companyId, long groupId, String name, String description,
		int scope, boolean andOperator) {

		return ddlRecordSetFinder.countByC_G_N_D_S(
			companyId, groupId, name, description, scope, andOperator);
	}

	@Override
	public DDLRecordSet updateMinDisplayRows(
			long recordSetId, int minDisplayRows, ServiceContext serviceContext)
		throws PortalException {

		DDLRecordSet recordSet = ddlRecordSetPersistence.findByPrimaryKey(
			recordSetId);

		recordSet.setMinDisplayRows(minDisplayRows);

		ddlRecordSetPersistence.update(recordSet);

		return recordSet;
	}

	@Override
	public DDLRecordSet updateRecordSet(
			long recordSetId, long ddmStructureId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, int minDisplayRows,
			ServiceContext serviceContext)
		throws PortalException {

		DDLRecordSet recordSet = ddlRecordSetPersistence.findByPrimaryKey(
			recordSetId);

		return doUpdateRecordSet(
			ddmStructureId, nameMap, descriptionMap, minDisplayRows,
			serviceContext, recordSet);
	}

	@Override
	public DDLRecordSet updateRecordSet(
			long groupId, long ddmStructureId, String recordSetKey,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			int minDisplayRows, ServiceContext serviceContext)
		throws PortalException {

		DDLRecordSet recordSet = ddlRecordSetPersistence.findByG_R(
			groupId, recordSetKey);

		return doUpdateRecordSet(
			ddmStructureId, nameMap, descriptionMap, minDisplayRows,
			serviceContext, recordSet);
	}

	@Override
	public DDLRecordSet updateRecordSet(long recordSetId, String settings)
		throws PortalException {

		Date now = new Date();

		UnicodeProperties settingsProperties = new UnicodeProperties();

		settingsProperties.fastLoad(settings);

		validateSettingsProperties(settingsProperties);

		DDLRecordSet recordSet = ddlRecordSetPersistence.findByPrimaryKey(
			recordSetId);

		recordSet.setModifiedDate(now);
		recordSet.setSettings(settingsProperties.toString());

		return ddlRecordSetPersistence.update(recordSet);
	}

	protected DDLRecordSet doUpdateRecordSet(
			long ddmStructureId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, int minDisplayRows,
			ServiceContext serviceContext, DDLRecordSet recordSet)
		throws PortalException {

		// Record set

		validateDDMStructureId(ddmStructureId);
		validateName(nameMap);

		long oldDDMStructureId = recordSet.getDDMStructureId();

		recordSet.setDDMStructureId(ddmStructureId);
		recordSet.setNameMap(nameMap);
		recordSet.setDescriptionMap(descriptionMap);
		recordSet.setMinDisplayRows(minDisplayRows);

		ddlRecordSetPersistence.update(recordSet);

		if (oldDDMStructureId != ddmStructureId) {

			// Records

			ddlRecordLocalService.deleteRecords(recordSet.getRecordSetId());

			// Dynamic data mapping structure link

			long classNameId = classNameLocalService.getClassNameId(
				DDLRecordSet.class);

			DDMStructureLink ddmStructureLink =
				ddmStructureLinkLocalService.getUniqueStructureLink(
					classNameId, recordSet.getRecordSetId());

			ddmStructureLinkLocalService.updateStructureLink(
				ddmStructureLink.getStructureLinkId(), classNameId,
				recordSet.getRecordSetId(), ddmStructureId);
		}

		return recordSet;
	}

	protected void validate(
			long groupId, long ddmStructureId, String recordSetKey,
			Map<Locale, String> nameMap)
		throws PortalException {

		validateDDMStructureId(ddmStructureId);

		if (Validator.isNotNull(recordSetKey)) {
			DDLRecordSet recordSet = ddlRecordSetPersistence.fetchByG_R(
				groupId, recordSetKey);

			if (recordSet != null) {
				RecordSetDuplicateRecordSetKeyException rsdrske =
					new RecordSetDuplicateRecordSetKeyException();

				rsdrske.setRecordSetKey(recordSet.getRecordSetKey());

				throw rsdrske;
			}
		}

		validateName(nameMap);
	}

	protected void validateDDMStructureId(long ddmStructureId)
		throws PortalException {

		DDMStructure ddmStructure = ddmStructureLocalService.fetchStructure(
			ddmStructureId);

		if (ddmStructure == null) {
			throw new RecordSetDDMStructureIdException(
				"No DDMStructure found for {ddmStructureId=" +
					ddmStructureId + "}");
		}
	}

	protected void validateName(Map<Locale, String> nameMap)
		throws PortalException {

		Locale locale = LocaleUtil.getSiteDefault();

		String name = nameMap.get(locale);

		if (Validator.isNull(name)) {
			throw new RecordSetNameException(
				"Name is null for locale " + locale.getDisplayName());
		}
	}

	protected void validateSettingsProperties(
			UnicodeProperties settingsProperties)
		throws PortalException {

		String redirectURL = settingsProperties.getProperty("redirectURL");

		if (Validator.isNotNull(redirectURL) && !Validator.isUrl(redirectURL)) {
			throw new RecordSetSettingsException(
				"The property \"redirectURL\" is not a URL");
		}

		String requireCaptcha = settingsProperties.getProperty(
			"requireCaptcha");

		if (Validator.isNotNull(requireCaptcha) &&
			!Validator.isBoolean(requireCaptcha)) {

			throw new RecordSetSettingsException(
				"The property \"requireCaptcha\" is not a boolean");
		}

		boolean sendEmailNotification = GetterUtil.getBoolean(
			settingsProperties.getProperty("sendEmailNotification"));

		if (sendEmailNotification) {
			String emailFromAddress = settingsProperties.getProperty(
				"emailFromAddress");

			if (!Validator.isEmailAddress(emailFromAddress)) {
				throw new RecordSetSettingsException(
					"The property \"emailFromAddress\" is not an email " +
						"address");
			}

			String emailFromName = settingsProperties.getProperty(
				"emailFromName");

			if (Validator.isNull(emailFromName)) {
				throw new RecordSetSettingsException(
					"The property \"emailFromName\" is empty");
			}

			String emailToAddress = settingsProperties.getProperty(
				"emailToAddresses");

			if (!Validator.isEmailAddress(emailToAddress)) {
				throw new RecordSetSettingsException(
					"The property \"emailToAddress\" is not an email address");
			}
		}
	}

	@ServiceReference(type = DDMStructureLinkLocalService.class)
	protected DDMStructureLinkLocalService ddmStructureLinkLocalService;

	@ServiceReference(type = DDMStructureLocalService.class)
	protected DDMStructureLocalService ddmStructureLocalService;

}