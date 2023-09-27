/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.content.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.headless.admin.content.client.dto.v1_0.DisplayPageTemplate;
import com.liferay.headless.admin.content.client.pagination.Page;
import com.liferay.headless.admin.content.client.pagination.Pagination;
import com.liferay.headless.admin.content.client.resource.v1_0.DisplayPageTemplateResource;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.test.rule.Inject;

import java.util.Collection;
import java.util.Collections;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class DisplayPageTemplateResourceTest
	extends BaseDisplayPageTemplateResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_originalName = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(TestPropsValues.getUserId());
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		PrincipalThreadLocal.setName(_originalName);
	}

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
	@Test
	public void testGetSiteDisplayPageTemplatesPage() throws Exception {
		super.testGetSiteDisplayPageTemplatesPage();

		Page<DisplayPageTemplate> page =
			displayPageTemplateResource.getSiteDisplayPageTemplatesPage(
				testGroup.getGroupId(), Pagination.of(1, 10), null);

		for (DisplayPageTemplate displayPageTemplate : page.getItems()) {
			LayoutPageTemplateEntry layoutPageTemplateEntry =
				_layoutPageTemplateEntryLocalService.getLayoutPageTemplateEntry(
					testGroup.getGroupId(),
					displayPageTemplate.getDisplayPageTemplateKey());

			_layoutPageTemplateEntryLocalService.deleteLayoutPageTemplateEntry(
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId());
		}

		DisplayPageTemplate displayPageTemplate =
			testGetSiteDisplayPageTemplatesPage_addDisplayPageTemplate(
				testGroup.getGroupId(), randomDisplayPageTemplate());

		displayPageTemplateResource = DisplayPageTemplateResource.builder(
		).authentication(
			"test@liferay.com", "test"
		).locale(
			LocaleUtil.getDefault()
		).parameters(
			"nestedFields", "profileURL"
		).build();

		page = displayPageTemplateResource.getSiteDisplayPageTemplatesPage(
			testGroup.getGroupId(), Pagination.of(1, 10), null);

		Assert.assertEquals(1, page.getTotalCount());
		Assert.assertEquals(
			displayPageTemplate.getDisplayPageTemplateKey(),
			page.fetchFirstItem(
			).getDisplayPageTemplateKey());
		Assert.assertNotNull(
			page.fetchFirstItem(
			).getCreator(
			).getProfileURL());
		assertValid(page);

		displayPageTemplateResource = DisplayPageTemplateResource.builder(
		).authentication(
			"test@liferay.com", "test"
		).locale(
			LocaleUtil.getDefault()
		).parameters(
			"restrictFields",
			StringBundler.concat(
				"actions,availableLanguages,creator,customFields,dateCreated,",
				"dateModified,displayPageTemplateKey,",
				"displayPageTemplateSettings,",
				"openGraphSettingsMapping,seoSettingsMapping,markedAsDefault,",
				"pageDefinition,settings,siteId,uuid")
		).build();

		page = displayPageTemplateResource.getSiteDisplayPageTemplatesPage(
			testGroup.getGroupId(), Pagination.of(1, 10), null);

		Assert.assertEquals(1, page.getTotalCount());
		assertEquals(
			new DisplayPageTemplate() {
				{
					title = displayPageTemplate.getTitle();
				}
			},
			page.fetchFirstItem());
		assertValid(page);
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

	private String _originalName;

	@Inject
	private Portal _portal;

}