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

package com.liferay.headless.delivery.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.delivery.client.dto.v1_0.ContentPage;
import com.liferay.layout.page.template.importer.LayoutPageTemplatesImporter;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureRelLocalService;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;

import java.io.InputStream;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class ContentPageResourceTest extends BaseContentPageResourceTestCase {

	@Override
	@Test
	public void testGetSiteContentPageRenderedPage() throws Exception {
		Layout layout = _addLayout(testGroup.getGroupId(), true);

		String friendlyURL = layout.getFriendlyURL();

		String siteContentPageRenderedPage =
			contentPageResource.getSiteContentPageRenderedPage(
				testGroup.getGroupId(), friendlyURL.substring(1));

		Assert.assertNotNull(siteContentPageRenderedPage);
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteContentPagesPageWithSortString() {
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"title"};
	}

	@Override
	protected ContentPage testGetSiteContentPage_addContentPage()
		throws Exception {

		return _addContentPage();
	}

	@Override
	protected ContentPage testGetSiteContentPagesPage_addContentPage(
			Long siteId, ContentPage contentPage)
		throws Exception {

		Layout layout = _addLayout(siteId, false);

		contentPage.setDateCreated(layout.getCreateDate());
		contentPage.setDateModified(layout.getModifiedDate());
		contentPage.setDatePublished(layout.getPublishDate());

		String friendlyURL = layout.getFriendlyURL();

		contentPage.setFriendlyUrlPath(friendlyURL.substring(1));

		contentPage.setId(layout.getPlid());
		contentPage.setSiteId(siteId);
		contentPage.setTitle(layout.getName(LocaleUtil.getDefault()));
		contentPage.setUuid(layout.getUuid());

		return contentPage;
	}

	@Override
	protected ContentPage testGraphQLContentPage_addContentPage()
		throws Exception {

		return _addContentPage();
	}

	private ContentPage _addContentPage() throws Exception {
		Layout layout = _addLayout(testGroup.getGroupId(), false);

		String friendlyURL = layout.getFriendlyURL();

		return new ContentPage() {
			{
				dateCreated = layout.getCreateDate();
				dateModified = layout.getModifiedDate();
				datePublished = layout.getLastPublishDate();
				friendlyUrlPath = friendlyURL.substring(1);
				id = layout.getPlid();
				siteId = layout.getGroupId();
				title = layout.getName(LocaleUtil.getDefault());
				uuid = layout.getUuid();
			}
		};
	}

	private Layout _addLayout(long siteId, boolean importPageDefinition)
		throws Exception {

		Layout layout = LayoutLocalServiceUtil.addLayout(
			TestPropsValues.getUserId(), siteId, false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			StringPool.BLANK, LayoutConstants.TYPE_CONTENT, false,
			StringPool.BLANK, ServiceContextTestUtil.getServiceContext(siteId));

		if (importPageDefinition) {
			String name = PrincipalThreadLocal.getName();

			try {
				PrincipalThreadLocal.setName(TestPropsValues.getUserId());

				ServiceContext serviceContext =
					ServiceContextTestUtil.getServiceContext(
						testGroup.getGroupId());

				ServiceContextThreadLocal.pushServiceContext(serviceContext);

				String contentPageDefinitionJSON = _read(
					"test-content-page-definition.json");

				LayoutPageTemplateStructure layoutPageTemplateStructure =
					_layoutPageTemplateStructureLocalService.
						fetchLayoutPageTemplateStructure(
							testGroup.getGroupId(), layout.getPlid(), true);

				LayoutStructure layoutStructure = LayoutStructure.of(
					layoutPageTemplateStructure.getData(0));

				layoutStructure.addRootLayoutStructureItem();

				_layoutPageTemplatesImporter.importPageElement(
					layout, layoutStructure, layoutStructure.getMainItemId(),
					contentPageDefinitionJSON, 0);
			}
			finally {
				PrincipalThreadLocal.setName(name);

				ServiceContextThreadLocal.popServiceContext();
			}
		}

		return layout;
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	@Inject
	private LayoutPageTemplatesImporter _layoutPageTemplatesImporter;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject
	private LayoutPageTemplateStructureRelLocalService
		_layoutPageTemplateStructureRelLocalService;

	@Inject
	private Portal _portal;

}