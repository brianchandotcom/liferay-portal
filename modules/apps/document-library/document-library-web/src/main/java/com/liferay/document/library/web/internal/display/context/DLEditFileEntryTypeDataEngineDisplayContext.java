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

package com.liferay.document.library.web.internal.display.context;

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alicia Garcia
 */
public class DLEditFileEntryTypeDataEngineDisplayContext {

	public DLEditFileEntryTypeDataEngineDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(
			liferayPortletRequest);
	}

	public List<Map<String, Object>> getAdditionalPanels(
		String npmResolvedPackageName) {

		List<Map<String, Object>> additionalPanels = new ArrayList<>();

		additionalPanels.add(
			HashMapBuilder.<String, Object>put(
				"icon", "cog"
			).put(
				"label", LanguageUtil.get(_httpServletRequest, "details")
			).put(
				"pluginEntryPoint",
				npmResolvedPackageName +
					"/document_library/js/ddm/panels/index.es"
			).put(
				"sidebarPanelId", "details"
			).put(
				"url",
				() -> {
					PortletURL editBasicInfoURL =
						_liferayPortletResponse.createRenderURL();

					editBasicInfoURL.setParameter(
						"mvcPath", "/document_library/ddm/details.jsp");
					editBasicInfoURL.setParameter(
						"fileEntryTypeId",
						String.valueOf(
							ParamUtil.getLong(
								_liferayPortletRequest, "fileEntryTypeId")));
					editBasicInfoURL.setWindowState(
						LiferayWindowState.EXCLUSIVE);

					return editBasicInfoURL.toString();
				}
			).build());

		additionalPanels.add(
			HashMapBuilder.<String, Object>put(
				"icon", "document"
			).put(
				"label",
				LanguageUtil.get(
					_httpServletRequest, "additional-metadata-fields")
			).put(
				"pluginEntryPoint",
				npmResolvedPackageName +
					"/document_library/js/ddm/panels/index.es"
			).put(
				"sidebarPanelId", "additionalMetadataFields"
			).put(
				"url",
				() -> {
					PortletURL editAdditionalMetadataFieldsURL =
						_liferayPortletResponse.createRenderURL();

					editAdditionalMetadataFieldsURL.setParameter(
						"mvcPath",
						"/document_library/ddm/additional_metadata_fields.jsp");
					editAdditionalMetadataFieldsURL.setParameter(
						"fileEntryTypeId",
						String.valueOf(
							ParamUtil.getLong(
								_liferayPortletRequest, "fileEntryTypeId")));
					editAdditionalMetadataFieldsURL.setWindowState(
						LiferayWindowState.EXCLUSIVE);

					return editAdditionalMetadataFieldsURL.toString();
				}
			).build());

		DLFileEntryType fileEntryType =
			(DLFileEntryType)_liferayPortletRequest.getAttribute(
				WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY_TYPE);

		if (fileEntryType == null) {
			additionalPanels.add(
				HashMapBuilder.<String, Object>put(
					"icon", "lock"
				).put(
					"label",
					LanguageUtil.get(_httpServletRequest, "permissions")
				).put(
					"pluginEntryPoint",
					npmResolvedPackageName +
						"/document_library/js/ddm/panels/index.es"
				).put(
					"sidebarPanelId", "permissions"
				).put(
					"url",
					() -> {
						PortletURL editPermissionsURL =
							_liferayPortletResponse.createRenderURL();

						editPermissionsURL.setParameter(
							"mvcPath", "/document_library/ddm/permissions.jsp");
						editPermissionsURL.setWindowState(
							LiferayWindowState.EXCLUSIVE);

						return editPermissionsURL.toString();
					}
				).build());
		}

		return additionalPanels;
	}

	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;

}