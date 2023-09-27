/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.catalog.internal.resource.v1_0;

import com.liferay.account.exception.NoSuchEntryException;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.context.CommerceContextFactory;
import com.liferay.commerce.product.exception.NoSuchCPInstanceException;
import com.liferay.commerce.product.exception.NoSuchCProductException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.permission.CommerceProductViewPermission;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CPInstanceUnitOfMeasureLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.util.CommerceAccountHelper;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.DDMOption;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Product;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Sku;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.SkuOption;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.converter.SkuDTOConverterContext;
import com.liferay.headless.commerce.delivery.catalog.resource.v1_0.SkuResource;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.util.BigDecimalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldId;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Andrea Sbarra
 * @author Alessio Antonio Rendina
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/sku.properties",
	property = "nested.field.support=true", scope = ServiceScope.PROTOTYPE,
	service = SkuResource.class
)
@CTAware
public class SkuResourceImpl extends BaseSkuResourceImpl {

	@Override
	public Sku getChannelProductSku(
			Long channelId, Long productId, Long skuId, Long accountId)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionLocalService.fetchCPDefinitionByCProductId(productId);

		if (cpDefinition == null) {
			throw new NoSuchCProductException();
		}

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(channelId);

		CommerceContext commerceContext = _getCommerceContext(
			accountId, commerceChannel);

		AccountEntry accountEntry = commerceContext.getAccountEntry();

		_commerceProductViewPermission.check(
			PermissionCheckerFactoryUtil.create(contextUser),
			accountEntry.getAccountEntryId(), commerceChannel.getGroupId(),
			cpDefinition.getCPDefinitionId());

		CPInstance cpInstance = _cpInstanceLocalService.fetchCPInstance(skuId);

		if (cpInstance == null) {
			throw new NoSuchCPInstanceException();
		}

		return _skuDTOConverter.toDTO(
			new SkuDTOConverterContext(
				commerceContext, contextCompany.getCompanyId(), cpDefinition,
				contextAcceptLanguage.getPreferredLocale(), BigDecimal.ONE,
				cpInstance.getCPInstanceId(),
				_getDefaultUnitOfMeasureKey(cpInstance.getCPInstanceId()),
				contextUriInfo, contextUser));
	}

	@NestedField(parentClass = Product.class, value = "skus")
	@Override
	public Page<Sku> getChannelProductSkusPage(
			Long channelId, @NestedFieldId("productId") Long productId,
			Long accountId, Pagination pagination)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionLocalService.fetchCPDefinitionByCProductId(productId);

		if (cpDefinition == null) {
			throw new NoSuchCProductException();
		}

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(channelId);

		int countUserCommerceAccounts =
			_commerceAccountHelper.countUserCommerceAccounts(
				contextUser.getUserId(), commerceChannel.getGroupId());

		if (countUserCommerceAccounts > 1) {
			if (accountId == null) {
				throw new NoSuchEntryException();
			}
		}
		else {
			long[] commerceAccountIds =
				_commerceAccountHelper.getUserCommerceAccountIds(
					contextUser.getUserId(), commerceChannel.getGroupId());

			if (commerceAccountIds.length == 0) {
				AccountEntry accountEntry =
					_accountEntryLocalService.getGuestAccountEntry(
						contextCompany.getCompanyId());

				commerceAccountIds = new long[] {
					accountEntry.getAccountEntryId()
				};
			}

			accountId = commerceAccountIds[0];
		}

		_commerceProductViewPermission.check(
			PermissionThreadLocal.getPermissionChecker(), accountId,
			commerceChannel.getGroupId(), cpDefinition.getCPDefinitionId());

		List<CPInstance> cpInstances =
			_cpInstanceLocalService.getCPDefinitionInstances(
				cpDefinition.getCPDefinitionId(),
				WorkflowConstants.STATUS_APPROVED,
				pagination.getStartPosition(), pagination.getEndPosition(),
				null);

		int totalItems = _cpInstanceLocalService.getCPDefinitionInstancesCount(
			cpDefinition.getCPDefinitionId(),
			WorkflowConstants.STATUS_APPROVED);

		return Page.of(
			_toSKUs(channelId, accountId, cpInstances, cpDefinition),
			pagination, totalItems);
	}

	@Override
	public Sku postChannelProductSku(
		Long channelId, Long productId, Long accountId, BigDecimal quantity,
		DDMOption[] ddmOptions) {

		throw new UnsupportedOperationException();
	}

	@Override
	public Sku postChannelProductSkuBySkuOption(
			Long channelId, Long productId, Long accountId, BigDecimal quantity,
			String skuUnitOfMeasureKey, SkuOption[] skuOptions)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionLocalService.fetchCPDefinitionByCProductId(productId);

		if (cpDefinition == null) {
			throw new NoSuchCProductException();
		}

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(channelId);

		CommerceContext commerceContext = _getCommerceContext(
			accountId, commerceChannel);

		AccountEntry accountEntry = commerceContext.getAccountEntry();

		_commerceProductViewPermission.check(
			PermissionCheckerFactoryUtil.create(contextUser),
			accountEntry.getAccountEntryId(), commerceChannel.getGroupId(),
			cpDefinition.getCPDefinitionId());

		JSONArray skuOptionJSONArray = JSONUtil.toJSONArray(
			skuOptions,
			skuOption -> _jsonFactory.createJSONObject(skuOption.toString()));

		CPInstance cpInstance = _cpInstanceHelper.fetchCPInstance(
			cpDefinition.getCPDefinitionId(), skuOptionJSONArray);

		if (cpInstance == null) {
			throw new NoSuchCPInstanceException();
		}

		if (Validator.isNull(skuUnitOfMeasureKey)) {
			skuUnitOfMeasureKey = _getDefaultUnitOfMeasureKey(
				cpInstance.getCPInstanceId());
		}

		return _skuDTOConverter.toDTO(
			new SkuDTOConverterContext(
				commerceContext, contextCompany.getCompanyId(), cpDefinition,
				contextAcceptLanguage.getPreferredLocale(),
				BigDecimalUtil.get(quantity, BigDecimal.ONE),
				cpInstance.getCPInstanceId(), skuUnitOfMeasureKey,
				contextUriInfo, contextUser));
	}

	private CommerceContext _getCommerceContext(
			Long accountId, CommerceChannel commerceChannel)
		throws Exception {

		int countUserCommerceAccounts =
			_commerceAccountHelper.countUserCommerceAccounts(
				contextUser.getUserId(), commerceChannel.getGroupId());

		if (countUserCommerceAccounts > 1) {
			if (accountId == null) {
				throw new NoSuchEntryException();
			}

			return _commerceContextFactory.create(
				contextCompany.getCompanyId(), commerceChannel.getGroupId(),
				contextUser.getUserId(), 0, accountId);
		}

		long[] commerceAccountIds =
			_commerceAccountHelper.getUserCommerceAccountIds(
				contextUser.getUserId(), commerceChannel.getGroupId());

		if (commerceAccountIds.length == 0) {
			AccountEntry accountEntry =
				_accountEntryLocalService.getGuestAccountEntry(
					contextCompany.getCompanyId());

			commerceAccountIds = new long[] {accountEntry.getAccountEntryId()};
		}

		return _commerceContextFactory.create(
			contextCompany.getCompanyId(), commerceChannel.getGroupId(),
			contextUser.getUserId(), 0, commerceAccountIds[0]);
	}

	private String _getDefaultUnitOfMeasureKey(long cpInstanceId) {
		List<CPInstanceUnitOfMeasure> cpInstanceUnitOfMeasures =
			_cpInstanceUnitOfMeasureLocalService.
				getActiveCPInstanceUnitOfMeasures(cpInstanceId);

		if (cpInstanceUnitOfMeasures.isEmpty()) {
			return StringPool.BLANK;
		}

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure =
			cpInstanceUnitOfMeasures.get(0);

		return cpInstanceUnitOfMeasure.getKey();
	}

	private List<Sku> _toSKUs(
			Long channelId, Long accountId, List<CPInstance> cpInstances,
			CPDefinition cpDefinition)
		throws Exception {

		List<Sku> skus = new ArrayList<>();

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(channelId);

		for (CPInstance cpInstance : cpInstances) {
			skus.add(
				_skuDTOConverter.toDTO(
					new SkuDTOConverterContext(
						_getCommerceContext(accountId, commerceChannel),
						contextCompany.getCompanyId(), cpDefinition,
						contextAcceptLanguage.getPreferredLocale(),
						BigDecimal.ONE, cpInstance.getCPInstanceId(),
						_getDefaultUnitOfMeasureKey(
							cpInstance.getCPInstanceId()),
						contextUriInfo, contextUser)));
		}

		return skus;
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private CommerceAccountHelper _commerceAccountHelper;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceContextFactory _commerceContextFactory;

	@Reference
	private CommerceProductViewPermission _commerceProductViewPermission;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

	@Reference
	private CPInstanceUnitOfMeasureLocalService
		_cpInstanceUnitOfMeasureLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.delivery.catalog.internal.dto.v1_0.converter.SkuDTOConverter)"
	)
	private DTOConverter<CPInstance, Sku> _skuDTOConverter;

}