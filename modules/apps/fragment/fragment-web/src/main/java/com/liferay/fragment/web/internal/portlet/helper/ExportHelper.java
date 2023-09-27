/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.web.internal.portlet.helper;

import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentComposition;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactory;

import java.io.File;

import java.util.List;

import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = ExportHelper.class)
public class ExportHelper {

	public File exportFragmentCollections(
			List<FragmentCollection> fragmentCollections)
		throws PortletException {

		ZipWriter zipWriter = _zipWriterFactory.getZipWriter();

		try {
			for (FragmentCollection fragmentCollection : fragmentCollections) {
				fragmentCollection.populateZipWriter(zipWriter);
			}

			return zipWriter.getFile();
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}
	}

	public File exportFragmentCompositionsAndFragmentEntries(
			List<FragmentComposition> fragmentCompositions,
			List<FragmentEntry> fragmentEntries)
		throws PortletException {

		ZipWriter zipWriter = _zipWriterFactory.getZipWriter();

		try {
			for (FragmentComposition fragmentComposition :
					fragmentCompositions) {

				fragmentComposition.populateZipWriter(
					zipWriter, StringPool.BLANK);
			}

			for (FragmentEntry fragmentEntry : fragmentEntries) {
				if (fragmentEntry.isTypeReact()) {
					continue;
				}

				fragmentEntry.populateZipWriter(zipWriter, StringPool.BLANK);
			}

			return zipWriter.getFile();
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}
	}

	@Reference
	private ZipWriterFactory _zipWriterFactory;

}