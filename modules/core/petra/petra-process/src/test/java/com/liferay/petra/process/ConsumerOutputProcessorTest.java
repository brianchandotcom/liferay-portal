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

package com.liferay.petra.process;

import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class ConsumerOutputProcessorTest extends BaseOutputProcessorTestCase {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testConsumeFail() {
		testFailToRead(new ConsumerOutputProcessor());
	}

	@Test
	public void testConsumeSuccess() throws ProcessException {
		ConsumerOutputProcessor consumerOutputProcessor =
			new ConsumerOutputProcessor();

		UnsyncByteArrayInputStream unsyncByteArrayInputStream =
			new UnsyncByteArrayInputStream(new byte[1024]);

		Assert.assertNull(
			consumerOutputProcessor.processStdErr(unsyncByteArrayInputStream));
		Assert.assertEquals(0, unsyncByteArrayInputStream.available());

		unsyncByteArrayInputStream = new UnsyncByteArrayInputStream(
			new byte[1024]);

		Assert.assertNull(
			consumerOutputProcessor.processStdOut(unsyncByteArrayInputStream));
		Assert.assertEquals(0, unsyncByteArrayInputStream.available());
	}

}