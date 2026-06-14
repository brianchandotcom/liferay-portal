/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audience.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.audience.exception.AudienceEntryNameException;
import com.liferay.audience.model.AudienceEntry;
import com.liferay.audience.service.AudienceEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class AudienceEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_serviceContext = ServiceContextTestUtil.getServiceContext(
			TestPropsValues.getGroupId());
	}

	@Test
	public void testAddAudienceEntry() throws Exception {
		String name = RandomTestUtil.randomString();

		AudienceEntry audienceEntry = _addAudienceEntry(name);

		Assert.assertEquals(name, audienceEntry.getName());

		AssertUtils.assertFailure(
			AudienceEntryNameException.class, null,
			() -> _addAudienceEntry(StringPool.BLANK));
	}

	@Test
	public void testDeleteAudienceEntry() throws Exception {
		AudienceEntry audienceEntry =
			_audienceEntryLocalService.addAudienceEntry(
				null, RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), _serviceContext);

		long audienceEntryId = audienceEntry.getAudienceEntryId();

		_audienceEntryLocalService.deleteAudienceEntry(audienceEntryId);

		Assert.assertNull(
			_audienceEntryLocalService.fetchAudienceEntry(audienceEntryId));
	}

	@Test
	public void testUpdateAudienceEntry() throws Exception {
		AudienceEntry audienceEntry = _addAudienceEntry(
			RandomTestUtil.randomString());

		String name = RandomTestUtil.randomString();

		audienceEntry = _audienceEntryLocalService.updateAudienceEntry(
			audienceEntry.getAudienceEntryId(), audienceEntry.getJSON(), name);

		Assert.assertEquals(name, audienceEntry.getName());

		AudienceEntry updatedAudienceEntry = audienceEntry;

		AssertUtils.assertFailure(
			AudienceEntryNameException.class, null,
			() -> _audienceEntryLocalService.updateAudienceEntry(
				updatedAudienceEntry.getAudienceEntryId(),
				updatedAudienceEntry.getJSON(), StringPool.BLANK));
	}

	private AudienceEntry _addAudienceEntry(String name) throws Exception {
		AudienceEntry audienceEntry =
			_audienceEntryLocalService.addAudienceEntry(
				null, RandomTestUtil.randomString(), name, _serviceContext);

		_audienceEntries.add(audienceEntry);

		return audienceEntry;
	}

	@DeleteAfterTestRun
	private final List<AudienceEntry> _audienceEntries = new ArrayList<>();

	@Inject
	private AudienceEntryLocalService _audienceEntryLocalService;

	private ServiceContext _serviceContext;

}