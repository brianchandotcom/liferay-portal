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

package com.liferay.asset.tags.validator.internal;

import com.liferay.asset.kernel.exception.AssetTagException;
import com.liferay.asset.kernel.validator.AssetEntryValidator;
import com.liferay.message.boards.model.MBDiscussion;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ArrayUtil;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true, property = "model.class.name=*",
	service = AssetEntryValidator.class
)
public class AtLeastOneTagAssetEntryValidator implements AssetEntryValidator {

	@Override
	public void validate(
			long groupId, String className, long classTypePK,
			long[] categoryIds, String[] tagNames)
		throws PortalException {

		if (!className.equals(MBDiscussion.class.getName()) &&
			!className.equals(Layout.class.getName()) &&
			!className.equals(User.class.getName()) &&
			ArrayUtil.isEmpty(tagNames)) {

			throw new AssetTagException(AssetTagException.AT_LEAST_ONE_TAG);
		}
	}

}