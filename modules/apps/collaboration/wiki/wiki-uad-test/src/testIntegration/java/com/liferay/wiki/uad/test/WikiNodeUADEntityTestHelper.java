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

package com.liferay.wiki.uad.test;

import com.liferay.wiki.model.WikiNode;

import org.junit.Assume;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = WikiNodeUADEntityTestHelper.class)
public class WikiNodeUADEntityTestHelper {
	/**
	 * Implement addWikiNode() to enable some UAD tests.
	 *
	 * <p>
	 * Several UAD tests depend on creating one or more valid WikiNodes with a specified user ID in order to execute correctly. Implement addWikiNode() such that it creates a valid WikiNode with the specified user ID value and returns it in order to enable the UAD tests that depend on it.
	 * </p>
	 *
	 */
	public WikiNode addWikiNode(long userId) throws Exception {
		Assume.assumeTrue(false);

		return null;
	}

	/**
	 * Implement addWikiNodeWithStatusByUserId() to enable some UAD tests.
	 *
	 * <p>
	 * Several UAD tests depend on creating one or more valid WikiNodes with specified user ID and status by user ID in order to execute correctly. Implement addWikiNodeWithStatusByUserId() such that it creates a valid WikiNode with the specified user ID and status by user ID values and returns it in order to enable the UAD tests that depend on it.
	 * </p>
	 *
	 */
	public WikiNode addWikiNodeWithStatusByUserId(long userId,
		long statusByUserId) throws Exception {
		Assume.assumeTrue(false);

		return null;
	}
}