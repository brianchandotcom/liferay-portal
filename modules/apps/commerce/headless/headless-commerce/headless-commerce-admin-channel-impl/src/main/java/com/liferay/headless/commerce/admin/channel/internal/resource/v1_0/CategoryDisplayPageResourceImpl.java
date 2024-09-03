/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.channel.internal.resource.v1_0;

import com.liferay.account.exception.NoSuchGroupException;
import com.liferay.asset.kernel.exception.NoSuchCategoryException;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.commerce.product.model.CPDisplayLayout;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPDisplayLayoutService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.headless.commerce.admin.channel.dto.v1_0.CategoryDisplayPage;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.CategoryDisplayPageResource;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupService;
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
	properties = "OSGI-INF/liferay/rest/v1_0/category-display-page.properties",
	scope = ServiceScope.PROTOTYPE, service = CategoryDisplayPageResource.class
)
public class CategoryDisplayPageResourceImpl
	extends BaseCategoryDisplayPageResourceImpl {

	@Override
	public void deleteCategoryDisplayPage(Long id) throws Exception {
		_cpDisplayLayoutService.deleteCPDisplayLayout(id);
	}

	@Override
	public CategoryDisplayPage getCategoryDisplayPage(Long id)
		throws Exception {

		return _toCategoryDisplayPage(id);
	}

	@Override
	public Page<CategoryDisplayPage>
			getChannelByExternalReferenceCodeCategoryDisplayPagesPage(
				String externalReferenceCode, String search, Filter filter,
				Pagination pagination, Sort[] sorts)
		throws Exception {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.
				getCommerceChannelByExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		return getChannelIdCategoryDisplayPagesPage(
			commerceChannel.getCommerceChannelId(), search, filter, pagination,
			sorts);
	}

	@Override
	public Page<CategoryDisplayPage> getChannelIdCategoryDisplayPagesPage(
			Long id, String search, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(id);

		return SearchUtil.search(
			HashMapBuilder.put(
				"post",
				addAction(
					ActionKeys.UPDATE, id, "postChannelIdCategoryDisplayPage",
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
					"entryModelClassName", AssetCategory.class.getName());

				searchContext.setAttribute(
					Field.GROUP_ID,
					String.valueOf(commerceChannel.getSiteGroupId()));
			},
			sorts,
			document -> {
				long cpDisplayLayoutId = GetterUtil.getLong(
					document.get(Field.ENTRY_CLASS_PK));

				return _toCategoryDisplayPage(cpDisplayLayoutId);
			});
	}

	@Override
	public CategoryDisplayPage patchCategoryDisplayPage(
			Long id, CategoryDisplayPage categoryDisplayPage)
		throws Exception {

		CPDisplayLayout cpDisplayLayout =
			_cpDisplayLayoutService.getCPDisplayLayout(id);

		long classPK = GetterUtil.getLong(categoryDisplayPage.getCategoryId());

		if (classPK == 0) {
			String categoryExternalReferenceCode = GetterUtil.getString(
				categoryDisplayPage.getCategoryExternalReferenceCode());

			if (Validator.isNotNull(categoryExternalReferenceCode)) {
				Group group = _groupService.fetchGroupByExternalReferenceCode(
					categoryDisplayPage.getGroupExternalReferenceCode(),
					contextCompany.getCompanyId());

				if (group == null) {
					throw new NoSuchGroupException();
				}

				AssetCategory assetCategory =
					_assetCategoryService.fetchCategoryByExternalReferenceCode(
						categoryExternalReferenceCode, group.getGroupId());

				if (assetCategory != null) {
					classPK = assetCategory.getCategoryId();
				}
			}

			if (classPK == 0) {
				classPK = cpDisplayLayout.getClassPK();
			}
		}

		_cpDisplayLayoutService.updateCPDisplayLayout(
			id, classPK, cpDisplayLayout.getLayoutPageTemplateEntryUuid(),
			GetterUtil.getString(
				categoryDisplayPage.getPageUuid(),
				cpDisplayLayout.getLayoutUuid()));

		return _toCategoryDisplayPage(id);
	}

	@Override
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

	@Override
	public CategoryDisplayPage postChannelIdCategoryDisplayPage(
			Long id, CategoryDisplayPage categoryDisplayPage)
		throws Exception {

		long classPK = GetterUtil.getLong(categoryDisplayPage.getCategoryId());

		if (classPK == 0) {
			Group group = _groupService.fetchGroupByExternalReferenceCode(
				categoryDisplayPage.getGroupExternalReferenceCode(),
				contextCompany.getCompanyId());

			if (group == null) {
				throw new NoSuchGroupException();
			}

			AssetCategory assetCategory =
				_assetCategoryService.fetchCategoryByExternalReferenceCode(
					categoryDisplayPage.getCategoryExternalReferenceCode(),
					group.getGroupId());

			if (assetCategory == null) {
				throw new NoSuchCategoryException();
			}

			classPK = assetCategory.getCategoryId();
		}

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(id);

		CPDisplayLayout cpDisplayLayout =
			_cpDisplayLayoutService.addCPDisplayLayout(
				commerceChannel.getSiteGroupId(), AssetCategory.class, classPK,
				null, categoryDisplayPage.getPageUuid());

		return _toCategoryDisplayPage(cpDisplayLayout.getCPDisplayLayoutId());
	}

	private Map<String, Map<String, String>> _getActions(
		CPDisplayLayout cpDisplayLayout) {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			ActionUtil.addAction(
				ActionKeys.UPDATE, getClass(), cpDisplayLayout.getClassPK(),
				"deleteCategoryDisplayPage",
				_assetCategoryModelResourcePermission,
				cpDisplayLayout.getCPDisplayLayoutId(), contextUriInfo)
		).put(
			"get",
			ActionUtil.addAction(
				ActionKeys.VIEW, getClass(), cpDisplayLayout.getClassPK(),
				"getCategoryDisplayPage", _assetCategoryModelResourcePermission,
				cpDisplayLayout.getCPDisplayLayoutId(), contextUriInfo)
		).put(
			"patch",
			ActionUtil.addAction(
				ActionKeys.UPDATE, getClass(), cpDisplayLayout.getClassPK(),
				"patchCategoryDisplayPage",
				_assetCategoryModelResourcePermission,
				cpDisplayLayout.getCPDisplayLayoutId(), contextUriInfo)
		).build();
	}

	private CategoryDisplayPage _toCategoryDisplayPage(Long cpDisplayLayoutId)
		throws Exception {

		CPDisplayLayout cpDisplayLayout =
			_cpDisplayLayoutService.getCPDisplayLayout(cpDisplayLayoutId);

		return _categoryDisplayPageDTOConvertor.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				_getActions(cpDisplayLayout), _dtoConverterRegistry,
				cpDisplayLayoutId, contextAcceptLanguage.getPreferredLocale(),
				contextUriInfo, contextUser));
	}

	@Reference(
		target = "(model.class.name=com.liferay.asset.kernel.model.AssetCategory)"
	)
	private ModelResourcePermission<AssetCategory>
		_assetCategoryModelResourcePermission;

	@Reference
	private AssetCategoryService _assetCategoryService;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.admin.channel.internal.dto.v1_0.converter.CategoryDisplayPageDTOConverter)"
	)
	private DTOConverter<CPDisplayLayout, CategoryDisplayPage>
		_categoryDisplayPageDTOConvertor;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CPDisplayLayoutService _cpDisplayLayoutService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private GroupService _groupService;

}