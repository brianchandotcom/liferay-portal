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

package com.liferay.info.provider;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;

import java.util.Optional;

/**
 * @author Jorge Ferrer
 */
public class SimpleInfoListProviderContext implements InfoListProviderContext {

	public SimpleInfoListProviderContext(User user, Group scopeGroup) {
		_user = user;
		_scopeGroup = scopeGroup;
	}

	@Override
	public Optional<AssetEntry> getAssetEntryOptional() {
		return Optional.ofNullable(_assetEntry);
	}

	@Override
	public Optional<Layout> getLayout() {
		return Optional.of(_layout);
	}

	@Override
	public Group getScopeGroup() {
		return _scopeGroup;
	}

	@Override
	public User getUser() {
		return _user;
	}

	public void setAssetEntry(AssetEntry assetEntry) {
		_assetEntry = assetEntry;
	}

	public void setLayout(Layout layout) {
		_layout = layout;
	}

	private AssetEntry _assetEntry;
	private Layout _layout;
	private final Group _scopeGroup;
	private final User _user;

}