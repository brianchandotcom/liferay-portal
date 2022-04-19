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

package com.liferay.fragment.web.internal.portlet.helper;

import com.liferay.fragment.constants.FragmentConstants;
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
@Component(immediate = true, service = ExportHelper.class)
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
				if (fragmentEntry.getType() == FragmentConstants.TYPE_REACT) {
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