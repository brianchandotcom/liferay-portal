/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0;

import com.liferay.commerce.product.exception.NoSuchCPConfigurationListException;
import com.liferay.commerce.product.model.CPConfigurationList;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.model.CommerceChannelRel;
import com.liferay.commerce.product.service.CPConfigurationListService;
import com.liferay.commerce.product.service.CommerceChannelRelService;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductConfigurationList;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductConfigurationListChannel;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.ProductConfigurationListChannelResource;
import com.liferay.headless.commerce.core.helper.ServiceContextHelper;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Danny Situ
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/product-configuration-list-channel.properties",
	property = "nested.field.support=true", scope = ServiceScope.PROTOTYPE,
	service = ProductConfigurationListChannelResource.class
)
public class ProductConfigurationListChannelResourceImpl
	extends BaseProductConfigurationListChannelResourceImpl {

	@Override
	public void deleteProductConfigurationListChannel(Long id)
		throws Exception {

		_commerceChannelRelService.deleteCommerceChannelRel(id);
	}

	@Override
	public Page<ProductConfigurationListChannel>
			getProductConfigurationListByExternalReferenceCodeProductConfigurationListChannelsPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		CPConfigurationList cpConfigurationList =
			_cpConfigurationListService.
				fetchCPConfigurationListByExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (cpConfigurationList == null) {
			throw new NoSuchCPConfigurationListException(
				"Unable to find product configuration list channel with " +
					"external reference code " + externalReferenceCode);
		}

		return getProductConfigurationListIdProductConfigurationListChannelsPage(
			cpConfigurationList.getCPConfigurationListId(), null, null,
			pagination, null);
	}

	@NestedField(
		parentClass = ProductConfigurationList.class,
		value = "productConfigurationListChannels"
	)
	@Override
	public Page<ProductConfigurationListChannel>
			getProductConfigurationListIdProductConfigurationListChannelsPage(
				Long id, String search, Filter filter, Pagination pagination,
				Sort[] sorts)
		throws Exception {

		return Page.of(
			_toProductConfigurationListChannels(
				_commerceChannelRelService.getCommerceChannelRels(
					CPConfigurationList.class.getName(), id, search,
					pagination.getStartPosition(),
					pagination.getEndPosition())),
			pagination,
			_commerceChannelRelService.getCommerceChannelRelsCount(
				CPConfigurationList.class.getName(), id, search));
	}

	@Override
	public ProductConfigurationListChannel
			postProductConfigurationListByExternalReferenceCodeProductConfigurationListChannel(
				String externalReferenceCode,
				ProductConfigurationListChannel productConfigurationListChannel)
		throws Exception {

		CPConfigurationList cpConfigurationList =
			_cpConfigurationListService.
				fetchCPConfigurationListByExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (cpConfigurationList == null) {
			throw new NoSuchCPConfigurationListException(
				"Unable to find product configuration list channel with " +
					"external reference code " + externalReferenceCode);
		}

		return postProductConfigurationListIdProductConfigurationListChannel(
			cpConfigurationList.getCPConfigurationListId(),
			productConfigurationListChannel);
	}

	@Override
	public ProductConfigurationListChannel
			postProductConfigurationListIdProductConfigurationListChannel(
				Long id,
				ProductConfigurationListChannel productConfigurationListChannel)
		throws Exception {

		CommerceChannelRel commerceChannelRel = _addCommerceChannelRel(
			_cpConfigurationListService.getCPConfigurationList(id),
			productConfigurationListChannel);

		return _toProductConfigurationListChannel(
			commerceChannelRel.getCommerceChannelRelId());
	}

	private CommerceChannelRel _addCommerceChannelRel(
			CPConfigurationList cpConfigurationList,
			ProductConfigurationListChannel productConfigurationListChannel)
		throws Exception {

		CommerceChannel commerceChannel =
			_commerceChannelService.fetchCommerceChannelByExternalReferenceCode(
				GetterUtil.getString(
					productConfigurationListChannel.
						getChannelExternalReferenceCode()),
				cpConfigurationList.getCompanyId());

		if (commerceChannel == null) {
			commerceChannel = _commerceChannelService.getCommerceChannel(
				productConfigurationListChannel.getChannelId());
		}

		return _commerceChannelRelService.addCommerceChannelRel(
			CPConfigurationList.class.getName(),
			cpConfigurationList.getCPConfigurationListId(),
			commerceChannel.getCommerceChannelId(),
			_serviceContextHelper.getServiceContext());
	}

	private Map<String, Map<String, String>> _getActions(
		long commerceChannelRelId) {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			addAction(
				"UPDATE", commerceChannelRelId,
				"deleteProductConfigurationListChannel",
				_commerceChannelRelModelResourcePermission)
		).build();
	}

	private ProductConfigurationListChannel _toProductConfigurationListChannel(
			Long commerceChannelRelId)
		throws Exception {

		return _productConfigurationListChannelDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				_getActions(commerceChannelRelId), _dtoConverterRegistry,
				commerceChannelRelId,
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	private List<ProductConfigurationListChannel>
		_toProductConfigurationListChannels(
			List<CommerceChannelRel> commerceChannelRels) {

		return transform(
			commerceChannelRels,
			commerceChannelRel -> _toProductConfigurationListChannel(
				commerceChannelRel.getCommerceChannelRelId()));
	}

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
	private CPConfigurationListService _cpConfigurationListService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter.ProductConfigurationListChannelDTOConverter)"
	)
	private DTOConverter<CommerceChannelRel, ProductConfigurationListChannel>
		_productConfigurationListChannelDTOConverter;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}