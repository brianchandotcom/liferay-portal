/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryService;
import com.liferay.headless.admin.site.dto.v1_0.ContentPageSpecification;
import com.liferay.headless.admin.site.dto.v1_0.DisplayPageTemplate;
import com.liferay.headless.admin.site.dto.v1_0.ItemExternalReference;
import com.liferay.headless.admin.site.dto.v1_0.MasterPage;
import com.liferay.headless.admin.site.dto.v1_0.PageElement;
import com.liferay.headless.admin.site.dto.v1_0.PageExperience;
import com.liferay.headless.admin.site.dto.v1_0.PageSpecification;
import com.liferay.headless.admin.site.dto.v1_0.PageTemplate;
import com.liferay.headless.admin.site.dto.v1_0.Scope;
import com.liferay.headless.admin.site.dto.v1_0.Settings;
import com.liferay.headless.admin.site.dto.v1_0.SitePage;
import com.liferay.headless.admin.site.dto.v1_0.UtilityPage;
import com.liferay.headless.admin.site.dto.v1_0.WidgetPageSpecification;
import com.liferay.headless.admin.site.internal.resource.util.GroupUtil;
import com.liferay.headless.admin.site.resource.v1_0.PageSpecificationResource;
import com.liferay.headless.common.spi.service.context.ServiceContextBuilder;
import com.liferay.layout.constants.LayoutTypeSettingsConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryService;
import com.liferay.portal.kernel.exception.LockedLayoutException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.ColorScheme;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ColorSchemeFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldId;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceService;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryService;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rubén Pulido
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/page-specification.properties",
	property = "nested.field.support=true", scope = ServiceScope.PROTOTYPE,
	service = PageSpecificationResource.class
)
public class PageSpecificationResourceImpl
	extends BasePageSpecificationResourceImpl {

	@Override
	public void deleteSiteSiteByExternalReferenceCodePageSpecification(
			String siteExternalReferenceCode,
			String pageSpecificationExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Layout layout = _layoutService.getLayoutByExternalReferenceCode(
			pageSpecificationExternalReferenceCode,
			GroupUtil.getGroupId(
				true, contextCompany.getCompanyId(),
				siteExternalReferenceCode));

		if (!layout.isDraftLayout() ||
			(layout.isApproved() &&
			 GetterUtil.getBoolean(
				 layout.getTypeSettingsProperty("published")))) {

			throw new UnsupportedOperationException();
		}

		_discardDraftLayout(layout);
	}

	@NestedField(
		parentClass = DisplayPageTemplate.class, value = "pageSpecifications"
	)
	@Override
	public Page<PageSpecification>
			getSiteSiteByExternalReferenceCodeDisplayPageTemplatePageSpecificationsPage(
				String siteExternalReferenceCode,
				@NestedFieldId(value = "externalReferenceCode") String
					displayPageTemplateExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryService.
				getLayoutPageTemplateEntryByExternalReferenceCode(
					displayPageTemplateExternalReferenceCode,
					GroupUtil.getGroupId(
						true, contextCompany.getCompanyId(),
						siteExternalReferenceCode));

		if (!Objects.equals(
				LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE,
				layoutPageTemplateEntry.getType())) {

			throw new UnsupportedOperationException();
		}

		return Page.of(
			_toPageSpecifications(
				_layoutLocalService.getLayout(
					layoutPageTemplateEntry.getPlid())));
	}

	@NestedField(parentClass = MasterPage.class, value = "pageSpecifications")
	@Override
	public Page<PageSpecification>
			getSiteSiteByExternalReferenceCodeMasterPagePageSpecificationsPage(
				String siteExternalReferenceCode,
				@NestedFieldId(value = "externalReferenceCode") String
					masterPageExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryService.
				getLayoutPageTemplateEntryByExternalReferenceCode(
					masterPageExternalReferenceCode,
					GroupUtil.getGroupId(
						true, contextCompany.getCompanyId(),
						siteExternalReferenceCode));

		if (!Objects.equals(
				LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT,
				layoutPageTemplateEntry.getType())) {

			throw new UnsupportedOperationException();
		}

		return Page.of(
			_toPageSpecifications(
				_layoutLocalService.getLayout(
					layoutPageTemplateEntry.getPlid())));
	}

	@Override
	public PageSpecification
			getSiteSiteByExternalReferenceCodePageSpecification(
				String siteExternalReferenceCode,
				String pageSpecificationExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Layout layout = _layoutService.getLayoutByExternalReferenceCode(
			pageSpecificationExternalReferenceCode,
			GroupUtil.getGroupId(
				true, true, contextCompany.getCompanyId(),
				siteExternalReferenceCode));

		if (!_isPageSpecificationSupported(layout)) {
			throw new UnsupportedOperationException();
		}

		return _pageSpecificationDTOConverter.toDTO(layout);
	}

	@NestedField(parentClass = PageTemplate.class, value = "pageSpecifications")
	@Override
	public Page<PageSpecification>
			getSiteSiteByExternalReferenceCodePageTemplatePageSpecificationsPage(
				String siteExternalReferenceCode,
				@NestedFieldId(value = "externalReferenceCode") String
					pageTemplateExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryService.
				getLayoutPageTemplateEntryByExternalReferenceCode(
					pageTemplateExternalReferenceCode,
					GroupUtil.getGroupId(
						true, true, contextCompany.getCompanyId(),
						siteExternalReferenceCode));

		if (!Objects.equals(
				LayoutPageTemplateEntryTypeConstants.BASIC,
				layoutPageTemplateEntry.getType()) &&
			!Objects.equals(
				LayoutPageTemplateEntryTypeConstants.WIDGET_PAGE,
				layoutPageTemplateEntry.getType())) {

			throw new UnsupportedOperationException();
		}

		return Page.of(
			_toPageSpecifications(
				_layoutLocalService.getLayout(
					layoutPageTemplateEntry.getPlid())));
	}

	@NestedField(parentClass = SitePage.class, value = "pageSpecifications")
	@Override
	public Page<PageSpecification>
			getSiteSiteByExternalReferenceCodeSitePagePageSpecificationsPage(
				String siteExternalReferenceCode,
				@NestedFieldId(value = "externalReferenceCode") String
					sitePageExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Layout layout = _layoutService.getLayoutByExternalReferenceCode(
			sitePageExternalReferenceCode,
			GroupUtil.getGroupId(
				true, contextCompany.getCompanyId(),
				siteExternalReferenceCode));

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

		return Page.of(_toPageSpecifications(layout));
	}

	@NestedField(parentClass = UtilityPage.class, value = "pageSpecifications")
	@Override
	public Page<PageSpecification>
			getSiteSiteByExternalReferenceCodeUtilityPagePageSpecificationsPage(
				String siteExternalReferenceCode,
				@NestedFieldId(value = "externalReferenceCode") String
					utilityPageExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_layoutUtilityPageEntryService.
				getLayoutUtilityPageEntryByExternalReferenceCode(
					utilityPageExternalReferenceCode,
					GroupUtil.getGroupId(
						true, contextCompany.getCompanyId(),
						siteExternalReferenceCode));

		return Page.of(
			_toPageSpecifications(
				_layoutLocalService.getLayout(
					layoutUtilityPageEntry.getPlid())));
	}

	@Override
	public PageSpecification
			putSiteSiteByExternalReferenceCodePageSpecification(
				String siteExternalReferenceCode,
				String pageSpecificationExternalReferenceCode,
				PageSpecification pageSpecification)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Layout layout = _layoutService.getLayoutByExternalReferenceCode(
			pageSpecificationExternalReferenceCode,
			GroupUtil.getGroupId(
				true, true, contextCompany.getCompanyId(),
				siteExternalReferenceCode));

		ServiceContext serviceContext = ServiceContextBuilder.create(
			layout.getGroupId(), contextHttpServletRequest, null
		).build();

		serviceContext.setUserId(contextUser.getUserId());

		if (!layout.isTypeAssetDisplay() && !layout.isTypeContent()) {
			return _updateWidgetPageSpecification(
				layout, (WidgetPageSpecification)pageSpecification,
				serviceContext);
		}

		if (layout.isTypeAssetDisplay() || layout.isTypeUtility()) {
			serviceContext.setAttribute(
				"layout.instanceable.allowed", Boolean.TRUE);
		}

		return _updateContentPageSpecification(
			(ContentPageSpecification)pageSpecification, layout,
			serviceContext);
	}

	private void _addChildPageElements(
		LayoutStructure layoutStructure, PageElement pageElement) {

		if (ArrayUtil.isEmpty(pageElement.getPageElements())) {
			return;
		}

		for (PageElement childPageElement : pageElement.getPageElements()) {
			layoutStructure.addLayoutStructureItem(
				childPageElement.getExternalReferenceCode(),
				_externalToInternalValuesMap.get(childPageElement.getType()),
				pageElement.getExternalReferenceCode(),
				GetterUtil.getInteger(childPageElement.getPosition(), -1));

			_addChildPageElements(layoutStructure, childPageElement);
		}
	}

	private void _discardDraftLayout(Layout draftLayout) throws Exception {
		Layout layout = _layoutLocalService.getLayout(draftLayout.getClassPK());

		try {
			boolean published = layout.isPublished();

			draftLayout = _layoutLocalService.copyLayoutContent(
				layout, draftLayout);

			ServiceContext serviceContext = ServiceContextBuilder.create(
				layout.getGroupId(), contextHttpServletRequest, null
			).build();

			serviceContext.setAttribute(
				LayoutTypeSettingsConstants.KEY_PUBLISHED, published);
			serviceContext.setUserId(contextUser.getUserId());

			_layoutLocalService.updateStatus(
				contextUser.getUserId(), draftLayout.getPlid(),
				WorkflowConstants.STATUS_APPROVED, serviceContext);
		}
		catch (Exception exception) {
			if (!(exception instanceof LockedLayoutException) &&
				!(exception.getCause() instanceof LockedLayoutException)) {

				throw new UnsupportedOperationException();
			}
		}
	}

	private long _getFaviconFileEntryId(Layout layout, Settings settings)
		throws Exception {

		if ((settings.getFavIcon() == null) ||
			!(settings.getFavIcon() instanceof ItemExternalReference)) {

			return 0;
		}

		ItemExternalReference itemExternalReference =
			(ItemExternalReference)settings.getFavIcon();

		long groupId = layout.getGroupId();

		Scope scope = itemExternalReference.getScope();

		if (scope != null) {
			groupId = GroupUtil.getGroupId(
				true, true, layout.getCompanyId(),
				scope.getExternalReferenceCode());
		}

		DLFileEntry dlFileEntry =
			_dlFileEntryService.fetchFileEntryByExternalReferenceCode(
				groupId, itemExternalReference.getExternalReferenceCode());

		if (dlFileEntry == null) {
			throw new UnsupportedOperationException();
		}

		return dlFileEntry.getFileEntryId();
	}

	private long _getMasterLayoutPlid(Layout layout, Settings settings)
		throws Exception {

		if (settings.getMasterPageItemExternalReference() == null) {
			return 0;
		}

		ItemExternalReference itemExternalReference =
			settings.getMasterPageItemExternalReference();

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryService.
				fetchLayoutPageTemplateEntryByExternalReferenceCode(
					itemExternalReference.getExternalReferenceCode(),
					layout.getGroupId());

		if (layoutPageTemplateEntry == null) {
			throw new UnsupportedOperationException();
		}

		return layoutPageTemplateEntry.getPlid();
	}

	private long _getStyleBookEntryId(Layout layout, Settings settings)
		throws Exception {

		if (settings.getStyleBookItemExternalReference() == null) {
			return 0;
		}

		ItemExternalReference itemExternalReference =
			settings.getStyleBookItemExternalReference();

		StyleBookEntry styleBookEntry =
			_styleBookEntryService.getStyleBookEntryByExternalReferenceCode(
				itemExternalReference.getExternalReferenceCode(),
				layout.getGroupId());

		return styleBookEntry.getStyleBookEntryId();
	}

	private boolean _isPageSpecificationSupported(Layout layout) {
		if (_isPublished(layout)) {
			if (!layout.isApproved() || !layout.isDraftLayout()) {
				return true;
			}

			return false;
		}

		if (layout.isDraftLayout()) {
			return true;
		}

		return false;
	}

	private boolean _isPublished(Layout layout) {
		if (!layout.isTypeAssetDisplay() && !layout.isTypeContent()) {
			return true;
		}

		if (layout.isDraftLayout()) {
			return GetterUtil.getBoolean(
				layout.getTypeSettingsProperty("published"));
		}

		Layout draftLayout = layout.fetchDraftLayout();

		return GetterUtil.getBoolean(
			draftLayout.getTypeSettingsProperty("published"));
	}

	private List<PageSpecification> _toPageSpecifications(Layout layout)
		throws Exception {

		Layout draftLayout = layout.fetchDraftLayout();

		if (_isPublished(layout)) {
			if ((draftLayout != null) && !draftLayout.isApproved()) {
				return ListUtil.fromArray(
					_pageSpecificationDTOConverter.toDTO(layout),
					_pageSpecificationDTOConverter.toDTO(draftLayout));
			}

			return ListUtil.fromArray(
				_pageSpecificationDTOConverter.toDTO(layout));
		}

		if (draftLayout == null) {
			throw new UnsupportedOperationException();
		}

		return ListUtil.fromArray(
			_pageSpecificationDTOConverter.toDTO(draftLayout));
	}

	private PageSpecification _updateContentPageSpecification(
			ContentPageSpecification contentPageSpecification, Layout layout,
			ServiceContext serviceContext)
		throws Exception {

		_updatePageExperiences(
			layout, contentPageSpecification.getPageExperiences());

		_updateSettings(
			layout, contentPageSpecification.getSettings(), serviceContext);

		return _pageSpecificationDTOConverter.toDTO(
			_layoutLocalService.updateStatus(
				contextUser.getUserId(), layout.getPlid(),
				WorkflowConstants.STATUS_DRAFT, serviceContext));
	}

	private void _updateLayoutPageTemplateStructureData(
			Layout layout, PageExperience pageExperience,
			SegmentsExperience segmentsExperience)
		throws Exception {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(), layout.getPlid());

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(
				segmentsExperience.getSegmentsExperienceId()));

		LayoutStructure newLayoutStructure = new LayoutStructure();

		newLayoutStructure.addRootLayoutStructureItem(
			layoutStructure.getMainItemId());

		for (PageElement pageElement : pageExperience.getPageElements()) {
			newLayoutStructure.addLayoutStructureItem(
				pageElement.getExternalReferenceCode(),
				_externalToInternalValuesMap.get(pageElement.getType()),
				layoutStructure.getMainItemId(),
				GetterUtil.getInteger(pageElement.getPosition(), -1));

			_addChildPageElements(newLayoutStructure, pageElement);
		}

		_layoutPageTemplateStructureLocalService.
			updateLayoutPageTemplateStructureData(
				layoutPageTemplateStructure.getGroupId(),
				layoutPageTemplateStructure.getPlid(),
				segmentsExperience.getSegmentsExperienceId(),
				newLayoutStructure.toString());
	}

	private Layout _updateLookAndFeel(Layout layout, Settings settings)
		throws Exception {

		UnicodeProperties unicodeProperties =
			layout.getTypeSettingsProperties();

		unicodeProperties.setProperty("javascript", settings.getJavascript());

		if (settings.getThemeSettings() != null) {
			for (String key :
					ListUtil.fromCollection(unicodeProperties.keySet())) {

				if (key.startsWith("lfr-theme:")) {
					unicodeProperties.remove(key);
				}
			}

			unicodeProperties.putAll(
				(Map<String, ? extends String>)settings.getThemeSettings());
		}

		layout = _layoutService.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			unicodeProperties.toString());

		Theme theme = null;
		String themeId = null;

		if (Validator.isNotNull(settings.getThemeName())) {
			for (Theme curTheme :
					_themeLocalService.getThemes(layout.getCompanyId())) {

				if (!Objects.equals(
						curTheme.getName(), settings.getThemeName())) {

					continue;
				}

				theme = curTheme;
				themeId = curTheme.getThemeId();

				break;
			}

			if (themeId == null) {
				throw new UnsupportedOperationException();
			}
		}

		String colorSchemeId = null;

		if (Validator.isNotNull(settings.getColorSchemeName())) {
			if (theme == null) {
				throw new UnsupportedOperationException();
			}

			for (ColorScheme colorScheme : theme.getColorSchemes()) {
				if (!Objects.equals(
						colorScheme.getName(), settings.getColorSchemeName())) {

					continue;
				}

				colorSchemeId = colorScheme.getColorSchemeId();

				break;
			}

			if (colorSchemeId == null) {
				ColorScheme colorScheme =
					ColorSchemeFactoryUtil.getDefaultRegularColorScheme();

				if (Objects.equals(
						colorScheme.getName(), settings.getColorSchemeName())) {

					colorSchemeId = colorScheme.getColorSchemeId();
				}
			}

			if (colorSchemeId == null) {
				throw new UnsupportedOperationException();
			}
		}

		return _layoutService.updateLookAndFeel(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			themeId, colorSchemeId, settings.getCss());
	}

	private void _updatePageExperiences(
			Layout layout, PageExperience[] pageExperiences)
		throws Exception {

		for (PageExperience pageExperience : pageExperiences) {
			_updateLayoutPageTemplateStructureData(
				layout, pageExperience,
				_segmentsExperienceService.getSegmentsExperience(
					layout.getGroupId(),
					pageExperience.getExternalReferenceCode(),
					layout.getPlid()));
		}
	}

	private Layout _updateSettings(
			Layout layout, Settings settings, ServiceContext serviceContext)
		throws Exception {

		layout = _updateLookAndFeel(layout, settings);

		return _layoutService.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getParentLayoutId(), layout.getNameMap(),
			layout.getTitleMap(), layout.getDescriptionMap(),
			layout.getKeywordsMap(), layout.getRobotsMap(), layout.getType(),
			layout.isHidden(), layout.getFriendlyURLMap(),
			layout.getIconImage(), null, _getStyleBookEntryId(layout, settings),
			_getFaviconFileEntryId(layout, settings),
			_getMasterLayoutPlid(layout, settings), serviceContext);
	}

	private PageSpecification _updateWidgetPageSpecification(
			Layout layout, WidgetPageSpecification widgetPageSpecification,
			ServiceContext serviceContext)
		throws Exception {

		return _pageSpecificationDTOConverter.toDTO(
			_updateSettings(
				layout, widgetPageSpecification.getSettings(), serviceContext));
	}

	private static final Map<PageElement.Type, String>
		_externalToInternalValuesMap = HashMapBuilder.put(
			PageElement.Type.COLLECTION,
			LayoutDataItemTypeConstants.TYPE_COLLECTION
		).put(
			PageElement.Type.COLLECTION_ITEM,
			LayoutDataItemTypeConstants.TYPE_COLLECTION_ITEM
		).put(
			PageElement.Type.COLUMN, LayoutDataItemTypeConstants.TYPE_COLUMN
		).put(
			PageElement.Type.CONTAINER,
			LayoutDataItemTypeConstants.TYPE_CONTAINER
		).put(
			PageElement.Type.DROP_ZONE,
			LayoutDataItemTypeConstants.TYPE_DROP_ZONE
		).put(
			PageElement.Type.FORM, LayoutDataItemTypeConstants.TYPE_FORM
		).put(
			PageElement.Type.FRAGMENT, LayoutDataItemTypeConstants.TYPE_FRAGMENT
		).put(
			PageElement.Type.FRAGMENT_DROP_ZONE,
			LayoutDataItemTypeConstants.TYPE_FRAGMENT_DROP_ZONE
		).put(
			PageElement.Type.ROW, LayoutDataItemTypeConstants.TYPE_ROW
		).build();

	@Reference
	private DLFileEntryService _dlFileEntryService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private LayoutService _layoutService;

	@Reference
	private LayoutUtilityPageEntryService _layoutUtilityPageEntryService;

	@Reference(
		target = "(component.name=com.liferay.headless.admin.site.internal.dto.v1_0.converter.PageSpecificationDTOConverter)"
	)
	private DTOConverter<Layout, PageSpecification>
		_pageSpecificationDTOConverter;

	@Reference
	private SegmentsExperienceService _segmentsExperienceService;

	@Reference
	private StyleBookEntryService _styleBookEntryService;

	@Reference
	private ThemeLocalService _themeLocalService;

}