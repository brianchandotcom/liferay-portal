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

import com.liferay.asset.display.page.constants.AssetDisplayPageWebKeys;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.fragment.util.FragmentEntryConfigUtil;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.info.item.renderer.InfoItemRenderer;
import com.liferay.info.item.renderer.InfoItemRendererTracker;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.constants.SegmentsWebKeys;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(service = FragmentRenderer.class)
public class LayoutDisplayObjectFragmentRenderer implements FragmentRenderer {

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
							"label", "select-content"
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

		Object displayObject = _getDisplayObject(
			fragmentRendererContext, httpServletRequest);

		if (displayObject == null) {
			_printPortletMessageInfo(
				httpServletRequest, httpServletResponse,
				"the-rendered-content-will-be-shown-here");

			return;
		}

		InfoItemRenderer infoItemRenderer = _getInfoItemRenderer(
			displayObject.getClass(), fragmentRendererContext,
			httpServletRequest);

		if (infoItemRenderer == null) {
			_printPortletMessageInfo(
				httpServletRequest, httpServletResponse,
				"there-are-no-available-renderers-for-the-selected-content");

			return;
		}

		infoItemRenderer.render(
			displayObject, httpServletRequest, httpServletResponse);
	}

	private InfoDisplayObjectProvider
		_getConfigurationInfoDisplayObjectProvider(
			FragmentRendererContext fragmentRendererContext,
			HttpServletRequest httpServletRequest) {

		JSONObject jsonObject = _getFieldValueJSONObject(
			fragmentRendererContext, httpServletRequest);

		if (jsonObject != null) {
			InfoDisplayContributor infoDisplayContributor =
				_infoDisplayContributorTracker.getInfoDisplayContributor(
					jsonObject.getString("className"));

			try {
				return infoDisplayContributor.getInfoDisplayObjectProvider(
					jsonObject.getLong("classPK"));
			}
			catch (Exception e) {
			}
		}

		return (InfoDisplayObjectProvider)httpServletRequest.getAttribute(
			AssetDisplayPageWebKeys.INFO_DISPLAY_OBJECT_PROVIDER);
	}

	private Object _getDisplayObject(
		FragmentRendererContext fragmentRendererContext,
		HttpServletRequest httpServletRequest) {

		InfoDisplayObjectProvider infoDisplayObjectProvider =
			_getConfigurationInfoDisplayObjectProvider(
				fragmentRendererContext, httpServletRequest);

		if (infoDisplayObjectProvider == null) {
			return null;
		}

		return infoDisplayObjectProvider.getDisplayObject();
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

		List<InfoItemRenderer> infoItemRenderers = _getInfoItemRenderers(
			displayObjectClass);

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

	private List<InfoItemRenderer> _getInfoItemRenderers(Class<?> clazz) {
		Class<?>[] interfaces = clazz.getInterfaces();

		if (interfaces.length != 0) {
			for (Class<?> anInterface : interfaces) {
				List<InfoItemRenderer> infoItemRenderers =
					_infoItemRendererTracker.getInfoItemRenderers(
						anInterface.getName());

				if (!infoItemRenderers.isEmpty()) {
					return infoItemRenderers;
				}
			}
		}

		Class<?> superclass = clazz.getSuperclass();

		if (superclass != null) {
			return _getInfoItemRenderers(superclass);
		}

		return null;
	}

	private void _printPortletMessageInfo(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, String message) {

		try {
			PrintWriter printWriter = httpServletResponse.getWriter();

			StringBundler sb = new StringBundler(3);

			sb.append("<div class=\"portlet-msg-info\">");

			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				"content.Language", themeDisplay.getLocale(), getClass());

			sb.append(LanguageUtil.get(resourceBundle, message));

			sb.append("</div>");

			printWriter.write(sb.toString());
		}
		catch (IOException ioe) {
			if (_log.isDebugEnabled()) {
				_log.debug(ioe, ioe);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutDisplayObjectFragmentRenderer.class);

	@Reference
	private InfoDisplayContributorTracker _infoDisplayContributorTracker;

	@Reference
	private InfoItemRendererTracker _infoItemRendererTracker;

}