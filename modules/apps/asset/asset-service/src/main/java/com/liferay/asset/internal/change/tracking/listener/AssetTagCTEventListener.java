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

package com.liferay.asset.internal.change.tracking.listener;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.change.tracking.exception.CTEventException;
import com.liferay.change.tracking.listener.CTEventListener;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.petra.lang.SafeClosable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = CTEventListener.class)
public class AssetTagCTEventListener implements CTEventListener {

	@Override
	public void onAfterCopy(
			CTCollection sourceCTCollection, CTCollection targetCTCollection)
		throws CTEventException {

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					targetCTCollection.getCtCollectionId())) {

			_recalculateAssetCount(targetCTCollection.getCtCollectionId());
		}
	}

	@Override
	public void onBeforePublish(long ctCollectionId) throws CTEventException {
		_recalculateAssetCount(ctCollectionId);
	}

	private void _recalculateAssetCount(long ctCollectionId)
		throws CTEventException {

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select distinct tagId from AssetEntries_AssetTags where " +
					"ctCollectionId = " + ctCollectionId);
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long tagId = resultSet.getLong(1);

				AssetTag assetTag = _assetTagLocalService.fetchAssetTag(tagId);

				if (assetTag == null) {
					continue;
				}

				int count =
					_assetEntryLocalService.getAssetTagAssetEntriesCount(tagId);

				if (assetTag.getAssetCount() != count) {
					assetTag.setAssetCount(count);

					_assetTagLocalService.updateAssetTag(assetTag);
				}
			}
		}
		catch (SQLException sqlException) {
			throw new CTEventException(sqlException);
		}
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

}