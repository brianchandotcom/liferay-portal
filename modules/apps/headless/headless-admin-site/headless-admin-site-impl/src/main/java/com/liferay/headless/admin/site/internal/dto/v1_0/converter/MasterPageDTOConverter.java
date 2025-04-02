/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.converter;

import com.liferay.asset.kernel.model.AssetTag;
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

	private ItemExternalReference[] _getKeywordItemExternalReferences(
			String className, long classPK, long groupId)
		throws Exception {

		List<AssetTag> assetTags = _assetTagLocalService.getTags(
			className, classPK);

		if (ListUtil.isEmpty(assetTags)) {
			return new ItemExternalReference[0];
		}

		return TransformUtil.unsafeTransform(
			assetTags,
			assetTag -> {
				Group group = _groupLocalService.getGroup(
					assetTag.getGroupId());

				ItemExternalReference itemExternalReference =
					new ItemExternalReference();

				if (groupId != group.getGroupId()) {
					Scope scope = new Scope();

					scope.setType(
						() -> {
							if (group.getType() == GroupConstants.TYPE_DEPOT) {
								return Scope.Type.ASSET_LIBRARY;
							}

							return Scope.Type.SITE;
						});
					scope.setExternalReferenceCode(
						group::getExternalReferenceCode);

					itemExternalReference.setScope(() -> scope);
				}

				itemExternalReference.setClassName(AssetTag.class::getName);
				itemExternalReference.setExternalReferenceCode(
					assetTag::getExternalReferenceCode);

				return itemExternalReference;
			}
		).toArray(
			new ItemExternalReference[0]
		);
	}

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private PortletFileRepository _portletFileRepository;

}