/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0;

import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.headless.admin.site.dto.v1_0.ContentPageSettings;
import com.liferay.headless.admin.site.dto.v1_0.ContentPageSpecification;
import com.liferay.headless.admin.site.dto.v1_0.PageSettings;
import com.liferay.headless.admin.site.dto.v1_0.PageSpecification;
import com.liferay.headless.admin.site.dto.v1_0.SitePage;
import com.liferay.headless.admin.site.dto.v1_0.WidgetPageSettings;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.SitePageTypeUtil;
import com.liferay.headless.admin.site.internal.odata.entity.v1_0.SitePageEntityModel;
import com.liferay.headless.admin.site.internal.resource.v1_0.util.GroupUtil;
import com.liferay.headless.admin.site.internal.resource.v1_0.util.LayoutUtil;
import com.liferay.headless.admin.site.internal.resource.v1_0.util.PageSpecificationUtil;
import com.liferay.headless.admin.site.internal.resource.v1_0.util.ServiceContextUtil;
import com.liferay.headless.admin.site.resource.v1_0.SitePageResource;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.CustomizedPages;
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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.portal.vulcan.util.SearchUtil;

import jakarta.validation.ValidationException;

import jakarta.ws.rs.NotSupportedException;
import jakarta.ws.rs.core.MultivaluedMap;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

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
	public void deleteSiteSitePage(
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
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	@Override
	public ContentPageSpecification postSiteSitePagePageSpecification(
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
			LayoutUtil.addDraftToLayout(
				_cetManager, contentPageSpecification, _infoItemServiceRegistry,
				layout,
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

			return getSiteSitePagesPage(
				group.getExternalReferenceCode(), search, null, filter,
				pagination, sorts);
		}

		throw new NotSupportedException(
			"One of the following parameters must be specified: [siteId]");
	}

	@Override
	protected SitePage doGetSiteSitePage(
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
	protected Page<SitePage> doGetSiteSitePagesPage(
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
						LayoutConstants.TYPE_CONTENT,
						LayoutConstants.TYPE_PORTLET
					});
				searchContext.setAttribute(
					"privateLayout", Boolean.FALSE.toString());
				searchContext.setAttribute(
					"status", WorkflowConstants.STATUS_ANY);
				searchContext.setAttribute(
					"systemLayout", Boolean.FALSE.toString());
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
	protected SitePage doPostSiteSitePage(
			String siteExternalReferenceCode, SitePage sitePage)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		return _toSitePage(
			_addLayout(
				sitePage.getExternalReferenceCode(),
				GroupUtil.getGroupId(
					false, contextCompany.getCompanyId(),
					siteExternalReferenceCode),
				sitePage));
	}

	@Override
	protected SitePage doPutSiteSitePage(
			String siteExternalReferenceCode,
			String sitePageExternalReferenceCode, SitePage sitePage)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		long groupId = GroupUtil.getGroupId(
			false, contextCompany.getCompanyId(), siteExternalReferenceCode);

		Layout layout = _layoutService.fetchLayoutByExternalReferenceCode(
			sitePageExternalReferenceCode, groupId);

		if (layout == null) {
			return _toSitePage(
				_addLayout(sitePageExternalReferenceCode, groupId, sitePage));
		}

		_validateSitePageLayout(layout);

		if ((sitePage.getType() != null) &&
			!Objects.equals(
				layout.getType(),
				SitePageTypeUtil.toInternalType(sitePage.getType()))) {

			throw new UnsupportedOperationException();
		}

		return _toSitePage(_updateLayout(layout, sitePage));
	}

	@Override
	protected Long getPermissionCheckerResourceId(
			String groupExternalReferenceCode, String externalReferenceCode)
		throws Exception {

		Layout layout = _layoutService.getLayoutByExternalReferenceCode(
			externalReferenceCode,
			getPermissionCheckerGroupId(groupExternalReferenceCode));

		return layout.getPrimaryKey();
	}

	@Override
	protected String getPermissionCheckerResourceName(
			String groupExternalReferenceCode, String externalReferenceCode)
		throws Exception {

		return Layout.class.getName();
	}

	@Override
	protected void preparePatch(SitePage sitePage, SitePage existingSitePage) {
		if (sitePage.getPageSettings() != null) {
			existingSitePage.setPageSettings(sitePage::getPageSettings);
		}

		if (sitePage.getPageSpecifications() != null) {
			existingSitePage.setPageSpecifications(
				sitePage::getPageSpecifications);
		}
	}

	private Layout _addLayout(
			String externalReferenceCode, long groupId, SitePage sitePage)
		throws Exception {

		if (sitePage.getExternalReferenceCode() == null) {
			sitePage.setExternalReferenceCode(() -> externalReferenceCode);
		}

		if (!Objects.equals(
				externalReferenceCode, sitePage.getExternalReferenceCode())) {

			throw new UnsupportedOperationException();
		}

		ServiceContext serviceContext = ServiceContextUtil.createServiceContext(
			sitePage.getTaxonomyCategoryItemExternalReferences(),
			contextCompany.getCompanyId(), sitePage.getDateCreated(), groupId,
			contextHttpServletRequest, sitePage.getKeywords(),
			sitePage.getDateModified(), contextUser.getUserId(),
			sitePage.getUuid(), sitePage.getPageSettings());

		_validatePageSpecificationExternalReferenceCode(
			serviceContext, sitePage);

		Map<Locale, String> nameMap = LocalizedMapUtil.getLocalizedMap(
			sitePage.getName_i18n());

		UnicodeProperties typeSettingsUnicodeProperties =
			_getTypeSettingsUnicodeProperties(sitePage);

		Layout layout = null;

		if (Objects.equals(sitePage.getType(), SitePage.Type.CONTENT_PAGE)) {
			layout = LayoutUtil.addContentLayout(
				_cetManager, groupId, _infoItemServiceRegistry,
				sitePage.getPageSpecifications(),
				_getParentLayoutId(
					LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, groupId,
					sitePage.getParentSitePageExternalReferenceCode(),
					serviceContext),
				false, nameMap, null, null, null,
				SitePageTypeUtil.toInternalType(sitePage.getType()),
				typeSettingsUnicodeProperties,
				_isHiddenFromNavigation(false, sitePage.getPageSettings()),
				false,
				LocalizedMapUtil.getLocalizedMap(
					sitePage.getFriendlyUrlPath_i18n()),
				WorkflowConstants.STATUS_APPROVED, serviceContext);
		}
		else {
			layout = LayoutUtil.addPortletLayout(
				_cetManager, sitePage.getExternalReferenceCode(),
				_infoItemServiceRegistry, groupId,
				_getParentLayoutId(
					LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, groupId,
					sitePage.getParentSitePageExternalReferenceCode(),
					serviceContext),
				nameMap, typeSettingsUnicodeProperties,
				_isHiddenFromNavigation(false, sitePage.getPageSettings()),
				LocalizedMapUtil.getLocalizedMap(
					sitePage.getFriendlyUrlPath_i18n()),
				serviceContext,
				PageSpecificationUtil.getWidgetPageSpecification(
					sitePage.getPageSpecifications()));
		}

		PageSettings pageSettings = sitePage.getPageSettings();

		if ((pageSettings != null) && (pageSettings.getPriority() != null)) {
			layout = _layoutService.updatePriority(
				layout.getPlid(), pageSettings.getPriority());
		}

		return layout;
	}

	private long _getParentLayoutId(
			long defaultParentLayoutId, long groupId,
			String parentSitePageExternalReferenceCode,
			ServiceContext serviceContext)
		throws Exception {

		if (parentSitePageExternalReferenceCode == null) {
			return defaultParentLayoutId;
		}

		if (Validator.isNull(parentSitePageExternalReferenceCode)) {
			return LayoutConstants.DEFAULT_PARENT_LAYOUT_ID;
		}

		Layout layout = _layoutService.getOrAddEmptyLayout(
			parentSitePageExternalReferenceCode, groupId, serviceContext);

		return layout.getLayoutId();
	}

	private UnicodeProperties _getTypeSettingsUnicodeProperties(
		SitePage sitePage) {

		PageSettings pageSettings = sitePage.getPageSettings();

		if (pageSettings == null) {
			return null;
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

		UnicodePropertiesBuilder.UnicodePropertiesWrapper
			unicodePropertiesWrapper = UnicodePropertiesBuilder.create(
				true
			).setProperty(
				LayoutConstants.CUSTOMIZABLE_LAYOUT,
				String.valueOf(
					GetterUtil.getBoolean(widgetPageSettings.getCustomizable()))
			);

		if (widgetPageSettings.getLayoutTemplateId() != null) {
			unicodePropertiesWrapper.setProperty(
				LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID,
				widgetPageSettings.getLayoutTemplateId());
		}

		String[] customizableSectionIds =
			widgetPageSettings.getCustomizableSectionIds();

		if (ArrayUtil.isEmpty(customizableSectionIds)) {
			return unicodePropertiesWrapper.build();
		}

		for (String customizableSectionId : customizableSectionIds) {
			unicodePropertiesWrapper.setProperty(
				CustomizedPages.namespaceColumnId(customizableSectionId),
				"true");
		}

		return unicodePropertiesWrapper.build();
	}

	private boolean _isHiddenFromNavigation(
		boolean defaultValue, PageSettings pageSettings) {

		if (pageSettings == null) {
			return defaultValue;
		}

		return GetterUtil.getBoolean(
			pageSettings.getHiddenFromNavigation(), defaultValue);
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

	private Layout _updateLayout(Layout layout, SitePage sitePage)
		throws Exception {

		Map<Locale, String> nameMap = layout.getNameMap();

		if (sitePage.getName_i18n() != null) {
			nameMap = LocalizedMapUtil.getLocalizedMap(sitePage.getName_i18n());
		}

		Map<Locale, String> friendlyURLMap = layout.getFriendlyURLMap();

		if (sitePage.getFriendlyUrlPath_i18n() != null) {
			friendlyURLMap = LocalizedMapUtil.getLocalizedMap(
				sitePage.getFriendlyUrlPath_i18n());
		}

		ServiceContext serviceContext = ServiceContextUtil.createServiceContext(
			sitePage.getTaxonomyCategoryItemExternalReferences(),
			contextCompany.getCompanyId(), sitePage.getDateCreated(),
			layout.getGroupId(), contextHttpServletRequest,
			sitePage.getKeywords(), sitePage.getDateModified(),
			contextUser.getUserId(), sitePage.getUuid(),
			sitePage.getPageSettings());

		serviceContext.setAttribute(
			"hidden",
			_isHiddenFromNavigation(
				layout.isHidden(), sitePage.getPageSettings()));
		serviceContext.setAttribute(
			"parentLayoutId",
			_getParentLayoutId(
				layout.getParentLayoutId(), layout.getGroupId(),
				sitePage.getParentSitePageExternalReferenceCode(),
				serviceContext));

		if (Objects.equals(sitePage.getType(), SitePage.Type.CONTENT_PAGE)) {
			layout = LayoutUtil.updateContentLayout(
				_cetManager, _infoItemServiceRegistry, layout, nameMap,
				layout.getTitleMap(), layout.getDescriptionMap(),
				layout.getRobotsMap(), friendlyURLMap,
				sitePage.getPageSpecifications(), serviceContext);
		}
		else {
			layout = LayoutUtil.updatePortletLayout(
				_cetManager, layout, nameMap, friendlyURLMap,
				_getTypeSettingsUnicodeProperties(sitePage), serviceContext,
				PageSpecificationUtil.getWidgetPageSpecification(
					sitePage.getPageSpecifications()));
		}

		int priority = Integer.MAX_VALUE;

		PageSettings pageSettings = sitePage.getPageSettings();

		if ((pageSettings != null) && (pageSettings.getPriority() != null)) {
			priority = pageSettings.getPriority();
		}

		if (layout.getPriority() == priority) {
			return layout;
		}

		return _layoutService.updatePriority(layout.getPlid(), priority);
	}

	private void _validatePageSpecificationExternalReferenceCode(
		ServiceContext serviceContext, SitePage sitePage) {

		PageSpecification[] pageSpecifications =
			sitePage.getPageSpecifications();

		if (ArrayUtil.isEmpty(pageSpecifications)) {
			serviceContext.setAttribute(
				"layoutExternalReferenceCode",
				sitePage.getExternalReferenceCode());

			return;
		}

		PageSpecification publishedPageSpecification = null;

		if (Objects.equals(sitePage.getType(), SitePage.Type.CONTENT_PAGE) &&
			(pageSpecifications.length == 2)) {

			ContentPageSpecification publishedContentPageSpecification =
				(ContentPageSpecification)pageSpecifications[0];

			if (Validator.isNull(
					publishedContentPageSpecification.
						getDraftContentPageSpecificationExternalReferenceCode())) {

				publishedContentPageSpecification =
					(ContentPageSpecification)pageSpecifications[1];
			}

			publishedPageSpecification = publishedContentPageSpecification;
		}
		else if (Objects.equals(
					sitePage.getType(), SitePage.Type.WIDGET_PAGE) &&
				 (pageSpecifications.length == 1)) {

			publishedPageSpecification = pageSpecifications[0];
		}
		else {
			throw new UnsupportedOperationException();
		}

		if ((publishedPageSpecification.getExternalReferenceCode() != null) &&
			!Objects.equals(
				sitePage.getExternalReferenceCode(),
				publishedPageSpecification.getExternalReferenceCode())) {

			throw new ValidationException(
				StringBundler.concat(
					"Site page external reference code ",
					sitePage.getExternalReferenceCode(),
					" does not match published page specification external ",
					"reference code ",
					publishedPageSpecification.getExternalReferenceCode()));
		}

		publishedPageSpecification.setExternalReferenceCode(
			sitePage::getExternalReferenceCode);
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

	private static final EntityModel _entityModel = new SitePageEntityModel();

	@Reference
	private CETManager _cetManager;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

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