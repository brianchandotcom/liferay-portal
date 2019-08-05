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
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
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

import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

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
	}

	public Set<Locale> getAvailableLocales() throws PortalException {
		if (_availableLocales != null) {
			return _availableLocales;
		}

		_availableLocales = LanguageUtil.getAvailableLocales(getGroupId());

		return _availableLocales;
	}

	public JSONArray getContributorsJSONArray() throws PortalException {
		List<SegmentsCriteriaContributor> segmentsCriteriaContributors =
			getSegmentsCriteriaContributors();

		JSONArray contributorsJSONArray = JSONFactoryUtil.createJSONArray();

		for (SegmentsCriteriaContributor segmentsCriteriaContributor :
				segmentsCriteriaContributors) {

			Criteria.Criterion criterion =
				segmentsCriteriaContributor.getCriterion(getCriteria());

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

	public Criteria getCriteria() throws PortalException {
		SegmentsEntry segmentsEntry = getSegmentsEntry();

		if ((segmentsEntry == null) ||
			(segmentsEntry.getCriteriaObj() == null)) {

			return new Criteria();
		}

		return segmentsEntry.getCriteriaObj();
	}

	public String getDefaultLanguageId() throws PortalException {
		if (_defaultLanguageId != null) {
			return _defaultLanguageId;
		}

		Locale siteDefaultLocale = null;

		try {
			siteDefaultLocale = PortalUtil.getSiteDefaultLocale(getGroupId());
		}
		catch (PortalException pe) {
			_log.error(pe, pe);

			siteDefaultLocale = LocaleUtil.getSiteDefault();
		}

		SegmentsEntry segmentsEntry = getSegmentsEntry();

		if (segmentsEntry == null) {
			_defaultLanguageId = LocaleUtil.toLanguageId(siteDefaultLocale);
		}
		else {
			_defaultLanguageId = LocalizationUtil.getDefaultLanguageId(
				segmentsEntry.getName(), siteDefaultLocale);
		}

		return _defaultLanguageId;
	}

	public long getGroupId() throws PortalException {
		if (_groupId != null) {
			return _groupId;
		}

		_groupId = BeanParamUtil.getLong(
			getSegmentsEntry(), _httpServletRequest, "groupId",
			_themeDisplay.getScopeGroupId());

		return _groupId;
	}

	public JSONArray getPropertyGroupsJSONArray(Locale locale)
		throws PortalException {

		List<SegmentsCriteriaContributor> segmentsCriteriaContributors =
			getSegmentsCriteriaContributors();

		JSONArray jsonContributorsArray = JSONFactoryUtil.createJSONArray();

		for (SegmentsCriteriaContributor segmentsCriteriaContributor :
				segmentsCriteriaContributors) {

			JSONObject jsonContributorObject = JSONUtil.put(
				"entityName", segmentsCriteriaContributor.getEntityName()
			).put(
				"name", segmentsCriteriaContributor.getLabel(locale)
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

	public List<SegmentsCriteriaContributor> getSegmentsCriteriaContributors()
		throws PortalException {

		return _segmentsCriteriaContributorRegistry.
			getSegmentsCriteriaContributors(getType());
	}

	public SegmentsEntry getSegmentsEntry() throws PortalException {
		if (_segmentsEntry != null) {
			return _segmentsEntry;
		}

		long segmentsEntryId = getSegmentsEntryId();

		if (segmentsEntryId > 0) {
			_segmentsEntry = _segmentsEntryService.getSegmentsEntry(
				segmentsEntryId);
		}

		return _segmentsEntry;
	}

	public int getSegmentsEntryClassPKsCount() {
		try {
			if (_segmentsEntryClassPKsCount != null) {
				return _segmentsEntryClassPKsCount;
			}

			SegmentsEntry segmentsEntry = null;

			segmentsEntry = getSegmentsEntry();

			if (segmentsEntry == null) {
				return 0;
			}

			_segmentsEntryClassPKsCount =
				_segmentsEntryProviderRegistry.getSegmentsEntryClassPKsCount(
					segmentsEntry.getSegmentsEntryId());

			return _segmentsEntryClassPKsCount;
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get the segments entry class PKs count", pe);
			}

			return 0;
		}
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

		SegmentsEntry segmentsEntry = getSegmentsEntry();

		if (segmentsEntry == null) {
			_segmentsEntryKey = StringPool.BLANK;
		}
		else {
			_segmentsEntryKey = segmentsEntry.getSegmentsEntryKey();
		}

		return _segmentsEntryKey;
	}

	public String getSource() throws PortalException {
		SegmentsEntry segmentsEntry = getSegmentsEntry();

		if (segmentsEntry != null) {
			return segmentsEntry.getSource();
		}

		return ParamUtil.getString(
			_httpServletRequest, "source",
			SegmentsEntryConstants.SOURCE_DEFAULT);
	}

	public String getTitle(Locale locale) throws PortalException {
		if (_title != null) {
			return _title;
		}

		SegmentsEntry segmentsEntry = getSegmentsEntry();

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
		SegmentsEntry segmentsEntry = getSegmentsEntry();

		if (segmentsEntry != null) {
			return segmentsEntry.getType();
		}

		return ParamUtil.getString(
			_httpServletRequest, "type", User.class.getName());
	}

	public boolean hasUpdatePermission() throws PortalException {
		if (_hasUpdatePermission != null) {
			return _hasUpdatePermission;
		}

		SegmentsEntry segmentsEntry = getSegmentsEntry();

		if (segmentsEntry != null) {
			_hasUpdatePermission = SegmentsEntryPermission.contains(
				_themeDisplay.getPermissionChecker(), segmentsEntry,
				ActionKeys.UPDATE);
		}
		else {
			_hasUpdatePermission = true;
		}

		return _hasUpdatePermission;
	}

	public boolean isShowInEditMode() {
		if (_showInEditMode != null) {
			return _showInEditMode;
		}

		_showInEditMode = ParamUtil.getBoolean(
			_httpServletRequest, "showInEditMode", true);

		return _showInEditMode;
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

	private static final Log _log = LogFactoryUtil.getLog(
		EditSegmentsEntryDisplayContext.class);

	private Set<Locale> _availableLocales;
	private String _defaultLanguageId;
	private Long _groupId;
	private Boolean _hasUpdatePermission;
	private final HttpServletRequest _httpServletRequest;
	private String _redirect;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final SegmentsCriteriaContributorRegistry
		_segmentsCriteriaContributorRegistry;
	private SegmentsEntry _segmentsEntry;
	private Integer _segmentsEntryClassPKsCount;
	private Long _segmentsEntryId;
	private String _segmentsEntryKey;
	private final SegmentsEntryProviderRegistry _segmentsEntryProviderRegistry;
	private final SegmentsEntryService _segmentsEntryService;
	private Boolean _showInEditMode;
	private final ThemeDisplay _themeDisplay;
	private String _title;

}