/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.internal.model.listener;

import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.asset.list.service.AssetListEntrySegmentsEntryRelLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;

import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Joshua Cords
 * @author Jürgen Kappler
 */
@Component(service = ModelListener.class)
public class GroupModelListener extends BaseModelListener<Group> {

	@Override
	public void onBeforeRemove(Group group) throws ModelListenerException {
		_deleteAssetListEntries(group.getGroupId());
		_updateTypeSettings(group.getGroupId());
	}

	private void _deleteAssetListEntries(long groupId) {
		try {
			List<AssetListEntry> assetListEntries =
				_assetListEntryLocalService.getAssetListEntries(groupId);

			for (AssetListEntry assetListEntry : assetListEntries) {
				_assetListEntryLocalService.deleteAssetListEntry(
					assetListEntry);
			}
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	private void _updateTypeSettings(
		AssetListEntry assetListEntry, String groupId, long segmentsEntryId) {

		UnicodeProperties unicodeProperties = UnicodePropertiesBuilder.create(
			true
		).fastLoad(
			assetListEntry.getTypeSettings(segmentsEntryId)
		).build();

		String[] groupIds = StringUtil.split(
			unicodeProperties.getProperty("groupIds", StringPool.BLANK));

		if (ArrayUtil.contains(groupIds, groupId)) {
			unicodeProperties.setProperty(
				"groupIds",
				StringUtil.merge(ArrayUtil.remove(groupIds, groupId)));

			_assetListEntrySegmentsEntryRelLocalService.
				updateAssetListEntrySegmentsEntryRelTypeSettings(
					assetListEntry.getAssetListEntryId(), segmentsEntryId,
					unicodeProperties.toString());
		}
	}

	private void _updateTypeSettings(long groupId) {
		try {
			List<AssetListEntry> assetListEntries =
				_assetListEntryLocalService.getAssetListEntries(
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			for (AssetListEntry assetListEntry : assetListEntries) {
				if (Objects.equals(
						assetListEntry.getType(),
						AssetListEntryTypeConstants.TYPE_MANUAL)) {

					continue;
				}

				long[] segmentsEntryIds = ArrayUtil.toLongArray(
					TransformUtil.transform(
						_assetListEntrySegmentsEntryRelLocalService.
							getAssetListEntrySegmentsEntryRels(
								assetListEntry.getAssetListEntryId(),
								QueryUtil.ALL_POS, QueryUtil.ALL_POS),
						assetListEntrySegmentsEntryRel ->
							assetListEntrySegmentsEntryRel.
								getSegmentsEntryId()));

				for (long segmentsEntryId : segmentsEntryIds) {
					_updateTypeSettings(
						assetListEntry, String.valueOf(groupId),
						segmentsEntryId);
				}
			}
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	@Reference
	private AssetListEntryLocalService _assetListEntryLocalService;

	@Reference
	private AssetListEntrySegmentsEntryRelLocalService
		_assetListEntrySegmentsEntryRelLocalService;

}