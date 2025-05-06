/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0;

import com.liferay.headless.admin.site.dto.v1_0.ContentPageSpecification;
import com.liferay.headless.admin.site.dto.v1_0.PageSpecification;
import com.liferay.headless.admin.site.dto.v1_0.UtilityPage;
import com.liferay.headless.admin.site.dto.v1_0.UtilityPageSEOSettings;
import com.liferay.headless.admin.site.dto.v1_0.UtilityPageSettings;
import com.liferay.headless.admin.site.internal.resource.v1_0.util.FileEntryUtil;
import com.liferay.headless.admin.site.internal.resource.v1_0.util.GroupUtil;
import com.liferay.headless.admin.site.internal.resource.v1_0.util.LayoutUtil;
import com.liferay.headless.admin.site.internal.resource.v1_0.util.ServiceContextUtil;
import com.liferay.headless.admin.site.resource.v1_0.UtilityPageResource;
import com.liferay.layout.utility.page.kernel.constants.LayoutUtilityPageEntryConstants;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryService;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Collections;
import java.util.Locale;
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

		_layoutUtilityPageEntryService.deleteLayoutUtilityPageEntry(
			utilityPageExternalReferenceCode,
			GroupUtil.getGroupId(
				false, contextCompany.getCompanyId(),
				siteExternalReferenceCode));
	}

	@Override
	public UtilityPage getSiteSiteByExternalReferenceCodeUtilityPage(
			String siteExternalReferenceCode,
			String utilityPageExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		return _utilityPageDTOConverter.toDTO(
			_layoutUtilityPageEntryService.
				getLayoutUtilityPageEntryByExternalReferenceCode(
					utilityPageExternalReferenceCode,
					GroupUtil.getGroupId(
						true, contextCompany.getCompanyId(),
						siteExternalReferenceCode)));
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

		long groupId = GroupUtil.getGroupId(
			true, contextCompany.getCompanyId(), siteExternalReferenceCode);

		if (Validator.isNull(search)) {
			return Page.of(
				transform(
					_layoutUtilityPageEntryService.getLayoutUtilityPageEntries(
						groupId, pagination.getStartPosition(),
						pagination.getEndPosition(), null),
					layoutUtilityPageEntry -> _utilityPageDTOConverter.toDTO(
						layoutUtilityPageEntry)),
				pagination,
				_layoutUtilityPageEntryService.getLayoutUtilityPageEntriesCount(
					groupId));
		}

		return Page.of(
			transform(
				_layoutUtilityPageEntryService.getLayoutUtilityPageEntries(
					groupId, search, null, pagination.getStartPosition(),
					pagination.getEndPosition(), null),
				layoutUtilityPageEntry -> _utilityPageDTOConverter.toDTO(
					layoutUtilityPageEntry)),
			pagination,
			_layoutUtilityPageEntryService.getLayoutUtilityPageEntriesCount(
				groupId, search, null));
	}

	@Override
	public UtilityPage postSiteSiteByExternalReferenceCodeUtilityPage(
			String siteExternalReferenceCode, UtilityPage utilityPage)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		return _addLayoutUtilityPageEntry(
			GroupUtil.getGroupId(
				false, contextCompany.getCompanyId(),
				siteExternalReferenceCode),
			utilityPage);
	}

	@Override
	public ContentPageSpecification
			postSiteSiteByExternalReferenceCodeUtilityPagePageSpecification(
				String siteExternalReferenceCode,
				String utilityPageExternalReferenceCode,
				ContentPageSpecification contentPageSpecification)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_layoutUtilityPageEntryService.
				getLayoutUtilityPageEntryByExternalReferenceCode(
					utilityPageExternalReferenceCode,
					GroupUtil.getGroupId(
						false, contextCompany.getCompanyId(),
						siteExternalReferenceCode));

		return (ContentPageSpecification)_pageSpecificationDTOConverter.toDTO(
			LayoutUtil.addDraftToLayout(
				contentPageSpecification,
				_layoutLocalService.getLayout(layoutUtilityPageEntry.getPlid()),
				ServiceContextUtil.createServiceContext(
					layoutUtilityPageEntry.getGroupId(),
					contextHttpServletRequest, contextUser.getUserId())));
	}

	@Override
	public UtilityPage putSiteSiteByExternalReferenceCodeUtilityPage(
			String siteExternalReferenceCode,
			String utilityPageExternalReferenceCode, UtilityPage utilityPage)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		long groupId = GroupUtil.getGroupId(
			false, contextCompany.getCompanyId(), siteExternalReferenceCode);

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_layoutUtilityPageEntryService.
				fetchLayoutUtilityPageEntryByExternalReferenceCode(
					utilityPageExternalReferenceCode, groupId);

		if (layoutUtilityPageEntry == null) {
			return _addLayoutUtilityPageEntry(groupId, utilityPage);
		}

		Layout layout = _layoutLocalService.getLayout(
			layoutUtilityPageEntry.getPlid());

		Map<Locale, String> titleMap = Collections.singletonMap(
			_portal.getSiteDefaultLocale(groupId), utilityPage.getName());
		Map<Locale, String> descriptionMap = Collections.emptyMap();

		UtilityPageSettings utilityPageSettings =
			utilityPage.getUtilityPageSettings();

		if ((utilityPageSettings != null) &&
			(utilityPageSettings.getSeoSettings() != null)) {

			UtilityPageSEOSettings utilityPageSEOSettings =
				utilityPageSettings.getSeoSettings();

			titleMap = LocalizedMapUtil.getLocalizedMap(
				utilityPageSEOSettings.getHtmlTitle_i18n());
			descriptionMap = LocalizedMapUtil.getLocalizedMap(
				utilityPageSEOSettings.getDescription_i18n());
		}

		LayoutUtil.updateContentLayout(
			layout, layout.getNameMap(), titleMap, descriptionMap,
			LocalizedMapUtil.getLocalizedMap(
				utilityPage.getFriendlyUrlPath_i18n()),
			utilityPage.getPageSpecifications(),
			_getServiceContext(groupId, utilityPage));

		if (GetterUtil.getBoolean(utilityPage.getMarkedAsDefault()) &&
			!layoutUtilityPageEntry.isDefaultLayoutUtilityPageEntry()) {

			layoutUtilityPageEntry =
				_layoutUtilityPageEntryService.setDefaultLayoutUtilityPageEntry(
					layoutUtilityPageEntry.getLayoutUtilityPageEntryId());
		}
		else if (!GetterUtil.getBoolean(utilityPage.getMarkedAsDefault()) &&
				 layoutUtilityPageEntry.isDefaultLayoutUtilityPageEntry()) {

			layoutUtilityPageEntry =
				_layoutUtilityPageEntryService.
					unsetDefaultLayoutUtilityPageEntry(
						layoutUtilityPageEntry.getLayoutUtilityPageEntryId());
		}

		return _utilityPageDTOConverter.toDTO(
			_layoutUtilityPageEntryService.updateLayoutUtilityPageEntry(
				layoutUtilityPageEntry.getLayoutUtilityPageEntryId(),
				utilityPage.getName()));
	}

	@Override
	protected void preparePatch(
		UtilityPage utilityPage, UtilityPage existingUtilityPage) {

		if (utilityPage.getPageSpecifications() != null) {
			existingUtilityPage.setPageSpecifications(
				utilityPage::getPageSpecifications);
		}

		if (utilityPage.getUtilityPageSettings() != null) {
			existingUtilityPage.setUtilityPageSettings(
				utilityPage::getUtilityPageSettings);
		}
	}

	private UtilityPage _addLayoutUtilityPageEntry(
			long groupId, UtilityPage utilityPage)
		throws Exception {

		ServiceContext serviceContext = _getServiceContext(
			groupId, utilityPage);

		return _utilityPageDTOConverter.toDTO(
			_layoutUtilityPageEntryService.addLayoutUtilityPageEntry(
				utilityPage.getExternalReferenceCode(), groupId,
				_getLayoutPlid(groupId, utilityPage, serviceContext),
				FileEntryUtil.getPreviewFileEntryId(
					groupId, utilityPage.getThumbnail()),
				utilityPage.getMarkedAsDefault(), utilityPage.getName(),
				_getType(utilityPage.getType()), 0L, serviceContext));
	}

	private long _getLayoutPlid(
			long groupId, UtilityPage utilityPage,
			ServiceContext serviceContext)
		throws Exception {

		Map<Locale, String> nameMap = Collections.singletonMap(
			_portal.getSiteDefaultLocale(groupId), utilityPage.getName());

		Map<Locale, String> titleMap = nameMap;

		Map<Locale, String> descriptionMap = Collections.emptyMap();

		UtilityPageSettings utilityPageSettings =
			utilityPage.getUtilityPageSettings();

		if ((utilityPageSettings != null) &&
			(utilityPageSettings.getSeoSettings() != null)) {

			UtilityPageSEOSettings utilityPageSEOSettings =
				utilityPageSettings.getSeoSettings();

			titleMap = LocalizedMapUtil.getLocalizedMap(
				utilityPageSEOSettings.getHtmlTitle_i18n());
			descriptionMap = LocalizedMapUtil.getLocalizedMap(
				utilityPageSEOSettings.getDescription_i18n());
		}

		serviceContext.setAttribute(
			"layout.instanceable.allowed", Boolean.TRUE);

		Layout layout = LayoutUtil.addContentLayout(
			groupId, utilityPage.getPageSpecifications(), false, nameMap,
			titleMap, descriptionMap, LayoutConstants.TYPE_UTILITY, true, true,
			LocalizedMapUtil.getLocalizedMap(
				utilityPage.getFriendlyUrlPath_i18n()),
			WorkflowConstants.STATUS_DRAFT, serviceContext);

		if (layout == null) {
			return 0;
		}

		return layout.getPlid();
	}

	private ServiceContext _getServiceContext(
		long groupId, UtilityPage utilityPage) {

		ServiceContext serviceContext = ServiceContextUtil.createServiceContext(
			groupId, contextHttpServletRequest, contextUser.getUserId());

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
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutUtilityPageEntryService _layoutUtilityPageEntryService;

	@Reference(
		target = "(component.name=com.liferay.headless.admin.site.internal.dto.v1_0.converter.PageSpecificationDTOConverter)"
	)
	private DTOConverter<Layout, PageSpecification>
		_pageSpecificationDTOConverter;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(component.name=com.liferay.headless.admin.site.internal.dto.v1_0.converter.UtilityPageDTOConverter)"
	)
	private DTOConverter<LayoutUtilityPageEntry, UtilityPage>
		_utilityPageDTOConverter;

}