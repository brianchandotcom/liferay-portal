/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.avalara.connector.internal.engine;

import com.liferay.commerce.avalara.connector.CommerceAvalaraConnector;
import com.liferay.commerce.avalara.connector.constants.CommerceAvalaraConstants;
import com.liferay.commerce.avalara.connector.engine.CommerceAvalaraConnectorEngine;
import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.service.CPTaxCategoryLocalService;
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel;
import com.liferay.commerce.tax.engine.fixed.service.CommerceTaxFixedRateAddressRelLocalService;
import com.liferay.commerce.tax.model.CommerceTaxMethod;
import com.liferay.commerce.tax.service.CommerceTaxMethodLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchRegionException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.RegionLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.avalara.avatax.rest.client.models.TaxCodeModel;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 * @author Katie Nesterovich
 */
@Component(service = CommerceAvalaraConnectorEngine.class)
public class CommerceAvalaraConnectorEngineImpl
	implements CommerceAvalaraConnectorEngine {

	@Override
	public void addTaxCategories(long userId) throws Exception {
		List<TaxCodeModel> taxCodeModels =
			_commerceAvalaraConnector.getTaxCodeModels();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setUserId(userId);

		for (TaxCodeModel taxCodeModel : taxCodeModels) {
			Map<Locale, String> nameMap = Collections.singletonMap(
				LocaleUtil.getSiteDefault(), taxCodeModel.getDescription());

			_cpTaxCategoryLocalService.addCPTaxCategory(
				taxCodeModel.getTaxCode(), nameMap, Collections.emptyMap(),
				serviceContext);
		}
	}

	@Override
	public void deleteByAddressEntries(CommerceTaxMethod commerceTaxMethod) {
		_commerceTaxFixedRateAddressRelLocalService.
			deleteCommerceTaxFixedRateAddressRelsByCommerceTaxMethodId(
				commerceTaxMethod.getCommerceTaxMethodId());
	}

	@Override
	public void updateByAddressEntries(long groupId) throws Exception {
		String taxRateByZipCode =
			_commerceAvalaraConnector.getTaxRateByZipCode();

		if (Validator.isBlank(taxRateByZipCode)) {
			return;
		}

		CommerceTaxMethod commerceTaxMethod =
			_commerceTaxMethodLocalService.fetchCommerceTaxMethod(
				groupId, "avalara");

		if (commerceTaxMethod == null) {
			return;
		}

		String[] taxRatesByZipCodeLines = StringUtil.splitLines(
			taxRateByZipCode);

		Country country = _countryLocalService.fetchCountryByA2(
			commerceTaxMethod.getCompanyId(), "US");

		if (country == null) {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(commerceTaxMethod.getCompanyId());
			serviceContext.setLanguageId(
				commerceTaxMethod.getDefaultLanguageId());
			serviceContext.setUserId(commerceTaxMethod.getUserId());

			country = _countryLocalService.addCountry(
				"US", "USA", true, true, "", "united-states", "840", 19, true,
				false, true, serviceContext);
		}

		long cpTaxCategoryId = 0;

		CPTaxCategory tangiblePersonalPropertyCPTaxCategory =
			_cpTaxCategoryLocalService.
				fetchCPTaxCategoryByExternalReferenceCode(
					CommerceAvalaraConstants.
						CP_TAX_CATEGORY_ERC_TANGIBLE_PERSONAL_PROPERTY,
					commerceTaxMethod.getCompanyId());

		if (tangiblePersonalPropertyCPTaxCategory != null) {
			cpTaxCategoryId =
				tangiblePersonalPropertyCPTaxCategory.getCPTaxCategoryId();
		}

		for (int i = 1; i < taxRatesByZipCodeLines.length; i++) {
			String[] taxRatesByZipCodeLine = StringUtil.split(
				taxRatesByZipCodeLines[i], StringPool.COMMA);

			_addOrUpdateByAddressEntry(
				commerceTaxMethod.getUserId(), groupId, cpTaxCategoryId,
				commerceTaxMethod.getCommerceTaxMethodId(), country,
				taxRatesByZipCodeLine[1], taxRatesByZipCodeLine[0],
				Double.valueOf(taxRatesByZipCodeLine[10]));
		}
	}

	private void _addOrUpdateByAddressEntry(
			long userId, long groupId,
			long tangiblePersonalPropertyCPTaxCategoryId,
			long commerceTaxMethodId, Country country, String regionCode,
			String zip, double rate)
		throws Exception {

		Region region;

		try {
			region = _regionLocalService.getRegion(
				country.getCountryId(), regionCode);
		}
		catch (NoSuchRegionException noSuchRegionException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchRegionException);
			}

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setUserId(userId);

			region = _regionLocalService.addRegion(
				country.getCountryId(), Boolean.FALSE, regionCode, 0,
				regionCode, serviceContext);
		}

		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel =
			_commerceTaxFixedRateAddressRelLocalService.
				fetchCommerceTaxFixedRateAddressRel(
					commerceTaxMethodId, country.getCountryId(),
					region.getRegionId(), zip);

		if (commerceTaxFixedRateAddressRel == null) {
			_commerceTaxFixedRateAddressRelLocalService.
				addCommerceTaxFixedRateAddressRel(
					userId, groupId, commerceTaxMethodId,
					tangiblePersonalPropertyCPTaxCategoryId,
					country.getCountryId(), region.getRegionId(), zip,
					rate * 100);
		}
		else {
			commerceTaxFixedRateAddressRel.setCPTaxCategoryId(
				tangiblePersonalPropertyCPTaxCategoryId);
			commerceTaxFixedRateAddressRel.setRate(rate * 100);

			_commerceTaxFixedRateAddressRelLocalService.
				updateCommerceTaxFixedRateAddressRel(
					commerceTaxFixedRateAddressRel);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceAvalaraConnectorEngineImpl.class);

	@Reference
	private CommerceAvalaraConnector _commerceAvalaraConnector;

	@Reference
	private CommerceTaxFixedRateAddressRelLocalService
		_commerceTaxFixedRateAddressRelLocalService;

	@Reference
	private CommerceTaxMethodLocalService _commerceTaxMethodLocalService;

	@Reference
	private CountryLocalService _countryLocalService;

	@Reference
	private CPTaxCategoryLocalService _cpTaxCategoryLocalService;

	@Reference
	private RegionLocalService _regionLocalService;

}