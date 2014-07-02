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

package com.liferay.portlet.documentlibrary.util.comparator;

import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;
import com.liferay.portlet.documentlibrary.model.DLFolder;

/**
 *
 * @author wnewbury
 */
public class DLFolderOrderByComparator extends OrderByComparator<DLFolder> {

	public DLFolderOrderByComparator(
		OrderByComparator<Folder> orderByComparator) {

		_orderByComparator = orderByComparator;
	}

	@Override
	public int compare(DLFolder o1, DLFolder o2) {
		return _orderByComparator.compare(toFolder(o1), toFolder(o2));
	}

	protected Folder toFolder(DLFolder dlFolder) {
		return new LiferayFolder(dlFolder);
	}

	private OrderByComparator<Folder> _orderByComparator;

}