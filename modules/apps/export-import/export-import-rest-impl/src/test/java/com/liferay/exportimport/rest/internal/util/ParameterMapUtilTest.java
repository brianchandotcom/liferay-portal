/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.internal.util;

import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.rest.dto.v1_0.ExportProcessRequest;
import com.liferay.exportimport.rest.dto.v1_0.ImportProcessRequest;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Petteri Karttunen
 */
public class ParameterMapUtilTest {

	@ClassRule
	@Rule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testToParameterMapWhenExportSiteExternalReferenceCodeIsNull() {
		ExportProcessRequest exportProcessRequest = new ExportProcessRequest();

		exportProcessRequest.setSiteExternalReferenceCodes(
			new String[] {"erc1", null});

		Map<String, String[]> parameterMap = ParameterMapUtil.toParameterMap(
			exportProcessRequest, false);

		Assert.assertArrayEquals(
			new String[] {"erc1"},
			parameterMap.get(
				PortletDataHandlerKeys.GROUP_EXTERNAL_REFERENCE_CODES));
	}

	@Test
	public void testToParameterMapWhenExportSiteExternalReferenceCodesAreBlank() {
		ExportProcessRequest exportProcessRequest = new ExportProcessRequest();

		exportProcessRequest.setSiteExternalReferenceCodes(
			new String[] {StringPool.BLANK, "   "});

		Map<String, String[]> parameterMap = ParameterMapUtil.toParameterMap(
			exportProcessRequest, false);

		Assert.assertArrayEquals(
			new String[] {StringPool.BLANK, "   "},
			parameterMap.get(
				PortletDataHandlerKeys.GROUP_EXTERNAL_REFERENCE_CODES));
	}

	@Test
	public void testToParameterMapWhenExportSiteExternalReferenceCodesAreDuplicated() {
		ExportProcessRequest exportProcessRequest = new ExportProcessRequest();

		exportProcessRequest.setSiteExternalReferenceCodes(
			new String[] {"erc1", "erc2", "erc1"});

		Map<String, String[]> parameterMap = ParameterMapUtil.toParameterMap(
			exportProcessRequest, false);

		Assert.assertArrayEquals(
			new String[] {"erc1", "erc2"},
			parameterMap.get(
				PortletDataHandlerKeys.GROUP_EXTERNAL_REFERENCE_CODES));
	}

	@Test
	public void testToParameterMapWhenExportSiteExternalReferenceCodesAreEmpty() {
		ExportProcessRequest exportProcessRequest = new ExportProcessRequest();

		exportProcessRequest.setSiteExternalReferenceCodes(new String[0]);

		Map<String, String[]> parameterMap = ParameterMapUtil.toParameterMap(
			exportProcessRequest, false);

		Assert.assertFalse(
			parameterMap.containsKey(
				PortletDataHandlerKeys.GROUP_EXTERNAL_REFERENCE_CODES));
	}

	@Test
	public void testToParameterMapWhenExportSiteExternalReferenceCodesAreNull() {
		Map<String, String[]> parameterMap = ParameterMapUtil.toParameterMap(
			new ExportProcessRequest(), false);

		Assert.assertFalse(
			parameterMap.containsKey(
				PortletDataHandlerKeys.GROUP_EXTERNAL_REFERENCE_CODES));
	}

	@Test
	public void testToParameterMapWhenExportSiteExternalReferenceCodesArePadded() {
		ExportProcessRequest exportProcessRequest = new ExportProcessRequest();

		exportProcessRequest.setSiteExternalReferenceCodes(
			new String[] {"  erc1  "});

		Map<String, String[]> parameterMap = ParameterMapUtil.toParameterMap(
			exportProcessRequest, false);

		Assert.assertArrayEquals(
			new String[] {"  erc1  "},
			parameterMap.get(
				PortletDataHandlerKeys.GROUP_EXTERNAL_REFERENCE_CODES));
	}

	@Test
	public void testToParameterMapWhenExportSiteExternalReferenceCodesAreSet() {
		ExportProcessRequest exportProcessRequest = new ExportProcessRequest();

		exportProcessRequest.setSiteExternalReferenceCodes(
			new String[] {"erc1", "erc2"});

		Map<String, String[]> parameterMap = ParameterMapUtil.toParameterMap(
			exportProcessRequest, false);

		Assert.assertArrayEquals(
			new String[] {"erc1", "erc2"},
			parameterMap.get(
				PortletDataHandlerKeys.GROUP_EXTERNAL_REFERENCE_CODES));
	}

	@Test
	public void testToParameterMapWhenImportSiteExternalReferenceCodesAreEmpty() {
		ImportProcessRequest importProcessRequest = new ImportProcessRequest();

		importProcessRequest.setSiteExternalReferenceCodes(new String[0]);

		Map<String, String[]> parameterMap = ParameterMapUtil.toParameterMap(
			importProcessRequest, false);

		Assert.assertFalse(
			parameterMap.containsKey(
				PortletDataHandlerKeys.GROUP_EXTERNAL_REFERENCE_CODES));
	}

	@Test
	public void testToParameterMapWhenImportSiteExternalReferenceCodesAreSet() {
		ImportProcessRequest importProcessRequest = new ImportProcessRequest();

		importProcessRequest.setSiteExternalReferenceCodes(
			new String[] {"erc1"});

		Map<String, String[]> parameterMap = ParameterMapUtil.toParameterMap(
			importProcessRequest, false);

		Assert.assertArrayEquals(
			new String[] {"erc1"},
			parameterMap.get(
				PortletDataHandlerKeys.GROUP_EXTERNAL_REFERENCE_CODES));
	}

}