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

package com.liferay.petra.io;

import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Preston Crary
 */
public class DummyWriterTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testDummyOutputStream() {
		try (DummyWriter dummyWriter = new DummyWriter()) {
			Assert.assertSame(dummyWriter, dummyWriter.append('a'));
			Assert.assertSame(dummyWriter, dummyWriter.append("test"));
			Assert.assertSame(dummyWriter, dummyWriter.append("test", 0, 0));

			dummyWriter.write('a');
			dummyWriter.write(new char[0]);
			dummyWriter.write(new char[0], 0, 0);
			dummyWriter.write("test");
			dummyWriter.write("test", 0, 0);

			dummyWriter.flush();
		}
	}

}