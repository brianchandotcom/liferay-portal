/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ratings.taglib.internal.className.provider;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;

/**
 * @author Roberto Díaz
 */
public class RatingsClassNameProviderUtil {

	public static String getRatingsClassName(String className) {
		if (className.equals(DLFileEntry.class.getName()) ||
			className.equals(FileEntry.class.getName()) ||
			className.equals(LiferayFileEntry.class.getName())) {

			return DLFileEntryConstants.getClassName();
		}

		return className;
	}

}