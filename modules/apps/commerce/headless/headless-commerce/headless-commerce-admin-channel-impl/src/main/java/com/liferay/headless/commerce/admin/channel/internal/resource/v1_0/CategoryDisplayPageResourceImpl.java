/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.channel.internal.resource.v1_0;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.commerce.product.constants.CPDisplayLayoutConstants;
import com.liferay.commerce.product.exception.NoSuchCPDisplayLayoutException;
import com.liferay.commerce.product.model.CPDisplayLayout;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPDisplayLayoutService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.headless.commerce.admin.channel.dto.v1_0.CategoryDisplayPage;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.CategoryDisplayPageResource;
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
	properties = "OSGI-INF/liferay/rest/v1_0/category-display-page.properties",
	scope = ServiceScope.PROTOTYPE, service = CategoryDisplayPageResource.class
)
public class CategoryDisplayPageResourceImpl
	extends BaseCategoryDisplayPageResourceImpl {

	public void deleteCategoryDisplayPage(Long id) throws Exception {
		_cpDisplayLayoutService.deleteCPDisplayLayout(id);
	}

	public CategoryDisplayPage getCategoryDisplayPage(Long id)
		throws Exception {

		CPDisplayLayout cpDisplayLayout =
			_cpDisplayLayoutService.fetchCPDisplayLayout(id);

		if (cpDisplayLayout == null) {
			throw new NoSuchCPDisplayLayoutException();
		}

		return _toCategoryDisplayPage(cpDisplayLayout.getCPDisplayLayoutId());
	}

	public Page<CategoryDisplayPage>
			getChannelByExternalReferenceCodeCategoryDisplayPagesPage(
				String externalReferenceCode, String search,
				Pagination pagination, Sort[] sorts)
		throws Exception {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.
				getCommerceChannelByExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		return getChannelIdCategoryDisplayPagesPage(
			commerceChannel.getCommerceChannelId(), search, pagination, sorts);
	}

	public Page<CategoryDisplayPage> getChannelIdCategoryDisplayPagesPage(
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
					AssetCategory.class.getName(),
					CPDisplayLayoutConstants.TYPE_LAYOUT, search,
					pagination.getStartPosition(), pagination.getEndPosition(),
					sort);

		return Page.of(
			HashMapBuilder.put(
				"post",
				addAction(
					ActionKeys.UPDATE, id, "postChannelIdCategoryDisplayPage",
					commerceChannel.getUserId(),
					CommerceChannel.class.getName(),
					commerceChannel.getGroupId())
			).build(),
			transform(
				cpDisplayLayoutBaseModelSearchResult.getBaseModels(),
				cpDisplayLayout -> _toCategoryDisplayPage(
					cpDisplayLayout.getCPDisplayLayoutId())));
	}

	public CategoryDisplayPage patchCategoryDisplayPage(
			Long id, CategoryDisplayPage categoryDisplayPage)
		throws Exception {

		CPDisplayLayout cpDisplayLayout =
			_cpDisplayLayoutService.fetchCPDisplayLayout(id);

		if (cpDisplayLayout == null) {
			throw new NoSuchCPDisplayLayoutException();
		}

		_cpDisplayLayoutService.updateCPDisplayLayout(
			id,
			GetterUtil.getLong(
				categoryDisplayPage.getCategoryId(),
				cpDisplayLayout.getClassPK()),
			cpDisplayLayout.getLayoutPageTemplateEntryUuid(),
			GetterUtil.getString(
				categoryDisplayPage.getPageUuid(),
				cpDisplayLayout.getLayoutUuid()));

		return _toCategoryDisplayPage(id);
	}

	public CategoryDisplayPage
			postChannelByExternalReferenceCodeCategoryDisplayPage(
				String externalReferenceCode,
				CategoryDisplayPage categoryDisplayPage)
		throws Exception {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.
				getCommerceChannelByExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		return postChannelIdCategoryDisplayPage(
			commerceChannel.getCommerceChannelId(), categoryDisplayPage);
	}

	public CategoryDisplayPage postChannelIdCategoryDisplayPage(
			Long id, CategoryDisplayPage categoryDisplayPage)
		throws Exception {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(id);

		CPDisplayLayout cpDisplayLayout =
			_cpDisplayLayoutService.addCPDisplayLayout(
				commerceChannel.getSiteGroupId(), AssetCategory.class,
				categoryDisplayPage.getCategoryId(), null,
				categoryDisplayPage.getPageUuid());

		return _toCategoryDisplayPage(cpDisplayLayout.getCPDisplayLayoutId());
	}

	private Map<String, Map<String, String>> _getActions(
		CPDisplayLayout cpDisplayLayout) {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			addAction(
				ActionKeys.DELETE, cpDisplayLayout.getCPDisplayLayoutId(),
				"deleteCategoryDisplayPage",
				_cpDisplayLayoutModelResourcePermission)
		).put(
			"get",
			addAction(
				ActionKeys.VIEW, cpDisplayLayout.getCPDisplayLayoutId(),
				"getCategoryDisplayPage",
				_cpDisplayLayoutModelResourcePermission)
		).put(
			"patch",
			addAction(
				ActionKeys.UPDATE, cpDisplayLayout.getCPDisplayLayoutId(),
				"patchCategoryDisplayPage",
				_cpDisplayLayoutModelResourcePermission)
		).build();
	}

	private CategoryDisplayPage _toCategoryDisplayPage(Long cpDisplayLayoutId)
		throws Exception {

		CPDisplayLayout cpDisplayLayout =
			_cpDisplayLayoutService.fetchCPDisplayLayout(cpDisplayLayoutId);

		return _categoryDisplayPageDTOConvertor.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				_getActions(cpDisplayLayout), _dtoConverterRegistry,
				cpDisplayLayoutId, contextAcceptLanguage.getPreferredLocale(),
				contextUriInfo, contextUser));
	}

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.admin.channel.internal.dto.v1_0.converter.CategoryDisplayPageDTOConverter)"
	)
	private DTOConverter<CPDisplayLayout, CategoryDisplayPage>
		_categoryDisplayPageDTOConvertor;

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

}