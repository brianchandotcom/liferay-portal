/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTCollectionService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.journal.test.util.JournalFolderFixture;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutTypeController;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.LayoutTypeControllerTracker;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.SiteInitializerRegistry;

import javax.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Preston Crary
 */
@Ignore
@RunWith(Arquillian.class)
public class LargeDatasetCTTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_httpServletRequest = _getHttpServletRequest(TestPropsValues.getUser());

		_themeDisplay = _getThemeDisplay(
			_httpServletRequest, TestPropsValues.getUser());
	}

	@After
	public void tearDown() throws Exception {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testAssignOrganizations() throws Exception {
		for (int i = 0; i < _BATCH_SIZE; i++) {
			OrganizationTestUtil.addOrganization();
		}

		CTCollection ctCollection = _ctCollectionService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), RandomTestUtil.randomString());

		try (LoggingTimer loggingTimer = new LoggingTimer();
			SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollection.getCtCollectionId())) {

			long[] organizationIds = ListUtil.toLongArray(
				_organizationLocalService.getOrganizations(0, _BATCH_SIZE),
				Organization.ORGANIZATION_ID_ACCESSOR);

			_organizationLocalService.addUserOrganizations(
				TestPropsValues.getUserId(), organizationIds);
		}
	}

	@Test
	public void testDiscardCTEntryWithOver1000Entries() throws Exception {
		CTCollection ctCollection = _ctCollectionService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), RandomTestUtil.randomString());

		JournalFolder journalFolder = null;

		try (LoggingTimer loggingTimer = new LoggingTimer();
			SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollection.getCtCollectionId())) {

			journalFolder = _journalFolderFixture.addFolder(
				_group.getGroupId(), RandomTestUtil.randomString());

			for (int i = 0; i < _BATCH_SIZE; i++) {
				_journalFolderFixture.addFolder(
					_group.getGroupId(), journalFolder.getFolderId(),
					RandomTestUtil.randomString());
			}
		}

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			_ctCollectionService.discardCTEntry(
				ctCollection.getCtCollectionId(),
				_classNameLocalService.getClassNameId(JournalFolder.class),
				journalFolder.getFolderId());
		}

		Assert.assertEquals(
			0,
			_ctEntryLocalService.getCTCollectionCTEntriesCount(
				ctCollection.getCtCollectionId()));
	}

	@Ignore
	@Test
	public void testIncludeLayoutContent() throws Exception {
		CTCollection ctCollection = _ctCollectionService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), RandomTestUtil.randomString());

		try (LoggingTimer loggingTimer = new LoggingTimer();
			SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollection.getCtCollectionId())) {

			SiteInitializer siteInitializer =
				_siteInitializerRegistry.getSiteInitializer(
					"com.liferay.site.initializer.welcome");

			siteInitializer.initialize(_group.getGroupId());

			Layout layout = _layoutLocalService.fetchDefaultLayout(
				_group.getGroupId(), false);

			_themeDisplay.setLayout(layout);

			_httpServletRequest.setAttribute(
				WebKeys.THEME_DISPLAY, _themeDisplay);

			LayoutTypeController layoutTypeController =
				LayoutTypeControllerTracker.getLayoutTypeController(
					layout.getType());

			layoutTypeController.includeLayoutContent(
				_httpServletRequest, new MockHttpServletResponse(), layout);

			StringBundler sb = (StringBundler)_httpServletRequest.getAttribute(
				WebKeys.LAYOUT_CONTENT);

			Assert.assertTrue(
				sb.toString(), Validator.isNotNull(sb.toString()));
		}
	}

	@Test
	public void testMoveCTEntryWithOver1000Entries() throws Exception {
		CTCollection fromCTCollection = _ctCollectionService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), RandomTestUtil.randomString());

		CTCollection toCTCollection = _ctCollectionService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), RandomTestUtil.randomString());

		JournalFolder journalFolder = null;

		try (LoggingTimer loggingTimer = new LoggingTimer();
			SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					fromCTCollection.getCtCollectionId())) {

			journalFolder = _journalFolderFixture.addFolder(
				_group.getGroupId(), RandomTestUtil.randomString());

			for (int i = 0; i < _BATCH_SIZE; i++) {
				_journalFolderFixture.addFolder(
					_group.getGroupId(), journalFolder.getFolderId(),
					RandomTestUtil.randomString());
			}
		}

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			_ctCollectionService.moveCTEntry(
				fromCTCollection.getCtCollectionId(),
				toCTCollection.getCtCollectionId(),
				_classNameLocalService.getClassNameId(JournalFolder.class),
				journalFolder.getFolderId());
		}

		Assert.assertEquals(
			0,
			_ctEntryLocalService.getCTCollectionCTEntriesCount(
				fromCTCollection.getCtCollectionId()));

		Assert.assertNotEquals(
			0,
			_ctEntryLocalService.getCTCollectionCTEntriesCount(
				toCTCollection.getCtCollectionId()));
	}

	@Test
	public void testPublishCTCollectionWithOver1000Entries() throws Exception {
		CTCollection ctCollection = _ctCollectionService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), RandomTestUtil.randomString());

		try (LoggingTimer loggingTimer = new LoggingTimer();
			SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollection.getCtCollectionId())) {

			for (int i = 0; i < _BATCH_SIZE; i++) {
				_journalFolderFixture.addFolder(
					_group.getGroupId(), RandomTestUtil.randomString());
			}

			_ctCollectionService.publishCTCollection(
				TestPropsValues.getUserId(), ctCollection.getCtCollectionId());
		}

		ctCollection = _ctCollectionLocalService.getCTCollection(
			ctCollection.getCtCollectionId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, ctCollection.getStatus());

		ctCollection = _ctCollectionService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), RandomTestUtil.randomString());

		try (LoggingTimer loggingTimer = new LoggingTimer();
			SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollection.getCtCollectionId())) {

			for (JournalFolder journalFolder :
					_journalFolderLocalService.getFolders(
						_group.getGroupId())) {

				journalFolder.setName(RandomTestUtil.randomString());

				_journalFolderLocalService.updateJournalFolder(journalFolder);
			}

			_ctCollectionService.publishCTCollection(
				TestPropsValues.getUserId(), ctCollection.getCtCollectionId());
		}

		ctCollection = _ctCollectionLocalService.getCTCollection(
			ctCollection.getCtCollectionId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, ctCollection.getStatus());

		ctCollection = _ctCollectionService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), RandomTestUtil.randomString());

		try (LoggingTimer loggingTimer = new LoggingTimer();
			SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollection.getCtCollectionId())) {

			for (JournalFolder journalFolder :
					_journalFolderLocalService.getFolders(
						_group.getGroupId())) {

				_journalFolderLocalService.deleteFolder(journalFolder);
			}

			_ctCollectionService.publishCTCollection(
				TestPropsValues.getUserId(), ctCollection.getCtCollectionId());
		}

		ctCollection = _ctCollectionLocalService.getCTCollection(
			ctCollection.getCtCollectionId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, ctCollection.getStatus());
	}

	private HttpServletRequest _getHttpServletRequest(User user)
		throws Exception {

		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		httpServletRequest.setAttribute(
			WebKeys.CURRENT_URL, "http://localhost:8080/");

		UserTestUtil.setUser(user);

		httpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay(httpServletRequest, user));

		return httpServletRequest;
	}

	private ThemeDisplay _getThemeDisplay(
			HttpServletRequest httpServletRequest, User user)
		throws Exception {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		Company company = _companyLocalService.getCompany(
			_group.getCompanyId());

		themeDisplay.setCompany(company);

		themeDisplay.setLanguageId(_group.getDefaultLanguageId());

		LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
			_group.getGroupId(), false);

		themeDisplay.setLayoutSet(layoutSet);

		themeDisplay.setLocale(
			LocaleUtil.fromLanguageId(_group.getDefaultLanguageId()));
		themeDisplay.setLookAndFeel(layoutSet.getTheme(), null);
		themeDisplay.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));
		themeDisplay.setPortalDomain(company.getVirtualHostname());
		themeDisplay.setPortalURL(company.getPortalURL(_group.getGroupId()));
		themeDisplay.setRequest(httpServletRequest);
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setServerPort(8080);
		themeDisplay.setSignedIn(true);
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(user);

		return themeDisplay;
	}

	private static final int _BATCH_SIZE = 1001;

	@Inject
	private static ClassNameLocalService _classNameLocalService;

	@Inject
	private static CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private static CTCollectionService _ctCollectionService;

	@Inject
	private static JournalFolderLocalService _journalFolderLocalService;

	@Inject
	private static LayoutLocalService _layoutLocalService;

	@Inject
	private static LayoutSetLocalService _layoutSetLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private CTEntryLocalService _ctEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private HttpServletRequest _httpServletRequest;
	private final JournalFolderFixture _journalFolderFixture =
		new JournalFolderFixture(_journalFolderLocalService);

	@Inject
	private OrganizationLocalService _organizationLocalService;

	@Inject
	private Portal _portal;

	@Inject
	private SiteInitializerRegistry _siteInitializerRegistry;

	private ThemeDisplay _themeDisplay;

}