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

import com.liferay.portal.LayoutFriendlyURLException;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.impl.LayoutImpl;

/**
 * @author Shuyang Zhou
 */
public class FriendlyURLRandomizerBumper implements RandomizerBumper<String> {

	public static final FriendlyURLRandomizerBumper INSTANCE =
		new FriendlyURLRandomizerBumper();

	@Override
	public boolean accept(String randomValue) {
		if ((randomValue == null) || randomValue.isEmpty()) {
			return false;
		}

		if (randomValue.charAt(0) != CharPool.SLASH) {
			randomValue = StringPool.SLASH.concat(randomValue);
		}

		if (LayoutImpl.validateFriendlyURL(randomValue) != -1) {
			return false;
		}

		try {
			LayoutImpl.validateFriendlyURLKeyword(randomValue);

			return true;
		}
		catch (LayoutFriendlyURLException lfurle) {
			return false;
		}
	}

}