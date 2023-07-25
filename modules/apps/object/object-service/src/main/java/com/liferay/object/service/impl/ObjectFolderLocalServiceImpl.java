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

package com.liferay.object.service.impl;

import com.liferay.object.exception.ObjectFolderLabelException;
import com.liferay.object.exception.ObjectFolderNameException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectFolder;
import com.liferay.object.service.base.ObjectFolderLocalServiceBaseImpl;
import com.liferay.object.service.persistence.ObjectDefinitionPersistence;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

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

	@Indexable(type = IndexableType.DELETE)
	@Override
	public ObjectFolder deleteObjectFolder(long objectFolderId)
		throws PortalException {

		ObjectFolder objectFolder = objectFolderPersistence.findByPrimaryKey(
			objectFolderId);

		if (objectFolder.isUncategorized()) {
			throw new UnsupportedOperationException(
				"Uncategorized cannot be deleted");
		}

		objectFolder = objectFolderPersistence.remove(objectFolderId);

		_resourceLocalService.deleteResource(
			objectFolder, ResourceConstants.SCOPE_INDIVIDUAL);

		ObjectFolder uncategorizedObjectFolder =
			objectFolderPersistence.findByERC_C(
				"uncategorized", objectFolder.getCompanyId());

		for (ObjectDefinition objectDefinition :
				_objectDefinitionPersistence.findByObjectFolderId(
					objectFolderId)) {

			objectDefinition.setObjectFolderId(
				uncategorizedObjectFolder.getObjectFolderId());

			_objectDefinitionPersistence.update(objectDefinition);
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

		return objectFolderPersistence.update(objectFolder);
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
	private ObjectDefinitionPersistence _objectDefinitionPersistence;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private UserLocalService _userLocalService;

}