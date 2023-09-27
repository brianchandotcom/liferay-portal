/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.account.internal.resource.v1_0;

import com.liferay.account.exception.NoSuchEntryException;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.commerce.exception.DuplicateCommerceShippingOptionAccountEntryRelException;
import com.liferay.commerce.exception.NoSuchShippingMethodException;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.model.CommerceShippingOptionAccountEntryRel;
import com.liferay.commerce.product.exception.NoSuchChannelException;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceShippingMethodLocalService;
import com.liferay.commerce.service.CommerceShippingOptionAccountEntryRelService;
import com.liferay.commerce.shipping.engine.fixed.exception.NoSuchShippingFixedOptionException;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionLocalService;
import com.liferay.headless.commerce.admin.account.dto.v1_0.AccountChannelShippingOption;
import com.liferay.headless.commerce.admin.account.resource.v1_0.AccountChannelShippingOptionResource;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Danny Situ
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/account-channel-shipping-option.properties",
	scope = ServiceScope.PROTOTYPE,
	service = AccountChannelShippingOptionResource.class
)
public class AccountChannelShippingOptionResourceImpl
	extends BaseAccountChannelShippingOptionResourceImpl {

	@Override
	public void deleteAccountChannelShippingOption(Long id) throws Exception {
		_commerceShippingOptionAccountEntryRelService.
			deleteCommerceShippingOptionAccountEntryRel(id);
	}

	@Override
	public Page<AccountChannelShippingOption>
			getAccountByExternalReferenceCodeAccountChannelShippingOptionPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		AccountEntry accountEntry =
			_accountEntryLocalService.fetchAccountEntryByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (accountEntry == null) {
			throw new NoSuchEntryException();
		}

		return _getPage(
			accountEntry.getAccountEntryId(),
			HashMapBuilder.<String, Map<String, String>>put(
				"create",
				_addExternalReferenceCodeAction(
					ActionKeys.UPDATE,
					"postAccountByExternalReferenceCodeAccountChannel" +
						"ShippingOption",
					accountEntry)
			).put(
				"get",
				_addExternalReferenceCodeAction(
					ActionKeys.VIEW,
					"getAccountByExternalReferenceCodeAccountChannel" +
						"ShippingOptionPage",
					accountEntry)
			).build(),
			pagination);
	}

	@Override
	public AccountChannelShippingOption getAccountChannelShippingOption(Long id)
		throws Exception {

		return _toAccountChannelShippingOption(
			_commerceShippingOptionAccountEntryRelService.
				getCommerceShippingOptionAccountEntryRel(id));
	}

	@Override
	public Page<AccountChannelShippingOption>
			getAccountIdAccountChannelShippingOptionPage(
				Long id, Pagination pagination)
		throws Exception {

		AccountEntry accountEntry = _accountEntryLocalService.fetchAccountEntry(
			id);

		if (accountEntry == null) {
			throw new NoSuchEntryException();
		}

		return _getPage(
			accountEntry.getAccountEntryId(),
			HashMapBuilder.<String, Map<String, String>>put(
				"create",
				addAction(
					ActionKeys.UPDATE, accountEntry.getAccountEntryId(),
					"postAccountIdAccountChannelShippingOption",
					_accountEntryModelResourcePermission)
			).put(
				"get",
				addAction(
					ActionKeys.VIEW, accountEntry.getAccountEntryId(),
					"getAccountIdAccountChannelShippingOptionPage",
					_accountEntryModelResourcePermission)
			).build(),
			pagination);
	}

	@Override
	public AccountChannelShippingOption patchAccountChannelShippingOption(
			Long id, AccountChannelShippingOption accountChannelShippingOption)
		throws Exception {

		CommerceShippingOptionAccountEntryRel
			commerceShippingOptionAccountEntryRel =
				_commerceShippingOptionAccountEntryRelService.
					getCommerceShippingOptionAccountEntryRel(id);

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(
				commerceShippingOptionAccountEntryRel.getCommerceChannelId());

		CommerceShippingMethod commerceShippingMethod =
			_commerceShippingMethodLocalService.fetchCommerceShippingMethod(
				commerceChannel.getGroupId(),
				accountChannelShippingOption.getShippingMethodKey());

		if ((commerceShippingMethod == null) ||
			!commerceShippingMethod.isActive()) {

			commerceShippingMethod =
				_commerceShippingMethodLocalService.fetchCommerceShippingMethod(
					GetterUtil.getLong(
						accountChannelShippingOption.getShippingMethodId()));

			if ((commerceShippingMethod == null) ||
				!commerceShippingMethod.isActive()) {

				throw new NoSuchShippingMethodException();
			}

			accountChannelShippingOption.setShippingMethodKey(
				commerceShippingMethod.getEngineKey());
		}

		CommerceShippingFixedOption commerceShippingFixedOption =
			_commerceShippingFixedOptionLocalService.
				fetchCommerceShippingFixedOption(
					contextCompany.getCompanyId(),
					accountChannelShippingOption.getShippingOptionKey());

		if (commerceShippingFixedOption == null) {
			commerceShippingFixedOption =
				_commerceShippingFixedOptionLocalService.
					fetchCommerceShippingFixedOption(
						GetterUtil.getLong(
							accountChannelShippingOption.
								getShippingOptionId()));

			if (commerceShippingFixedOption == null) {
				throw new NoSuchShippingFixedOptionException();
			}

			accountChannelShippingOption.setShippingOptionKey(
				commerceShippingFixedOption.getKey());
		}

		if (commerceShippingMethod.getCommerceShippingMethodId() !=
				commerceShippingFixedOption.getCommerceShippingMethodId()) {

			throw new NoSuchShippingFixedOptionException();
		}

		return _toAccountChannelShippingOption(
			_commerceShippingOptionAccountEntryRelService.
				updateCommerceShippingOptionAccountEntryRel(
					commerceShippingOptionAccountEntryRel.
						getCommerceShippingOptionAccountEntryRelId(),
					accountChannelShippingOption.getShippingMethodKey(),
					accountChannelShippingOption.getShippingOptionKey()));
	}

	@Override
	public AccountChannelShippingOption
			postAccountByExternalReferenceCodeAccountChannelShippingOption(
				String externalReferenceCode,
				AccountChannelShippingOption accountChannelShippingOption)
		throws Exception {

		AccountEntry accountEntry =
			_accountEntryLocalService.fetchAccountEntryByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (accountEntry == null) {
			throw new NoSuchEntryException();
		}

		return postAccountIdAccountChannelShippingOption(
			(Long)accountEntry.getAccountEntryId(),
			accountChannelShippingOption);
	}

	@Override
	public AccountChannelShippingOption
			postAccountIdAccountChannelShippingOption(
				Long id,
				AccountChannelShippingOption accountChannelShippingOption)
		throws Exception {

		AccountEntry accountEntry = _accountEntryLocalService.fetchAccountEntry(
			id);

		if (accountEntry == null) {
			throw new NoSuchEntryException();
		}

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.fetchCommerceChannel(
				_getCommerceChannelId(accountChannelShippingOption));

		if (commerceChannel == null) {
			throw new NoSuchChannelException();
		}

		CommerceShippingMethod commerceShippingMethod =
			_commerceShippingMethodLocalService.fetchCommerceShippingMethod(
				commerceChannel.getGroupId(),
				accountChannelShippingOption.getShippingMethodKey());

		if ((commerceShippingMethod == null) ||
			!commerceShippingMethod.isActive()) {

			commerceShippingMethod =
				_commerceShippingMethodLocalService.fetchCommerceShippingMethod(
					GetterUtil.getLong(
						accountChannelShippingOption.getShippingMethodId()));

			if ((commerceShippingMethod == null) ||
				!commerceShippingMethod.isActive()) {

				throw new NoSuchShippingMethodException();
			}
		}

		CommerceShippingFixedOption commerceShippingFixedOption =
			_commerceShippingFixedOptionLocalService.
				fetchCommerceShippingFixedOption(
					contextCompany.getCompanyId(),
					accountChannelShippingOption.getShippingOptionKey());

		if (commerceShippingFixedOption == null) {
			commerceShippingFixedOption =
				_commerceShippingFixedOptionLocalService.
					fetchCommerceShippingFixedOption(
						GetterUtil.getLong(
							accountChannelShippingOption.
								getShippingOptionId()));

			if (commerceShippingFixedOption == null) {
				throw new NoSuchShippingFixedOptionException();
			}
		}

		if (commerceShippingMethod.getCommerceShippingMethodId() !=
				commerceShippingFixedOption.getCommerceShippingMethodId()) {

			throw new NoSuchShippingFixedOptionException();
		}

		CommerceShippingOptionAccountEntryRel
			commerceShippingOptionAccountEntryRel =
				_commerceShippingOptionAccountEntryRelService.
					fetchCommerceShippingOptionAccountEntryRel(
						accountEntry.getAccountEntryId(),
						commerceChannel.getCommerceChannelId());

		if (commerceShippingOptionAccountEntryRel != null) {
			throw new DuplicateCommerceShippingOptionAccountEntryRelException();
		}

		return _toAccountChannelShippingOption(
			_commerceShippingOptionAccountEntryRelService.
				addCommerceShippingOptionAccountEntryRel(
					accountEntry.getAccountEntryId(),
					commerceChannel.getCommerceChannelId(),
					commerceShippingMethod.getEngineKey(),
					commerceShippingFixedOption.getKey()));
	}

	private Map<String, String> _addExternalReferenceCodeAction(
			String actionKey, String methodName, AccountEntry accountEntry)
		throws Exception {

		Map<String, String> action = addAction(
			actionKey, accountEntry.getAccountEntryId(), methodName,
			_accountEntryModelResourcePermission);

		if (action == null) {
			return action;
		}

		action.put(
			"href",
			StringUtil.replace(
				action.get("href"),
				"by-externalReferenceCode/" +
					String.valueOf(accountEntry.getAccountEntryId()),
				"by-externalReferenceCode/" +
					accountEntry.getExternalReferenceCode()));

		return action;
	}

	private long _getCommerceChannelId(
			AccountChannelShippingOption accountChannelShippingOption)
		throws Exception {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.fetchByExternalReferenceCode(
				GetterUtil.getString(
					accountChannelShippingOption.
						getChannelExternalReferenceCode()),
				contextCompany.getCompanyId());

		if (commerceChannel == null) {
			commerceChannel = _commerceChannelLocalService.fetchCommerceChannel(
				GetterUtil.getLong(
					accountChannelShippingOption.getChannelId()));
		}

		if (commerceChannel == null) {
			throw new NoSuchChannelException();
		}

		return commerceChannel.getCommerceChannelId();
	}

	private Page<AccountChannelShippingOption> _getPage(
			Long accountEntryId, Map<String, Map<String, String>> actions,
			Pagination pagination)
		throws Exception {

		return Page.of(
			actions,
			transform(
				_commerceShippingOptionAccountEntryRelService.
					getCommerceShippingOptionAccountEntryRels(accountEntryId),
				commerceShippingOptionAccountEntryRel ->
					_toAccountChannelShippingOption(
						commerceShippingOptionAccountEntryRel)),
			pagination,
			_commerceShippingOptionAccountEntryRelService.
				getCommerceShippingOptionAccountEntryRelsCount(accountEntryId));
	}

	private AccountChannelShippingOption _toAccountChannelShippingOption(
			CommerceShippingOptionAccountEntryRel
				commerceShippingOptionAccountEntryRel)
		throws Exception {

		return _accountChannelShippingOptionDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				HashMapBuilder.<String, Map<String, String>>put(
					"delete",
					addAction(
						ActionKeys.UPDATE,
						commerceShippingOptionAccountEntryRel.
							getCommerceShippingOptionAccountEntryRelId(),
						"deleteAccountChannelShippingOption",
						_commerceShippingOptionAccountEntryRelModelResourcePermission)
				).put(
					"get",
					addAction(
						ActionKeys.VIEW,
						commerceShippingOptionAccountEntryRel.
							getCommerceShippingOptionAccountEntryRelId(),
						"getAccountChannelShippingOption",
						_commerceShippingOptionAccountEntryRelModelResourcePermission)
				).put(
					"patch",
					addAction(
						ActionKeys.UPDATE,
						commerceShippingOptionAccountEntryRel.
							getCommerceShippingOptionAccountEntryRelId(),
						"patchAccountChannelShippingOption",
						_commerceShippingOptionAccountEntryRelModelResourcePermission)
				).build(),
				_dtoConverterRegistry,
				commerceShippingOptionAccountEntryRel.
					getCommerceShippingOptionAccountEntryRelId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser),
			commerceShippingOptionAccountEntryRel);
	}

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.admin.account.internal.dto.v1_0.converter.AccountChannelShippingOptionDTOConverter)"
	)
	private DTOConverter
		<CommerceShippingOptionAccountEntryRel, AccountChannelShippingOption>
			_accountChannelShippingOptionDTOConverter;

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(model.class.name=com.liferay.account.model.AccountEntry)"
	)
	private volatile ModelResourcePermission<AccountEntry>
		_accountEntryModelResourcePermission;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceShippingFixedOptionLocalService
		_commerceShippingFixedOptionLocalService;

	@Reference
	private CommerceShippingMethodLocalService
		_commerceShippingMethodLocalService;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(model.class.name=com.liferay.commerce.model.CommerceShippingOptionAccountEntryRel)"
	)
	private volatile ModelResourcePermission
		<CommerceShippingOptionAccountEntryRel>
			_commerceShippingOptionAccountEntryRelModelResourcePermission;

	@Reference
	private CommerceShippingOptionAccountEntryRelService
		_commerceShippingOptionAccountEntryRelService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

}