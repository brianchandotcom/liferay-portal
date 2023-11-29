/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.upgrade.v4_0_0;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMFieldLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.util.JournalConverter;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.Portal;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * @author Preston Crary
 */
public class JournalArticleDDMFieldsUpgradeProcess extends UpgradeProcess {

	public JournalArticleDDMFieldsUpgradeProcess(
		ClassNameLocalService classNameLocalService,
		DDMFieldLocalService ddmFieldLocalService,
		DDMStructureLocalService ddmStructureLocalService,
		FieldsToDDMFormValuesConverter fieldsToDDMFormValuesConverter,
		JournalConverter journalConverter, Portal portal) {

		_classNameLocalService = classNameLocalService;
		_ddmFieldLocalService = ddmFieldLocalService;
		_ddmStructureLocalService = ddmStructureLocalService;
		_fieldsToDDMFormValuesConverter = fieldsToDDMFormValuesConverter;
		_journalConverter = journalConverter;
		_portal = portal;
	}

	@Override
	protected void doUpgrade() throws Exception {
		long classNameId = _classNameLocalService.getClassNameId(
			JournalArticle.class);

		processConcurrently(
			"select id_, groupId, content, DDMStructureKey from " +
				"JournalArticle where ctCollectionId = 0",
			resultSet -> new Object[] {
				resultSet.getLong("id_"), resultSet.getLong("groupId"),
				resultSet.getString("content"),
				resultSet.getString("DDMStructureKey")
			},
			values -> {
				long id = (Long)values[0];
				long groupId = (Long)values[1];

				String content = (String)values[2];

				String ddmStructureKey = (String)values[3];

				DDMStructure ddmStructure =
					_ddmStructureLocalService.getStructure(
						_portal.getSiteGroupId(groupId), classNameId,
						ddmStructureKey, true);

				content = _convertFieldNames(content);

				DDMFormValues ddmFormValues =
					_fieldsToDDMFormValuesConverter.convert(
						ddmStructure,
						_journalConverter.getDDMFields(ddmStructure, content));

				_ddmFieldLocalService.updateDDMFormValues(
					ddmStructure.getStructureId(), id, ddmFormValues);
			},
			null);
	}
	private String _convertFieldNames(String content)
		throws Exception {
		Document document = DocumentBuilderFactory.newInstance()
			.newDocumentBuilder()
			.parse(new InputSource(new StringReader(content)));

		NodeList nodeList = document.getElementsByTagName("dynamic-element");

		for(int i = 0 ; i < nodeList.getLength() ; i++) {
			Node node = nodeList.item(i);

			NamedNodeMap namedNodeMap = node.getAttributes();

			Node nodeName = namedNodeMap.getNamedItem("name");

			String oldNameValue = nodeName.getTextContent();

			String newNameValue = oldNameValue.replaceAll(StringPool.MINUS, StringPool.BLANK);

			nodeName.setTextContent(newNameValue);
		}

		return _convertDocumentToString(document);
	}

	private String _convertDocumentToString(Document document)
		throws TransformerException {

		TransformerFactory transformerFactory = TransformerFactory.newInstance();

		Transformer transformer = transformerFactory.newTransformer();

		StringWriter writer = new StringWriter();

		transformer.transform(new DOMSource(document), new StreamResult(writer));

		return writer.getBuffer().toString();

	}

	@Override
	protected UpgradeStep[] getPostUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.dropColumns("JournalArticle", "content")
		};
	}

	private final ClassNameLocalService _classNameLocalService;
	private final DDMFieldLocalService _ddmFieldLocalService;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final FieldsToDDMFormValuesConverter
		_fieldsToDDMFormValuesConverter;
	private final JournalConverter _journalConverter;
	private final Portal _portal;

}