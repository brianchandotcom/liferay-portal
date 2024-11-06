/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0;

import com.liferay.headless.admin.site.dto.v1_0.PageTemplateSet;
import com.liferay.headless.admin.site.resource.v1_0.PageTemplateSetResource;
import com.liferay.headless.common.spi.service.context.ServiceContextBuilder;
import com.liferay.layout.page.template.constants.LayoutPageTemplateCollectionTypeConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateConstants;
import com.liferay.layout.page.template.exception.NoSuchPageTemplateCollectionException;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionService;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rubén Pulido
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/page-template-set.properties",
	scope = ServiceScope.PROTOTYPE, service = PageTemplateSetResource.class
)
public class PageTemplateSetResourceImpl
	extends BasePageTemplateSetResourceImpl {

	@Override
	public void deleteSiteSiteByExternalReferenceCodePageTemplateSet(
			String siteExternalReferenceCode,
			String pageTemplateSetExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Group group = _groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		_layoutPageTemplateCollectionService.deleteLayoutPageTemplateCollection(
			pageTemplateSetExternalReferenceCode, group.getGroupId());
	}

	@Override
	public PageTemplateSet getSiteSiteByExternalReferenceCodePageTemplateSet(
			String siteExternalReferenceCode,
			String pageTemplateSetExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Group group = _groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionService.
				fetchLayoutPageTemplateCollection(
					pageTemplateSetExternalReferenceCode, group.getGroupId());

		if (layoutPageTemplateCollection == null) {
			throw new NoSuchPageTemplateCollectionException();
		}

		return _toPageTemplateSet(layoutPageTemplateCollection);
	}

	@Override
	public Page<PageTemplateSet>
			getSiteSiteByExternalReferenceCodePageTemplateSetsPage(
				String siteExternalReferenceCode, String search,
				Aggregation aggregation, Filter filter, Pagination pagination,
				Sort[] sorts)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Group group = _groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		return Page.of(
			transform(
				_layoutPageTemplateCollectionService.
					getLayoutPageTemplateCollections(
						group.getGroupId(),
						LayoutPageTemplateCollectionTypeConstants.BASIC,
						pagination.getStartPosition(),
						pagination.getEndPosition()),
				this::_toPageTemplateSet),
			pagination,
			_layoutPageTemplateCollectionService.
				getLayoutPageTemplateCollectionsCount(
					group.getGroupId(),
					LayoutPageTemplateCollectionTypeConstants.BASIC));
	}

	@Override
	public PageTemplateSet patchSiteSiteByExternalReferenceCodePageTemplateSet(
			String siteExternalReferenceCode,
			String pageTemplateSetExternalReferenceCode,
			PageTemplateSet pageTemplateSet)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Group group = _groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionService.
				fetchLayoutPageTemplateCollection(
					pageTemplateSetExternalReferenceCode, group.getGroupId());

		if (layoutPageTemplateCollection == null) {
			throw new NoSuchPageTemplateCollectionException();
		}

		PageTemplateSet existingPageTemplateSet = _toPageTemplateSet(
			layoutPageTemplateCollection);

		preparePatch(pageTemplateSet, existingPageTemplateSet);

		return _toPageTemplateSet(
			_layoutPageTemplateCollectionService.
				updateLayoutPageTemplateCollection(
					layoutPageTemplateCollection.
						getLayoutPageTemplateCollectionId(),
					existingPageTemplateSet.getName(),
					existingPageTemplateSet.getDescription()));
	}

	@Override
	public PageTemplateSet postSiteSiteByExternalReferenceCodePageTemplateSet(
			String siteExternalReferenceCode, PageTemplateSet pageTemplateSet)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Group group = _groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		return _toPageTemplateSet(
			_addLayoutPageTemplateCollection(group, pageTemplateSet));
	}

	@Override
	public PageTemplateSet putSiteSiteByExternalReferenceCodePageTemplateSet(
			String siteExternalReferenceCode,
			String pageTemplateSetExternalReferenceCode,
			PageTemplateSet pageTemplateSet)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Group group = _groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionService.
				fetchLayoutPageTemplateCollection(
					pageTemplateSetExternalReferenceCode, group.getGroupId());

		if (layoutPageTemplateCollection == null) {
			return _toPageTemplateSet(
				_addLayoutPageTemplateCollection(group, pageTemplateSet));
		}

		return _toPageTemplateSet(
			_layoutPageTemplateCollectionService.
				updateLayoutPageTemplateCollection(
					layoutPageTemplateCollection.
						getLayoutPageTemplateCollectionId(),
					pageTemplateSet.getName(),
					pageTemplateSet.getDescription()));
	}

	@Override
	protected void preparePatch(
		PageTemplateSet pageTemplateSet,
		PageTemplateSet existingPageTemplateSet) {

		if (pageTemplateSet.getDateCreated() != null) {
			existingPageTemplateSet.setDateCreated(
				pageTemplateSet::getDateCreated);
		}

		if (pageTemplateSet.getDateModified() != null) {
			existingPageTemplateSet.setDateModified(
				pageTemplateSet::getDateModified);
		}

		if (Validator.isNotNull(pageTemplateSet.getDescription())) {
			existingPageTemplateSet.setDescription(
				pageTemplateSet::getDescription);
		}

		if (Validator.isNotNull(pageTemplateSet.getExternalReferenceCode())) {
			existingPageTemplateSet.setExternalReferenceCode(
				pageTemplateSet::getExternalReferenceCode);
		}

		if (Validator.isNotNull(pageTemplateSet.getName())) {
			existingPageTemplateSet.setName(pageTemplateSet::getName);
		}
	}

	private LayoutPageTemplateCollection _addLayoutPageTemplateCollection(
			Group group, PageTemplateSet pageTemplateSet)
		throws Exception {

		return _layoutPageTemplateCollectionService.
			addLayoutPageTemplateCollection(
				pageTemplateSet.getExternalReferenceCode(), group.getGroupId(),
				LayoutPageTemplateConstants.
					PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
				pageTemplateSet.getName(), pageTemplateSet.getDescription(),
				LayoutPageTemplateCollectionTypeConstants.BASIC,
				_getServiceContext(group, pageTemplateSet));
	}

	private ServiceContext _getServiceContext(
		Group group, PageTemplateSet pageTemplateSet) {

		ServiceContext serviceContext = ServiceContextBuilder.create(
			group.getGroupId(), contextHttpServletRequest, null
		).build();

		serviceContext.setCreateDate(pageTemplateSet.getDateCreated());
		serviceContext.setModifiedDate(pageTemplateSet.getDateModified());
		serviceContext.setUuid(pageTemplateSet.getUuid());

		return serviceContext;
	}

	private PageTemplateSet _toPageTemplateSet(
			LayoutPageTemplateCollection layoutPageTemplateCollection)
		throws Exception {

		return _pageTemplateSetDTOConverter.toDTO(layoutPageTemplateCollection);
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutPageTemplateCollectionService
		_layoutPageTemplateCollectionService;

	@Reference(
		target = "(component.name=com.liferay.headless.admin.site.internal.dto.v1_0.converter.PageTemplateSetDTOConverter)"
	)
	private DTOConverter<LayoutPageTemplateCollection, PageTemplateSet>
		_pageTemplateSetDTOConverter;

}