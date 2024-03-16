/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.exception.LARTypeException;
import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.test.util.lar.BaseExportImportTestCase;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.persistence.FragmentEntryLinkUtil;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.io.InputStream;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eduardo García
 */
@RunWith(Arquillian.class)
public class LayoutExportImportTest extends BaseExportImportTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		UserTestUtil.setUser(TestPropsValues.getUser());
	}

	@Test
	public void testDeleteMissingLayouts() throws Exception {
		Layout layout1 = LayoutTestUtil.addTypePortletLayout(group);
		Layout layout2 = LayoutTestUtil.addTypePortletLayout(group);

		long[] layoutIds = ExportImportHelperUtil.getLayoutIds(
			LayoutLocalServiceUtil.getLayouts(group.getGroupId(), false));

		exportImportLayouts(layoutIds, getImportParameterMap());

		Assert.assertEquals(
			LayoutLocalServiceUtil.getLayoutsCount(group, false),
			LayoutLocalServiceUtil.getLayoutsCount(importedGroup, false));

		LayoutTestUtil.addTypePortletLayout(importedGroup);

		Map<String, String[]> parameterMap = getImportParameterMap();

		parameterMap.put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			new String[] {Boolean.TRUE.toString()});

		layoutIds = new long[] {layout1.getLayoutId()};

		exportImportLayouts(layoutIds, getImportParameterMap());

		Assert.assertEquals(
			LayoutLocalServiceUtil.getLayoutsCount(group, false),
			LayoutLocalServiceUtil.getLayoutsCount(importedGroup, false));

		Layout importedLayout1 =
			LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				layout1.getUuid(), importedGroup.getGroupId(), false);

		Assert.assertNotNull(importedLayout1);

		Layout importedLayout2 =
			LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				layout2.getUuid(), importedGroup.getGroupId(), false);

		Assert.assertNotNull(importedLayout2);
	}

	@Test
	public void testExportImportCompanyGroupInvalidLARType() throws Exception {

		// Import a layout set to a company layout set

		Group originalImportedGroup = importedGroup;
		Group originalGroup = group;

		Company company = CompanyLocalServiceUtil.getCompany(
			TestPropsValues.getCompanyId());

		importedGroup = company.getGroup();

		long[] layoutIds = new long[0];

		try {
			exportImportLayouts(layoutIds, getImportParameterMap(), true);

			Assert.fail();
		}
		catch (LARTypeException larTypeException) {
			if (_log.isDebugEnabled()) {
				_log.debug(larTypeException);
			}
		}
		finally {
			importedGroup = originalImportedGroup;
		}

		// Import a company layout set to a layout set

		group = company.getGroup();
		importedGroup = originalGroup;

		try {
			exportImportLayouts(layoutIds, getImportParameterMap(), true);

			Assert.fail();
		}
		catch (LARTypeException larTypeException) {
			if (_log.isDebugEnabled()) {
				_log.debug(larTypeException);
			}
		}
		finally {
			importedGroup = originalImportedGroup;
			group = originalGroup;
		}
	}

	@Test
	public void testExportImportLayoutFromMasterLayoutPageTemplateAndDraftLayoutMappingOnImportSide()
		throws Exception {

		// This line is needed to reproduce LPD-18967

		LayoutTestUtil.addTypePortletLayout(group, true);

		LayoutPageTemplateEntry masterLayoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				TestPropsValues.getUserId(), group.getGroupId(), 0,
				"Test Master Page",
				LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT, 0,
				WorkflowConstants.STATUS_APPROVED,
				ServiceContextTestUtil.getServiceContext(group.getGroupId()));

		FragmentEntryLink fragmentEntryLink = FragmentEntryLinkUtil.create(
			RandomTestUtil.randomLong());

		Layout masterPageTemplateLayout = LayoutLocalServiceUtil.getLayout(
			masterLayoutPageTemplateEntry.getPlid());

		fragmentEntryLink.setPlid(
			masterPageTemplateLayout.fetchDraftLayout(
			).getPlid());

		Layout contentLayout = LayoutTestUtil.addTypeContentLayout(
			group, "Test Page From Master Layout Page Template");

		String editableValuesJSON = _getContent(
			"fragment_entry_link_editable_values_with_configuration.json");

		String replacedValues = StringUtil.replace(
			editableValuesJSON, "$GROUP_ID",
			String.valueOf(group.getGroupId()));

		replacedValues = StringUtil.replace(
			replacedValues, "$LAYOUT_ID",
			String.valueOf(contentLayout.getLayoutId()));
		replacedValues = StringUtil.replace(
			replacedValues, "$LAYOUT_UUID",
			String.valueOf(contentLayout.getUuid()));
		replacedValues = StringUtil.replace(
			replacedValues, "$TITLE", contentLayout.getName("en_US"));

		fragmentEntryLink.setEditableValues(replacedValues);

		fragmentEntryLink.setSegmentsExperienceId(
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				masterPageTemplateLayout.getPlid()));

		fragmentEntryLink.setGroupId(group.getGroupId());

		_fragmentEntryLinkLocalService.updateFragmentEntryLink(
			fragmentEntryLink);

		exportImportLayouts(
			new long[] {contentLayout.getLayoutId()}, getImportParameterMap());

		Layout importedLayout =
			LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				contentLayout.getUuid(), importedGroup.getGroupId(), false);

		Layout importedDraftLayout = importedLayout.fetchDraftLayout();

		Assert.assertTrue(importedDraftLayout.isDraftLayout());
		Assert.assertEquals(
			importedLayout.getName(), importedDraftLayout.getName());

		Layout importedMasterPageTemplateLayout =
			LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				masterPageTemplateLayout.getUuid(), importedGroup.getGroupId(),
				true);

		Layout importedDraftLayoutOfMasterPageTemplate =
			importedMasterPageTemplateLayout.fetchDraftLayout();

		Assert.assertTrue(
			importedDraftLayoutOfMasterPageTemplate.isDraftLayout());
		Assert.assertEquals(
			importedMasterPageTemplateLayout.getName(),
			importedDraftLayoutOfMasterPageTemplate.getName());
	}

	@Test
	public void testExportImportLayoutPrototypeInvalidLARType()
		throws Exception {

		// Import a layout prototype to a layout set

		LayoutPrototype layoutPrototype = LayoutTestUtil.addLayoutPrototype(
			RandomTestUtil.randomString());

		group = layoutPrototype.getGroup();

		importedGroup = GroupTestUtil.addGroup();

		long[] layoutIds = new long[0];

		try {
			exportImportLayouts(layoutIds, getImportParameterMap(), true);

			Assert.fail();
		}
		catch (LARTypeException larTypeException) {
			if (_log.isDebugEnabled()) {
				_log.debug(larTypeException);
			}
		}

		// Import a layout prototype to a layout set pototype

		LayoutSetPrototype layoutSetPrototype =
			LayoutTestUtil.addLayoutSetPrototype(RandomTestUtil.randomString());

		importedGroup = layoutSetPrototype.getGroup();

		try {
			exportImportLayouts(layoutIds, getImportParameterMap(), true);

			Assert.fail();
		}
		catch (LARTypeException larTypeException) {
			if (_log.isDebugEnabled()) {
				_log.debug(larTypeException);
			}
		}
		finally {
			LayoutSetPrototypeLocalServiceUtil.deleteLayoutSetPrototype(
				layoutSetPrototype);

			importedGroup = null;
		}
	}

	@Test
	public void testExportImportLayouts() throws Exception {
		LayoutTestUtil.addTypePortletLayout(group);

		exportImportLayouts(
			ExportImportHelperUtil.getLayoutIds(
				LayoutLocalServiceUtil.getLayouts(group.getGroupId(), false)),
			getImportParameterMap());

		Assert.assertEquals(
			LayoutLocalServiceUtil.getLayoutsCount(group, false),
			LayoutLocalServiceUtil.getLayoutsCount(importedGroup, false));
	}

	@Test
	public void testExportImportLayoutSetInvalidLARType() throws Exception {

		// Import a layout set to a layout prototype

		LayoutPrototype layoutPrototype = LayoutTestUtil.addLayoutPrototype(
			RandomTestUtil.randomString());

		importedGroup = layoutPrototype.getGroup();

		long[] layoutIds = new long[0];

		try {
			exportImportLayouts(layoutIds, getImportParameterMap(), true);

			Assert.fail();
		}
		catch (LARTypeException larTypeException) {
			if (_log.isDebugEnabled()) {
				_log.debug(larTypeException);
			}
		}

		// Import a layout set to a layout set prototype

		LayoutSetPrototype layoutSetPrototype =
			LayoutTestUtil.addLayoutSetPrototype(RandomTestUtil.randomString());

		importedGroup = layoutSetPrototype.getGroup();

		try {
			exportImportLayouts(layoutIds, getImportParameterMap(), true);

			Assert.fail();
		}
		catch (LARTypeException larTypeException) {
			if (_log.isDebugEnabled()) {
				_log.debug(larTypeException);
			}
		}
		finally {
			LayoutSetPrototypeLocalServiceUtil.deleteLayoutSetPrototype(
				layoutSetPrototype);

			importedGroup = null;
		}
	}

	@Test
	public void testExportImportLayoutSetPrototypeInvalidLARType()
		throws Exception {

		// Import a layout set prototype to a layout set

		LayoutSetPrototype layoutSetPrototype =
			LayoutTestUtil.addLayoutSetPrototype(RandomTestUtil.randomString());

		try {
			group = layoutSetPrototype.getGroup();
			importedGroup = GroupTestUtil.addGroup();

			long[] layoutIds = new long[0];

			try {
				exportImportLayouts(layoutIds, getImportParameterMap(), true);

				Assert.fail();
			}
			catch (LARTypeException larTypeException) {
				if (_log.isDebugEnabled()) {
					_log.debug(larTypeException);
				}
			}

			// Import a layout set prototype to a layout prototyope

			LayoutPrototype layoutPrototype = LayoutTestUtil.addLayoutPrototype(
				RandomTestUtil.randomString());

			importedGroup = layoutPrototype.getGroup();

			try {
				exportImportLayouts(layoutIds, getImportParameterMap(), true);

				Assert.fail();
			}
			catch (LARTypeException larTypeException) {
				if (_log.isDebugEnabled()) {
					_log.debug(larTypeException);
				}
			}
		}
		finally {
			LayoutSetPrototypeLocalServiceUtil.deleteLayoutSetPrototype(
				layoutSetPrototype);

			group = null;
		}
	}

	@Test
	public void testExportImportLayoutsInvalidAvailableLocales()
		throws Exception {

		testAvailableLocales(
			Arrays.asList(LocaleUtil.US, LocaleUtil.SPAIN),
			Arrays.asList(LocaleUtil.US, LocaleUtil.GERMANY), true);
	}

	@Test
	public void testExportImportLayoutsPriorities() throws Exception {
		Layout layout1 = LayoutTestUtil.addTypePortletLayout(group);
		Layout layout2 = LayoutTestUtil.addTypePortletLayout(group);
		Layout layout3 = LayoutTestUtil.addTypePortletLayout(group);

		int priority = layout1.getPriority();

		layout1.setPriority(layout3.getPriority());

		layout3.setPriority(priority);

		layout1 = LayoutLocalServiceUtil.updateLayout(layout1);
		layout3 = LayoutLocalServiceUtil.updateLayout(layout3);

		long[] layoutIds = {layout1.getLayoutId(), layout2.getLayoutId()};

		exportImportLayouts(layoutIds, getImportParameterMap());

		Layout importedLayout1 =
			LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				layout1.getUuid(), importedGroup.getGroupId(), false);

		Assert.assertNotEquals(
			layout1.getPriority(), importedLayout1.getPriority());

		Layout importedLayout2 =
			LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				layout2.getUuid(), importedGroup.getGroupId(), false);

		Assert.assertNotEquals(
			layout2.getPriority(), importedLayout2.getPriority());

		exportImportLayouts(
			ExportImportHelperUtil.getLayoutIds(
				LayoutLocalServiceUtil.getLayouts(group.getGroupId(), false)),
			getImportParameterMap());

		importedLayout1 = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
			layout1.getUuid(), importedGroup.getGroupId(), false);

		Assert.assertEquals(
			layout1.getPriority(), importedLayout1.getPriority());

		importedLayout2 = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
			layout2.getUuid(), importedGroup.getGroupId(), false);

		Assert.assertEquals(
			layout2.getPriority(), importedLayout2.getPriority());

		Layout importedLayout3 =
			LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				layout3.getUuid(), importedGroup.getGroupId(), false);

		Assert.assertEquals(
			layout3.getPriority(), importedLayout3.getPriority());
	}

	@Test
	public void testExportImportLayoutsValidAvailableLocales()
		throws Exception {

		testAvailableLocales(
			Arrays.asList(LocaleUtil.US, LocaleUtil.US),
			Arrays.asList(LocaleUtil.US, LocaleUtil.SPAIN, LocaleUtil.US),
			false);
	}

	@Test
	public void testExportImportSelectedLayouts() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(group);

		long[] layoutIds = {layout.getLayoutId()};

		exportImportLayouts(layoutIds, getImportParameterMap());

		Assert.assertEquals(
			layoutIds.length,
			LayoutLocalServiceUtil.getLayoutsCount(importedGroup, false));

		importedLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
			layout.getUuid(), importedGroup.getGroupId(), false);

		Assert.assertNotNull(importedLayout);
	}

	@Test
	public void testExportImportUnselectedChildLayouts() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(group);

		Layout childLayout = LayoutTestUtil.addTypePortletLayout(
			group, layout.getPlid());

		Map<Long, Boolean> selectedLayouts = HashMapBuilder.put(
			LayoutConstants.DEFAULT_PLID, true
		).put(
			layout.getPlid(), false
		).build();

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			group.getGroupId(), false);

		Map<String, String[]> exportParameterMap = getExportParameterMap();

		exportParameterMap.put(Constants.CMD, new String[] {Constants.EXPORT});

		exportLayouts(
			ExportImportHelperUtil.getLayoutIds(selectedLayouts),
			exportParameterMap);

		importLayouts(getImportParameterMap());

		Assert.assertNotEquals(
			layouts.size(),
			LayoutLocalServiceUtil.getLayoutsCount(importedGroup, false));

		importedLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
			childLayout.getUuid(), importedGroup.getGroupId(), false);

		Assert.assertNull(importedLayout);
	}

	@Ignore
	@Test
	public void testFriendlyURLCollision() throws Exception {
		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getDefault());

		Layout layoutA = LayoutTestUtil.addTypePortletLayout(group);

		String friendlyURLA = layoutA.getFriendlyURL();

		layoutA = LayoutLocalServiceUtil.updateFriendlyURL(
			layoutA.getUserId(), layoutA.getPlid(), friendlyURLA + "-de", "de");

		Layout layoutB = LayoutTestUtil.addTypePortletLayout(group);

		String friendlyURLB = layoutB.getFriendlyURL();

		layoutB = LayoutLocalServiceUtil.updateFriendlyURL(
			layoutB.getUserId(), layoutB.getPlid(), friendlyURLB + "-de", "de");

		long[] layoutIds = {layoutA.getLayoutId(), layoutB.getLayoutId()};

		exportImportLayouts(layoutIds, getImportParameterMap());

		layoutA = LayoutLocalServiceUtil.updateFriendlyURL(
			layoutA.getUserId(), layoutA.getPlid(), "/temp", defaultLanguageId);

		layoutA = LayoutLocalServiceUtil.updateFriendlyURL(
			layoutA.getUserId(), layoutA.getPlid(), "/temp-de", "de");

		layoutB = LayoutLocalServiceUtil.updateFriendlyURL(
			layoutB.getUserId(), layoutB.getPlid(), friendlyURLA,
			defaultLanguageId);

		LayoutLocalServiceUtil.updateFriendlyURL(
			layoutB.getUserId(), layoutB.getPlid(), friendlyURLA + "-de", "de");

		layoutA = LayoutLocalServiceUtil.updateFriendlyURL(
			layoutA.getUserId(), layoutA.getPlid(), friendlyURLB,
			defaultLanguageId);

		LayoutLocalServiceUtil.updateFriendlyURL(
			layoutA.getUserId(), layoutA.getPlid(), friendlyURLB + "-de", "de");

		exportImportLayouts(layoutIds, getImportParameterMap());
	}

	protected void testAvailableLocales(
			Collection<Locale> sourceAvailableLocales,
			Collection<Locale> targetAvailableLocales, boolean expectFailure)
		throws Exception {

		group = GroupTestUtil.updateDisplaySettings(
			group.getGroupId(), sourceAvailableLocales, null);
		importedGroup = GroupTestUtil.updateDisplaySettings(
			importedGroup.getGroupId(), targetAvailableLocales, null);

		LayoutTestUtil.addTypePortletLayout(group);

		long[] layoutIds = new long[0];

		try {
			exportImportLayouts(layoutIds, getImportParameterMap(), true);

			Assert.assertFalse(expectFailure);
		}
		catch (LocaleException localeException) {
			if (_log.isDebugEnabled()) {
				_log.debug(localeException);
			}

			Assert.assertTrue(expectFailure);
		}
	}

	private String _getContent(String fileName) throws Exception {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		Scanner scanner = new Scanner(inputStream);

		scanner.useDelimiter("\\Z");

		return scanner.next();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutExportImportTest.class);

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}