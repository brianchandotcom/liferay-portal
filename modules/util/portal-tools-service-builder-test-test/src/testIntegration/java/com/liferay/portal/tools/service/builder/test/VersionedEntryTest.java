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

package com.liferay.portal.tools.service.builder.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.service.version.VersionService;
import com.liferay.portal.kernel.service.version.VersionServiceListener;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchVersionedEntryException;
import com.liferay.portal.tools.service.builder.test.model.VersionedEntry;
import com.liferay.portal.tools.service.builder.test.model.VersionedEntryContent;
import com.liferay.portal.tools.service.builder.test.model.VersionedEntryContentVersion;
import com.liferay.portal.tools.service.builder.test.model.VersionedEntryVersion;
import com.liferay.portal.tools.service.builder.test.service.VersionedEntryLocalService;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public class VersionedEntryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testCheckout() throws Exception {
		VersionedEntry draftVersionedEntry = _versionService.create();

		draftVersionedEntry.setGroupId(_GROUP_ID_1);

		draftVersionedEntry = _versionService.updateDraft(draftVersionedEntry);

		VersionedEntryContent versionedEntryContent1 =
			_versionedEntryLocalService.updateVersionedEntryContent(
				draftVersionedEntry, _LANGUAGE_ID_1, _CONTENT_1);

		VersionedEntryContent versionedEntryContent2 =
			_versionedEntryLocalService.updateVersionedEntryContent(
				draftVersionedEntry, _LANGUAGE_ID_2, _CONTENT_2);

		List<VersionedEntryVersion> versionedEntryVersions =
			_versionService.getVersions(draftVersionedEntry);

		Assert.assertSame(Collections.emptyList(), versionedEntryVersions);

		_versionedEntry = _versionService.publishDraft(draftVersionedEntry);

		List<VersionedEntryContent> versionedEntryContents =
			_versionedEntryLocalService.getVersionedEntryContents(
				_versionedEntry.getPrimaryKey());

		Assert.assertEquals(
			versionedEntryContents.toString(), 2,
			versionedEntryContents.size());

		_assertContains(versionedEntryContent1, versionedEntryContents);
		_assertContains(versionedEntryContent2, versionedEntryContents);

		Assert.assertEquals(_GROUP_ID_1, _versionedEntry.getGroupId());

		draftVersionedEntry = _versionService.getDraft(_versionedEntry);

		Map<String, String> languageIdToContentMap =
			draftVersionedEntry.getLanguageIdToContentMap();

		Assert.assertEquals(
			_CONTENT_2, languageIdToContentMap.remove(_LANGUAGE_ID_2));

		Assert.assertNull(
			languageIdToContentMap.put(_LANGUAGE_ID_3, _CONTENT_3));

		_versionedEntryLocalService.updateVersionedEntryContents(
			draftVersionedEntry, languageIdToContentMap);

		draftVersionedEntry.setGroupId(_GROUP_ID_2);

		_versionedEntry = _versionService.publishDraft(draftVersionedEntry);

		versionedEntryContents =
			_versionedEntryLocalService.getVersionedEntryContents(
				_versionedEntry.getPrimaryKey());

		Assert.assertEquals(
			versionedEntryContents.toString(), 2,
			versionedEntryContents.size());

		_assertContains(versionedEntryContent1, versionedEntryContents);
		_assertNotContains(versionedEntryContent2, versionedEntryContents);

		VersionedEntryContent versionedEntryContent3 =
			_versionedEntryLocalService.getVersionedEntryContent(
				_versionedEntry.getPrimaryKey(), _LANGUAGE_ID_3);

		_assertContains(versionedEntryContent3, versionedEntryContents);

		Assert.assertEquals(_GROUP_ID_2, _versionedEntry.getGroupId());

		draftVersionedEntry = _versionService.checkout(_versionedEntry, 1);

		Assert.assertEquals(_GROUP_ID_1, draftVersionedEntry.getGroupId());
		Assert.assertTrue(
			draftVersionedEntry.toString(), draftVersionedEntry.isDraft());

		versionedEntryVersions = _versionService.getVersions(_versionedEntry);

		Assert.assertEquals(
			versionedEntryVersions.toString(), 2,
			versionedEntryVersions.size());

		VersionedEntryVersion versionedEntryVersion2 =
			versionedEntryVersions.get(0);
		VersionedEntryVersion versionedEntryVersion1 =
			versionedEntryVersions.get(1);

		Assert.assertEquals(2, versionedEntryVersion2.getVersion());
		Assert.assertEquals(_GROUP_ID_2, versionedEntryVersion2.getGroupId());

		Assert.assertEquals(1, versionedEntryVersion1.getVersion());
		Assert.assertEquals(_GROUP_ID_1, versionedEntryVersion1.getGroupId());

		versionedEntryContents =
			_versionedEntryLocalService.getVersionedEntryContents(
				draftVersionedEntry.getPrimaryKey());

		Assert.assertEquals(
			versionedEntryContents.toString(), 2,
			versionedEntryContents.size());

		_assertContains(versionedEntryContent1, versionedEntryContents);
		_assertContains(versionedEntryContent2, versionedEntryContents);
		_assertNotContains(versionedEntryContent3, versionedEntryContents);

		try {
			_versionService.checkout(draftVersionedEntry, 1);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Cannot checkout with unpublished changes " +
					draftVersionedEntry.getHeadId(),
				iae.getMessage());
		}

		try {
			_versionService.checkout(_versionedEntry, 1);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Cannot checkout with unpublished changes " +
					draftVersionedEntry.getHeadId(),
				iae.getMessage());
		}
	}

	@Test
	public void testCreate() {
		VersionedEntry versionedEntry = _versionService.create();

		Assert.assertTrue(versionedEntry.isDraft());
		Assert.assertTrue(versionedEntry.isNew());
		Assert.assertEquals(
			versionedEntry.getHeadId(), versionedEntry.getPrimaryKey());

		Assert.assertNull(
			_versionService.fetchDraft(versionedEntry.getPrimaryKey()));
	}

	@Test
	public void testDelete() throws Exception {
		VersionedEntry draftVersionedEntry = _versionService.create();

		try {
			_versionedEntryLocalService.updateVersionedEntryContent(
				draftVersionedEntry, _LANGUAGE_ID_1, _CONTENT_1);

			Assert.fail();
		}
		catch (NoSuchVersionedEntryException nsvee) {
			Assert.assertEquals(
				"No VersionedEntry exists with the primary key " +
					draftVersionedEntry.getPrimaryKey(),
				nsvee.getMessage());
		}

		draftVersionedEntry = _versionService.updateDraft(draftVersionedEntry);

		_versionedEntryLocalService.updateVersionedEntryContent(
			draftVersionedEntry, _LANGUAGE_ID_1, _CONTENT_1);

		_versionedEntry = _versionService.publishDraft(draftVersionedEntry);

		draftVersionedEntry = _versionService.getDraft(_versionedEntry);

		try {
			_versionService.delete(draftVersionedEntry);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"VersionedEntry is a draft " +
					draftVersionedEntry.getPrimaryKey(),
				iae.getMessage());
		}

		_versionedEntryLocalService.updateVersionedEntryContent(
			draftVersionedEntry, _LANGUAGE_ID_1, _CONTENT_2);

		draftVersionedEntry = _versionService.updateDraft(draftVersionedEntry);

		Assert.assertNotNull(draftVersionedEntry);
		Assert.assertNotEquals(draftVersionedEntry, _versionedEntry);

		Assert.assertEquals(
			_versionedEntry, _versionService.delete(_versionedEntry));

		List<VersionedEntryVersion> versionedEntryVersions =
			_versionService.getVersions(_versionedEntry);

		Assert.assertEquals(
			versionedEntryVersions.toString(), 0,
			versionedEntryVersions.size());

		VersionedEntryContent versionedEntryContent =
			_versionedEntryLocalService.fetchVersionedEntryContent(
				_versionedEntry.getPrimaryKey(), _LANGUAGE_ID_1);

		Assert.assertNull(versionedEntryContent);

		VersionedEntryContentVersion versionedEntryContentVersion =
			_versionedEntryLocalService.fetchVersionedEntryContentVersion(
				_versionedEntry.getPrimaryKey(), _LANGUAGE_ID_1, 1);

		Assert.assertNull(versionedEntryContentVersion);

		versionedEntryContent =
			_versionedEntryLocalService.fetchVersionedEntryContent(
				draftVersionedEntry.getPrimaryKey(), _LANGUAGE_ID_1);

		Assert.assertNull(versionedEntryContent);

		_versionedEntry = _versionService.fetchPublished(
			_versionedEntry.getVersionedEntryId());

		Assert.assertNull(_versionedEntry);

		draftVersionedEntry = _versionService.fetchDraft(
			draftVersionedEntry.getHeadId());

		Assert.assertNull(draftVersionedEntry);
	}

	@Test
	public void testDeleteDraft() throws Exception {
		VersionedEntry draftVersionedEntry = _versionService.create();

		draftVersionedEntry = _versionService.updateDraft(draftVersionedEntry);

		_versionedEntryLocalService.updateVersionedEntryContent(
			draftVersionedEntry, _LANGUAGE_ID_1, _CONTENT_1);

		_versionedEntry = _versionService.publishDraft(draftVersionedEntry);

		try {
			_versionService.deleteDraft(_versionedEntry);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"VersionedEntry is not a draft " +
					_versionedEntry.getPrimaryKey(),
				iae.getMessage());
		}

		VersionedEntryContent versionedEntryContent =
			_versionedEntryLocalService.fetchVersionedEntryContent(
				_versionedEntry.getPrimaryKey(), _LANGUAGE_ID_1);

		Assert.assertNotNull(versionedEntryContent);
		Assert.assertEquals(_CONTENT_1, versionedEntryContent.getContent());

		draftVersionedEntry = _versionService.getDraft(_versionedEntry);

		_versionedEntryLocalService.updateVersionedEntryContent(
			draftVersionedEntry, _LANGUAGE_ID_1, _CONTENT_2);

		draftVersionedEntry = _versionService.updateDraft(draftVersionedEntry);

		Assert.assertNotNull(draftVersionedEntry);
		Assert.assertNotEquals(draftVersionedEntry, _versionedEntry);

		Assert.assertSame(
			draftVersionedEntry,
			_versionService.deleteDraft(draftVersionedEntry));

		versionedEntryContent =
			_versionedEntryLocalService.fetchVersionedEntryContent(
				_versionedEntry.getPrimaryKey(), _LANGUAGE_ID_1);

		Assert.assertNotNull(versionedEntryContent);

		VersionedEntryContentVersion versionedEntryContentVersion =
			_versionedEntryLocalService.fetchVersionedEntryContentVersion(
				_versionedEntry.getPrimaryKey(), _LANGUAGE_ID_1, 1);

		Assert.assertNotNull(versionedEntryContentVersion);

		versionedEntryContent =
			_versionedEntryLocalService.fetchVersionedEntryContent(
				draftVersionedEntry.getPrimaryKey(), _LANGUAGE_ID_1);

		Assert.assertNull(versionedEntryContent);

		_versionedEntry = _versionService.fetchPublished(
			_versionedEntry.getVersionedEntryId());

		Assert.assertNotNull(_versionedEntry);

		draftVersionedEntry = _versionService.fetchDraft(
			draftVersionedEntry.getHeadId());

		Assert.assertNull(draftVersionedEntry);
	}

	@Test
	public void testDeleteLatestVersion() throws Exception {
		VersionedEntry draftVersionedEntry = _versionService.create();

		_versionedEntry = _versionService.publishDraft(draftVersionedEntry);

		VersionedEntryVersion versionedEntryVersion =
			_versionService.fetchLatestVersion(_versionedEntry);

		Assert.assertNotNull(versionedEntryVersion);

		try {
			_versionService.deleteVersion(versionedEntryVersion);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Cannot delete latest version 1", iae.getMessage());
		}
	}

	@Test
	public void testDeleteVersion() throws Exception {
		VersionedEntry draftVersionedEntry = _versionService.create();

		_versionedEntry = _versionService.publishDraft(draftVersionedEntry);

		draftVersionedEntry = _versionService.getDraft(_versionedEntry);

		_versionedEntryLocalService.updateVersionedEntryContent(
			draftVersionedEntry, _LANGUAGE_ID_1, _CONTENT_1);

		_versionedEntry = _versionService.publishDraft(draftVersionedEntry);

		draftVersionedEntry = _versionService.getDraft(_versionedEntry);

		_versionedEntryLocalService.updateVersionedEntryContent(
			draftVersionedEntry, _LANGUAGE_ID_1, _CONTENT_2);

		VersionedEntryVersion versionedEntryVersion1 =
			_versionService.getVersion(_versionedEntry, 1);

		Assert.assertEquals(
			versionedEntryVersion1,
			_versionService.deleteVersion(versionedEntryVersion1));

		List<VersionedEntryVersion> versionedEntryVersions =
			_versionService.getVersions(_versionedEntry);

		Assert.assertEquals(
			versionedEntryVersions.toString(), 1,
			versionedEntryVersions.size());

		VersionedEntryVersion versionedEntryVersion2 =
			versionedEntryVersions.get(0);

		Assert.assertEquals(2, versionedEntryVersion2.getVersion());

		VersionedEntryContent versionedEntryContent =
			_versionedEntryLocalService.fetchVersionedEntryContent(
				_versionedEntry.getPrimaryKey(), _LANGUAGE_ID_1);

		Assert.assertNotNull(versionedEntryContent);

		versionedEntryContent =
			_versionedEntryLocalService.fetchVersionedEntryContent(
				draftVersionedEntry.getPrimaryKey(), _LANGUAGE_ID_1);

		Assert.assertNotNull(versionedEntryContent);

		VersionedEntryContentVersion versionedEntryContentVersion =
			_versionedEntryLocalService.fetchVersionedEntryContentVersion(
				_versionedEntry.getPrimaryKey(), _LANGUAGE_ID_1, 1);

		Assert.assertNull(versionedEntryContentVersion);

		versionedEntryContentVersion =
			_versionedEntryLocalService.fetchVersionedEntryContentVersion(
				_versionedEntry.getPrimaryKey(), _LANGUAGE_ID_1, 2);

		Assert.assertNotNull(versionedEntryContentVersion);
	}

	@Test
	public void testDeleteVersionedEntry() throws Exception {
		VersionedEntry draftVersionedEntry = _versionService.create();

		draftVersionedEntry = _versionService.updateDraft(draftVersionedEntry);

		_versionedEntry = _versionService.publishDraft(draftVersionedEntry);

		_versionedEntryLocalService.deleteVersionedEntry(
			_versionedEntry.getPrimaryKey());

		_versionedEntry = _versionedEntryLocalService.deleteVersionedEntry(
			_versionedEntry.getPrimaryKey());

		Assert.assertNull(_versionedEntry);

		draftVersionedEntry = _versionService.create();

		try {
			_versionedEntryLocalService.deleteVersionedEntry(
				draftVersionedEntry);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"VersionedEntry is a draft " +
					draftVersionedEntry.getPrimaryKey(),
				iae.getMessage());
		}

		try {
			_versionedEntryLocalService.deleteVersionedEntry(
				draftVersionedEntry);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"VersionedEntry is a draft " +
					draftVersionedEntry.getPrimaryKey(),
				iae.getMessage());
		}

		_versionedEntry = _versionService.publishDraft(draftVersionedEntry);

		PortalException portalException = new PortalException();

		TestVersionServiceListener testVersionServiceListener =
			new TestVersionServiceListener(Collections.emptyMap()) {

				@Override
				public void afterDelete(VersionedEntry versionedEntry)
					throws PortalException {

					throw portalException;
				}

			};

		_versionService.registerListener(testVersionServiceListener);

		try {
			try {
				_versionedEntryLocalService.deleteVersionedEntry(
					_versionedEntry.getPrimaryKey());

				Assert.fail();
			}
			catch (SystemException se) {
				Assert.assertSame(portalException, se.getCause());
			}

			try {
				_versionedEntryLocalService.deleteVersionedEntry(
					_versionedEntry);

				Assert.fail();
			}
			catch (SystemException se) {
				Assert.assertSame(portalException, se.getCause());
			}
		}
		finally {
			_versionService.unregisterListener(testVersionServiceListener);
		}
	}

	@Test
	public void testFetchDraft() throws Exception {
		VersionedEntry draftVersionedEntry = _versionService.create();

		draftVersionedEntry.setGroupId(_GROUP_ID_1);

		draftVersionedEntry = _versionService.updateDraft(draftVersionedEntry);

		Assert.assertTrue(draftVersionedEntry.isDraft());

		Assert.assertSame(
			draftVersionedEntry,
			_versionService.fetchDraft(draftVersionedEntry));

		VersionedEntryContent draftVersionedEntryContent =
			_versionedEntryLocalService.updateVersionedEntryContent(
				draftVersionedEntry, _LANGUAGE_ID_1, _CONTENT_1);

		Assert.assertEquals(
			draftVersionedEntryContent,
			_versionedEntryLocalService.getVersionedEntryContent(
				draftVersionedEntry.getPrimaryKey(), _LANGUAGE_ID_1));

		_versionedEntry = _versionService.publishDraft(draftVersionedEntry);

		VersionedEntryContent publishedVersionEntryContent =
			_versionedEntryLocalService.getVersionedEntryContent(
				_versionedEntry.getPrimaryKey(), _LANGUAGE_ID_1);

		Assert.assertEquals(
			_CONTENT_1, publishedVersionEntryContent.getContent());

		Assert.assertNotEquals(draftVersionedEntry, _versionedEntry);

		draftVersionedEntryContent =
			_versionedEntryLocalService.fetchVersionedEntryContent(
				draftVersionedEntry.getPrimaryKey(), _LANGUAGE_ID_1);

		Assert.assertNull(draftVersionedEntryContent);

		Assert.assertNull(_versionService.fetchDraft(_versionedEntry));

		draftVersionedEntry = _versionService.getDraft(_versionedEntry);

		Assert.assertNotNull(draftVersionedEntry);
		Assert.assertNotEquals(draftVersionedEntry, _versionedEntry);

		Assert.assertEquals(
			draftVersionedEntry.getGroupId(), _versionedEntry.getGroupId());
	}

	@Test
	public void testFetchLatestVersion() throws Exception {
		VersionedEntry draftVersionedEntry = _versionService.create();

		_versionedEntry = _versionService.publishDraft(draftVersionedEntry);

		draftVersionedEntry = _versionService.getDraft(_versionedEntry);

		VersionedEntryVersion versionedEntryVersion =
			_versionService.fetchLatestVersion(draftVersionedEntry);

		Assert.assertEquals(
			versionedEntryVersion,
			_versionService.getVersion(draftVersionedEntry, 1));
	}

	@Test
	public void testFetchPublished() throws Exception {
		VersionedEntry draftVersionedEntry = _versionService.create();

		draftVersionedEntry = _versionService.updateDraft(draftVersionedEntry);

		_versionedEntryLocalService.updateVersionedEntryContent(
			draftVersionedEntry, _LANGUAGE_ID_1, _CONTENT_1);

		draftVersionedEntry = _versionService.updateDraft(draftVersionedEntry);

		Assert.assertTrue(draftVersionedEntry.isDraft());

		Assert.assertNull(_versionService.fetchPublished(draftVersionedEntry));
		Assert.assertNull(
			_versionService.fetchPublished(
				draftVersionedEntry.getPrimaryKey()));

		_versionedEntry = _versionService.publishDraft(draftVersionedEntry);

		VersionedEntryContent versionedEntryContent =
			_versionedEntryLocalService.getVersionedEntryContent(
				_versionedEntry.getPrimaryKey(), _LANGUAGE_ID_1);

		Assert.assertEquals(_CONTENT_1, versionedEntryContent.getContent());

		Assert.assertEquals(
			_versionedEntry, _versionService.fetchPublished(_versionedEntry));
		Assert.assertEquals(
			_versionedEntry,
			_versionService.fetchPublished(_versionedEntry.getPrimaryKey()));

		draftVersionedEntry = _versionService.getDraft(_versionedEntry);

		Assert.assertEquals(
			_versionedEntry,
			_versionService.fetchPublished(draftVersionedEntry));
		Assert.assertEquals(
			_versionedEntry,
			_versionService.fetchPublished(draftVersionedEntry.getHeadId()));
	}

	@Test
	public void testGetDraft() throws Exception {
		VersionedEntry draftVersionedEntry = _versionService.create();

		draftVersionedEntry = _versionService.updateDraft(draftVersionedEntry);

		Assert.assertSame(
			draftVersionedEntry, _versionService.getDraft(draftVersionedEntry));

		_versionedEntryLocalService.updateVersionedEntryContent(
			draftVersionedEntry, _LANGUAGE_ID_1, _CONTENT_1);

		Assert.assertEquals(
			draftVersionedEntry,
			_versionService.getDraft(draftVersionedEntry.getPrimaryKey()));

		_versionedEntry = _versionService.publishDraft(draftVersionedEntry);

		draftVersionedEntry = _versionService.getDraft(
			_versionedEntry.getPrimaryKey());

		Assert.assertNotEquals(_versionedEntry, draftVersionedEntry);

		VersionedEntryContent versionedEntryContent =
			_versionedEntryLocalService.getVersionedEntryContent(
				draftVersionedEntry.getPrimaryKey(), _LANGUAGE_ID_1);

		Assert.assertEquals(_CONTENT_1, versionedEntryContent.getContent());
	}

	@Test
	public void testGetVersions() throws Exception {
		VersionedEntry draftVersionedEntry = _versionService.create();

		draftVersionedEntry.setGroupId(_GROUP_ID_1);

		draftVersionedEntry = _versionService.updateDraft(draftVersionedEntry);

		_versionedEntryLocalService.updateVersionedEntryContent(
			draftVersionedEntry, _LANGUAGE_ID_1, _CONTENT_1);

		_versionedEntry = _versionService.publishDraft(draftVersionedEntry);

		draftVersionedEntry = _versionService.getDraft(_versionedEntry);

		draftVersionedEntry.setGroupId(_GROUP_ID_2);

		_versionedEntryLocalService.updateVersionedEntryContent(
			draftVersionedEntry, _LANGUAGE_ID_1, _CONTENT_2);

		_versionedEntry = _versionService.publishDraft(draftVersionedEntry);

		List<VersionedEntryVersion> versionedEntryVersions =
			_versionService.getVersions(draftVersionedEntry);

		Assert.assertEquals(
			versionedEntryVersions.toString(), 2,
			versionedEntryVersions.size());

		VersionedEntryVersion versionedEntryVersion2 =
			versionedEntryVersions.get(0);

		Assert.assertEquals(2, versionedEntryVersion2.getVersion());
		Assert.assertEquals(_GROUP_ID_2, versionedEntryVersion2.getGroupId());

		VersionedEntryVersion versionedEntryVersion1 =
			versionedEntryVersions.get(1);

		Assert.assertEquals(1, versionedEntryVersion1.getVersion());
		Assert.assertEquals(_GROUP_ID_1, versionedEntryVersion1.getGroupId());

		List<VersionedEntryContentVersion> versionedEntryContentVersions =
			_versionedEntryLocalService.getVersionedEntryContentVersions(
				_versionedEntry.getPrimaryKey(), _LANGUAGE_ID_1);

		Assert.assertEquals(
			versionedEntryContentVersions.toString(), 2,
			versionedEntryContentVersions.size());

		VersionedEntryContentVersion versionedEntryContentVersion2 =
			versionedEntryContentVersions.get(0);

		Assert.assertEquals(2, versionedEntryContentVersion2.getVersion());
		Assert.assertEquals(
			_CONTENT_2, versionedEntryContentVersion2.getContent());

		VersionedEntryContentVersion versionedEntryContentVersion1 =
			versionedEntryContentVersions.get(1);

		Assert.assertEquals(1, versionedEntryContentVersion1.getVersion());
		Assert.assertEquals(
			_CONTENT_1, versionedEntryContentVersion1.getContent());
	}

	@Test
	public void testPublish() throws Exception {
		VersionedEntry draftVersionedEntry = _versionService.create();

		Assert.assertTrue(
			draftVersionedEntry.toString(), draftVersionedEntry.isDraft());

		_versionedEntry = _versionService.publishDraft(draftVersionedEntry);

		Assert.assertFalse(
			_versionedEntry.toString(), _versionedEntry.isDraft());

		VersionedEntryVersion versionedEntryVersion =
			_versionService.fetchLatestVersion(_versionedEntry);

		Assert.assertNotNull(versionedEntryVersion);

		Assert.assertEquals(1, versionedEntryVersion.getVersion());

		try {
			_versionService.publishDraft(_versionedEntry);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Can only publish drafts " + _versionedEntry.getPrimaryKey(),
				iae.getMessage());
		}
	}

	@Test
	public void testSameService() throws Exception {
		Assert.assertSame(_versionedEntryLocalService, _versionService);
	}

	@Test
	public void testServiceListener() throws Exception {
		Map<String, Object[]> methodNameToParametersMap = new HashMap<>();

		TestVersionServiceListener testVersionServiceListener =
			new TestVersionServiceListener(methodNameToParametersMap);

		_versionService.registerListener(testVersionServiceListener);

		VersionedEntry draftVersionedEntry = _versionService.create();

		Assert.assertTrue(
			methodNameToParametersMap.toString(),
			methodNameToParametersMap.isEmpty());

		draftVersionedEntry = _versionService.updateDraft(draftVersionedEntry);

		Object[] parameters = methodNameToParametersMap.remove(
			"afterCreateDraft");

		Assert.assertSame(draftVersionedEntry, parameters[0]);

		draftVersionedEntry = _versionService.updateDraft(draftVersionedEntry);

		parameters = methodNameToParametersMap.remove("afterUpdateDraft");

		Assert.assertSame(draftVersionedEntry, parameters[0]);

		_versionedEntry = _versionService.publishDraft(draftVersionedEntry);

		parameters = methodNameToParametersMap.remove("afterPublishDraft");

		Assert.assertSame(draftVersionedEntry, parameters[0]);
		Assert.assertSame(1, parameters[1]);

		draftVersionedEntry = _versionService.getDraft(_versionedEntry);

		parameters = methodNameToParametersMap.remove("afterCreateDraft");

		Assert.assertSame(draftVersionedEntry, parameters[0]);

		_versionedEntry = _versionService.publishDraft(draftVersionedEntry);

		parameters = methodNameToParametersMap.remove("afterPublishDraft");

		Assert.assertSame(draftVersionedEntry, parameters[0]);
		Assert.assertSame(2, parameters[1]);

		VersionedEntryVersion versionedEntryVersion =
			_versionService.getVersion(_versionedEntry, 1);

		_versionService.deleteVersion(versionedEntryVersion);

		parameters = methodNameToParametersMap.remove("afterDeleteVersion");

		Assert.assertEquals(versionedEntryVersion, parameters[0]);

		draftVersionedEntry = _versionService.checkout(_versionedEntry, 2);

		parameters = methodNameToParametersMap.remove("afterCheckout");

		Assert.assertSame(draftVersionedEntry, parameters[0]);
		Assert.assertEquals(2, parameters[1]);

		_versionService.deleteDraft(draftVersionedEntry);

		parameters = methodNameToParametersMap.remove("afterDeleteDraft");

		Assert.assertSame(draftVersionedEntry, parameters[0]);

		_versionService.delete(_versionedEntry);

		parameters = methodNameToParametersMap.remove("afterDelete");

		Assert.assertSame(_versionedEntry, parameters[0]);

		_versionedEntry = null;

		_versionService.unregisterListener(testVersionServiceListener);

		draftVersionedEntry = _versionService.create();

		draftVersionedEntry = _versionService.updateDraft(draftVersionedEntry);

		_versionService.deleteDraft(draftVersionedEntry);

		Assert.assertTrue(
			methodNameToParametersMap.toString(),
			methodNameToParametersMap.isEmpty());
	}

	@Test
	public void testUpdate() throws Exception {
		VersionedEntry draftVersionedEntry = _versionService.create();

		_versionedEntry = _versionService.publishDraft(draftVersionedEntry);

		try {
			_versionService.updateDraft(_versionedEntry);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Can only update draft entries " +
					_versionedEntry.getPrimaryKey(),
				iae.getMessage());
		}

		try {
			_versionedEntryLocalService.updateVersionedEntryContent(
				_versionedEntry, _LANGUAGE_ID_1, _CONTENT_1);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Can only update draft entries " +
					_versionedEntry.getPrimaryKey(),
				iae.getMessage());
		}

		try {
			_versionedEntryLocalService.updateVersionedEntryContents(
				_versionedEntry, Collections.emptyMap());

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Can only update draft entries " +
					_versionedEntry.getPrimaryKey(),
				iae.getMessage());
		}
	}

	private void _assertContains(
		VersionedEntryContent expectedVersionedEntryContent,
		List<VersionedEntryContent> versionedEntryContents) {

		for (VersionedEntryContent versionedEntryContent :
				versionedEntryContents) {

			if (Objects.equals(
					expectedVersionedEntryContent.getLanguageId(),
					versionedEntryContent.getLanguageId())) {

				Assert.assertEquals(
					expectedVersionedEntryContent.getContent(),
					versionedEntryContent.getContent());

				return;
			}
		}

		Assert.fail(
			StringBundler.concat(
				"Failed to find ", expectedVersionedEntryContent.toString(),
				" in ", versionedEntryContents.toString()));
	}

	private void _assertNotContains(
		VersionedEntryContent expectedVersionedEntryContent,
		List<VersionedEntryContent> versionedEntryContents) {

		for (VersionedEntryContent versionedEntryContent :
				versionedEntryContents) {

			if (Objects.equals(
					expectedVersionedEntryContent.getLanguageId(),
					versionedEntryContent.getLanguageId())) {

				Assert.assertNotEquals(
					expectedVersionedEntryContent.getContent(),
					versionedEntryContent.getContent());

				return;
			}
		}
	}

	private static final String _CONTENT_1 = "CONTENT 1";

	private static final String _CONTENT_2 = "CONTENT 2";

	private static final String _CONTENT_3 = "CONTENT 3";

	private static final long _GROUP_ID_1 = 1;

	private static final long _GROUP_ID_2 = 2;

	private static final String _LANGUAGE_ID_1 = "LANGUAGE ID 1";

	private static final String _LANGUAGE_ID_2 = "LANGUAGE ID 2";

	private static final String _LANGUAGE_ID_3 = "LANGUAGE ID 3";

	@Inject
	private static VersionedEntryLocalService _versionedEntryLocalService;

	@Inject(
		filter = "model.class.name=com.liferay.portal.tools.service.builder.test.model.VersionedEntry"
	)
	private static VersionService<VersionedEntry, VersionedEntryVersion>
		_versionService;

	@DeleteAfterTestRun
	private VersionedEntry _versionedEntry;

	private static class TestVersionServiceListener
		implements VersionServiceListener
			<VersionedEntry, VersionedEntryVersion> {

		@Override
		public void afterCheckout(VersionedEntry versionedEntry, int version) {
			_methodNameToParametersMap.put(
				"afterCheckout", new Object[] {versionedEntry, version});
		}

		@Override
		public void afterCreateDraft(VersionedEntry versionedEntry) {
			_methodNameToParametersMap.put(
				"afterCreateDraft", new Object[] {versionedEntry});
		}

		@Override
		public void afterDelete(VersionedEntry versionedEntry)
			throws PortalException {

			_methodNameToParametersMap.put(
				"afterDelete", new Object[] {versionedEntry});
		}

		@Override
		public void afterDeleteDraft(VersionedEntry versionedEntry) {
			_methodNameToParametersMap.put(
				"afterDeleteDraft", new Object[] {versionedEntry});
		}

		@Override
		public void afterDeleteVersion(
			VersionedEntryVersion versionedEntryVersion) {

			_methodNameToParametersMap.put(
				"afterDeleteVersion", new Object[] {versionedEntryVersion});
		}

		@Override
		public void afterPublishDraft(
			VersionedEntry versionedEntry, int version) {

			_methodNameToParametersMap.put(
				"afterPublishDraft", new Object[] {versionedEntry, version});
		}

		@Override
		public void afterUpdateDraft(VersionedEntry versionedEntry) {
			_methodNameToParametersMap.put(
				"afterUpdateDraft", new Object[] {versionedEntry});
		}

		private TestVersionServiceListener(
			Map<String, Object[]> methodNameToParametersMap) {

			_methodNameToParametersMap = methodNameToParametersMap;
		}

		private final Map<String, Object[]> _methodNameToParametersMap;

	}

}