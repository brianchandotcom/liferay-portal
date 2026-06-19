/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audiences.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.audiences.exception.AudiencesEntryJSONException;
import com.liferay.audiences.exception.AudiencesEntryNameException;
import com.liferay.audiences.model.AudiencesEntry;
import com.liferay.audiences.service.AudiencesEntryLocalService;
import com.liferay.petra.string.StringBundler;
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
public class AudiencesEntryLocalServiceTest {

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
	public void testAddAudiencesEntry() throws Exception {
		String name = RandomTestUtil.randomString();

		AudiencesEntry audiencesEntry = _addAudiencesEntry(
			StringBundler.concat(
				"{\"conjunction\": \"AND\", \"rules\": [{\"attribute\": ",
				"\"url\", \"operator\": \"eq\", \"value\": \"",
				RandomTestUtil.randomString(), "\"}]}"),
			name);

		Assert.assertNotNull(audiencesEntry.getJSON());
		Assert.assertEquals(name, audiencesEntry.getName());

		AssertUtils.assertFailure(
			AudiencesEntryNameException.class, null,
			() -> _addAudiencesEntry(StringPool.BLANK));
		AssertUtils.assertFailure(
			AudiencesEntryJSONException.class, null,
			() -> _addAudiencesEntry(
				"{\"conjunction\": \"INVALID\", \"rules\": []}",
				RandomTestUtil.randomString()));
	}

	@Test
	public void testDeleteAudiencesEntry() throws Exception {
		AudiencesEntry audiencesEntry =
			_audiencesEntryLocalService.addAudiencesEntry(
				null, RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), _serviceContext);

		long audiencesEntryId = audiencesEntry.getAudiencesEntryId();

		_audiencesEntryLocalService.deleteAudiencesEntry(audiencesEntryId);

		Assert.assertNull(
			_audiencesEntryLocalService.fetchAudiencesEntry(audiencesEntryId));
	}

	@Test
	public void testUpdateAudiencesEntry() throws Exception {
		AudiencesEntry audiencesEntry = _addAudiencesEntry(
			RandomTestUtil.randomString());

		String name = RandomTestUtil.randomString();

		audiencesEntry = _audiencesEntryLocalService.updateAudiencesEntry(
			audiencesEntry.getAudiencesEntryId(), audiencesEntry.getJSON(),
			name);

		Assert.assertEquals(name, audiencesEntry.getName());

		AudiencesEntry updatedAudiencesEntry = audiencesEntry;

		AssertUtils.assertFailure(
			AudiencesEntryNameException.class, null,
			() -> _audiencesEntryLocalService.updateAudiencesEntry(
				updatedAudiencesEntry.getAudiencesEntryId(),
				updatedAudiencesEntry.getJSON(), StringPool.BLANK));
		AssertUtils.assertFailure(
			AudiencesEntryJSONException.class, null,
			() -> _audiencesEntryLocalService.updateAudiencesEntry(
				updatedAudiencesEntry.getAudiencesEntryId(), StringPool.BLANK,
				updatedAudiencesEntry.getName()));
	}

	private AudiencesEntry _addAudiencesEntry(String name) throws Exception {
		return _addAudiencesEntry(StringPool.BLANK, name);
	}

	private AudiencesEntry _addAudiencesEntry(String json, String name)
		throws Exception {

		AudiencesEntry audiencesEntry =
			_audiencesEntryLocalService.addAudiencesEntry(
				null, json, name, _serviceContext);

		_audiencesEntries.add(audiencesEntry);

		return audiencesEntry;
	}

	@DeleteAfterTestRun
	private final List<AudiencesEntry> _audiencesEntries = new ArrayList<>();

	@Inject
	private AudiencesEntryLocalService _audiencesEntryLocalService;

	private ServiceContext _serviceContext;

}