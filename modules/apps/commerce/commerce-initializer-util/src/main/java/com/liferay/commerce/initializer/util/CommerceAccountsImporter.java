/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.initializer.util;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.exception.NoSuchGroupException;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryOrganizationRel;
import com.liferay.account.model.AccountGroup;
import com.liferay.account.model.AccountGroupRel;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.account.service.AccountGroupRelLocalService;
import com.liferay.commerce.exception.NoSuchCountryException;
import com.liferay.commerce.price.list.exception.NoSuchPriceListException;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListAccountRelLocalService;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.service.CommerceAddressLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.RegionLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.File;
import com.liferay.portal.kernel.util.FriendlyURLNormalizer;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.FileNotFoundException;
import java.io.InputStream;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 */
@Component(service = CommerceAccountsImporter.class)
public class CommerceAccountsImporter {

	public void importCommerceAccounts(
			JSONArray jsonArray, ClassLoader classLoader,
			String dependenciesPath, long scopeGroupId, long userId)
		throws Exception {

		User user = _userLocalService.getUser(userId);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(user.getCompanyId());
		serviceContext.setScopeGroupId(scopeGroupId);
		serviceContext.setUserId(userId);

		for (int i = 0; i < jsonArray.length(); i++) {
			_importCommerceAccount(
				jsonArray.getJSONObject(i), classLoader, dependenciesPath,
				serviceContext);
		}
	}

	protected Country getCountry(String twoLetterISOCode)
		throws PortalException {

		DynamicQuery dynamicQuery = _countryLocalService.dynamicQuery();

		Property nameProperty = PropertyFactoryUtil.forName("a2");

		dynamicQuery.add(nameProperty.eq(twoLetterISOCode));

		List<Country> countries = _countryLocalService.dynamicQuery(
			dynamicQuery, 0, 1);

		if (countries.isEmpty()) {
			throw new NoSuchCountryException(
				"No country exists with two-letter ISO " + twoLetterISOCode);
		}

		return countries.get(0);
	}

	private void _importCommerceAccount(
			JSONObject jsonObject, ClassLoader classLoader,
			String dependenciesPath, ServiceContext serviceContext)
		throws Exception {

		String name = jsonObject.getString("name");

		AccountEntry accountEntry =
			_accountEntryLocalService.fetchAccountEntryByExternalReferenceCode(
				_friendlyURLNormalizer.normalize(name),
				serviceContext.getCompanyId());

		if (accountEntry != null) {
			return;
		}

		String accountType = jsonObject.getString("accountType");
		String email = jsonObject.getString("email");
		String taxId = jsonObject.getString("taxId");

		// Add Commerce Account

		accountEntry = _accountEntryLocalService.addAccountEntry(
			serviceContext.getUserId(),
			AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT, name, null, null,
			email, null, taxId, accountType, WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		accountEntry = _accountEntryLocalService.updateExternalReferenceCode(
			accountEntry.getAccountEntryId(),
			_friendlyURLNormalizer.normalize(accountEntry.getName()));

		String twoLetterISOCode = jsonObject.getString("country");

		Country country = getCountry(twoLetterISOCode);

		long regionId = 0;

		String regionCode = jsonObject.getString("region");

		if (!Validator.isBlank(regionCode)) {
			try {
				Region region = _regionLocalService.getRegion(
					country.getCountryId(), regionCode);

				regionId = region.getRegionId();
			}
			catch (PortalException portalException) {
				_log.error(portalException);
			}
		}

		String street1 = jsonObject.getString("street1");
		String city = jsonObject.getString("city");
		String zip = jsonObject.getString("zip");

		// Add Commerce Address

		_commerceAddressLocalService.addCommerceAddress(
			AccountEntry.class.getName(), accountEntry.getAccountEntryId(),
			accountEntry.getName(), StringPool.BLANK, street1, StringPool.BLANK,
			StringPool.BLANK, city, zip, regionId, country.getCountryId(),
			StringPool.BLANK, true, true, serviceContext);

		// Add Company Logo

		String companyLogo = jsonObject.getString("companyLogo");

		if (!Validator.isBlank(companyLogo)) {
			String filePath = dependenciesPath + "images/" + companyLogo;

			try (InputStream inputStream = classLoader.getResourceAsStream(
					filePath)) {

				if (inputStream == null) {
					throw new FileNotFoundException(
						"No file found at " + filePath);
				}

				_accountEntryLocalService.updateAccountEntry(
					accountEntry.getAccountEntryId(),
					accountEntry.getParentAccountEntryId(),
					accountEntry.getName(), accountEntry.getDescription(),
					false, accountEntry.getDomainsArray(),
					accountEntry.getEmailAddress(), _file.getBytes(inputStream),
					accountEntry.getTaxIdNumber(),
					WorkflowConstants.STATUS_APPROVED, serviceContext);
			}
		}

		// Add Related Organization

		String relatedOrganization = jsonObject.getString(
			"relatedOrganization");

		if (!Validator.isBlank(relatedOrganization)) {
			Organization organization =
				_organizationLocalService.fetchOrganization(
					serviceContext.getCompanyId(), relatedOrganization);

			if (organization == null) {
				organization = _organizationLocalService.addOrganization(
					null, serviceContext.getUserId(), 0, name,
					OrganizationConstants.TYPE_ORGANIZATION, 0, 0,
					ListTypeConstants.ORGANIZATION_STATUS_DEFAULT,
					StringPool.BLANK, false, serviceContext);
			}

			AccountEntryOrganizationRel accountEntryOrganizationRel =
				_accountEntryOrganizationRelLocalService.
					fetchAccountEntryOrganizationRel(
						accountEntry.getAccountEntryId(),
						organization.getOrganizationId());

			if (accountEntryOrganizationRel == null) {
				_accountEntryOrganizationRelLocalService.
					addAccountEntryOrganizationRel(
						accountEntry.getAccountEntryId(),
						organization.getOrganizationId());
			}
		}

		// Add Price List Account Rel

		JSONArray priceListsJSONArray = jsonObject.getJSONArray("priceLists");

		if (priceListsJSONArray != null) {
			for (int i = 0; i < priceListsJSONArray.length(); i++) {
				try {
					String externalReferenceCode =
						_friendlyURLNormalizer.normalize(
							priceListsJSONArray.getString(i));

					CommercePriceList commercePriceList =
						_commercePriceListLocalService.
							fetchByExternalReferenceCode(
								externalReferenceCode,
								serviceContext.getCompanyId());

					if (commercePriceList != null) {
						_commercePriceListAccountRelLocalService.
							addCommercePriceListAccountRel(
								serviceContext.getUserId(),
								commercePriceList.getCommercePriceListId(),
								accountEntry.getAccountEntryId(), 0,
								serviceContext);
					}
				}
				catch (NoSuchPriceListException noSuchPriceListException) {
					_log.error(noSuchPriceListException);
				}
			}
		}

		// Add/Find Account Group and Add Rel

		JSONArray accountGroupsJSONArray = jsonObject.getJSONArray(
			"accountGroups");

		if (accountGroupsJSONArray != null) {
			for (int i = 0; i < accountGroupsJSONArray.length(); i++) {
				try {
					String accountGroupName = accountGroupsJSONArray.getString(
						i);

					String externalReferenceCode =
						_friendlyURLNormalizer.normalize(accountGroupName);

					AccountGroup accountGroup =
						_accountGroupLocalService.
							fetchAccountGroupByExternalReferenceCode(
								externalReferenceCode,
								serviceContext.getCompanyId());

					if (accountGroup == null) {
						accountGroup =
							_accountGroupLocalService.addAccountGroup(
								serviceContext.getUserId(), null,
								accountGroupName, serviceContext);

						accountGroup.setExternalReferenceCode(
							externalReferenceCode);
						accountGroup.setDefaultAccountGroup(false);
						accountGroup.setType(
							AccountConstants.ACCOUNT_GROUP_TYPE_GUEST);
						accountGroup.setExpandoBridgeAttributes(serviceContext);

						accountGroup =
							_accountGroupLocalService.updateAccountGroup(
								accountGroup);
					}

					AccountGroupRel accountGroupRel =
						_accountGroupRelLocalService.fetchAccountGroupRel(
							accountGroup.getAccountGroupId(),
							AccountEntry.class.getName(),
							accountEntry.getAccountEntryId());

					if (accountGroupRel == null) {
						_accountGroupRelLocalService.addAccountGroupRel(
							accountGroup.getAccountGroupId(),
							AccountEntry.class.getName(),
							accountEntry.getAccountEntryId());
					}
				}
				catch (NoSuchGroupException noSuchGroupException) {
					_log.error(noSuchGroupException);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceAccountsImporter.class);

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;

	@Reference
	private AccountGroupLocalService _accountGroupLocalService;

	@Reference
	private AccountGroupRelLocalService _accountGroupRelLocalService;

	@Reference
	private CommerceAddressLocalService _commerceAddressLocalService;

	@Reference
	private CommercePriceListAccountRelLocalService
		_commercePriceListAccountRelLocalService;

	@Reference
	private CommercePriceListLocalService _commercePriceListLocalService;

	@Reference
	private CountryLocalService _countryLocalService;

	@Reference
	private File _file;

	@Reference
	private FriendlyURLNormalizer _friendlyURLNormalizer;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private RegionLocalService _regionLocalService;

	@Reference
	private UserLocalService _userLocalService;

}