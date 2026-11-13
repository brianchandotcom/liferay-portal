/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.marketing.platform.web.internal.display.context.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.content.marketing.platform.test.util.CMPTestUtil;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.object.model.ObjectEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import jakarta.servlet.http.HttpServletRequest;

import java.io.Serializable;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Larissa Ribeiro
 */
@RunWith(Arquillian.class)
@Sync
public class ViewSubtasksSectionDisplayContextTest
	extends BaseSectionDisplayContextTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_cmpProjectObjectEntry = CMPTestUtil.addCMPProjectObjectEntry();

		_cmpTaskObjectEntry = CMPTestUtil.addCMPTaskObjectEntry(
			_cmpProjectObjectEntry);
	}

	@Test
	public void testGetAPIURL() throws Exception {
		Assert.assertEquals(
			StringBundler.concat(
				"/o/search/v1.0/search?emptySearch=true&filter=",
				"(objectDefinitionId eq ",
				objectDefinition.getObjectDefinitionId(),
				" and cmpParentTaskId eq ",
				_cmpTaskObjectEntry.getObjectEntryId(),
				")&nestedFields=embedded,embedded.cmpProjectToCMPTasks",
				"&nestedFieldsDepth=2"),
			ReflectionTestUtil.invoke(
				_getSectionDisplayContext(_cmpTaskObjectEntry), "getAPIURL",
				new Class<?>[0]));
	}

	@Test
	public void testGetCreationMenu() throws Exception {
		CreationMenu creationMenu = ReflectionTestUtil.invoke(
			_getSectionDisplayContext(_cmpTaskObjectEntry), "getCreationMenu",
			new Class<?>[0]);

		List<DropdownItem> dropdownItems = (List<DropdownItem>)creationMenu.get(
			"primaryItems");

		Assert.assertEquals(dropdownItems.toString(), 1, dropdownItems.size());

		DropdownItem dropdownItem = dropdownItems.get(0);

		Assert.assertEquals("createTask", getValue(dropdownItem, "action"));
		Assert.assertEquals("New Subtask", dropdownItem.get("label"));
		Assert.assertEquals(
			StringBundler.concat(
				themeDisplay.getPortalURL(), themeDisplay.getPathMain(),
				GroupConstants.CMS_FRIENDLY_URL,
				"/add_task?objectDefinitionId=",
				objectDefinition.getObjectDefinitionId(), "&plid=",
				themeDisplay.getPlid(), "&projectGroupId=",
				_cmpTaskObjectEntry.getGroupId(), "&projectId=",
				_cmpProjectObjectEntry.getObjectEntryId(), "&redirect=",
				themeDisplay.getURLCurrent(), "&parentId=",
				_cmpTaskObjectEntry.getObjectEntryId()),
			getValue(dropdownItem, "redirect"));
	}

	@FeatureFlag("LPD-107678")
	@Test
	public void testIsVisible() throws Exception {
		Assert.assertTrue(_isVisible(_cmpTaskObjectEntry));

		ObjectEntry cmpTaskObjectEntry2 = _addCMPTaskObjectEntry(
			_cmpTaskObjectEntry);

		Assert.assertTrue(_isVisible(cmpTaskObjectEntry2));
		Assert.assertFalse(
			_isVisible(_addCMPTaskObjectEntry(cmpTaskObjectEntry2)));
	}

	@FeatureFlag(enable = false, value = "LPD-107678")
	@Test
	public void testIsVisibleWhenFeatureFlagIsDisabled() throws Exception {
		Assert.assertFalse(_isVisible(_cmpTaskObjectEntry));
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
			"com.liferay.content.marketing.platform.web.internal.display." +
				"context.ViewSubtasksSectionDisplayContext");
	}

	private ObjectEntry _addCMPTaskObjectEntry(
			ObjectEntry parentCMPTaskObjectEntry)
		throws Exception {

		return CMPTestUtil.addCMPTaskObjectEntry(
			_cmpProjectObjectEntry,
			HashMapBuilder.<String, Serializable>put(
				"r_cmpTaskToCMPTasks_c_cmpTaskId",
				parentCMPTaskObjectEntry.getObjectEntryId()
			).build(),
			WorkflowConstants.ACTION_SAVE_DRAFT);
	}

	private Object _getSectionDisplayContext(ObjectEntry objectEntry)
		throws Exception {

		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		httpServletRequest.setAttribute(
			InfoDisplayWebKeys.INFO_ITEM, objectEntry);
		httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		return getSectionDisplayContext(httpServletRequest);
	}

	private boolean _isVisible(ObjectEntry objectEntry) throws Exception {
		return ReflectionTestUtil.invoke(
			_getSectionDisplayContext(objectEntry), "isVisible",
			new Class<?>[0]);
	}

	private ObjectEntry _cmpProjectObjectEntry;
	private ObjectEntry _cmpTaskObjectEntry;

	@Inject(
		filter = "component.name=com.liferay.content.marketing.platform.web.internal.fragment.renderer.ViewSubtasksJSPSectionFragmentRenderer"
	)
	private FragmentRenderer _fragmentRenderer;

}