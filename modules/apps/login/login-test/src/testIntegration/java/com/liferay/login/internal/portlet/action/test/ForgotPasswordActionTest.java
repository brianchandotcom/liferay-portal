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
import com.liferay.portal.kernel.test.rule.DataGuard;
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
@DataGuard(scope = DataGuard.Scope.METHOD)
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
		Group group = GroupTestUtil.addGroup();

		_company = CompanyLocalServiceUtil.getCompany(group.getCompanyId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		_layoutUtilityPageEntry =
			_layoutUtilityPageEntryLocalService.addLayoutUtilityPageEntry(
				null, serviceContext.getUserId(), group.getGroupId(), 0, 0,
				true, RandomTestUtil.randomString(),
				LayoutUtilityPageEntryConstants.TYPE_FORGOT_PASSWORD, 0,
				serviceContext);

		_user = UserTestUtil.addGroupUser(group, RoleConstants.POWER_USER);

		_layout = _layoutLocalService.fetchLayout(
			_layoutUtilityPageEntry.getPlid());

		long defaultSegmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				_layout.getPlid());

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.addFragmentCollection(
				null, group.getGroupId(), "Fragment Collection",
				StringPool.BLANK,
				ServiceContextTestUtil.getServiceContext(
					group.getGroupId(), TestPropsValues.getUserId()));

		_fragmentTextNondefaultSite = RandomTestUtil.randomString();

		FragmentEntry fragmentEntry = _fragmentEntryService.addFragmentEntry(
			null, group.getGroupId(),
			fragmentCollection.getFragmentCollectionId(), "fragment-entry",
			"Fragment Entry", null,
			"<div>" + _fragmentTextNondefaultSite + "</div>", null, false, null,
			null, 0, false, false, FragmentConstants.TYPE_SECTION, null,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId()));

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkService.addFragmentEntryLink(
				null, group.getGroupId(), 0, fragmentEntry.getFragmentEntryId(),
				defaultSegmentsExperienceId, _layout.getPlid(),
				StringPool.BLANK, fragmentEntry.getHtml(), StringPool.BLANK,
				"{fieldSets: []}", StringPool.BLANK, StringPool.BLANK, 0, null,
				fragmentEntry.getType(),
				ServiceContextTestUtil.getServiceContext(
					group, _user.getUserId()));

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
				group.getGroupId(), _layout.getPlid(),
				defaultSegmentsExperienceId, dataJSONObject1.toString());

		Group defaultGroup = _groupLocalService.getGroup(
			_company.getCompanyId(), GroupConstants.GUEST);

		_layoutUtilityPageEntryDefaultSite =
			_layoutUtilityPageEntryLocalService.addLayoutUtilityPageEntry(
				null, serviceContext.getUserId(), defaultGroup.getGroupId(), 0,
				0, true, RandomTestUtil.randomString(),
				LayoutUtilityPageEntryConstants.TYPE_FORGOT_PASSWORD, 0,
				serviceContext);

		Layout defaultLayout = _layoutLocalService.fetchLayout(
			_layoutUtilityPageEntryDefaultSite.getPlid());

		long defaultSegmentsExperienceIdDefaultSite =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				defaultLayout.getPlid());

		FragmentCollection fragmentCollectionDefaultSite =
			_fragmentCollectionService.addFragmentCollection(
				null, defaultGroup.getGroupId(), "Fragment Collection",
				StringPool.BLANK, serviceContext);

		_fragmentTextDefaultSite = RandomTestUtil.randomString();

		FragmentEntry fragmentEntryDefaultSite =
			_fragmentEntryService.addFragmentEntry(
				null, defaultGroup.getGroupId(),
				fragmentCollectionDefaultSite.getFragmentCollectionId(),
				"fragment-entry-2", "Fragment Entry", null,
				"<div>" + _fragmentTextDefaultSite + "</div>", null, false,
				null, null, 0, false, false, FragmentConstants.TYPE_SECTION,
				null, WorkflowConstants.STATUS_APPROVED, serviceContext);

		FragmentEntryLink fragmentEntryLinkDefaultSite =
			_fragmentEntryLinkService.addFragmentEntryLink(
				null, defaultGroup.getGroupId(), 0,
				fragmentEntryDefaultSite.getFragmentEntryId(),
				defaultSegmentsExperienceIdDefaultSite, defaultLayout.getPlid(),
				StringPool.BLANK, fragmentEntryDefaultSite.getHtml(),
				StringPool.BLANK, "{fieldSets: []}", StringPool.BLANK,
				StringPool.BLANK, 0, null, fragmentEntryDefaultSite.getType(),
				serviceContext);

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
			_isFragmentRendered(_fragmentTextNondefaultSite, false));
		Assert.assertTrue(_isFragmentRendered(_fragmentTextDefaultSite, false));
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
			_isFragmentRendered(_fragmentTextNondefaultSite, false));
		Assert.assertFalse(
			_isFragmentRendered(_fragmentTextDefaultSite, false));
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

		Assert.assertFalse(
			_isFragmentRendered(_fragmentTextNondefaultSite, true));
		Assert.assertFalse(_isFragmentRendered(_fragmentTextDefaultSite, true));
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

		Assert.assertTrue(
			_isFragmentRendered(_fragmentTextNondefaultSite, true));
		Assert.assertFalse(_isFragmentRendered(_fragmentTextDefaultSite, true));
	}

	private boolean _isFragmentRendered(
			String expectedText, boolean includePlid)
		throws Exception {

		Ticket ticket = _ticketLocalService.addDistinctTicket(
			_user.getCompanyId(), User.class.getName(), _user.getUserId(),
			TicketConstants.TYPE_PASSWORD, null,
			new Date(System.currentTimeMillis() + 3600000),
			new ServiceContext());

		String ticketId = String.valueOf(ticket.getTicketId());
		String ticketKey = ticket.getKey();

		URL url;

		if (includePlid) {
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

		return StringUtil.contains(
			URLUtil.toString(url), expectedText, StringPool.BLANK);
	}

	private Company _company;

	@Inject
	private FragmentCollectionService _fragmentCollectionService;

	@Inject
	private FragmentEntryLinkService _fragmentEntryLinkService;

	@Inject
	private FragmentEntryService _fragmentEntryService;

	private String _fragmentTextDefaultSite;
	private String _fragmentTextNondefaultSite;

	@Inject
	private GroupLocalService _groupLocalService;

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	private LayoutUtilityPageEntry _layoutUtilityPageEntry;
	private LayoutUtilityPageEntry _layoutUtilityPageEntryDefaultSite;

	@Inject
	private LayoutUtilityPageEntryLocalService
		_layoutUtilityPageEntryLocalService;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Inject
	private TicketLocalService _ticketLocalService;

	private User _user;

	@Inject
	private UserLocalService _userLocalService;

}