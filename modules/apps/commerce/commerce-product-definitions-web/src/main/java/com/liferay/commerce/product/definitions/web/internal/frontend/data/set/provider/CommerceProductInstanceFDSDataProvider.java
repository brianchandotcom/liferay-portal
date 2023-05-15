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

package com.liferay.commerce.product.definitions.web.internal.frontend.data.set.provider;

import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.frontend.model.LabelField;
import com.liferay.commerce.inventory.engine.CommerceInventoryEngine;
import com.liferay.commerce.price.CommerceProductPriceCalculation;
import com.liferay.commerce.product.definitions.web.internal.constants.CommerceProductFDSNames;
import com.liferay.commerce.product.definitions.web.internal.model.Sku;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.product.util.JsonHelper;
import com.liferay.frontend.data.set.provider.FDSDataProvider;
import com.liferay.frontend.data.set.provider.search.FDSKeywords;
import com.liferay.frontend.data.set.provider.search.FDSPagination;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringJoiner;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"fds.data.provider.key=" + CommerceProductFDSNames.ALL_PRODUCT_INSTANCES,
		"fds.data.provider.key=" + CommerceProductFDSNames.PRODUCT_INSTANCES
	},
	service = FDSDataProvider.class
)
public class CommerceProductInstanceFDSDataProvider
	implements FDSDataProvider<Sku> {

	@Override
	public List<Sku> getItems(
			FDSKeywords fdsKeywords, FDSPagination fdsPagination,
			HttpServletRequest httpServletRequest, Sort sort)
		throws PortalException {

		List<Sku> skus = new ArrayList<>();

		long cpDefinitionId = ParamUtil.getLong(
			httpServletRequest, "cpDefinitionId");

		Locale locale = _portal.getLocale(httpServletRequest);

		String languageId = _language.getLanguageId(locale);

		List<CPInstance> cpInstances = _getCPInstances(
			_portal.getCompanyId(httpServletRequest), cpDefinitionId,
			fdsKeywords.getKeywords(), fdsPagination.getStartPosition(),
			fdsPagination.getEndPosition(), sort);

		for (CPInstance cpInstance : cpInstances) {
			Map<String, List<String>>
				cpDefinitionOptionRelKeysCPDefinitionOptionValueRelKeys =
					_cpDefinitionOptionRelLocalService.
						getCPDefinitionOptionRelKeysCPDefinitionOptionValueRelKeys(
							cpInstance.getCPInstanceId());

			CPDefinition cpDefinition = cpInstance.getCPDefinition();

			String cpDefinitionName = cpDefinition.getName(languageId);

			JSONArray jsonArray = _jsonHelper.toJSONArray(
				cpDefinitionOptionRelKeysCPDefinitionOptionValueRelKeys);

			int stockQuantity = _commerceInventoryEngine.getStockQuantity(
				cpInstance.getCompanyId(), cpDefinition.getGroupId(),
				cpInstance.getSku());

			String statusDisplayStyle = StringPool.BLANK;

			if (cpInstance.getStatus() == WorkflowConstants.STATUS_APPROVED) {
				statusDisplayStyle = "success";
			}

			String discontinued = "no";

			if (cpInstance.isDiscontinued()) {
				discontinued = "yes";
			}

			skus.add(
				new Sku(
					cpInstance.getCPInstanceId(), cpInstance.getSku(),
					HtmlUtil.escape(
						_getOptions(
							cpInstance.getCPDefinitionId(),
							jsonArray.toString(), locale)),
					HtmlUtil.escape(_formatPrice(cpInstance, locale)),
					cpDefinitionName, stockQuantity,
					new LabelField(
						statusDisplayStyle,
						_language.get(
							httpServletRequest,
							WorkflowConstants.getStatusLabel(
								cpInstance.getStatus()))),
					_language.get(httpServletRequest, discontinued)));
		}

		return skus;
	}

	@Override
	public int getItemsCount(
			FDSKeywords fdsKeywords, HttpServletRequest httpServletRequest)
		throws PortalException {

		String keywords = fdsKeywords.getKeywords();

		long cpDefinitionId = ParamUtil.getLong(
			httpServletRequest, "cpDefinitionId");

		if (Validator.isNotNull(keywords) || (cpDefinitionId == 0)) {
			BaseModelSearchResult<CPInstance> baseModelSearchResult =
				_getBaseModelSearchResult(
					_portal.getCompanyId(httpServletRequest), cpDefinitionId,
					keywords, null);

			return baseModelSearchResult.getLength();
		}

		return _cpInstanceService.getCPDefinitionInstancesCount(
			cpDefinitionId, WorkflowConstants.STATUS_ANY);
	}

	private String _formatPrice(CPInstance cpInstance, Locale locale)
		throws PortalException {

		CommerceCatalog commerceCatalog = cpInstance.getCommerceCatalog();

		CommerceMoney commerceMoney =
			_commerceProductPriceCalculation.getBasePrice(
				cpInstance.getCPInstanceId(),
				_commerceCurrencyLocalService.getCommerceCurrency(
					cpInstance.getCompanyId(),
					commerceCatalog.getCommerceCurrencyCode()));

		return commerceMoney.format(locale);
	}

	private BaseModelSearchResult<CPInstance> _getBaseModelSearchResult(
			long companyId, long cpDefinitionId, String keywords, int start,
			int end, Sort sort)
		throws PortalException {

		return _cpInstanceService.searchCPDefinitionInstances(
			companyId, cpDefinitionId, keywords, WorkflowConstants.STATUS_ANY,
			start, end, sort);
	}

	private BaseModelSearchResult<CPInstance> _getBaseModelSearchResult(
			long companyId, long cpDefinitionId, String keywords, Sort sort)
		throws PortalException {

		return _cpInstanceService.searchCPDefinitionInstances(
			companyId, cpDefinitionId, keywords, WorkflowConstants.STATUS_ANY,
			sort);
	}

	private List<CPInstance> _getCPInstances(
			long companyId, long cpDefinitionId, String keywords, int start,
			int end, Sort sort)
		throws PortalException {

		if (Validator.isNotNull(keywords) || (cpDefinitionId == 0)) {
			BaseModelSearchResult<CPInstance> cpInstanceBaseModelSearchResult =
				_getBaseModelSearchResult(
					companyId, cpDefinitionId, keywords, start, end, sort);

			return cpInstanceBaseModelSearchResult.getBaseModels();
		}

		return _cpInstanceService.getCPDefinitionInstances(
			cpDefinitionId, WorkflowConstants.STATUS_ANY, start, end, null);
	}

	private String _getOptions(long cpDefinitionId, String json, Locale locale)
		throws PortalException {

		List<KeyValuePair> keyValuePairs = _cpInstanceHelper.getKeyValuePairs(
			cpDefinitionId, json, locale);

		StringJoiner stringJoiner = new StringJoiner(
			StringPool.COMMA + StringPool.SPACE);

		for (KeyValuePair keyValuePair : keyValuePairs) {
			stringJoiner.add(keyValuePair.getValue());
		}

		return stringJoiner.toString();
	}

	@Reference
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@Reference
	private CommerceInventoryEngine _commerceInventoryEngine;

	@Reference
	private CommerceProductPriceCalculation _commerceProductPriceCalculation;

	@Reference
	private CPDefinitionOptionRelLocalService
		_cpDefinitionOptionRelLocalService;

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	@Reference
	private CPInstanceService _cpInstanceService;

	@Reference
	private JsonHelper _jsonHelper;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}