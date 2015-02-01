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

package com.liferay.portlet.journal.asset;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.asset.service.persistence.BaseAssetSearchTestCase;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMStructureTestUtil;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMTemplateTestUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.util.test.JournalTestUtil;

import java.util.Date;

import org.junit.ClassRule;
import org.junit.Rule;

/**
 * @author Eudaldo Alonso
 */
@Sync
public class JournalArticleAssetSearchTest extends BaseAssetSearchTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Override
	protected BaseModel<?> addBaseModel(
			BaseModel<?> parentBaseModel, String keywords, Date expirationDate,
			ServiceContext serviceContext)
		throws Exception {

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		return JournalTestUtil.addArticle(
			serviceContext.getScopeGroupId(), keywords, "Content",
			expirationDate, serviceContext);
	}

	@Override
	protected BaseModel<?> addBaseModel(
			BaseModel<?> parentBaseModel, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		return JournalTestUtil.addArticle(
			serviceContext.getScopeGroupId(), keywords, "Content",
			serviceContext);
	}

	@Override
	protected BaseModel<?> addBaseModelWithClassType(
			BaseModel<?> parentBaseModel, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		if (_ddmStructure == null) {
			_ddmStructure = DDMStructureTestUtil.addStructure(
				serviceContext.getScopeGroupId(),
				JournalArticle.class.getName());
		}

		if (_ddmTemplate == null) {
			_ddmTemplate = DDMTemplateTestUtil.addTemplate(
				serviceContext.getScopeGroupId(),
				_ddmStructure.getStructureId());
		}

		String content = DDMStructureTestUtil.getSampleStructuredContent();

		return JournalTestUtil.addArticleWithXMLContent(
			serviceContext.getScopeGroupId(), content,
			_ddmStructure.getStructureKey(), _ddmTemplate.getTemplateKey());
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return JournalArticle.class;
	}

	@Override
	protected long[] getClassTypeIds() {
		if (_ddmStructure == null) {
			return null;
		}

		return new long[] {_ddmStructure.getStructureId()};
	}

	@Override
	protected String getSearchKeywords() {
		return "title";
	}

	protected DDMStructure _ddmStructure;
	protected DDMTemplate _ddmTemplate;

}