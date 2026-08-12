/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.util;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Petteri Karttunen
 */
public class LARManifestPathUtilTest {

	@ClassRule
	@Rule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetExportManifestXmlFilePathWhenPassIsCompanyScoped() {
		Assert.assertEquals(
			"/manifest.xml",
			LARManifestPathUtil.getExportManifestXmlFilePath(
				_mockPortletDataContext(new HashMap<>())));
	}

	@Test
	public void testGetExportManifestXmlFilePathWhenPassIsSiteScoped() {
		Assert.assertEquals(
			"/group/" + _SCOPE_GROUP_ID + "/manifest.xml",
			LARManifestPathUtil.getExportManifestXmlFilePath(
				_mockPortletDataContext(_getSiteParameterMap())));
	}

	@Test
	public void testGetImportManifestXmlFilePathMatchesTheExportedPath() {
		Assert.assertEquals(
			LARManifestPathUtil.getExportManifestXmlFilePath(
				_mockPortletDataContext(_getSiteParameterMap())),
			LARManifestPathUtil.getImportManifestXmlFilePath(_SCOPE_GROUP_ID));
	}

	@Test
	public void testGetImportManifestXmlFilePathWhenPassIsCompanyScoped() {
		Assert.assertEquals(
			"/manifest.xml",
			LARManifestPathUtil.getImportManifestXmlFilePath(
				_mockPortletDataContext(new HashMap<>())));
	}

	@Test
	public void testGetImportManifestXmlFilePathWhenPassIsSiteScoped() {
		Assert.assertEquals(
			"/group/" + _SOURCE_GROUP_ID + "/manifest.xml",
			LARManifestPathUtil.getImportManifestXmlFilePath(
				_mockPortletDataContext(_getSiteParameterMap())));
	}

	@Test
	public void testGetImportManifestXmlFilePathWhenSourceGroupIdIsGiven() {
		Assert.assertEquals(
			"/group/" + _SOURCE_GROUP_ID + "/manifest.xml",
			LARManifestPathUtil.getImportManifestXmlFilePath(_SOURCE_GROUP_ID));
	}

	private Map<String, String[]> _getSiteParameterMap() {
		return SiteExportImportParameterUtil.toSiteExportParameterMap(
			new HashMap<>(), "erc");
	}

	private PortletDataContext _mockPortletDataContext(
		Map<String, String[]> parameterMap) {

		PortletDataContext portletDataContext = Mockito.mock(
			PortletDataContext.class);

		Mockito.when(
			portletDataContext.getParameterMap()
		).thenReturn(
			parameterMap
		);

		Mockito.when(
			portletDataContext.getScopeGroupId()
		).thenReturn(
			_SCOPE_GROUP_ID
		);

		Mockito.when(
			portletDataContext.getSourceGroupId()
		).thenReturn(
			_SOURCE_GROUP_ID
		);

		return portletDataContext;
	}

	private static final long _SCOPE_GROUP_ID = 1;

	private static final long _SOURCE_GROUP_ID = 2;

}