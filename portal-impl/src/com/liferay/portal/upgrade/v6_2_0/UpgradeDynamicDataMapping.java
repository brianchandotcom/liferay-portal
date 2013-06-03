/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_2_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.upgrade.v6_2_0.util.DDMTemplateTable;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

/**
 * @author Juan Fernández
 * @author Marcellus Tavares
 */
public class UpgradeDynamicDataMapping extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try {
			runSQL("alter table DDMTemplate add classNameId LONG");

			runSQL("alter table DDMTemplate add templateKey STRING");

			runSQL("alter_column_name DDMTemplate structureId classPK LONG");
		}
		catch (SQLException sqle) {
			upgradeTable(
				DDMTemplateTable.TABLE_NAME, DDMTemplateTable.TABLE_COLUMNS,
				DDMTemplateTable.TABLE_SQL_CREATE,
				DDMTemplateTable.TABLE_SQL_ADD_INDEXES);
		}

		long classNameId = PortalUtil.getClassNameId(DDMStructure.class);

		runSQL("update DDMTemplate set classNameId = " + classNameId);

		updateStructures();
	}

	protected void updateMetadataElement(
		Element metadataElement, String[] relocatedMetadadaEntryNames,
		String[] removedMetadataEntryNames) {

		Element parentElement = metadataElement.getParent();

		List<Element> entryElements = metadataElement.elements("entry");

		for (Element entryElement : entryElements) {
			String name = entryElement.attributeValue("name");

			if (ArrayUtil.contains(removedMetadataEntryNames, name)) {
				metadataElement.remove(entryElement);
			}
			else if (ArrayUtil.contains(relocatedMetadadaEntryNames, name)) {
				parentElement.addAttribute(name, entryElement.getText());

				metadataElement.remove(entryElement);
			}
		}
	}

	protected void updateStructure(
			long structureId, String structureKey, String xsd)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update DDMStructure set structureKey = ?, xsd = ? where " +
					"structureId = ?");

			ps.setString(1, structureKey);
			ps.setString(2, xsd);
			ps.setLong(3, structureId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateStructures() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement("select * from DDMStructure");

			rs = ps.executeQuery();

			while (rs.next()) {
				long structureId = rs.getLong("structureId");
				String structureKey = rs.getString("structureKey");
				String xsd = rs.getString("xsd");

				updateStructure(
					structureId, structureKey.trim().toUpperCase(),
					updateXSD(xsd));
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected String updateXSD(String xsd) throws Exception {
		Document document = SAXReaderUtil.read(xsd);

		Element rootElement = document.getRootElement();

		List<Element> dynamicElementElements = rootElement.elements(
			"dynamic-element");

		for (Element dynamicElementElement : dynamicElementElements) {
			updateXSDDynamicElement(dynamicElementElement);
		}

		return DDMXMLUtil.formatXML(document);
	}

	protected void updateXSDDynamicElement(Element element) {
		Element metadataElement = element.element("meta-data");

		updateMetadataElement(
			metadataElement,
			new String[] {
				"multiple", "readOnly", "repeatable", "required", "showLabel",
				"width",
			},
			new String[] {
				"displayChildLabelAsValue", "fieldCssClass"
			});

		List<Element> dynamicElementElements = element.elements(
			"dynamic-element");

		for (Element dynamicElementElement : dynamicElementElements) {
			updateXSDDynamicElement(dynamicElementElement);
		}
	}

}