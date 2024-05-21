/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.graphql.servlet.test.v2_0;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;

import java.util.Map;

/**
 * @author Carlos Correa
 */
public class TestDTO {

	public String getExtendedString() {
		return _EXTENDED_STRING;
	}

	public long getId() {
		return id;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public String getString() {
		return string;
	}

	public double getVersion() {
		return version;
	}

	@GraphQLField
	protected long id = RandomTestUtil.randomLong();

	@GraphQLField
	protected Map<String, String> map = HashMapBuilder.put(
		"a" + RandomTestUtil.randomString(), RandomTestUtil.randomString()
	).put(
		"a" + RandomTestUtil.randomString(), RandomTestUtil.randomString()
	).build();

	@GraphQLField
	protected String string = RandomTestUtil.randomString();

	@GraphQLField
	protected int version = 2;

	private static final String _EXTENDED_STRING =
		RandomTestUtil.randomString();

}