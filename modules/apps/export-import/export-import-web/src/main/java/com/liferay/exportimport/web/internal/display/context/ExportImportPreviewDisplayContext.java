/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.web.internal.display.context;

import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate.Scope;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.group.capability.GroupCapabilityUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.staging.StagingGroupHelper;

import jakarta.servlet.http.HttpServletRequest;

import java.net.URLEncoder;

import java.nio.charset.StandardCharsets;

/**
 * @author Daniel Raposo
 * @author Jorge González
 */
public class ExportImportPreviewDisplayContext {

	public ExportImportPreviewDisplayContext(
		String backMVCRenderCommandName, HttpServletRequest httpServletRequest,
		LiferayPortletResponse liferayPortletResponse, Group group,
		long groupId, long liveGroupId, boolean privateLayout,
		StagingGroupHelper stagingGroupHelper) {

		_backMVCRenderCommandName = backMVCRenderCommandName;
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
				_backMVCRenderCommandName
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

		_exportPreviewAPIURL = _getResourceAPIURL("/export-preview");

		return _exportPreviewAPIURL;
	}

	public String getExportProcessAPIURL() {
		if (_exportProcessAPIURL != null) {
			return _exportProcessAPIURL;
		}

		_exportProcessAPIURL = _getResourceAPIURL("/export-processes");

		return _exportProcessAPIURL;
	}

	public String getExportTitle() {
		return _getTitle("new-export-process");
	}

	public String getImportPreviewAPIURL() {
		if (_importPreviewAPIURL != null) {
			return _importPreviewAPIURL;
		}

		_importPreviewAPIURL = _getResourceAPIURL("/import-preview");

		return _importPreviewAPIURL;
	}

	public String getImportProcessAPIURL() {
		if (_importProcessAPIURL != null) {
			return _importProcessAPIURL;
		}

		_importProcessAPIURL = _getResourceAPIURL("/import-processes");

		return _importProcessAPIURL;
	}

	public String getImportTitle() {
		return _getTitle("new-import-process");
	}

	public Scope getScope() {
		if (!Validator.isBlank(_getPortletId())) {
			return Scope.PORTLET;
		}

		if (_stagingGroupHelper.isCompanyGroup(_group)) {
			return Scope.COMPANY;
		}

		if (_group.isDepot()) {
			return Scope.DEPOT;
		}

		return Scope.SITE;
	}

	public boolean isCommentsAndRatingsEnabled() {
		if (!_stagingGroupHelper.isCompanyGroup(_group) ||
			FeatureFlagManagerUtil.isEnabled(
				_group.getCompanyId(), "LPD-43996")) {

			return true;
		}

		return false;
	}

	public boolean isLookAndFeelEnabled() {
		if ((getScope() != Scope.PORTLET) &&
			GroupCapabilityUtil.isSupportsPages(_group) &&
			!_group.isCompany() && !_group.isLayoutPrototype()) {

			return true;
		}

		return false;
	}

	private String _encode(String value) {
		if (Validator.isBlank(value)) {
			return "";
		}

		return URLEncoder.encode(value, StandardCharsets.UTF_8);
	}

	private String _getPortletId() {
		return ParamUtil.getString(_httpServletRequest, "portletId");
	}

	private String _getResourceAPIURL(String endpoint) {
		String portletId = _getPortletId();

		if (!Validator.isBlank(portletId)) {
			return StringBundler.concat(
				_BASE_PATH, _getScopePath(), "/portlets/", _encode(portletId),
				endpoint, "?plid=",
				ParamUtil.getLong(_httpServletRequest, "plid"));
		}

		return _BASE_PATH + _getScopePath() + endpoint;
	}

	private String _getScopePath() {
		if (_stagingGroupHelper.isCompanyGroup(_group)) {
			return StringPool.BLANK;
		}

		if (_group.isDepot()) {
			return "/asset-libraries/" +
				_encode(_group.getExternalReferenceCode());
		}

		return "/sites/" + _encode(_group.getExternalReferenceCode());
	}

	private String _getTitle(String key) {
		String label = LanguageUtil.get(_httpServletRequest, key);

		String portletId = _getPortletId();

		if (Validator.isBlank(portletId)) {
			return label;
		}

		return StringBundler.concat(
			label, " - ",
			PortalUtil.getPortletTitle(
				portletId, PortalUtil.getLocale(_httpServletRequest)));
	}

	private static final String _BASE_PATH = "/o/export-import/v1.0";

	private final String _backMVCRenderCommandName;
	private String _backURL;
	private String _exportPreviewAPIURL;
	private String _exportProcessAPIURL;
	private final Group _group;
	private final long _groupId;
	private final HttpServletRequest _httpServletRequest;
	private String _importPreviewAPIURL;
	private String _importProcessAPIURL;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final long _liveGroupId;
	private final boolean _privateLayout;
	private final StagingGroupHelper _stagingGroupHelper;

}