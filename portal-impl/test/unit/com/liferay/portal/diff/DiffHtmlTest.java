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

package com.liferay.portal.diff;

import com.liferay.portal.kernel.diff.DiffHtml;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.StringReader;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Adolfo Pérez
 */
public class DiffHtmlTest {

	@Test
	public void testDiffMustNotHaveXMLDeclaration() throws Exception {
		String source = StringUtil.randomString();
		String target = StringUtil.randomString();

		String diff = _diffHtml.diff(
			new StringReader(source), new StringReader(target));

		Assert.assertFalse(diff.startsWith("<?xml"));
	}

	@Test
	public void testDiffWhereSourceAndTargetAreDifferent() throws Exception {
		String source = StringUtil.randomString();
		String target = StringUtil.randomString();

		String diff = _diffHtml.diff(
			new StringReader(source), new StringReader(target));

		Assert.assertNotEquals(source, diff);
		Assert.assertNotEquals(target, diff);
	}

	@Test
	public void testDiffWhereSourceAndTargetAreIdentical() throws Exception {
		String content = StringUtil.randomString();

		Assert.assertEquals(
			content,
			_diffHtml.diff(
				new StringReader(content), new StringReader(content)));
	}

	@Test(expected = NullPointerException.class)
	public void testDiffWhereSourceAndTargetAreNull() throws Exception {
		_diffHtml.diff(null, null);
	}

	@Test(expected = NullPointerException.class)
	public void testDiffWhereSourceIsNull() throws Exception {
		_diffHtml.diff(null, new StringReader(StringUtil.randomString()));
	}

	@Test(expected = NullPointerException.class)
	public void testDiffWhereTargetIsNull() throws Exception {
		_diffHtml.diff(new StringReader(StringUtil.randomString()), null);
	}

	private final DiffHtml _diffHtml = new DiffHtmlImpl();

}