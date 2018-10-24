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
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.versioning.VersioningPolicy;
import com.liferay.portal.kernel.security.RandomUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.documentlibrary.model.impl.DLFileVersionImpl;

import java.util.Optional;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adolfo Pérez
 */
@RunWith(Arquillian.class)
public class MetadataVersioningPolicyTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testComponentIsAvailable() {
		Assert.assertNotNull(_versioningPolicy);
	}

	@Test(expected = NullPointerException.class)
	public void testFailsWithNullNextDLFileVersion() {
		_versioningPolicy.computeDLVersionNumberIncrease(
			new DLFileVersionImpl(), null);
	}

	@Test(expected = NullPointerException.class)
	public void testFailsWithNullPreviousDLFileVersion() {
		_versioningPolicy.computeDLVersionNumberIncrease(
			null, new DLFileVersionImpl());
	}

	@Test
	public void testMinorVersionWhenDescriptionChanges() {
		DLFileVersionImpl previousDLFileVersion = new DLFileVersionImpl();

		previousDLFileVersion.setTitle(StringUtil.randomString());
		previousDLFileVersion.setDescription(StringUtil.randomString(5));

		DLFileVersionImpl nextDLFileVersion = new DLFileVersionImpl();

		nextDLFileVersion.setTitle(previousDLFileVersion.getTitle());
		nextDLFileVersion.setDescription(StringUtil.randomString(6));

		Optional<DLVersionNumberIncrease> dlVersionNumberIncreaseOptional =
			_versioningPolicy.computeDLVersionNumberIncrease(
				previousDLFileVersion, nextDLFileVersion);

		Assert.assertTrue(dlVersionNumberIncreaseOptional.isPresent());
		Assert.assertEquals(
			DLVersionNumberIncrease.MINOR,
			dlVersionNumberIncreaseOptional.get());
	}

	@Test
	public void testMinorVersionWhenFileEntryTypeChanges() {
		DLFileVersionImpl previousDLFileVersion = new DLFileVersionImpl();

		previousDLFileVersion.setTitle(StringUtil.randomString());
		previousDLFileVersion.setDescription(StringUtil.randomString(5));
		previousDLFileVersion.setFileEntryTypeId(RandomUtil.nextInt(10));

		DLFileVersionImpl nextDLFileVersion = new DLFileVersionImpl();

		nextDLFileVersion.setTitle(previousDLFileVersion.getTitle());
		nextDLFileVersion.setDescription(StringUtil.randomString(6));
		nextDLFileVersion.setFileEntryTypeId(
			previousDLFileVersion.getFileEntryTypeId() + 1);

		Optional<DLVersionNumberIncrease> dlVersionNumberIncreaseOptional =
			_versioningPolicy.computeDLVersionNumberIncrease(
				previousDLFileVersion, nextDLFileVersion);

		Assert.assertTrue(dlVersionNumberIncreaseOptional.isPresent());
		Assert.assertEquals(
			DLVersionNumberIncrease.MINOR,
			dlVersionNumberIncreaseOptional.get());
	}

	@Test
	public void testMinorVersionWhenTitleChanges() {
		DLFileVersionImpl previousDLFileVersion = new DLFileVersionImpl();

		previousDLFileVersion.setTitle(StringUtil.randomString(5));

		DLFileVersionImpl nextDLFileVersion = new DLFileVersionImpl();

		nextDLFileVersion.setTitle(StringUtil.randomString(6));

		Optional<DLVersionNumberIncrease> dlVersionNumberIncreaseOptional =
			_versioningPolicy.computeDLVersionNumberIncrease(
				previousDLFileVersion, nextDLFileVersion);

		Assert.assertTrue(dlVersionNumberIncreaseOptional.isPresent());
		Assert.assertEquals(
			DLVersionNumberIncrease.MINOR,
			dlVersionNumberIncreaseOptional.get());
	}

	@Inject(filter = "component.name=*.MetadataVersioningPolicy")
	private VersioningPolicy _versioningPolicy;

}