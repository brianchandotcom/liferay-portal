/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.headless.admin.site.dto.v1_0.Site;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Petteri Karttunen
 */
public class SiteResourceImplTest {

	@ClassRule
	@Rule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_siteResourceImpl = new SiteResourceImpl();

		_siteResourceImpl.setContextBatchUnsafeBiConsumer(
			(sites, unsafeFunction) -> {
				for (Site site : sites) {
					_batchSiteExternalReferenceCodes.add(
						site.getExternalReferenceCode());
				}
			});
	}

	@Test
	public void testCreateWhenSelectedExternalReferenceCodesAreEmpty()
		throws Exception {

		_create(new String[0], "erc1", "erc2");

		Assert.assertTrue(
			_batchSiteExternalReferenceCodes.toString(),
			_batchSiteExternalReferenceCodes.isEmpty());
	}

	@Test
	public void testCreateWhenSelectedExternalReferenceCodesAreNarrowed()
		throws Exception {

		_create(new String[] {"erc2"}, "erc1", "erc2", "erc3");

		Assert.assertEquals(
			Arrays.asList("erc2"), _batchSiteExternalReferenceCodes);
	}

	@Test(expected = ClassCastException.class)
	public void testCreateWhenSelectedExternalReferenceCodesAreNotAnArray()
		throws Exception {

		_create("erc2", "erc1", "erc2");
	}

	@Test
	public void testCreateWhenSelectedExternalReferenceCodesAreNotGiven()
		throws Exception {

		_create(null, "erc1", "erc2");

		Assert.assertEquals(
			Arrays.asList("erc1", "erc2"), _batchSiteExternalReferenceCodes);
	}

	@Test
	public void testDeleteWhenParametersAreNull() throws Exception {
		_siteResourceImpl.delete(_getSites("erc1", "erc2"), null);

		Assert.assertEquals(
			Arrays.asList("erc1", "erc2"), _batchSiteExternalReferenceCodes);
	}

	@Test
	public void testDeleteWhenSelectedExternalReferenceCodesAreNarrowed()
		throws Exception {

		_delete(new String[] {"erc2"}, "erc1", "erc2");

		Assert.assertEquals(
			Arrays.asList("erc2"), _batchSiteExternalReferenceCodes);
	}

	@Test
	public void testDeleteWhenSelectedExternalReferenceCodesAreNotGiven()
		throws Exception {

		_delete(null, "erc1", "erc2");

		Assert.assertEquals(
			Arrays.asList("erc1", "erc2"), _batchSiteExternalReferenceCodes);
	}

	@Test
	public void testGetExportImportDescriptorParameters() {
		PortletDataContext portletDataContext = Mockito.mock(
			PortletDataContext.class);

		Mockito.when(
			portletDataContext.getParameterMap()
		).thenReturn(
			HashMapBuilder.put(
				PortletDataHandlerKeys.SITE_EXTERNAL_REFERENCE_CODES,
				new String[] {"erc1", "erc2"}
			).build()
		);

		ExportImportVulcanBatchEngineTaskItemDelegate.ExportImportDescriptor<?>
			exportImportDescriptor =
				_siteResourceImpl.getExportImportDescriptor();

		Map<String, Serializable> parameters =
			exportImportDescriptor.getParameters(portletDataContext);

		Assert.assertArrayEquals(
			new String[] {"erc1", "erc2"},
			(String[])parameters.get("externalReferenceCodes"));
		Assert.assertArrayEquals(
			new String[] {"erc1", "erc2"},
			(String[])parameters.get("selectedExternalReferenceCodes"));
	}

	private void _create(
			Object externalReferenceCodes, String... siteExternalReferenceCodes)
		throws Exception {

		_siteResourceImpl.create(
			_getSites(siteExternalReferenceCodes),
			_getParameters(externalReferenceCodes));
	}

	private void _delete(
			Object externalReferenceCodes, String... siteExternalReferenceCodes)
		throws Exception {

		_siteResourceImpl.delete(
			_getSites(siteExternalReferenceCodes),
			_getParameters(externalReferenceCodes));
	}

	private Map<String, Serializable> _getParameters(
		Object selectedExternalReferenceCodes) {

		return HashMapBuilder.<String, Serializable>put(
			"createStrategy", (Serializable)"UPSERT"
		).put(
			"selectedExternalReferenceCodes",
			() -> (Serializable)selectedExternalReferenceCodes
		).build();
	}

	private List<Site> _getSites(String... siteExternalReferenceCodes) {
		List<Site> sites = new ArrayList<>();

		for (String siteExternalReferenceCode : siteExternalReferenceCodes) {
			Site site = new Site();

			site.setExternalReferenceCode(siteExternalReferenceCode);

			sites.add(site);
		}

		return sites;
	}

	private final List<String> _batchSiteExternalReferenceCodes =
		new ArrayList<>();
	private SiteResourceImpl _siteResourceImpl;

}