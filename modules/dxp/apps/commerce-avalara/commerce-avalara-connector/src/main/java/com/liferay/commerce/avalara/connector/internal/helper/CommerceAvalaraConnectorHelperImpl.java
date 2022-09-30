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

package com.liferay.commerce.avalara.connector.internal.helper;

import com.liferay.commerce.avalara.connector.CommerceAvalaraConnector;
import com.liferay.commerce.avalara.connector.constants.CommerceAvalaraConstants;
import com.liferay.commerce.avalara.connector.helper.CommerceAvalaraConnectorHelper;
import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.service.CPTaxCategoryLocalService;
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel;
import com.liferay.commerce.tax.engine.fixed.service.CommerceTaxFixedRateAddressRelLocalService;
import com.liferay.commerce.tax.model.CommerceTaxMethod;
import com.liferay.commerce.tax.service.CommerceTaxMethodLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchRegionException;
import com.liferay.portal.kernel.exception.PortalException;
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
@Component(
	enabled = false, immediate = true,
	service = CommerceAvalaraConnectorHelper.class
)
public class CommerceAvalaraConnectorHelperImpl
	implements CommerceAvalaraConnectorHelper {

	@Override
	public void createTaxCategories(long userId) throws Exception {
		List<TaxCodeModel> taxCodeModels =
			_commerceAvalaraConnector.getTaxCodes();

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
	public void removeByAddressEntries(CommerceTaxMethod commerceTaxMethod) {
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
				groupId, CommerceAvalaraConstants.KEY);

		if (commerceTaxMethod == null) {
			return;
		}

		String[] taxRatesByZipCodeLines = StringUtil.splitLines(
			taxRateByZipCode);

		Country country = _countryLocalService.fetchCountryByA2(
			commerceTaxMethod.getCompanyId(), _TWO_LETTERS_ISO_CODE_US);

		if (country == null) {
			country = _addUnitedStates(commerceTaxMethod);
		}

		long cpTaxCategoryId = 0;

		CPTaxCategory tangiblePersonalPropertyCPTaxCategory =
			_cpTaxCategoryLocalService.
				fetchCPTaxCategoryByExternalReferenceCode(
					commerceTaxMethod.getCompanyId(),
					_TANGIBLE_PERSONAL_PROPERTY_AVALARA_TAX_CODE);

		if (tangiblePersonalPropertyCPTaxCategory != null) {
			cpTaxCategoryId =
				tangiblePersonalPropertyCPTaxCategory.getCPTaxCategoryId();
		}

		for (int i = 1; i < taxRatesByZipCodeLines.length; i++) {
			String[] taxRatesByZipCodeLine = StringUtil.split(
				taxRatesByZipCodeLines[i], StringPool.COMMA);

			_upsertByAddressEntry(
				commerceTaxMethod.getUserId(), groupId, cpTaxCategoryId,
				commerceTaxMethod.getCommerceTaxMethodId(), country,
				taxRatesByZipCodeLine[1], taxRatesByZipCodeLine[0],
				Double.valueOf(taxRatesByZipCodeLine[10]));
		}
	}

	private Country _addUnitedStates(CommerceTaxMethod commerceTaxMethod) {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(commerceTaxMethod.getCompanyId());
		serviceContext.setLanguageId(commerceTaxMethod.getDefaultLanguageId());
		serviceContext.setUserId(commerceTaxMethod.getUserId());

		Country country = null;

		try {
			country = _countryLocalService.addCountry(
				_TWO_LETTERS_ISO_CODE_US, _THREE_LETTERS_ISO_CODE_US, true,
				true, "", _NAME_US, String.valueOf(_NUMERIC_ISO_CODE_US),
				_PRIORITY_US, true, false, true, serviceContext);
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return country;
	}

	private void _upsertByAddressEntry(
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

	private static final String _NAME_US = "united-states";

	private static final int _NUMERIC_ISO_CODE_US = 840;

	private static final int _PRIORITY_US = 19;

	private static final String _TANGIBLE_PERSONAL_PROPERTY_AVALARA_TAX_CODE =
		"P0000000";

	private static final String _THREE_LETTERS_ISO_CODE_US = "USA";

	private static final String _TWO_LETTERS_ISO_CODE_US = "US";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceAvalaraConnectorHelperImpl.class);

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