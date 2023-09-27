/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.pricing.internal.dto.v2_0.converter;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListService;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CommerceCatalogService;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceList;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.Status;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "dto.class.name=com.liferay.commerce.price.list.model.CommercePriceList",
	service = DTOConverter.class
)
public class PriceListDTOConverter
	implements DTOConverter<CommercePriceList, PriceList> {

	@Override
	public String getContentType() {
		return PriceList.class.getSimpleName();
	}

	@Override
	public PriceList toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommercePriceList commercePriceList =
			_commercePriceListService.getCommercePriceList(
				(Long)dtoConverterContext.getId());

		CommerceCurrency commerceCurrency =
			commercePriceList.getCommerceCurrency();

		String priceListStatusLabel = WorkflowConstants.getStatusLabel(
			commercePriceList.getStatus());

		String priceListStatusLabelI18n = _language.get(
			LanguageResources.getResourceBundle(
				dtoConverterContext.getLocale()),
			WorkflowConstants.getStatusLabel(commercePriceList.getStatus()));

		ExpandoBridge expandoBridge = commercePriceList.getExpandoBridge();

		return new PriceList() {
			{
				actions = dtoConverterContext.getActions();
				active = !commercePriceList.isInactive();
				author = commercePriceList.getUserName();
				catalogBasePriceList =
					commercePriceList.isCatalogBasePriceList();
				catalogId = _getCatalogId(commercePriceList);
				catalogName = _getCatalogName(commercePriceList);
				createDate = commercePriceList.getCreateDate();
				currencyCode = commerceCurrency.getCode();
				customFields = expandoBridge.getAttributes();
				displayDate = commercePriceList.getDisplayDate();
				expirationDate = commercePriceList.getExpirationDate();
				externalReferenceCode =
					commercePriceList.getExternalReferenceCode();
				id = commercePriceList.getCommercePriceListId();
				name = commercePriceList.getName();
				netPrice = commercePriceList.isNetPrice();
				parentPriceListId =
					commercePriceList.getParentCommercePriceListId();
				priority = commercePriceList.getPriority();
				type = Type.create(commercePriceList.getType());
				workflowStatusInfo = _toStatus(
					commercePriceList.getStatus(), priceListStatusLabel,
					priceListStatusLabelI18n);
			}
		};
	}

	private long _getCatalogId(CommercePriceList commercePriceList)
		throws Exception {

		CommerceCatalog commerceCatalog =
			_commerceCatalogService.fetchCommerceCatalogByGroupId(
				commercePriceList.getGroupId());

		if (commerceCatalog == null) {
			return 0L;
		}

		return commerceCatalog.getCommerceCatalogId();
	}

	private String _getCatalogName(CommercePriceList commercePriceList)
		throws Exception {

		CommerceCatalog commerceCatalog =
			_commerceCatalogService.fetchCommerceCatalogByGroupId(
				commercePriceList.getGroupId());

		if (commerceCatalog == null) {
			return StringPool.BLANK;
		}

		return commerceCatalog.getName();
	}

	private Status _toStatus(
		int statusCode, String priceListStatusLabel,
		String priceListStatusLabelI18n) {

		return new Status() {
			{
				code = statusCode;
				label = priceListStatusLabel;
				label_i18n = priceListStatusLabelI18n;
			}
		};
	}

	@Reference
	private CommerceCatalogService _commerceCatalogService;

	@Reference
	private CommercePriceListService _commercePriceListService;

	@Reference
	private Language _language;

}