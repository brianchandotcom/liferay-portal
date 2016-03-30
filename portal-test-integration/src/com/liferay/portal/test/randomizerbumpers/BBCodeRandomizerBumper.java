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

package com.liferay.portal.test.randomizerbumpers;

import com.liferay.portal.kernel.parsers.bbcode.BBCodeTranslatorUtil;
import com.liferay.portal.kernel.test.randomizerbumpers.RandomizerBumper;

/**
 * @author Shuyang Zhou
 */
public class BBCodeRandomizerBumper implements RandomizerBumper<String> {

	public static final BBCodeRandomizerBumper INSTANCE =
		new BBCodeRandomizerBumper();

	@Override
	public boolean accept(String randomValue) {
		if (randomValue.equals(BBCodeTranslatorUtil.getHTML(randomValue))) {
			return true;
		}

		return false;
	}

}