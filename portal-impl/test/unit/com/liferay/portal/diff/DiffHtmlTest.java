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

	@Test(expected = NullPointerException.class)
	public void testDiffBothNull() throws Exception {
		_diffHtml.diff(null, null);
	}

	@Test
	public void testDiffDifferences() throws Exception {
		String original = StringUtil.randomString();
		String modified = StringUtil.randomString();

		String diff = _diffHtml.diff(
			new StringReader(original), new StringReader(modified));

		Assert.assertNotEquals(original, diff);
		Assert.assertNotEquals(modified, diff);
	}

	@Test(expected = NullPointerException.class)
	public void testDiffLeftNull() throws Exception {
		_diffHtml.diff(null, new StringReader(StringUtil.randomString()));
	}

	@Test
	public void testDiffNoDifferences() throws Exception {
		String content = StringUtil.randomString();

		Assert.assertEquals(
			content,
			_diffHtml.diff(
				new StringReader(content), new StringReader(content)));
	}

	@Test
	public void testDiffNoXMLDeclaration() throws Exception {
		String original = StringUtil.randomString();
		String modified = StringUtil.randomString();

		String diff = _diffHtml.diff(
			new StringReader(original), new StringReader(modified));

		Assert.assertFalse(diff.startsWith("<?xml"));
	}

	@Test(expected = NullPointerException.class)
	public void testDiffRightNull() throws Exception {
		_diffHtml.diff(new StringReader(StringUtil.randomString()), null);
	}

	private DiffHtml _diffHtml = new DiffHtmlImpl();

}