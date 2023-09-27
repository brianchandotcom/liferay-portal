/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.subscriptions.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;
import com.liferay.subscription.test.util.BaseSubscriptionClassTypeTestCase;

import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
public class JournalSubscriptionClassTypeTest
	extends BaseSubscriptionClassTypeTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), SynchronousMailTestRule.INSTANCE);

	@Override
	protected long addBaseModelWithClassType(long containerId, long classTypeId)
		throws Exception {

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.getStructure(
			classTypeId);

		List<DDMTemplate> ddmTemplates = ddmStructure.getTemplates();

		DDMTemplate ddmTemplate = ddmTemplates.get(0);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		serviceContext.setLayoutFullURL("http://layout_url");

		JournalArticle article = JournalTestUtil.addArticleWithXMLContent(
			containerId, JournalArticleConstants.CLASS_NAME_ID_DEFAULT,
			"<title>Test Article</title>", ddmStructure.getStructureKey(),
			ddmTemplate.getTemplateKey(), LocaleUtil.getSiteDefault(), null,
			serviceContext);

		return article.getResourcePrimKey();
	}

	@Override
	protected long addClassType() throws Exception {
		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			group.getGroupId(), JournalArticle.class.getName());

		DDMTemplateTestUtil.addTemplate(
			group.getGroupId(), ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class));

		return ddmStructure.getStructureId();
	}

	@Override
	protected void addSubscriptionClassType(long classTypeId) throws Exception {
		JournalArticleLocalServiceUtil.subscribeStructure(
			group.getGroupId(), user.getUserId(), classTypeId);
	}

	@Override
	protected void deleteSubscriptionClassType(long classTypeId)
		throws Exception {

		JournalArticleLocalServiceUtil.unsubscribeStructure(
			group.getGroupId(), user.getUserId(), classTypeId);
	}

	@Override
	protected Long getDefaultClassTypeId() throws Exception {
		Company company = CompanyLocalServiceUtil.getCompany(
			group.getCompanyId());

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.getStructure(
			company.getGroupId(),
			PortalUtil.getClassNameId(JournalArticle.class),
			"BASIC-WEB-CONTENT");

		return ddmStructure.getStructureId();
	}

	@Override
	protected void updateBaseModel(long userId, long baseModelId)
		throws Exception {

		JournalArticle article =
			JournalArticleLocalServiceUtil.getLatestArticle(baseModelId);

		JournalTestUtil.updateArticleWithWorkflow(userId, article, true);
	}

}