/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.design.library.web.internal.display.context;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalServiceUtil;
import com.liferay.design.library.web.internal.constants.DesignLibraryConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.portlet.RenderResponse;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

/**
 * @author Mario Leandro
 */
public class DesignLibrarySettingsDisplayContext {

	public DesignLibrarySettingsDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletResponse liferayPortletResponse,
		long designLibraryEntryId) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_designLibraryEntryId = designLibraryEntryId;
	}

	public Map<String, Object> getProps() throws PortalException {
		Group group = _getGroup();

		return HashMapBuilder.<String, Object>put(
			"backURL", _getBackURL()
		).put(
			"externalReferenceCode", group.getExternalReferenceCode()
		).put(
			"groupId", group.getGroupId()
		).build();
	}

	public void setPortletDisplay(
			PortletDisplay portletDisplay, RenderResponse renderResponse)
		throws PortalException {

		portletDisplay.setShowBackIcon(true);
		portletDisplay.setURLBack(_getBackURL());

		Group group = _getGroup();
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		renderResponse.setTitle(
			LanguageUtil.format(
				_httpServletRequest, "x-settings",
				group.getName(themeDisplay.getLocale()), false));
	}

	private String _getBackURL() {
		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCRenderCommandName(
			"/design_library/design_library_resources"
		).setParameter(
			DesignLibraryConstants.DESIGN_LIBRARY_ENTRY_ID_KEY,
			_designLibraryEntryId
		).buildString();
	}

	private Group _getGroup() throws PortalException {
		if (_group != null) {
			return _group;
		}

		DepotEntry depotEntry = DepotEntryLocalServiceUtil.getDepotEntry(
			_designLibraryEntryId);

		_group = depotEntry.getGroup();

		return _group;
	}

	private final long _designLibraryEntryId;
	private Group _group;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;

}