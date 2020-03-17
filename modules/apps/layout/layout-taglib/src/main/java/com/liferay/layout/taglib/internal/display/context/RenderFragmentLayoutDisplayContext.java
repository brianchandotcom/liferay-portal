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

package com.liferay.layout.taglib.internal.display.context;

import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.layout.list.retriever.DefaultLayoutListRetrieverContext;
import com.liferay.layout.list.retriever.LayoutListRetriever;
import com.liferay.layout.list.retriever.LayoutListRetrieverTracker;
import com.liferay.layout.list.retriever.ListObjectReferenceFactory;
import com.liferay.layout.list.retriever.ListObjectReferenceFactoryTracker;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.portlet.PortletJSONUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.servlet.PipingServletResponse;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Rubén Pulido
 */
public class RenderFragmentLayoutDisplayContext {

	public RenderFragmentLayoutDisplayContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		InfoDisplayContributorTracker infoDisplayContributorTracker,
		InfoDisplayObjectProvider infoDisplayObjectProvider,
		LayoutListRetrieverTracker layoutListRetrieverTracker,
		ListObjectReferenceFactoryTracker listObjectReferenceFactoryTracker) {

		_httpServletRequest = httpServletRequest;
		_httpServletResponse = httpServletResponse;
		_infoDisplayContributorTracker = infoDisplayContributorTracker;
		_infoDisplayObjectProvider = infoDisplayObjectProvider;
		_layoutListRetrieverTracker = layoutListRetrieverTracker;
		_listObjectReferenceFactoryTracker = listObjectReferenceFactoryTracker;
	}

	public String getBackgroundImage(JSONObject rowConfigJSONObject)
		throws PortalException {

		if (rowConfigJSONObject == null) {
			return StringPool.BLANK;
		}

		String mappedField = rowConfigJSONObject.getString("mappedField");

		if (Validator.isNotNull(mappedField) &&
			(_infoDisplayContributorTracker != null) &&
			(_infoDisplayObjectProvider != null)) {

			InfoDisplayContributor infoDisplayContributor =
				_infoDisplayContributorTracker.getInfoDisplayContributor(
					PortalUtil.getClassName(
						_infoDisplayObjectProvider.getClassNameId()));

			if (infoDisplayContributor != null) {
				Object object = infoDisplayContributor.getInfoDisplayFieldValue(
					_infoDisplayObjectProvider.getDisplayObject(), mappedField,
					LocaleUtil.getDefault());

				if (object instanceof JSONObject) {
					JSONObject fieldValueJSONObject = (JSONObject)object;

					return fieldValueJSONObject.getString(
						"url", StringPool.BLANK);
				}
			}
		}

		String fieldId = rowConfigJSONObject.getString("fieldId");

		if (Validator.isNotNull(fieldId)) {
			long classNameId = rowConfigJSONObject.getLong("classNameId");
			long classPK = rowConfigJSONObject.getLong("classPK");

			if ((classNameId != 0L) && (classPK != 0L)) {
				InfoDisplayContributor infoDisplayContributor =
					_infoDisplayContributorTracker.getInfoDisplayContributor(
						PortalUtil.getClassName(classNameId));

				if (infoDisplayContributor != null) {
					InfoDisplayObjectProvider infoDisplayObjectProvider =
						infoDisplayContributor.getInfoDisplayObjectProvider(
							classPK);

					if (infoDisplayObjectProvider != null) {
						Object object =
							infoDisplayContributor.getInfoDisplayFieldValue(
								infoDisplayObjectProvider.getDisplayObject(),
								fieldId, LocaleUtil.getDefault());

						if (object instanceof JSONObject) {
							JSONObject fieldValueJSONObject =
								(JSONObject)object;

							return fieldValueJSONObject.getString(
								"url", StringPool.BLANK);
						}
					}
				}
			}
		}

		String backgroundImageURL = rowConfigJSONObject.getString("url");

		if (Validator.isNotNull(backgroundImageURL)) {
			return backgroundImageURL;
		}

		return StringPool.BLANK;
	}

	public List getCollection(
		JSONObject jsonObject, long[] segmentsExperienceIds) {

		if (jsonObject.length() <= 0) {
			return Collections.emptyList();
		}

		String type = jsonObject.getString("type");

		LayoutListRetriever layoutListRetriever =
			_layoutListRetrieverTracker.getLayoutListRetriever(type);

		if (layoutListRetriever == null) {
			return Collections.emptyList();
		}

		ListObjectReferenceFactory listObjectReferenceFactory =
			_listObjectReferenceFactoryTracker.getListObjectReference(type);

		if (listObjectReferenceFactory == null) {
			return Collections.emptyList();
		}

		DefaultLayoutListRetrieverContext defaultLayoutListRetrieverContext =
			new DefaultLayoutListRetrieverContext();

		defaultLayoutListRetrieverContext.setSegmentsExperienceIdsOptional(
			segmentsExperienceIds);

		return layoutListRetriever.getList(
			listObjectReferenceFactory.getListObjectReference(jsonObject),
			defaultLayoutListRetrieverContext);
	}

	public String getPortletPaths() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		PipingServletResponse pipingServletResponse = new PipingServletResponse(
			_httpServletResponse, unsyncStringWriter);

		List<PortletPreferences> portletPreferencesList =
			PortletPreferencesLocalServiceUtil.getPortletPreferences(
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, themeDisplay.getPlid());

		for (PortletPreferences portletPreferences : portletPreferencesList) {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				themeDisplay.getCompanyId(), portletPreferences.getPortletId());

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			try {
				PortletJSONUtil.populatePortletJSONObject(
					_httpServletRequest, StringPool.BLANK, portlet, jsonObject);

				PortletJSONUtil.writeHeaderPaths(
					pipingServletResponse, jsonObject);

				PortletJSONUtil.writeFooterPaths(
					pipingServletResponse, jsonObject);
			}
			catch (Exception exception) {
				_log.error(
					"Unable to write portlet paths " + portlet.getPortletId(),
					exception);
			}
		}

		return unsyncStringWriter.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RenderFragmentLayoutDisplayContext.class);

	private final HttpServletRequest _httpServletRequest;
	private final HttpServletResponse _httpServletResponse;
	private final InfoDisplayContributorTracker _infoDisplayContributorTracker;
	private final InfoDisplayObjectProvider _infoDisplayObjectProvider;
	private final LayoutListRetrieverTracker _layoutListRetrieverTracker;
	private final ListObjectReferenceFactoryTracker
		_listObjectReferenceFactoryTracker;

}