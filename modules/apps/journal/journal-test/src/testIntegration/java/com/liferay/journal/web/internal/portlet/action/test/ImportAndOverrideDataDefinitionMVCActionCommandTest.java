/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinitionField;
import com.liferay.data.engine.rest.test.util.DataDefinitionTestUtil;
import com.liferay.dynamic.data.mapping.model.DDMField;
import com.liferay.dynamic.data.mapping.service.DDMFieldLocalService;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.Inject;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mariano Álvaro Sáiz
 */
@RunWith(Arquillian.class)
public class ImportAndOverrideDataDefinitionMVCActionCommandTest
	extends BaseDataDefinitionMVCActionCommandTestCase {

	public MVCActionCommand getMVCActionCommand() {
		return _mvcActionCommand;
	}

	@Test
	public void testProcessAction() throws Exception {
		DataDefinition dataDefinition =
			DataDefinitionTestUtil.addDataDefinition(
				"journal", dataDefinitionResourceFactory, group.getGroupId(),
				_read("previous_version_valid_data_definition.json"),
				TestPropsValues.getUser());

		_processAction(
			dataDefinition.getId(),
			"previous_version_valid_data_definition.json",
			"Imported Structure");

		dataDefinition = getImportedDataDefinition();

		DataDefinitionField[] dataDefinitionFields =
			dataDefinition.getDataDefinitionFields();

		String previousTextFieldName = "Text1";

		Assert.assertTrue(
			StringUtil.startsWith(
				dataDefinitionFields[0].getName(), previousTextFieldName));

		String suffix = StringUtil.removeSubstring(
			dataDefinitionFields[0].getName(), previousTextFieldName);

		Assert.assertTrue(Validator.isNumber(suffix));

		dataDefinition = DataDefinitionTestUtil.addDataDefinition(
			"journal", dataDefinitionResourceFactory, group.getGroupId(),
			_read("legacy_data_definition.json"), TestPropsValues.getUser());

		JournalArticle journalArticle1 =
			JournalTestUtil.addArticleWithXMLContent(
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				JournalArticleConstants.CLASS_NAME_ID_DEFAULT, 0,
				_read("journal_article_content.xml"),
				dataDefinition.getDataDefinitionKey(), null, LocaleUtil.SPAIN,
				null,
				ServiceContextTestUtil.getServiceContext(
					group.getCompanyId(), group.getGroupId(),
					TestPropsValues.getUserId()));

		List<DDMField> ddmFields = _ddmFieldLocalService.getDDMFields(
			journalArticle1.getId(), "CopyOfCajaDeTexto9fap");

		Assert.assertEquals(ddmFields.toString(), 2, ddmFields.size());

		String journalArticle1Content = journalArticle1.getContent();

		_processAction(
			dataDefinition.getId(), "legacy_data_definition.json", "Simple");

		JournalArticle journalArticle2 = _journalArticleLocalService.getArticle(
			journalArticle1.getId());

		ddmFields = _ddmFieldLocalService.getDDMFields(
			journalArticle1.getId(), "CopyOfCajaDeTexto9fap");

		Assert.assertEquals(ddmFields.toString(), 0, ddmFields.size());

		String journalArticle2Content = journalArticle2.getContent();

		Assert.assertNotEquals(journalArticle1Content, journalArticle2Content);

		Assert.assertTrue(journalArticle2Content.contains("CCC1"));
		Assert.assertTrue(journalArticle2Content.contains("CCC2"));
	}

	private void _processAction(
			Long dataDefinitionId, String fileName, String name)
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			createMockLiferayPortletActionRequest(
				fileName, name, dataDefinitionId);

		setUpUploadPortletRequest(mockLiferayPortletActionRequest);

		_mvcActionCommand.processAction(
			mockLiferayPortletActionRequest,
			new MockLiferayPortletActionResponse());

		Assert.assertNull(
			SessionMessages.get(
				mockLiferayPortletActionRequest,
				portal.getPortletId(mockLiferayPortletActionRequest) +
					SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE));
		Assert.assertNull(
			SessionErrors.get(
				mockLiferayPortletActionRequest,
				"importDataDefinitionErrorMessage"));
	}

	private String _read(String fileName) throws Exception {
		return new String(
			FileUtil.getBytes(getClass(), "dependencies/" + fileName));
	}

	@Inject
	private DDMFieldLocalService _ddmFieldLocalService;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	@Inject(
		filter = "mvc.command.name=/journal/import_and_override_data_definition"
	)
	private MVCActionCommand _mvcActionCommand;

}