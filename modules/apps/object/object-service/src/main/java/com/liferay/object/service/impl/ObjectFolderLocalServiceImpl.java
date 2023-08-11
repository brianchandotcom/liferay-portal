/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.impl;

import com.liferay.object.constants.ObjectFolderConstants;
import com.liferay.object.exception.ObjectFolderLabelException;
import com.liferay.object.exception.ObjectFolderNameException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectFolder;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.base.ObjectFolderLocalServiceBaseImpl;
import com.liferay.object.service.persistence.ObjectDefinitionPersistence;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalInstances;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Murilo Stodolni
 */
@Component(
	property = "model.class.name=com.liferay.object.model.ObjectFolder",
	service = AopService.class
)
public class ObjectFolderLocalServiceImpl
	extends ObjectFolderLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectFolder addObjectFolder(
			String externalReferenceCode, long userId,
			Map<Locale, String> labelMap, String name)
		throws PortalException {

		_validateLabel(labelMap);

		User user = _userLocalService.getUser(userId);

		_validateName(user.getCompanyId(), name);

		ObjectFolder objectFolder = objectFolderPersistence.create(
			counterLocalService.increment());

		objectFolder.setExternalReferenceCode(externalReferenceCode);
		objectFolder.setCompanyId(user.getCompanyId());
		objectFolder.setUserId(userId);
		objectFolder.setUserName(user.getFullName());
		objectFolder.setLabelMap(labelMap, LocaleUtil.getSiteDefault());
		objectFolder.setName(name);

		objectFolder = objectFolderPersistence.update(objectFolder);

		_resourceLocalService.addResources(
			objectFolder.getCompanyId(), 0, objectFolder.getUserId(),
			ObjectFolder.class.getName(), objectFolder.getObjectFolderId(),
			false, true, true);

		return objectFolder;
	}

	@Override
	public void deleteCompanyObjectFolders(long companyId)
		throws PortalException {

		List<ObjectFolder> objectFolders =
			objectFolderPersistence.findByCompanyId(companyId);

		for (ObjectFolder objectFolder : objectFolders) {
			objectFolderLocalService.deleteObjectFolder(objectFolder);
		}
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public ObjectFolder deleteObjectFolder(long objectFolderId)
		throws PortalException {

		ObjectFolder objectFolder = objectFolderPersistence.findByPrimaryKey(
			objectFolderId);

		return objectFolderLocalService.deleteObjectFolder(objectFolder);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public ObjectFolder deleteObjectFolder(ObjectFolder objectFolder)
		throws PortalException {

		if (!PortalInstances.isCurrentCompanyInDeletionProcess() &&
			objectFolder.isUncategorized()) {

			throw new UnsupportedOperationException(
				"Uncategorized cannot be deleted");
		}

		objectFolder = objectFolderPersistence.remove(objectFolder);

		_resourceLocalService.deleteResource(
			objectFolder, ResourceConstants.SCOPE_INDIVIDUAL);

		if (PortalInstances.isCurrentCompanyInDeletionProcess()) {
			return objectFolder;
		}

		ObjectFolder uncategorizedObjectFolder =
			objectFolderPersistence.findByC_N(
				objectFolder.getCompanyId(),
				ObjectFolderConstants.NAME_UNCATEGORIZED);

		for (ObjectDefinition objectDefinition :
				_objectDefinitionPersistence.findByObjectFolderId(
					objectFolder.getObjectFolderId())) {

			_objectDefinitionLocalService.updateObjectFolderId(
				objectDefinition.getObjectDefinitionId(),
				uncategorizedObjectFolder.getObjectFolderId());
		}

		return objectFolder;
	}

	@Override
	public ObjectFolder fetchObjectFolder(long companyId, String name) {
		return objectFolderPersistence.fetchByC_N(companyId, name);
	}

	@Override
	public ObjectFolder getObjectFolder(long companyId, String name)
		throws PortalException {

		return objectFolderPersistence.findByC_N(companyId, name);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectFolder updateObjectFolder(
			String externalReferenceCode, long objectFolderId,
			Map<Locale, String> labelMap)
		throws PortalException {

		_validateLabel(labelMap);

		ObjectFolder objectFolder = objectFolderPersistence.findByPrimaryKey(
			objectFolderId);

		if (objectFolder.isUncategorized()) {
			throw new UnsupportedOperationException(
				"Uncategorized cannot be updated");
		}

		objectFolder.setExternalReferenceCode(externalReferenceCode);
		objectFolder.setLabelMap(labelMap, LocaleUtil.getSiteDefault());

		objectFolder = objectFolderPersistence.update(objectFolder);

		for (ObjectDefinition objectDefinition :
				_objectDefinitionPersistence.findByObjectFolderId(
					objectFolder.getObjectFolderId())) {

			Indexer<ObjectDefinition> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(ObjectDefinition.class);

			indexer.reindex(objectDefinition);
		}

		return objectFolder;
	}

	private void _validateLabel(Map<Locale, String> labelMap)
		throws PortalException {

		Locale locale = LocaleUtil.getSiteDefault();

		if ((labelMap == null) || Validator.isNull(labelMap.get(locale))) {
			throw new ObjectFolderLabelException(
				"Label is null for locale " + locale.getDisplayName());
		}
	}

	private void _validateName(long companyId, String name)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new ObjectFolderNameException.MustNotBeNull();
		}

		char[] nameCharArray = name.toCharArray();

		for (char c : nameCharArray) {
			if (!Validator.isChar(c) && !Validator.isDigit(c)) {
				throw new ObjectFolderNameException.
					MustOnlyContainLettersAndDigits();
			}
		}

		if (nameCharArray.length > 41) {
			throw new ObjectFolderNameException.MustBeLessThan41Characters();
		}

		if (Validator.isNotNull(
				objectFolderPersistence.fetchByC_N(companyId, name))) {

			throw new ObjectFolderNameException.MustNotBeDuplicate(name);
		}
	}

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectDefinitionPersistence _objectDefinitionPersistence;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private UserLocalService _userLocalService;

}