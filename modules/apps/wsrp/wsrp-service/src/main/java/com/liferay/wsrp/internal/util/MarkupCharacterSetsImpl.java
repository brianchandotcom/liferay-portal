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

package com.liferay.wsrp.internal.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.wsrp.util.MarkupCharacterSetsHelper;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(immediate = true)
public class MarkupCharacterSetsImpl implements MarkupCharacterSetsHelper {

	@Override
	public String getSupportedMarkupCharacterSets(String markupCharacterSets) {
		if (Validator.isNull(markupCharacterSets)) {
			return null;
		}

		if (!markupCharacterSets.contains(StringPool.UTF8)) {
			markupCharacterSets = markupCharacterSets.concat(
				StringPool.COMMA).concat(StringPool.UTF8);
		}

		return markupCharacterSets;
	}

}