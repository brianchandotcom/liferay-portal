/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.exception.NoSuchEntryException;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.layout.content.page.editor.web.internal.portlet.constants.LayoutContentPageEditorWebPortletKeys;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
@Sync
public class AddFragmentEntryLinkMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_company = _companyLocalService.getCompany(_group.getCompanyId());

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		_layout = layout.fetchDraftLayout();
	}

	@Test
	public void testAddFragmentEntryLink() throws Exception {
		_testAddFragmentEntryLink(_getFragmentEntry(_company.getGroupId()));
		_testAddFragmentEntryLink(_getFragmentEntry(_group.getGroupId()));

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			_getMockLiferayPortletActionRequest(_group.getGroupId());

		mockLiferayPortletActionRequest.addParameter(
			"fragmentEntryKey", RandomTestUtil.randomString());

		try {
			ReflectionTestUtil.invoke(
				_mvcActionCommand, "addFragmentEntryLink",
				new Class<?>[] {ActionRequest.class},
				mockLiferayPortletActionRequest);

			Assert.fail();
		}
		catch (Exception exception) {
			Assert.assertEquals(
				NoSuchEntryException.class, exception.getClass());
		}
	}

	@Test
	public void testAddFragmentEntryLinkWithEmbeddedPortlet() throws Exception {
		String instanceId = RandomTestUtil.randomString();

		_assertAddFragmentEntryLinkWithEmbeddedPortlet(
			_addFragmentEntry(
				StringBundler.concat(
					"<div><lfr-widget-",
					LayoutContentPageEditorWebPortletKeys.
						LAYOUT_CONTENT_PAGE_EDITOR_WEB_TEST_PORTLET_ALIAS,
					" id=\"", instanceId, "\"></lfr-widget-",
					LayoutContentPageEditorWebPortletKeys.
						LAYOUT_CONTENT_PAGE_EDITOR_WEB_TEST_PORTLET_ALIAS,
					"></div>")),
			instanceId,
			LayoutContentPageEditorWebPortletKeys.
				LAYOUT_CONTENT_PAGE_EDITOR_WEB_TEST_PORTLET);
		_assertAddFragmentEntryLinkWithEmbeddedPortlet(
			_addFragmentEntry(
				StringBundler.concat(
					"<div>[@liferay_portlet.runtime portletName=\"",
					LayoutContentPageEditorWebPortletKeys.
						LAYOUT_CONTENT_PAGE_EDITOR_WEB_TEST_PORTLET,
					"\" instanceId=\"${fragmentEntryLinkNamespace}", instanceId,
					"\"/]/div>")),
			instanceId,
			LayoutContentPageEditorWebPortletKeys.
				LAYOUT_CONTENT_PAGE_EDITOR_WEB_TEST_PORTLET);
		_assertAddFragmentEntryLinkWithEmbeddedPortlet(
			_addFragmentEntry(
				StringBundler.concat(
					"<div>[@liferay_portlet[\"runtime\"] portletName=\"",
					LayoutContentPageEditorWebPortletKeys.
						LAYOUT_CONTENT_PAGE_EDITOR_WEB_TEST_PORTLET,
					"\" instanceId=\"${fragmentEntryLinkNamespace}", instanceId,
					"\"/]/div>")),
			instanceId,
			LayoutContentPageEditorWebPortletKeys.
				LAYOUT_CONTENT_PAGE_EDITOR_WEB_TEST_PORTLET);
	}

	private FragmentEntry _addFragmentEntry(String html) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.addFragmentCollection(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				StringUtil.randomString(), StringPool.BLANK, serviceContext);

		return _fragmentEntryLocalService.addFragmentEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			fragmentCollection.getFragmentCollectionId(),
			StringUtil.randomString(), StringUtil.randomString(),
			RandomTestUtil.randomString(), html, RandomTestUtil.randomString(),
			false, "{fieldSets: []}", null, 0, false,
			FragmentConstants.TYPE_COMPONENT, null,
			WorkflowConstants.STATUS_APPROVED, serviceContext);
	}

	private void _assertAddFragmentEntryLinkWithEmbeddedPortlet(
			FragmentEntry fragmentEntry, String instanceId, String portletId)
		throws Exception {

		List<PortletPreferences> originalPortletPreferencesList =
			_portletPreferencesLocalService.getPortletPreferencesByPlid(
				_layout.getPlid());

		FragmentEntryLink fragmentEntryLink = _testAddFragmentEntryLink(
			fragmentEntry);

		List<PortletPreferences> portletPreferencesList = ListUtil.remove(
			_portletPreferencesLocalService.getPortletPreferencesByPlid(
				_layout.getPlid()),
			originalPortletPreferencesList);

		Assert.assertEquals(
			portletPreferencesList.toString(), 1,
			portletPreferencesList.size());

		PortletPreferences portletPreferences = portletPreferencesList.get(0);

		Assert.assertEquals(
			_portal.getJsSafePortletId(
				PortletIdCodec.encode(
					portletId, fragmentEntryLink.getNamespace() + instanceId)),
			portletPreferences.getPortletId());
	}

	private FragmentEntry _getFragmentEntry(long groupId) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.addFragmentCollection(
				null, TestPropsValues.getUserId(), groupId,
				StringUtil.randomString(), StringPool.BLANK, serviceContext);

		return _fragmentEntryLocalService.addFragmentEntry(
			null, TestPropsValues.getUserId(), groupId,
			fragmentCollection.getFragmentCollectionId(),
			StringUtil.randomString(), StringUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), false, "{fieldSets: []}", null, 0,
			false, FragmentConstants.TYPE_COMPONENT, null,
			WorkflowConstants.STATUS_APPROVED, serviceContext);
	}

	private MockLiferayPortletActionRequest _getMockLiferayPortletActionRequest(
			long groupId)
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			ContentLayoutTestUtil.getMockLiferayPortletActionRequest(
				_company, _group, _layout);

		mockLiferayPortletActionRequest.addParameter(
			"groupId", String.valueOf(groupId));

		return mockLiferayPortletActionRequest;
	}

	private FragmentEntryLink _testAddFragmentEntryLink(
			FragmentEntry fragmentEntry)
		throws Exception {

		List<FragmentEntryLink> originalFragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinksByPlid(
				_group.getGroupId(), _layout.getPlid());

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			_getMockLiferayPortletActionRequest(fragmentEntry.getGroupId());

		mockLiferayPortletActionRequest.addParameter(
			"fragmentEntryKey", fragmentEntry.getFragmentEntryKey());

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_mvcActionCommand, "_processAddFragmentEntryLink",
			new Class<?>[] {ActionRequest.class, ActionResponse.class},
			mockLiferayPortletActionRequest,
			new MockLiferayPortletActionResponse());

		JSONObject fragmentEntryLinkJSONObject = jsonObject.getJSONObject(
			"fragmentEntryLink");

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.getFragmentEntryLink(
				GetterUtil.getLong(
					fragmentEntryLinkJSONObject.getString(
						"fragmentEntryLinkId")));

		Assert.assertNotNull(fragmentEntryLink);

		Assert.assertEquals(
			fragmentEntry.getFragmentEntryId(),
			fragmentEntryLink.getFragmentEntryId());
		Assert.assertEquals(_layout.getPlid(), fragmentEntryLink.getPlid());
		Assert.assertEquals(fragmentEntry.getCss(), fragmentEntryLink.getCss());
		Assert.assertEquals(
			fragmentEntry.getHtml(), fragmentEntryLink.getHtml());
		Assert.assertEquals(fragmentEntry.getJs(), fragmentEntryLink.getJs());
		Assert.assertEquals(
			fragmentEntry.getConfiguration(),
			fragmentEntryLink.getConfiguration());
		Assert.assertEquals(
			StringPool.BLANK, fragmentEntryLink.getRendererKey());

		List<FragmentEntryLink> actualFragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinksByPlid(
				_group.getGroupId(), _layout.getPlid());

		Assert.assertEquals(
			actualFragmentEntryLinks.toString(),
			originalFragmentEntryLinks.size() + 1,
			actualFragmentEntryLinks.size());

		return fragmentEntryLink;
	}

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject(
		filter = "mvc.command.name=/layout_content_page_editor/add_fragment_entry_link"
	)
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private Portal _portal;

	@Inject
	private PortletPreferencesLocalService _portletPreferencesLocalService;

}