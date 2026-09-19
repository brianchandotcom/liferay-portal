/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.group;

import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.exportimport.group.ExportedGroup;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.petra.string.StringBundler;
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
public class ExportedGroupProviderImplTest {

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
		_exportedGroupProviderImpl = new ExportedGroupProviderImpl();

		_dlFileEntryLocalService = Mockito.mock(DLFileEntryLocalService.class);
		_zipReaderFactory = Mockito.mock(ZipReaderFactory.class);

		ReflectionTestUtil.setFieldValue(
			_exportedGroupProviderImpl, "_dlFileEntryLocalService",
			_dlFileEntryLocalService);
		ReflectionTestUtil.setFieldValue(
			_exportedGroupProviderImpl, "_zipReaderFactory", _zipReaderFactory);
	}

	@Test
	public void testGetExportedGroupsClosesWhatItOpens() throws Exception {
		InputStream inputStream = Mockito.mock(InputStream.class);

		String manifest = _getManifest(_getGroupElement("EMEA", "erc"));

		InputStream entryInputStream = Mockito.spy(
			new ByteArrayInputStream(manifest.getBytes()));

		ZipReader zipReader = Mockito.mock(ZipReader.class);

		Mockito.when(
			zipReader.getEntryAsInputStream("/manifest.xml")
		).thenReturn(
			entryInputStream
		);

		_exportedGroupProviderImpl.getExportedGroups(
			_mockFileEntry(inputStream, zipReader));

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
	public void testGetExportedGroupsWhenFileEntryIsGiven() throws Exception {
		ZipReader zipReader = _mockZipReader(
			_getManifest(_getGroupElement("EMEA", "erc")), "/manifest.xml");

		List<ExportedGroup> exportedGroups =
			_exportedGroupProviderImpl.getExportedGroups(
				_mockFileEntry(
					new ByteArrayInputStream(new byte[0]), zipReader));

		Assert.assertEquals(
			exportedGroups.toString(), 1, exportedGroups.size());

		Mockito.verify(
			zipReader
		).getEntryAsInputStream(
			"/manifest.xml"
		);
	}

	@Test
	public void testGetExportedGroupsWhenGroupExternalReferenceCodeIsMissing()
		throws Exception {

		List<ExportedGroup> exportedGroups = _getExportedGroups(
			_getManifest(
				"<group descriptive-name=\"EMEA\" group-id=\"123\" />",
				_getGroupElement("Support", "erc-support")));

		Assert.assertEquals(
			exportedGroups.toString(), 1, exportedGroups.size());

		ExportedGroup exportedGroup = exportedGroups.get(0);

		Assert.assertEquals(
			"erc-support", exportedGroup.getExternalReferenceCode());
	}

	@Test
	public void testGetExportedGroupsWhenManifestHasGroups() throws Exception {
		List<ExportedGroup> exportedGroups = _getExportedGroups(
			_getManifest(
				_getGroupElement(
					2, "EMEA", "erc-emea", 123, "erc-global", "Global / EMEA"),
				_getGroupElement("Support", "erc-support")));

		Assert.assertEquals(
			exportedGroups.toString(), 2, exportedGroups.size());

		ExportedGroup exportedGroup = exportedGroups.get(0);

		Assert.assertEquals(2, exportedGroup.getChildGroupCount());
		Assert.assertEquals("EMEA", exportedGroup.getDescriptiveName());
		Assert.assertEquals(
			"erc-emea", exportedGroup.getExternalReferenceCode());
		Assert.assertEquals(123, exportedGroup.getGroupId());
		Assert.assertEquals(
			"erc-global", exportedGroup.getParentGroupExternalReferenceCode());
		Assert.assertEquals("Global / EMEA", exportedGroup.getPath());
	}

	@Test
	public void testGetExportedGroupsWhenManifestHasNoGroups()
		throws Exception {

		List<ExportedGroup> exportedGroups = _getExportedGroups(
			"<root><header /></root>");

		Assert.assertTrue(exportedGroups.isEmpty());
	}

	@Test
	public void testGetExportedGroupsWhenManifestIsMissing() throws Exception {
		List<ExportedGroup> exportedGroups = _getExportedGroups(null);

		Assert.assertTrue(exportedGroups.isEmpty());
	}

	private List<ExportedGroup> _getExportedGroups(String manifest)
		throws Exception {

		return _exportedGroupProviderImpl.getExportedGroups(
			_mockPortletDataContext(_mockZipReader(manifest, "/manifest.xml")));
	}

	private String _getGroupElement(
		int childGroupCount, String descriptiveName,
		String externalReferenceCode, long groupId,
		String parentGroupExternalReferenceCode, String path) {

		return String.format(
			StringBundler.concat(
				"<group child-group-count=\"%d\" descriptive-name=\"%s\" ",
				"external-reference-code=\"%s\" group-id=\"%d\" ",
				"parent-group-external-reference-code=\"%s\" path=\"%s\" />"),
			childGroupCount, descriptiveName, externalReferenceCode, groupId,
			parentGroupExternalReferenceCode, path);
	}

	private String _getGroupElement(
		String descriptiveName, String externalReferenceCode) {

		return String.format(
			"<group child-group-count=\"0\" descriptive-name=\"%s\" " +
				"external-reference-code=\"%s\" group-id=\"1\" path=\"%s\" />",
			descriptiveName, externalReferenceCode, descriptiveName);
	}

	private String _getManifest(String... groupElements) {
		StringBundler sb = new StringBundler(groupElements.length + 2);

		sb.append("<root><header /><groups>");

		for (String groupElement : groupElements) {
			sb.append(groupElement);
		}

		sb.append("</groups></root>");

		return sb.toString();
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
	private ExportedGroupProviderImpl _exportedGroupProviderImpl;
	private ZipReaderFactory _zipReaderFactory;

}