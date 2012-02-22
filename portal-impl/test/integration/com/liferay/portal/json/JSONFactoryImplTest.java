/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.json;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.BaseTestCase;

/**
 * @author Igor Spasic
 */
public class JSONFactoryImplTest extends BaseTestCase {

	public void testAnnotations() {
		FooBean fooBean = new FooBean();

		String json = _removeQuotationMarks(
			JSONFactoryUtil.looseSerialize(fooBean));

		assertEquals(
			"{class:com.liferay.portal.json.FooBean,name:bar,value:173}",
			json);
	}

	public void testCollection() {
		FooBean1 fooBean1 = new FooBean1();

		String json = _removeQuotationMarks(
			JSONFactoryUtil.looseSerialize(fooBean1));

		assertEquals(
			"{class:com.liferay.portal.json.FooBean1,collection:[element]," +
				"value:173}",
			json);
	}

	public void testStrictMode() {
		FooBean2 fooBean2 = new FooBean2();

		String json = _removeQuotationMarks(
			JSONFactoryUtil.looseSerialize(fooBean2));

		assertEquals("{value:173}", json);
	}

	private String _removeQuotationMarks(String string) {
		return StringUtil.replace(string, StringPool.QUOTE, StringPool.BLANK);
	}

}