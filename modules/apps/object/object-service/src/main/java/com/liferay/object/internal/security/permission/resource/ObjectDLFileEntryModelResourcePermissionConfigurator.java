/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.security.permission.resource;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionRegistryUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import jakarta.servlet.http.HttpServletRequest;

import java.util.function.Consumer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Manuele Castro
 */
@Component(
	property = "model.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
	service = ModelResourcePermissionFactory.ModelResourcePermissionConfigurator.class
)
public class ObjectDLFileEntryModelResourcePermissionConfigurator
	implements ModelResourcePermissionFactory.
				   ModelResourcePermissionConfigurator<DLFileEntry> {

	@Override
	public void configureModelResourcePermissionLogics(
		ModelResourcePermission<DLFileEntry> modelResourcePermission,
		Consumer<ModelResourcePermissionLogic<DLFileEntry>> consumer) {

		consumer.accept(
			(permissionChecker, name, dlFileEntry, actionId) -> {
				if (!actionId.equals(ActionKeys.DOWNLOAD) &&
					FeatureFlagManagerUtil.isEnabled(
						dlFileEntry.getCompanyId(), "LPD-17564")) {

					return null;
				}

				ServiceContext serviceContext =
					ServiceContextThreadLocal.getServiceContext();

				if (serviceContext == null) {
					return _handleInvalidContext(dlFileEntry);
				}

				HttpServletRequest httpServletRequest =
					serviceContext.getRequest();

				if (httpServletRequest == null) {
					return _handleInvalidContext(dlFileEntry);
				}

				boolean download = ParamUtil.getBoolean(
					httpServletRequest, "download");

				if (!download) {
					return null;
				}

				String objectFieldExternalReferenceCode = ParamUtil.getString(
					httpServletRequest, "objectFieldExternalReferenceCode");

				if (Validator.isNotNull(objectFieldExternalReferenceCode)) {
					long companyId = PortalUtil.getCompanyId(
						httpServletRequest);

					ObjectDefinition objectDefinition =
						_objectDefinitionLocalService.
							fetchObjectDefinitionByExternalReferenceCode(
								ParamUtil.getString(
									httpServletRequest,
									"objectDefinitionExternalReferenceCode"),
								companyId);

					if (objectDefinition == null) {
						return _handleInvalidContext(dlFileEntry);
					}

					long groupId = ObjectDefinitionConstants.GROUP_ID_DEFAULT;

					Group group =
						_groupLocalService.fetchGroupByExternalReferenceCode(
							ParamUtil.getString(
								httpServletRequest,
								"groupExternalReferenceCode"),
							companyId);

					if (group != null) {
						groupId = group.getGroupId();
					}

					long objectDefinitionId =
						objectDefinition.getObjectDefinitionId();

					ObjectEntry objectEntry =
						_objectEntryLocalService.fetchObjectEntry(
							ParamUtil.getString(
								httpServletRequest,
								"objectEntryExternalReferenceCode"),
							groupId, objectDefinitionId);

					if (objectEntry == null) {
						return _handleInvalidContext(dlFileEntry);
					}

					ObjectField objectField =
						_objectFieldLocalService.fetchObjectField(
							objectFieldExternalReferenceCode,
							objectDefinitionId);

					if (objectField == null) {
						return _handleInvalidContext(dlFileEntry);
					}

					ModelResourcePermission<?>
						objectEntryModelResourcePermission =
							ModelResourcePermissionRegistryUtil.
								getModelResourcePermission(
									objectDefinition.getClassName());

					if ((objectEntryModelResourcePermission == null) ||
						(objectEntryModelResourcePermission.contains(
							permissionChecker, objectEntry.getObjectEntryId(),
							ActionKeys.VIEW) &&
						 objectEntryModelResourcePermission.contains(
							 permissionChecker, objectEntry.getObjectEntryId(),
							 objectField.getAttachmentDownloadActionKey()))) {

						return null;
					}

					return false;
				}

				return _handleInvalidContext(dlFileEntry);
			});
	}

	private Boolean _handleInvalidContext(DLFileEntry dlFileEntry) {
		String className = dlFileEntry.getClassName();

		if (!className.contains("com.liferay.object.model.ObjectDefinition#")) {
			return null;
		}

		_log.error("Unable to verify download permission for " + className);

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectDLFileEntryModelResourcePermissionConfigurator.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

}