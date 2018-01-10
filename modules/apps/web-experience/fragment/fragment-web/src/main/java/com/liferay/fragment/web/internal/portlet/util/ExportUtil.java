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

package com.liferay.fragment.web.internal.portlet.util;

import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;

import java.io.File;

import java.util.List;

import javax.portlet.PortletException;

/**
 * @author Eudaldo Alonso
 */
public class ExportUtil {

	public static File exportFragmentCollections(
			List<FragmentCollection> fragmentCollections)
		throws PortletException {

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		try {
			for (FragmentCollection fragmentCollection : fragmentCollections) {
				fragmentCollection.populateZipWriter(
					zipWriter, StringPool.BLANK);
			}

			zipWriter.finish();

			return zipWriter.getFile();
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	public static File exportFragmentEntries(
			List<FragmentEntry> fragmentEntries)
		throws PortletException {

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		try {
			for (FragmentEntry fragmentEntry : fragmentEntries) {
				fragmentEntry.populateZipWriter(zipWriter, StringPool.BLANK);
			}

			zipWriter.finish();

			return zipWriter.getFile();
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

}