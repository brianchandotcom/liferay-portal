/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0;

import com.liferay.headless.admin.site.dto.v1_0.ClassNameReference;
import com.liferay.headless.admin.site.dto.v1_0.CollectionPageSettings;
import com.liferay.headless.admin.site.dto.v1_0.CollectionReference;
import com.liferay.headless.admin.site.dto.v1_0.ContentPageSettings;
import com.liferay.headless.admin.site.dto.v1_0.ContentPageSpecification;
import com.liferay.headless.admin.site.dto.v1_0.PageSettings;
import com.liferay.headless.admin.site.dto.v1_0.PageSpecification;
import com.liferay.headless.admin.site.dto.v1_0.SitePage;
import com.liferay.headless.admin.site.dto.v1_0.WidgetPageSettings;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.SitePageTypeUtil;
import com.liferay.headless.admin.site.internal.resource.v1_0.util.GroupUtil;
import com.liferay.headless.admin.site.internal.resource.v1_0.util.LayoutUtil;
import com.liferay.headless.admin.site.internal.resource.v1_0.util.ServiceContextUtil;
import com.liferay.headless.admin.site.resource.v1_0.SitePageResource;
import com.liferay.headless.common.spi.service.context.ServiceContextBuilder;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.io.Serializable;

import java.util.Map;

import javax.ws.rs.NotSupportedException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rubén Pulido
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/site-page.properties",
	scope = ServiceScope.PROTOTYPE, service = SitePageResource.class
)
public class SitePageResourceImpl extends BaseSitePageResourceImpl {

	@Override
	public void deleteSiteSiteByExternalReferenceCodeSitePage(
			String siteExternalReferenceCode,
			String sitePageExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Layout layout = _layoutService.getLayoutByExternalReferenceCode(
			sitePageExternalReferenceCode,
			GroupUtil.getGroupId(
				false, contextCompany.getCompanyId(),
				siteExternalReferenceCode));

		_validateSitePageLayout(layout);

		_layoutService.deleteLayout(
			layout.getPlid(),
			ServiceContextUtil.createServiceContext(
				layout.getGroupId(), contextHttpServletRequest,
				contextUser.getUserId()));
	}

	@Override
	public SitePage getSiteSiteByExternalReferenceCodeSitePage(
			String siteExternalReferenceCode,
			String sitePageExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Layout layout = _layoutService.getLayoutByExternalReferenceCode(
			sitePageExternalReferenceCode,
			GroupUtil.getGroupId(
				true, contextCompany.getCompanyId(),
				siteExternalReferenceCode));

		_validateSitePageLayout(layout);

		return _toSitePage(layout);
	}

	@Override
	public Page<SitePage> getSiteSiteByExternalReferenceCodeSitePagesPage(
			String siteExternalReferenceCode, String search,
			Aggregation aggregation, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		long groupId = GroupUtil.getGroupId(
			true, contextCompany.getCompanyId(), siteExternalReferenceCode);

		return SearchUtil.search(
			null,
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter(Field.GROUP_ID, String.valueOf(groupId)),
					BooleanClauseOccur.MUST);
			},
			filter, Layout.class.getName(), search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.addVulcanAggregation(aggregation);
				searchContext.setAttribute(Field.TITLE, search);
				searchContext.setAttribute(
					Field.TYPE,
					new String[] {
						LayoutConstants.TYPE_COLLECTION,
						LayoutConstants.TYPE_CONTENT,
						LayoutConstants.TYPE_PORTLET
					});
				searchContext.setAttribute(
					"privateLayout", Boolean.FALSE.toString());
				searchContext.setCompanyId(contextCompany.getCompanyId());
				searchContext.setGroupIds(new long[] {groupId});
				searchContext.setKeywords(search);
			},
			sorts,
			document -> {
				long plid = GetterUtil.getLong(
					document.get(Field.ENTRY_CLASS_PK));

				return _toSitePage(_layoutLocalService.getLayout(plid));
			});
	}

	@Override
	public SitePage postByExternalReferenceCodeSitePage(
			String siteExternalReferenceCode, SitePage sitePage)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		return _toSitePage(
			_addLayout(
				GroupUtil.getGroupId(
					false, contextCompany.getCompanyId(),
					siteExternalReferenceCode),
				sitePage));
	}

	@Override
	public ContentPageSpecification
			postSiteSiteByExternalReferenceCodeSitePagePageSpecification(
				String siteExternalReferenceCode,
				String sitePageExternalReferenceCode,
				ContentPageSpecification contentPageSpecification)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Layout layout = _layoutService.getLayoutByExternalReferenceCode(
			sitePageExternalReferenceCode,
			GroupUtil.getGroupId(
				false, contextCompany.getCompanyId(),
				siteExternalReferenceCode));

		if (!layout.isTypeContent()) {
			throw new UnsupportedOperationException();
		}

		return (ContentPageSpecification)_pageSpecificationDTOConverter.toDTO(
			LayoutUtil.addDraftToPublishedLayout(
				contentPageSpecification, layout,
				ServiceContextUtil.createServiceContext(
					layout.getGroupId(), contextHttpServletRequest,
					contextUser.getUserId())));
	}

	@Override
	public Page<SitePage> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		if (parameters.containsKey("siteId")) {
			Group group = _groupLocalService.getGroup(
				(Long)parameters.get("siteId"));

			return getSiteSiteByExternalReferenceCodeSitePagesPage(
				group.getExternalReferenceCode(), search, null, filter,
				pagination, sorts);
		}

		throw new NotSupportedException(
			"One of the following parameters must be specified: [siteId]");
	}

	private Layout _addLayout(long groupId, SitePage sitePage)
		throws Exception {

		ServiceContext serviceContext = ServiceContextBuilder.create(
			groupId, contextHttpServletRequest, sitePage.getViewableByAsString()
		).build();

		serviceContext.setUuid(sitePage.getUuid());

		return _layoutService.addLayout(
			sitePage.getExternalReferenceCode(), groupId, false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, 0, 0,
			LocalizedMapUtil.getLocalizedMap(sitePage.getName_i18n()),
			LocalizedMapUtil.getLocalizedMap(sitePage.getName_i18n()), null,
			null, null, SitePageTypeUtil.toInternalType(sitePage.getType()),
			_getTypeSettings(sitePage),
			_isHiddenFromNavigation(sitePage.getPageSettings()), false,
			LocalizedMapUtil.getLocalizedMap(
				sitePage.getFriendlyUrlPath_i18n()),
			0, serviceContext);
	}

	private String _getTypeSettings(
		CollectionPageSettings collectionPageSettings) {

		CollectionReference collectionReference =
			collectionPageSettings.getCollectionReference();

		if ((collectionReference == null) ||
			(collectionReference.getCollectionType() == null)) {

			throw new UnsupportedOperationException();
		}

		CollectionReference.CollectionType collectionType =
			collectionReference.getCollectionType();

		if (collectionType == CollectionReference.CollectionType.COLLECTION) {

			// TODO

		}
		else if (collectionType ==
					CollectionReference.CollectionType.COLLECTION_PROVIDER) {

			if (!(collectionReference instanceof ClassNameReference)) {
				throw new UnsupportedOperationException();
			}

			ClassNameReference classNameReference =
				(ClassNameReference)collectionReference;

			if (classNameReference.getClassName() == null) {
				throw new UnsupportedOperationException();
			}

			return UnicodePropertiesBuilder.create(
				true
			).setProperty(
				"collectionPK", classNameReference.getClassName()
			).setProperty(
				"collectionType", "TODO"
			).buildString();
		}

		throw new UnsupportedOperationException();
	}

	private String _getTypeSettings(SitePage sitePage) {
		PageSettings pageSettings = sitePage.getPageSettings();

		if (sitePage.getType() == SitePage.Type.COLLECTION_PAGE) {
			if (!(pageSettings instanceof CollectionPageSettings)) {
				throw new UnsupportedOperationException();
			}

			return _getTypeSettings((CollectionPageSettings)pageSettings);
		}

		if (sitePage.getType() == SitePage.Type.CONTENT_PAGE) {
			if (!(pageSettings instanceof ContentPageSettings)) {
				throw new UnsupportedOperationException();
			}

			return null;
		}

		if ((sitePage.getType() != SitePage.Type.WIDGET_PAGE) ||
			!(pageSettings instanceof WidgetPageSettings)) {

			throw new UnsupportedOperationException();
		}

		WidgetPageSettings widgetPageSettings =
			(WidgetPageSettings)pageSettings;

		return UnicodePropertiesBuilder.create(
			true
		).setProperty(
			LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID,
			widgetPageSettings.getLayoutTemplateId()
		).buildString();
	}

	private boolean _isHiddenFromNavigation(PageSettings pageSettings) {
		if (GetterUtil.getBoolean(pageSettings.getHiddenFromNavigation())) {
			return true;
		}

		return false;
	}

	private SitePage _toSitePage(Layout layout) throws Exception {
		return _sitePageDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(), null,
				_dtoConverterRegistry, contextHttpServletRequest,
				layout.getPlid(), contextAcceptLanguage.getPreferredLocale(),
				contextUriInfo, contextUser),
			layout);
	}

	private void _validateSitePageLayout(Layout layout) {
		if (layout.isDraftLayout() || layout.isTypeAssetDisplay() ||
			layout.isTypeUtility()) {

			throw new UnsupportedOperationException();
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.
				fetchLayoutPageTemplateEntryByPlid(layout.getPlid());

		if (layoutPageTemplateEntry != null) {
			throw new UnsupportedOperationException();
		}
	}

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutService _layoutService;

	@Reference(
		target = "(component.name=com.liferay.headless.admin.site.internal.dto.v1_0.converter.PageSpecificationDTOConverter)"
	)
	private DTOConverter<Layout, PageSpecification>
		_pageSpecificationDTOConverter;

	@Reference(
		target = "(component.name=com.liferay.headless.admin.site.internal.dto.v1_0.converter.SitePageDTOConverter)"
	)
	private DTOConverter<Layout, SitePage> _sitePageDTOConverter;

}