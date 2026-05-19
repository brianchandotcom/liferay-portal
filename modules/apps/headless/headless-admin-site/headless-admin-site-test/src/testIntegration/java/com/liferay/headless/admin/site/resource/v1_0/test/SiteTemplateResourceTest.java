/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.site.client.dto.v1_0.SiteTemplate;
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

import org.junit.runner.RunWith;

/**
 * @author Balázs Sáfrány-Kovalik
 */
@RunWith(Arquillian.class)
public class SiteTemplateResourceTest extends BaseSiteTemplateResourceTestCase {

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

	@Inject
	private LayoutSetPrototypeLocalService _layoutSetPrototypeLocalService;

}