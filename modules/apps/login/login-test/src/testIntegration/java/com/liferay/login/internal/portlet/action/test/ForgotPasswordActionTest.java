/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.login.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentCollectionService;
import com.liferay.fragment.service.FragmentEntryLinkService;
import com.liferay.fragment.service.FragmentEntryService;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.structure.ContainerStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.utility.page.kernel.constants.LayoutUtilityPageEntryConstants;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.model.TicketConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.pwd.PasswordEncryptorUtil;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.TicketLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alvaro Saugar
 */
@FeatureFlag("LPD-6378")
@RunWith(Arquillian.class)
public class ForgotPasswordActionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_company = CompanyLocalServiceUtil.getCompany(_group.getCompanyId());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);

		_layoutUtilityPageEntry =
			_layoutUtilityPageEntryLocalService.addLayoutUtilityPageEntry(
				null, _serviceContext.getUserId(), _group.getGroupId(), 0, 0,
				true, RandomTestUtil.randomString(),
				LayoutUtilityPageEntryConstants.TYPE_FORGOT_PASSWORD, 0,
				_serviceContext);

		_user = UserTestUtil.addGroupUser(_group, RoleConstants.POWER_USER);

		_layout = _layoutLocalService.fetchLayout(
			_layoutUtilityPageEntry.getPlid());

		long defaultSegmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				_layout.getPlid());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.addFragmentCollection(
				null, _group.getGroupId(), "Fragment Collection",
				StringPool.BLANK, serviceContext);

		_fragmentTextNondefaultSite = RandomTestUtil.randomString();

		FragmentEntry fragmentEntry = _fragmentEntryService.addFragmentEntry(
			null, _group.getGroupId(),
			fragmentCollection.getFragmentCollectionId(), "fragment-entry",
			"Fragment Entry", null,
			"<div>" + _fragmentTextNondefaultSite + "</div>", null, false, null,
			null, 0, false, false, FragmentConstants.TYPE_SECTION, null,
			WorkflowConstants.STATUS_APPROVED, serviceContext);

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkService.addFragmentEntryLink(
				null, _group.getGroupId(), 0,
				fragmentEntry.getFragmentEntryId(), defaultSegmentsExperienceId,
				_layout.getPlid(), StringPool.BLANK, fragmentEntry.getHtml(),
				StringPool.BLANK, "{fieldSets: []}", StringPool.BLANK,
				StringPool.BLANK, 0, null, fragmentEntry.getType(),
				ServiceContextTestUtil.getServiceContext(
					_group, _user.getUserId()));

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					_layout.getGroupId(), _layout.getPlid());

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getDefaultSegmentsExperienceData());

		ContainerStyledLayoutStructureItem containerStyledLayoutStructureItem =
			(ContainerStyledLayoutStructureItem)
				layoutStructure.addContainerStyledLayoutStructureItem(
					layoutStructure.getMainItemId(), 0);

		containerStyledLayoutStructureItem.setWidthType("fixed");

		layoutStructure.addFragmentStyledLayoutStructureItem(
			fragmentEntryLink.getFragmentEntryLinkId(),
			containerStyledLayoutStructureItem.getItemId(), 0);

		JSONObject dataJSONObject1 = layoutStructure.toJSONObject();

		_layoutPageTemplateStructureLocalService.
			updateLayoutPageTemplateStructureData(
				_group.getGroupId(), _layout.getPlid(),
				defaultSegmentsExperienceId, dataJSONObject1.toString());

		Group defaultGroup = _groupLocalService.getGroup(
			_company.getCompanyId(), GroupConstants.GUEST);

		_layoutUtilityPageEntryDefaultSite =
			_layoutUtilityPageEntryLocalService.addLayoutUtilityPageEntry(
				null, _serviceContext.getUserId(), defaultGroup.getGroupId(), 0,
				0, true, RandomTestUtil.randomString(),
				LayoutUtilityPageEntryConstants.TYPE_FORGOT_PASSWORD, 0,
				_serviceContext);

		Layout defaultLayout = _layoutLocalService.fetchLayout(
			_layoutUtilityPageEntryDefaultSite.getPlid());

		long defaultSegmentsExperienceIdDefaultSite =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				defaultLayout.getPlid());

		FragmentCollection fragmentCollectionDefaultSite =
			_fragmentCollectionService.addFragmentCollection(
				null, defaultGroup.getGroupId(), "Fragment Collection",
				StringPool.BLANK, _serviceContext);

		_fragmentTextDefaultSite = RandomTestUtil.randomString();

		_fragmentEntryDefaultSite = _fragmentEntryService.addFragmentEntry(
			null, defaultGroup.getGroupId(),
			fragmentCollectionDefaultSite.getFragmentCollectionId(),
			"fragment-entry-2", "Fragment Entry", null,
			"<div>" + _fragmentTextDefaultSite + "</div>", null, false, null,
			null, 0, false, false, FragmentConstants.TYPE_SECTION, null,
			WorkflowConstants.STATUS_APPROVED, _serviceContext);

		FragmentEntryLink fragmentEntryLinkDefaultSite =
			_fragmentEntryLinkService.addFragmentEntryLink(
				null, defaultGroup.getGroupId(), 0,
				_fragmentEntryDefaultSite.getFragmentEntryId(),
				defaultSegmentsExperienceIdDefaultSite, defaultLayout.getPlid(),
				StringPool.BLANK, _fragmentEntryDefaultSite.getHtml(),
				StringPool.BLANK, "{fieldSets: []}", StringPool.BLANK,
				StringPool.BLANK, 0, null, _fragmentEntryDefaultSite.getType(),
				_serviceContext);

		LayoutPageTemplateStructure layoutPageTemplateStructureDefaultSite =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					defaultLayout.getGroupId(), defaultLayout.getPlid());

		LayoutStructure layoutStructureDefaultSite = LayoutStructure.of(
			layoutPageTemplateStructureDefaultSite.
				getDefaultSegmentsExperienceData());

		ContainerStyledLayoutStructureItem
			containerStyledLayoutStructureItemDefaultSite =
				(ContainerStyledLayoutStructureItem)
					layoutStructureDefaultSite.
						addContainerStyledLayoutStructureItem(
							layoutStructureDefaultSite.getMainItemId(), 0);

		containerStyledLayoutStructureItemDefaultSite.setWidthType("fixed");

		layoutStructureDefaultSite.addFragmentStyledLayoutStructureItem(
			fragmentEntryLinkDefaultSite.getFragmentEntryLinkId(),
			containerStyledLayoutStructureItemDefaultSite.getItemId(), 0);

		JSONObject dataJSONObject2 = layoutStructureDefaultSite.toJSONObject();

		_layoutPageTemplateStructureLocalService.
			updateLayoutPageTemplateStructureData(
				defaultGroup.getGroupId(), defaultLayout.getPlid(),
				defaultSegmentsExperienceIdDefaultSite,
				dataJSONObject2.toString());

		UserTestUtil.setUser(
			_userLocalService.getGuestUser(_company.getCompanyId()));
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testUpdatePasswordRedirectWithLayoutUtilityPageEntryInDefaultSiteWithoutPlid()
		throws Exception {

		_layoutUtilityPageEntryDefaultSite.setDefaultLayoutUtilityPageEntry(
			true);

		_layoutUtilityPageEntryDefaultSite =
			_layoutUtilityPageEntryLocalService.updateLayoutUtilityPageEntry(
				_layoutUtilityPageEntryDefaultSite);

		Assert.assertFalse(
			_fragmentIsShown(false, _fragmentTextNondefaultSite));
		Assert.assertTrue(_fragmentIsShown(false, _fragmentTextDefaultSite));
	}

	@Test
	public void testUpdatePasswordRedirectWithLayoutUtilityPageEntryInNondefaultSiteWithoutPlid()
		throws Exception {

		_layoutUtilityPageEntryDefaultSite.setDefaultLayoutUtilityPageEntry(
			false);

		_layoutUtilityPageEntryDefaultSite =
			_layoutUtilityPageEntryLocalService.updateLayoutUtilityPageEntry(
				_layoutUtilityPageEntryDefaultSite);

		_layoutUtilityPageEntry.setDefaultLayoutUtilityPageEntry(true);

		_layoutUtilityPageEntry =
			_layoutUtilityPageEntryLocalService.updateLayoutUtilityPageEntry(
				_layoutUtilityPageEntry);

		Assert.assertFalse(
			_fragmentIsShown(false, _fragmentTextNondefaultSite));
		Assert.assertFalse(_fragmentIsShown(false, _fragmentTextDefaultSite));
	}

	@Test
	public void testUpdatePasswordRedirectWithLayoutUtilityPageEntrySetASNondefault()
		throws Exception {

		_layoutUtilityPageEntryDefaultSite.setDefaultLayoutUtilityPageEntry(
			false);

		_layoutUtilityPageEntryDefaultSite =
			_layoutUtilityPageEntryLocalService.updateLayoutUtilityPageEntry(
				_layoutUtilityPageEntryDefaultSite);

		_layoutUtilityPageEntry.setDefaultLayoutUtilityPageEntry(false);

		_layoutUtilityPageEntry =
			_layoutUtilityPageEntryLocalService.updateLayoutUtilityPageEntry(
				_layoutUtilityPageEntry);

		Assert.assertFalse(_fragmentIsShown(true, _fragmentTextNondefaultSite));
		Assert.assertFalse(_fragmentIsShown(true, _fragmentTextDefaultSite));
	}

	@Test
	public void testUpdatePasswordRedirectWithLayoutUtilityPageEntryWithPlid()
		throws Exception {

		_layoutUtilityPageEntryDefaultSite.setDefaultLayoutUtilityPageEntry(
			false);

		_layoutUtilityPageEntryDefaultSite =
			_layoutUtilityPageEntryLocalService.updateLayoutUtilityPageEntry(
				_layoutUtilityPageEntryDefaultSite);

		_layoutUtilityPageEntry.setDefaultLayoutUtilityPageEntry(true);

		_layoutUtilityPageEntry =
			_layoutUtilityPageEntryLocalService.updateLayoutUtilityPageEntry(
				_layoutUtilityPageEntry);

		Assert.assertTrue(_fragmentIsShown(true, _fragmentTextNondefaultSite));
		Assert.assertFalse(_fragmentIsShown(true, _fragmentTextDefaultSite));
	}

	private boolean _fragmentIsShown(boolean hasPlid, String fragmentText)
		throws Exception {

		Ticket ticket = _ticketLocalService.addDistinctTicket(
			_user.getCompanyId(), User.class.getName(), _user.getUserId(),
			TicketConstants.TYPE_PASSWORD, null,
			new Date(System.currentTimeMillis() + 3600000),
			new ServiceContext());

		String ticketId = String.valueOf(ticket.getTicketId());
		String ticketKey = ticket.getKey();

		URL url;

		if (hasPlid) {
			url = new URL(
				StringBundler.concat(
					"http://", _company.getVirtualHostname(),
					":8080/c/portal/update_password?p_l_id=", _layout.getPlid(),
					"&ticketId=", ticketId, "&ticketId=", ticketKey));
		}
		else {
			url = new URL(
				StringBundler.concat(
					"http://", _company.getVirtualHostname(),
					":8080/c/portal/update_password?ticketId=", ticketId,
					"&ticketId=", ticketKey));
		}

		ticket.setKey(PasswordEncryptorUtil.encrypt(ticket.getKey()));

		_ticketLocalService.updateTicket(ticket);

		return StringUtil.contains(URLUtil.toString(url), fragmentText, StringPool.BLANK);


	}

	private Company _company;

	@Inject
	private FragmentCollectionService _fragmentCollectionService;

	@DeleteAfterTestRun
	private FragmentEntry _fragmentEntryDefaultSite;

	@Inject
	private FragmentEntryLinkService _fragmentEntryLinkService;

	@Inject
	private FragmentEntryService _fragmentEntryService;

	private String _fragmentTextDefaultSite;
	private String _fragmentTextNondefaultSite;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@DeleteAfterTestRun
	private LayoutUtilityPageEntry _layoutUtilityPageEntry;

	@DeleteAfterTestRun
	private LayoutUtilityPageEntry _layoutUtilityPageEntryDefaultSite;

	@Inject
	private LayoutUtilityPageEntryLocalService
		_layoutUtilityPageEntryLocalService;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	private ServiceContext _serviceContext;

	@Inject
	private TicketLocalService _ticketLocalService;

	private User _user;

	@Inject
	private UserLocalService _userLocalService;

}