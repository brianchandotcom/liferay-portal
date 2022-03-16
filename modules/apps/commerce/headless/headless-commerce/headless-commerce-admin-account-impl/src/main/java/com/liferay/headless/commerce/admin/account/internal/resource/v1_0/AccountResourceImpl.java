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

package com.liferay.headless.commerce.admin.account.internal.resource.v1_0;

import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.account.exception.NoSuchAccountException;
import com.liferay.commerce.account.exception.NoSuchAccountGroupException;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.commerce.account.model.CommerceAccountGroupCommerceAccountRel;
import com.liferay.commerce.account.model.CommerceAccountOrganizationRel;
import com.liferay.commerce.account.model.CommerceAccountUserRel;
import com.liferay.commerce.account.service.CommerceAccountGroupCommerceAccountRelService;
import com.liferay.commerce.account.service.CommerceAccountGroupService;
import com.liferay.commerce.account.service.CommerceAccountOrganizationRelService;
import com.liferay.commerce.account.service.CommerceAccountService;
import com.liferay.commerce.account.service.CommerceAccountUserRelService;
import com.liferay.commerce.account.service.persistence.CommerceAccountOrganizationRelPK;
import com.liferay.commerce.account.service.persistence.CommerceAccountUserRelPK;
import com.liferay.commerce.constants.CommerceAddressConstants;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.headless.commerce.admin.account.dto.v1_0.Account;
import com.liferay.headless.commerce.admin.account.dto.v1_0.AccountAddress;
import com.liferay.headless.commerce.admin.account.dto.v1_0.AccountMember;
import com.liferay.headless.commerce.admin.account.dto.v1_0.AccountOrganization;
import com.liferay.headless.commerce.admin.account.internal.dto.v1_0.converter.AccountDTOConverter;
import com.liferay.headless.commerce.admin.account.internal.odata.entity.v1_0.AccountEntityModel;
import com.liferay.headless.commerce.admin.account.internal.util.v1_0.AccountMemberUtil;
import com.liferay.headless.commerce.admin.account.internal.util.v1_0.AccountOrganizationUtil;
import com.liferay.headless.commerce.admin.account.resource.v1_0.AccountResource;
import com.liferay.headless.commerce.core.util.ExpandoUtil;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.RegionLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.io.IOException;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/account.properties",
	scope = ServiceScope.PROTOTYPE, service = AccountResource.class
)
public class AccountResourceImpl
	extends BaseAccountResourceImpl implements EntityModelResource {

	@Override
	public Response deleteAccount(Long id) throws Exception {
		_commerceAccountService.deleteCommerceAccount(id);

		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	@Override
	public Response deleteAccountByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		CommerceAccount commerceAccount =
			_commerceAccountService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceAccount == null) {
			throw new NoSuchAccountException(
				"Unable to find account with external reference code " +
					externalReferenceCode);
		}

		_commerceAccountService.deleteCommerceAccount(
			commerceAccount.getCommerceAccountId());

		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	@Override
	public Response deleteAccountGroupByExternalReferenceCodeAccount(
			String accountExternalReferenceCode, String externalReferenceCode)
		throws Exception {

		CommerceAccountGroup commerceAccountGroup =
			_commerceAccountGroupService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceAccountGroup == null) {
			throw new NoSuchAccountGroupException(
				"Unable to find account group with external reference code " +
					externalReferenceCode);
		}

		CommerceAccount commerceAccount =
			_commerceAccountService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), accountExternalReferenceCode);

		if (commerceAccount == null) {
			throw new NoSuchAccountException(
				"Unable to find Account with external reference code: " +
					accountExternalReferenceCode);
		}

		CommerceAccountGroupCommerceAccountRel
			commerceAccountGroupCommerceAccountRel =
				_commerceAccountGroupCommerceAccountRelService.
					getCommerceAccountGroupCommerceAccountRel(
						commerceAccountGroup.getCommerceAccountGroupId(),
						commerceAccount.getCommerceAccountId());

		_commerceAccountGroupCommerceAccountRelService.
			deleteCommerceAccountGroupCommerceAccountRel(
				commerceAccountGroupCommerceAccountRel.
					getCommerceAccountGroupCommerceAccountRelId());

		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	@Override
	public Account getAccount(Long id) throws Exception {
		return _accountDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				GetterUtil.getLong(id),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Override
	public Account getAccountByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		CommerceAccount commerceAccount =
			_commerceAccountService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceAccount == null) {
			throw new NoSuchAccountException(
				"Unable to find account with external reference code " +
					externalReferenceCode);
		}

		return _accountDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceAccount.getCommerceAccountId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Override
	public Page<Account> getAccountsPage(
			String search, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		Page<Account> page = SearchUtil.search(
			Collections.emptyMap(),
			booleanQuery -> booleanQuery.getPreBooleanFilter(), filter,
			AccountEntry.class.getName(), search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			new UnsafeConsumer() {

				public void accept(Object object) throws Exception {
					SearchContext searchContext = (SearchContext)object;

					searchContext.setCompanyId(contextCompany.getCompanyId());
				}

			},
			sorts,
			document -> _toAccount(
				_commerceAccountService.fetchCommerceAccount(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));

		Collection<Account> items = page.getItems();

		items.removeIf(item -> item == null);

		return Page.of(items);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return _entityModel;
	}

	@Override
	public Response patchAccount(Long id, Account account) throws Exception {
		_updateAccount(id, account);

		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	@Override
	public Response patchAccountByExternalReferenceCode(
			String externalReferenceCode, Account account)
		throws Exception {

		CommerceAccount commerceAccount =
			_commerceAccountService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceAccount == null) {
			throw new NoSuchAccountException(
				"Unable to find account with external reference code " +
					externalReferenceCode);
		}

		_updateAccount(commerceAccount.getCommerceAccountId(), account);

		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	@Override
	public Account postAccount(Account account) throws Exception {
		CommerceAccount commerceAccount =
			_commerceAccountService.addOrUpdateCommerceAccount(
				account.getName(),
				CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID, true, null,
				_getEmailAddress(account, null), account.getTaxId(),
				GetterUtil.get(
					account.getType(),
					CommerceAccountConstants.ACCOUNT_TYPE_PERSONAL),
				GetterUtil.getBoolean(account.getActive(), true),
				account.getExternalReferenceCode(),
				_serviceContextHelper.getServiceContext());

		if (_isValidId(account.getDefaultBillingAccountAddressId())) {
			_commerceAccountService.updateDefaultBillingAddress(
				commerceAccount.getCommerceAccountId(),
				account.getDefaultBillingAccountAddressId());
		}

		if (_isValidId(account.getDefaultShippingAccountAddressId())) {
			_commerceAccountService.updateDefaultShippingAddress(
				commerceAccount.getCommerceAccountId(),
				account.getDefaultShippingAccountAddressId());
		}

		// Expando

		Map<String, ?> customFields = account.getCustomFields();

		if ((customFields != null) && !customFields.isEmpty()) {
			ExpandoUtil.updateExpando(
				contextCompany.getCompanyId(), AccountEntry.class,
				commerceAccount.getPrimaryKey(), customFields);
		}

		// Update nested resources

		_updateNestedResources(
			account, commerceAccount,
			_serviceContextHelper.getServiceContext());

		return _accountDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceAccount.getCommerceAccountId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Override
	public Response postAccountByExternalReferenceCodeLogo(
			String externalReferenceCode, MultipartBody multipartBody)
		throws Exception {

		CommerceAccount commerceAccount =
			_commerceAccountService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceAccount == null) {
			throw new NoSuchAccountException(
				"Unable to find account with external reference code " +
					externalReferenceCode);
		}

		updateAccountLogo(commerceAccount, multipartBody);

		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	@Override
	public Response postAccountGroupByExternalReferenceCodeAccount(
			String externalReferenceCode, Account account)
		throws Exception {

		CommerceAccountGroup commerceAccountGroup =
			_commerceAccountGroupService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceAccountGroup == null) {
			throw new NoSuchAccountGroupException(
				"Unable to find account group with external reference code " +
					externalReferenceCode);
		}

		CommerceAccount commerceAccount = null;

		if (account.getId() != null) {
			commerceAccount = _commerceAccountService.fetchCommerceAccount(
				account.getId());
		}
		else if (account.getExternalReferenceCode() != null) {
			commerceAccount =
				_commerceAccountService.fetchByExternalReferenceCode(
					contextCompany.getCompanyId(),
					account.getExternalReferenceCode());
		}

		if (commerceAccount == null) {
			throw new NoSuchAccountException(
				"Unable to find Account with external reference code: " +
					account.getExternalReferenceCode());
		}

		_commerceAccountGroupCommerceAccountRelService.
			addCommerceAccountGroupCommerceAccountRel(
				commerceAccountGroup.getCommerceAccountGroupId(),
				commerceAccount.getCommerceAccountId(),
				_serviceContextHelper.getServiceContext());

		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	@Override
	public Response postAccountLogo(Long id, MultipartBody multipartBody)
		throws Exception {

		updateAccountLogo(
			_commerceAccountService.getCommerceAccount(id), multipartBody);

		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	public void updateAccountLogo(
			CommerceAccount commerceAccount, MultipartBody multipartBody)
		throws IOException, PortalException {

		_commerceAccountService.updateCommerceAccount(
			commerceAccount.getCommerceAccountId(), commerceAccount.getName(),
			true, multipartBody.getBinaryFileAsBytes("logo"),
			commerceAccount.getEmail(), commerceAccount.getTaxId(),
			commerceAccount.isActive(),
			_serviceContextHelper.getServiceContext(
				commerceAccount.getCommerceAccountGroupId()));
	}

	private String _getEmailAddress(
		Account account, CommerceAccount commerceAccount) {

		String[] emailAddresses = new String[0];

		if (account.getEmailAddresses() != null) {
			emailAddresses = account.getEmailAddresses();
		}

		if (emailAddresses.length > 0) {
			return emailAddresses[0];
		}

		if (commerceAccount == null) {
			return "";
		}

		return commerceAccount.getEmail();
	}

	private long _getRegionId(Country country, AccountAddress accountAddress)
		throws Exception {

		if (Validator.isNull(accountAddress.getRegionISOCode()) ||
			(country == null)) {

			return 0;
		}

		Region region = _regionLocalService.fetchRegion(
			country.getCountryId(), accountAddress.getRegionISOCode());

		if (region == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to find region with ISO code ",
						accountAddress.getRegionISOCode(), " for country ",
						country.getCountryId()));
			}

			return 0;
		}

		return region.getRegionId();
	}

	private boolean _isValidId(Long value) {
		if ((value == null) || (value <= 0)) {
			return false;
		}

		return true;
	}

	private Account _toAccount(CommerceAccount commerceAccount)
		throws Exception {

		if (commerceAccount == null) {
			return null;
		}

		return _accountDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceAccount.getCommerceAccountId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	private CommerceAccount _updateAccount(Long id, Account account)
		throws Exception {

		CommerceAccount commerceAccount =
			_commerceAccountService.getCommerceAccount(id);

		ServiceContext serviceContext = _serviceContextHelper.getServiceContext(
			commerceAccount.getCommerceAccountGroupId());

		commerceAccount = _commerceAccountService.updateCommerceAccount(
			commerceAccount.getCommerceAccountId(), account.getName(), true,
			null, _getEmailAddress(account, commerceAccount),
			GetterUtil.get(account.getTaxId(), commerceAccount.getTaxId()),
			GetterUtil.getBoolean(
				account.getActive(), commerceAccount.isActive()),
			GetterUtil.getLong(
				account.getDefaultBillingAccountAddressId(),
				commerceAccount.getDefaultBillingAddressId()),
			GetterUtil.getLong(
				account.getDefaultShippingAccountAddressId(),
				commerceAccount.getDefaultShippingAddressId()),
			account.getExternalReferenceCode(), serviceContext);

		// Expando

		Map<String, ?> customFields = account.getCustomFields();

		if ((customFields != null) && !customFields.isEmpty()) {
			ExpandoUtil.updateExpando(
				serviceContext.getCompanyId(), AccountEntry.class,
				commerceAccount.getPrimaryKey(), customFields);
		}

		// Update nested resources

		_updateNestedResources(account, commerceAccount, serviceContext);

		return commerceAccount;
	}

	private CommerceAccount _updateNestedResources(
			Account account, CommerceAccount commerceAccount,
			ServiceContext serviceContext)
		throws Exception {

		// Account addresses

		AccountAddress[] accountAddresses = account.getAccountAddresses();

		if (accountAddresses != null) {
			List<CommerceAddress> commerceAddresses =
				_commerceAddressService.getCommerceAddresses(
					AccountEntry.class.getName(),
					commerceAccount.getCommerceAccountId(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null);

			for (CommerceAddress commerceAddress : commerceAddresses) {
				_commerceAddressService.deleteCommerceAddress(
					commerceAddress.getCommerceAddressId());
			}

			for (AccountAddress accountAddress : accountAddresses) {
				Country country = _countryService.fetchCountryByA2(
					commerceAccount.getCompanyId(),
					accountAddress.getCountryISOCode());

				if (country == null) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringBundler.concat(
								"Unable to import account address with ",
								"country ISO code ", account.getName(),
								" and account name ",
								accountAddress.getCountryISOCode()));
					}

					continue;
				}

				CommerceAddress commerceAddress =
					_commerceAddressService.addCommerceAddress(
						GetterUtil.getString(
							accountAddress.getExternalReferenceCode(), null),
						AccountEntry.class.getName(),
						commerceAccount.getCommerceAccountId(),
						accountAddress.getName(),
						accountAddress.getDescription(),
						accountAddress.getStreet1(),
						accountAddress.getStreet2(),
						accountAddress.getStreet3(), accountAddress.getCity(),
						accountAddress.getZip(),
						_getRegionId(country, accountAddress),
						country.getCountryId(), accountAddress.getPhoneNumber(),
						GetterUtil.getInteger(
							accountAddress.getType(),
							CommerceAddressConstants.
								ADDRESS_TYPE_BILLING_AND_SHIPPING),
						serviceContext);

				if (GetterUtil.get(accountAddress.getDefaultBilling(), false)) {
					_commerceAccountService.updateDefaultBillingAddress(
						commerceAccount.getCommerceAccountId(),
						commerceAddress.getCommerceAddressId());
				}

				if (GetterUtil.get(
						accountAddress.getDefaultShipping(), false)) {

					_commerceAccountService.updateDefaultShippingAddress(
						commerceAccount.getCommerceAccountId(),
						commerceAddress.getCommerceAddressId());
				}
			}
		}

		// Account members

		AccountMember[] accountMembers = account.getAccountMembers();

		if (accountMembers != null) {
			for (AccountMember accountMember : accountMembers) {
				User user = AccountMemberUtil.getUser(
					_userLocalService, accountMember,
					contextCompany.getCompanyId());

				CommerceAccountUserRel commerceAccountUserRel =
					_commerceAccountUserRelService.fetchCommerceAccountUserRel(
						new CommerceAccountUserRelPK(
							commerceAccount.getCommerceAccountId(),
							user.getUserId()));

				if (commerceAccountUserRel != null) {
					continue;
				}

				AccountMemberUtil.addCommerceAccountUserRel(
					_commerceAccountUserRelService, accountMember,
					commerceAccount, user, serviceContext);
			}
		}

		// Account organizations

		AccountOrganization[] accountOrganizations =
			account.getAccountOrganizations();

		if (accountOrganizations != null) {
			for (AccountOrganization accountOrganization :
					accountOrganizations) {

				long organizationId = AccountOrganizationUtil.getOrganizationId(
					_organizationLocalService, accountOrganization,
					contextCompany.getCompanyId());

				CommerceAccountOrganizationRel commerceAccountOrganizationRel =
					_commerceAccountOrganizationRelService.
						fetchCommerceAccountOrganizationRel(
							new CommerceAccountOrganizationRelPK(
								commerceAccount.getCommerceAccountId(),
								organizationId));

				if (commerceAccountOrganizationRel != null) {
					continue;
				}

				_commerceAccountOrganizationRelService.
					addCommerceAccountOrganizationRel(
						commerceAccount.getCommerceAccountId(), organizationId,
						serviceContext);
			}
		}

		return commerceAccount;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AccountResourceImpl.class);

	private static final EntityModel _entityModel = new AccountEntityModel();

	@Reference
	private AccountDTOConverter _accountDTOConverter;

	@Reference
	private CommerceAccountGroupCommerceAccountRelService
		_commerceAccountGroupCommerceAccountRelService;

	@Reference
	private CommerceAccountGroupService _commerceAccountGroupService;

	@Reference
	private CommerceAccountOrganizationRelService
		_commerceAccountOrganizationRelService;

	@Reference
	private CommerceAccountService _commerceAccountService;

	@Reference
	private CommerceAccountUserRelService _commerceAccountUserRelService;

	@Reference
	private CommerceAddressService _commerceAddressService;

	@Reference
	private CountryService _countryService;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private RegionLocalService _regionLocalService;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

	@Reference
	private UserLocalService _userLocalService;

}