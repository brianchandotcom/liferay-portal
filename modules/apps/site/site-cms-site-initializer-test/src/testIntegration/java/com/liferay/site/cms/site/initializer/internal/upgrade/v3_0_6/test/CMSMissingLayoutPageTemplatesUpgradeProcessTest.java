/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.upgrade.v3_0_6.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.test.util.DisplayPageTemplateTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jan Brychta
 */
@RunWith(Arquillian.class)
public class CMSMissingLayoutPageTemplatesUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_company = CompanyTestUtil.addCompany(true);
	}

	@After
	public void tearDown() throws Exception {
		_companyLocalService.deleteCompany(_company);
	}

	@Test
	public void testUpgrade() throws Exception {
		_testUpgrade(false);
	}

	@Test
	public void testUpgradeWithoutPermissionChecker() throws Exception {
		_testUpgrade(true);
	}

	private LayoutPageTemplateEntry _addDisplayPageTemplate(
			long groupId, String name)
		throws Exception {

		return DisplayPageTemplateTestUtil.addDisplayPageTemplate(
			groupId, _portal.getClassNameId(RandomTestUtil.randomString()),
			null, true, null, name, WorkflowConstants.STATUS_APPROVED);
	}

	private void _testUpgrade(boolean clearPermissionChecker) throws Exception {
		Group group = _groupLocalService.getGroup(
			_company.getCompanyId(), GroupConstants.CMS);

		Layout preexistingLayout = LayoutTestUtil.addTypePortletLayout(group);

		LayoutPageTemplateEntry
			existingContentEditorMasterLayoutPageTemplateEntry =
				_layoutPageTemplateEntryLocalService.
					fetchLayoutPageTemplateEntry(
						group.getGroupId(), "content-editor-master");

		if (existingContentEditorMasterLayoutPageTemplateEntry != null) {
			_layoutPageTemplateEntryLocalService.deleteLayoutPageTemplateEntry(
				existingContentEditorMasterLayoutPageTemplateEntry);
		}

		Assert.assertNull(
			_layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
				group.getGroupId(), "content-editor-master"));

		LayoutPageTemplateEntry basicDocumentLayoutPageTemplateEntry =
			_addDisplayPageTemplate(group.getGroupId(), "Basic Document");

		Layout basicDocumentLayout = _layoutLocalService.getLayout(
			basicDocumentLayoutPageTemplateEntry.getPlid());

		Layout basicDocumentDraftLayout =
			basicDocumentLayout.fetchDraftLayout();

		LayoutPageTemplateEntry unmatchedLayoutPageTemplateEntry =
			_addDisplayPageTemplate(
				group.getGroupId(), RandomTestUtil.randomString());

		Layout unmatchedLayout = _layoutLocalService.getLayout(
			unmatchedLayoutPageTemplateEntry.getPlid());

		Layout unmatchedDraftLayout = unmatchedLayout.fetchDraftLayout();

		Assert.assertTrue(
			Validator.isNull(
				basicDocumentDraftLayout.
					getMasterLayoutPageTemplateEntryERC()));

		try (SafeCloseable safeCloseable = CompanyThreadLocal.lock(
				_company.getCompanyId())) {

			if (clearPermissionChecker) {
				PermissionThreadLocal.setPermissionChecker(null);
				PrincipalThreadLocal.setName(null);
			}

			UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
				_upgradeStepRegistrator,
				"com.liferay.site.cms.site.initializer.internal.upgrade." +
					"v3_0_6.CMSMissingLayoutPageTemplatesUpgradeProcess");

			upgradeProcess.upgrade();
		}

		Assert.assertNotNull(
			_layoutLocalService.fetchLayout(preexistingLayout.getPlid()));

		LayoutPageTemplateEntry contentEditorMasterLayoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
				group.getGroupId(), "content-editor-master");

		Assert.assertNotNull(contentEditorMasterLayoutPageTemplateEntry);

		String externalReferenceCode =
			contentEditorMasterLayoutPageTemplateEntry.
				getExternalReferenceCode();

		Layout updatedBasicDocumentLayout = _layoutLocalService.fetchLayout(
			basicDocumentLayout.getPlid());
		Layout updatedBasicDocumentDraftLayout =
			_layoutLocalService.fetchLayout(basicDocumentDraftLayout.getPlid());

		Assert.assertEquals(
			externalReferenceCode,
			updatedBasicDocumentLayout.getMasterLayoutPageTemplateEntryERC());
		Assert.assertEquals(
			externalReferenceCode,
			updatedBasicDocumentDraftLayout.
				getMasterLayoutPageTemplateEntryERC());

		Layout updatedUnmatchedLayout = _layoutLocalService.fetchLayout(
			unmatchedLayout.getPlid());
		Layout updatedUnmatchedDraftLayout = _layoutLocalService.fetchLayout(
			unmatchedDraftLayout.getPlid());

		Assert.assertTrue(
			Validator.isNull(
				updatedUnmatchedLayout.getMasterLayoutPageTemplateEntryERC()));
		Assert.assertTrue(
			Validator.isNull(
				updatedUnmatchedDraftLayout.
					getMasterLayoutPageTemplateEntryERC()));
	}

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private Portal _portal;

	@Inject(
		filter = "component.name=com.liferay.site.cms.site.initializer.internal.upgrade.registry.SiteCMSSiteInitializerUpgradeStepRegistrator"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}