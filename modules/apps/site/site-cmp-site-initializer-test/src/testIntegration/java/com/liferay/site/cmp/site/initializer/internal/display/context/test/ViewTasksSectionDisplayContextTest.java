/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.display.context.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.frontend.data.set.constants.FDSEntityFieldTypes;
import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.site.cmp.site.initializer.test.util.CMPTestUtil;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Pedro Leite
 */
@FeatureFlags(
	featureFlags = {@FeatureFlag("LPD-17564"), @FeatureFlag("LPD-58677")}
)
@RunWith(Arquillian.class)
@Sync
public class ViewTasksSectionDisplayContextTest
	extends BaseSectionDisplayContextTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_projectObjectDefinition =
			objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_CMP_PROJECT", TestPropsValues.getCompanyId());

		ObjectEntry projectObjectEntry = CMPTestUtil.addProjectObjectEntry();

		projectObjectEntry = _objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(), projectObjectEntry.getObjectEntryId(),
			projectObjectEntry.getObjectEntryFolderId(),
			projectObjectEntry.getValues(),
			ServiceContextTestUtil.getServiceContext());

		_assetEntry = _assetEntryLocalService.getEntry(
			_projectObjectDefinition.getClassName(),
			projectObjectEntry.getObjectEntryId());
	}

	@Test
	public void testGetAPIURL() throws Exception {
		Assert.assertTrue(
			StringUtil.equals(
				getAPIURL(null),
				StringBundler.concat(
					"/o/search/v1.0/search?emptySearch=true&entryClassNames=",
					HtmlUtil.escapeURL(objectDefinition.getClassName()), ",",
					_CLASS_NAME_KALEO_TASK_INSTANCE_TOKEN,
					"&filter=(objectDefinitionId eq ",
					objectDefinition.getObjectDefinitionId(),
					" or keywords/any(k:startswith(k, 'L_CMP_TASK')))",
					"&nestedFields=cmpProjectToCMPTasks,embedded")));
		Assert.assertTrue(
			StringUtil.equals(
				getAPIURL(_assetEntry),
				StringBundler.concat(
					"/o/search/v1.0/search?emptySearch=true&filter=",
					"(objectDefinitionId eq ",
					objectDefinition.getObjectDefinitionId(),
					" and scopeGroupId eq ", _assetEntry.getGroupId(),
					")&nestedFields=cmpProjectToCMPTasks,embedded")));
	}

	@Test
	public void testGetCreationMenu() throws Exception {
		DropdownItem dropdownItem = _getDropdownItem(
			getCreationMenu(_assetEntry));

		Assert.assertEquals("createTask", getValue(dropdownItem, "action"));
		Assert.assertTrue(
			Validator.isNull(getValue(dropdownItem, "addProjectURL")));
		Assert.assertTrue(
			Validator.isNull(getValue(dropdownItem, "addTaskURL")));
		Assert.assertEquals("New Task", dropdownItem.get("label"));
		Assert.assertEquals(
			String.valueOf(objectDefinition.getObjectDefinitionId()),
			getValue(dropdownItem, "objectDefinitionId"));
		Assert.assertEquals(
			StringBundler.concat(
				themeDisplay.getPortalURL(), themeDisplay.getPathMain(),
				GroupConstants.CMS_FRIENDLY_URL,
				"/add_task?objectDefinitionId=",
				objectDefinition.getObjectDefinitionId(), "&plid=",
				themeDisplay.getPlid(), "&projectGroupId=",
				_assetEntry.getGroupId(), "&projectId=",
				_assetEntry.getClassPK(), "&redirect=",
				themeDisplay.getURLCurrent()),
			getValue(dropdownItem, "redirect"));
		Assert.assertEquals("Task", getValue(dropdownItem, "title"));

		dropdownItem = _getDropdownItem(getCreationMenu(null));

		Assert.assertEquals(
			StringBundler.concat(
				themeDisplay.getPortalURL(), themeDisplay.getPathMain(),
				GroupConstants.CMS_FRIENDLY_URL,
				"/add_project?objectDefinitionId=",
				_projectObjectDefinition.getObjectDefinitionId(), "&plid=",
				themeDisplay.getPlid(), "&redirect=",
				themeDisplay.getURLCurrent(),
				"&action=createProjectGlobalTask"),
			getValue(dropdownItem, "addProjectURL"));
		Assert.assertEquals(
			StringBundler.concat(
				themeDisplay.getPortalURL(), themeDisplay.getPathMain(),
				GroupConstants.CMS_FRIENDLY_URL,
				"/add_task?objectDefinitionId=",
				objectDefinition.getObjectDefinitionId(), "&plid=",
				themeDisplay.getPlid(), "&projectGroupId=0&projectId=0",
				"&redirect=", themeDisplay.getURLCurrent(),
				"&action=createGlobalTask"),
			getValue(dropdownItem, "addTaskURL"));
		Assert.assertEquals("New", dropdownItem.get("label"));
		Assert.assertTrue(Validator.isNull(getValue(dropdownItem, "redirect")));
	}

	@Test
	public void testGetFDSActionDropdownItems() throws Exception {
		List<FDSActionDropdownItem> fdsActionDropdownItems =
			getFDSActionDropdownItems(_assetEntry);

		Assert.assertEquals(
			fdsActionDropdownItems.toString(), 8,
			fdsActionDropdownItems.size());

		assertFDSActionDropdownItem(
			"pencil", "edit", "Edit", "get",
			Collections.singletonMap(
				"entryClassName", objectDefinition.getClassName()),
			fdsActionDropdownItems.get(0));
		assertFDSActionDropdownItem(
			"view", "actionLink", "View", null,
			Collections.singletonMap(
				"entryClassName", objectDefinition.getClassName()),
			fdsActionDropdownItems.get(1));
		assertFDSActionDropdownItem(
			null, "assign-to", "Assign to...", null,
			Collections.singletonMap(
				"entryClassName", objectDefinition.getClassName()),
			fdsActionDropdownItems.get(2));
		assertFDSActionDropdownItem(
			"trash", "delete", "Delete", null,
			Collections.singletonMap(
				"entryClassName", objectDefinition.getClassName()),
			fdsActionDropdownItems.get(3));
		assertFDSActionDropdownItem(
			"view", "actionLinkWorkflowTask", "View", null,
			Collections.singletonMap(
				"entryClassName", _CLASS_NAME_KALEO_TASK_INSTANCE_TOKEN),
			fdsActionDropdownItems.get(4));
		assertFDSActionDropdownItem(
			null, "assignToMeWorkflowTask", "Assign to Me", null,
			HashMapBuilder.<String, Object>put(
				"embedded.assignedToMe", false
			).put(
				"embedded.completed", false
			).put(
				"entryClassName", _CLASS_NAME_KALEO_TASK_INSTANCE_TOKEN
			).build(),
			fdsActionDropdownItems.get(5));
		assertFDSActionDropdownItem(
			null, "assignToWorkflowTask", "Assign to...", null,
			HashMapBuilder.<String, Object>put(
				"embedded.completed", false
			).put(
				"entryClassName", _CLASS_NAME_KALEO_TASK_INSTANCE_TOKEN
			).build(),
			fdsActionDropdownItems.get(6));
		assertFDSActionDropdownItem(
			"date-time", "updateDueDateWorkflowTask", "Update Due Date", null,
			HashMapBuilder.<String, Object>put(
				"embedded.completed", false
			).put(
				"entryClassName", _CLASS_NAME_KALEO_TASK_INSTANCE_TOKEN
			).build(),
			fdsActionDropdownItems.get(7));
	}

	@Test
	public void testGetFDSFilters() throws Exception {
		List<FDSFilter> fdsFilters = getFDSFilters(null);

		Assert.assertEquals(fdsFilters.toString(), 4, fdsFilters.size());

		assertFDSFilter(
			FDSEntityFieldTypes.DATE_TIME, "dateCreated", "create-date",
			fdsFilters.get(0));
		assertFDSFilter(
			FDSEntityFieldTypes.DATE_TIME, "cmpDueDate", "due-date",
			fdsFilters.get(1));
		assertFDSFilter(
			FDSEntityFieldTypes.INTEGER, "cmpTaskCMPProjectId", "project",
			fdsFilters.get(2));
		assertFDSFilter(
			FDSEntityFieldTypes.STRING, "cmpState", "state", fdsFilters.get(3));

		fdsFilters = getFDSFilters(_assetEntry);

		Assert.assertEquals(fdsFilters.toString(), 3, fdsFilters.size());

		assertFDSFilter(
			FDSEntityFieldTypes.DATE_TIME, "dateCreated", "create-date",
			fdsFilters.get(0));
		assertFDSFilter(
			FDSEntityFieldTypes.DATE_TIME, "cmpDueDate", "due-date",
			fdsFilters.get(1));
		assertFDSFilter(
			FDSEntityFieldTypes.STRING, "cmpState", "state", fdsFilters.get(2));
	}

	@Override
	protected String getObjectDefinitionExternalReferenceCode() {
		return "L_CMP_TASK";
	}

	@Override
	protected Object getSectionDisplayContext(
			HttpServletRequest httpServletRequest)
		throws Exception {

		_fragmentRenderer.render(
			null, httpServletRequest, new MockHttpServletResponse());

		return httpServletRequest.getAttribute(
			"com.liferay.site.cmp.site.initializer.internal.display.context." +
				"ViewTasksSectionDisplayContext");
	}

	private DropdownItem _getDropdownItem(CreationMenu creationMenu) {
		List<DropdownItem> dropdownItems = (List<DropdownItem>)creationMenu.get(
			"primaryItems");

		Assert.assertEquals(dropdownItems.toString(), 1, dropdownItems.size());

		return dropdownItems.get(0);
	}

	private static final String _CLASS_NAME_KALEO_TASK_INSTANCE_TOKEN =
		"com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken";

	private AssetEntry _assetEntry;

	@Inject
	private AssetEntryLocalService _assetEntryLocalService;

	@Inject(
		filter = "component.name=com.liferay.site.cmp.site.initializer.internal.fragment.renderer.ViewTasksJSPSectionFragmentRenderer"
	)
	private FragmentRenderer _fragmentRenderer;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	private ObjectDefinition _projectObjectDefinition;

}