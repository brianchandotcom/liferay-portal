/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.asset.model;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.document.library.helper.DLURLHelper;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.display.context.ObjectEntryDisplayContextFactory;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.web.internal.security.permission.resource.util.ObjectDefinitionResourcePermissionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;

import jakarta.servlet.ServletContext;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author Feliphe Marinho
 */
public class ObjectEntryAssetRendererFactory
	extends BaseAssetRendererFactory<ObjectEntry> {

	public ObjectEntryAssetRendererFactory(
		AssetDisplayPageFriendlyURLProvider assetDisplayPageFriendlyURLProvider,
		DLAppLocalService dlAppLocalService, DLURLHelper dlURLHelper,
		ObjectDefinition objectDefinition,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectEntryDisplayContextFactory objectEntryDisplayContextFactory,
		ObjectEntryLocalService objectEntryLocalService,
		ObjectEntryService objectEntryService,
		ObjectFieldLocalService objectFieldLocalService,
		ServletContext servletContext) {

		setClassName(objectDefinition.getClassName());
		setSearchable(true);
		setPortletId(objectDefinition.getPortletId());
		setSelectable(
			!StringUtil.equals(
				objectDefinition.getScope(),
				ObjectDefinitionConstants.SCOPE_COMPANY));

		_assetDisplayPageFriendlyURLProvider =
			assetDisplayPageFriendlyURLProvider;
		_dlAppLocalService = dlAppLocalService;
		_dlURLHelper = dlURLHelper;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectEntryDisplayContextFactory = objectEntryDisplayContextFactory;
		_objectEntryLocalService = objectEntryLocalService;
		_objectEntryService = objectEntryService;
		_objectFieldLocalService = objectFieldLocalService;
		_servletContext = servletContext;

		_companyId = objectDefinition.getCompanyId();
		_defaultStorageType = objectDefinition.isDefaultStorageType();
		_objectDefinitionId = objectDefinition.getObjectDefinitionId();
	}

	@Override
	public AssetRenderer<ObjectEntry> getAssetRenderer(long classPK, int type)
		throws PortalException {

		if (!_defaultStorageType) {
			return null;
		}

		ObjectEntryAssetRenderer objectEntryAssetRenderer =
			new ObjectEntryAssetRenderer(
				_assetDisplayPageFriendlyURLProvider, _dlAppLocalService,
				_dlURLHelper, _objectEntryLocalService.getObjectEntry(classPK),
				_objectEntryDisplayContextFactory, _objectEntryService,
				_objectFieldLocalService);

		objectEntryAssetRenderer.setServletContext(_servletContext);

		return objectEntryAssetRenderer;
	}

	@Override
	public String getIconCssClass() {
		return getIconCssClass(
			_objectDefinitionLocalService.fetchObjectDefinition(
				_objectDefinitionId));
	}

	@Override
	public String getType() {
		return getClassName();
	}

	@Override
	public String getTypeName(Locale locale) {
		String typeName = super.getTypeName(locale);

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				_objectDefinitionId);

		if (objectDefinition.isCMS()) {
			typeName = StringUtil.appendParentheticalSuffix(typeName, "CMS");
		}

		return typeName;
	}

	@Override
	public boolean hasPermission(
		PermissionChecker permissionChecker, long classPK, String actionId) {

		try {
			if (_defaultStorageType &&
				Objects.equals(actionId, ActionKeys.DOWNLOAD)) {

				ObjectEntry objectEntry =
					_objectEntryLocalService.getObjectEntry(classPK);

				ObjectDefinition objectDefinition =
					objectEntry.getObjectDefinition();

				if (objectDefinition.isCMS()) {
					ObjectField objectField =
						_objectFieldLocalService.fetchObjectField(
							_objectDefinitionId, "file");

					if ((objectField != null) &&
						objectField.compareBusinessType(
							ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT)) {

						actionId = objectField.getAttachmentDownloadActionKey();
					}
				}

				return _objectEntryService.hasModelResourcePermission(
					objectEntry, actionId);
			}

			return ObjectDefinitionResourcePermissionUtil.
				hasModelResourcePermission(
					_defaultStorageType, _objectDefinitionId, classPK,
					_objectEntryService, actionId);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return false;
		}
	}

	@Override
	public boolean isActive(long companyId) {
		if (_companyId == companyId) {
			return true;
		}

		return false;
	}

	protected static String getIconCssClass(ObjectDefinition objectDefinition) {
		if (!objectDefinition.isCMS()) {
			return StringPool.BLANK;
		}

		return _icons.getOrDefault(
			objectDefinition.getExternalReferenceCode(), "forms");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryAssetRendererFactory.class);

	private static final Map<String, String> _icons = HashMapBuilder.put(
		"L_CMS_BASIC_DOCUMENT", "documents-and-media"
	).put(
		"L_CMS_BASIC_WEB_CONTENT", "forms"
	).put(
		"L_CMS_BLOG", "blogs"
	).put(
		"L_CMS_EXTERNAL_VIDEO", "video"
	).put(
		"L_CMS_VOCABULARY", "vocabulary"
	).put(
		"L_CONTENTS", "web-content"
	).put(
		"L_FILES", "document-default"
	).build();

	private final AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;
	private final long _companyId;
	private final boolean _defaultStorageType;
	private final DLAppLocalService _dlAppLocalService;
	private final DLURLHelper _dlURLHelper;
	private final long _objectDefinitionId;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectEntryDisplayContextFactory
		_objectEntryDisplayContextFactory;
	private final ObjectEntryLocalService _objectEntryLocalService;
	private final ObjectEntryService _objectEntryService;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final ServletContext _servletContext;

}