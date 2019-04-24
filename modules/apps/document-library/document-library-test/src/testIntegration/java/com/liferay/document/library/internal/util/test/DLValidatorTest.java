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

package com.liferay.document.library.internal.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.util.DLValidator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeFormatter;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.util.PropsValues;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alicia García
 */
@RunWith(Arquillian.class)
public class DLValidatorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testFixNameBlacklistChar() {
		String name = StringUtil.randomString(20);

		for (String blacklistChar : PropsValues.DL_CHAR_BLACKLIST) {
			Assert.assertEquals(
				name + StringPool.UNDERLINE,
				_dlValidator.fixName(name + blacklistChar));
		}
	}

	@Test
	public void testFixNameBlacklistLastChar() {
		String name = StringUtil.randomString(20);

		for (String blacklistLastChar : PropsValues.DL_CHAR_LAST_BLACKLIST) {
			Assert.assertEquals(
				name + StringPool.BLANK,
				_dlValidator.fixName(name + _parseString(blacklistLastChar)));
		}
	}

	@Test
	public void testFixNameBlacklistName() {
		String name = StringPool.PERIOD + StringUtil.randomString(3);

		for (String blacklistName : PropsValues.DL_NAME_BLACKLIST) {
			Assert.assertEquals(
				blacklistName + StringPool.UNDERLINE +
					StringUtil.toLowerCase(name),
				_dlValidator.fixName(blacklistName + name));
		}
	}

	@Test
	public void testFixNameCharSubstitutionWebDAV() {
		String name = StringUtil.randomString(20);

		Assert.assertEquals(
			name + StringPool.UNDERLINE + name,
			_dlValidator.fixName(
				name + PropsValues.DL_WEBDAV_SUBSTITUTION_CHAR + name));
	}

	@Test
	public void testFixNameWithNoBlacklistedChars() {
		String name = StringUtil.randomString(20);

		Assert.assertEquals(name, _dlValidator.fixName(name));
	}

	private String _parseString(String s) {
		if (s.startsWith(UnicodeFormatter.UNICODE_PREFIX)) {
			return UnicodeFormatter.parseString(s);
		}

		return s;
	}

	@Inject
	private DLValidator _dlValidator;

}