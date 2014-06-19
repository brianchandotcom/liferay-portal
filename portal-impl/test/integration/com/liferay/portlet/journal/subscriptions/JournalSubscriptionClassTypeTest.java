/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.journal.subscriptions;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousMailExecutionTestListener;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.subscriptions.BaseSubscriptionClassTypeTestCase;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMStructureTestUtil;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMTemplateTestUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.util.test.JournalTestUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 * @author Roberto Díaz
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousMailExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class JournalSubscriptionClassTypeTest
	extends BaseSubscriptionClassTypeTestCase {

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
			containerId, JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			"<title>Test Article</title>", ddmStructure.getStructureKey(),
			ddmTemplate.getTemplateKey(), LocaleUtil.getSiteDefault(), null,
			serviceContext);

		return article.getResourcePrimKey();
	}

	@Override
	protected long addClassType() throws Exception {
		_ddmStructure = DDMStructureTestUtil.addStructure(
			group.getGroupId(), JournalArticle.class.getName());

		_ddmTemplate = DDMTemplateTestUtil.addTemplate(
			group.getGroupId(), _ddmStructure.getStructureId());

		return _ddmStructure.getStructureId();
	}

	@Override
	protected void addSubscriptionClassType(long classTypeId) throws Exception {
		JournalArticleLocalServiceUtil.subscribeStructure(
			group.getGroupId(), TestPropsValues.getUserId(), classTypeId);
	}

	@Override
	protected Long getDefaultClassTypeId() throws Exception {
		Company company = CompanyLocalServiceUtil.getCompany(
			group.getCompanyId());

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.getStructure(
			company.getGroupId(),
			PortalUtil.getClassNameId(JournalArticle.class),
			"BASIC-WEB-CONTENT");

		Assert.assertNotNull(ddmStructure);

		return ddmStructure.getStructureId();
	}

	protected DDMStructure _ddmStructure;
	protected DDMTemplate _ddmTemplate;

}