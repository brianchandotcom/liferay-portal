/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		DLFileVersionImpl previousDLFileVersionImpl = new DLFileVersionImpl();

		previousDLFileVersionImpl.setTitle(StringUtil.randomString());
		previousDLFileVersionImpl.setDescription(StringUtil.randomString(5));

		DLFileVersionImpl nextDLFileVersionImpl = new DLFileVersionImpl();

		nextDLFileVersionImpl.setTitle(previousDLFileVersionImpl.getTitle());
		nextDLFileVersionImpl.setDescription(StringUtil.randomString(6));

		DLVersionNumberIncrease dlVersionNumberIncrease =
			_versioningPolicy.computeDLVersionNumberIncrease(
				previousDLFileVersionImpl, nextDLFileVersionImpl);

		Assert.assertNotNull(dlVersionNumberIncrease);
		Assert.assertEquals(
			DLVersionNumberIncrease.MINOR, dlVersionNumberIncrease);
	}

	@Test
	public void testMinorVersionWhenFileEntryTypeChanges() {
		DLFileVersionImpl previousDLFileVersionImpl = new DLFileVersionImpl();

		previousDLFileVersionImpl.setTitle(StringUtil.randomString());
		previousDLFileVersionImpl.setDescription(StringUtil.randomString(5));
		previousDLFileVersionImpl.setFileEntryTypeId(RandomUtil.nextInt(10));

		DLFileVersionImpl nextDLFileVersionImpl = new DLFileVersionImpl();

		nextDLFileVersionImpl.setTitle(previousDLFileVersionImpl.getTitle());
		nextDLFileVersionImpl.setDescription(StringUtil.randomString(6));
		nextDLFileVersionImpl.setFileEntryTypeId(
			previousDLFileVersionImpl.getFileEntryTypeId() + 1);

		DLVersionNumberIncrease dlVersionNumberIncrease =
			_versioningPolicy.computeDLVersionNumberIncrease(
				previousDLFileVersionImpl, nextDLFileVersionImpl);

		Assert.assertNotNull(dlVersionNumberIncrease);
		Assert.assertEquals(
			DLVersionNumberIncrease.MINOR, dlVersionNumberIncrease);
	}

	@Test
	public void testMinorVersionWhenTitleChanges() {
		DLFileVersionImpl previousDLFileVersionImpl = new DLFileVersionImpl();

		previousDLFileVersionImpl.setTitle(StringUtil.randomString(5));

		DLFileVersionImpl nextDLFileVersionImpl = new DLFileVersionImpl();

		nextDLFileVersionImpl.setTitle(StringUtil.randomString(6));

		DLVersionNumberIncrease dlVersionNumberIncrease =
			_versioningPolicy.computeDLVersionNumberIncrease(
				previousDLFileVersionImpl, nextDLFileVersionImpl);

		Assert.assertNotNull(dlVersionNumberIncrease);
		Assert.assertEquals(
			DLVersionNumberIncrease.MINOR, dlVersionNumberIncrease);
	}

	@Inject(
		filter = "component.name=com.liferay.document.library.internal.versioning.MetadataVersioningPolicy"
	)
	private VersioningPolicy _versioningPolicy;

}