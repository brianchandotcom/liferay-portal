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

import com.liferay.content.dashboard.web.internal.display.context.ContentDashboardFileExtensionItemSelectorViewDisplayContext;
import com.liferay.content.dashboard.web.internal.item.selector.criteria.content.dashboard.file.extension.criterion.ContentDashboardFileExtensionItemSelectorCriterion;
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
public class ContentDashboardFileExtensionItemSelectorView
	implements ItemSelectorView
		<ContentDashboardFileExtensionItemSelectorCriterion> {

	@Override
	public Class<ContentDashboardFileExtensionItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return ContentDashboardFileExtensionItemSelectorCriterion.class;
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
			ContentDashboardFileExtensionItemSelectorCriterion
				contentDashboardFileExtensionItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(
				"/view_content_dashboard_file_extensions.jsp");

		servletRequest.setAttribute(
			ContentDashboardFileExtensionItemSelectorViewDisplayContext.class.
				getName(),
			new ContentDashboardFileExtensionItemSelectorViewDisplayContext(
				_getContentDashboardFileExtensionGroupsJSONArray(
					servletRequest),
				itemSelectedEventName));

		requestDispatcher.include(servletRequest, servletResponse);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_dlConfiguration = ConfigurableUtil.createConfigurable(
			DLConfiguration.class, properties);
	}

	private JSONObject _getContentDashboardFileExtensionGroupJSONObject(
		Set<String> checkedFileExtensions, String icon, String labelKey,
		Locale locale, String[] mimeTypes) {

		JSONArray fileExtensionsJSONArray = JSONFactoryUtil.createJSONArray();

		Stream<String> stream = Arrays.stream(mimeTypes);

		stream.map(
			MimeTypesUtil::getExtensions
		).flatMap(
			Set::stream
		).distinct(
		).map(
			fileExtension -> fileExtension.replaceAll("^\\.", StringPool.BLANK)
		).forEach(
			fileExtension -> {
				fileExtensionsJSONArray.put(
					JSONUtil.put(
						"fileExtension", fileExtension
					).put(
						"selected",
						checkedFileExtensions.contains(fileExtension)
					));
			}
		);

		return JSONUtil.put(
			"fileExtensions", fileExtensionsJSONArray
		).put(
			"icon", icon
		).put(
			"label", LanguageUtil.get(locale, labelKey)
		);
	}

	private JSONArray _getContentDashboardFileExtensionGroupsJSONArray(
		ServletRequest servletRequest) {

		Set<String> checkedFileExtensions = SetUtil.fromArray(
			servletRequest.getParameterValues("checkedFileExtensions"));

		ThemeDisplay themeDisplay = (ThemeDisplay)servletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Locale locale = themeDisplay.getLocale();

		return JSONUtil.putAll(
			_getContentDashboardFileExtensionGroupJSONObject(
				checkedFileExtensions, "document-multimedia", "audio", locale,
				PropsValues.DL_FILE_ENTRY_PREVIEW_AUDIO_MIME_TYPES),
			_getContentDashboardFileExtensionGroupJSONObject(
				checkedFileExtensions, "document-code", "code", locale,
				_dlConfiguration.codeFileMimeTypes()),
			_getContentDashboardFileExtensionGroupJSONObject(
				checkedFileExtensions, "document-compressed", "compressed",
				locale, _dlConfiguration.compressedFileMimeTypes()),
			_getContentDashboardFileExtensionGroupJSONObject(
				checkedFileExtensions, "document-image", "image", locale,
				PropsValues.DL_FILE_ENTRY_PREVIEW_IMAGE_MIME_TYPES),
			_getContentDashboardFileExtensionGroupJSONObject(
				checkedFileExtensions, "document-presentation", "presentation",
				locale, _dlConfiguration.presentationFileMimeTypes()),
			_getContentDashboardFileExtensionGroupJSONObject(
				checkedFileExtensions, "document-table", "spreadsheet", locale,
				_dlConfiguration.spreadSheetFileMimeTypes()),
			_getContentDashboardFileExtensionGroupJSONObject(
				checkedFileExtensions, "document-text", "text", locale,
				_dlConfiguration.textFileMimeTypes()),
			_getContentDashboardFileExtensionGroupJSONObject(
				checkedFileExtensions, "document-pdf", "vectorial", locale,
				_dlConfiguration.vectorialFileMimeTypes()),
			_getContentDashboardFileExtensionGroupJSONObject(
				checkedFileExtensions, "document-multimedia", "video", locale,
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