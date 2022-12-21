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

package com.liferay.layout.utility.page.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.importer.LayoutsImporter;
import com.liferay.layout.importer.LayoutsImporterResultEntry;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.File;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Bárbara Cabrera
 */
@RunWith(Arquillian.class)
public class ExportImportLayoutUtilityPageTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group1 = GroupTestUtil.addGroup();
		_group2 = GroupTestUtil.addGroup();

		_serviceContext1 = ServiceContextTestUtil.getServiceContext(
			_group1, TestPropsValues.getUserId());
		_serviceContext2 = ServiceContextTestUtil.getServiceContext(
			_group2, TestPropsValues.getUserId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext1);
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testExportImportLayoutUtilityPageEntry() throws Exception {
		String externalReferenceCode = RandomTestUtil.randomString();

		String type = "LAYOUT";

		LayoutUtilityPageEntry layoutUtilityPageEntry1 =
			_layoutUtilityPageEntryLocalService.addLayoutUtilityPageEntry(
				externalReferenceCode, _serviceContext1.getUserId(),
				_serviceContext1.getScopeGroupId(), 0, 0, false,
				StringUtil.randomString(), type, 0);

		Repository repository = PortletFileRepositoryUtil.addPortletRepository(
			_group1.getGroupId(), RandomTestUtil.randomString(),
			_serviceContext1);

		Class<?> clazz = getClass();

		FileEntry fileEntry = PortletFileRepositoryUtil.addPortletFileEntry(
			null, _group1.getGroupId(), TestPropsValues.getUserId(),
			LayoutUtilityPageEntry.class.getName(),
			layoutUtilityPageEntry1.getLayoutUtilityPageEntryId(),
			RandomTestUtil.randomString(), repository.getDlFolderId(),
			clazz.getResourceAsStream("dependencies/thumbnail.png"),
			layoutUtilityPageEntry1.getLayoutUtilityPageEntryId() +
				"_preview.png",
			ContentTypes.IMAGE_PNG, false);

		layoutUtilityPageEntry1 =
			_layoutUtilityPageEntryLocalService.updateLayoutUtilityPageEntry(
				layoutUtilityPageEntry1.getLayoutUtilityPageEntryId(),
				fileEntry.getFileEntryId());

		Assert.assertNotEquals(
			0, layoutUtilityPageEntry1.getPreviewFileEntryId());

		File file = ReflectionTestUtil.invoke(
			_mvcResourceCommand, "_getFile", new Class<?>[] {long[].class},
			new long[] {layoutUtilityPageEntry1.getLayoutUtilityPageEntryId()});

		List<LayoutsImporterResultEntry> layoutsImporterResultEntries = null;

		ServiceContextThreadLocal.pushServiceContext(_serviceContext2);

		try {
			layoutsImporterResultEntries = _layoutsImporter.importFile(
				TestPropsValues.getUserId(), _group2.getGroupId(), 0, file,
				false);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		Assert.assertNotNull(layoutsImporterResultEntries);

		Assert.assertEquals(
			layoutsImporterResultEntries.toString(), 1,
			layoutsImporterResultEntries.size());

		LayoutsImporterResultEntry layoutUtilityPageImportEntry =
			layoutsImporterResultEntries.get(0);

		Assert.assertEquals(
			LayoutsImporterResultEntry.Status.IMPORTED,
			layoutUtilityPageImportEntry.getStatus());

		LayoutUtilityPageEntry layoutUtilityPageEntry2 =
			_layoutUtilityPageEntryLocalService.
				fetchLayoutUtilityPageEntryByExternalReferenceCode(
					externalReferenceCode, _group2.getGroupId());

		Assert.assertNotNull(layoutUtilityPageEntry2);

		Assert.assertEquals(
			layoutUtilityPageEntry1.getName(),
			layoutUtilityPageEntry2.getName());
		Assert.assertEquals(
			layoutUtilityPageEntry1.getType(),
			layoutUtilityPageEntry2.getType());

		Assert.assertNotEquals(
			0, layoutUtilityPageEntry2.getPreviewFileEntryId());
	}

	@DeleteAfterTestRun
	private Group _group1;

	@DeleteAfterTestRun
	private Group _group2;

	@Inject
	private LayoutsImporter _layoutsImporter;

	@Inject
	private LayoutUtilityPageEntryLocalService
		_layoutUtilityPageEntryLocalService;

	@Inject(
		filter = "mvc.command.name=/layout_admin/export_layout_utility_page_entries"
	)
	private MVCResourceCommand _mvcResourceCommand;

	private ServiceContext _serviceContext1;
	private ServiceContext _serviceContext2;

}