/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.content.web.internal.frontend.data.set.provider;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.context.CommerceContextFactory;
import com.liferay.commerce.frontend.model.PriceModel;
import com.liferay.commerce.frontend.util.ProductHelper;
import com.liferay.commerce.product.catalog.CPSku;
import com.liferay.commerce.product.content.web.internal.constants.CPContentFDSNames;
import com.liferay.commerce.product.content.web.internal.model.ReplacementSku;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.frontend.data.set.provider.FDSDataProvider;
import com.liferay.frontend.data.set.provider.search.FDSKeywords;
import com.liferay.frontend.data.set.provider.search.FDSPagination;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "fds.data.provider.key=" + CPContentFDSNames.REPLACEMENT_CP_INSTANCES,
	service = FDSDataProvider.class
)
public class ReplacementCPInstanceFDSDataProvider
	implements FDSDataProvider<ReplacementSku> {

	@Override
	public List<ReplacementSku> getItems(
			FDSKeywords fdsKeywords, FDSPagination fdsPagination,
			HttpServletRequest httpServletRequest, Sort sort)
		throws PortalException {

		long commerceAccountId = ParamUtil.getLong(
			httpServletRequest, "commerceAccountId");
		long commerceChannelGroupId = ParamUtil.getLong(
			httpServletRequest, "commerceChannelGroupId");
		long commerceOrderId = ParamUtil.getLong(
			httpServletRequest, "commerceOrderId");
		String cpInstanceUuid = ParamUtil.getString(
			httpServletRequest, "cpInstanceUuid");
		long cProductId = ParamUtil.getLong(httpServletRequest, "cProductId");

		CommerceContext commerceContext = _commerceContextFactory.create(
			_portal.getCompanyId(httpServletRequest), commerceChannelGroupId,
			_portal.getUserId(httpServletRequest), commerceOrderId,
			commerceAccountId);

		Locale locale = _portal.getLocale(httpServletRequest);

		return TransformUtil.transform(
			_getReplacementCPSkus(cpInstanceUuid, cProductId),
			replacementCPSku -> {
				CPInstance cpInstance = _cpInstanceLocalService.getCPInstance(
					replacementCPSku.getCPInstanceId());

				CPDefinition cpDefinition = cpInstance.getCPDefinition();

				return new ReplacementSku(
					cpDefinition.getName(LocaleUtil.toLanguageId(locale)),
					_getPriceModel(
						commerceContext, cpDefinition.getCPDefinitionId(),
						cpInstance.getCPInstanceId(), StringPool.BLANK, locale),
					cpInstance.getCPInstanceId(), cpInstance.getSku());
			});
	}

	@Override
	public int getItemsCount(
			FDSKeywords fdsKeywords, HttpServletRequest httpServletRequest)
		throws PortalException {

		String cpInstanceUuid = ParamUtil.getString(
			httpServletRequest, "cpInstanceUuid");
		long cProductId = ParamUtil.getLong(httpServletRequest, "cProductId");

		List<CPSku> cpSkus = _getReplacementCPSkus(cpInstanceUuid, cProductId);

		return cpSkus.size();
	}

	private PriceModel _getPriceModel(
			CommerceContext commerceContext, long cpDefinitionId,
			long cpInstanceId, String unitOfMeasureKey, Locale locale)
		throws PortalException {

		if (cpInstanceId > 0) {
			return _productHelper.getPriceModel(
				cpInstanceId, StringPool.BLANK, BigDecimal.ONE,
				unitOfMeasureKey, commerceContext, locale);
		}

		return _productHelper.getMinPriceModel(
			cpDefinitionId, commerceContext, locale);
	}

	private List<CPSku> _getReplacementCPSkus(
		String cpInstanceUuid, long cProductId) {

		List<CPSku> cpSkus = new ArrayList<>();

		CPInstance cpInstance = _cpInstanceLocalService.fetchCProductInstance(
			cProductId, cpInstanceUuid);

		CPInstance replacementCPInstance =
			_cpInstanceLocalService.fetchCProductInstance(
				cpInstance.getReplacementCProductId(),
				cpInstance.getReplacementCPInstanceUuid());

		while (replacementCPInstance != null) {
			cpSkus.add(_cpInstanceHelper.toCPSku(replacementCPInstance));

			replacementCPInstance =
				_cpInstanceLocalService.fetchCProductInstance(
					replacementCPInstance.getReplacementCProductId(),
					replacementCPInstance.getReplacementCPInstanceUuid());
		}

		return cpSkus;
	}

	@Reference
	private CommerceContextFactory _commerceContextFactory;

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private ProductHelper _productHelper;

}