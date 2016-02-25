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

package com.liferay.portal.kernel.util;

/**
 * @author     Brian Wing Shun Chan
 * @deprecated
 */
@Deprecated
public class Randomizer_IW {

	public static Randomizer_IW getInstance() {
		return _instance;
	}

	public com.liferay.portal.kernel.util.Randomizer getWrappedInstance() {
		return Randomizer.getInstance();
	}

	private Randomizer_IW() {
	}

	private static Randomizer_IW _instance = new Randomizer_IW();

}