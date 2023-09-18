/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.manager.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.constants.LockedLayoutType;
import com.liferay.layout.manager.LayoutLockManager;
import com.liferay.layout.model.LockedLayout;
import com.liferay.layout.model.LockedLayoutOrder;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.utility.page.kernel.constants.LayoutUtilityPageEntryConstants;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalService;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.exception.LockedLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.lock.LockManager;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.lock.service.LockLocalService;
import com.liferay.portal.model.impl.LayoutModelImpl;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portletmvc4spring.test.mock.web.portlet.MockActionRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jürgen Kappler
 */
@FeatureFlags("LPS-180328")
@RunWith(Arquillian.class)
public class LayoutLockManagerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addUser();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getCompanyId(), _group.getGroupId(), _user.getUserId());
	}

	@Test
	public void testGetLock() throws Exception {
		Layout draftLayout = _getDraftLayout();

		_lockLayout(draftLayout, _user);

		Lock lock = _lockManager.fetchLock(
			Layout.class.getName(), draftLayout.getPlid());

		Assert.assertNotNull(lock);
	}

	@Test
	public void testGetLockedLayouts() throws Exception {
		long[] layoutPlids = {};

		Layout draftLayout1 = _getDraftLayout();

		layoutPlids = ArrayUtil.append(layoutPlids, draftLayout1.getPlid());

		_lockLayout(draftLayout1, _user);

		Layout draftLayout2 = _getDraftLayout();

		layoutPlids = ArrayUtil.append(layoutPlids, draftLayout2.getPlid());

		_lockLayout(draftLayout2, _user);

		LayoutTestUtil.addTypePortletLayout(_group);
		LayoutTestUtil.addTypeContentLayout(_group);

		_assertLockedLayouts(
			2, layoutPlids,
			_layoutLockManager.getLockedLayouts(
				TestPropsValues.getCompanyId(), _group.getGroupId(), null,
				null));
	}

	@Test
	public void testGetLockedLayoutsFilterByCollectionPage() throws Exception {
		Layout draftLayout = _getDraftLayout(LayoutConstants.TYPE_COLLECTION);

		_lockLayout(draftLayout, _user);

		_lockLayout(_getDraftLayout(), _user);

		_assertLockedLayouts(
			1, new long[] {draftLayout.getPlid()},
			_layoutLockManager.getLockedLayouts(
				TestPropsValues.getCompanyId(), _group.getGroupId(), null,
				LockedLayoutType.COLLECTION_PAGE));
	}

	@Test
	public void testGetLockedLayoutsFilterByContentPage() throws Exception {
		Layout draftLayout = _getDraftLayout();

		_lockLayout(draftLayout, _user);

		_lockLayout(_getDraftLayout(LayoutConstants.TYPE_COLLECTION), _user);

		_assertLockedLayouts(
			1, new long[] {draftLayout.getPlid()},
			_layoutLockManager.getLockedLayouts(
				TestPropsValues.getCompanyId(), _group.getGroupId(), null,
				LockedLayoutType.CONTENT_PAGE));
	}

	@Test
	public void testGetLockedLayoutsFilterByContentPageTemplate()
		throws Exception {

		Layout draftLayout = _getDraftLayout();

		_addLayoutPageTemplateEntry(
			draftLayout.getClassPK(),
			LayoutPageTemplateEntryTypeConstants.TYPE_BASIC);

		_lockLayout(draftLayout, _user);

		_lockLayout(_getDraftLayout(), _user);

		_assertLockedLayouts(
			1, new long[] {draftLayout.getPlid()},
			_layoutLockManager.getLockedLayouts(
				TestPropsValues.getCompanyId(), _group.getGroupId(), null,
				LockedLayoutType.CONTENT_PAGE_TEMPLATE));
	}

	@Test
	public void testGetLockedLayoutsFilterByDisplayPageTemplate()
		throws Exception {

		Layout draftLayout = _getDraftLayout(
			LayoutConstants.TYPE_ASSET_DISPLAY);

		_addLayoutPageTemplateEntry(
			draftLayout.getClassPK(),
			LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE);

		_lockLayout(draftLayout, _user);

		_lockLayout(_getDraftLayout(), _user);

		_assertLockedLayouts(
			1, new long[] {draftLayout.getPlid()},
			_layoutLockManager.getLockedLayouts(
				TestPropsValues.getCompanyId(), _group.getGroupId(), null,
				LockedLayoutType.DISPLAY_PAGE_TEMPLATE));
	}

	@Test
	public void testGetLockedLayoutsFilterByMasterPage() throws Exception {
		Layout draftLayout = _getDraftLayout();

		_addLayoutPageTemplateEntry(
			draftLayout.getClassPK(),
			LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT);

		_lockLayout(draftLayout, _user);

		_lockLayout(_getDraftLayout(), _user);

		_assertLockedLayouts(
			1, new long[] {draftLayout.getPlid()},
			_layoutLockManager.getLockedLayouts(
				TestPropsValues.getCompanyId(), _group.getGroupId(), null,
				LockedLayoutType.MASTER_PAGE));
	}

	@Test
	public void testGetLockedLayoutsFilterByUtilityPage() throws Exception {
		Layout draftLayout = _getDraftLayout();

		_layoutUtilityPageEntryLocalService.addLayoutUtilityPageEntry(
			null, _serviceContext.getUserId(),
			_serviceContext.getScopeGroupId(), draftLayout.getClassPK(), 0,
			false, RandomTestUtil.randomString(),
			LayoutUtilityPageEntryConstants.TYPE_SC_INTERNAL_SERVER_ERROR, 0,
			_serviceContext);

		_lockLayout(draftLayout, _user);

		_lockLayout(_getDraftLayout(), _user);

		_assertLockedLayouts(
			1, new long[] {draftLayout.getPlid()},
			_layoutLockManager.getLockedLayouts(
				TestPropsValues.getCompanyId(), _group.getGroupId(), null,
				LockedLayoutType.UTILITY_PAGE));
	}

	@Test
	public void testGetLockedLayoutsOrderByLastAutosaveAscending()
		throws Exception {

		_assertLockedLayoutsCreateDates(
			_getLockedLayoutsCreateDates(Collections::sort),
			_layoutLockManager.getLockedLayouts(
				TestPropsValues.getCompanyId(), _group.getGroupId(),
				new LockedLayoutOrder(
					true, LocaleUtil.getDefault(),
					LockedLayoutOrder.LockedLayoutOrderType.LAST_AUTOSAVE),
				null));
	}

	@Test
	public void testGetLockedLayoutsOrderByLastAutosaveDescending()
		throws Exception {

		_assertLockedLayoutsCreateDates(
			_getLockedLayoutsCreateDates(null),
			_layoutLockManager.getLockedLayouts(
				TestPropsValues.getCompanyId(), _group.getGroupId(),
				new LockedLayoutOrder(
					false, LocaleUtil.getDefault(),
					LockedLayoutOrder.LockedLayoutOrderType.LAST_AUTOSAVE),
				null));
	}

	@Test
	public void testGetLockedLayoutsOrderByNameAscending() throws Exception {
		List<String> names = new ArrayList<>();

		Locale locale = LocaleUtil.getDefault();

		for (int i = 0; i < 5; i++) {
			Layout draftLayout = _getDraftLayout();

			_lockLayout(draftLayout, _user);

			names.add(
				LocalizationUtil.getLocalization(
					draftLayout.getName(), locale.getLanguage()));
		}

		Collections.sort(names, String.CASE_INSENSITIVE_ORDER);

		List<LockedLayout> lockedLayouts = _layoutLockManager.getLockedLayouts(
			TestPropsValues.getCompanyId(), _group.getGroupId(),
			new LockedLayoutOrder(
				true, LocaleUtil.getDefault(),
				LockedLayoutOrder.LockedLayoutOrderType.NAME),
			null);

		Assert.assertEquals(
			lockedLayouts.toString(), names.size(), lockedLayouts.size());

		for (int i = 0; i < names.size(); i++) {
			LockedLayout lockedLayout = lockedLayouts.get(i);

			Assert.assertEquals(
				names.get(i),
				LocalizationUtil.getLocalization(
					lockedLayout.getName(), locale.getLanguage()));
		}
	}

	@Test(expected = LockedLayoutException.class)
	public void testGetLockWithDifferentUser() throws Exception {
		Layout draftLayout = _getDraftLayout();

		User newUser = UserTestUtil.addUser();

		try {
			_lockLayout(draftLayout, _user);
			_lockLayout(draftLayout, newUser);
		}
		finally {
			_userLocalService.deleteUser(newUser);
		}
	}

	@Test
	public void testGetLockWithSameUser() throws Exception {
		long originalLockExpirationTime = LayoutModelImpl.LOCK_EXPIRATION_TIME;

		Layout draftLayout = _getDraftLayout();

		try {
			ReflectionTestUtil.setFieldValue(
				LayoutModelImpl.class, "LOCK_EXPIRATION_TIME", 60000);

			_lockLayout(draftLayout, _user);

			Lock lock1 = _lockManager.fetchLock(
				Layout.class.getName(), draftLayout.getPlid());

			Assert.assertNotNull(lock1);
			Assert.assertNotNull(lock1.getExpirationDate());

			_lockLayout(draftLayout, _user);

			Lock lock2 = _lockManager.fetchLock(
				Layout.class.getName(), draftLayout.getPlid());

			Assert.assertNotNull(lock2);
			Assert.assertNotNull(lock2.getExpirationDate());

			Assert.assertEquals(lock1.getLockId(), lock2.getLockId());

			Date lock1ExpirationDate = lock1.getExpirationDate();
			Date lock2ExpirationDate = lock2.getExpirationDate();

			Assert.assertTrue(lock2ExpirationDate.after(lock1ExpirationDate));
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				LayoutModelImpl.class, "LOCK_EXPIRATION_TIME",
				originalLockExpirationTime);
		}
	}

	@Test
	public void testUnlock() throws Exception {
		Layout draftLayout = _getDraftLayout();

		_lockLayout(draftLayout, _user);

		Lock lock = _lockManager.fetchLock(
			Layout.class.getName(), draftLayout.getPlid());

		Assert.assertNotNull(lock);

		_layoutLockManager.unlock(draftLayout, _user.getUserId());

		lock = _lockManager.fetchLock(
			Layout.class.getName(), draftLayout.getPlid());

		Assert.assertNull(lock);
	}

	private void _addLayoutPageTemplateEntry(long plid, int type)
		throws Exception {

		_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
			TestPropsValues.getUserId(), _group.getGroupId(), 0, 0, 0,
			RandomTestUtil.randomString(), type, 0, true, 0, plid, 0,
			WorkflowConstants.STATUS_APPROVED, _serviceContext);
	}

	private void _assertLockedLayouts(
		int expectedSize, long[] expectedLayoutPlids,
		List<LockedLayout> lockedLayouts) {

		Assert.assertEquals(
			lockedLayouts.toString(), expectedSize, lockedLayouts.size());

		for (LockedLayout lockedLayout : lockedLayouts) {
			Assert.assertTrue(
				ArrayUtil.contains(
					expectedLayoutPlids, lockedLayout.getPlid()));
		}
	}

	private void _assertLockedLayoutsCreateDates(
		List<Date> expectedCreateDates, List<LockedLayout> lockedLayouts) {

		Assert.assertEquals(
			lockedLayouts.toString(), expectedCreateDates.size(),
			lockedLayouts.size());

		for (int i = 0; i < expectedCreateDates.size(); i++) {
			LockedLayout lockedLayout = lockedLayouts.get(i);

			Assert.assertEquals(
				expectedCreateDates.get(i), lockedLayout.getLastAutoSaveDate());
		}
	}

	private Layout _getDraftLayout() throws Exception {
		return _getDraftLayout(LayoutConstants.TYPE_CONTENT);
	}

	private Layout _getDraftLayout(String type) throws Exception {
		if (Objects.equals(LayoutConstants.TYPE_ASSET_DISPLAY, type)) {
			_serviceContext.setAttribute(
				"layout.instanceable.allowed", Boolean.TRUE);
		}

		Layout layout = _layoutLocalService.addLayout(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, 0, 0,
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), Collections.emptyMap(),
			Collections.emptyMap(), Collections.emptyMap(), type,
			UnicodePropertiesBuilder.put(
				"published", "true"
			).buildString(),
			false, false, Collections.emptyMap(), 0, _serviceContext);

		Layout draftLayout = layout.fetchDraftLayout();

		draftLayout.setStatus(WorkflowConstants.STATUS_DRAFT);

		return _layoutLocalService.updateLayout(draftLayout);
	}

	private List<Date> _getLockedLayoutsCreateDates(
			UnsafeConsumer<List<Date>, Exception>
				createDatesOrderUnsafeConsumer)
		throws Exception {

		List<Date> createDates = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			Layout draftLayout = _getDraftLayout();

			_lockLayout(draftLayout, _user);

			com.liferay.portal.lock.model.Lock lock =
				_lockLocalService.fetchLock(
					Layout.class.getName(), draftLayout.getPlid());

			Assert.assertNotNull(lock);

			lock.setCreateDate(
				new Date(System.currentTimeMillis() - (i * Time.MINUTE)));

			lock = _lockLocalService.updateLock(lock);

			createDates.add(lock.getCreateDate());
		}

		if (createDatesOrderUnsafeConsumer != null) {
			createDatesOrderUnsafeConsumer.accept(createDates);
		}

		return createDates;
	}

	private void _lockLayout(Layout layout, User user) throws PortalException {
		MockActionRequest mockActionRequest = new MockActionRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setLayout(layout);
		themeDisplay.setUser(user);

		mockActionRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		_layoutLockManager.getLock(mockActionRequest);
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutLockManager _layoutLockManager;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private LayoutUtilityPageEntryLocalService
		_layoutUtilityPageEntryLocalService;

	@Inject
	private LockLocalService _lockLocalService;

	@Inject
	private LockManager _lockManager;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

	@Inject
	private UserLocalService _userLocalService;

}