/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.engine.SearchEngineInformation;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.DocumentToSynonymSetTranslator;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.SynonymSet;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.name.SynonymSetIndexName;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.name.SynonymSetIndexNameBuilder;
import com.liferay.portal.search.tuning.synonyms.web.internal.request.SearchSynonymSetRequest;
import com.liferay.portal.search.tuning.synonyms.web.internal.request.SearchSynonymSetResponse;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionURL;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.RenderURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Filipe Oshiro
 */
public class SynonymsDisplayBuilder {

	public SynonymsDisplayBuilder(
		DocumentToSynonymSetTranslator documentToSynonymSetTranslator,
		HttpServletRequest httpServletRequest, Language language, Portal portal,
		Queries queries, RenderRequest renderRequest,
		RenderResponse renderResponse, SearchEngineAdapter searchEngineAdapter,
		SearchEngineInformation searchEngineInformation, Sorts sorts,
		SynonymSetIndexNameBuilder synonymSetIndexNameBuilder) {

		_documentToSynonymSetTranslator = documentToSynonymSetTranslator;
		_httpServletRequest = httpServletRequest;
		_language = language;
		_portal = portal;
		_queries = queries;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_searchEngineAdapter = searchEngineAdapter;
		_searchEngineInformation = searchEngineInformation;
		_sorts = sorts;
		_synonymSetIndexNameBuilder = synonymSetIndexNameBuilder;
	}

	public SynonymsDisplayContext build() {
		SynonymsDisplayContext synonymsDisplayContext =
			new SynonymsDisplayContext();

		if (Objects.equals(
				_searchEngineInformation.getVendorString(), "Solr")) {

			return synonymsDisplayContext;
		}

		synonymsDisplayContext.setCreationMenu(getCreationMenu());

		SearchContainer<SynonymSetDisplayContext> searchContainer =
			buildSearchContainer();

		List<SynonymSetDisplayContext> synonymSetDisplayContexts =
			searchContainer.getResults();

		synonymsDisplayContext.setDisabledManagementBar(
			isDisabledManagementBar(synonymSetDisplayContexts));

		synonymsDisplayContext.setDropdownItems(getDropdownItems());
		synonymsDisplayContext.setItemsTotal(synonymSetDisplayContexts.size());
		synonymsDisplayContext.setSearchContainer(searchContainer);

		return synonymsDisplayContext;
	}

	public String getDisplayedSynonymSet(String synonymSet) {
		return StringUtil.replace(synonymSet, ',', ", ");
	}

	protected RenderURL buildEditRenderURL(SynonymSet synonymSet) {
		RenderURL editRenderURL = _renderResponse.createRenderURL();

		editRenderURL.setParameter("mvcRenderCommandName", "editSynonymSet");
		editRenderURL.setParameter(
			"redirect", _portal.getCurrentURL(_httpServletRequest));
		editRenderURL.setParameter("synonymSetId", synonymSet.getId());

		return editRenderURL;
	}

	protected SearchContainer<SynonymSetDisplayContext> buildSearchContainer() {
		SearchContainer<SynonymSetDisplayContext> searchContainer =
			new SearchContainer<>(
				_renderRequest, _getPortletURL(), null, "there-are-no-entries");

		searchContainer.setId("synonymSetsEntries");
		searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		SearchSynonymSetRequest searchSynonymSetRequest =
			new SearchSynonymSetRequest(
				buildSynonymSetIndexName(), _httpServletRequest, _queries,
				_sorts, searchContainer, _searchEngineAdapter);

		SearchSynonymSetResponse searchSynonymSetResponse =
			searchSynonymSetRequest.search();

		searchContainer.setResults(
			buildSynonymSetDisplayContexts(
				searchSynonymSetResponse.getSearchHits()));

		searchContainer.setSearch(true);
		searchContainer.setTotal(searchSynonymSetResponse.getTotalHits());

		return searchContainer;
	}

	protected SynonymSetDisplayContext buildSynonymSetDisplayContext(
		SynonymSet synonymSet) {

		SynonymSetDisplayContext synonymSetDisplayContext =
			new SynonymSetDisplayContext();

		String synonyms = synonymSet.getSynonyms();

		RenderURL editRenderURL = buildEditRenderURL(synonymSet);

		synonymSetDisplayContext.setDropDownItems(
			buildSynonymSetDropdownItemList(synonymSet, editRenderURL));
		synonymSetDisplayContext.setEditRenderURL(editRenderURL.toString());

		synonymSetDisplayContext.setDisplayedSynonymSet(
			getDisplayedSynonymSet(synonyms));
		synonymSetDisplayContext.setSynonymSetId(synonymSet.getId());
		synonymSetDisplayContext.setSynonyms(synonyms);

		return synonymSetDisplayContext;
	}

	protected List<SynonymSetDisplayContext> buildSynonymSetDisplayContexts(
		SearchHits searchHits) {

		List<SynonymSet> synonymSets =
			_documentToSynonymSetTranslator.translateAll(searchHits);

		Stream<SynonymSet> stream = synonymSets.stream();

		return stream.map(
			this::buildSynonymSetDisplayContext
		).collect(
			Collectors.toList()
		);
	}

	protected List<DropdownItem> buildSynonymSetDropdownItemList(
		SynonymSet synonymSet, RenderURL editRenderURL) {

		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setHref(editRenderURL);
				dropdownItem.setLabel(
					_language.get(_httpServletRequest, "edit"));
				dropdownItem.setQuickAction(true);
			}
		).add(
			dropdownItem -> {
				dropdownItem.putData("action", "delete");

				ActionURL deleteURL = _renderResponse.createActionURL();

				deleteURL.setParameter(
					ActionRequest.ACTION_NAME, "deleteSynonymSet");
				deleteURL.setParameter(Constants.CMD, Constants.DELETE);
				deleteURL.setParameter("rowIds", synonymSet.getId());
				deleteURL.setParameter(
					"redirect", _portal.getCurrentURL(_httpServletRequest));

				dropdownItem.putData("deleteURL", deleteURL.toString());

				dropdownItem.setIcon("times");
				dropdownItem.setLabel(
					_language.get(_httpServletRequest, "delete"));
				dropdownItem.setQuickAction(true);
			}
		).build();
	}

	protected SynonymSetIndexName buildSynonymSetIndexName() {
		return _synonymSetIndexNameBuilder.getSynonymSetIndexName(
			_portal.getCompanyId(_renderRequest));
	}

	protected CreationMenu getCreationMenu() {
		return CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(
					_renderResponse.createRenderURL(), "mvcRenderCommandName",
					"editSynonymSet", "redirect",
					_portal.getCurrentURL(_httpServletRequest));
				dropdownItem.setLabel(
					_language.get(_httpServletRequest, "new-synonym-set"));
			}
		).build();
	}

	protected List<DropdownItem> getDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.putData("action", "deleteMultipleSynonyms");
				dropdownItem.setIcon("times-circle");
				dropdownItem.setLabel(
					_language.get(_httpServletRequest, "delete"));
				dropdownItem.setQuickAction(true);
			}
		).build();
	}

	protected boolean isDisabledManagementBar(
		List<SynonymSetDisplayContext> synonymSetDisplayContexts) {

		if (synonymSetDisplayContexts.isEmpty()) {
			return true;
		}

		return false;
	}

	private PortletURL _getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/view.jsp");

		return portletURL;
	}

	private final DocumentToSynonymSetTranslator
		_documentToSynonymSetTranslator;
	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private final Portal _portal;
	private final Queries _queries;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final SearchEngineAdapter _searchEngineAdapter;
	private final SearchEngineInformation _searchEngineInformation;
	private final Sorts _sorts;
	private final SynonymSetIndexNameBuilder _synonymSetIndexNameBuilder;

}