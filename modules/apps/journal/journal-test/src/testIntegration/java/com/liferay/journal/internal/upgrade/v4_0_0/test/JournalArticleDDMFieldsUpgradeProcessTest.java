/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.upgrade.v4_0_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.io.InputStream;
import java.io.StringReader;

import java.lang.reflect.Method;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;

/**
 * @author Chaitanya Sammetla
 */
@RunWith(Arquillian.class)
public class JournalArticleDDMFieldsUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	@TestInfo("LPD-90349")
	public void testUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator, _CLASS_NAME);

		Class<?> upgradeProcessClass = upgradeProcess.getClass();

		Method convertFieldNamesMethod = upgradeProcessClass.getDeclaredMethod(
			"_convertFieldNames", String.class);

		convertFieldNamesMethod.setAccessible(true);

		ClassLoader classLoader =
			JournalArticleDDMFieldsUpgradeProcessTest.class.getClassLoader();

		try (InputStream inputStream = classLoader.getResourceAsStream(
				_JOURNAL_ARTICLE_CONTENT_WITH_REPEATABLE_FIELDS_XML)) {

			String result = (String)convertFieldNamesMethod.invoke(
				upgradeProcess, StringUtil.read(inputStream));

			DocumentBuilderFactory documentBuilderFactory =
				DocumentBuilderFactory.newInstance();

			DocumentBuilder documentBuilder =
				documentBuilderFactory.newDocumentBuilder();

			Document document = documentBuilder.parse(
				new InputSource(new StringReader(result)));

			NodeList nodeList = document.getElementsByTagName(
				"dynamic-element");

			Assert.assertEquals(2, nodeList.getLength());

			_assertElement(nodeList.item(0), "TextBox", "first-instance-id");
			_assertElement(nodeList.item(1), "TextBox", "second-instance-id");
		}
	}

	private void _assertElement(
		Node node, String expectedName, String expectedInstanceId) {

		NamedNodeMap attributes = node.getAttributes();

		Node nameNode = attributes.getNamedItem("name");

		Assert.assertEquals(expectedName, nameNode.getTextContent());

		Node instanceIdNode = attributes.getNamedItem("instance-id");

		Assert.assertEquals(
			expectedInstanceId, instanceIdNode.getTextContent());
	}

	private static final String _CLASS_NAME =
		"com.liferay.journal.internal.upgrade.v4_0_0." +
			"JournalArticleDDMFieldsUpgradeProcess";

	private static final String
		_JOURNAL_ARTICLE_CONTENT_WITH_REPEATABLE_FIELDS_XML =
			"com/liferay/journal/internal/upgrade/v4_0_0/test/dependencies" +
				"/journal_article_content_with_repeatable_fields.xml";

	@Inject(
		filter = "(&(component.name=com.liferay.journal.internal.upgrade.registry.JournalServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

}