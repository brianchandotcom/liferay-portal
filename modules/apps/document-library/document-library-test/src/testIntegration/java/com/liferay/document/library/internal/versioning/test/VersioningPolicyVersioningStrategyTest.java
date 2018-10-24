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

package com.liferay.document.library.internal.versioning.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.versioning.VersioningStrategy;
import com.liferay.portal.kernel.security.RandomUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.documentlibrary.model.impl.DLFileVersionImpl;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adolfo Pérez
 */
@RunWith(Arquillian.class)
public class VersioningPolicyVersioningStrategyTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testComponentIsAvailable() {
		Assert.assertNotNull(_versioningStrategy);
	}

	@Test(expected = NullPointerException.class)
	public void testFailsWithNullNextDLFileVersion() {
		_versioningStrategy.computeDLVersionNumberIncrease(
			new DLFileVersionImpl(), null);
	}

	@Test(expected = NullPointerException.class)
	public void testFailsWithNullPreviousDLFileVersion() {
		_versioningStrategy.computeDLVersionNumberIncrease(
			null, new DLFileVersionImpl());
	}

	@Test
	public void testNoIncrementIfFileVersionDoesntChange() {
		DLFileVersion previousDLFileVersion = new DLFileVersionImpl();

		previousDLFileVersion.setTitle(StringUtil.randomString());
		previousDLFileVersion.setDescription(StringUtil.randomString());
		previousDLFileVersion.setSize(RandomUtil.nextInt(10));
		previousDLFileVersion.setChecksum(StringUtil.randomString());

		DLFileVersion nextDLFileVersion =
			(DLFileVersion)previousDLFileVersion.clone();

		Assert.assertEquals(
			DLVersionNumberIncrease.NONE,
			_versioningStrategy.computeDLVersionNumberIncrease(
				previousDLFileVersion, nextDLFileVersion));
	}

	@Inject(filter = "component.name=*.VersioningPolicyVersioningStrategy")
	private VersioningStrategy _versioningStrategy;

}