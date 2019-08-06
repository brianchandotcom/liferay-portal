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

package com.liferay.portal.search.tuning.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.tuning.web.internal.synonym.SynonymException;
import com.liferay.portal.search.tuning.web.internal.synonym.SynonymIndexer;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionURL;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Filipe Oshiro
 */
public class SynonymsDisplayBuilder {

	public SynonymsDisplayBuilder(
			HttpServletRequest httpServletRequest, Language language,
			Portal portal, RenderRequest renderRequest,
			RenderResponse renderResponse, SynonymIndexer synonymIndexer)
		throws PortalException {

		_httpServletRequest = httpServletRequest;
		_language = language;
		_portal = portal;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_synonymIndexer = synonymIndexer;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public SynonymsDisplayContext build() throws PortalException {
		SynonymsDisplayContext synonymsDisplayContext =
			new SynonymsDisplayContext();

		synonymsDisplayContext.setCreationMenu(getCreationMenu());

		List<SynonymSetDisplayContext> synonymSetDisplayContexts =
			buildSynonymSetDisplayContexts();

		synonymsDisplayContext.setDisabledManagementBar(
			isDisabledManagementBar(synonymSetDisplayContexts));

		synonymsDisplayContext.setDropdownItems(getDropdownItems());
		synonymsDisplayContext.setItemsTotal(synonymSetDisplayContexts.size());
		synonymsDisplayContext.setSearchContainer(
			buildSearchContainer(synonymSetDisplayContexts));

		return synonymsDisplayContext;
	}

	public String getDisplayedSynonymSet(String synonymSet) {
		return synonymSet.replace(",", ", ");
	}

	protected SearchContainer<SynonymSetDisplayContext> buildSearchContainer(
			List<SynonymSetDisplayContext> synonymSetsEntryDisplayContexts)
		throws PortalException {

		SearchContainer<SynonymSetDisplayContext> searchContainer =
			new SearchContainer<>(
				_renderRequest, _getPortletURL(), null, "there-are-no-entries");

		searchContainer.setId("synonymSetsEntries");
		searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		searchContainer.setSearch(true);
		searchContainer.setTotal(synonymSetsEntryDisplayContexts.size());
		searchContainer.setResults(synonymSetsEntryDisplayContexts);

		return searchContainer;
	}

	protected List<SynonymSetDisplayContext> buildSynonymSetDisplayContexts()
		throws PortalException {

		List<SynonymSetDisplayContext> synonymSetDisplayContexts =
			new ArrayList<>();

		String[] synonymSets = null;

		try {
			synonymSets = _synonymIndexer.getSynonymSets(
				_themeDisplay.getCompanyId(), "liferay_filter_synonym_en");
		}
		catch (SynonymException se) {
			throw new PortalException(se);
		}

		for (String synonymSet : synonymSets) {
			SynonymSetDisplayContext synonymSetDisplayContext =
				new SynonymSetDisplayContext();

			synonymSetDisplayContext.setDropDownItems(
				buildSynonymSetDropdownItemList(synonymSet));
			synonymSetDisplayContext.setDisplayedSynonymSet(
				getDisplayedSynonymSet(synonymSet));
			synonymSetDisplayContext.setSynonyms(synonymSet);

			synonymSetDisplayContexts.add(synonymSetDisplayContext);
		}

		return synonymSetDisplayContexts;
	}

	protected List<DropdownItem> buildSynonymSetDropdownItemList(
		String synonymSet) {

		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setHref(
							_renderResponse.createRenderURL(),
							"mvcRenderCommandName", "updateSynonymSet",
							"redirect",
							_portal.getCurrentURL(_httpServletRequest),
							"synonymSets", synonymSet);

						dropdownItem.setLabel(
							_language.get(_httpServletRequest, "edit"));
						dropdownItem.setQuickAction(true);
					});

				add(
					dropdownItem -> {
						ActionURL deleteURL = _renderResponse.createActionURL();

						deleteURL.setParameter(
							ActionRequest.ACTION_NAME, "deleteSynonymSet");
						deleteURL.setParameter(Constants.CMD, Constants.DELETE);
						deleteURL.setParameter(
							"deletedSynonymSetsString", synonymSet);
						deleteURL.setParameter(
							"redirect",
							_portal.getCurrentURL(_httpServletRequest));

						dropdownItem.putData("action", "delete");
						dropdownItem.putData("deleteURL", deleteURL.toString());
						dropdownItem.setIcon("times");
						dropdownItem.setLabel(
							_language.get(_httpServletRequest, "delete"));
						dropdownItem.setQuickAction(true);
					});
			}
		};
	}

	protected CreationMenu getCreationMenu() {
		return new CreationMenu() {
			{
				addPrimaryDropdownItem(
					dropdownItem -> {
						dropdownItem.setHref(
							_renderResponse.createRenderURL(),
							"mvcRenderCommandName", "updateSynonymSet",
							"redirect",
							_portal.getCurrentURL(_httpServletRequest));
						dropdownItem.setLabel(
							_language.get(
								_httpServletRequest, "new-synonym-set"));
					});
			}
		};
	}

	protected List<DropdownItem> getDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.putData(
							"action", "deleteMultipleSynonyms");
						dropdownItem.setIcon("times-circle");
						dropdownItem.setLabel(
							_language.get(_httpServletRequest, "delete"));
						dropdownItem.setQuickAction(true);
					});
			}
		};
	}

	protected boolean isDisabledManagementBar(
			List<SynonymSetDisplayContext> synonymSetDisplayContexts)
		throws PortalException {

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

	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private final Portal _portal;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final SynonymIndexer _synonymIndexer;
	private final ThemeDisplay _themeDisplay;

}