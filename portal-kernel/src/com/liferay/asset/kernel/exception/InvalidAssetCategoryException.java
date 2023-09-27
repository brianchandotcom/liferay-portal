/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.exception;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Locale;

/**
 * @author Jürgen Kappler
 */
public class InvalidAssetCategoryException extends PortalException {

	public static final int CANNOT_MOVE_INTO_CHILD_CATEGORY = 1;

	public static final int CANNOT_MOVE_INTO_ITSELF = 2;

	public InvalidAssetCategoryException(long categoryId, int type) {
		_categoryId = categoryId;
		_type = type;
	}

	public long getCategoryId() {
		return _categoryId;
	}

	public String getMessageArgument(Locale locale) {
		try {
			AssetCategory category = AssetCategoryLocalServiceUtil.getCategory(
				_categoryId);

			return category.getTitle(locale);
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}

			return StringPool.BLANK;
		}
	}

	public String getMessageKey() {
		if (_type == CANNOT_MOVE_INTO_CHILD_CATEGORY) {
			return "unable-to-move-category-x-into-one-of-its-children";
		}
		else if (_type == CANNOT_MOVE_INTO_ITSELF) {
			return "unable-to-move-category-x-into-itself";
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		InvalidAssetCategoryException.class);

	private final long _categoryId;
	private final int _type;

}