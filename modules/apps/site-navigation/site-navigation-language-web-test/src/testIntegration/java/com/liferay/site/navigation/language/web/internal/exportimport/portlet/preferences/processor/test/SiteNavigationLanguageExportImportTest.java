/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.language.web.internal.exportimport.portlet.preferences.processor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.exportimport.test.util.lar.BasePortletExportImportTestCase;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.servlet.taglib.ui.LanguageEntry;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portlet.display.template.PortletDisplayTemplate;
import com.liferay.site.navigation.language.constants.SiteNavigationLanguagePortletKeys;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class SiteNavigationLanguageExportImportTest
	extends BasePortletExportImportTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Override
	public String getPortletId() throws Exception {
		return PortletIdCodec.encode(
			SiteNavigationLanguagePortletKeys.SITE_NAVIGATION_LANGUAGE,
			RandomTestUtil.randomString());
	}

	@Override
	@Test
	public void testExportImportAssetLinks() throws Exception {
	}

	@Override
	@Test
	@TestInfo("LPD-33733")
	public void testExportImportDisplayStyleFromDifferentGroup()
		throws Exception {

		super.testExportImportDisplayStyleFromDifferentGroup();

		long classNameId = _portal.getClassNameId(
			LanguageEntry.class.getName());

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			group.getGroupId(), classNameId, 0,
			_portal.getClassNameId(PortletDisplayTemplate.class.getName()),
			TemplateConstants.LANG_TYPE_FTL, RandomTestUtil.randomString(),
			_portal.getSiteDefaultLocale(group));

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			HashMapBuilder.put(
				"displayStyle",
				new String[] {
					PortletDisplayTemplate.DISPLAY_STYLE_PREFIX +
						ddmTemplate.getTemplateKey()
				}
			).build());

		Assert.assertNotNull(
			_ddmTemplateLocalService.getTemplate(
				importedGroup.getGroupId(), classNameId,
				ddmTemplate.getTemplateKey()));

		Assert.assertEquals(
			PortletDisplayTemplate.DISPLAY_STYLE_PREFIX +
				ddmTemplate.getTemplateKey(),
			portletPreferences.getValue("displayStyle", null));
		Assert.assertNull(
			portletPreferences.getValue(
				"displayStyleGroupExternalReferenceCode", null));
	}

	@Override
	@Test
	public void testExportImportDisplayStyleFromGlobalScope() throws Exception {
		super.testExportImportDisplayStyleFromGlobalScope();

		Group companyGroup = _groupLocalService.getCompanyGroup(
			TestPropsValues.getCompanyId());

		long classNameId = _portal.getClassNameId(
			LanguageEntry.class.getName());

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			companyGroup.getGroupId(), classNameId, 0,
			_portal.getClassNameId(PortletDisplayTemplate.class.getName()),
			TemplateConstants.LANG_TYPE_FTL, RandomTestUtil.randomString(),
			_portal.getSiteDefaultLocale(companyGroup));

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			HashMapBuilder.put(
				"displayStyle",
				new String[] {
					PortletDisplayTemplate.DISPLAY_STYLE_PREFIX +
						ddmTemplate.getTemplateKey()
				}
			).put(
				"displayStyleGroupExternalReferenceCode",
				new String[] {companyGroup.getExternalReferenceCode()}
			).build());

		Assert.assertNull(
			_ddmTemplateLocalService.fetchTemplate(
				importedGroup.getGroupId(), classNameId,
				ddmTemplate.getTemplateKey()));

		Assert.assertEquals(
			PortletDisplayTemplate.DISPLAY_STYLE_PREFIX +
				ddmTemplate.getTemplateKey(),
			portletPreferences.getValue("displayStyle", null));
		Assert.assertEquals(
			companyGroup.getExternalReferenceCode(),
			portletPreferences.getValue(
				"displayStyleGroupExternalReferenceCode", null));
	}

	@Override
	protected void testExportImportDisplayStyle(
			long displayStyleGroupId, String scopeType)
		throws Exception {

		Group displayStyleGroup = _groupLocalService.getGroup(
			displayStyleGroupId);

		long classNameId = _portal.getClassNameId(
			LanguageEntry.class.getName());

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			displayStyleGroup.getGroupId(), classNameId, 0,
			_portal.getClassNameId(PortletDisplayTemplate.class.getName()),
			TemplateConstants.LANG_TYPE_FTL, RandomTestUtil.randomString(),
			_portal.getSiteDefaultLocale(displayStyleGroup));

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			HashMapBuilder.put(
				"displayStyle",
				new String[] {
					PortletDisplayTemplate.DISPLAY_STYLE_PREFIX +
						ddmTemplate.getTemplateKey()
				}
			).put(
				"displayStyleGroupExternalReferenceCode",
				() -> {
					if (displayStyleGroup.getGroupId() == layout.getGroupId()) {
						return null;
					}

					return new String[] {
						displayStyleGroup.getExternalReferenceCode()
					};
				}
			).build());

		Assert.assertEquals(
			PortletDisplayTemplate.DISPLAY_STYLE_PREFIX +
				ddmTemplate.getTemplateKey(),
			portletPreferences.getValue("displayStyle", null));

		DDMTemplate importedDDMTemplate =
			_ddmTemplateLocalService.fetchTemplate(
				layout.getGroupId(), classNameId, ddmTemplate.getTemplateKey());

		String importedDisplayStyleGroupExternalReferenceCode =
			portletPreferences.getValue(
				"displayStyleGroupExternalReferenceCode", null);

		if (displayStyleGroup.getGroupId() != layout.getGroupId()) {
			Assert.assertNull(importedDDMTemplate);

			Assert.assertEquals(
				displayStyleGroup.getExternalReferenceCode(),
				importedDisplayStyleGroupExternalReferenceCode);
		}
		else {
			Assert.assertNotNull(importedDDMTemplate);
			Assert.assertNull(importedDisplayStyleGroupExternalReferenceCode);
		}
	}

	@Inject
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private Portal _portal;

}