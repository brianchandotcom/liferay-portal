/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audience.web.internal.display.context;

import com.liferay.audience.model.AudienceEntry;
import com.liferay.audience.service.AudienceEntryServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import jakarta.portlet.PortletURL;
import jakarta.portlet.RenderResponse;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class EditAudienceEntryDisplayContext {

	public EditAudienceEntryDisplayContext(
		HttpServletRequest httpServletRequest, RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderResponse = renderResponse;
	}

	public long getAudienceEntryId() {
		if (_audienceEntryId != null) {
			return _audienceEntryId;
		}

		_audienceEntryId = ParamUtil.getLong(
			_httpServletRequest, "audienceEntryId");

		return _audienceEntryId;
	}

	public String getBackURL() {
		if (_backURL != null) {
			return _backURL;
		}

		_backURL = ParamUtil.getString(
			_httpServletRequest, "backURL", getRedirect());

		return _backURL;
	}

	public String getBackURLTitle() {
		String backURLTitle = ParamUtil.getString(
			_httpServletRequest, "backURLTitle");

		if (Validator.isNotNull(backURLTitle)) {
			return backURLTitle;
		}

		return LanguageUtil.get(_httpServletRequest, "audiences");
	}

	public String getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_httpServletRequest, "redirect");

		if (Validator.isNull(_redirect)) {
			PortletURL portletURL = _renderResponse.createRenderURL();

			_redirect = portletURL.toString();
		}

		return _redirect;
	}

	public String getTitle() throws PortalException {
		if (_title != null) {
			return _title;
		}

		AudienceEntry audienceEntry = _getAudienceEntry();

		if (audienceEntry != null) {
			_title = audienceEntry.getName();
		}
		else {
			_title = LanguageUtil.get(_httpServletRequest, "new-audience");
		}

		return _title;
	}

	private AudienceEntry _getAudienceEntry() throws PortalException {
		if (_audienceEntry != null) {
			return _audienceEntry;
		}

		long audienceEntryId = getAudienceEntryId();

		if (audienceEntryId > 0) {
			_audienceEntry = AudienceEntryServiceUtil.getAudienceEntry(
				audienceEntryId);

			return _audienceEntry;
		}

		return null;
	}

	private AudienceEntry _audienceEntry;
	private Long _audienceEntryId;
	private String _backURL;
	private final HttpServletRequest _httpServletRequest;
	private String _redirect;
	private final RenderResponse _renderResponse;
	private String _title;

}