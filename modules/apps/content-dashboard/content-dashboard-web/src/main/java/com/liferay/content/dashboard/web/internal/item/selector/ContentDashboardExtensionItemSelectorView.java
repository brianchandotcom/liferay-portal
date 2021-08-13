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

package com.liferay.content.dashboard.web.internal.item.selector;

import com.liferay.content.dashboard.web.internal.display.context.ContentDashboardExtensionItemSelectorViewDisplayContext;
import com.liferay.content.dashboard.web.internal.item.selector.criteria.content.dashboard.extension.ContentDashboardExtensionItemSelectorCriterion;
import com.liferay.document.library.configuration.DLConfiguration;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Stream;

import javax.portlet.PortletURL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	configurationPid = "com.liferay.document.library.configuration.DLConfiguration",
	service = ItemSelectorView.class
)
public class ContentDashboardExtensionItemSelectorView
	implements ItemSelectorView
		<ContentDashboardExtensionItemSelectorCriterion> {

	@Override
	public Class<ContentDashboardExtensionItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return ContentDashboardExtensionItemSelectorCriterion.class;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, getClass());

		return ResourceBundleUtil.getString(resourceBundle, "extension");
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			ContentDashboardExtensionItemSelectorCriterion
				contentDashboardExtensionItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(
				"/view_content_dashboard_extensions.jsp");

		servletRequest.setAttribute(
			ContentDashboardExtensionItemSelectorViewDisplayContext.class.
				getName(),
			new ContentDashboardExtensionItemSelectorViewDisplayContext(
				_getContentDashboardExtensionGroupsJSONArray(servletRequest),
				itemSelectedEventName));

		requestDispatcher.include(servletRequest, servletResponse);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_dlConfiguration = ConfigurableUtil.createConfigurable(
			DLConfiguration.class, properties);
	}

	private JSONObject _getContentDashboardExtensionGroupJSONObject(
		Set<String> checkedExtensions, String icon, String labelKey,
		Locale locale, String[] mimeTypes) {

		JSONArray extensionsJSONArray = JSONFactoryUtil.createJSONArray();

		Stream<String> stream = Arrays.stream(mimeTypes);

		stream.map(
			MimeTypesUtil::getExtensions
		).flatMap(
			Set::stream
		).forEach(
			extension -> extensionsJSONArray.put(
				JSONUtil.put(
					"extension", extension.replaceAll("^\\.", StringPool.BLANK)
				).put(
					"selected", checkedExtensions.contains(extension)
				))
		);

		return JSONUtil.put(
			"extensions", extensionsJSONArray
		).put(
			"icon", icon
		).put(
			"label", LanguageUtil.get(locale, labelKey)
		);
	}

	private JSONArray _getContentDashboardExtensionGroupsJSONArray(
		ServletRequest servletRequest) {

		Set<String> checkedExtensions = SetUtil.fromArray(
			servletRequest.getParameterValues("checkedExtensions"));

		ThemeDisplay themeDisplay = (ThemeDisplay)servletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Locale locale = themeDisplay.getLocale();

		return JSONUtil.putAll(
			_getContentDashboardExtensionGroupJSONObject(
				checkedExtensions, "document-multimedia", "audio", locale,
				PropsValues.DL_FILE_ENTRY_PREVIEW_AUDIO_MIME_TYPES),
			_getContentDashboardExtensionGroupJSONObject(
				checkedExtensions, "document-code", "code", locale,
				_dlConfiguration.codeFileMimeTypes()),
			_getContentDashboardExtensionGroupJSONObject(
				checkedExtensions, "document-compressed", "compressed", locale,
				_dlConfiguration.compressedFileMimeTypes()),
			_getContentDashboardExtensionGroupJSONObject(
				checkedExtensions, "document-image", "image", locale,
				PropsValues.DL_FILE_ENTRY_PREVIEW_IMAGE_MIME_TYPES),
			_getContentDashboardExtensionGroupJSONObject(
				checkedExtensions, "document-presentation", "presentation",
				locale, _dlConfiguration.presentationFileMimeTypes()),
			_getContentDashboardExtensionGroupJSONObject(
				checkedExtensions, "document-table", "spreadsheet", locale,
				_dlConfiguration.spreadSheetFileMimeTypes()),
			_getContentDashboardExtensionGroupJSONObject(
				checkedExtensions, "document-text", "text", locale,
				_dlConfiguration.textFileMimeTypes()),
			_getContentDashboardExtensionGroupJSONObject(
				checkedExtensions, "document-pdf", "vectorial", locale,
				_dlConfiguration.vectorialFileMimeTypes()),
			_getContentDashboardExtensionGroupJSONObject(
				checkedExtensions, "document-multimedia", "video", locale,
				PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_MIME_TYPES));
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new UUIDItemSelectorReturnType());

	private volatile DLConfiguration _dlConfiguration;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.content.dashboard.web)"
	)
	private ServletContext _servletContext;

}