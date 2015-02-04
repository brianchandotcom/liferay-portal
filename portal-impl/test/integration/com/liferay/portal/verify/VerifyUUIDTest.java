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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.exception.BulkException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.verify.model.LayoutVerifiableModel;
import com.liferay.portal.verify.model.VerifiableUUIDModel;
import com.liferay.portal.verify.test.BaseVerifyProcessTestCase;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 */
public class VerifyUUIDTest extends BaseVerifyProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Test
	public void testVerifyModel() throws Exception {
		VerifyUUID.verify(new LayoutVerifiableModel());
	}

	@Test(expected = BulkException.class)
	public void testVerifyModelWithUnknownPKColumnName() throws Exception {
		VerifyUUID.verify(
			new VerifiableUUIDModel() {

				@Override
				public String getPrimaryKeyColumnName() {
					return _UNKNOWN;
				}

				@Override
				public String getTableName() {
					return "Layout";
				}

			});
	}

	@Test(expected = BulkException.class)
	public void testVerifyUnknownModelWithUnknownPKColumnName()
		throws Exception {

		VerifyUUID.verify(
			new VerifiableUUIDModel() {

				@Override
				public String getPrimaryKeyColumnName() {
					return _UNKNOWN;
				}

				@Override
				public String getTableName() {
					return _UNKNOWN;
				}

			});
	}

	@Override
	protected VerifyProcess getVerifyProcess() {
		return new VerifyUUID();
	}

	private static final String _UNKNOWN = "Unknown";

}