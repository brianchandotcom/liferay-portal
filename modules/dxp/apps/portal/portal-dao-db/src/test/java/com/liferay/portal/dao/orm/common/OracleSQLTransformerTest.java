/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.dao.orm.common;

import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Alberto Chaparro
 * @author Miguel Pastor
 */
public class OracleSQLTransformerTest extends BaseSQLTransformerTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testReplaceNotEqualsBlankStringComparison() {
		Assert.assertEquals(
			"SELECT * FROM User_ WHERE emailAddress IS NOT NULL",
			transformSQL("SELECT * FROM User_ WHERE emailAddress != ''"));
	}

	@Override
	protected DBType getDBType() {
		return DBType.ORACLE;
	}

}