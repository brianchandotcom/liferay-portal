/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.graphql.servlet.test.v1_0;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;

import java.util.Map;

/**
 * @author Carlos Correa
 */
public class TestDTO {

	public TestDTO() {
		this(
			RandomTestUtil.randomString(), RandomTestUtil.randomLong(),
			HashMapBuilder.put(
				"a" + RandomTestUtil.randomString(),
				RandomTestUtil.randomString()
			).put(
				"a" + RandomTestUtil.randomString(),
				RandomTestUtil.randomString()
			).build(),
			RandomTestUtil.randomString());
	}

	public TestDTO(
		String extendedString, long id, Map<String, String> map,
		String string) {

		_extendedString = extendedString;

		this.id = id;
		this.map = map;
		this.string = string;
	}

	public String getExtendedString() {
		return _extendedString;
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
	protected long id;

	@GraphQLField
	protected Map<String, String> map;

	@GraphQLField
	protected String string;

	@GraphQLField
	protected int version = 1;

	private String _extendedString;

}