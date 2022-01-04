/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.kaleo.forms.internal.upgrade.v1_0_2;

import com.liferay.dynamic.data.mapping.service.DDMTemplateLinkLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLink;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Rafael Praxedes
 */
public class KaleoProcessTemplateLinkUpgradeProcess extends UpgradeProcess {

	public KaleoProcessTemplateLinkUpgradeProcess(
		ClassNameLocalService classNameLocalService,
		DDMTemplateLinkLocalService ddmTemplateLinkLocalService) {

		_classNameLocalService = classNameLocalService;
		_ddmTemplateLinkLocalService = ddmTemplateLinkLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_updateKaleoProcess();
		_updateKaleoProcessLink();
	}

	private void _updateKaleoProcess() throws Exception {
		long kaleoProcessClassNameId = _classNameLocalService.getClassNameId(
			KaleoProcess.class.getName());

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select KaleoProcess.kaleoProcessId, KaleoProcess.",
					"DDMTemplateId from KaleoProcess where (KaleoProcess.",
					"DDMTemplateId > 0) and not exists (select 1 from ",
					"DDMTemplateLink where (DDMTemplateLink.classPK = ",
					"KaleoProcess.kaleoProcessId) and (DDMTemplateLink.",
					"classNameId = ", kaleoProcessClassNameId, "))"));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long kaleoProcessLinkId = resultSet.getLong("kaleoProcessId");
				long ddmTemplateId = resultSet.getLong("DDMTemplateId");

				_ddmTemplateLinkLocalService.addTemplateLink(
					kaleoProcessClassNameId, kaleoProcessLinkId, ddmTemplateId);
			}
		}
	}

	private void _updateKaleoProcessLink() throws Exception {
		long kaleoProcessLinkClassNameId =
			_classNameLocalService.getClassNameId(
				KaleoProcessLink.class.getName());

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select KaleoProcessLink.kaleoProcessLinkId, ",
					"KaleoProcessLink.DDMTemplateId from KaleoProcessLink ",
					"where (KaleoProcessLink.DDMTemplateId > 0) and not ",
					"exists (select 1 from DDMTemplateLink where ",
					"(DDMTemplateLink.classPK = ",
					"KaleoProcessLink.kaleoProcessLinkId) and ",
					"(DDMTemplateLink.classNameId = ",
					kaleoProcessLinkClassNameId, "))"));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long kaleoProcessLinkId = resultSet.getLong(
					"kaleoProcessLinkId");
				long ddmTemplateId = resultSet.getLong("DDMTemplateId");

				_ddmTemplateLinkLocalService.addTemplateLink(
					kaleoProcessLinkClassNameId, kaleoProcessLinkId,
					ddmTemplateId);
			}
		}
	}

	private final ClassNameLocalService _classNameLocalService;
	private final DDMTemplateLinkLocalService _ddmTemplateLinkLocalService;

}