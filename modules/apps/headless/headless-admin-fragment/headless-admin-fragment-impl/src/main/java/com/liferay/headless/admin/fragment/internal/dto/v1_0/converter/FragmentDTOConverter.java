/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.fragment.internal.dto.v1_0.converter;

import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.headless.admin.fragment.dto.v1_0.Fragment;
import com.liferay.headless.admin.fragment.dto.v1_0.FragmentSet;
import com.liferay.headless.admin.fragment.dto.v1_0.FragmentVersion;
import com.liferay.headless.admin.site.dto.v1_0.util.ThumbnailURLReferenceUtil;
import com.liferay.headless.admin.user.dto.v1_0.Creator;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedFieldsSupplier;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(
	property = "dto.class.name=com.liferay.fragment.model.FragmentEntry",
	service = DTOConverter.class
)
public class FragmentDTOConverter
	implements DTOConverter<FragmentEntry, Fragment> {

	@Override
	public String getContentType() {
		return Fragment.class.getSimpleName();
	}

	@Override
	public Fragment toDTO(
		DTOConverterContext dtoConverterContext, FragmentEntry fragmentEntry) {

		FragmentEntry draftOnlyOrPublishedFragmentEntry =
			_fetchDraftOnlyOrPublishedFragmentEntry(fragmentEntry);

		if (draftOnlyOrPublishedFragmentEntry == null) {
			throw new IllegalStateException(
				StringBundler.concat(
					"Fragment entry draft with ID ",
					fragmentEntry.getFragmentEntryId(), " references head ID ",
					fragmentEntry.getHeadId(), " that no longer exists"));
		}

		return _toFragment(draftOnlyOrPublishedFragmentEntry);
	}

	private FragmentEntry _fetchDraftOnlyOrPublishedFragmentEntry(
		FragmentEntry fragmentEntry) {

		if (fragmentEntry.isHead()) {
			return fragmentEntry;
		}

		return _fragmentEntryLocalService.fetchFragmentEntry(
			fragmentEntry.getHeadId());
	}

	private Fragment _toFragment(FragmentEntry fragmentEntry) {
		List<FragmentVersion> fragmentVersionList = new ArrayList<>();

		if (fragmentEntry.isHead()) {
			fragmentVersionList.add(
				_toFragmentVersion(
					fragmentEntry, FragmentVersion.Status.APPROVED));
		}

		FragmentEntry draftFragmentEntry =
			_fragmentEntryLocalService.fetchDraft(
				fragmentEntry.getPrimaryKey());

		if (draftFragmentEntry != null) {
			fragmentVersionList.add(
				_toFragmentVersion(
					draftFragmentEntry, FragmentVersion.Status.DRAFT));
		}
		else if (!fragmentEntry.isHead()) {
			fragmentVersionList.add(
				_toFragmentVersion(
					fragmentEntry, FragmentVersion.Status.DRAFT));
		}

		return new Fragment() {
			{
				setCacheable(fragmentEntry::isCacheable);
				setCreator(
					() -> {
						User user = _userLocalService.fetchUser(
							fragmentEntry.getUserId());

						if (user == null) {
							return null;
						}

						return new Creator() {
							{
								setExternalReferenceCode(
									user::getExternalReferenceCode);
							}
						};
					});
				setDateCreated(fragmentEntry::getCreateDate);
				setDateModified(fragmentEntry::getModifiedDate);
				setExternalReferenceCode(
					fragmentEntry::getExternalReferenceCode);
				setFragmentSet(
					() -> _fragmentSetDTOConverter.toDTO(
						null,
						_fragmentCollectionLocalService.getFragmentCollection(
							fragmentEntry.getFragmentCollectionId())));
				setFragmentVersions(
					() -> fragmentVersionList.toArray(new FragmentVersion[0]));
				setIcon(fragmentEntry::getIcon);
				setKey(fragmentEntry::getFragmentEntryKey);
				setMarketplace(fragmentEntry::isMarketplace);
				setName(fragmentEntry::getName);
				setReadOnly(fragmentEntry::isReadOnly);
				setThumbnailURLReference(
					() -> NestedFieldsSupplier.supply(
						"thumbnailURLReference",
						fieldName ->
							ThumbnailURLReferenceUtil.
								getFileEntryThumbnailURLReference(
									fragmentEntry.getPreviewFileEntryId())));
				setType(
					() -> Fragment.Type.create(
						StringUtil.upperCaseFirstLetter(
							FragmentConstants.getTypeLabel(
								fragmentEntry.getType()))));
			}
		};
	}

	private FragmentVersion _toFragmentVersion(
		FragmentEntry fragmentEntry,
		FragmentVersion.Status fragmentVersionStatus) {

		FragmentVersion fragmentVersion = new FragmentVersion() {
			{
				setStatus(() -> fragmentVersionStatus);
			}
		};

		if (!fragmentEntry.isMarketplace()) {
			fragmentVersion.setConfiguration(fragmentEntry::getConfiguration);
			fragmentVersion.setCss(fragmentEntry::getCss);
			fragmentVersion.setHtml(fragmentEntry::getHtml);
			fragmentVersion.setJs(fragmentEntry::getJs);
		}

		return fragmentVersion;
	}

	@Reference
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Reference
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Reference(
		target = "(component.name=com.liferay.headless.admin.fragment.internal.dto.v1_0.converter.FragmentSetDTOConverter)"
	)
	private DTOConverter<FragmentCollection, FragmentSet>
		_fragmentSetDTOConverter;

	@Reference
	private UserLocalService _userLocalService;

}