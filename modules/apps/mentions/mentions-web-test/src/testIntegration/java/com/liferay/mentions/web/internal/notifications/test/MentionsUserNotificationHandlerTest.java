/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mentions.web.internal.notifications.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.blogs.test.util.BlogsTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.mentions.constants.MentionsPortletKeys;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.test.util.ObjectEntryTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.notifications.UserNotificationFeedEntry;
import com.liferay.portal.kernel.notifications.UserNotificationManagerUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserNotificationEventTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Mikel Lorza
 */
@FeatureFlag("LPD-104086")
@RunWith(Arquillian.class)
public class MentionsUserNotificationHandlerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousMailTestRule.INSTANCE);

	@Test
	public void testGetLink() throws Exception {
		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.publishObjectDefinition();

		ObjectEntry objectEntry = ObjectEntryTestUtil.addObjectEntry(
			objectDefinition);

		String portalURL =
			"http://localhost:" + _portal.getPortalServerPort(false);

		String entryURL = portalURL + "/web/cms/view-asset";

		Group group = GroupTestUtil.addGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		Layout layout = LayoutTestUtil.addTypeContentLayout(group);

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.fetchCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setLayout(layout);
		themeDisplay.setLayoutSet(layout.getLayoutSet());
		themeDisplay.setRequest(mockHttpServletRequest);

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		serviceContext.setPortalURL(portalURL);
		serviceContext.setRequest(mockHttpServletRequest);

		Assert.assertEquals(
			entryURL,
			_getLink(
				JSONUtil.put(
					"className", objectDefinition.getClassName()
				).put(
					"classPK", objectEntry.getObjectEntryId()
				).put(
					"entryURL", entryURL
				).put(
					"userId", TestPropsValues.getUserId()
				),
				serviceContext));

		String link = _getLink(
			JSONUtil.put(
				"discussionClassName", objectDefinition.getClassName()
			).put(
				"discussionClassPK", objectEntry.getObjectEntryId()
			).put(
				"entryURL", entryURL
			).put(
				"userId", TestPropsValues.getUserId()
			),
			serviceContext);

		Assert.assertTrue(link, link.startsWith(entryURL + "?backURL="));

		String backURL = HttpComponentsUtil.getParameter(
			link, "backURL", false);

		Assert.assertEquals(
			"com_liferay_notifications_web_portlet_NotificationsPortlet",
			HttpComponentsUtil.getParameter(
				URLCodec.decodeURL(backURL), "p_p_id", false));
		Assert.assertEquals(
			backURL,
			HttpComponentsUtil.getParameter(link, "p_l_back_url", false));

		Assert.assertEquals(
			"Notifications",
			HttpComponentsUtil.getParameter(link, "p_l_back_url_title", false));

		_groupLocalService.deleteGroup(group);
		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);
	}

	@Test
	public void testIsApplicable() throws Exception {
		_testIsApplicableWithBlogsEntry();
		_testIsApplicableWithObjectEntry();
	}

	private String _getLink(
			JSONObject payloadJSONObject, ServiceContext serviceContext)
		throws Exception {

		UserNotificationEvent userNotificationEvent =
			_userNotificationEventLocalService.addUserNotificationEvent(
				TestPropsValues.getUserId(), MentionsPortletKeys.MENTIONS,
				System.currentTimeMillis(),
				UserNotificationDeliveryConstants.TYPE_WEBSITE, 0,
				payloadJSONObject.toString(), false,
				ServiceContextTestUtil.getServiceContext());

		UserNotificationFeedEntry userNotificationFeedEntry =
			UserNotificationManagerUtil.interpret(
				StringPool.BLANK, userNotificationEvent, serviceContext);

		_userNotificationEventLocalService.deleteUserNotificationEvent(
			userNotificationEvent);

		return userNotificationFeedEntry.getLink();
	}

	private boolean _isApplicable(UserNotificationEvent userNotificationEvent)
		throws Exception {

		UserNotificationFeedEntry userNotificationFeedEntry =
			UserNotificationManagerUtil.interpret(
				StringPool.BLANK, userNotificationEvent,
				ServiceContextTestUtil.getServiceContext());

		return userNotificationFeedEntry.isApplicable();
	}

	private void _testIsApplicableWithBlogsEntry() throws Exception {
		BlogsEntry blogsEntry1 = BlogsTestUtil.addEntry(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext());
		BlogsEntry blogsEntry2 = BlogsTestUtil.addEntry(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext());

		User user = UserTestUtil.addUser();

		String mentionHTML = StringBundler.concat(
			"<span class=\"mention mention-user-",
			user.getExternalReferenceCode(), "\">@", user.getFullName(),
			"</span>");

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setAttribute(
			"discussionClassName", BlogsEntry.class.getName());
		serviceContext.setAttribute(
			"discussionClassNameId",
			String.valueOf(_portal.getClassNameId(BlogsEntry.class)));
		serviceContext.setAttribute(
			"discussionClassPK", String.valueOf(blogsEntry2.getEntryId()));
		serviceContext.setAttribute("previousBody", mentionHTML);

		_commentManager.addComment(
			null, TestPropsValues.getUserId(), blogsEntry1.getGroupId(),
			BlogsEntry.class.getName(), blogsEntry1.getEntryId(),
			StringPool.BLANK, null,
			StringBundler.concat(
				"<p>", RandomTestUtil.randomString(), StringPool.SPACE,
				mentionHTML, "</p>"),
			className -> serviceContext);

		List<UserNotificationEvent> userNotificationEvents =
			UserNotificationEventTestUtil.getUserNotificationEvents(
				user.getUserId());

		Assert.assertEquals(
			userNotificationEvents.toString(), 1,
			userNotificationEvents.size());

		UserNotificationEvent userNotificationEvent =
			userNotificationEvents.get(0);

		JSONObject payloadJSONObject = _jsonFactory.createJSONObject(
			userNotificationEvent.getPayload());

		Assert.assertFalse(payloadJSONObject.has("discussionClassName"));
		Assert.assertFalse(payloadJSONObject.has("discussionClassPK"));

		UserTestUtil.setUser(user);

		Assert.assertTrue(_isApplicable(userNotificationEvent));

		UserTestUtil.setUser(TestPropsValues.getUser());

		_blogsEntryLocalService.deleteEntry(blogsEntry1);
		_blogsEntryLocalService.deleteEntry(blogsEntry2);
		_userLocalService.deleteUser(user);
	}

	private void _testIsApplicableWithObjectEntry() throws Exception {
		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.publishObjectDefinition();

		ObjectEntry objectEntry = ObjectEntryTestUtil.addObjectEntry(
			objectDefinition);

		User user = UserTestUtil.addUser();

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		_resourcePermissionLocalService.setResourcePermissions(
			TestPropsValues.getCompanyId(), objectDefinition.getClassName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(objectEntry.getObjectEntryId()), role.getRoleId(),
			new String[] {ActionKeys.VIEW});

		_userLocalService.addRoleUser(role.getRoleId(), user);

		UserNotificationEvent userNotificationEvent =
			_userNotificationEventLocalService.addUserNotificationEvent(
				user.getUserId(), MentionsPortletKeys.MENTIONS,
				System.currentTimeMillis(),
				UserNotificationDeliveryConstants.TYPE_WEBSITE, 0,
				JSONUtil.put(
					"discussionClassName", objectDefinition.getClassName()
				).put(
					"discussionClassPK", objectEntry.getObjectEntryId()
				).put(
					"userId", TestPropsValues.getUserId()
				).toString(),
				false, ServiceContextTestUtil.getServiceContext());

		UserTestUtil.setUser(user);

		Assert.assertTrue(_isApplicable(userNotificationEvent));

		objectEntry = _objectEntryLocalService.moveObjectEntryToTrash(
			TestPropsValues.getUserId(), objectEntry,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertFalse(_isApplicable(userNotificationEvent));

		_objectEntryLocalService.restoreObjectEntryFromTrash(
			TestPropsValues.getUserId(), objectEntry,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertTrue(_isApplicable(userNotificationEvent));

		_resourcePermissionLocalService.setResourcePermissions(
			TestPropsValues.getCompanyId(), objectDefinition.getClassName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(objectEntry.getObjectEntryId()), role.getRoleId(),
			new String[0]);

		Assert.assertFalse(_isApplicable(userNotificationEvent));

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);
		_roleLocalService.deleteRole(role);
		_userLocalService.deleteUser(user);
	}

	@Inject
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Inject
	private CommentManager _commentManager;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private Portal _portal;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private UserLocalService _userLocalService;

	@Inject
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}