/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.channel.internal.resource.v1_0;

import com.liferay.commerce.exception.NoSuchAddressException;
import com.liferay.commerce.product.exception.NoSuchChannelException;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.model.CommerceChannelRel;
import com.liferay.commerce.product.service.CommerceChannelRelService;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.headless.commerce.admin.channel.dto.v1_0.AccountAddress;
import com.liferay.headless.commerce.admin.channel.dto.v1_0.AccountAddressChannel;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.AccountAddressChannelResource;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Danny Situ
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/account-address-channel.properties",
	property = "nested.field.support=true", scope = ServiceScope.PROTOTYPE,
	service = AccountAddressChannelResource.class
)
public class AccountAddressChannelResourceImpl
	extends BaseAccountAddressChannelResourceImpl {

	@Override
	public void deleteAccountAddressChannel(Long accountAddressChannelId)
		throws Exception {

		_commerceChannelRelService.deleteCommerceChannelRel(
			accountAddressChannelId);
	}

	@Override
	public Page<AccountAddressChannel>
			getAccountAddressByExternalReferenceCodeAccountAddressChannelsPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		Address address =
			_addressLocalService.fetchAddressByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (address == null) {
			throw new NoSuchAddressException(
				"Unable to find address with external reference code " +
					externalReferenceCode);
		}

		List<CommerceChannelRel> commerceChannelRels =
			_commerceChannelRelService.getCommerceChannelRels(
				Address.class.getName(), address.getAddressId(), null,
				pagination.getStartPosition(), pagination.getEndPosition());

		int totalCount = _commerceChannelRelService.getCommerceChannelRelsCount(
			Address.class.getName(), address.getAddressId());

		return Page.of(
			_toAccountAddressChannels(commerceChannelRels), pagination,
			totalCount);
	}

	@NestedField(
		parentClass = AccountAddress.class, value = "accountAddressChannels"
	)
	@Override
	public Page<AccountAddressChannel>
			getAccountAddressIdAccountAddressChannelsPage(
				Long addressId, String search, Filter filter,
				Pagination pagination, Sort[] sorts)
		throws Exception {

		Address address = _addressLocalService.fetchAddress(addressId);

		if (address == null) {
			return Page.of(Collections.emptyList());
		}

		List<CommerceChannelRel> commerceChannelRel =
			_commerceChannelRelService.getCommerceChannelRels(
				Address.class.getName(), addressId, search,
				pagination.getStartPosition(), pagination.getEndPosition());

		int totalCount = _commerceChannelRelService.getCommerceChannelRelsCount(
			Address.class.getName(), addressId, search);

		return Page.of(
			_toAccountAddressChannels(commerceChannelRel), pagination,
			totalCount);
	}

	@Override
	public AccountAddressChannel
			postAccountAddressByExternalReferenceCodeAccountAddressChannel(
				String externalReferenceCode,
				AccountAddressChannel accountAddressChannel)
		throws Exception {

		Address address =
			_addressLocalService.fetchAddressByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (address == null) {
			throw new NoSuchAddressException(
				"Unable to find address with external reference code " +
					externalReferenceCode);
		}

		CommerceChannelRel commerceChannelRel = _addAccountAddressChannelRel(
			address.getAddressId(), accountAddressChannel);

		return _toAccountAddressChannel(
			commerceChannelRel.getCommerceChannelRelId());
	}

	@Override
	public AccountAddressChannel postAccountAddressIdAccountAddressChannel(
			Long addressId, AccountAddressChannel accountAddressChannel)
		throws Exception {

		CommerceChannelRel commerceChannelRel = _addAccountAddressChannelRel(
			addressId, accountAddressChannel);

		return _toAccountAddressChannel(
			commerceChannelRel.getCommerceChannelRelId());
	}

	private CommerceChannelRel _addAccountAddressChannelRel(
			long addressId, AccountAddressChannel accountAddressChannel)
		throws Exception {

		CommerceChannel commerceChannel = null;

		ServiceContext serviceContext =
			_serviceContextHelper.getServiceContext();

		if (Validator.isNull(
				accountAddressChannel.
					getAddressChannelExternalReferenceCode())) {

			commerceChannel = _commerceChannelService.fetchCommerceChannel(
				accountAddressChannel.getAddressChannelId());

			if (commerceChannel == null) {
				throw new NoSuchChannelException(
					"Unable to find channel with commerce channel id " +
						accountAddressChannel.getAddressChannelId());
			}
		}
		else {
			String externalReferenceCode =
				accountAddressChannel.getAddressChannelExternalReferenceCode();

			commerceChannel =
				_commerceChannelService.
					fetchCommerceChannelByExternalReferenceCode(
						externalReferenceCode, serviceContext.getCompanyId());

			if (commerceChannel == null) {
				throw new NoSuchChannelException(
					"Unable to find channel with external reference code " +
						externalReferenceCode);
			}
		}

		return _commerceChannelRelService.addCommerceChannelRel(
			Address.class.getName(), addressId,
			commerceChannel.getCommerceChannelId(), serviceContext);
	}

	private Map<String, Map<String, String>> _getActions(
		CommerceChannelRel commerceChannelRel) {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			addAction(
				"UPDATE", commerceChannelRel.getCommerceChannelRelId(),
				"deleteAccountAddressChannel",
				_commerceChannelRelModelResourcePermission)
		).build();
	}

	private AccountAddressChannel _toAccountAddressChannel(
			Long commerceChannelRelId)
		throws Exception {

		CommerceChannelRel commerceChannelRel =
			_commerceChannelRelService.getCommerceChannelRel(
				commerceChannelRelId);

		return _accountAddressChannelDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				_getActions(commerceChannelRel), _dtoConverterRegistry,
				commerceChannelRelId,
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	private List<AccountAddressChannel> _toAccountAddressChannels(
			List<CommerceChannelRel> commerceChannelRels)
		throws Exception {

		List<AccountAddressChannel> accountAddressChannels = new ArrayList<>();

		for (CommerceChannelRel commerceChannelRel : commerceChannelRels) {
			accountAddressChannels.add(
				_toAccountAddressChannel(
					commerceChannelRel.getCommerceChannelRelId()));
		}

		return accountAddressChannels;
	}

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.admin.channel.internal.dto.v1_0.converter.AccountAddressChannelDTOConverter)"
	)
	private DTOConverter<CommerceChannelRel, AccountAddressChannel>
		_accountAddressChannelDTOConverter;

	@Reference
	private AddressLocalService _addressLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CommerceChannelRel)"
	)
	private ModelResourcePermission<CommerceChannelRel>
		_commerceChannelRelModelResourcePermission;

	@Reference
	private CommerceChannelRelService _commerceChannelRelService;

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}