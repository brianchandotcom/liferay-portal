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

package com.liferay.layout.taglib.servlet.taglib;

import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalServiceUtil;
import com.liferay.layout.page.template.util.LayoutDataConverter;
import com.liferay.layout.taglib.internal.servlet.ServletContextUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.constants.SegmentsWebKeys;
import com.liferay.taglib.util.IncludeTag;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Víctor Galán
 */
public class RenderFragmentLayoutTag extends IncludeTag {

	public Map<String, Object> getFieldValues() {
		return _fieldValues;
	}

	public long getGroupId() {
		return _groupId;
	}

	public String getMode() {
		return _mode;
	}

	public long getPlid() {
		return _plid;
	}

	public boolean getShowPreview() {
		return _showPreview;
	}

	public void setFieldValues(Map<String, Object> fieldValues) {
		_fieldValues = fieldValues;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setMode(String mode) {
		_mode = mode;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setPlid(long plid) {
		_plid = plid;
	}

	public void setShowPreview(boolean showPreview) {
		_showPreview = showPreview;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_dataJSONObject = null;
		_fieldValues = null;
		_groupId = 0;
		_mode = null;
		_plid = 0;
		_showPreview = false;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		super.setAttributes(httpServletRequest);

		httpServletRequest.setAttribute(
			"liferay-layout:render-fragment-layout:dataJSONObject",
			_getDataJSONObject());
		httpServletRequest.setAttribute(
			"liferay-layout:render-fragment-layout:fieldValues", _fieldValues);
		httpServletRequest.setAttribute(
			"liferay-layout:render-fragment-layout:mode", _mode);
		httpServletRequest.setAttribute(
			"liferay-layout:render-fragment-layout:previewClassNameId",
			_getPreviewClassNameId());
		httpServletRequest.setAttribute(
			"liferay-layout:render-fragment-layout:previewClassPK",
			_getPreviewClassPK());
		httpServletRequest.setAttribute(
			"liferay-layout:render-fragment-layout:previewType",
			_getPreviewType());
		httpServletRequest.setAttribute(
			"liferay-layout:render-fragment-layout:segmentsExperienceIds",
			_getSegmentsExperienceIds());
	}

	private JSONObject _getDataJSONObject() {
		if (_dataJSONObject != null) {
			return _dataJSONObject;
		}

		try {
			LayoutPageTemplateStructure layoutPageTemplateStructure =
				LayoutPageTemplateStructureLocalServiceUtil.
					fetchLayoutPageTemplateStructure(
						getGroupId(),
						PortalUtil.getClassNameId(Layout.class.getName()),
						getPlid(), true);

			String data = layoutPageTemplateStructure.getData(
				_getSegmentsExperienceIds());

			JSONObject dataJSONObject = JSONFactoryUtil.createJSONObject(data);

			if (LayoutDataConverter.isLatestVersion(dataJSONObject)) {
				_dataJSONObject = dataJSONObject;

				return _dataJSONObject;
			}

			String masterLayoutData = _getMasterLayoutData();

			if (Validator.isNull(masterLayoutData)) {
				_dataJSONObject = _getDefaultDataJSONObject();

				return _dataJSONObject;
			}

			_dataJSONObject = JSONFactoryUtil.createJSONObject(
				masterLayoutData);

			return _dataJSONObject;
		}
		catch (Exception exception) {
			_log.error("Unable to get data JSON object", exception);

			return null;
		}
	}

	private JSONObject _getDefaultDataJSONObject() {
		return JSONUtil.put(
			"structure",
			JSONUtil.putAll(
				JSONUtil.put(
					"columns",
					JSONUtil.putAll(
						JSONUtil.put(
							"fragmentEntryLinkIds", JSONUtil.put("drop-zone")
						).put(
							"size", 12
						)))));
	}

	private String _getMasterLayoutData() {
		Layout layout = LayoutLocalServiceUtil.fetchLayout(_plid);

		LayoutPageTemplateEntry masterLayoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.
				fetchLayoutPageTemplateEntryByPlid(
					layout.getMasterLayoutPlid());

		if (masterLayoutPageTemplateEntry == null) {
			return null;
		}

		LayoutPageTemplateStructure masterLayoutPageTemplateStructure =
			LayoutPageTemplateStructureLocalServiceUtil.
				fetchLayoutPageTemplateStructure(
					masterLayoutPageTemplateEntry.getGroupId(),
					PortalUtil.getClassNameId(Layout.class),
					masterLayoutPageTemplateEntry.getPlid());

		return masterLayoutPageTemplateStructure.getData(
			SegmentsExperienceConstants.ID_DEFAULT);
	}

	private long _getPreviewClassNameId() {
		if (!_showPreview) {
			return 0;
		}

		return ParamUtil.getLong(request, "previewClassNameId");
	}

	private long _getPreviewClassPK() {
		if (!_showPreview) {
			return 0;
		}

		return ParamUtil.getLong(request, "previewClassPK");
	}

	private int _getPreviewType() {
		if (!_showPreview) {
			return 0;
		}

		return ParamUtil.getInteger(request, "previewType");
	}

	private long[] _getSegmentsExperienceIds() {
		return GetterUtil.getLongValues(
			request.getAttribute(SegmentsWebKeys.SEGMENTS_EXPERIENCE_IDS),
			new long[] {SegmentsExperienceConstants.ID_DEFAULT});
	}

	private static final String _PAGE = "/render_fragment_layout/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(
		RenderFragmentLayoutTag.class);

	private JSONObject _dataJSONObject;
	private Map<String, Object> _fieldValues;
	private long _groupId;
	private String _mode;
	private long _plid;
	private boolean _showPreview;

}