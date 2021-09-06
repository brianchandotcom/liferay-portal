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
import com.liferay.content.dashboard.web.internal.searcher.ContentDashboardSearchRequestBuilderFactory;
import com.liferay.document.library.configuration.DLConfiguration;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.Bucket;
import com.liferay.portal.search.aggregation.bucket.TermsAggregationResult;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletURL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

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

		_fileExtensionGroups = Arrays.asList(
			new FileExtensionGroup(
				"document-multimedia", "audio",
				PropsValues.DL_FILE_ENTRY_PREVIEW_AUDIO_MIME_TYPES),
			new FileExtensionGroup(
				"document-code", "code", _dlConfiguration.codeFileMimeTypes()),
			new FileExtensionGroup(
				"document-compressed", "compressed",
				_dlConfiguration.compressedFileMimeTypes()),
			new FileExtensionGroup(
				"document-image", "image",
				PropsValues.DL_FILE_ENTRY_PREVIEW_IMAGE_MIME_TYPES),
			new FileExtensionGroup(
				"document-presentation", "presentation",
				_dlConfiguration.presentationFileMimeTypes()),
			new FileExtensionGroup(
				"document-table", "spreadsheet",
				_dlConfiguration.spreadSheetFileMimeTypes()),
			new FileExtensionGroup(
				"document-text", "text", _dlConfiguration.textFileMimeTypes()),
			new FileExtensionGroup(
				"document-pdf", "vectorial",
				_dlConfiguration.vectorialFileMimeTypes()),
			new FileExtensionGroup(
				"document-multimedia", "video",
				PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_MIME_TYPES),
			new FileExtensionGroup("document-default", "other", new String[0]));

		_fileExtensionFileExtensionGroupKeys = new HashMap<>();

		for (FileExtensionGroup fileExtensionGroup : _fileExtensionGroups) {
			for (String mimeType : fileExtensionGroup.getMimeTypes()) {
				for (String fileExtension :
						MimeTypesUtil.getExtensions(mimeType)) {

					_fileExtensionFileExtensionGroupKeys.put(
						fileExtension.replaceAll("^\\.", StringPool.BLANK),
						fileExtensionGroup.getKey());
				}
			}
		}
	}

	private JSONArray _getContentDashboardFileExtensionGroupsJSONArray(
		ServletRequest servletRequest) {

		Set<String> checkedFileExtensions = SetUtil.fromArray(
			servletRequest.getParameterValues("checkedFileExtensions"));

		ThemeDisplay themeDisplay = (ThemeDisplay)servletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<String> existingFileExtensions = _getExistingFileExtensions(
			themeDisplay.getRequest());

		Stream<String> stream = existingFileExtensions.stream();

		Map<String, List<String>> fileExtensionGroupKeyFileExtensions =
			stream.filter(
				Objects::nonNull
			).collect(
				Collectors.groupingBy(
					fileExtension ->
						_fileExtensionFileExtensionGroupKeys.getOrDefault(
							fileExtension, "other"))
			);

		Stream<FileExtensionGroup> fileExtensionGroupStream =
			_fileExtensionGroups.stream();

		return JSONUtil.putAll(
			fileExtensionGroupStream.map(
				fileExtensionGroup -> {
					List<String> fileExtensions =
						fileExtensionGroupKeyFileExtensions.get(
							fileExtensionGroup.getKey());

					if (ListUtil.isEmpty(fileExtensions)) {
						return null;
					}

					Stream<String> fileExtensionsStream =
						fileExtensions.stream();

					return JSONUtil.put(
						"fileExtensions",
						JSONUtil.putAll(
							fileExtensionsStream.sorted(
							).map(
								fileExtension -> JSONUtil.put(
									"fileExtension", fileExtension
								).put(
									"selected",
									checkedFileExtensions.contains(
										fileExtension)
								)
							).toArray())
					).put(
						"icon", fileExtensionGroup.getIcon()
					).put(
						"label",
						LanguageUtil.get(
							ResourceBundleUtil.getBundle(
								themeDisplay.getLocale(), getClass()),
							fileExtensionGroup.getKey())
					);
				}
			).filter(
				Objects::nonNull
			).toArray());
	}

	private List<String> _getExistingFileExtensions(
		HttpServletRequest httpServletRequest) {

		SearchContext searchContext = SearchContextFactory.getInstance(
			httpServletRequest);

		searchContext.setGroupIds(new long[0]);

		SearchRequestBuilder searchRequestBuilder =
			_contentDashboardSearchRequestBuilderFactory.builder(searchContext);

		SearchResponse searchResponse = _searcher.search(
			searchRequestBuilder.addAggregation(
				_aggregations.terms("extensions", "extension")
			).size(
				0
			).build());

		TermsAggregationResult termsAggregationResult =
			(TermsAggregationResult)searchResponse.getAggregationResult(
				"extensions");

		Collection<Bucket> buckets = termsAggregationResult.getBuckets();

		Stream<Bucket> stream = buckets.stream();

		return stream.map(
			bucket -> bucket.getKey()
		).collect(
			Collectors.toList()
		);
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new UUIDItemSelectorReturnType());

	@Reference
	private Aggregations _aggregations;

	@Reference
	private ContentDashboardSearchRequestBuilderFactory
		_contentDashboardSearchRequestBuilderFactory;

	private volatile DLConfiguration _dlConfiguration;
	private volatile Map<String, String> _fileExtensionFileExtensionGroupKeys =
		new HashMap<>();
	private volatile List<FileExtensionGroup> _fileExtensionGroups =
		new ArrayList<>();

	@Reference
	private Searcher _searcher;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.content.dashboard.web)"
	)
	private ServletContext _servletContext;

	private class FileExtensionGroup {

		public FileExtensionGroup(String icon, String key, String[] mimeTypes) {
			_icon = icon;
			_key = key;
			_mimeTypes = mimeTypes;
		}

		public String getIcon() {
			return _icon;
		}

		public String getKey() {
			return _key;
		}

		public List<String> getMimeTypes() {
			return Arrays.asList(_mimeTypes);
		}

		private final String _icon;
		private final String _key;
		private final String[] _mimeTypes;

	}

}