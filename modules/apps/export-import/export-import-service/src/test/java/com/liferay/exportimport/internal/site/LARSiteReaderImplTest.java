/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.site;

import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.site.LARSite;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactory;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.tools.ToolDependencies;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Petteri Karttunen
 */
public class LARSiteReaderImplTest {

	@ClassRule
	@Rule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		ToolDependencies.wireBasic();
	}

	@Before
	public void setUp() {
		_larSiteReaderImpl = new LARSiteReaderImpl();

		_dlFileEntryLocalService = Mockito.mock(DLFileEntryLocalService.class);
		_zipReaderFactory = Mockito.mock(ZipReaderFactory.class);

		ReflectionTestUtil.setFieldValue(
			_larSiteReaderImpl, "_dlFileEntryLocalService",
			_dlFileEntryLocalService);
		ReflectionTestUtil.setFieldValue(
			_larSiteReaderImpl, "_zipReaderFactory", _zipReaderFactory);
	}

	@Test
	public void testGetLARSitesClosesWhatItOpens() throws Exception {
		InputStream inputStream = Mockito.mock(InputStream.class);

		InputStream entryInputStream = Mockito.spy(
			new ByteArrayInputStream(
				_getManifest(
					_getSiteElement("EMEA", "erc")
				).getBytes()));

		ZipReader zipReader = Mockito.mock(ZipReader.class);

		Mockito.when(
			zipReader.getEntryAsInputStream("/manifest.xml")
		).thenReturn(
			entryInputStream
		);

		_larSiteReaderImpl.getLARSites(_mockFileEntry(inputStream, zipReader));

		Mockito.verify(
			entryInputStream, Mockito.atLeastOnce()
		).close();

		Mockito.verify(
			inputStream
		).close();

		Mockito.verify(
			zipReader
		).close();
	}

	@Test
	public void testGetLARSitesWhenFileEntryIsGiven() throws Exception {
		ZipReader zipReader = _mockZipReader(
			_getManifest(_getSiteElement("EMEA", "erc")), "/manifest.xml");

		List<LARSite> larSites = _larSiteReaderImpl.getLARSites(
			_mockFileEntry(new ByteArrayInputStream(new byte[0]), zipReader));

		Assert.assertEquals(larSites.toString(), 1, larSites.size());

		Mockito.verify(
			zipReader
		).getEntryAsInputStream(
			"/manifest.xml"
		);
	}

	@Test
	public void testGetLARSitesWhenManifestHasNoSites() throws Exception {
		Assert.assertTrue(
			_getLARSites(
				"<root><header /></root>"
			).isEmpty());
	}

	@Test
	public void testGetLARSitesWhenManifestHasSites() throws Exception {
		List<LARSite> larSites = _getLARSites(
			_getManifest(
				_getSiteElement(
					2, "EMEA", "erc-emea", 123, "erc-global", "Global / EMEA"),
				_getSiteElement("Support", "erc-support")));

		Assert.assertEquals(larSites.toString(), 2, larSites.size());

		LARSite larSite = larSites.get(0);

		Assert.assertEquals(2, larSite.getChildSiteCount());
		Assert.assertEquals("EMEA", larSite.getDescriptiveName());
		Assert.assertEquals("erc-emea", larSite.getExternalReferenceCode());
		Assert.assertEquals(123, larSite.getGroupId());
		Assert.assertEquals(
			"erc-global", larSite.getParentExternalReferenceCode());
		Assert.assertEquals("Global / EMEA", larSite.getPath());
	}

	@Test
	public void testGetLARSitesWhenManifestIsMissing() throws Exception {
		Assert.assertTrue(
			_getLARSites(
				null
			).isEmpty());
	}

	@Test
	public void testGetLARSitesWhenSiteExternalReferenceCodeIsMissing()
		throws Exception {

		List<LARSite> larSites = _getLARSites(
			_getManifest(
				"<site descriptive-name=\"EMEA\" group-id=\"123\" />",
				_getSiteElement("Support", "erc-support")));

		Assert.assertEquals(larSites.toString(), 1, larSites.size());

		LARSite larSite = larSites.get(0);

		Assert.assertEquals("erc-support", larSite.getExternalReferenceCode());
	}

	private List<LARSite> _getLARSites(String manifest) throws Exception {
		return _larSiteReaderImpl.getLARSites(
			_mockPortletDataContext(_mockZipReader(manifest, "/manifest.xml")));
	}

	private String _getManifest(String... siteElements) {
		StringBuilder sb = new StringBuilder();

		sb.append("<root><header /><sites>");

		for (String siteElement : siteElements) {
			sb.append(siteElement);
		}

		sb.append("</sites></root>");

		return sb.toString();
	}

	private String _getSiteElement(
		int childSiteCount, String descriptiveName,
		String externalReferenceCode, long groupId,
		String parentExternalReferenceCode, String path) {

		return String.format(
			"<site child-site-count=\"%d\" descriptive-name=\"%s\" " +
				"external-reference-code=\"%s\" group-id=\"%d\" " +
					"parent-external-reference-code=\"%s\" path=\"%s\" />",
			childSiteCount, descriptiveName, externalReferenceCode, groupId,
			parentExternalReferenceCode, path);
	}

	private String _getSiteElement(
		String descriptiveName, String externalReferenceCode) {

		return String.format(
			"<site child-site-count=\"0\" descriptive-name=\"%s\" " +
				"external-reference-code=\"%s\" group-id=\"1\" path=\"%s\" />",
			descriptiveName, externalReferenceCode, descriptiveName);
	}

	private FileEntry _mockFileEntry(
			InputStream inputStream, ZipReader zipReader)
		throws Exception {

		Mockito.when(
			_dlFileEntryLocalService.getFileAsStream(
				Mockito.anyLong(), Mockito.any(), Mockito.eq(false))
		).thenReturn(
			inputStream
		);

		Mockito.when(
			_zipReaderFactory.getZipReader(Mockito.any(InputStream.class))
		).thenReturn(
			zipReader
		);

		return Mockito.mock(FileEntry.class);
	}

	private PortletDataContext _mockPortletDataContext(ZipReader zipReader) {
		PortletDataContext portletDataContext = Mockito.mock(
			PortletDataContext.class);

		Mockito.when(
			portletDataContext.getParameterMap()
		).thenReturn(
			new HashMap<>()
		);

		Mockito.when(
			portletDataContext.getZipReader()
		).thenReturn(
			zipReader
		);

		return portletDataContext;
	}

	private ZipReader _mockZipReader(String manifest, String path) {
		ZipReader zipReader = Mockito.mock(ZipReader.class);

		Mockito.when(
			zipReader.getEntryAsInputStream(path)
		).thenReturn(
			(manifest == null) ? null :
				new ByteArrayInputStream(manifest.getBytes())
		);

		return zipReader;
	}

	private DLFileEntryLocalService _dlFileEntryLocalService;
	private LARSiteReaderImpl _larSiteReaderImpl;
	private ZipReaderFactory _zipReaderFactory;

}