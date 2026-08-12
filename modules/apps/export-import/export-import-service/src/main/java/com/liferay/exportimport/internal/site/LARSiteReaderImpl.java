/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.site;

import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.exportimport.internal.util.LARManifestPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.site.LARSite;
import com.liferay.exportimport.site.LARSiteReader;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.xml.SecureXMLFactoryProviderUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.ElementHandler;
import com.liferay.portal.kernel.xml.ElementProcessor;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactory;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/**
 * @author Petteri Karttunen
 */
@Component(service = LARSiteReader.class)
public class LARSiteReaderImpl implements LARSiteReader {

	@Override
	public List<LARSite> getLARSites(FileEntry fileEntry) throws Exception {
		try (InputStream inputStream = _dlFileEntryLocalService.getFileAsStream(
				fileEntry.getFileEntryId(), fileEntry.getVersion(), false)) {

			try (ZipReader zipReader = _zipReaderFactory.getZipReader(
					inputStream)) {

				return _getLARSites(
					zipReader, LARManifestPathUtil.MANIFEST_XML_FILE_PATH);
			}
		}
	}

	@Override
	public List<LARSite> getLARSites(PortletDataContext portletDataContext)
		throws Exception {

		return _getLARSites(
			portletDataContext.getZipReader(),
			LARManifestPathUtil.getImportManifestXmlFilePath(
				portletDataContext));
	}

	private List<LARSite> _getLARSites(
			ZipReader zipReader, String manifestXmlFilePath)
		throws Exception {

		List<LARSite> larSites = new ArrayList<>();

		try (InputStream inputStream = zipReader.getEntryAsInputStream(
				manifestXmlFilePath)) {

			if (inputStream == null) {
				return larSites;
			}

			XMLReader xmlReader = SecureXMLFactoryProviderUtil.newXMLReader();

			xmlReader.setContentHandler(
				new ElementHandler(
					new ElementProcessor() {

						@Override
						public void processElement(Element element) {
							LARSite larSite = _toLARSite(element);

							if (larSite != null) {
								larSites.add(larSite);
							}
						}

					},
					new String[] {"site"}));

			xmlReader.parse(new InputSource(inputStream));
		}

		return larSites;
	}

	private LARSite _toLARSite(Element element) {
		String externalReferenceCode = element.attributeValue(
			"external-reference-code");

		if (Validator.isNull(externalReferenceCode)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Skipping ", element.attributeValue("group-id"),
						" because the external reference code attribute is ",
						"missing"));
			}

			return null;
		}

		return new LARSite(
			GetterUtil.getInteger(element.attributeValue("child-site-count")),
			element.attributeValue("descriptive-name"), externalReferenceCode,
			GetterUtil.getLong(element.attributeValue("group-id")),
			element.attributeValue("parent-external-reference-code"),
			element.attributeValue("path"));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LARSiteReaderImpl.class);

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private ZipReaderFactory _zipReaderFactory;

}