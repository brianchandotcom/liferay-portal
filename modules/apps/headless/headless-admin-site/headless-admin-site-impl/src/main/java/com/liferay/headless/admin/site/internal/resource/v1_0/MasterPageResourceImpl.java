/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0;

import com.liferay.headless.admin.site.dto.v1_0.MasterPage;
import com.liferay.headless.admin.site.resource.v1_0.MasterPageResource;
import com.liferay.headless.common.spi.service.context.ServiceContextBuilder;
import com.liferay.layout.page.template.constants.LayoutPageTemplateConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
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
	properties = "OSGI-INF/liferay/rest/v1_0/master-page.properties",
	scope = ServiceScope.PROTOTYPE, service = MasterPageResource.class
)
public class MasterPageResourceImpl extends BaseMasterPageResourceImpl {

	@Override
	public void deleteSiteSiteByExternalReferenceCodeMasterPage(
			String siteExternalReferenceCode,
			String masterPageExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Group group = groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		_layoutPageTemplateEntryService.deleteLayoutPageTemplateEntry(
			masterPageExternalReferenceCode, group.getGroupId());
	}

	@Override
	public MasterPage getSiteSiteByExternalReferenceCodeMasterPage(
			String siteExternalReferenceCode,
			String masterPageExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Group group = groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		return _masterPageDTOConverter.toDTO(
			_layoutPageTemplateEntryService.
				getLayoutPageTemplateEntryByExternalReferenceCode(
					masterPageExternalReferenceCode, group.getGroupId()));
	}

	@Override
	public Page<MasterPage> getSiteSiteByExternalReferenceCodeMasterPagesPage(
			String siteExternalReferenceCode, String search,
			Aggregation aggregation, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Group group = groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		return Page.of(
			transform(
				_layoutPageTemplateEntryService.getLayoutPageTemplateEntries(
					group.getGroupId(),
					LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null),
				layoutPageTemplateEntry -> _masterPageDTOConverter.toDTO(
					layoutPageTemplateEntry)));
	}

	@Override
	public MasterPage postSiteSiteByExternalReferenceCodeMasterPage(
			String siteExternalReferenceCode, MasterPage masterPage)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Group group = groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		return _addMasterPage(group, masterPage);
	}

	private MasterPage _addMasterPage(Group group, MasterPage masterPage)
		throws Exception {

		return _masterPageDTOConverter.toDTO(
			_layoutPageTemplateEntryService.addLayoutPageTemplateEntry(
				masterPage.getExternalReferenceCode(), group.getGroupId(),
				LayoutPageTemplateConstants.
					PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
				masterPage.getName(),
				LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT, 0,
				WorkflowConstants.STATUS_DRAFT,
				_getServiceContext(group, masterPage)));
	}

	private ServiceContext _getServiceContext(
		Group group, MasterPage masterPage) {

		ServiceContext serviceContext = ServiceContextBuilder.create(
			group.getGroupId(), contextHttpServletRequest, null
		).build();

		serviceContext.setCreateDate(masterPage.getDateCreated());
		serviceContext.setModifiedDate(masterPage.getDateModified());
		serviceContext.setUuid(masterPage.getUuid());

		return serviceContext;
	}

	@Reference
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	@Reference(
		target = "(component.name=com.liferay.headless.admin.site.internal.dto.v1_0.converter.MasterPageDTOConverter)"
	)
	private DTOConverter<LayoutPageTemplateEntry, MasterPage>
		_masterPageDTOConverter;

}