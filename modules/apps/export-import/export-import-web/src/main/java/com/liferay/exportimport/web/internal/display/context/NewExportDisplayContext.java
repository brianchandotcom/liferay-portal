/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.web.internal.display.context;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.staging.StagingGroupHelper;

import jakarta.servlet.http.HttpServletRequest;

import java.net.URLEncoder;

import java.nio.charset.StandardCharsets;

import java.util.Map;

/**
 * @author Jorge González
 */
public class NewExportDisplayContext {

	public NewExportDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletResponse liferayPortletResponse, Group group,
		long groupId, long liveGroupId, boolean privateLayout,
		StagingGroupHelper stagingGroupHelper) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_group = group;
		_groupId = groupId;
		_liveGroupId = liveGroupId;
		_privateLayout = privateLayout;
		_stagingGroupHelper = stagingGroupHelper;
	}

	public String getBackURL() {
		if (_backURL != null) {
			return _backURL;
		}

		String backURL = ParamUtil.getString(_httpServletRequest, "backURL");

		if (Validator.isBlank(backURL)) {
			backURL = PortletURLBuilder.createRenderURL(
				_liferayPortletResponse
			).setMVCRenderCommandName(
				"/export_import/view_export_layouts"
			).setParameter(
				"displayStyle",
				() -> ParamUtil.getString(_httpServletRequest, "displayStyle")
			).setParameter(
				"groupId", _groupId
			).setParameter(
				"liveGroupId", _liveGroupId
			).setParameter(
				"privateLayout", _privateLayout
			).buildString();
		}

		_backURL = backURL;

		return _backURL;
	}

	public String getExportPreviewAPIURL() {
		if (_exportPreviewAPIURL != null) {
			return _exportPreviewAPIURL;
		}

		if (_stagingGroupHelper.isCompanyGroup(_group)) {
			_exportPreviewAPIURL = _REST_BASE_PATH + "/export-preview";
		}
		else if (_group.isDepot()) {
			_exportPreviewAPIURL = StringBundler.concat(
				_REST_BASE_PATH, "/asset-libraries/",
				_encode(_group.getExternalReferenceCode()), "/export-preview");
		}
		else {
			_exportPreviewAPIURL = StringBundler.concat(
				_REST_BASE_PATH, "/sites/",
				_encode(_group.getExternalReferenceCode()), "/export-preview");
		}

		return _exportPreviewAPIURL;
	}

	public Map<String, Object> getReactProps() {
		return HashMapBuilder.<String, Object>put(
			"backURL", getBackURL()
		).put(
			"exportPreviewAPIURL", getExportPreviewAPIURL()
		).build();
	}

	private String _encode(String value) {
		if (Validator.isBlank(value)) {
			return "";
		}

		return URLEncoder.encode(value, StandardCharsets.UTF_8);
	}

	private static final String _REST_BASE_PATH = "/o/export-import/v1.0";

	private String _backURL;
	private String _exportPreviewAPIURL;
	private final Group _group;
	private final long _groupId;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final long _liveGroupId;
	private final boolean _privateLayout;
	private final StagingGroupHelper _stagingGroupHelper;

}