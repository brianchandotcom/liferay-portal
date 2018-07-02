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

package com.liferay.portlet.asset.util;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author     Brian Wing Shun Chan
 * @deprecated As of Wilberforce (7.0.x), with no direct replacement
 */
@Deprecated
public class BaseAssetEntryValidator implements AssetEntryValidator {

	/**
	 * @throws PortalException
	 */
	@Override
	public void validate(
			long groupId, String className, long classTypePK,
			long[] categoryIds, String[] entryNames)
		throws PortalException {
	}

	protected boolean isAssetCategorizable(long classNameId) {
		return true;
	}

	/**
	 * @throws PortalException
	 */
	protected void validate(
			long classNameId, long classTypePK, final long[] categoryIds,
			AssetVocabulary vocabulary)
		throws PortalException {
	}

}