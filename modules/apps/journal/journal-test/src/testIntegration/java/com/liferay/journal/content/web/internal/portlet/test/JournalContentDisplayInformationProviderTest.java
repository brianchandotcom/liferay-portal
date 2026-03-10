/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.content.web.internal.portlet.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.constants.JournalContentPortletKeys;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.DisplayInformationProvider;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactory;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import jakarta.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mikel Lorza
 */
@RunWith(Arquillian.class)
public class JournalContentDisplayInformationProviderTest {

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
	}

	@Test
	public void testGetClassPK() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(
			_group.getGroupId());

		PortletPreferences portletPreferences =
			_portletPreferencesFactory.getPortletSetup(
				layout,
				JournalContentPortletKeys.JOURNAL_CONTENT + "_INSTANCE_" +
					RandomTestUtil.randomString(),
				null);

		JournalArticle journalArticle1 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		portletPreferences.setValue(
			"articleExternalReferenceCode",
			journalArticle1.getExternalReferenceCode());

		Assert.assertEquals(
			journalArticle1.getArticleId(),
			_displayInformationProvider.getClassPK(
				portletPreferences, _group.getGroupId()));

		Group companyGroup = _company.getGroup();

		JournalArticle journalArticle2 = JournalTestUtil.addArticle(
			companyGroup.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		portletPreferences.setValue(
			"articleExternalReferenceCode",
			journalArticle2.getExternalReferenceCode());

		portletPreferences.setValue(
			"groupExternalReferenceCode",
			companyGroup.getExternalReferenceCode());

		Assert.assertEquals(
			journalArticle2.getArticleId(),
			_displayInformationProvider.getClassPK(
				portletPreferences, _group.getGroupId()));
	}

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject(
		filter = "jakarta.portlet.name=" + JournalContentPortletKeys.JOURNAL_CONTENT
	)
	private DisplayInformationProvider _displayInformationProvider;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private PortletPreferencesFactory _portletPreferencesFactory;

}