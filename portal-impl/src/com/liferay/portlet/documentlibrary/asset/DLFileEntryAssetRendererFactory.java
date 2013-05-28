/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.asset;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.BaseAssetRendererFactory;
import com.liferay.portlet.assetpublisher.util.AssetPublisherUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryTypePermission;
import com.liferay.portlet.documentlibrary.service.permission.DLPermission;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Julio Camarero
 * @author Juan Fernández
 * @author Raymond Augé
 * @author Sergio González
 */
public class DLFileEntryAssetRendererFactory extends BaseAssetRendererFactory {

	public static final String TYPE = "document";

	@Override
	public AssetRenderer getAssetRenderer(long classPK, int type)
		throws PortalException, SystemException {

		FileEntry fileEntry = null;
		FileVersion fileVersion = null;

		if (type == TYPE_LATEST) {
			fileVersion = DLAppLocalServiceUtil.getFileVersion(classPK);

			fileEntry = fileVersion.getFileEntry();
		}
		else {
			fileEntry = DLAppLocalServiceUtil.getFileEntry(classPK);

			fileVersion = fileEntry.getFileVersion();
		}

		return new DLFileEntryAssetRenderer(fileEntry, fileVersion, type);
	}

	@Override
	public String getClassName() {
		return DLFileEntry.class.getName();
	}

	@Override
	public List<Tuple> getClassTypeFieldNames(
			long classTypeId, Locale locale, int start, int end)
		throws Exception {

		List<Tuple> classTypeFieldNames = new ArrayList<Tuple>();

		DLFileEntryType dlFileEntryType =
			DLFileEntryTypeLocalServiceUtil.getDLFileEntryType(classTypeId);

		List<DDMStructure> ddmStructures = dlFileEntryType.getDDMStructures();

		for (DDMStructure ddmStructure : ddmStructures) {
			classTypeFieldNames.addAll(
				getDDMStructureFieldNames(ddmStructure, locale));
		}

		if ((start != QueryUtil.ALL_POS) || (end != QueryUtil.ALL_POS)) {
			classTypeFieldNames = ListUtil.subList(
				classTypeFieldNames, start, end);
		}

		return classTypeFieldNames;
	}

	@Override
	public int getClassTypeFieldNamesCount(long classTypeId, Locale locale)
		throws Exception {

		List<Tuple> classTypeFieldNames = new ArrayList<Tuple>();

		DLFileEntryType dlFileEntryType =
			DLFileEntryTypeLocalServiceUtil.getDLFileEntryType(classTypeId);

		List<DDMStructure> ddmStructures = dlFileEntryType.getDDMStructures();

		for (DDMStructure ddmStructure : ddmStructures) {
			classTypeFieldNames.addAll(
				getDDMStructureFieldNames(ddmStructure, locale));
		}

		return classTypeFieldNames.size();
	}

	@Override
	public Map<Long, String> getClassTypes(long[] groupIds, Locale locale)
		throws Exception {

		Map<Long, String> classTypes = new HashMap<Long, String>();

		List<DLFileEntryType> dlFileEntryTypes =
			DLFileEntryTypeServiceUtil.getFileEntryTypes(groupIds);

		for (DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
			classTypes.put(
				dlFileEntryType.getFileEntryTypeId(),
				dlFileEntryType.getName(locale));
		}

		return classTypes;
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public String getTypeName(Locale locale, boolean hasSubtypes) {
		if (hasSubtypes) {
			return LanguageUtil.get(locale, "basic-document");
		}

		return super.getTypeName(locale, hasSubtypes);
	}

	@Override
	public PortletURL getURLAdd(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException, SystemException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!DLPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), ActionKeys.ADD_DOCUMENT)) {

			return null;
		}

		long classTypeId = GetterUtil.getLong(
			liferayPortletRequest.getAttribute(
				WebKeys.ASSET_RENDERER_FACTORY_CLASS_TYPE_ID));

		if ((classTypeId > 0) &&
			!DLFileEntryTypePermission.contains(
				themeDisplay.getPermissionChecker(), classTypeId,
				ActionKeys.VIEW)) {

			return null;
		}

		PortletURL portletURL = PortletURLFactoryUtil.create(
			liferayPortletRequest, PortletKeys.DOCUMENT_LIBRARY,
			getControlPanelPlid(themeDisplay), PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"struts_action", "/document_library/edit_file_entry");
		portletURL.setParameter(
			"folderId",
			String.valueOf(
				AssetPublisherUtil.getRecentFolderId(
					liferayPortletRequest, getClassName())));

		return portletURL;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws Exception {

		return DLFileEntryPermission.contains(
			permissionChecker, classPK, actionId);
	}

	@Override
	public boolean isLinkable() {
		return _LINKABLE;
	}

	@Override
	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/clip.png";
	}

	private static final boolean _LINKABLE = true;

}