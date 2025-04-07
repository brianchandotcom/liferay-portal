/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.converter;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.headless.admin.site.dto.v1_0.ItemExternalReference;
import com.liferay.headless.admin.site.dto.v1_0.MasterPage;
import com.liferay.headless.admin.site.dto.v1_0.Scope;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.List;
import java.util.function.Function;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	property = "dto.class.name=com.liferay.layout.page.template.model.LayoutPageTemplateEntry",
	service = DTOConverter.class
)
public class MasterPageDTOConverter
	implements DTOConverter<LayoutPageTemplateEntry, MasterPage> {

	@Override
	public String getContentType() {
		return MasterPage.class.getSimpleName();
	}

	@Override
	public MasterPage toDTO(
			DTOConverterContext dtoConverterContext,
			LayoutPageTemplateEntry layoutPageTemplateEntry)
		throws Exception {

		Layout layout = _layoutLocalService.getLayout(
			layoutPageTemplateEntry.getPlid());

		return new MasterPage() {
			{
				setDateCreated(layoutPageTemplateEntry::getCreateDate);
				setDateModified(layoutPageTemplateEntry::getModifiedDate);
				setDatePublished(layout::getPublishDate);
				setExternalReferenceCode(
					layoutPageTemplateEntry::getExternalReferenceCode);
				setKey(layoutPageTemplateEntry::getLayoutPageTemplateEntryKey);
				setKeywordItemExternalReferences(
					() -> _getKeywordItemExternalReferences(
						Layout.class.getName(),
						layoutPageTemplateEntry.getPlid(),
						layoutPageTemplateEntry.getGroupId()));
				setMarkedAsDefault(layoutPageTemplateEntry::isDefaultTemplate);
				setName(layoutPageTemplateEntry::getName);
				setTaxonomyCategoryItemExternalReferences(
					() -> _getTaxonomyCategoryItemExternalReferences(
						Layout.class.getName(),
						layoutPageTemplateEntry.getPlid(),
						layoutPageTemplateEntry.getGroupId()));
				setThumbnail(
					() -> {
						if (layoutPageTemplateEntry.getPreviewFileEntryId() <=
								0) {

							return null;
						}

						FileEntry fileEntry =
							_portletFileRepository.getPortletFileEntry(
								layoutPageTemplateEntry.
									getPreviewFileEntryId());

						if (fileEntry == null) {
							return null;
						}

						return new ItemExternalReference() {
							{
								setClassName(() -> FileEntry.class.getName());
								setExternalReferenceCode(
									fileEntry::getExternalReferenceCode);
							}
						};
					});
				setUuid(layoutPageTemplateEntry::getUuid);
			}
		};
	}

	private <T> ItemExternalReference[] _getItemExternalReferences(
		Function<T, String> getExternalReferenceCodeFunction,
		Function<T, Long> getGroupIdFunction, long groupId, List<T> items) {

		if (ListUtil.isEmpty(items)) {
			return new ItemExternalReference[0];
		}

		return TransformUtil.unsafeTransformToArray(
			items,
			item -> new ItemExternalReference() {
				{
					setExternalReferenceCode(
						() -> getExternalReferenceCodeFunction.apply(item));
					setScope(
						() -> _getScope(
							groupId, getGroupIdFunction.apply(item)));
				}
			},
			ItemExternalReference.class);
	}

	private ItemExternalReference[] _getKeywordItemExternalReferences(
		String className, long classPK, long groupId) {

		List<AssetTag> assetTags = _assetTagLocalService.getTags(
			className, classPK);

		return _getItemExternalReferences(
			AssetTag::getExternalReferenceCode, AssetTag::getGroupId, groupId,
			assetTags);
	}

	private Scope _getScope(long groupId, long scopeGroupId) throws Exception {
		if (groupId == scopeGroupId) {
			return null;
		}

		Group group = _groupLocalService.getGroup(scopeGroupId);

		return new Scope() {
			{
				setExternalReferenceCode(group::getExternalReferenceCode);
				setType(
					() -> {
						if (group.getType() == GroupConstants.TYPE_DEPOT) {
							return Scope.Type.ASSET_LIBRARY;
						}

						return Scope.Type.SITE;
					});
			}
		};
	}

	private ItemExternalReference[] _getTaxonomyCategoryItemExternalReferences(
		String className, long classPK, long groupId) {

		List<AssetCategory> assetCategories =
			_assetCategoryLocalService.getCategories(className, classPK);

		return _getItemExternalReferences(
			AssetCategory::getExternalReferenceCode, AssetCategory::getGroupId,
			groupId, assetCategories);
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private PortletFileRepository _portletFileRepository;

}