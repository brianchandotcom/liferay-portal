/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.video.streaming;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ambrín Chaudhary
 */
@Component(service = FragmentRenderer.class)
public class VideoStreamingFragmentRenderer implements FragmentRenderer {

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
							"label", "url"
						).put(
							"name", "url"
						).put(
							"type", "text"
						),
						JSONUtil.put(
							"label", "autoplay"
						).put(
							"name", "autoplay"
						).put(
							"type", "checkbox"
						),
						JSONUtil.put(
							"label", "loop"
						).put(
							"name", "loop"
						).put(
							"type", "checkbox"
						),
						JSONUtil.put(
							"label", "mute"
						).put(
							"name", "mute"
						).put(
							"type", "checkbox"
						),
						JSONUtil.put(
							"label", "width"
						).put(
							"name", "videoWidth"
						).put(
							"type", "text"
						),
						JSONUtil.put(
							"label", "height"
						).put(
							"name", "videoHeight"
						).put(
							"type", "text"
						))
				).put(
					"label",
					_language.format(
						fragmentRendererContext.getLocale(), "video-options",
						"video-options", true)
				))
		).toString();
	}

	@Override
	public String getIcon() {
		return "video";
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "video-streaming");
	}

	public boolean hasViewPermission(
		FragmentRendererContext fragmentRendererContext,
		HttpServletRequest httpServletRequest) {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-200108")) {
			return false;
		}

		return true;
	}

	public boolean isSelectable(HttpServletRequest httpServletRequest) {
		if (!FeatureFlagManagerUtil.isEnabled("LPS-200108")) {
			return false;
		}

		return true;
	}

	@Override
	public void render(
		FragmentRendererContext fragmentRendererContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		try {
			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher("/page.jsp");

			FragmentEntryLink fragmentEntryLink =
				fragmentRendererContext.getFragmentEntryLink();

			String src = GetterUtil.getString(
				_fragmentEntryConfigurationParser.getFieldValue(
					getConfiguration(fragmentRendererContext),
					fragmentEntryLink.getEditableValues(),
					fragmentRendererContext.getLocale(), "url"));

			String height = GetterUtil.getString(
				_fragmentEntryConfigurationParser.getFieldValue(
					getConfiguration(fragmentRendererContext),
					fragmentEntryLink.getEditableValues(),
					fragmentRendererContext.getLocale(), "videoHeight"));

			String width = GetterUtil.getString(
				_fragmentEntryConfigurationParser.getFieldValue(
					getConfiguration(fragmentRendererContext),
					fragmentEntryLink.getEditableValues(),
					fragmentRendererContext.getLocale(), "videoWidth"));

			boolean loop = GetterUtil.getBoolean(
				_fragmentEntryConfigurationParser.getFieldValue(
					getConfiguration(fragmentRendererContext),
					fragmentEntryLink.getEditableValues(),
					fragmentRendererContext.getLocale(), "loop"));

			boolean muted = GetterUtil.getBoolean(
				_fragmentEntryConfigurationParser.getFieldValue(
					getConfiguration(fragmentRendererContext),
					fragmentEntryLink.getEditableValues(),
					fragmentRendererContext.getLocale(), "muted"));

			httpServletRequest.setAttribute("height", height);
			httpServletRequest.setAttribute("loop", loop);
			httpServletRequest.setAttribute("muted", muted);
			httpServletRequest.setAttribute("src", src);
			httpServletRequest.setAttribute("width", width);

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			_log.error("Unable to render video streaming fragment", exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		VideoStreamingFragmentRenderer.class);

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private Language _language;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.fragment.video.streaming)"
	)
	private ServletContext _servletContext;

}