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

package com.liferay.commerce.product.content.search.web.internal.portlet;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.constants.CPWebKeys;
import com.liferay.commerce.product.content.search.web.internal.configuration.CPSearchResultsPortletInstanceConfiguration;
import com.liferay.commerce.product.content.search.web.internal.constants.CPSearchResultsConfigurationConstants;
import com.liferay.commerce.product.content.search.web.internal.display.context.CPSearchResultsDisplayContext;
import com.liferay.commerce.product.links.CPDefinitionLinkTypeRegistry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.search.generic.BooleanClauseImpl;
import com.liferay.portal.kernel.search.generic.TermQueryImpl;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;

import java.io.IOException;

import java.util.Optional;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-cp-search-results",
		"com.liferay.portlet.display-category=commerce",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.restore-current-view=false",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Search Results",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/search_results/view.jsp",
		"javax.portlet.name=" + CPPortletKeys.CP_SEARCH_RESULTS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = {Portlet.class, PortletSharedSearchContributor.class}
)
public class CPSearchResultsPortlet
	extends MVCPortlet implements PortletSharedSearchContributor {

	@Override
	public void contribute(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		RenderRequest renderRequest =
			portletSharedSearchSettings.getRenderRequest();

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		try {
			_cpSearchResultsPortletInstanceConfiguration =
				portletDisplay.getPortletInstanceConfiguration(
					CPSearchResultsPortletInstanceConfiguration.class);
		}
		catch (ConfigurationException ce) {
			_log.error(ce, ce);
		}

		Optional<String> parameterValueOptional =
			portletSharedSearchSettings.getParameter("q");

		portletSharedSearchSettings.setKeywords(
			parameterValueOptional.orElse("*"));

		portletSharedSearchSettings.addCondition(
			new BooleanClauseImpl<Query>(
				new TermQueryImpl(
					Field.ENTRY_CLASS_NAME, CPDefinition.class.getName()),
				BooleanClauseOccur.MUST));

		String configurationMethod =
			_cpSearchResultsPortletInstanceConfiguration.configurationMethod();

		if (configurationMethod.equals(
				CPSearchResultsConfigurationConstants.SELECT_CATEGORIES)) {

			String[] assetCategoryIds =
				_cpSearchResultsPortletInstanceConfiguration.assetCategoryIds();

			portletSharedSearchSettings.addCondition(
				new BooleanClauseImpl<Query>(
					new TermQueryImpl(
						Field.ASSET_CATEGORY_IDS,
						StringUtil.merge(assetCategoryIds)),
					BooleanClauseOccur.MUST));
		}
		else {
			AssetCategory assetCategory =
				(AssetCategory)renderRequest.getAttribute(
					WebKeys.ASSET_CATEGORY);

			if (assetCategory != null) {
				portletSharedSearchSettings.addCondition(
					new BooleanClauseImpl<Query>(
						new TermQueryImpl(
							Field.ASSET_CATEGORY_IDS,
							String.valueOf(assetCategory.getCategoryId())),
						BooleanClauseOccur.MUST));
			}

			CPDefinition cpDefinition =
				(CPDefinition)renderRequest.getAttribute(
					CPWebKeys.CP_DEFINITION);

			if ((cpDefinition != null) &&
				Validator.isNotNull(configurationMethod)) {

				portletSharedSearchSettings.addCondition(
					new BooleanClauseImpl<Query>(
						new TermQueryImpl(
							configurationMethod,
							String.valueOf(cpDefinition.getCPDefinitionId())),
						BooleanClauseOccur.MUST));
			}
		}

		SearchContext searchContext =
			portletSharedSearchSettings.getSearchContext();

		QueryConfig queryConfig = portletSharedSearchSettings.getQueryConfig();

		queryConfig.setHighlightEnabled(false);

		searchContext.setSorts(SortFactoryUtil.create(Field.TITLE, false));

		filterByThisSite(portletSharedSearchSettings);

		paginate(
			_cpSearchResultsPortletInstanceConfiguration,
			portletSharedSearchSettings);
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			renderRequest);

		PortletSharedSearchResponse portletSharedSearchResponse =
			_portletSharedSearchRequest.search(renderRequest);

		try {
			CPSearchResultsDisplayContext cpSearchResultsDisplayContext =
				new CPSearchResultsDisplayContext(
					_cpDefinitionHelper, _cpDefinitionLinkTypeRegistry,
					_cpInstanceHelper, _dlAppService, httpServletRequest,
					portletSharedSearchResponse);

			renderRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT, cpSearchResultsDisplayContext);
		}
		catch (ConfigurationException ce) {
			_log.error(ce, ce);
		}

		super.render(renderRequest, renderResponse);
	}

	protected void filterByThisSite(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		long groupIdOptional = getScopeGroupId(portletSharedSearchSettings);

		portletSharedSearchSettings.addCondition(
			new BooleanClauseImpl<Query>(
				new TermQueryImpl(
					Field.GROUP_ID, String.valueOf(groupIdOptional)),
				BooleanClauseOccur.MUST));
	}

	protected long getScopeGroupId(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		ThemeDisplay themeDisplay =
			portletSharedSearchSettings.getThemeDisplay();

		return themeDisplay.getScopeGroupId();
	}

	protected void paginate(
		CPSearchResultsPortletInstanceConfiguration
			cpSearchResultsPortletInstanceConfiguration,
		PortletSharedSearchSettings portletSharedSearchSettings) {

		String paginationStartParameterName = "start";

		portletSharedSearchSettings.setPaginationStartParameterName(
			paginationStartParameterName);

		Optional<String> paginationStartParameterValueOptional =
			portletSharedSearchSettings.getParameter(
				paginationStartParameterName);

		Optional<Integer> paginationStartOptional =
			paginationStartParameterValueOptional.map(Integer::valueOf);

		paginationStartOptional.ifPresent(
			portletSharedSearchSettings::setPaginationStart);

		String paginationDeltaParameterName = "delta";

		Optional<String> paginationDeltaParameterValueOptional =
			portletSharedSearchSettings.getParameter(
				paginationDeltaParameterName);

		Optional<Integer> paginationDeltaOptional =
			paginationDeltaParameterValueOptional.map(Integer::valueOf);

		int paginationDelta = paginationDeltaOptional.orElse(
			cpSearchResultsPortletInstanceConfiguration.paginationDelta());

		portletSharedSearchSettings.setPaginationDelta(paginationDelta);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPSearchResultsPortlet.class);

	@Reference
	private CPDefinitionHelper _cpDefinitionHelper;

	@Reference
	private CPDefinitionLinkTypeRegistry _cpDefinitionLinkTypeRegistry;

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	private CPSearchResultsPortletInstanceConfiguration
		_cpSearchResultsPortletInstanceConfiguration;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletSharedSearchRequest _portletSharedSearchRequest;

}