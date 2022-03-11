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

package com.liferay.exportimport.internal.content.processor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.exportimport.test.util.TestReaderWriter;
import com.liferay.exportimport.test.util.TestUserIdStrategy;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.test.util.LayoutFriendlyURLRandomizerBumper;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.randomizerbumpers.NumericStringRandomizerBumper;
import com.liferay.portal.kernel.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.util.PropsValues;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TreeMap;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Michael Bowerman
 */
@RunWith(Arquillian.class)
public class LayoutReferencesExportImportContentProcessorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testExportDefaultGroupCompanyHostImportDefaultGroupCompanyHost()
		throws Exception {

		Group exportGroup = GroupTestUtil.addGroup();

		GroupTestUtil.addLayoutSetVirtualHost(exportGroup, false);

		Group importGroup = GroupTestUtil.addGroup();

		GroupTestUtil.addLayoutSetVirtualHost(importGroup, false);

		Layout exportLayout = LayoutTestUtil.addTypePortletLayout(exportGroup);

		Assert.assertEquals(
			_getCompanyHostPortalURL(importGroup) +
				exportLayout.getFriendlyURL(),
			_exportAndImportLayoutURL(
				_getCompanyHostPortalURL(exportGroup) +
					exportLayout.getFriendlyURL(),
				exportGroup, importGroup, true, true));
	}

	@Test
	public void testExportDefaultGroupCompanyHostImportNoVirtualHost()
		throws Exception {

		Group exportGroup = GroupTestUtil.addGroup();

		Layout exportLayout = LayoutTestUtil.addTypePortletLayout(exportGroup);

		Group importGroup = GroupTestUtil.addGroup();

		Assert.assertEquals(
			_getCompanyHostPortalURL(importGroup) +
				PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING +
					importGroup.getFriendlyURL() +
						exportLayout.getFriendlyURL(),
			_exportAndImportLayoutURL(
				_getCompanyHostPortalURL(exportGroup) +
					exportLayout.getFriendlyURL(),
				exportGroup, importGroup, true, false));
	}

	@Test
	public void testExportDefaultGroupCompanyHostImportPublicPagesVirtualHost()
		throws Exception {

		Group exportGroup = GroupTestUtil.addGroup();

		Layout exportLayout = LayoutTestUtil.addTypePortletLayout(exportGroup);

		Group importGroup = GroupTestUtil.addGroup();

		GroupTestUtil.addLayoutSetVirtualHost(importGroup, false);

		Assert.assertEquals(
			_getGroupVirtualHostPortalURL(importGroup, false) +
				exportLayout.getFriendlyURL(),
			_exportAndImportLayoutURL(
				_getCompanyHostPortalURL(exportGroup) +
					exportLayout.getFriendlyURL(),
				exportGroup, importGroup, true, false));
	}

	@Test
	public void testExportDefaultGroupPublicPagesVirtualHostImportDefaultGroupNoVirtualHost()
		throws Exception {

		Group exportGroup = GroupTestUtil.addGroup();

		GroupTestUtil.addLayoutSetVirtualHost(exportGroup, false);

		Layout exportLayout = LayoutTestUtil.addTypePortletLayout(exportGroup);

		Group importGroup = GroupTestUtil.addGroup();

		Assert.assertEquals(
			_getCompanyHostPortalURL(importGroup) +
				exportLayout.getFriendlyURL(),
			_exportAndImportLayoutURL(
				_getGroupVirtualHostPortalURL(exportGroup, false) +
					exportLayout.getFriendlyURL(),
				exportGroup, importGroup, true, true));
	}

	@Test
	public void testExportDefaultGroupPublicPagesVirtualHostImportDefaultGroupPublicPagesVirtualHost()
		throws Exception {

		Group exportGroup = GroupTestUtil.addGroup();

		GroupTestUtil.addLayoutSetVirtualHost(exportGroup, false);

		Layout exportLayout = LayoutTestUtil.addTypePortletLayout(exportGroup);

		Group importGroup = GroupTestUtil.addGroup();

		GroupTestUtil.addLayoutSetVirtualHost(importGroup, false);

		Assert.assertEquals(
			_getGroupVirtualHostPortalURL(importGroup, false) +
				exportLayout.getFriendlyURL(),
			_exportAndImportLayoutURL(
				_getGroupVirtualHostPortalURL(exportGroup, false) +
					exportLayout.getFriendlyURL(),
				exportGroup, importGroup, true, true));
	}

	@Test
	public void testExportDefaultGroupPublicPagesVirtualHostImportNoVirtualHost()
		throws Exception {

		Group exportGroup = GroupTestUtil.addGroup();

		GroupTestUtil.addLayoutSetVirtualHost(exportGroup, false);

		Layout exportLayout = LayoutTestUtil.addTypePortletLayout(exportGroup);

		Group importGroup = GroupTestUtil.addGroup();

		Assert.assertEquals(
			_getCompanyHostPortalURL(importGroup) +
				PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING +
					importGroup.getFriendlyURL() +
						exportLayout.getFriendlyURL(),
			_exportAndImportLayoutURL(
				_getGroupVirtualHostPortalURL(exportGroup, false) +
					exportLayout.getFriendlyURL(),
				exportGroup, importGroup, true, false));
	}

	@Test
	public void testExportDefaultGroupPublicPagesVirtualHostImportPublicPagesVirtualHost()
		throws Exception {

		Group exportGroup = GroupTestUtil.addGroup();

		GroupTestUtil.addLayoutSetVirtualHost(exportGroup, false);

		Layout exportLayout = LayoutTestUtil.addTypePortletLayout(exportGroup);

		Group importGroup = GroupTestUtil.addGroup();

		GroupTestUtil.addLayoutSetVirtualHost(importGroup, false);

		Assert.assertEquals(
			_getGroupVirtualHostPortalURL(importGroup, false) +
				exportLayout.getFriendlyURL(),
			_exportAndImportLayoutURL(
				_getGroupVirtualHostPortalURL(exportGroup, false) +
					exportLayout.getFriendlyURL(),
				exportGroup, importGroup, true, false));
	}

	@Test
	public void testExportDefaultGroupRelativeURLImportDefaultGroup()
		throws Exception {

		Group exportGroup = GroupTestUtil.addGroup();

		Layout exportLayout = LayoutTestUtil.addTypePortletLayout(exportGroup);

		Group importGroup = GroupTestUtil.addGroup();

		Assert.assertEquals(
			exportLayout.getFriendlyURL(),
			_exportAndImportLayoutURL(
				exportLayout.getFriendlyURL(), exportGroup, importGroup, true,
				true));
	}

	@Test
	public void testExportDefaultGroupRelativeURLImportNoVirtualHost()
		throws Exception {

		Group exportGroup = GroupTestUtil.addGroup();

		Layout exportLayout = LayoutTestUtil.addTypePortletLayout(exportGroup);

		Group importGroup = GroupTestUtil.addGroup();

		Assert.assertEquals(
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING +
				importGroup.getFriendlyURL() + exportLayout.getFriendlyURL(),
			_exportAndImportLayoutURL(
				exportLayout.getFriendlyURL(), exportGroup, importGroup, true,
				false));
	}

	@Test
	public void testExportDefaultGroupRelativeURLImportPublicPagesVirtualHost()
		throws Exception {

		Group exportGroup = GroupTestUtil.addGroup();

		Layout exportLayout = LayoutTestUtil.addTypePortletLayout(exportGroup);

		Group importGroup = GroupTestUtil.addGroup();

		GroupTestUtil.addLayoutSetVirtualHost(importGroup, false);

		Assert.assertEquals(
			exportLayout.getFriendlyURL(),
			_exportAndImportLayoutURL(
				exportLayout.getFriendlyURL(), exportGroup, importGroup, true,
				false));
	}

	@Test
	public void testExportDefaultGroupRelativeURLWithLocaleImportDefaultGroup()
		throws Exception {

		Group exportGroup = GroupTestUtil.addGroup();

		Layout exportLayout = LayoutTestUtil.addTypePortletLayout(exportGroup);

		Group importGroup = GroupTestUtil.addGroup();

		Assert.assertEquals(
			StringPool.SLASH + LocaleUtil.getDefault() +
				exportLayout.getFriendlyURL(),
			_exportAndImportLayoutURL(
				StringPool.SLASH + LocaleUtil.getDefault() +
					exportLayout.getFriendlyURL(),
				exportGroup, importGroup, true, true));
	}

	@Test
	public void testExportPrivatePagesVirtualHostURLImportDefaultGroup()
		throws Exception {

		Group exportGroup = GroupTestUtil.addGroup();

		GroupTestUtil.addLayoutSetVirtualHost(exportGroup, true);

		Layout exportLayout = LayoutTestUtil.addTypePortletLayout(
			exportGroup, true);

		Group importGroup = GroupTestUtil.addGroup();

		Assert.assertEquals(
			_getCompanyHostPortalURL(importGroup) +
				PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING +
					importGroup.getFriendlyURL() +
						exportLayout.getFriendlyURL(),
			_exportAndImportLayoutURL(
				_getGroupVirtualHostPortalURL(exportGroup, true) +
					exportLayout.getFriendlyURL(),
				exportGroup, importGroup, false, true));
	}

	@Test
	public void testExportPublicPagesVirtualHostImportDefaultGroupNoVirtualHost()
		throws Exception {

		Group exportGroup = GroupTestUtil.addGroup();

		GroupTestUtil.addLayoutSetVirtualHost(exportGroup, false);

		Layout exportLayout = LayoutTestUtil.addTypePortletLayout(exportGroup);

		Group importGroup = GroupTestUtil.addGroup();

		Assert.assertEquals(
			_getCompanyHostPortalURL(importGroup) +
				exportLayout.getFriendlyURL(),
			_exportAndImportLayoutURL(
				_getGroupVirtualHostPortalURL(exportGroup, false) +
					exportLayout.getFriendlyURL(),
				exportGroup, importGroup, false, true));
	}

	@Test
	public void testExportPublicPagesVirtualHostImportDefaultGroupPublicPagesVirtualHost()
		throws Exception {

		Group exportGroup = GroupTestUtil.addGroup();

		GroupTestUtil.addLayoutSetVirtualHost(exportGroup, false);

		Layout exportLayout = LayoutTestUtil.addTypePortletLayout(exportGroup);

		Group importGroup = GroupTestUtil.addGroup();

		GroupTestUtil.addLayoutSetVirtualHost(importGroup, false);

		Assert.assertEquals(
			_getGroupVirtualHostPortalURL(importGroup, false) +
				exportLayout.getFriendlyURL(),
			_exportAndImportLayoutURL(
				_getGroupVirtualHostPortalURL(exportGroup, false) +
					exportLayout.getFriendlyURL(),
				exportGroup, importGroup, false, true));
	}

	@Test
	public void testExportRelativeURLImportDefaultGroupNoVirtualHost()
		throws Exception {

		Group exportGroup = GroupTestUtil.addGroup();

		GroupTestUtil.addLayoutSetVirtualHost(exportGroup, false);

		Layout exportLayout = LayoutTestUtil.addTypePortletLayout(exportGroup);

		Assert.assertEquals(
			exportLayout.getFriendlyURL(),
			_exportAndImportLayoutURL(
				exportLayout.getFriendlyURL(), exportGroup,
				GroupTestUtil.addGroup(), false, true));
	}

	@Test
	public void testExternalURLNotModified() throws Exception {
		String exportedURL = RandomTestUtil.randomString(
			LayoutFriendlyURLRandomizerBumper.INSTANCE);

		Assert.assertEquals(
			exportedURL,
			_exportAndImportLayoutURL(
				exportedURL, GroupTestUtil.addGroup(),
				GroupTestUtil.addGroup()));
	}

	@Test
	public void testPrivateVirtualHostLayoutFriendlyURLFormatModifiedWhenNecessary()
		throws Exception {

		Group exportGroup = GroupTestUtil.addGroup();

		GroupTestUtil.addLayoutSetVirtualHost(exportGroup, true);

		Layout exportLayout = LayoutTestUtil.addTypePortletLayout(
			exportGroup, true);

		Group importGroup = GroupTestUtil.addGroup();

		Assert.assertEquals(
			_getCompanyHostPortalURL(importGroup) +
				PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING +
					importGroup.getFriendlyURL() +
						exportLayout.getFriendlyURL(),
			_exportAndImportLayoutURL(
				_getGroupVirtualHostPortalURL(exportGroup, true) +
					exportLayout.getFriendlyURL(),
				exportGroup, importGroup));
	}

	@Test
	public void testPrivateVirtualHostLayoutFriendlyURLFormatPreservedWhenPossible()
		throws Exception {

		Group exportGroup = GroupTestUtil.addGroup();

		GroupTestUtil.addLayoutSetVirtualHost(exportGroup, true);

		Layout exportLayout = LayoutTestUtil.addTypePortletLayout(
			exportGroup, true);

		Group importGroup = GroupTestUtil.addGroup();

		GroupTestUtil.addLayoutSetVirtualHost(importGroup, true);

		Assert.assertEquals(
			_getGroupVirtualHostPortalURL(importGroup, true) +
				exportLayout.getFriendlyURL(),
			_exportAndImportLayoutURL(
				_getGroupVirtualHostPortalURL(exportGroup, true) +
					exportLayout.getFriendlyURL(),
				exportGroup, importGroup));
	}

	@Test
	public void testPublicVirtualHostLayoutFriendlyURLFormatModifiedWhenNecessary()
		throws Exception {

		Group exportGroup = GroupTestUtil.addGroup();

		GroupTestUtil.addLayoutSetVirtualHost(exportGroup, false);

		Layout exportLayout = LayoutTestUtil.addTypePortletLayout(exportGroup);

		Group importGroup = GroupTestUtil.addGroup();

		Assert.assertEquals(
			_getCompanyHostPortalURL(importGroup) +
				PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING +
					importGroup.getFriendlyURL() +
						exportLayout.getFriendlyURL(),
			_exportAndImportLayoutURL(
				_getGroupVirtualHostPortalURL(exportGroup, false) +
					exportLayout.getFriendlyURL(),
				exportGroup, importGroup));
	}

	@Test
	public void testPublicVirtualHostLayoutFriendlyURLFormatPreservedWhenPossible()
		throws Exception {

		Group exportGroup = GroupTestUtil.addGroup();

		GroupTestUtil.addLayoutSetVirtualHost(exportGroup, false);

		Layout exportLayout = LayoutTestUtil.addTypePortletLayout(exportGroup);

		Group importGroup = GroupTestUtil.addGroup();

		GroupTestUtil.addLayoutSetVirtualHost(importGroup, false);

		Assert.assertEquals(
			_getGroupVirtualHostPortalURL(importGroup, false) +
				exportLayout.getFriendlyURL(),
			_exportAndImportLayoutURL(
				_getGroupVirtualHostPortalURL(exportGroup, false) +
					exportLayout.getFriendlyURL(),
				exportGroup, importGroup));
	}

	@Test
	public void testRelativeLayoutFriendlyURLFormatModifiedWhenNecessary()
		throws Exception {

		Group exportGroup = GroupTestUtil.addGroup();

		GroupTestUtil.addLayoutSetVirtualHost(exportGroup, false);

		Layout exportLayout = LayoutTestUtil.addTypePortletLayout(exportGroup);

		Group importGroup = GroupTestUtil.addGroup();

		Assert.assertEquals(
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING +
				importGroup.getFriendlyURL() + exportLayout.getFriendlyURL(),
			_exportAndImportLayoutURL(
				exportLayout.getFriendlyURL(), exportGroup, importGroup));
	}

	@Test
	public void testRelativeLayoutFriendlyURLFormatPreservedWhenPossible()
		throws Exception {

		Group exportGroup = GroupTestUtil.addGroup();

		GroupTestUtil.addLayoutSetVirtualHost(exportGroup, false);

		Layout exportLayout = LayoutTestUtil.addTypePortletLayout(exportGroup);

		Group importGroup = GroupTestUtil.addGroup();

		GroupTestUtil.addLayoutSetVirtualHost(importGroup, false);

		Assert.assertEquals(
			exportLayout.getFriendlyURL(),
			_exportAndImportLayoutURL(
				exportLayout.getFriendlyURL(), exportGroup, importGroup));
	}

	@Test
	public void testRelativePublicDefaultPageURLWithLocale() throws Exception {
		Group exportGroup = GroupTestUtil.addGroup();

		GroupTestUtil.addLayoutSetVirtualHost(exportGroup, false);

		Group importGroup = GroupTestUtil.addGroup();

		GroupTestUtil.addLayoutSetVirtualHost(importGroup, false);

		Locale siteDefaultLocale = _portal.getSiteDefaultLocale(exportGroup);

		String url = StringPool.SLASH + siteDefaultLocale.getLanguage();

		Assert.assertEquals(
			url, _exportAndImportLayoutURL(url, exportGroup, importGroup));
	}

	@Test
	public void testValidateContentRelativePublicDefaultPageURLWithLocale()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		GroupTestUtil.addLayoutSetVirtualHost(group, false);

		Locale siteDefaultLocale = _portal.getSiteDefaultLocale(group);

		_layoutReferencesExportImportContentProcessor.validateContentReferences(
			group.getGroupId(),
			StringBundler.concat(
				_CONTENT_PREFIX, StringPool.SLASH,
				siteDefaultLocale.getLanguage(), _CONTENT_POSTFIX));
	}

	private String _exportAndImportLayoutURL(
			String url, Group exportGroup, Group importGroup)
		throws Exception {

		return _exportAndImportLayoutURL(
			url, exportGroup, importGroup, false, false);
	}

	private String _exportAndImportLayoutURL(
			String url, Group exportGroup, Group importGroup,
			boolean exportFromDefaultGroup, boolean importToDefaultGroup)
		throws Exception {

		TestReaderWriter testReaderWriter = new TestReaderWriter();

		Group defaultGroup = _groupLocalService.fetchGroup(
			exportGroup.getCompanyId(),
			PropsValues.VIRTUAL_HOSTS_DEFAULT_SITE_NAME);

		if (defaultGroup != null) {
			defaultGroup = _groupLocalService.fetchGroup(
				defaultGroup.getGroupId());
		}

		String exportGroupKey = exportGroup.getGroupKey();

		String importGroupKey = importGroup.getGroupKey();

		try {
			if (exportFromDefaultGroup) {
				if (defaultGroup != null) {
					defaultGroup.setGroupKey(_GROUP_KEY);

					defaultGroup = _groupLocalService.updateGroup(defaultGroup);
				}

				exportGroup.setGroupKey(
					PropsValues.VIRTUAL_HOSTS_DEFAULT_SITE_NAME);

				exportGroup = _groupLocalService.updateGroup(exportGroup);
			}

			PortletDataContext exportPortletDataContext =
				PortletDataContextFactoryUtil.createExportPortletDataContext(
					exportGroup.getCompanyId(), exportGroup.getGroupId(),
					new HashMap<>(),
					new Date(System.currentTimeMillis() - Time.HOUR),
					new Date(), testReaderWriter);

			Document document = SAXReaderUtil.createDocument();

			Element manifestRootElement = document.addElement("root");

			manifestRootElement.addElement("header");

			testReaderWriter.addEntry("/manifest.xml", document.asXML());

			Element rootElement = SAXReaderUtil.createElement("root");

			exportPortletDataContext.setExportDataRootElement(rootElement);

			Element missingReferencesElement = rootElement.addElement(
				"missing-references");

			exportPortletDataContext.setMissingReferencesElement(
				missingReferencesElement);

			StagedModel referrerStagedModel = JournalTestUtil.addArticle(
				exportGroup.getGroupId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString());

			String contentToExport = _CONTENT_PREFIX + url + _CONTENT_POSTFIX;

			String exportedContent =
				_layoutReferencesExportImportContentProcessor.
					replaceExportContentReferences(
						exportPortletDataContext, referrerStagedModel,
						contentToExport, true, false);

			if (exportFromDefaultGroup) {
				exportGroup.setGroupKey(exportGroupKey);

				exportGroup = _groupLocalService.updateGroup(exportGroup);

				if (defaultGroup != null) {
					defaultGroup.setGroupKey(
						PropsValues.VIRTUAL_HOSTS_DEFAULT_SITE_NAME);

					defaultGroup = _groupLocalService.updateGroup(defaultGroup);
				}
			}

			if (importToDefaultGroup) {
				if (defaultGroup != null) {
					defaultGroup.setGroupKey(_GROUP_KEY);

					defaultGroup = _groupLocalService.updateGroup(defaultGroup);
				}

				importGroup.setGroupKey(
					PropsValues.VIRTUAL_HOSTS_DEFAULT_SITE_NAME);

				importGroup = _groupLocalService.updateGroup(importGroup);
			}

			PortletDataContext importPortletDataContext =
				PortletDataContextFactoryUtil.createImportPortletDataContext(
					importGroup.getCompanyId(), importGroup.getGroupId(),
					new HashMap<>(), new TestUserIdStrategy(),
					testReaderWriter);

			importPortletDataContext.setImportDataRootElement(rootElement);

			importPortletDataContext.setMissingReferencesElement(
				missingReferencesElement);

			String importedContent =
				_layoutReferencesExportImportContentProcessor.
					replaceImportContentReferences(
						importPortletDataContext, referrerStagedModel,
						exportedContent);

			if (importToDefaultGroup) {
				importGroup.setGroupKey(importGroupKey);

				importGroup = _groupLocalService.updateGroup(importGroup);

				if (defaultGroup != null) {
					defaultGroup.setGroupKey(
						PropsValues.VIRTUAL_HOSTS_DEFAULT_SITE_NAME);

					defaultGroup = _groupLocalService.updateGroup(defaultGroup);
				}
			}

			return importedContent.substring(
				_CONTENT_PREFIX.length(),
				importedContent.length() - _CONTENT_POSTFIX.length());
		}
		catch (Exception exception) {
			exportGroup.setGroupKey(exportGroupKey);

			_groupLocalService.updateGroup(exportGroup);

			importGroup.setGroupKey(importGroupKey);

			_groupLocalService.updateGroup(importGroup);

			if (defaultGroup != null) {
				defaultGroup.setGroupKey(
					PropsValues.VIRTUAL_HOSTS_DEFAULT_SITE_NAME);

				_groupLocalService.updateGroup(defaultGroup);
			}

			throw exception;
		}
	}

	private String _getCompanyHostPortalURL(Group group) throws Exception {
		Company company = _companyLocalService.getCompany(group.getCompanyId());

		return _portal.getPortalURL(
			company.getVirtualHostname(), _portal.getPortalServerPort(false),
			false);
	}

	private String _getGroupVirtualHostPortalURL(
		Group group, boolean privateLayout) {

		LayoutSet layoutSet = null;

		if (privateLayout) {
			layoutSet = group.getPrivateLayoutSet();
		}
		else {
			layoutSet = group.getPublicLayoutSet();
		}

		TreeMap<String, String> virtualHostnames =
			layoutSet.getVirtualHostnames();

		return _portal.getPortalURL(
			virtualHostnames.firstKey(), _portal.getPortalServerPort(false),
			false);
	}

	private static final String _CONTENT_POSTFIX = "\">link</a>";

	private static final String _CONTENT_PREFIX = "<a href=\"";

	private static final String _GROUP_KEY = RandomTestUtil.randomString(
		NumericStringRandomizerBumper.INSTANCE,
		UniqueStringRandomizerBumper.INSTANCE);

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject(filter = "content.processor.type=LayoutReferences")
	private ExportImportContentProcessor<String>
		_layoutReferencesExportImportContentProcessor;

	@Inject
	private Portal _portal;

}