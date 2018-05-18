/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.item.selector.web.internal.search;

import com.liferay.commerce.model.CommerceWarehouse;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Set;

import javax.portlet.PortletResponse;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceWarehouseChecker extends EmptyOnClickRowChecker {

	public CommerceWarehouseChecker(
		PortletResponse portletResponse, long[] checkedCommerceWarehouseIds,
		long[] disabledCommerceWarehouseIds) {

		super(portletResponse);

		_checkedCommerceWarehouseIds = SetUtil.fromArray(
			checkedCommerceWarehouseIds);
		_disabledCommerceWarehouseIds = SetUtil.fromArray(
			disabledCommerceWarehouseIds);
	}

	@Override
	public boolean isChecked(Object obj) {
		CommerceWarehouse commerceWarehouse = (CommerceWarehouse)obj;

		return _checkedCommerceWarehouseIds.contains(
			commerceWarehouse.getCommerceWarehouseId());
	}

	@Override
	public boolean isDisabled(Object obj) {
		CommerceWarehouse commerceWarehouse = (CommerceWarehouse)obj;

		return _disabledCommerceWarehouseIds.contains(
			commerceWarehouse.getCommerceWarehouseId());
	}

	private final Set<Long> _checkedCommerceWarehouseIds;
	private final Set<Long> _disabledCommerceWarehouseIds;

}