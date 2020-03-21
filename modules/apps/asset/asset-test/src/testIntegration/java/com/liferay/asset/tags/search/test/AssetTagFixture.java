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