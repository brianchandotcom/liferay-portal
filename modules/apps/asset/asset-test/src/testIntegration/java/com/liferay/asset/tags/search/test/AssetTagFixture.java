/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.tags.search.test;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor Fabiano Nazar
 * @author Luan Maoski
 * @author Luca Marques
 */
public class AssetTagFixture {

	public AssetTagFixture(
		AssetTagLocalService assetTagLocalService, Group group, User user) {

		_assetTagLocalService = assetTagLocalService;
		_group = group;
		_user = user;
	}

	public AssetTag createAssetTag() throws Exception {
		long userId = _user.getUserId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), userId);

		AssetTag assetTag = _assetTagLocalService.addTag(
			userId, _group.getGroupId(), RandomTestUtil.randomString(),
			serviceContext);

		_assetTags.add(assetTag);

		return assetTag;
	}

	public List<AssetTag> getAssetTags() {
		return _assetTags;
	}

	private final AssetTagLocalService _assetTagLocalService;
	private final List<AssetTag> _assetTags = new ArrayList<>();
	private final Group _group;
	private final User _user;

}