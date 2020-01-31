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

package com.liferay.item.selector;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alejandro Tardín
 */
public interface ItemSelectorViewDescriptor<T> {

	public ItemDescriptor getItemDescriptor(T t);

	public ItemSelectorReturnType getItemSelectorReturnType();

	public default String[] getOrderByKeys() {
		return null;
	}

	public SearchContainer getSearchContainer() throws PortalException;

	public default boolean isShowBreadcrumb() {
		return true;
	}

	public default boolean isShowManagementToolbar() {
		return true;
	}

	public interface ItemDescriptor {

		public String getIcon();

		public String getImageURL();

		public String getPayload();

		public String getSubtitle();

		public String getTitle();

		public default boolean isCompact() {
			return false;
		}

	}

}