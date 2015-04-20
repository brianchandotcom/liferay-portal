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

package com.liferay.portal.tools.sourceformatter;

import com.liferay.portal.kernel.util.Tuple;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Hugo Huijser
 */
public class SourceFormatterTest {

	@Test
	public void testFileNameWithIncorrectExtension() throws Exception {
		SourceFormatter sourceFormatter = SourceFormatterUtil.create(
			false, false, false, false);

		String fileName =
			"portal-impl/test/integration/com/liferay/portal/tools/" +
				"sourceformatter/dependencies/wrong.foo";

		Tuple tuple = sourceFormatter.format(fileName);

		Assert.assertNull(tuple);
	}

	@Test
	public void testSourceFormatter() throws Throwable {
		SourceFormatter sourceFormatter = SourceFormatterUtil.create(
			false, true, false, false);

		try {
			sourceFormatter.format();
		}
		catch (SourceMismatchException sme) {
			Assert.assertEquals(
				sme.getFileName(), sme.getFormattedSource(),
				sme.getOriginalSource());
		}
	}

}