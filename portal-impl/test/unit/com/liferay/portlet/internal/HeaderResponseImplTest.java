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

package com.liferay.portlet.internal;

import com.liferay.portal.kernel.test.ReflectionTestUtil;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Neil Griffin
 */
public class HeaderResponseImplTest {

	@Test
	public void testWellFormedXML() {
		_testTagName("link");
		_testTagName("LINK");
		_testTagName("meta");
		_testTagName("META");
	}

	private void _testTagName(String tagName) {
		String openTag = "<" + tagName + ">";
		String closeTag = "</" + tagName + ">";

		String openTagCloseTag = openTag + closeTag;

		String actual = ReflectionTestUtil.invoke(
			HeaderResponseImpl.class, "_addClosingTags",
			new Class<?>[] {String.class}, openTag);

		Assert.assertEquals(openTagCloseTag, actual);

		actual = ReflectionTestUtil.invoke(
			HeaderResponseImpl.class, "_addClosingTags",
			new Class<?>[] {String.class}, openTagCloseTag);

		Assert.assertEquals(openTagCloseTag, actual);

		actual = ReflectionTestUtil.invoke(
			HeaderResponseImpl.class, "_addClosingTags",
			new Class<?>[] {String.class}, "<head>" + openTag + "</head>");

		String openCloseTagInsideHead = "<head>" + openTagCloseTag + "</head>";

		Assert.assertEquals(openCloseTagInsideHead, actual);

		actual = ReflectionTestUtil.invoke(
			HeaderResponseImpl.class, "_addClosingTags",
			new Class<?>[] {String.class}, openCloseTagInsideHead);

		Assert.assertEquals(openCloseTagInsideHead, actual);
	}

}