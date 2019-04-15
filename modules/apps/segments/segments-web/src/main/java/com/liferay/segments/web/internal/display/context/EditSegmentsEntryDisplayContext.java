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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.constants.SegmentsConstants;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributorRegistry;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.provider.SegmentsEntryProvider;
import com.liferay.segments.service.SegmentsEntryService;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eduardo García
 */
public class EditSegmentsEntryDisplayContext {

	public EditSegmentsEntryDisplayContext(
		HttpServletRequest request, RenderRequest renderRequest,
		RenderResponse renderResponse,
		SegmentsCriteriaContributorRegistry segmentsCriteriaContributorRegistry,
		SegmentsEntryProvider segmentsEntryProvider,
		SegmentsEntryService segmentsEntryService) {

		_request = request;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_segmentsCriteriaContributorRegistry =
			segmentsCriteriaContributorRegistry;
		_segmentsEntryProvider = segmentsEntryProvider;
		_segmentsEntryService = segmentsEntryService;
	}

	public JSONArray getContributorsJSONArray() throws PortalException {
		List<SegmentsCriteriaContributor> segmentsCriteriaContributors =
			getSegmentsCriteriaContributors();

		JSONArray jsonContributorsArray = JSONFactoryUtil.createJSONArray();

		for (SegmentsCriteriaContributor segmentsCriteriaContributor :
				segmentsCriteriaContributors) {

			Criteria.Criterion criterion =
				segmentsCriteriaContributor.getCriterion(getCriteria());

			JSONObject jsonContributorObject =
				JSONFactoryUtil.createJSONObject();

			jsonContributorObject.put(
				"conjunctionId", _getCriterionConjunction(criterion));
			jsonContributorObject.put(
				"conjunctionInputId",
				_renderResponse.getNamespace() + "criterionConjunction" +
					segmentsCriteriaContributor.getKey());
			jsonContributorObject.put(
				"initialQuery", _getCriterionFilterString(criterion));
			jsonContributorObject.put(
				"inputId",
				_renderResponse.getNamespace() + "criterionFilter" +
					segmentsCriteriaContributor.getKey());
			jsonContributorObject.put(
				"propertyKey", segmentsCriteriaContributor.getKey());

			jsonContributorsArray.put(jsonContributorObject);
		}

		return jsonContributorsArray;
	}

	public Criteria getCriteria() throws PortalException {
		SegmentsEntry segmentsEntry = getSegmentsEntry();

		if ((segmentsEntry == null) ||
			(segmentsEntry.getCriteriaObj() == null)) {

			return new Criteria();
		}

		return segmentsEntry.getCriteriaObj();
	}

	public JSONArray getPropertyGroupsJSONArray(Locale locale)
		throws PortalException {

		List<SegmentsCriteriaContributor> segmentsCriteriaContributors =
			getSegmentsCriteriaContributors();

		JSONArray jsonContributorsArray = JSONFactoryUtil.createJSONArray();

		for (SegmentsCriteriaContributor segmentsCriteriaContributor :
				segmentsCriteriaContributors) {

			JSONObject jsonContributorObject =
				JSONFactoryUtil.createJSONObject();

			jsonContributorObject.put(
				"entityName", segmentsCriteriaContributor.getEntityName());
			jsonContributorObject.put(
				"name", segmentsCriteriaContributor.getLabel(locale));
			jsonContributorObject.put(
				"properties",
				JSONFactoryUtil.createJSONArray(
					JSONFactoryUtil.looseSerializeDeep(
						segmentsCriteriaContributor.getFields(
							_renderRequest))));
			jsonContributorObject.put(
				"propertyKey", segmentsCriteriaContributor.getKey());

			jsonContributorsArray.put(jsonContributorObject);
		}

		return jsonContributorsArray;
	}

	public String getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_request, "redirect");

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

	public int getSegmentsEntryClassPKsCount() throws PortalException {
		if (_segmentsEntryClassPKsCount != null) {
			return _segmentsEntryClassPKsCount;
		}

		SegmentsEntry segmentsEntry = getSegmentsEntry();

		if (segmentsEntry == null) {
			return 0;
		}

		_segmentsEntryClassPKsCount =
			_segmentsEntryProvider.getSegmentsEntryClassPKsCount(
				segmentsEntry.getSegmentsEntryId());

		return _segmentsEntryClassPKsCount;
	}

	public long getSegmentsEntryId() {
		if (_segmentsEntryId != null) {
			return _segmentsEntryId;
		}

		_segmentsEntryId = ParamUtil.getLong(_request, "segmentsEntryId");

		return _segmentsEntryId;
	}

	public String getSource() throws PortalException {
		SegmentsEntry segmentsEntry = getSegmentsEntry();

		if (segmentsEntry != null) {
			return segmentsEntry.getSource();
		}

		return ParamUtil.getString(
			_request, "source", SegmentsConstants.SOURCE_DEFAULT);
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
				_request, "new-x-segment", type, false);
		}

		return _title;
	}

	public String getType() throws PortalException {
		SegmentsEntry segmentsEntry = getSegmentsEntry();

		if (segmentsEntry != null) {
			return segmentsEntry.getType();
		}

		return ParamUtil.getString(_request, "type", User.class.getName());
	}

	public boolean isShowInEditMode() {
		if (_showInEditMode != null) {
			return _showInEditMode;
		}

		_showInEditMode = ParamUtil.getBoolean(
			_request, "showInEditMode", true);

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

	private String _redirect;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;
	private final SegmentsCriteriaContributorRegistry
		_segmentsCriteriaContributorRegistry;
	private SegmentsEntry _segmentsEntry;
	private Integer _segmentsEntryClassPKsCount;
	private Long _segmentsEntryId;
	private final SegmentsEntryProvider _segmentsEntryProvider;
	private final SegmentsEntryService _segmentsEntryService;
	private Boolean _showInEditMode;
	private String _title;

}