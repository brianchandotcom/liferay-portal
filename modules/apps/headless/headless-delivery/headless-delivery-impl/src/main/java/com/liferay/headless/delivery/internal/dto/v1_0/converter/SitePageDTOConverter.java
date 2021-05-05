/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.headless.delivery.internal.dto.v1_0.converter;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.kernel.StorageEngineManager;
import com.liferay.headless.delivery.dto.v1_0.SitePage;
import com.liferay.headless.delivery.dto.v1_0.TaxonomyCategoryBrief;
import com.liferay.headless.delivery.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.delivery.dto.v1_0.util.CustomFieldsUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.AggregateRatingUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.PageSettingsUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.RenderedPageUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.TaxonomyCategoryBriefUtil;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.seo.service.LayoutSEOEntryLocalService;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypeController;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.util.LayoutTypeControllerTracker;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.portal.vulcan.util.TransformUtil;
import com.liferay.ratings.kernel.service.RatingsStatsLocalService;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier de Arcos
 * @author Jürgen Kappler
 */
@Component(
	property = "dto.class.name=com.liferay.portal.kernel.model.Layout",
	service = {DTOConverter.class, SitePageDTOConverter.class}
)
public class SitePageDTOConverter implements DTOConverter<Layout, SitePage> {

	@Override
	public String getContentType() {
		return SitePage.class.getSimpleName();
	}

	@Override
	public SitePage toDTO(
			DTOConverterContext dtoConverterContext, Layout layout)
		throws Exception {

		return new SitePage() {
			{
				actions = dtoConverterContext.getActions();
				aggregateRating = AggregateRatingUtil.toAggregateRating(
					_ratingsStatsLocalService.fetchStats(
						Layout.class.getName(), layout.getPlid()));
				availableLanguages = LocaleUtil.toW3cLanguageIds(
					layout.getAvailableLanguageIds());
				creator = CreatorUtil.toCreator(
					_portal, dtoConverterContext.getUriInfoOptional(),
					_userLocalService.fetchUser(layout.getUserId()));
				customFields = CustomFieldsUtil.toCustomFields(
					dtoConverterContext.isAcceptAllLanguages(),
					Layout.class.getName(), layout.getPlid(),
					layout.getCompanyId(), dtoConverterContext.getLocale());
				dateCreated = layout.getCreateDate();
				dateModified = layout.getModifiedDate();
				datePublished = layout.getPublishDate();
				friendlyUrlPath = layout.getFriendlyURL(
					dtoConverterContext.getLocale());
				friendlyUrlPath_i18n = LocalizedMapUtil.getI18nMap(
					dtoConverterContext.isAcceptAllLanguages(),
					layout.getFriendlyURLMap());
				keywords = ListUtil.toArray(
					_assetTagLocalService.getTags(
						Layout.class.getName(), layout.getPlid()),
					AssetTag.NAME_ACCESSOR);
				pageSettings = PageSettingsUtil.getPageSettings(
					_dlAppService, _dlURLHelper, dtoConverterContext,
					_layoutSEOEntryLocalService, layout, _storageEngineManager);
				renderedPage = RenderedPageUtil.getRenderedPage(
					dtoConverterContext, layout,
					_layoutPageTemplateEntryLocalService, _portal);
				siteId = layout.getGroupId();
				taxonomyCategoryBriefs = TransformUtil.transformToArray(
					_assetCategoryLocalService.getCategories(
						Layout.class.getName(), layout.getPlid()),
					assetCategory ->
						TaxonomyCategoryBriefUtil.toTaxonomyCategoryBrief(
							assetCategory, dtoConverterContext),
					TaxonomyCategoryBrief.class);
				title = layout.getName(dtoConverterContext.getLocale());
				title_i18n = LocalizedMapUtil.getI18nMap(
					dtoConverterContext.isAcceptAllLanguages(),
					layout.getNameMap());
				uuid = layout.getUuid();

				setExperience(
					() -> {
						boolean showSegmentsExperience = GetterUtil.getBoolean(
							dtoConverterContext.getAttribute("showExperience"));

						if (!showSegmentsExperience) {
							return null;
						}

						SegmentsExperience segmentsExperience =
							(SegmentsExperience)
								dtoConverterContext.getAttribute(
									"segmentsExperience");

						return _experienceDTOConverter.toDTO(
							dtoConverterContext, segmentsExperience);
					});
				setPageDefinition(
					() -> {
						boolean embeddedPageDefinition = GetterUtil.getBoolean(
							dtoConverterContext.getAttribute(
								"embeddedPageDefinition"));

						if (!layout.isTypeContent() ||
							!embeddedPageDefinition) {

							return null;
						}

						dtoConverterContext.setAttribute("layout", layout);

						LayoutPageTemplateStructure
							layoutPageTemplateStructure =
								_layoutPageTemplateStructureLocalService.
									fetchLayoutPageTemplateStructure(
										layout.getGroupId(), layout.getPlid());

						if (layoutPageTemplateStructure == null) {
							return null;
						}

						long segmentsExperienceId =
							SegmentsEntryConstants.ID_DEFAULT;

						SegmentsExperience segmentsExperience =
							(SegmentsExperience)
								dtoConverterContext.getAttribute(
									"segmentsExperience");

						if (segmentsExperience != null) {
							segmentsExperienceId =
								segmentsExperience.getSegmentsExperienceId();
						}

						LayoutStructure layoutStructure = LayoutStructure.of(
							layoutPageTemplateStructure.getData(
								segmentsExperienceId));

						return _pageDefinitionDTOConverter.toDTO(
							dtoConverterContext, layoutStructure);
					});
				setPageType(
					() -> {
						LayoutTypeController layoutTypeController =
							LayoutTypeControllerTracker.getLayoutTypeController(
								layout.getType());

						return LanguageUtil.get(
							dtoConverterContext.getHttpServletRequest(),
							ResourceBundleUtil.getBundle(
								"content.Language",
								dtoConverterContext.getLocale(),
								layoutTypeController.getClass()),
							"layout.types." + layout.getType());
					});
			}
		};
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private ExperienceDTOConverter _experienceDTOConverter;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private LayoutSEOEntryLocalService _layoutSEOEntryLocalService;

	@Reference
	private PageDefinitionDTOConverter _pageDefinitionDTOConverter;

	@Reference
	private Portal _portal;

	@Reference
	private RatingsStatsLocalService _ratingsStatsLocalService;

	@Reference
	private SegmentsExperienceService _segmentsExperienceService;

	@Reference
	private StorageEngineManager _storageEngineManager;

	@Reference
	private UserLocalService _userLocalService;

}