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

package com.liferay.meris.asset.category.demo.internal;

import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.meris.MerisProfileManager;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Garcia
 */
@Component(immediate = true, service = MerisProfileManager.class)
public class AssetCategoryMerisProfileManager
	implements MerisProfileManager<AssetCategoryMerisProfile> {

	@Override
	public AssetCategoryMerisProfile getMerisProfile(String merisProfileId) {
		long userId = GetterUtil.getLong(merisProfileId);

		User user = _userLocalService.fetchUser(userId);

		if (user != null) {
			long[] assetCategoryIds = _assetCategoryLocalService.getCategoryIds(
				user.getModelClassName(), userId);

			return new AssetCategoryMerisProfile(user, assetCategoryIds);
		}

		return null;
	}

	@Override
	public List<AssetCategoryMerisProfile> getMerisProfiles(
		int start, int end, Comparator<AssetCategoryMerisProfile> comparator) {

		List<User> users = _userLocalService.getUsers(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Stream<User> stream = users.stream();

		return stream.map(
			user -> getMerisProfile(String.valueOf(user.getUserId()))
		).collect(
			Collectors.collectingAndThen(
				Collectors.toList(),
				list -> {
					list.sort(comparator);

					return ListUtil.subList(list, start, end);
				})
		);
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}