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

package com.liferay.fragment.internal.renderer;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.fragment.util.FragmentEntryConfigUtil;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.info.item.renderer.InfoItemRenderer;
import com.liferay.info.item.renderer.InfoItemRendererTracker;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.constants.SegmentsWebKeys;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(
	configurationPid = "com.liferay.fragment.internal.configuration.ContentObjectConfiguration",
	service = FragmentRenderer.class
)
public class ContentObjectFragmentRenderer implements FragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "content-display";
	}

	@Override
	public String getConfiguration(
		FragmentRendererContext fragmentRendererContext) {

		return JSONUtil.put(
			"fieldSets",
			JSONUtil.putAll(
				JSONUtil.put(
					"fields",
					JSONUtil.putAll(
						JSONUtil.put(
							"label", "content"
						).put(
							"name", "itemSelector"
						).put(
							"type", "itemSelector"
						).put(
							"typeOptions",
							JSONUtil.put("enableSelectTemplate", true)
						))))
		).toString();
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "content");
	}

	@Override
	public void render(
		FragmentRendererContext fragmentRendererContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		JSONObject jsonObject = _getFieldValueJSONObject(
			fragmentRendererContext, httpServletRequest);

		if (jsonObject == null) {
			if (FragmentRendererUtil.isEditMode(httpServletRequest)) {
				FragmentRendererUtil.printPortletMessageInfo(
					httpServletRequest, httpServletResponse,
					"the-selected-content-will-be-shown-here");
			}

			return;
		}

		Object displayObject = _getDisplayObject(
			jsonObject.getString("className"), jsonObject.getLong("classPK"));

		if (displayObject == null) {
			if (FragmentRendererUtil.isEditMode(httpServletRequest)) {
				FragmentRendererUtil.printPortletMessageInfo(
					httpServletRequest, httpServletResponse,
					"the-selected-content-is-no-longer-available.-please-" +
						"select-another");
			}

			return;
		}

		InfoItemRenderer infoItemRenderer = _getInfoItemRenderer(
			displayObject.getClass(), fragmentRendererContext,
			httpServletRequest);

		if (infoItemRenderer == null) {
			if (FragmentRendererUtil.isEditMode(httpServletRequest)) {
				FragmentRendererUtil.printPortletMessageInfo(
					httpServletRequest, httpServletResponse,
					"there-are-no-available-renderers-for-the-selected-" +
						"content");
			}

			return;
		}

		infoItemRenderer.render(
			displayObject, httpServletRequest, httpServletResponse);
	}

	private Object _getDisplayObject(String className, long classPK) {
		InfoDisplayContributor infoDisplayContributor =
			_infoDisplayContributorTracker.getInfoDisplayContributor(className);

		try {
			InfoDisplayObjectProvider infoDisplayObjectProvider =
				infoDisplayContributor.getInfoDisplayObjectProvider(classPK);

			if (infoDisplayObjectProvider == null) {
				return null;
			}

			return infoDisplayObjectProvider.getDisplayObject();
		}
		catch (Exception e) {
		}

		return null;
	}

	private JSONObject _getFieldValueJSONObject(
		FragmentRendererContext fragmentRendererContext,
		HttpServletRequest httpServletRequest) {

		FragmentEntryLink fragmentEntryLink =
			fragmentRendererContext.getFragmentEntryLink();

		long[] segmentsExperienceIds = GetterUtil.getLongValues(
			httpServletRequest.getAttribute(
				SegmentsWebKeys.SEGMENTS_EXPERIENCE_IDS),
			new long[] {SegmentsExperienceConstants.ID_DEFAULT});

		return (JSONObject)FragmentEntryConfigUtil.getFieldValue(
			getConfiguration(fragmentRendererContext),
			fragmentEntryLink.getEditableValues(), segmentsExperienceIds,
			"itemSelector");
	}

	private InfoItemRenderer _getInfoItemRenderer(
		Class<?> displayObjectClass,
		FragmentRendererContext fragmentRendererContext,
		HttpServletRequest httpServletRequest) {

		List<InfoItemRenderer> infoItemRenderers =
			FragmentRendererUtil.getInfoItemRenderers(
				displayObjectClass, _infoItemRendererTracker);

		if (infoItemRenderers == null) {
			return null;
		}

		InfoItemRenderer defaultInfoItemRenderer = infoItemRenderers.get(0);

		JSONObject jsonObject = _getFieldValueJSONObject(
			fragmentRendererContext, httpServletRequest);

		if (jsonObject == null) {
			return defaultInfoItemRenderer;
		}

		String template = jsonObject.getString("template");

		if (Validator.isNull(template)) {
			return defaultInfoItemRenderer;
		}

		for (InfoItemRenderer infoItemRenderer : infoItemRenderers) {
			if (Objects.equals(infoItemRenderer.getKey(), template)) {
				return infoItemRenderer;
			}
		}

		return defaultInfoItemRenderer;
	}

	@Reference
	private InfoDisplayContributorTracker _infoDisplayContributorTracker;

	@Reference
	private InfoItemRendererTracker _infoItemRendererTracker;

}