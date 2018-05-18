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

package com.liferay.commerce.product.definitions.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.search.CPDefinitionIndexer;
import com.liferay.commerce.product.search.FacetImpl;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelService;
import com.liferay.commerce.product.service.CPOptionService;
import com.liferay.commerce.product.type.CPType;
import com.liferay.commerce.product.type.CPTypeServicesTracker;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.CP_DEFINITIONS,
		"mvc.command.name=cpDefinitionsFacets"
	},
	service = MVCResourceCommand.class
)
public class CPDefinitionsFacetsMVCResourceCommand
	extends BaseMVCResourceCommand {

	protected SearchContext buildSearchContext(
		ResourceRequest resourceRequest) {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long[] groupIds = {themeDisplay.getScopeGroupId()};

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(themeDisplay.getCompanyId());
		searchContext.setGroupIds(groupIds);
		searchContext.setLayout(themeDisplay.getLayout());
		searchContext.setLocale(themeDisplay.getLocale());
		searchContext.setTimeZone(themeDisplay.getTimeZone());
		searchContext.setUserId(themeDisplay.getUserId());

		searchContext.setAttribute(Field.STATUS, WorkflowConstants.STATUS_ANY);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setLocale(themeDisplay.getLocale());

		return searchContext;
	}

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String fieldName = ParamUtil.getString(resourceRequest, "fieldName");
		String filterFields = ParamUtil.getString(
			resourceRequest, "filterFields");
		String filtersValues = ParamUtil.getString(
			resourceRequest, "filtersValues");
		String indexFieldName = _getIndexFieldName(fieldName);

		String currentOptionKey = StringPool.BLANK;

		if (fieldName.startsWith("OPTION_")) {
			currentOptionKey = fieldName.replace("OPTION_", StringPool.BLANK);
		}

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		SearchContext searchContext = buildSearchContext(resourceRequest);

		List<Facet> facets = _cpDefinitionHelper.getFacets(
			filterFields, filtersValues, searchContext);

		searchContext.setFacets(facets);

		Facet facet = null;

		if (filterFields.contains(fieldName)) {
			for (Facet curFacet : facets) {
				if (indexFieldName.equals(curFacet.getFieldName())) {
					facet = curFacet;

					break;
				}
			}
		}

		if (facet == null) {
			facet = new FacetImpl(indexFieldName, searchContext);

			searchContext.addFacet(facet);
		}

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.addSelectedFieldNames(indexFieldName);

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		Indexer<CPDefinition> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			CPDefinition.class);

		indexer.search(searchContext);

		FacetCollector facetCollector = facet.getFacetCollector();

		for (TermCollector termCollector : facetCollector.getTermCollectors()) {
			String label = termCollector.getTerm();

			JSONObject jsonObject = _jsonFactory.createJSONObject();

			jsonObject.put("term", termCollector.getTerm());

			if (fieldName.equals(Field.STATUS)) {
				int status = GetterUtil.getInteger(termCollector.getTerm());

				label = LanguageUtil.get(
					themeDisplay.getLocale(),
					WorkflowConstants.getStatusLabel(status));
			}
			else if (fieldName.equals(
						CPDefinitionIndexer.FIELD_PRODUCT_TYPE_NAME)) {

				String productTypeName = termCollector.getTerm();

				CPType cpType = _cpTypeServicesTracker.getCPType(
					productTypeName);

				label = cpType.getLabel(themeDisplay.getLocale());
			}
			else if (fieldName.equals(CPDefinitionIndexer.FIELD_OPTION_NAMES)) {
				String optionKey = termCollector.getTerm();

				CPOption cpOption = _cpOptionService.fetchCPOption(
					themeDisplay.getScopeGroupId(), optionKey);

				if (cpOption != null) {
					label = cpOption.getName(themeDisplay.getLocale());
				}
			}
			else if (Validator.isNotNull(currentOptionKey)) {
				List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
					_cpDefinitionOptionValueRelService.
						getCPDefinitionOptionValueRels(
							themeDisplay.getScopeGroupId(),
							termCollector.getTerm(), 0, 1);

				if (!cpDefinitionOptionValueRels.isEmpty()) {
					CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
						cpDefinitionOptionValueRels.get(0);

					label = cpDefinitionOptionValueRel.getName(
						themeDisplay.getLocale());
				}
			}

			jsonObject.put("frequency", termCollector.getFrequency());
			jsonObject.put("label", label);

			jsonArray.put(jsonObject);
		}

		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(resourceResponse);

		httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);

		ServletResponseUtil.write(httpServletResponse, jsonArray.toString());
	}

	private String _getIndexFieldName(String fieldName) {
		if (fieldName.startsWith("OPTION_")) {
			fieldName = fieldName.replace("OPTION_", StringPool.BLANK);

			return "ATTRIBUTE_" + fieldName + "_VALUES_NAMES";
		}

		return fieldName;
	}

	@Reference
	private CPDefinitionHelper _cpDefinitionHelper;

	@Reference
	private CPDefinitionOptionValueRelService
		_cpDefinitionOptionValueRelService;

	@Reference
	private CPOptionService _cpOptionService;

	@Reference
	private CPTypeServicesTracker _cpTypeServicesTracker;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}