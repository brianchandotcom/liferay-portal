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

package com.liferay.chat.web.internal.util;

import com.liferay.chat.util.BuddyFinder;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Ankit Srivastava
 * @author Peter Fellwock
 */
@Component(enabled = false, immediate = true, service = BuddyFinderUtil.class)
public class BuddyFinderUtil {

	public static List<Object[]> getBuddies(long companyId, long userId) {
		return getBuddyFinder().getBuddies(companyId, userId);
	}

	public static BuddyFinder getBuddyFinder() {
		return _buddyFinder;
	}

	@Reference(unbind = "-")
	protected void setBuddyFinder(BuddyFinder buddyFinder) {
		_buddyFinder = buddyFinder;
	}

	private static BuddyFinder _buddyFinder;

}