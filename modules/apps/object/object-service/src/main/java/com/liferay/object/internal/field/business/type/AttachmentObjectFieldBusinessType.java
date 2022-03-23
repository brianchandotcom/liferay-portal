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

package com.liferay.object.internal.field.business.type;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.dynamic.data.mapping.form.field.type.constants.ObjectDDMFormFieldTypeConstants;
import com.liferay.object.exception.ObjectFieldSettingValueException;
import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.object.field.render.ObjectFieldRenderingContext;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carolina Barbosa
 */
@Component(
	immediate = true,
	property = "object.field.business.type.key=" + ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT,
	service = ObjectFieldBusinessType.class
)
public class AttachmentObjectFieldBusinessType
	implements ObjectFieldBusinessType {

	@Override
	public String getDBType() {
		return ObjectFieldConstants.DB_TYPE_LONG;
	}

	@Override
	public String getDDMFormFieldTypeName() {
		return ObjectDDMFormFieldTypeConstants.ATTACHMENT;
	}

	@Override
	public String getDescription(Locale locale) {
		return LanguageUtil.get(
			ResourceBundleUtil.getModuleAndPortalResourceBundle(
				locale, getClass()),
			"upload-files-or-select-from-documents-and-media");
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(
			ResourceBundleUtil.getModuleAndPortalResourceBundle(
				locale, getClass()),
			"attachment");
	}

	@Override
	public String getName() {
		return ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT;
	}

	@Override
	public Map<String, Object> getProperties(
		ObjectField objectField,
		ObjectFieldRenderingContext objectFieldRenderingContext) {

		Map<String, Object> properties = HashMapBuilder.<String, Object>put(
			"folderId",
			() -> {
				if (Validator.isNull(
						objectFieldRenderingContext.getPortletId())) {

					return null;
				}

				Folder folder = _getFolder(objectFieldRenderingContext);

				if (folder == null) {
					return null;
				}

				return folder.getFolderId();
			}
		).put(
			"objectEntryId", objectFieldRenderingContext.getObjectEntryId()
		).put(
			"objectFieldId", objectField.getObjectFieldId()
		).put(
			"portletId", objectFieldRenderingContext.getPortletId()
		).build();

		List<ObjectFieldSetting> objectFieldSettings =
			_objectFieldSettingLocalService.getObjectFieldSettings(
				objectField.getObjectFieldId());

		ListUtil.isNotEmptyForEach(
			objectFieldSettings,
			objectFieldSetting -> properties.put(
				objectFieldSetting.getName(), objectFieldSetting.getValue()));

		return properties;
	}

	@Override
	public Set<String> getRequiredObjectFieldSettingsNames() {
		return SetUtil.fromArray(
			"acceptedFileExtensions", "fileSource", "maximumFileSize");
	}

	@Override
	public void validateObjectFieldSettings(
			String objectFieldName,
			List<ObjectFieldSetting> objectFieldSettings)
		throws PortalException {

		ObjectFieldBusinessType.super.validateObjectFieldSettings(
			objectFieldName, objectFieldSettings);

		for (ObjectFieldSetting objectFieldSetting : objectFieldSettings) {
			if (Objects.equals(objectFieldSetting.getName(), "fileSource")) {
				_validateObjectFieldSettingFileSource(
					objectFieldName, objectFieldSetting.getValue());
			}
			else if (Objects.equals(
						objectFieldSetting.getName(), "maximumFileSize")) {

				_validateObjectFieldSettingMaximumFileSize(
					objectFieldName, objectFieldSetting.getValue());
			}
		}
	}

	private Folder _addFolder(
		long userId, long repositoryId, HttpServletRequest httpServletRequest) {

		try {
			return _portletFileRepository.addPortletFolder(
				userId, repositoryId,
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				String.valueOf(userId),
				ServiceContextFactory.getInstance(httpServletRequest));
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return null;
		}
	}

	private Folder _getFolder(
		ObjectFieldRenderingContext objectFieldRenderingContext) {

		Repository repository = _getRepository(
			objectFieldRenderingContext.getGroupId(),
			objectFieldRenderingContext.getPortletId(),
			objectFieldRenderingContext.getHttpServletRequest());

		if (repository == null) {
			return null;
		}

		try {
			return _portletFileRepository.getPortletFolder(
				repository.getRepositoryId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				String.valueOf(objectFieldRenderingContext.getUserId()));
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return _addFolder(
				objectFieldRenderingContext.getUserId(),
				repository.getRepositoryId(),
				objectFieldRenderingContext.getHttpServletRequest());
		}
	}

	private Repository _getRepository(
		long groupId, String portletId, HttpServletRequest httpServletRequest) {

		Repository repository = _portletFileRepository.fetchPortletRepository(
			groupId, portletId);

		if (repository != null) {
			return repository;
		}

		try {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				httpServletRequest);

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);

			return _portletFileRepository.addPortletRepository(
				groupId, portletId, serviceContext);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return null;
		}
	}

	private void _validateObjectFieldSettingFileSource(
			String objectFieldName, String objectFieldSettingValue)
		throws PortalException {

		if (!Objects.equals(objectFieldSettingValue, "documentsAndMedia") &&
			!Objects.equals(objectFieldSettingValue, "userComputer")) {

			throw new ObjectFieldSettingValueException.MustSetValidValue(
				objectFieldName, "fileSource", objectFieldSettingValue);
		}
	}

	private void _validateObjectFieldSettingMaximumFileSize(
			String objectFieldName, String objectFieldSettingValue)
		throws PortalException {

		try {
			BigDecimal maximumFileSize = new BigDecimal(
				objectFieldSettingValue);

			if (maximumFileSize.signum() == -1) {
				throw new ObjectFieldSettingValueException.MustSetValidValue(
					objectFieldName, "maximumFileSize",
					objectFieldSettingValue);
			}
		}
		catch (NumberFormatException numberFormatException) {
			throw new ObjectFieldSettingValueException.MustSetValidValue(
				objectFieldName, "maximumFileSize", objectFieldSettingValue,
				numberFormatException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AttachmentObjectFieldBusinessType.class);

	@Reference
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

	@Reference
	private PortletFileRepository _portletFileRepository;

}