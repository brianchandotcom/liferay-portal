/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.channel.internal.resource.v1_0;

import com.liferay.commerce.product.exception.NoSuchCPDefinitionException;
import com.liferay.commerce.product.exception.NoSuchCPDisplayLayoutException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDisplayLayout;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.service.CPDisplayLayoutService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.headless.commerce.admin.channel.dto.v1_0.ProductDisplayPage;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.ProductDisplayPageResource;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.ActionUtil;
import com.liferay.portal.vulcan.util.SearchUtil;

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

	@Override
	public void deleteProductDisplayPage(Long id) throws Exception {
		_cpDisplayLayoutService.deleteCPDisplayLayout(id);
	}

	@Override
	public Page<ProductDisplayPage>
			getChannelByExternalReferenceCodeProductDisplayPagesPage(
				String externalReferenceCode, String search, Filter filter,
				Pagination pagination, Sort[] sorts)
		throws Exception {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.
				getCommerceChannelByExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		return getChannelIdProductDisplayPagesPage(
			commerceChannel.getCommerceChannelId(), search, filter, pagination,
			sorts);
	}

	@Override
	public Page<ProductDisplayPage> getChannelIdProductDisplayPagesPage(
			Long id, String search, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(id);

		return SearchUtil.search(
			HashMapBuilder.put(
				"post",
				addAction(
					ActionKeys.UPDATE, id, "postChannelIdProductDisplayPage",
					contextUser.getUserId(), CommerceChannel.class.getName(),
					commerceChannel.getGroupId())
			).build(),
			booleanQuery -> {
			},
			filter, CPDisplayLayout.class.getName(), search, pagination,
			queryConfig -> {
			},
			searchContext -> {
				searchContext.setCompanyId(contextCompany.getCompanyId());

				if (Validator.isNotNull(search)) {
					searchContext.setKeywords(search);
				}

				searchContext.setAttribute(
					"entryModelClassName", CPDefinition.class.getName());

				searchContext.setAttribute(
					Field.GROUP_ID,
					String.valueOf(commerceChannel.getSiteGroupId()));
			},
			sorts,
			document -> {
				long cpDisplayLayoutId = GetterUtil.getLong(
					document.get(Field.ENTRY_CLASS_PK));

				return _toProductDisplayPage(cpDisplayLayoutId);
			});
	}

	@Override
	public ProductDisplayPage getProductDisplayPage(Long id) throws Exception {
		return _toProductDisplayPage(id);
	}

	@Override
	public ProductDisplayPage patchProductDisplayPage(
			Long id, ProductDisplayPage productDisplayPage)
		throws Exception {

		CPDisplayLayout cpDisplayLayout =
			_cpDisplayLayoutService.fetchCPDisplayLayout(id);

		if (cpDisplayLayout == null) {
			throw new NoSuchCPDisplayLayoutException();
		}

		long classPK = GetterUtil.getLong(productDisplayPage.getProductId());

		if (classPK == 0) {
			CPDefinition cpDefinition =
				_cpDefinitionService.
					fetchCPDefinitionByCProductExternalReferenceCode(
						productDisplayPage.getProductExternalReferenceCode(),
						contextCompany.getCompanyId());

			if (cpDefinition == null) {
				classPK = cpDisplayLayout.getClassPK();
			}
			else {
				classPK = cpDefinition.getCPDefinitionId();
			}
		}

		_cpDisplayLayoutService.updateCPDisplayLayout(
			id, classPK, cpDisplayLayout.getLayoutPageTemplateEntryUuid(),
			GetterUtil.getString(
				productDisplayPage.getPageUuid(),
				cpDisplayLayout.getLayoutUuid()));

		return _toProductDisplayPage(id);
	}

	@Override
	public ProductDisplayPage
			postChannelByExternalReferenceCodeProductDisplayPage(
				String externalReferenceCode,
				ProductDisplayPage productDisplayPage)
		throws Exception {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.
				getCommerceChannelByExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		return postChannelIdProductDisplayPage(
			commerceChannel.getCommerceChannelId(), productDisplayPage);
	}

	@Override
	public ProductDisplayPage postChannelIdProductDisplayPage(
			Long id, ProductDisplayPage productDisplayPage)
		throws Exception {

		long classPK = GetterUtil.getLong(productDisplayPage.getProductId());

		if (classPK == 0) {
			CPDefinition cpDefinition =
				_cpDefinitionService.
					fetchCPDefinitionByCProductExternalReferenceCode(
						productDisplayPage.getProductExternalReferenceCode(),
						contextCompany.getCompanyId());

			if (cpDefinition == null) {
				throw new NoSuchCPDefinitionException();
			}

			classPK = cpDefinition.getCPDefinitionId();
		}

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(id);

		CPDisplayLayout cpDisplayLayout =
			_cpDisplayLayoutService.addCPDisplayLayout(
				commerceChannel.getSiteGroupId(), CPDefinition.class, classPK,
				productDisplayPage.getPageTemplateUuid(),
				productDisplayPage.getPageUuid());

		return _toProductDisplayPage(cpDisplayLayout.getCPDisplayLayoutId());
	}

	private Map<String, Map<String, String>> _getActions(
		CPDisplayLayout cpDisplayLayout) {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			ActionUtil.addAction(
				ActionKeys.UPDATE, getClass(), cpDisplayLayout.getClassPK(),
				"deleteProductDisplayPage",
				_cpDefinitionModelResourcePermission,
				cpDisplayLayout.getCPDisplayLayoutId(), contextUriInfo)
		).put(
			"get",
			ActionUtil.addAction(
				ActionKeys.VIEW, getClass(), cpDisplayLayout.getClassPK(),
				"getProductDisplayPage", _cpDefinitionModelResourcePermission,
				cpDisplayLayout.getCPDisplayLayoutId(), contextUriInfo)
		).put(
			"patch",
			ActionUtil.addAction(
				ActionKeys.UPDATE, getClass(), cpDisplayLayout.getClassPK(),
				"patchProductDisplayPage", _cpDefinitionModelResourcePermission,
				cpDisplayLayout.getCPDisplayLayoutId(), contextUriInfo)
		).build();
	}

	private ProductDisplayPage _toProductDisplayPage(Long cpDisplayLayoutId)
		throws Exception {

		CPDisplayLayout cpDisplayLayout =
			_cpDisplayLayoutService.getCPDisplayLayout(cpDisplayLayoutId);

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
		target = "(model.class.name=com.liferay.commerce.product.model.CPDefinition)"
	)
	private ModelResourcePermission<CPDefinition>
		_cpDefinitionModelResourcePermission;

	@Reference
	private CPDefinitionService _cpDefinitionService;

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