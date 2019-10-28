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

package com.liferay.segments.web.internal.display.context;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributorRegistry;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.provider.SegmentsEntryProviderRegistry;
import com.liferay.segments.service.SegmentsEntryService;
import com.liferay.segments.web.internal.security.permission.resource.SegmentsEntryPermission;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eduardo García
 */
public class EditSegmentsEntryDisplayContext {

	public EditSegmentsEntryDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse,
		SegmentsCriteriaContributorRegistry segmentsCriteriaContributorRegistry,
		SegmentsEntryProviderRegistry segmentsEntryProviderRegistry,
		SegmentsEntryService segmentsEntryService) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_segmentsCriteriaContributorRegistry =
			segmentsCriteriaContributorRegistry;
		_segmentsEntryProviderRegistry = segmentsEntryProviderRegistry;
		_segmentsEntryService = segmentsEntryService;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_locale = _themeDisplay.getLocale();
	}

	public String getBackURL() {
		if (_backURL != null) {
			return _backURL;
		}

		_backURL = ParamUtil.getString(
			_httpServletRequest, "backURL", getRedirect());

		return _backURL;
	}

	public Map<String, Object> getData() throws Exception {
		if (_data != null) {
			return _data;
		}

		_data = HashMapBuilder.<String, Object>put(
			"context", getContext()
		).put(
			"props", getProps()
		).build();

		return _data;
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

	public long getSegmentsEntryId() {
		if (_segmentsEntryId != null) {
			return _segmentsEntryId;
		}

		_segmentsEntryId = ParamUtil.getLong(
			_httpServletRequest, "segmentsEntryId");

		return _segmentsEntryId;
	}

	public String getSegmentsEntryKey() throws PortalException {
		if (_segmentsEntryKey != null) {
			return _segmentsEntryKey;
		}

		SegmentsEntry segmentsEntry = _getSegmentsEntry();

		if (segmentsEntry == null) {
			_segmentsEntryKey = StringPool.BLANK;
		}
		else {
			_segmentsEntryKey = segmentsEntry.getSegmentsEntryKey();
		}

		return _segmentsEntryKey;
	}

	public String getTitle(Locale locale) throws PortalException {
		if (_title != null) {
			return _title;
		}

		SegmentsEntry segmentsEntry = _getSegmentsEntry();

		if (segmentsEntry != null) {
			_title = segmentsEntry.getName(locale);
		}
		else {
			String type = ResourceActionsUtil.getModelResource(
				locale, getType());

			_title = LanguageUtil.format(
				_httpServletRequest, "new-x-segment", type, false);
		}

		return _title;
	}

	public String getType() throws PortalException {
		SegmentsEntry segmentsEntry = _getSegmentsEntry();

		if (segmentsEntry != null) {
			return segmentsEntry.getType();
		}

		return ParamUtil.getString(
			_httpServletRequest, "type", User.class.getName());
	}

	protected Map<String, Object> getContext() {
		return HashMapBuilder.<String, Object>put(
			"assetsPath", PortalUtil.getPathContext(_renderRequest) + "/assets"
		).put(
			"namespace", _renderResponse.getNamespace()
		).put(
			"requestFieldValueNameURL", _getSegmentsFieldValueNameURL()
		).build();
	}

	protected Map<String, Object> getProps() throws Exception {
		return HashMapBuilder.<String, Object>put(
			"availableLocales", _getAvailableLocales()
		).put(
			"contributors", _getContributorsJSONArray()
		).put(
			"defaultLanguageId", _getDefaultLanguageId()
		).put(
			"formId", _renderResponse.getNamespace() + "editSegmentFm"
		).put(
			"hasUpdatePermission", _hasUpdatePermission()
		).put(
			"initialMembersCount", _getSegmentsEntryClassPKsCount()
		).put(
			"initialSegmentActive", _isInitialSegmentActive()
		).put(
			"initialSegmentName", _getInitialSegmentsNameJSONObject()
		).put(
			"locale", _locale.toString()
		).put(
			"portletNamespace", _renderResponse.getNamespace()
		).put(
			"previewMembersURL", _getPreviewMembersURL()
		).put(
			"propertyGroups", _getPropertyGroupsJSONArray()
		).put(
			"redirect", HtmlUtil.escape(getRedirect())
		).put(
			"requestMembersCountURL", _getSegmentsEntryClassPKsCountURL()
		).put(
			"showInEditMode", _isShowInEditMode()
		).put(
			"source", _getSource()
		).build();
	}

	private Map<String, String> _getAvailableLocales() throws PortalException {
		Map<String, String> availableLocales = new HashMap<>();

		for (Locale availableLocale :
				LanguageUtil.getAvailableLocales(_getGroupId())) {

			String availableLanguageId = LocaleUtil.toLanguageId(
				availableLocale);

			availableLocales.put(
				availableLanguageId, availableLocale.getDisplayName(_locale));
		}

		return availableLocales;
	}

	private JSONArray _getContributorsJSONArray() throws PortalException {
		List<SegmentsCriteriaContributor> segmentsCriteriaContributors =
			_getSegmentsCriteriaContributors();

		JSONArray contributorsJSONArray = JSONFactoryUtil.createJSONArray();

		for (SegmentsCriteriaContributor segmentsCriteriaContributor :
				segmentsCriteriaContributors) {

			Criteria.Criterion criterion =
				segmentsCriteriaContributor.getCriterion(_getCriteria());

			JSONObject contributorJSONObject = JSONUtil.put(
				"conjunctionId", _getCriterionConjunction(criterion)
			).put(
				"conjunctionInputId",
				_renderResponse.getNamespace() + "criterionConjunction" +
					segmentsCriteriaContributor.getKey()
			).put(
				"initialQuery", _getCriterionFilterString(criterion)
			).put(
				"inputId",
				_renderResponse.getNamespace() + "criterionFilter" +
					segmentsCriteriaContributor.getKey()
			).put(
				"propertyKey", segmentsCriteriaContributor.getKey()
			);

			contributorsJSONArray.put(contributorJSONObject);
		}

		return contributorsJSONArray;
	}

	private Criteria _getCriteria() throws PortalException {
		SegmentsEntry segmentsEntry = _getSegmentsEntry();

		if ((segmentsEntry == null) ||
			(segmentsEntry.getCriteriaObj() == null)) {

			return new Criteria();
		}

		return segmentsEntry.getCriteriaObj();
	}

	private String _getCriterionConjunction(Criteria.Criterion criterion) {
		if (criterion == null) {
			return StringPool.BLANK;
		}

		return criterion.getConjunction();
	}

	private String _getCriterionFilterString(Criteria.Criterion criterion) {
		if (criterion == null) {
			return StringPool.BLANK;
		}

		return criterion.getFilterString();
	}

	private String _getDefaultLanguageId() throws PortalException {
		Locale siteDefaultLocale = null;

		try {
			siteDefaultLocale = PortalUtil.getSiteDefaultLocale(_getGroupId());
		}
		catch (PortalException pe) {
			_log.error(pe, pe);

			siteDefaultLocale = LocaleUtil.getSiteDefault();
		}

		SegmentsEntry segmentsEntry = _getSegmentsEntry();

		if (segmentsEntry == null) {
			return LocaleUtil.toLanguageId(siteDefaultLocale);
		}

		return LocalizationUtil.getDefaultLanguageId(
			segmentsEntry.getName(), siteDefaultLocale);
	}

	private long _getGroupId() throws PortalException {
		return BeanParamUtil.getLong(
			_getSegmentsEntry(), _httpServletRequest, "groupId",
			_themeDisplay.getScopeGroupId());
	}

	private JSONObject _getInitialSegmentsNameJSONObject()
		throws PortalException {

		SegmentsEntry segmentsEntry = _getSegmentsEntry();

		if (segmentsEntry == null) {
			return null;
		}

		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		return JSONFactoryUtil.createJSONObject(
			jsonSerializer.serializeDeep(segmentsEntry.getNameMap()));
	}

	private String _getPreviewMembersURL() throws Exception {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "previewSegmentsEntryUsers");
		portletURL.setParameter(
			"segmentsEntryId", String.valueOf(getSegmentsEntryId()));
		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL.toString();
	}

	private JSONArray _getPropertyGroupsJSONArray() throws PortalException {
		List<SegmentsCriteriaContributor> segmentsCriteriaContributors =
			_getSegmentsCriteriaContributors();

		JSONArray jsonContributorsArray = JSONFactoryUtil.createJSONArray();

		for (SegmentsCriteriaContributor segmentsCriteriaContributor :
				segmentsCriteriaContributors) {

			JSONObject jsonContributorObject = JSONUtil.put(
				"entityName", segmentsCriteriaContributor.getEntityName()
			).put(
				"name", segmentsCriteriaContributor.getLabel(_locale)
			).put(
				"properties",
				JSONFactoryUtil.createJSONArray(
					JSONFactoryUtil.looseSerializeDeep(
						segmentsCriteriaContributor.getFields(_renderRequest)))
			).put(
				"propertyKey", segmentsCriteriaContributor.getKey()
			);

			jsonContributorsArray.put(jsonContributorObject);
		}

		return jsonContributorsArray;
	}

	private List<SegmentsCriteriaContributor> _getSegmentsCriteriaContributors()
		throws PortalException {

		return _segmentsCriteriaContributorRegistry.
			getSegmentsCriteriaContributors(getType());
	}

	private SegmentsEntry _getSegmentsEntry() throws PortalException {
		long segmentsEntryId = getSegmentsEntryId();

		if (segmentsEntryId > 0) {
			return _segmentsEntryService.getSegmentsEntry(segmentsEntryId);
		}

		return null;
	}

	private int _getSegmentsEntryClassPKsCount() {
		try {
			SegmentsEntry segmentsEntry = _getSegmentsEntry();

			if (segmentsEntry != null) {
				return _segmentsEntryProviderRegistry.
					getSegmentsEntryClassPKsCount(
						segmentsEntry.getSegmentsEntryId());
			}
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get the segments entry class PKs count", pe);
			}
		}

		return 0;
	}

	private String _getSegmentsEntryClassPKsCountURL() {
		ResourceURL resourceURL = _renderResponse.createResourceURL();

		resourceURL.setResourceID("getSegmentsEntryClassPKsCount");

		return resourceURL.toString();
	}

	private String _getSegmentsFieldValueNameURL() {
		ResourceURL resourceURL = _renderResponse.createResourceURL();

		resourceURL.setResourceID("getSegmentsFieldValueName");

		return resourceURL.toString();
	}

	private String _getSource() throws PortalException {
		SegmentsEntry segmentsEntry = _getSegmentsEntry();

		if (segmentsEntry != null) {
			return segmentsEntry.getSource();
		}

		return ParamUtil.getString(
			_httpServletRequest, "source",
			SegmentsEntryConstants.SOURCE_DEFAULT);
	}

	private boolean _hasUpdatePermission() throws PortalException {
		SegmentsEntry segmentsEntry = _getSegmentsEntry();

		if (segmentsEntry != null) {
			return SegmentsEntryPermission.contains(
				_themeDisplay.getPermissionChecker(), segmentsEntry,
				ActionKeys.UPDATE);
		}

		return true;
	}

	private boolean _isInitialSegmentActive() throws PortalException {
		SegmentsEntry segmentsEntry = _getSegmentsEntry();

		if (segmentsEntry != null) {
			return segmentsEntry.isActive();
		}

		return false;
	}

	private boolean _isShowInEditMode() {
		return ParamUtil.getBoolean(
			_httpServletRequest, "showInEditMode", true);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditSegmentsEntryDisplayContext.class);

	private String _backURL;
	private Map<String, Object> _data;
	private final HttpServletRequest _httpServletRequest;
	private final Locale _locale;
	private String _redirect;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final SegmentsCriteriaContributorRegistry
		_segmentsCriteriaContributorRegistry;
	private Long _segmentsEntryId;
	private String _segmentsEntryKey;
	private final SegmentsEntryProviderRegistry _segmentsEntryProviderRegistry;
	private final SegmentsEntryService _segmentsEntryService;
	private final ThemeDisplay _themeDisplay;
	private String _title;

}