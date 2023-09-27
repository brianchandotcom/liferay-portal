/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.portlet.shared.search;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.legacy.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.web.internal.display.context.PortletRequestThemeDisplaySupplier;
import com.liferay.portal.search.web.internal.display.context.ThemeDisplaySupplier;
import com.liferay.portal.search.web.internal.portlet.preferences.PortletPreferencesLookup;
import com.liferay.portal.search.web.internal.portlet.shared.task.helper.PortletSharedRequestHelper;
import com.liferay.portal.search.web.internal.search.request.SearchContainerBuilder;
import com.liferay.portal.search.web.internal.search.request.SearchContextBuilder;
import com.liferay.portal.search.web.internal.search.request.SearchRequestImpl;
import com.liferay.portal.search.web.internal.search.request.SearchResponseImpl;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;
import com.liferay.portal.search.web.portlet.shared.task.PortletSharedTaskExecutor;
import com.liferay.portal.search.web.search.request.SearchSettings;
import com.liferay.portal.search.web.search.request.SearchSettingsContributor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(service = PortletSharedSearchRequest.class)
public class PortletSharedSearchRequestImpl
	implements PortletSharedSearchRequest {

	@Override
	public PortletSharedSearchResponse search(RenderRequest renderRequest) {
		return portletSharedTaskExecutor.executeOnlyOnce(
			() -> _search(renderRequest),
			PortletSharedSearchResponse.class.getSimpleName(), renderRequest);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, PortletSharedSearchContributor.class,
			"javax.portlet.name");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	protected ThemeDisplay getThemeDisplay(RenderRequest renderRequest) {
		ThemeDisplaySupplier themeDisplaySupplier =
			new PortletRequestThemeDisplaySupplier(renderRequest);

		return themeDisplaySupplier.getThemeDisplay();
	}

	@Reference
	protected PortletLocalService portletLocalService;

	@Reference
	protected PortletPreferencesLocalService portletPreferencesLocalService;

	@Reference
	protected PortletPreferencesLookup portletPreferencesLookup;

	@Reference
	protected PortletSharedRequestHelper portletSharedRequestHelper;

	@Reference
	protected PortletSharedTaskExecutor portletSharedTaskExecutor;

	@Reference
	protected Searcher searcher;

	@Reference
	protected SearchRequestBuilderFactory searchRequestBuilderFactory;

	private SearchContainer<Document> _buildSearchContainer(
		SearchSettings searchSettings, RenderRequest renderRequest) {

		PortletRequest portletRequest = renderRequest;

		DisplayTerms displayTerms = null;
		DisplayTerms searchTerms = null;

		String curParam = GetterUtil.getString(
			searchSettings.getPaginationStartParameterName(),
			SearchContainer.DEFAULT_CUR_PARAM);

		int cur = GetterUtil.getInteger(searchSettings.getPaginationStart());

		int delta = GetterUtil.getInteger(
			searchSettings.getPaginationDelta(), SearchContainer.DEFAULT_DELTA);

		PortletURL portletURL = new NullPortletURL();

		List<String> headerNames = null;
		String emptyResultsMessage = null;
		String cssClass = null;

		return new SearchContainer<>(
			portletRequest, displayTerms, searchTerms, curParam, cur, delta,
			portletURL, headerNames, emptyResultsMessage, cssClass);
	}

	private SearchContext _buildSearchContext(ThemeDisplay themeDisplay) {
		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(themeDisplay.getCompanyId());
		searchContext.setLayout(themeDisplay.getLayout());
		searchContext.setLocale(themeDisplay.getLocale());
		searchContext.setTimeZone(themeDisplay.getTimeZone());
		searchContext.setUserId(themeDisplay.getUserId());

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setCollatedSpellCheckResultEnabled(false);
		queryConfig.setLocale(themeDisplay.getLocale());

		return searchContext;
	}

	private SearchRequestImpl _createSearchRequestImpl(
		ThemeDisplay themeDisplay, RenderRequest renderRequest) {

		SearchContextBuilder searchContextBuilder = () -> _buildSearchContext(
			themeDisplay);

		SearchContainerBuilder searchContainerBuilder =
			searchSettings -> _buildSearchContainer(
				searchSettings, renderRequest);

		return new SearchRequestImpl(
			searchContextBuilder, searchContainerBuilder, searcher,
			searchRequestBuilderFactory);
	}

	private List<Portlet> _getInstantiatedPortlets(
		Layout layout, long companyId) {

		List<Portlet> portlets = new ArrayList<>();

		List<PortletPreferences> portletPreferencesList =
			portletPreferencesLocalService.getPortletPreferences(
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid());

		for (PortletPreferences portletPreferences : portletPreferencesList) {
			Portlet portlet = portletLocalService.getPortletById(
				companyId, portletPreferences.getPortletId());

			if (portlet.isInstanceable() &&
				Validator.isNotNull(portlet.getInstanceId())) {

				portlets.add(portlet);
			}
		}

		return portlets;
	}

	private List<Portlet> _getPortlets(Layout layout, long companyId) {
		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		List<Portlet> portlets = layoutTypePortlet.getAllPortlets(false);

		if (Objects.equals(layout.getType(), LayoutConstants.TYPE_PORTLET)) {
			return portlets;
		}

		List<Portlet> instantiatedPortlets = _getInstantiatedPortlets(
			layout, companyId);

		for (Portlet instantiatedPortlet : instantiatedPortlets) {
			if (!portlets.contains(instantiatedPortlet)) {
				portlets.add(instantiatedPortlet);
			}
		}

		return portlets;
	}

	private SearchSettingsContributor _getSearchSettingsContributor(
		Portlet portlet, ThemeDisplay themeDisplay,
		RenderRequest renderRequest) {

		PortletSharedSearchContributor portletSharedSearchContributor =
			_serviceTrackerMap.getService(portlet.getPortletName());

		if (portletSharedSearchContributor == null) {
			return null;
		}

		return _getSearchSettingsContributor(
			portletSharedSearchContributor, portlet, themeDisplay,
			renderRequest);
	}

	private SearchSettingsContributor _getSearchSettingsContributor(
		PortletSharedSearchContributor portletSharedSearchContributor,
		Portlet portlet, ThemeDisplay themeDisplay,
		RenderRequest renderRequest) {

		return searchSettings -> portletSharedSearchContributor.contribute(
			new PortletSharedSearchSettingsImpl(
				searchSettings, portlet.getPortletId(),
				portletPreferencesLookup.fetchPreferences(
					portlet, themeDisplay),
				portletSharedRequestHelper, renderRequest));
	}

	private List<SearchSettingsContributor> _getSearchSettingsContributors(
		ThemeDisplay themeDisplay, RenderRequest renderRequest) {

		List<SearchSettingsContributor> searchSettingsContributors =
			new ArrayList<>();

		List<Portlet> portlets = _getPortlets(
			themeDisplay.getLayout(), themeDisplay.getCompanyId());

		for (Portlet portlet : portlets) {
			SearchSettingsContributor searchSettingsContributor =
				_getSearchSettingsContributor(
					portlet, themeDisplay, renderRequest);

			if (searchSettingsContributor != null) {
				searchSettingsContributors.add(searchSettingsContributor);
			}
		}

		return searchSettingsContributors;
	}

	private PortletSharedSearchResponse _search(RenderRequest renderRequest) {
		ThemeDisplay themeDisplay = getThemeDisplay(renderRequest);

		SearchRequestImpl searchRequestImpl = _createSearchRequestImpl(
			themeDisplay, renderRequest);

		List<SearchSettingsContributor> searchSettingsContributors =
			_getSearchSettingsContributors(themeDisplay, renderRequest);

		for (SearchSettingsContributor searchSettingsContributor :
				searchSettingsContributors) {

			searchRequestImpl.addSearchSettingsContributor(
				searchSettingsContributor);
		}

		SearchResponseImpl searchResponseImpl = searchRequestImpl.search();

		return new PortletSharedSearchResponseImpl(
			searchResponseImpl, portletSharedRequestHelper);
	}

	private ServiceTrackerMap<String, PortletSharedSearchContributor>
		_serviceTrackerMap;

}