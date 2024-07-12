/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.exception.DuplicateFragmentEntryExternalReferenceCodeException;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.fragment.test.util.FragmentEntryTestUtil;
import com.liferay.fragment.test.util.FragmentTestUtil;
import com.liferay.fragment.util.comparator.FragmentEntryCreateDateComparator;
import com.liferay.fragment.util.comparator.FragmentEntryNameComparator;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.sql.Timestamp;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Kyle Miho
 */
@RunWith(Arquillian.class)
public class FragmentEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_fragmentCollection = FragmentTestUtil.addFragmentCollection(
			_group.getGroupId());

		_updatedFragmentCollection = FragmentTestUtil.addFragmentCollection(
			_group.getGroupId());
	}

	@Test
	public void testAddFragmentEntry() throws Exception {
		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), false, StringPool.BLANK, null, 0,
				false, FragmentConstants.TYPE_COMPONENT, null,
				WorkflowConstants.STATUS_APPROVED,
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), TestPropsValues.getUserId()));

		Assert.assertTrue(
			Validator.isNotNull(fragmentEntry.getExternalReferenceCode()));
	}

	@Test(expected = DuplicateFragmentEntryExternalReferenceCodeException.class)
	public void testAddFragmentEntryWithExistingExternalReferenceCode()
		throws Exception {

		String externalReferenceCode = RandomTestUtil.randomString();

		_fragmentEntryLocalService.addFragmentEntry(
			externalReferenceCode, TestPropsValues.getUserId(),
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), false, StringPool.BLANK, null, 0,
			false, FragmentConstants.TYPE_COMPONENT, null,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));
		_fragmentEntryLocalService.addFragmentEntry(
			externalReferenceCode, TestPropsValues.getUserId(),
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), false, StringPool.BLANK, null, 0,
			false, FragmentConstants.TYPE_COMPONENT, null,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));
	}

	@Test
	public void testAddFragmentEntryWithFragmentEntryKeyNameCssHtmlJsConfigurationPreviewFileEntryIdTypeAndStatus()
		throws Exception {

		String fragmentEntryKey = RandomTestUtil.randomString();
		String name = RandomTestUtil.randomString();
		String css = RandomTestUtil.randomString();
		String html = RandomTestUtil.randomString();
		String js = RandomTestUtil.randomString();
		String configuration = _read("configuration-valid-complete.json");
		long previewFileEntryId = RandomTestUtil.randomLong();
		int type = FragmentConstants.TYPE_COMPONENT;
		int status = WorkflowConstants.STATUS_APPROVED;

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(), fragmentEntryKey,
				name, css, html, js, false, configuration, null,
				previewFileEntryId, false, type, null, status,
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), TestPropsValues.getUserId()));

		Assert.assertEquals(
			StringUtil.toLowerCase(fragmentEntryKey),
			fragmentEntry.getFragmentEntryKey());
		Assert.assertEquals(name, fragmentEntry.getName());
		Assert.assertEquals(css, fragmentEntry.getCss());
		Assert.assertEquals(html, fragmentEntry.getHtml());
		Assert.assertEquals(js, fragmentEntry.getJs());
		Assert.assertEquals(configuration, fragmentEntry.getConfiguration());
		Assert.assertEquals(
			previewFileEntryId, fragmentEntry.getPreviewFileEntryId());
		Assert.assertEquals(type, fragmentEntry.getType());
		Assert.assertEquals(status, fragmentEntry.getStatus());
	}

	@Test
	public void testAddFragmentEntryWithFragmentEntryKeyNameCssHtmlJsPreviewFileEntryIdAndStatus()
		throws Exception {

		String fragmentEntryKey = RandomTestUtil.randomString();
		String name = RandomTestUtil.randomString();
		String css = RandomTestUtil.randomString();
		String html = RandomTestUtil.randomString();
		String js = RandomTestUtil.randomString();
		long previewFileEntryId = RandomTestUtil.randomLong();
		int status = WorkflowConstants.STATUS_APPROVED;

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(), fragmentEntryKey,
				name, css, html, js, false, StringPool.BLANK, null,
				previewFileEntryId, false, FragmentConstants.TYPE_COMPONENT,
				null, status,
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), TestPropsValues.getUserId()));

		Assert.assertEquals(
			StringUtil.toLowerCase(fragmentEntryKey),
			fragmentEntry.getFragmentEntryKey());
		Assert.assertEquals(name, fragmentEntry.getName());
		Assert.assertEquals(css, fragmentEntry.getCss());
		Assert.assertEquals(html, fragmentEntry.getHtml());
		Assert.assertEquals(js, fragmentEntry.getJs());
		Assert.assertEquals(
			previewFileEntryId, fragmentEntry.getPreviewFileEntryId());
		Assert.assertEquals(status, fragmentEntry.getStatus());
	}

	@Test
	public void testAddFragmentEntryWithFragmentEntryKeyNameCssHtmlJsPreviewFileEntryIdTypeAndStatus()
		throws Exception {

		String fragmentEntryKey = RandomTestUtil.randomString();
		String name = RandomTestUtil.randomString();
		String css = RandomTestUtil.randomString();
		String html = RandomTestUtil.randomString();
		String js = RandomTestUtil.randomString();
		long previewFileEntryId = RandomTestUtil.randomLong();
		int type = FragmentConstants.TYPE_COMPONENT;
		int status = WorkflowConstants.STATUS_APPROVED;

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(), fragmentEntryKey,
				name, css, html, js, false, StringPool.BLANK, null,
				previewFileEntryId, false, type, null, status,
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), TestPropsValues.getUserId()));

		Assert.assertEquals(
			StringUtil.toLowerCase(fragmentEntryKey),
			fragmentEntry.getFragmentEntryKey());
		Assert.assertEquals(name, fragmentEntry.getName());
		Assert.assertEquals(css, fragmentEntry.getCss());
		Assert.assertEquals(html, fragmentEntry.getHtml());
		Assert.assertEquals(js, fragmentEntry.getJs());
		Assert.assertEquals(
			previewFileEntryId, fragmentEntry.getPreviewFileEntryId());
		Assert.assertEquals(type, fragmentEntry.getType());
		Assert.assertEquals(status, fragmentEntry.getStatus());
	}

	@Test
	public void testAddFragmentEntryWithFragmentEntryKeyNameCssHtmlJsTypeAndStatus()
		throws Exception {

		String fragmentEntryKey = RandomTestUtil.randomString();
		String name = RandomTestUtil.randomString();
		String css = RandomTestUtil.randomString();
		String html = RandomTestUtil.randomString();
		String js = RandomTestUtil.randomString();
		int type = FragmentConstants.TYPE_COMPONENT;
		int status = WorkflowConstants.STATUS_APPROVED;

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(), fragmentEntryKey,
				name, css, html, js, false, StringPool.BLANK, null, 0, false,
				type, null, status,
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), TestPropsValues.getUserId()));

		Assert.assertEquals(
			StringUtil.toLowerCase(fragmentEntryKey),
			fragmentEntry.getFragmentEntryKey());
		Assert.assertEquals(name, fragmentEntry.getName());
		Assert.assertEquals(css, fragmentEntry.getCss());
		Assert.assertEquals(html, fragmentEntry.getHtml());
		Assert.assertEquals(js, fragmentEntry.getJs());
		Assert.assertEquals(type, fragmentEntry.getType());
		Assert.assertEquals(status, fragmentEntry.getStatus());
	}

	@Test
	public void testAddFragmentEntryWithFragmentEntryKeyNamePreviewFileEntryIdTypeAndStatus()
		throws Exception {

		String fragmentEntryKey = RandomTestUtil.randomString();
		String name = RandomTestUtil.randomString();
		long previewFileEntryId = RandomTestUtil.randomLong();
		int type = FragmentConstants.TYPE_COMPONENT;
		int status = WorkflowConstants.STATUS_DRAFT;

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(), fragmentEntryKey,
				name, StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
				false, StringPool.BLANK, null, previewFileEntryId, false, type,
				null, status,
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), TestPropsValues.getUserId()));

		Assert.assertEquals(
			StringUtil.toLowerCase(fragmentEntryKey),
			fragmentEntry.getFragmentEntryKey());
		Assert.assertEquals(name, fragmentEntry.getName());
		Assert.assertEquals(
			previewFileEntryId, fragmentEntry.getPreviewFileEntryId());
		Assert.assertEquals(type, fragmentEntry.getType());
		Assert.assertEquals(status, fragmentEntry.getStatus());
	}

	@Test
	public void testAddFragmentEntryWithHtmlWithAmpersand() throws Exception {
		String html = "<H1>A&B&amp;C</H1>";

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				StringPool.BLANK, html, StringPool.BLANK, false,
				StringPool.BLANK, null, RandomTestUtil.randomLong(), false,
				FragmentConstants.TYPE_COMPONENT, null,
				WorkflowConstants.STATUS_APPROVED,
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), TestPropsValues.getUserId()));

		Assert.assertEquals(html, fragmentEntry.getHtml());
	}

	@Test
	public void testAddFragmentEntryWithNameCssHtmlJsAndStatus()
		throws Exception {

		String name = RandomTestUtil.randomString();
		String css = RandomTestUtil.randomString();
		String html = RandomTestUtil.randomString();
		String js = RandomTestUtil.randomString();
		int status = WorkflowConstants.STATUS_APPROVED;

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
				RandomTestUtil.randomString(), name, css, html, js, false,
				StringPool.BLANK, null, RandomTestUtil.randomLong(), false,
				FragmentConstants.TYPE_COMPONENT, null, status,
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), TestPropsValues.getUserId()));

		Assert.assertEquals(name, fragmentEntry.getName());
		Assert.assertEquals(css, fragmentEntry.getCss());
		Assert.assertEquals(html, fragmentEntry.getHtml());
		Assert.assertEquals(js, fragmentEntry.getJs());
		Assert.assertEquals(status, fragmentEntry.getStatus());
	}

	@Test
	public void testAddFragmentEntryWithNameCssHtmlJsPreviewFileEntryIdAndStatus()
		throws Exception {

		String name = RandomTestUtil.randomString();
		String css = RandomTestUtil.randomString();
		String html = RandomTestUtil.randomString();
		String js = RandomTestUtil.randomString();
		long previewFileEntryId = RandomTestUtil.randomLong();
		int status = WorkflowConstants.STATUS_APPROVED;

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
				RandomTestUtil.randomString(), name, css, html, js, false,
				StringPool.BLANK, null, previewFileEntryId, false,
				FragmentConstants.TYPE_COMPONENT, null, status,
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), TestPropsValues.getUserId()));

		Assert.assertEquals(name, fragmentEntry.getName());
		Assert.assertEquals(css, fragmentEntry.getCss());
		Assert.assertEquals(html, fragmentEntry.getHtml());
		Assert.assertEquals(js, fragmentEntry.getJs());
		Assert.assertEquals(
			previewFileEntryId, fragmentEntry.getPreviewFileEntryId());
		Assert.assertEquals(status, fragmentEntry.getStatus());
	}

	@Test
	public void testAddFragmentEntryWithNameCssHtmlJsPreviewFileEntryIdTypeAndStatus()
		throws Exception {

		String name = RandomTestUtil.randomString();
		String css = RandomTestUtil.randomString();
		String html = RandomTestUtil.randomString();
		String js = RandomTestUtil.randomString();
		long previewFileEntryId = RandomTestUtil.randomLong();
		int type = FragmentConstants.TYPE_COMPONENT;
		int status = WorkflowConstants.STATUS_APPROVED;

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
				RandomTestUtil.randomString(), name, css, html, js, false,
				StringPool.BLANK, null, previewFileEntryId, false,
				FragmentConstants.TYPE_COMPONENT, null, status,
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), TestPropsValues.getUserId()));

		Assert.assertEquals(name, fragmentEntry.getName());
		Assert.assertEquals(css, fragmentEntry.getCss());
		Assert.assertEquals(html, fragmentEntry.getHtml());
		Assert.assertEquals(js, fragmentEntry.getJs());
		Assert.assertEquals(
			previewFileEntryId, fragmentEntry.getPreviewFileEntryId());
		Assert.assertEquals(type, fragmentEntry.getType());
		Assert.assertEquals(status, fragmentEntry.getStatus());
	}

	@Test
	public void testAddFragmentEntryWithNameCssHtmlJsTypeAndStatus()
		throws Exception {

		String name = RandomTestUtil.randomString();
		String css = RandomTestUtil.randomString();
		String html = RandomTestUtil.randomString();
		String js = RandomTestUtil.randomString();
		int type = FragmentConstants.TYPE_COMPONENT;
		int status = WorkflowConstants.STATUS_APPROVED;

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
				RandomTestUtil.randomString(), name, css, html, js, false,
				StringPool.BLANK, null, 0, false,
				FragmentConstants.TYPE_COMPONENT, null, status,
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), TestPropsValues.getUserId()));

		Assert.assertEquals(name, fragmentEntry.getName());
		Assert.assertEquals(css, fragmentEntry.getCss());
		Assert.assertEquals(html, fragmentEntry.getHtml());
		Assert.assertEquals(js, fragmentEntry.getJs());
		Assert.assertEquals(type, fragmentEntry.getType());
		Assert.assertEquals(status, fragmentEntry.getStatus());
	}

	@Test
	public void testAddInputFragmentEntry() throws Exception {
		String fragmentEntryKey = RandomTestUtil.randomString();

		String typeOptions = JSONUtil.put(
			"fieldTypes", JSONUtil.put("string")
		).toString();

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(), fragmentEntryKey,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				false, "{fieldSets: []}", null, 0, false,
				FragmentConstants.TYPE_INPUT, typeOptions,
				WorkflowConstants.STATUS_APPROVED,
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), TestPropsValues.getUserId()));

		Assert.assertEquals(typeOptions, fragmentEntry.getTypeOptions());
	}

	@Test
	public void testCopyFragmentEntry() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), false, StringPool.BLANK, null, 0,
				false, FragmentConstants.TYPE_COMPONENT, null,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		FragmentEntry copyFragmentEntry =
			_fragmentEntryLocalService.copyFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				fragmentEntry.getFragmentEntryId(),
				fragmentEntry.getFragmentCollectionId(), serviceContext);

		_assertCopyFragmentEntry(fragmentEntry, copyFragmentEntry);

		Assert.assertEquals(
			fragmentEntry.getFragmentCollectionId(),
			copyFragmentEntry.getFragmentCollectionId());
		Assert.assertEquals(
			StringBundler.concat(
				fragmentEntry.getName(), " (",
				_language.get(LocaleUtil.getSiteDefault(), "copy"), ")"),
			copyFragmentEntry.getName());
	}

	@Test
	public void testCopyFragmentEntryToDifferentCollection() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection targetFragmentCollection =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), false, StringPool.BLANK, null, 0,
				false, FragmentConstants.TYPE_COMPONENT, null,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		FragmentEntry copyFragmentEntry =
			_fragmentEntryLocalService.copyFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				fragmentEntry.getFragmentEntryId(),
				targetFragmentCollection.getFragmentCollectionId(),
				serviceContext);

		_assertCopyFragmentEntry(fragmentEntry, copyFragmentEntry);

		Assert.assertEquals(
			targetFragmentCollection.getFragmentCollectionId(),
			copyFragmentEntry.getFragmentCollectionId());
	}

	@Test
	public void testCopyInputFragmentEntry() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		String typeOptions = JSONUtil.put(
			"fieldTypes", JSONUtil.put("string")
		).toString();

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), false, StringPool.BLANK, null, 0,
				false, FragmentConstants.TYPE_INPUT, typeOptions,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		FragmentEntry copyFragmentEntry =
			_fragmentEntryLocalService.copyFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				fragmentEntry.getFragmentEntryId(),
				fragmentEntry.getFragmentCollectionId(), serviceContext);

		_assertCopyFragmentEntry(fragmentEntry, copyFragmentEntry);

		Assert.assertEquals(
			fragmentEntry.getFragmentCollectionId(),
			copyFragmentEntry.getFragmentCollectionId());

		Assert.assertEquals(typeOptions, copyFragmentEntry.getTypeOptions());
	}

	@Test
	public void testDeleteFragmentEntry() throws Exception {
		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		_fragmentEntryLocalService.deleteFragmentEntry(
			fragmentEntry.getFragmentEntryId());

		Assert.assertNull(
			_fragmentEntryLocalService.fetchFragmentEntry(
				fragmentEntry.getFragmentEntryId()));
	}

	@Test
	public void testDeleteFragmentEntryByExternalReferenceCode()
		throws Exception {

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), false, StringPool.BLANK, null, 0,
				false, FragmentConstants.TYPE_COMPONENT, null,
				WorkflowConstants.STATUS_APPROVED,
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), TestPropsValues.getUserId()));

		_fragmentEntryLocalService.deleteFragmentEntry(
			fragmentEntry.getExternalReferenceCode(),
			fragmentEntry.getGroupId());

		Assert.assertNull(
			_fragmentEntryLocalService.fetchFragmentEntry(
				fragmentEntry.getFragmentEntryId()));
	}

	@Test
	public void testFetchFragmentEntryByFragmentEntryId() throws Exception {
		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		Assert.assertNotNull(
			_fragmentEntryLocalService.fetchFragmentEntry(
				fragmentEntry.getFragmentEntryId()));
	}

	@Test
	public void testFetchFragmentEntryByGroupIdAndFragmentEntryKey()
		throws Exception {

		String fragmentEntryKey = RandomTestUtil.randomString();

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(), fragmentEntryKey,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				false, "{fieldSets: []}", null, 0, false,
				FragmentConstants.TYPE_COMPONENT, null,
				WorkflowConstants.STATUS_APPROVED,
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), TestPropsValues.getUserId()));

		Assert.assertEquals(fragmentEntry, fragmentEntry);
	}

	@Test
	public void testGenerateFragmentEntryKey() throws Exception {
		_fragmentEntryLocalService.addFragmentEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			_fragmentCollection.getFragmentCollectionId(), "test-key",
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), false,
			"{fieldSets: []}", null, 0, false, FragmentConstants.TYPE_COMPONENT,
			null, WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));

		String fragmentEntryKey =
			_fragmentEntryLocalService.generateFragmentEntryKey(
				_group.getGroupId(), "Test Key");

		Assert.assertEquals("test-key-0", fragmentEntryKey);
	}

	@Test
	public void testGetFragmentEntriesByFragmentCollectionId()
		throws Exception {

		List<FragmentEntry> originalFragmentEntries =
			_fragmentEntryLocalService.getFragmentEntries(
				_fragmentCollection.getFragmentCollectionId());

		FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());
		FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		List<FragmentEntry> actualFragmentEntries =
			_fragmentEntryLocalService.getFragmentEntries(
				_fragmentCollection.getFragmentCollectionId());

		Assert.assertEquals(
			actualFragmentEntries.toString(),
			originalFragmentEntries.size() + 2, actualFragmentEntries.size());
	}

	@Test
	public void testGetFragmentEntriesByGroupIdAndFragmentCollectionIdOrderByCreateDateComparator()
		throws Exception {

		LocalDateTime localDateTime = LocalDateTime.now();

		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId(), "AB Fragment Entry",
			Timestamp.valueOf(localDateTime));

		localDateTime = localDateTime.plus(1, ChronoUnit.SECONDS);

		FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId(), "AA Fragment Entry",
			Timestamp.valueOf(localDateTime));

		List<FragmentEntry> fragmentEntries =
			_fragmentEntryLocalService.getFragmentEntries(
				_group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				FragmentEntryCreateDateComparator.getInstance(true));

		FragmentEntry firstFragmentEntry = fragmentEntries.get(0);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			firstFragmentEntry.getName());

		fragmentEntries = _fragmentEntryLocalService.getFragmentEntries(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			FragmentEntryCreateDateComparator.getInstance(false));

		FragmentEntry lastFragmentEntry = fragmentEntries.get(
			fragmentEntries.size() - 1);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			lastFragmentEntry.getName());
	}

	@Test
	public void testGetFragmentEntriesByGroupIdAndFragmentCollectionIdOrderByNameComparator()
		throws Exception {

		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId(), "AB Fragment Entry");

		FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId(), "AA Fragment Entry");

		List<FragmentEntry> fragmentEntries =
			_fragmentEntryLocalService.getFragmentEntries(
				_group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				FragmentEntryNameComparator.getInstance(true));

		FragmentEntry lastFragmentEntry = fragmentEntries.get(
			fragmentEntries.size() - 1);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			lastFragmentEntry.getName());

		fragmentEntries = _fragmentEntryLocalService.getFragmentEntries(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			FragmentEntryNameComparator.getInstance(false));

		FragmentEntry firstFragmentEntry = fragmentEntries.get(0);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			firstFragmentEntry.getName());
	}

	@Test
	public void testGetFragmentEntriesByGroupIdFragmentCollectionIdAndNameOrderByCreateDateComparator()
		throws Exception {

		LocalDateTime localDateTime = LocalDateTime.now();

		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId(), "AC Fragment Entry",
			Timestamp.valueOf(localDateTime));

		localDateTime = localDateTime.plus(1, ChronoUnit.SECONDS);

		FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId(), "AA Fragment",
			Timestamp.valueOf(localDateTime));

		localDateTime = localDateTime.plus(1, ChronoUnit.SECONDS);

		FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId(), "AB Fragment Entry",
			Timestamp.valueOf(localDateTime));

		List<FragmentEntry> fragmentEntries =
			_fragmentEntryLocalService.getFragmentEntries(
				_group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(), "Entry",
				QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				FragmentEntryCreateDateComparator.getInstance(true));

		FragmentEntry firstFragmentEntry = fragmentEntries.get(0);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			firstFragmentEntry.getName());

		fragmentEntries = _fragmentEntryLocalService.getFragmentEntries(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			"Entry", QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			FragmentEntryCreateDateComparator.getInstance(false));

		FragmentEntry lastFragmentEntry = fragmentEntries.get(
			fragmentEntries.size() - 1);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			lastFragmentEntry.getName());
	}

	@Test
	public void testGetFragmentEntriesByGroupIdFragmentCollectionIdAndNameOrderByNameComparator()
		throws Exception {

		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId(), "AB Fragment Entry");

		FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId(), "AA Fragment");
		FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId(), "AC Fragment Entry");

		List<FragmentEntry> fragmentEntries =
			_fragmentEntryLocalService.getFragmentEntries(
				_group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(), "Entry",
				QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				FragmentEntryNameComparator.getInstance(true));

		FragmentEntry firstFragmentEntry = fragmentEntries.get(0);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			firstFragmentEntry.getName());

		fragmentEntries = _fragmentEntryLocalService.getFragmentEntries(
			_group.getGroupId(), _fragmentCollection.getFragmentCollectionId(),
			"Entry", QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			FragmentEntryNameComparator.getInstance(false));

		FragmentEntry lastFragmentEntry = fragmentEntries.get(
			fragmentEntries.size() - 1);

		Assert.assertEquals(
			fragmentEntries.toString(), fragmentEntry.getName(),
			lastFragmentEntry.getName());
	}

	@Test
	public void testGetFragmentEntriesByGroupIdFragmentCollectionIdAndStatus()
		throws Exception {

		List<FragmentEntry> originalFragmentEntries =
			_fragmentEntryLocalService.getFragmentEntries(
				_group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
				WorkflowConstants.STATUS_DRAFT);

		FragmentEntryTestUtil.addFragmentEntryByStatus(
			_fragmentCollection.getFragmentCollectionId(),
			WorkflowConstants.STATUS_APPROVED);
		FragmentEntryTestUtil.addFragmentEntryByStatus(
			_fragmentCollection.getFragmentCollectionId(),
			WorkflowConstants.STATUS_DRAFT);
		FragmentEntryTestUtil.addFragmentEntryByStatus(
			_fragmentCollection.getFragmentCollectionId(),
			WorkflowConstants.STATUS_DRAFT);

		List<FragmentEntry> actualFragmentEntries =
			_fragmentEntryLocalService.getFragmentEntries(
				_group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
				WorkflowConstants.STATUS_DRAFT);

		Assert.assertEquals(
			actualFragmentEntries.toString(),
			originalFragmentEntries.size() + 2, actualFragmentEntries.size());
	}

	@Test
	public void testGetFragmentEntriesByUuidAndCompanyId() throws Exception {
		Group group = GroupTestUtil.addGroup();

		try {
			FragmentEntry fragmentEntry1 =
				FragmentEntryTestUtil.addFragmentEntry(
					_fragmentCollection.getFragmentCollectionId(),
					RandomTestUtil.randomString());

			List<FragmentEntry> fragmentEntries1 =
				_fragmentEntryLocalService.getFragmentEntriesByUuidAndCompanyId(
					fragmentEntry1.getUuid(), _group.getCompanyId());

			Assert.assertEquals(
				fragmentEntries1.toString(), 1, fragmentEntries1.size());
			Assert.assertEquals(fragmentEntry1, fragmentEntries1.get(0));

			FragmentCollection fragmentCollection =
				FragmentTestUtil.addFragmentCollection(group.getGroupId());

			FragmentEntry fragmentEntry2 =
				FragmentEntryTestUtil.addFragmentEntry(
					fragmentCollection.getFragmentCollectionId(),
					RandomTestUtil.randomString());

			List<FragmentEntry> fragmentEntries2 =
				_fragmentEntryLocalService.getFragmentEntriesByUuidAndCompanyId(
					fragmentEntry2.getUuid(), group.getCompanyId());

			Assert.assertEquals(
				fragmentEntries2.toString(), 1, fragmentEntries2.size());
			Assert.assertEquals(fragmentEntry2, fragmentEntries2.get(0));
		}
		finally {
			GroupLocalServiceUtil.deleteGroup(group);
		}
	}

	@Test
	public void testGetFragmentEntriesCount() throws Exception {
		int originalCount = _fragmentEntryLocalService.getFragmentEntriesCount(
			_fragmentCollection.getFragmentCollectionId());

		FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());
		FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		int actualCount = _fragmentEntryLocalService.getFragmentEntriesCount(
			_fragmentCollection.getFragmentCollectionId());

		Assert.assertEquals(originalCount + 2, actualCount);
	}

	@Test
	public void testMoveFragmentEntry() throws Exception {
		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		FragmentCollection targetFragmentCollection =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		fragmentEntry = _fragmentEntryLocalService.moveFragmentEntry(
			fragmentEntry.getFragmentEntryId(),
			targetFragmentCollection.getFragmentCollectionId());

		Assert.assertEquals(
			targetFragmentCollection.getFragmentCollectionId(),
			fragmentEntry.getFragmentCollectionId());
	}

	@Test
	public void testUpdateFragmentCollectionId() throws Exception {
		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), "<H1>A</H1>",
				RandomTestUtil.randomString(), false, StringPool.BLANK, null, 0,
				false, FragmentConstants.TYPE_COMPONENT, null,
				WorkflowConstants.STATUS_APPROVED,
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), TestPropsValues.getUserId()));

		fragmentEntry = _fragmentEntryLocalService.updateFragmentEntry(
			fragmentEntry.getUserId(), fragmentEntry.getFragmentEntryId(),
			_updatedFragmentCollection.getFragmentCollectionId(),
			fragmentEntry.getName(), fragmentEntry.getCss(),
			fragmentEntry.getHtml(), fragmentEntry.getJs(), false, null,
			fragmentEntry.getIcon(), fragmentEntry.getPreviewFileEntryId(),
			false, fragmentEntry.getTypeOptions(),
			WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(
			_updatedFragmentCollection.getFragmentCollectionId(),
			fragmentEntry.getFragmentCollectionId());
	}

	@Test
	public void testUpdateFragmentEntryName() throws Exception {
		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString());

		fragmentEntry = _fragmentEntryLocalService.updateFragmentEntry(
			fragmentEntry.getFragmentEntryId(), "Fragment Name Updated");

		Assert.assertEquals("Fragment Name Updated", fragmentEntry.getName());
	}

	@Test
	public void testUpdateFragmentEntryNameCssHtmlJsConfigurationAndStatus()
		throws Exception {

		String name = RandomTestUtil.randomString();
		String css = RandomTestUtil.randomString();
		String html = RandomTestUtil.randomString();
		String js = RandomTestUtil.randomString();
		String configuration = _read("configuration-valid-complete.json");
		int status = WorkflowConstants.STATUS_APPROVED;

		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		fragmentEntry = _fragmentEntryLocalService.updateFragmentEntry(
			TestPropsValues.getUserId(), fragmentEntry.getFragmentEntryId(),
			fragmentEntry.getFragmentCollectionId(), name, css, html, js,
			fragmentEntry.isCacheable(), configuration, fragmentEntry.getIcon(),
			0, false, fragmentEntry.getTypeOptions(), status);

		Assert.assertEquals(name, fragmentEntry.getName());
		Assert.assertEquals(css, fragmentEntry.getCss());
		Assert.assertEquals(html, fragmentEntry.getHtml());
		Assert.assertEquals(js, fragmentEntry.getJs());
		Assert.assertEquals(configuration, fragmentEntry.getConfiguration());
		Assert.assertEquals(status, fragmentEntry.getStatus());
	}

	@Test
	public void testUpdateFragmentEntryNameCssHtmlJsConfigurationPreviewFileEntryIdAndStatus()
		throws Exception {

		String name = RandomTestUtil.randomString();
		String css = RandomTestUtil.randomString();
		String html = RandomTestUtil.randomString();
		String js = RandomTestUtil.randomString();
		String configuration = _read("configuration-valid-complete.json");
		long previewFileEntryId = RandomTestUtil.randomLong();
		int status = WorkflowConstants.STATUS_APPROVED;

		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		fragmentEntry = _fragmentEntryLocalService.updateFragmentEntry(
			TestPropsValues.getUserId(), fragmentEntry.getFragmentEntryId(),
			fragmentEntry.getFragmentCollectionId(), name, css, html, js,
			fragmentEntry.isCacheable(), configuration, fragmentEntry.getIcon(),
			previewFileEntryId, false, fragmentEntry.getTypeOptions(), status);

		Assert.assertEquals(name, fragmentEntry.getName());
		Assert.assertEquals(css, fragmentEntry.getCss());
		Assert.assertEquals(html, fragmentEntry.getHtml());
		Assert.assertEquals(js, fragmentEntry.getJs());
		Assert.assertEquals(configuration, fragmentEntry.getConfiguration());
		Assert.assertEquals(
			previewFileEntryId, fragmentEntry.getPreviewFileEntryId());
		Assert.assertEquals(status, fragmentEntry.getStatus());
	}

	@Test
	public void testUpdateFragmentEntryPreviewFileEntryId() throws Exception {
		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		long previewFileEntryId = fragmentEntry.getPreviewFileEntryId();

		fragmentEntry = _fragmentEntryLocalService.updateFragmentEntry(
			fragmentEntry.getFragmentEntryId(), previewFileEntryId + 1);

		Assert.assertEquals(
			previewFileEntryId + 1, fragmentEntry.getPreviewFileEntryId());
	}

	@Test
	public void testUpdateFragmentEntryWithCacheable() throws Exception {
		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		fragmentEntry = _fragmentEntryLocalService.updateFragmentEntry(
			TestPropsValues.getUserId(), fragmentEntry.getFragmentEntryId(),
			fragmentEntry.getFragmentCollectionId(), fragmentEntry.getName(),
			fragmentEntry.getCss(), fragmentEntry.getHtml(),
			fragmentEntry.getJs(), true, fragmentEntry.getConfiguration(),
			fragmentEntry.getIcon(), fragmentEntry.getPreviewFileEntryId(),
			fragmentEntry.isReadOnly(), fragmentEntry.getTypeOptions(),
			fragmentEntry.getStatus());

		Assert.assertTrue(fragmentEntry.isCacheable());
	}

	@Test
	public void testUpdateFragmentEntryWithHtmlWithAmpersand()
		throws Exception {

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), "<H1>A</H1>",
				RandomTestUtil.randomString(), false, StringPool.BLANK, null,
				RandomTestUtil.randomLong(), false,
				FragmentConstants.TYPE_COMPONENT, null,
				WorkflowConstants.STATUS_APPROVED,
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), TestPropsValues.getUserId()));

		String html = "<H1>A&B&amp;C</H1>";

		fragmentEntry = _fragmentEntryLocalService.updateFragmentEntry(
			TestPropsValues.getUserId(), fragmentEntry.getFragmentEntryId(),
			fragmentEntry.getFragmentCollectionId(), fragmentEntry.getName(),
			fragmentEntry.getCss(), html, fragmentEntry.getJs(),
			fragmentEntry.isCacheable(), null, fragmentEntry.getIcon(),
			fragmentEntry.getPreviewFileEntryId(), false,
			fragmentEntry.getTypeOptions(), WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(html, fragmentEntry.getHtml());
	}

	@Test
	public void testUpdateFragmentEntryWithPreviewFileEntryId()
		throws Exception {

		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		long previewFileEntryId = fragmentEntry.getPreviewFileEntryId();

		fragmentEntry = _fragmentEntryLocalService.updateFragmentEntry(
			TestPropsValues.getUserId(), fragmentEntry.getFragmentEntryId(),
			fragmentEntry.getFragmentCollectionId(), fragmentEntry.getName(),
			fragmentEntry.getCss(), fragmentEntry.getHtml(),
			fragmentEntry.getJs(), fragmentEntry.isCacheable(),
			fragmentEntry.getConfiguration(), fragmentEntry.getIcon(),
			previewFileEntryId + 1, fragmentEntry.isReadOnly(),
			fragmentEntry.getTypeOptions(), fragmentEntry.getStatus());

		Assert.assertEquals(
			previewFileEntryId + 1, fragmentEntry.getPreviewFileEntryId());
	}

	private void _assertCopyFragmentEntry(
		FragmentEntry fragmentEntry, FragmentEntry copyFragmentEntry) {

		Assert.assertEquals(
			fragmentEntry.getGroupId(), copyFragmentEntry.getGroupId());
		Assert.assertEquals(
			fragmentEntry.getName() + " (Copy)", copyFragmentEntry.getName());
		Assert.assertEquals(fragmentEntry.getCss(), copyFragmentEntry.getCss());
		Assert.assertEquals(
			fragmentEntry.getHtml(), copyFragmentEntry.getHtml());
		Assert.assertEquals(fragmentEntry.getJs(), copyFragmentEntry.getJs());
		Assert.assertEquals(
			fragmentEntry.getStatus(), copyFragmentEntry.getStatus());
		Assert.assertEquals(
			fragmentEntry.getType(), copyFragmentEntry.getType());
	}

	private String _read(String fileName) throws Exception {
		return new String(
			FileUtil.getBytes(getClass(), "dependencies/" + fileName));
	}

	private FragmentCollection _fragmentCollection;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private Language _language;

	private FragmentCollection _updatedFragmentCollection;

}