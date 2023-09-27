/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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