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

package com.liferay.headless.admin.content.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.headless.admin.content.client.dto.v1_0.DisplayPageTemplate;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.test.rule.Inject;

import java.util.Collection;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class DisplayPageTemplateResourceTest
	extends BaseDisplayPageTemplateResourceTestCase {

	@Override
	@Test
	public void testGetSiteDisplayPageTemplate() throws Exception {
		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getLayoutPageTemplateEntry(testGroup.getGroupId());

		Assert.assertNotNull(
			displayPageTemplateResource.getSiteDisplayPageTemplate(
				testGroup.getGroupId(),
				layoutPageTemplateEntry.getLayoutPageTemplateEntryKey()));
	}

	@Override
	public DisplayPageTemplate
			testGetSiteDisplayPageTemplatesPage_addDisplayPageTemplate(
				Long siteId, DisplayPageTemplate displayPageTemplate)
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getLayoutPageTemplateEntry(siteId);

		displayPageTemplate.setDateCreated(
			layoutPageTemplateEntry.getCreateDate());
		displayPageTemplate.setDateModified(
			layoutPageTemplateEntry.getModifiedDate());
		displayPageTemplate.setDisplayPageTemplateKey(
			layoutPageTemplateEntry.getLayoutPageTemplateEntryKey());
		displayPageTemplate.setMarkedAsDefault(
			layoutPageTemplateEntry.isDefaultTemplate());

		displayPageTemplate.setSiteId(siteId);
		displayPageTemplate.setTitle(layoutPageTemplateEntry.getName());

		return displayPageTemplate;
	}

	@Override
	@Test
	public void testGraphQLGetSiteDisplayPageTemplatesPage() throws Exception {
		Long siteId = testGetSiteDisplayPageTemplatesPage_getSiteId();

		GraphQLField graphQLField = new GraphQLField(
			"admin",
			new GraphQLField(
				"displayPageTemplates",
				HashMapBuilder.<String, Object>put(
					"page", 1
				).put(
					"pageSize", 2
				).put(
					"siteKey", "\"" + siteId + "\""
				).build(),
				new GraphQLField("items", getGraphQLFields()),
				new GraphQLField("page"), new GraphQLField("totalCount")));

		JSONObject contentStructuresJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/admin", "JSONObject/displayPageTemplates");

		Assert.assertEquals(0, contentStructuresJSONObject.get("totalCount"));

		testGetSiteDisplayPageTemplatesPage_addDisplayPageTemplate(
			siteId, randomDisplayPageTemplate());

		contentStructuresJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/admin", "JSONObject/displayPageTemplates");

		Assert.assertEquals(1, contentStructuresJSONObject.get("totalCount"));
	}

	@Override
	protected Collection<EntityField> getEntityFields() {
		return Collections.singleton(
			new EntityField(
				"title", EntityField.Type.STRING, o -> null, o -> null,
				o -> null));
	}

	private LayoutPageTemplateEntry _getLayoutPageTemplateEntry(Long siteId)
		throws Exception {

		return _layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
			TestPropsValues.getUserId(), siteId, 0,
			_portal.getClassNameId(BlogsEntry.class.getName()), 0,
			RandomTestUtil.randomString(),
			LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE, 0, true, 0,
			0, 0, WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext(testGroup.getGroupId()));
	}

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private Portal _portal;

}