/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.channel.internal.resource.v1_0;

import com.liferay.commerce.product.exception.NoSuchCPDisplayLayoutException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDisplayLayout;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPDisplayLayoutService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.headless.commerce.admin.channel.dto.v1_0.ProductDisplayPage;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.ProductDisplayPageResource;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Danny Situ
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/product-display-page.properties",
	scope = ServiceScope.PROTOTYPE, service = ProductDisplayPageResource.class
)
public class ProductDisplayPageResourceImpl
	extends BaseProductDisplayPageResourceImpl {

	public void deleteProductDisplayPage(Long id) throws Exception {
		_cpDisplayLayoutService.deleteCPDisplayLayout(id);
	}

	public Page<ProductDisplayPage>
			getChannelByExternalReferenceCodeProductDisplayPagesPage(
				String externalReferenceCode, String search,
				Pagination pagination, Sort[] sorts)
		throws Exception {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.
				getCommerceChannelByExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		return getChannelIdProductDisplayPagesPage(
			commerceChannel.getCommerceChannelId(), search, pagination, sorts);
	}

	public Page<ProductDisplayPage> getChannelIdProductDisplayPagesPage(
			Long id, String search, Pagination pagination, Sort[] sorts)
		throws Exception {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(id);

		Sort sort = null;

		if (sorts != null) {
			sort = sorts[0];
		}

		BaseModelSearchResult<CPDisplayLayout>
			cpDisplayLayoutBaseModelSearchResult =
				_cpDisplayLayoutService.searchCPDisplayLayout(
					commerceChannel.getCompanyId(),
					commerceChannel.getSiteGroupId(),
					CPDefinition.class.getName(), null, search,
					pagination.getStartPosition(), pagination.getEndPosition(),
					sort);

		return Page.of(
			HashMapBuilder.put(
				"post",
				addAction(
					ActionKeys.UPDATE, id, "postChannelIdProductDisplayPage",
					commerceChannel.getUserId(),
					CommerceChannel.class.getName(),
					commerceChannel.getGroupId())
			).build(),
			transform(
				cpDisplayLayoutBaseModelSearchResult.getBaseModels(),
				cpDisplayLayout -> _toProductDisplayPage(
					cpDisplayLayout.getCPDisplayLayoutId())));
	}

	public ProductDisplayPage getProductDisplayPage(Long id) throws Exception {
		CPDisplayLayout cpDisplayLayout =
			_cpDisplayLayoutService.fetchCPDisplayLayout(id);

		if (cpDisplayLayout == null) {
			throw new NoSuchCPDisplayLayoutException();
		}

		return _toProductDisplayPage(cpDisplayLayout.getCPDisplayLayoutId());
	}

	public ProductDisplayPage patchProductDisplayPage(
			Long id, ProductDisplayPage productDisplayPage)
		throws Exception {

		CPDisplayLayout cpDisplayLayout =
			_cpDisplayLayoutService.fetchCPDisplayLayout(id);

		if (cpDisplayLayout == null) {
			throw new NoSuchCPDisplayLayoutException();
		}

		_cpDisplayLayoutService.updateCPDisplayLayout(
			id,
			GetterUtil.getLong(
				productDisplayPage.getProductId(),
				cpDisplayLayout.getClassPK()),
			cpDisplayLayout.getLayoutPageTemplateEntryUuid(),
			GetterUtil.getString(
				productDisplayPage.getPageUuid(),
				cpDisplayLayout.getLayoutUuid()));

		return _toProductDisplayPage(id);
	}

	public ProductDisplayPage
			postChannelByExternalReferenceCodeProductDisplayPage(
				String externalReferenceCode,
				ProductDisplayPage productDisplayPage)
		throws Exception {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.
				getCommerceChannelByExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		return postChannelProductDisplayPage(
			commerceChannel.getCommerceChannelId(), productDisplayPage);
	}

	public ProductDisplayPage postChannelProductDisplayPage(
			Long id, ProductDisplayPage productDisplayPage)
		throws Exception {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(id);

		CPDisplayLayout cpDisplayLayout =
			_cpDisplayLayoutService.addCPDisplayLayout(
				commerceChannel.getSiteGroupId(), CPDefinition.class,
				productDisplayPage.getProductId(),
				productDisplayPage.getPageTemplateUuid(),
				productDisplayPage.getPageUuid());

		return _toProductDisplayPage(cpDisplayLayout.getCPDisplayLayoutId());
	}

	private Map<String, Map<String, String>> _getActions(
		CPDisplayLayout cpDisplayLayout) {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			addAction(
				ActionKeys.DELETE, cpDisplayLayout.getCPDisplayLayoutId(),
				"deleteProductDisplayPage",
				_cpDisplayLayoutModelResourcePermission)
		).put(
			"get",
			addAction(
				ActionKeys.VIEW, cpDisplayLayout.getCPDisplayLayoutId(),
				"getProductDisplayPage",
				_cpDisplayLayoutModelResourcePermission)
		).put(
			"patch",
			addAction(
				ActionKeys.UPDATE, cpDisplayLayout.getCPDisplayLayoutId(),
				"patchProductDisplayPage",
				_cpDisplayLayoutModelResourcePermission)
		).build();
	}

	private ProductDisplayPage _toProductDisplayPage(Long cpDisplayLayoutId)
		throws Exception {

		CPDisplayLayout cpDisplayLayout =
			_cpDisplayLayoutService.fetchCPDisplayLayout(cpDisplayLayoutId);

		return _productDisplayPageDTOConvertor.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				_getActions(cpDisplayLayout), _dtoConverterRegistry,
				cpDisplayLayoutId, contextAcceptLanguage.getPreferredLocale(),
				contextUriInfo, contextUser));
	}

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPDisplayLayout)"
	)
	private ModelResourcePermission<CPDisplayLayout>
		_cpDisplayLayoutModelResourcePermission;

	@Reference
	private CPDisplayLayoutService _cpDisplayLayoutService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.admin.channel.internal.dto.v1_0.converter.ProductDisplayPageDTOConverter)"
	)
	private DTOConverter<CPDisplayLayout, ProductDisplayPage>
		_productDisplayPageDTOConvertor;

}