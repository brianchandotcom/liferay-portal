/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.site.client.dto.v1_0.ContentPageTemplate;
import com.liferay.headless.admin.site.client.dto.v1_0.PageTemplate;
import com.liferay.headless.admin.site.client.dto.v1_0.PageTemplateSet;
import com.liferay.headless.admin.site.client.dto.v1_0.WidgetPageTemplate;
import com.liferay.layout.page.template.constants.LayoutPageTemplateCollectionTypeConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;

import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@FeatureFlags("LPD-35443")
@RunWith(Arquillian.class)
public class PageTemplateResourceTest extends BasePageTemplateResourceTestCase {

	@Ignore
	@Override
	@Test
	public void testDeleteSiteSiteByExternalReferenceCodePageTemplate()
		throws Exception {

		super.testDeleteSiteSiteByExternalReferenceCodePageTemplate();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodePageTemplate()
		throws Exception {

		super.testGetSiteSiteByExternalReferenceCodePageTemplate();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodePageTemplatePermissionsPage()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodePageTemplatePermissionsPage();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodePageTemplateSetPageTemplatesPage()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodePageTemplateSetPageTemplatesPage();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodePageTemplatesPageWithPagination()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodePageTemplatesPageWithPagination();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodePageTemplatesPageWithSortDateTime()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodePageTemplatesPageWithSortDateTime();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodePageTemplatesPageWithSortDouble()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodePageTemplatesPageWithSortDouble();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodePageTemplatesPageWithSortInteger()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodePageTemplatesPageWithSortInteger();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteExternalReferenceCodePageTemplatePermissionsPage()
		throws Exception {

		super.testGetSiteSiteExternalReferenceCodePageTemplatePermissionsPage();
	}

	@Ignore
	@Override
	@Test
	public void testPatchSiteSiteByExternalReferenceCodePageTemplate()
		throws Exception {

		super.testPatchSiteSiteByExternalReferenceCodePageTemplate();
	}

	@Override
	@Test
	public void testPostSiteSiteByExternalReferenceCodePageTemplate()
		throws Exception {

		_testPostSiteSiteByExternalReferenceCodePageTemplate();
	}

	@Ignore
	@Override
	@Test
	public void testPostSiteSiteByExternalReferenceCodePageTemplatePageSpecification()
		throws Exception {

		super.
			testPostSiteSiteByExternalReferenceCodePageTemplatePageSpecification();
	}

	@Ignore
	@Override
	@Test
	public void testPostSiteSiteByExternalReferenceCodePageTemplateSetPageTemplate()
		throws Exception {

		super.
			testPostSiteSiteByExternalReferenceCodePageTemplateSetPageTemplate();
	}

	@Ignore
	@Override
	@Test
	public void testPutSiteSiteByExternalReferenceCodePageTemplate()
		throws Exception {

		super.testPutSiteSiteByExternalReferenceCodePageTemplate();
	}

	@Ignore
	@Override
	@Test
	public void testPutSiteSiteByExternalReferenceCodePageTemplatePermissionsPage()
		throws Exception {

		super.
			testPutSiteSiteByExternalReferenceCodePageTemplatePermissionsPage();
	}

	@Ignore
	@Override
	@Test
	public void testPutSiteSiteExternalReferenceCodePageTemplatePermissionsPage()
		throws Exception {

		super.testPutSiteSiteExternalReferenceCodePageTemplatePermissionsPage();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"description_i18n", "externalReferenceCode", "name", "name_i18n",
			"pageTemplateSet"
		};
	}

	@Override
	protected PageTemplate randomIrrelevantPageTemplate() throws Exception {
		return _getPageRandomTemplate(irrelevantGroup);
	}

	@Override
	protected PageTemplate randomPageTemplate() throws Exception {
		return _getPageRandomTemplate(testGroup);
	}

	@Override
	protected PageTemplate
			testGetSiteSiteByExternalReferenceCodePageTemplatesPage_addPageTemplate(
				String siteExternalReferenceCode, PageTemplate pageTemplate)
		throws Exception {

		return pageTemplateResource.
			postSiteSiteByExternalReferenceCodePageTemplate(
				siteExternalReferenceCode, pageTemplate);
	}

	@Override
	protected String
			testGetSiteSiteByExternalReferenceCodePageTemplatesPage_getIrrelevantSiteExternalReferenceCode()
		throws Exception {

		return irrelevantGroup.getExternalReferenceCode();
	}

	@Override
	protected String
			testGetSiteSiteByExternalReferenceCodePageTemplatesPage_getSiteExternalReferenceCode()
		throws Exception {

		return testGroup.getExternalReferenceCode();
	}

	@Override
	protected PageTemplate
			testPostSiteSiteByExternalReferenceCodePageTemplate_addPageTemplate(
				PageTemplate pageTemplate)
		throws Exception {

		return testGetSiteSiteByExternalReferenceCodePageTemplatesPage_addPageTemplate(
			testGroup.getExternalReferenceCode(), pageTemplate);
	}

	private ContentPageTemplate _getContentPageTemplate(Group group)
		throws Exception {

		return new ContentPageTemplate() {
			{
				creatorExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				datePublished = RandomTestUtil.nextDate();
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				key = StringUtil.toLowerCase(RandomTestUtil.randomString());
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				pageTemplateSet = _getPageTemplateSet(group);
				type = Type.CONTENT_PAGE_TEMPLATE;
				uuid = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	private PageTemplate _getPageRandomTemplate(Group group) throws Exception {
		List<UnsafeSupplier<PageTemplate, Exception>> unsafeSuppliers =
			Arrays.asList(
				() -> _getContentPageTemplate(group),
				() -> _getWidgetPageTemplate(group));

		UnsafeSupplier<PageTemplate, Exception> unsafeSupplier =
			unsafeSuppliers.get(
				RandomTestUtil.randomInt(0, unsafeSuppliers.size() - 1));

		return unsafeSupplier.get();
	}

	private PageTemplateSet _getPageTemplateSet(Group group) throws Exception {
		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionLocalService.
				addLayoutPageTemplateCollection(
					null, TestPropsValues.getUserId(), group.getGroupId(),
					LayoutPageTemplateConstants.
						PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
					RandomTestUtil.randomString(),
					RandomTestUtil.randomString(),
					LayoutPageTemplateCollectionTypeConstants.DISPLAY_PAGE,
					ServiceContextTestUtil.getServiceContext(
						group, TestPropsValues.getUserId()));

		return new PageTemplateSet() {
			{
				setDateCreated(layoutPageTemplateCollection::getCreateDate);
				setDateModified(layoutPageTemplateCollection::getModifiedDate);
				setDescription(layoutPageTemplateCollection::getDescription);
				setExternalReferenceCode(
					layoutPageTemplateCollection::getExternalReferenceCode);
				setName(layoutPageTemplateCollection::getName);
			}
		};
	}

	private WidgetPageTemplate _getWidgetPageTemplate(Group group)
		throws Exception {

		return new WidgetPageTemplate() {
			{
				active = RandomTestUtil.randomBoolean();
				creatorExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				datePublished = RandomTestUtil.nextDate();
				description_i18n = HashMapBuilder.put(
					LocaleUtil.toBCP47LanguageId(LocaleUtil.getDefault()),
					RandomTestUtil.randomString()
				).build();
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				hiddenFromNavigation = RandomTestUtil.randomBoolean();
				key = StringUtil.toLowerCase(RandomTestUtil.randomString());

				name = StringUtil.toLowerCase(RandomTestUtil.randomString());

				name_i18n = HashMapBuilder.put(
					LocaleUtil.toBCP47LanguageId(LocaleUtil.getDefault()), name
				).build();

				pageTemplateSet = _getPageTemplateSet(group);
				type = PageTemplate.Type.WIDGET_PAGE_TEMPLATE;
				uuid = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	private void _testPostSiteSiteByExternalReferenceCodePageTemplate()
		throws Exception {

		PageTemplate randomPageTemplate = randomPageTemplate();

		PageTemplate postPageTemplate =
			testPostSiteSiteByExternalReferenceCodePageTemplate_addPageTemplate(
				randomPageTemplate);

		assertEquals(randomPageTemplate, postPageTemplate);
		assertValid(postPageTemplate);

		ContentPageTemplate contentPageTemplate = _getContentPageTemplate(
			testGroup);

		assertEquals(
			contentPageTemplate,
			pageTemplateResource.
				postSiteSiteByExternalReferenceCodePageTemplate(
					testGroup.getExternalReferenceCode(), contentPageTemplate));

		WidgetPageTemplate widgetPageTemplate = _getWidgetPageTemplate(
			testGroup);

		assertEquals(
			widgetPageTemplate,
			pageTemplateResource.
				postSiteSiteByExternalReferenceCodePageTemplate(
					testGroup.getExternalReferenceCode(), widgetPageTemplate));
	}

	@Inject
	private LayoutPageTemplateCollectionLocalService
		_layoutPageTemplateCollectionLocalService;

}