/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.model.impl;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portlet.journal.NoSuchStructureException;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.service.JournalStructureLocalServiceUtil;

import java.util.Iterator;

/**
 * @author Brian Wing Shun Chan
 */
public class JournalStructureImpl extends JournalStructureBaseImpl {

	public JournalStructureImpl() {
	}

	public String getMergedXsd() {
		String parentStructureId = getParentStructureId();

		String xsd = getXsd();

		if (Validator.isNull(parentStructureId)) {
			return xsd;
		}

		try {
			JournalStructure parentStructure = null;

			try {
				parentStructure = JournalStructureLocalServiceUtil.getStructure(
					getGroupId(), parentStructureId);
			}
			catch (NoSuchStructureException nsse) {
				Group group = GroupLocalServiceUtil.getGroup(getGroupId());

				Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
					group.getCompanyId());

				if (getGroupId() == companyGroup.getGroupId()) {
					throw new NoSuchStructureException();
				}

				parentStructure = JournalStructureLocalServiceUtil.getStructure(
					companyGroup.getGroupId(), parentStructureId);
			}

			Document doc = SAXReaderUtil.read(getXsd());

			Element root = doc.getRootElement();

			Document parentDoc = SAXReaderUtil.read(
				parentStructure.getMergedXsd());

			Element parentRoot = parentDoc.getRootElement();

			addParentStructureId(parentRoot, parentStructureId);

			root.content().addAll(0, parentRoot.content());

			xsd = root.asXML();
		}
		catch (Exception e) {
		}

		return xsd;
	}

	protected void addParentStructureId(
		Element parentEl, String parentStructureId) {

		Iterator<Element> itr = parentEl.elements(_DYNAMIC_ELEMENT).iterator();

		while (itr.hasNext()) {
			Element dynamicEl = itr.next();

			dynamicEl.addAttribute(_PARENT_STRUCTURE_ID, parentStructureId);

			addParentStructureId(dynamicEl, parentStructureId);
		}
	}

	private static final String _DYNAMIC_ELEMENT = "dynamic-element";

	private static final String _PARENT_STRUCTURE_ID = "parent-structure-id";

}