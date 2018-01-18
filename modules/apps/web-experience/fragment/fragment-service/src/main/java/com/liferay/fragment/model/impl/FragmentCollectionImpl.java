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

package com.liferay.fragment.model.impl;

import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.xml.simple.Element;
import com.liferay.portal.kernel.zip.ZipWriter;

import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class FragmentCollectionImpl extends FragmentCollectionBaseImpl {

	@Override
	public void populateZipWriter(ZipWriter zipWriter, String path)
		throws Exception {

		path = path + StringPool.SLASH + getFragmentCollectionId();

		Element fragmentCollectionElement = new Element(
			"fragment-collection", false);

		fragmentCollectionElement.addElement("name", getName());
		fragmentCollectionElement.addElement("description", getDescription());

		zipWriter.addEntry(
			path + "/definition.xml", fragmentCollectionElement.toXMLString());

		List<FragmentEntry> fragmentEntries =
			FragmentEntryLocalServiceUtil.getFragmentEntries(
				getFragmentCollectionId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Element fragmentEntriesElement = new Element("fragment-entries", false);

		for (FragmentEntry fragmentEntry : fragmentEntries) {
			fragmentEntry.populateZipWriter(
				zipWriter, path + "/fragment_entries");

			fragmentEntriesElement.addElement(
				"fragment-entry", fragmentEntry.getFragmentEntryId());
		}

		zipWriter.addEntry(
			path + "/fragment_entries/definition.xml",
			fragmentEntriesElement.toXMLString());
	}

}