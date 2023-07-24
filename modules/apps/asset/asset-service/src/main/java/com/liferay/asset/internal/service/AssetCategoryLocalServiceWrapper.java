/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.internal.service;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.portal.kernel.service.ServiceWrapper;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 */
@Component(service = ServiceWrapper.class)
public class AssetCategoryLocalServiceWrapper
	extends com.liferay.asset.kernel.service.AssetCategoryLocalServiceWrapper {

	@Override
	public List<AssetCategory> getCategories(
		long classNameId, long classPK, int start, int end) {

		return super.getCategories(classNameId, classPK, start, end);
	}

	@Override
	public int getCategoriesCount(long classNameId, long classPK) {
		return super.getCategoriesCount(classNameId, classPK);
	}

	@Override
	public List<AssetCategory> search(
		long groupId, String name, String[] categoryProperties, int start,
		int end) {

		return super.search(groupId, name, categoryProperties, start, end);
	}

}