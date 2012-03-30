/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v5_2_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLUtil;
import com.liferay.util.PwdGenerator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradeJournal extends UpgradeProcess {

	protected void addDynamicElementInstanceId(Element root) throws Exception {
		List<Element> elements = root.elements();

		for (Element element : elements) {
			if (!element.getName().equals("dynamic-element")) {
				continue;
			}

			String instanceId = element.attributeValue("instance-id");
			String type = element.attributeValue("type");

			if (Validator.isNull(instanceId)) {
				instanceId = PwdGenerator.getPassword();

				element.addAttribute("instance-id", instanceId);

				if (type.equals("image")) {
					updateJournalArticleImageInstanceId(element, instanceId);
				}
			}

			addDynamicElementInstanceId(element);
		}
	}

	protected String addDynamicElementInstanceId(String content)
		throws Exception {

		Document doc = SAXReaderUtil.read(content);

		Element root = doc.getRootElement();

		addDynamicElementInstanceId(root);

		return DDMXMLUtil.formatXML(doc);
	}

	protected void deleteJournalArticleImages() throws Exception {
		runSQL(
			"delete from JournalArticleImage where elInstanceId is null or " +
				"elInstanceId = ''");
	}

	@Override
	protected void doUpgrade() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select id_, content, structureId from JournalArticle");

			rs = ps.executeQuery();

			while (rs.next()) {
				long id = rs.getLong("id_");
				String content = GetterUtil.getString(rs.getString("content"));
				String structureId = rs.getString("structureId");

				if (Validator.isNull(structureId)) {
					continue;
				}

				String newContent = addDynamicElementInstanceId(content);

				if (content.equals(newContent)) {
					continue;
				}

				updateJournalArticleContent(id, newContent);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		deleteJournalArticleImages();
	}

	protected void updateJournalArticleContent(long id, String content)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"update JournalArticle set content = ? where id_ = ?");

			ps.setString(1, content);
			ps.setLong(2, id);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void updateJournalArticleImageInstanceId(
			Element parentElement, String instanceId)
		throws Exception {

		List<Element> elements = parentElement.elements("dynamic-content");

		for (Element element : elements) {
			long articleImageId = GetterUtil.getLong(
				element.attributeValue("id"));

			if (articleImageId <= 0) {
				continue;
			}

			runSQL(
				"update JournalArticleImage set elInstanceId = '" + instanceId +
					"' where articleImageId = " + articleImageId);
		}
	}

}