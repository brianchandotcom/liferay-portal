/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.converter;

import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.headless.admin.site.dto.v1_0.ClassNameReference;
import com.liferay.headless.admin.site.dto.v1_0.CollectionPageSettings;
import com.liferay.headless.admin.site.dto.v1_0.CollectionReference;
import com.liferay.headless.admin.site.dto.v1_0.ContentPageSettings;
import com.liferay.headless.admin.site.dto.v1_0.ItemExternalReference;
import com.liferay.headless.admin.site.dto.v1_0.PageSettings;
import com.liferay.headless.admin.site.dto.v1_0.Scope;
import com.liferay.headless.admin.site.dto.v1_0.SitePage;
import com.liferay.headless.admin.site.dto.v1_0.WidgetPageSettings;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.SitePageTypeUtil;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(
	property = "dto.class.name=com.liferay.portal.kernel.model.Layout",
	service = DTOConverter.class
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
				setAvailableLanguages(
					() -> LocaleUtil.toW3cLanguageIds(
						layout.getAvailableLanguageIds()));
				setDateCreated(layout::getCreateDate);
				setDateModified(layout::getModifiedDate);
				setDatePublished(layout::getPublishDate);
				setExternalReferenceCode(layout::getExternalReferenceCode);
				setFriendlyUrlPath_i18n(
					() -> LocalizedMapUtil.getI18nMap(
						true, layout.getFriendlyURLMap()));
				setName_i18n(
					() -> LocalizedMapUtil.getI18nMap(
						true, layout.getNameMap()));
				setPageSettings(() -> _toPageSettings(layout));
				setType(
					() -> SitePageTypeUtil.toExternalType(layout.getType()));
				setUuid(layout::getUuid);
			}
		};
	}

	private CollectionReference _getCollectionReference(Layout layout) {
		String collectionType = layout.getTypeSettingsProperty(
			"collectionType");

		if (Objects.equals(
				collectionType,
				InfoListItemSelectorReturnType.class.getName())) {

			AssetListEntry assetListEntry =
				_assetListEntryLocalService.fetchAssetListEntry(
					GetterUtil.getLong(
						layout.getTypeSettingsProperty("collectionPK")));

			if (assetListEntry == null) {
				return null;
			}

			return new ItemExternalReference() {
				{
					setClassName(() -> AssetListEntry.class.getName());
					setCollectionType(() -> CollectionType.COLLECTION);
					setExternalReferenceCode(
						assetListEntry::getExternalReferenceCode);
					setScope(
						() -> {
							if (assetListEntry.getGroupId() ==
									layout.getGroupId()) {

								return null;
							}

							Scope scope = new Scope();

							Group group = _groupLocalService.getGroup(
								assetListEntry.getGroupId());

							scope.setExternalReferenceCode(
								group::getExternalReferenceCode);
							scope.setType(
								() -> {
									if (group.isDepot()) {
										return Scope.Type.ASSET_LIBRARY;
									}

									return Scope.Type.SITE;
								});

							return scope;
						});
				}
			};
		}

		if (Objects.equals(
				collectionType,
				InfoListProviderItemSelectorReturnType.class.getName())) {

			return new ClassNameReference() {
				{
					setClassName(
						() -> layout.getTypeSettingsProperty("collectionPK"));

					setCollectionType(
						() ->
							CollectionReference.CollectionType.
								COLLECTION_PROVIDER);
				}
			};
		}

		return null;
	}

	private PageSettings _getPageSettings(Layout layout) {
		SitePage.Type type = SitePageTypeUtil.toExternalType(layout.getType());

		if (type == SitePage.Type.COLLECTION_PAGE) {
			return _toCollectionPageSettings(layout);
		}

		if (type == SitePage.Type.CONTENT_PAGE) {
			return new ContentPageSettings();
		}

		return _toWidgetPageSettings(layout);
	}

	private CollectionPageSettings _toCollectionPageSettings(Layout layout) {
		CollectionPageSettings collectionPageSettings =
			new CollectionPageSettings();

		collectionPageSettings.setCollectionReference(
			() -> _getCollectionReference(layout));

		return collectionPageSettings;
	}

	private PageSettings _toPageSettings(Layout layout) {
		PageSettings pageSettings = _getPageSettings(layout);

		pageSettings.setHiddenFromNavigation(layout::isHidden);

		return pageSettings;
	}

	private WidgetPageSettings _toWidgetPageSettings(Layout layout) {
		WidgetPageSettings widgetPageSettings = new WidgetPageSettings();

		widgetPageSettings.setLayoutTemplateId(
			() -> layout.getTypeSettingsProperty(
				LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID));

		return widgetPageSettings;
	}

	@Reference
	private AssetListEntryLocalService _assetListEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

}