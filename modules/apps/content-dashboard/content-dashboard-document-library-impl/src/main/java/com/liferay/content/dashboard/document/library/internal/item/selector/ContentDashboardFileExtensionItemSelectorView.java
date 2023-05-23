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

package com.liferay.content.dashboard.document.library.internal.item.selector;

import com.liferay.content.dashboard.document.library.internal.item.display.context.ContentDashboardFileExtensionItemSelectorViewDisplayContext;
import com.liferay.content.dashboard.document.library.internal.item.provider.FileExtensionGroupsProvider;
import com.liferay.document.library.display.context.DLMimeTypeDisplayContext;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.item.selector.criteria.file.criterion.FileExtensionItemSelectorCriterion;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.Bucket;
import com.liferay.portal.search.aggregation.bucket.TermsAggregationResult;
import com.liferay.portal.search.legacy.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.portlet.PortletURL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Alejandro Tardín
 */
@Component(
	configurationPid = "com.liferay.document.library.configuration.DLConfiguration",
	service = ItemSelectorView.class
)
public class ContentDashboardFileExtensionItemSelectorView
	implements ItemSelectorView<FileExtensionItemSelectorCriterion> {

	@Override
	public Class<FileExtensionItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return FileExtensionItemSelectorCriterion.class;
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
			FileExtensionItemSelectorCriterion
				fileExtensionItemSelectorCriterion,
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
					fileExtensionItemSelectorCriterion, servletRequest),
				itemSelectedEventName));

		requestDispatcher.include(servletRequest, servletResponse);
	}

	private JSONArray _getContentDashboardFileExtensionGroupsJSONArray(
		FileExtensionItemSelectorCriterion fileExtensionItemSelectorCriterion,
		ServletRequest servletRequest) {

		ThemeDisplay themeDisplay = (ThemeDisplay)servletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<FileExtensionGroupsProvider.FileExtensionGroup>
			fileExtensionGroups =
				_fileExtensionGroupsProvider.getFileExtensionGroups();

		List<String> existingFileExtensions = _getExistingFileExtensions(
			fileExtensionItemSelectorCriterion, themeDisplay.getRequest());

		Set<String> otherFileExtensions = SetUtil.fromList(
			ListUtil.filter(
				existingFileExtensions, _fileExtensionGroupsProvider::isOther));

		return JSONUtil.toJSONArray(
			fileExtensionGroups,
			fileExtensionGroup -> fileExtensionGroup.toJSONObject(
				SetUtil.fromArray(
					servletRequest.getParameterValues("checkedFileExtensions")),
				existingFileExtensions, _dlMimeTypeDisplayContext, _language,
				themeDisplay.getLocale(), otherFileExtensions),
			_log);
	}

	private List<String> _getExistingFileExtensions(
		FileExtensionItemSelectorCriterion fileExtensionItemSelectorCriterion,
		HttpServletRequest httpServletRequest) {

		SearchContext searchContext = SearchContextFactory.getInstance(
			httpServletRequest);

		long[] selectedGroupIds =
			fileExtensionItemSelectorCriterion.getSelectedGroupIds();

		if (selectedGroupIds != null) {
			searchContext.setGroupIds(selectedGroupIds);
		}
		else {
			searchContext.setGroupIds(new long[0]);
		}

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder(
				searchContext
			).emptySearchEnabled(
				true
			).entryClassNames(
				DLFileEntry.class.getName()
			).fields(
				Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK, Field.UID
			).highlightEnabled(
				false
			);

		SearchResponse searchResponse = _searcher.search(
			searchRequestBuilder.addAggregation(
				_aggregations.terms("extensions", "extension")
			).size(
				0
			).build());

		TermsAggregationResult termsAggregationResult =
			(TermsAggregationResult)searchResponse.getAggregationResult(
				"extensions");

		return TransformUtil.transform(
			termsAggregationResult.getBuckets(), Bucket::getKey);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContentDashboardFileExtensionItemSelectorView.class);

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new UUIDItemSelectorReturnType());

	@Reference
	private Aggregations _aggregations;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile DLMimeTypeDisplayContext _dlMimeTypeDisplayContext;

	@Reference
	private FileExtensionGroupsProvider _fileExtensionGroupsProvider;

	@Reference
	private Language _language;

	@Reference
	private Searcher _searcher;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.content.dashboard.document.library.impl)"
	)
	private ServletContext _servletContext;

}