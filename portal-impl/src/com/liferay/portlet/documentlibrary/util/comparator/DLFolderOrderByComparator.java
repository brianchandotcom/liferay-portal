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

import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorAdapter;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;

/**
 * @author William Newbury
 */
public class DLFolderOrderByComparator
	extends OrderByComparatorAdapter<DLFolder, Folder> {

	public static OrderByComparator<DLFolder> getOrderByComparator(
		OrderByComparator<Folder> orderByComparator) {

		if (orderByComparator == null) {
			return null;
		}

		return new DLFolderOrderByComparator(orderByComparator);
	}

	@Override
	public Folder adapt(DLFolder dlFolder) {
		return new LiferayFolder(dlFolder);
	}

	private DLFolderOrderByComparator(
		OrderByComparator<Folder> orderByComparator) {

		super(orderByComparator);
	}

}