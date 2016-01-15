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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portlet.asset.exception.AssetTagException;
import com.liferay.portlet.messageboards.model.MBDiscussion;

/**
 * @author Brian Wing Shun Chan
 */
public class MinimalAssetEntryValidator extends BaseAssetEntryValidator {

	@Override
	public void validate(
			long groupId, String className, long classTypePK,
			long[] categoryIds, String[] tagNames)
		throws PortalException {

		if (!className.equals(MBDiscussion.class.getName()) &&
			ArrayUtil.isEmpty(tagNames)) {

			throw new AssetTagException(AssetTagException.AT_LEAST_ONE_TAG);
		}
	}

}