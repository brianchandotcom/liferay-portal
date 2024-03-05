/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.performance.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTProcessLocalService;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMFieldLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.SiteInitializerRegistry;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author David Truong
 */
@DataGuard(scope = DataGuard.Scope.NONE)
@RunWith(Arquillian.class)
public class CTRowUtilPerformanceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()));

		PrincipalThreadLocal.setName(TestPropsValues.getUserId());

		_ctCollection1 = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), null);
		_ctCollection2 = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), null);

		_group = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() throws Exception {
		_ctCollectionLocalService.deleteCTCollection(_ctCollection1);

		_ctCollectionLocalService.deleteCTCollection(_ctCollection2);

		GroupTestUtil.deleteGroup(_group);

		PermissionThreadLocal.setPermissionChecker(null);

		PrincipalThreadLocal.setName(null);
	}

	@Test
	public void testDDMModificationPerformance() throws Exception {
		String formFieldName = RandomTestUtil.randomString();

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection1.getCtCollectionId())) {

			DDMFormTestUtil.addTextDDMFormFields(ddmForm, formFieldName);

			DDMFormValuesTestUtil.createDDMFormValuesWithRandomValues(ddmForm);
		}

		DDMFormValuesTestUtil.createDDMFormValuesWithRandomValues(ddmForm);

		DDMFormTestUtil.addTextDDMFormFields(ddmForm, formFieldName);

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			_ctProcessLocalService.addCTProcess(
				_ctCollection1.getUserId(), _ctCollection1.getCtCollectionId());
		}
	}

	@Test
	public void testJournalArticleModificationPerformance() throws Exception {
		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure.getStructureId(),
			_portal.getClassNameId(JournalArticle.class));

		JournalFolder folder = JournalTestUtil.addFolder(
			_group.getGroupId(), RandomTestUtil.randomString());

		JournalArticle journalArticle =
			JournalTestUtil.addArticleWithXMLContent(
				_group.getGroupId(), folder.getFolderId(),
				JournalArticleConstants.CLASS_NAME_ID_DEFAULT,
				DDMStructureTestUtil.getSampleStructuredContent(),
				ddmStructure.getStructureKey(), ddmTemplate.getTemplateKey());

		JournalArticle ctJournalArticle = null;

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection1.getCtCollectionId())) {

			ctJournalArticle = JournalTestUtil.updateArticle(journalArticle);
		}

		Assert.assertNotEquals(
			journalArticle.getTitle(), ctJournalArticle.getTitle());

		journalArticle = JournalTestUtil.updateArticle(journalArticle);

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			_ctProcessLocalService.addCTProcess(
				_ctCollection1.getUserId(), _ctCollection1.getCtCollectionId());
		}

		journalArticle = _journalArticleLocalService.getLatestArticle(
			journalArticle.getResourcePrimKey());

		Assert.assertEquals(
			journalArticle.getTitle(), ctJournalArticle.getTitle());
	}

	@Test
	public void testLayoutConflictPerformance() throws Exception {
		String layoutName = RandomTestUtil.randomString();

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection1.getCtCollectionId())) {

			LayoutTestUtil.addTypeContentLayout(_group, layoutName);
		}

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection2.getCtCollectionId())) {

			LayoutTestUtil.addTypeContentLayout(_group, layoutName);
		}

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			_ctProcessLocalService.addCTProcess(
				_ctCollection2.getUserId(), _ctCollection2.getCtCollectionId());
		}

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			_ctCollectionLocalService.checkConflicts(_ctCollection1);
		}
	}

	@Test
	public void testSiteConflictPerformance() throws Exception {
		String groupName = RandomTestUtil.randomString();

		Group group = null;

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection1.getCtCollectionId())) {

			group = GroupTestUtil.addGroup(
				GroupConstants.DEFAULT_PARENT_GROUP_ID, groupName,
				new ServiceContext());

			ServiceContextThreadLocal.pushServiceContext(
				ServiceContextTestUtil.getServiceContext(group.getGroupId()));

			SiteInitializer siteInitializer =
				_siteInitializerRegistry.getSiteInitializer(
					"com.liferay.site.initializer.masterclass");

			siteInitializer.initialize(group.getGroupId());
		}

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection2.getCtCollectionId())) {

			group = GroupTestUtil.addGroup(
				GroupConstants.DEFAULT_PARENT_GROUP_ID, groupName,
				new ServiceContext());

			ServiceContextThreadLocal.pushServiceContext(
				ServiceContextTestUtil.getServiceContext(group.getGroupId()));

			SiteInitializer siteInitializer =
				_siteInitializerRegistry.getSiteInitializer(
					"com.liferay.site.initializer.masterclass");

			siteInitializer.initialize(group.getGroupId());
		}

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			_ctProcessLocalService.addCTProcess(
				_ctCollection2.getUserId(), _ctCollection2.getCtCollectionId());
		}

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			_ctCollectionLocalService.checkConflicts(_ctCollection1);
		}

		GroupTestUtil.deleteGroup(group);
	}

	@Inject
	private BackgroundTaskLocalService _backgroundTaskLocalService;

	@Inject
	private CounterLocalService _counterLocalService;

	@DeleteAfterTestRun
	private CTCollection _ctCollection1;

	@DeleteAfterTestRun
	private CTCollection _ctCollection2;

	@Inject
	private CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private CTProcessLocalService _ctProcessLocalService;

	@Inject
	private DDMFieldLocalService _ddmFieldLocalService;

	@Inject
	private DDMStructureLocalService _ddmStructureLocalService;

	@Inject
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private Portal _portal;

	@Inject
	private SiteInitializerRegistry _siteInitializerRegistry;

}