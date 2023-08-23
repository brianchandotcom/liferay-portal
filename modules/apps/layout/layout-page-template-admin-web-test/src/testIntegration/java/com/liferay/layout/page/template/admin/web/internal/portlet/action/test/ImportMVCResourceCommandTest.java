/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.admin.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.File;

import java.net.URL;

import java.util.Enumeration;
import java.util.Locale;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Víctor Galán
 */
@RunWith(Arquillian.class)
public class ImportMVCResourceCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_bundle = FrameworkUtil.getBundle(getClass());

		_group = GroupTestUtil.addGroup();

		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void test() throws Exception {
		_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
			TestPropsValues.getUserId(), _group.getGroupId(), 0,
			"Existing Master Page",
			LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT, 0,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_mvcResourceCommand, "_importFile",
			new Class<?>[] {
				File.class, long.class, long.class, Locale.class, boolean.class,
				long.class
			},
			_getFile(), _group.getGroupId(), 0, LocaleUtil.US, false,
			TestPropsValues.getUserId());

		JSONObject importResultsJSONObject = jsonObject.getJSONObject(
			"importResults");

		JSONArray invalidJSONArray = importResultsJSONObject.getJSONArray(
			"error");

		Assert.assertEquals(1, invalidJSONArray.length());

		JSONArray importedJSONArray = importResultsJSONObject.getJSONArray(
			"success");

		Assert.assertEquals(2, importedJSONArray.length());

		JSONArray ignoredJSONArray = importResultsJSONObject.getJSONArray(
			"warning");

		Assert.assertEquals(1, ignoredJSONArray.length());
	}

	private File _getFile() throws Exception {
		Enumeration<URL> enumeration = _bundle.findEntries(
			_RESOURCES_PATH, "*", true);

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		while (enumeration.hasMoreElements()) {
			URL url = enumeration.nextElement();

			String path = url.getPath();

			if (!path.endsWith(StringPool.SLASH)) {
				zipWriter.addEntry(
					StringUtil.removeSubstring(url.getPath(), _RESOURCES_PATH),
					url.openStream());
			}
		}

		return zipWriter.getFile();
	}

	private static final String _RESOURCES_PATH =
		"com/liferay/layout/page/template/admin/web/internal/portlet/action" +
			"/test/dependencies/import";

	private Bundle _bundle;
	private Group _group;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject(filter = "mvc.command.name=/layout_page_template_admin/import")
	private MVCResourceCommand _mvcResourceCommand;

}