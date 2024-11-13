/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0;

import com.liferay.headless.admin.site.dto.v1_0.UtilityPage;
import com.liferay.headless.admin.site.resource.v1_0.UtilityPageResource;
import com.liferay.headless.common.spi.service.context.ServiceContextBuilder;
import com.liferay.layout.utility.page.kernel.constants.LayoutUtilityPageEntryConstants;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rubén Pulido
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/utility-page.properties",
	scope = ServiceScope.PROTOTYPE, service = UtilityPageResource.class
)
public class UtilityPageResourceImpl extends BaseUtilityPageResourceImpl {

	@Override
	public void deleteSiteSiteByExternalReferenceCodeUtilityPage(
			String siteExternalReferenceCode,
			String utilityPageExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Group group = groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		_layoutUtilityPageEntryService.deleteLayoutUtilityPageEntry(
			utilityPageExternalReferenceCode, group.getGroupId());
	}

	@Override
	public UtilityPage getSiteSiteByExternalReferenceCodeUtilityPage(
			String siteExternalReferenceCode,
			String utilityPageExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Group group = groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		return _utilityPageDTOConverter.toDTO(
			_layoutUtilityPageEntryService.
				getLayoutUtilityPageEntryByExternalReferenceCode(
					utilityPageExternalReferenceCode, group.getGroupId()));
	}

	@Override
	public Page<UtilityPage> getSiteSiteByExternalReferenceCodeUtilityPagesPage(
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
				_layoutUtilityPageEntryService.getLayoutUtilityPageEntries(
					group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null),
				layoutUtilityPageEntry -> _utilityPageDTOConverter.toDTO(
					layoutUtilityPageEntry)));
	}

	@Override
	public UtilityPage postSiteSiteByExternalReferenceCodeUtilityPage(
			String siteExternalReferenceCode, UtilityPage utilityPage)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Group group = groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		return _addLayoutUtilityPageEntry(group, utilityPage);
	}

	@Override
	public UtilityPage putSiteSiteByExternalReferenceCodeUtilityPage(
			String siteExternalReferenceCode,
			String utilityPageExternalReferenceCode, UtilityPage utilityPage)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Group group = groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_layoutUtilityPageEntryService.
				fetchLayoutUtilityPageEntryByExternalReferenceCode(
					utilityPageExternalReferenceCode, group.getGroupId());

		if (layoutUtilityPageEntry == null) {
			return _addLayoutUtilityPageEntry(group, utilityPage);
		}

		if (Validator.isNotNull(utilityPage.getMarkedAsDefault())) {
			if (GetterUtil.getBoolean(utilityPage.getMarkedAsDefault()) &&
				!layoutUtilityPageEntry.isDefaultLayoutUtilityPageEntry()) {

				layoutUtilityPageEntry =
					_layoutUtilityPageEntryService.
						setDefaultLayoutUtilityPageEntry(
							layoutUtilityPageEntry.
								getLayoutUtilityPageEntryId());
			}
			else if (!GetterUtil.getBoolean(utilityPage.getMarkedAsDefault()) &&
					 layoutUtilityPageEntry.isDefaultLayoutUtilityPageEntry()) {

				layoutUtilityPageEntry =
					_layoutUtilityPageEntryService.
						unsetDefaultLayoutUtilityPageEntry(
							layoutUtilityPageEntry.
								getLayoutUtilityPageEntryId());
			}
		}

		return _utilityPageDTOConverter.toDTO(
			_layoutUtilityPageEntryService.updateLayoutUtilityPageEntry(
				layoutUtilityPageEntry.getLayoutUtilityPageEntryId(),
				utilityPage.getName()));
	}

	private UtilityPage _addLayoutUtilityPageEntry(
			Group group, UtilityPage utilityPage)
		throws Exception {

		return _utilityPageDTOConverter.toDTO(
			_layoutUtilityPageEntryService.addLayoutUtilityPageEntry(
				utilityPage.getExternalReferenceCode(), group.getGroupId(), 0L,
				0L, utilityPage.getMarkedAsDefault(), utilityPage.getName(),
				_getType(utilityPage.getType()), 0L,
				_getServiceContext(group, utilityPage)));
	}

	private ServiceContext _getServiceContext(
		Group group, UtilityPage utilityPage) {

		ServiceContext serviceContext = ServiceContextBuilder.create(
			group.getGroupId(), contextHttpServletRequest, null
		).build();

		serviceContext.setCreateDate(utilityPage.getDateCreated());
		serviceContext.setModifiedDate(utilityPage.getDateModified());
		serviceContext.setUuid(utilityPage.getUuid());

		return serviceContext;
	}

	private String _getType(UtilityPage.Type type) {
		if (_externalToInternalValuesMap.containsKey(type)) {
			return _externalToInternalValuesMap.get(type);
		}

		throw new UnsupportedOperationException();
	}

	private static final Map<UtilityPage.Type, String>
		_externalToInternalValuesMap = HashMapBuilder.put(
			UtilityPage.Type.COOKIE_POLICY,
			LayoutUtilityPageEntryConstants.TYPE_COOKIE_POLICY
		).put(
			UtilityPage.Type.CREATE_ACCOUNT,
			LayoutUtilityPageEntryConstants.TYPE_CREATE_ACCOUNT
		).put(
			UtilityPage.Type.ERROR, LayoutUtilityPageEntryConstants.TYPE_STATUS
		).put(
			UtilityPage.Type.ERROR_CODE404,
			LayoutUtilityPageEntryConstants.TYPE_SC_NOT_FOUND
		).put(
			UtilityPage.Type.ERROR_CODE500,
			LayoutUtilityPageEntryConstants.TYPE_SC_INTERNAL_SERVER_ERROR
		).put(
			UtilityPage.Type.FORGOT_PASSWORD,
			LayoutUtilityPageEntryConstants.TYPE_FORGOT_PASSWORD
		).put(
			UtilityPage.Type.LOGIN, LayoutUtilityPageEntryConstants.TYPE_LOGIN
		).put(
			UtilityPage.Type.TERMS_OF_USE,
			LayoutUtilityPageEntryConstants.TYPE_TERMS_OF_USE
		).build();

	@Reference
	private LayoutUtilityPageEntryService _layoutUtilityPageEntryService;

	@Reference(
		target = "(component.name=com.liferay.headless.admin.site.internal.dto.v1_0.converter.UtilityPageDTOConverter)"
	)
	private DTOConverter<LayoutUtilityPageEntry, UtilityPage>
		_utilityPageDTOConverter;

}