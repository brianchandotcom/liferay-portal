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

package com.liferay.contacts.uad.test;

import com.liferay.contacts.model.Entry;
import com.liferay.contacts.service.EntryLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author William Newbury
 */
@Component(immediate = true, service = EntryUADEntityTestHelper.class)
public class EntryUADEntityTestHelper {

	public Entry addEntry(long userId) throws Exception {
		return _entryLocalService.addEntry(
			userId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString() + "@liferay.com",
			RandomTestUtil.randomString());
	}

	@Reference
	private EntryLocalService _entryLocalService;

}