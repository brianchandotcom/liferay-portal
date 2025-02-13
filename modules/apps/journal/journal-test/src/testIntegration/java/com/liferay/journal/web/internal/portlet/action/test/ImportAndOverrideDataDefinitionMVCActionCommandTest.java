/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinitionField;
import com.liferay.data.engine.rest.test.util.DataDefinitionTestUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.test.rule.Inject;

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
	public void testProcessActionWithDataDefinitionFromPreviousVersion()
		throws Exception {

		DataDefinition dataDefinition =
			DataDefinitionTestUtil.addDataDefinition(
				"journal", dataDefinitionResourceFactory, group.getGroupId(),
				_read("previous_version_valid_data_definition.json"),
				TestPropsValues.getUser());

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			createMockLiferayPortletActionRequest(
				"previous_version_valid_data_definition.json",
				"Imported Structure", dataDefinition.getId());

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

		dataDefinition = getImportedDataDefinition();

		DataDefinitionField[] dataDefinitionFields =
			dataDefinition.getDataDefinitionFields();

		String previousTextFieldName = "Text1";

		Assert.assertEquals(
			previousTextFieldName, dataDefinitionFields[0].getName());
	}

	private String _read(String fileName) throws Exception {
		return new String(
			FileUtil.getBytes(getClass(), "dependencies/" + fileName));
	}

	@Inject(
		filter = "mvc.command.name=/journal/import_and_override_data_definition"
	)
	private MVCActionCommand _mvcActionCommand;

}