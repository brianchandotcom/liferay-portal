/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.site.client.dto.v1_0.SiteTemplate;
import com.liferay.headless.admin.site.client.pagination.Page;
import com.liferay.headless.admin.site.client.pagination.Pagination;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Balázs Sáfrány-Kovalik
 */
@RunWith(Arquillian.class)
public class SiteTemplateResourceTest extends BaseSiteTemplateResourceTestCase {

	@Override
	@Test
	public void testGetSiteTemplatesPage() throws Exception {
		super.testGetSiteTemplatesPage();

		_testGetSiteTemplatesPageWithExcludedSiteExternalReferenceCodes();
	}

	@Override
	protected SiteTemplate randomSiteTemplate() throws Exception {
		return new SiteTemplate() {
			{
				active = RandomTestUtil.randomBoolean();
				defaultLanguageId = "en-US";
				description = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				description_i18n = HashMapBuilder.put(
					"en-US", description
				).build();
				id = RandomTestUtil.randomLong();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				name_i18n = HashMapBuilder.put(
					"en-US", name
				).build();
				pagesUpdateable = RandomTestUtil.randomBoolean();
				siteExternalReferenceCode = RandomTestUtil.randomString();
			}
		};
	}

	@Override
	protected SiteTemplate testGetSiteTemplatesPage_addSiteTemplate(
			SiteTemplate siteTemplate)
		throws Exception {

		Map<Locale, String> names = new HashMap<>();

		Map<String, String> nameMap = siteTemplate.getName_i18n();

		for (Map.Entry<String, String> entry : nameMap.entrySet()) {
			names.put(
				LocaleUtil.fromLanguageId(entry.getKey()), entry.getValue());
		}

		Map<Locale, String> descriptions = new HashMap<>();

		Map<String, String> descriptionMap = siteTemplate.getDescription_i18n();

		for (Map.Entry<String, String> entry : descriptionMap.entrySet()) {
			descriptions.put(
				LocaleUtil.fromLanguageId(entry.getKey()), entry.getValue());
		}

		_layoutSetPrototypeLocalService.addLayoutSetPrototype(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(), names,
			descriptions, siteTemplate.getActive(),
			siteTemplate.getPagesUpdateable(), new ServiceContext());

		return siteTemplate;
	}

	private void _testGetSiteTemplatesPageWithExcludedSiteExternalReferenceCodes()
		throws Exception {

		SiteTemplate siteTemplate1 = randomSiteTemplate();

		siteTemplate1.setActive(true);

		testGetSiteTemplatesPage_addSiteTemplate(siteTemplate1);

		SiteTemplate siteTemplate2 = randomSiteTemplate();

		siteTemplate2.setActive(true);

		testGetSiteTemplatesPage_addSiteTemplate(siteTemplate2);

		Page<SiteTemplate> siteTemplatesPage =
			siteTemplateResource.getSiteTemplatesPage(
				true, null, Pagination.of(1, 100));

		String excludedSiteExternalReferenceCode = null;
		String includedSiteExternalReferenceCode = null;

		for (SiteTemplate siteTemplate : siteTemplatesPage.getItems()) {
			if (Objects.equals(
					siteTemplate1.getName(), siteTemplate.getName())) {

				excludedSiteExternalReferenceCode =
					siteTemplate.getSiteExternalReferenceCode();
			}
			else if (Objects.equals(
						siteTemplate2.getName(), siteTemplate.getName())) {

				includedSiteExternalReferenceCode =
					siteTemplate.getSiteExternalReferenceCode();
			}
		}

		Assert.assertNotNull(excludedSiteExternalReferenceCode);
		Assert.assertNotNull(includedSiteExternalReferenceCode);

		siteTemplatesPage = siteTemplateResource.getSiteTemplatesPage(
			true, new String[] {excludedSiteExternalReferenceCode},
			Pagination.of(1, 100));

		boolean excludedSiteTemplateFound = false;
		boolean includedSiteTemplateFound = false;

		for (SiteTemplate siteTemplate : siteTemplatesPage.getItems()) {
			String siteExternalReferenceCode =
				siteTemplate.getSiteExternalReferenceCode();

			if (excludedSiteExternalReferenceCode.equals(
					siteExternalReferenceCode)) {

				excludedSiteTemplateFound = true;
			}
			else if (includedSiteExternalReferenceCode.equals(
						siteExternalReferenceCode)) {

				includedSiteTemplateFound = true;
			}
		}

		Assert.assertFalse(excludedSiteTemplateFound);
		Assert.assertTrue(includedSiteTemplateFound);
	}

	@Inject
	private LayoutSetPrototypeLocalService _layoutSetPrototypeLocalService;

}