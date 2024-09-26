/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.channel.internal.resource.v1_0;

import com.liferay.account.exception.AccountEntryTypeException;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.commerce.product.constants.CommerceChannelAccountEntryRelConstants;
import com.liferay.commerce.product.exception.NoSuchChannelException;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.model.CommerceChannelAccountEntryRel;
import com.liferay.commerce.product.service.CommerceChannelAccountEntryRelService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.headless.commerce.admin.channel.dto.v1_0.ChannelAccount;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.ChannelAccountResource;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
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
	properties = "OSGI-INF/liferay/rest/v1_0/channel-account.properties",
	property = "nested.field.support=true", scope = ServiceScope.PROTOTYPE,
	service = ChannelAccountResource.class
)
public class ChannelAccountResourceImpl extends BaseChannelAccountResourceImpl {

	@Override
	public void deleteChannelAccount(Long channelAccountId) throws Exception {
		_commerceChannelAccountEntryRelService.
			deleteCommerceChannelAccountEntryRel(channelAccountId);
	}

	@Override
	public Page<ChannelAccount>
			getChannelByExternalReferenceCodeChannelAccountsPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.
				fetchCommerceChannelByExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (commerceChannel == null) {
			throw new NoSuchChannelException(
				"Unable to find channel with external reference code " +
					externalReferenceCode);
		}

		return Page.of(
			_toChannelAccounts(
				_commerceChannelAccountEntryRelService.
					getCommerceChannelAccountEntryRels(
						commerceChannel.getCommerceChannelId(), null,
						CommerceChannelAccountEntryRelConstants.
							TYPE_ELIGIBILITY,
						pagination.getStartPosition(),
						pagination.getEndPosition())),
			pagination,
			_commerceChannelAccountEntryRelService.
				getCommerceChannelAccountEntryRelsCount(
					commerceChannel.getCommerceChannelId(), null,
					CommerceChannelAccountEntryRelConstants.TYPE_ELIGIBILITY));
	}

	@NestedField(parentClass = ChannelAccount.class, value = "channelAccounts")
	@Override
	public Page<ChannelAccount> getChannelIdChannelAccountsPage(
			Long commerceChannelId, String search, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.fetchCommerceChannel(
				commerceChannelId);

		if (commerceChannel == null) {
			return Page.of(Collections.emptyList());
		}

		return Page.of(
			_toChannelAccounts(
				_commerceChannelAccountEntryRelService.
					getCommerceChannelAccountEntryRels(
						commerceChannelId, search,
						CommerceChannelAccountEntryRelConstants.
							TYPE_ELIGIBILITY,
						pagination.getStartPosition(),
						pagination.getEndPosition())),
			pagination,
			_commerceChannelAccountEntryRelService.
				getCommerceChannelAccountEntryRelsCount(
					commerceChannelId, search,
					CommerceChannelAccountEntryRelConstants.TYPE_ELIGIBILITY));
	}

	@Override
	public ChannelAccount postChannelByExternalReferenceCodeChannelAccount(
			String externalReferenceCode, ChannelAccount channelAccount)
		throws Exception {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.
				fetchCommerceChannelByExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (commerceChannel == null) {
			throw new NoSuchChannelException(
				"Unable to find channel with external reference code " +
					externalReferenceCode);
		}

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_addChannelAccountRel(
				commerceChannel.getCommerceChannelId(), channelAccount);

		return _toChannelAccount(
			commerceChannelAccountEntryRel.
				getCommerceChannelAccountEntryRelId());
	}

	@Override
	public ChannelAccount postChannelIdChannelAccount(
			Long commerceChannelId, ChannelAccount channelAccount)
		throws Exception {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_addChannelAccountRel(commerceChannelId, channelAccount);

		return _toChannelAccount(
			commerceChannelAccountEntryRel.
				getCommerceChannelAccountEntryRelId());
	}

	private CommerceChannelAccountEntryRel _addChannelAccountRel(
			long commerceChannelId, ChannelAccount channelAccount)
		throws Exception {

		AccountEntry accountEntry = _accountEntryLocalService.getAccountEntry(
			channelAccount.getAccountId());

		if (accountEntry.isGuestAccount()) {
			throw new AccountEntryTypeException();
		}

		CommerceChannel commerceChannel = null;

		if (Validator.isNull(
				channelAccount.getChannelExternalReferenceCode())) {

			commerceChannel = _commerceChannelService.fetchCommerceChannel(
				channelAccount.getChannelId());

			if (commerceChannel == null) {
				throw new NoSuchChannelException(
					"Unable to find channel with channel id " +
						channelAccount.getChannelId());
			}
		}
		else {
			String externalReferenceCode =
				channelAccount.getChannelExternalReferenceCode();

			ServiceContext serviceContext =
				_serviceContextHelper.getServiceContext();

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

		return _commerceChannelAccountEntryRelService.
			addCommerceChannelAccountEntryRel(
				channelAccount.getAccountId(), null, 0, commerceChannelId,
				false, 0,
				CommerceChannelAccountEntryRelConstants.TYPE_ELIGIBILITY);
	}

	private Map<String, Map<String, String>> _getActions(
		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel) {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			addAction(
				"UPDATE",
				commerceChannelAccountEntryRel.
					getCommerceChannelAccountEntryRelId(),
				"deleteChannelAccount",
				_commerceChannelAccountEntryRelModelResourcePermission)
		).build();
	}

	private ChannelAccount _toChannelAccount(
			Long commerceChannelAccountEntryRelId)
		throws Exception {

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_commerceChannelAccountEntryRelService.
				getCommerceChannelAccountEntryRel(
					commerceChannelAccountEntryRelId);

		return _channelAccountDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				_getActions(commerceChannelAccountEntryRel),
				_dtoConverterRegistry, commerceChannelAccountEntryRelId,
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	private List<ChannelAccount> _toChannelAccounts(
			List<CommerceChannelAccountEntryRel>
				commerceChannelAccountEntryRels)
		throws Exception {

		List<ChannelAccount> channelAccounts = new ArrayList<>();

		for (CommerceChannelAccountEntryRel commerceChannelAccountEntryRel :
				commerceChannelAccountEntryRels) {

			channelAccounts.add(
				_toChannelAccount(
					commerceChannelAccountEntryRel.
						getCommerceChannelAccountEntryRelId()));
		}

		return channelAccounts;
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.admin.channel.internal.dto.v1_0.converter.ChannelAccountDTOConverter)"
	)
	private DTOConverter<CommerceChannelAccountEntryRel, ChannelAccount>
		_channelAccountDTOConverter;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CommerceChannelAccountEntryRel)"
	)
	private ModelResourcePermission<CommerceChannelAccountEntryRel>
		_commerceChannelAccountEntryRelModelResourcePermission;

	@Reference
	private CommerceChannelAccountEntryRelService
		_commerceChannelAccountEntryRelService;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}