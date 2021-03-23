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
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;

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
			_getLayoutPageTemplateEntry();

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
			_getLayoutPageTemplateEntry();

		displayPageTemplate.setDateCreated(
			layoutPageTemplateEntry.getCreateDate());
		displayPageTemplate.setDateModified(
			layoutPageTemplateEntry.getModifiedDate());
		displayPageTemplate.setDisplayPageTemplateKey(
			layoutPageTemplateEntry.getLayoutPageTemplateEntryKey());
		displayPageTemplate.setMarkedAsDefault(
			layoutPageTemplateEntry.isDefaultTemplate());

		displayPageTemplate.setSiteId(testGroup.getGroupId());
		displayPageTemplate.setTitle(layoutPageTemplateEntry.getName());

		return displayPageTemplate;
	}

	private LayoutPageTemplateEntry _getLayoutPageTemplateEntry()
		throws Exception {

		return _layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
			TestPropsValues.getUserId(), testGroup.getGroupId(), 0,
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