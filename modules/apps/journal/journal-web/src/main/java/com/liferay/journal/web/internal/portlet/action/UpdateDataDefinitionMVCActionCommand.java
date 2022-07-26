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

package com.liferay.journal.web.internal.portlet.action;

import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v2_0.DataLayout;
import com.liferay.data.engine.rest.resource.exception.DataDefinitionValidationException;
import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.persistence.JournalArticleUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseTransactionalMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL,
		"mvc.command.name=/journal/update_data_definition"
	},
	service = MVCActionCommand.class
)
public class UpdateDataDefinitionMVCActionCommand
	extends BaseTransactionalMVCActionCommand {

	@Override
	public boolean processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		try {
			return super.processAction(actionRequest, actionResponse);
		}
		catch (PortletException portletException) {
			if (portletException.getCause() instanceof
					DataDefinitionValidationException) {

				DataDefinitionValidationException
					dataDefinitionValidationException =
						(DataDefinitionValidationException)
							portletException.getCause();

				SessionErrors.add(
					actionRequest, dataDefinitionValidationException.getClass(),
					dataDefinitionValidationException);
			}
			else {
				throw portletException;
			}
		}

		return false;
	}

	@Override
	protected void doTransactionalCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		DataDefinitionResource.Builder dataDefinitionResourcedBuilder =
			_dataDefinitionResourceFactory.create();

		DataDefinitionResource dataDefinitionResource =
			dataDefinitionResourcedBuilder.user(
				themeDisplay.getUser()
			).build();

		long dataDefinitionId = ParamUtil.getLong(
			actionRequest, "dataDefinitionId");

		String dataDefinitionString = ParamUtil.getString(
			actionRequest, "dataDefinition");

		DataDefinition dataDefinition = DataDefinition.toDTO(
			dataDefinitionString);

		String structureKey = ParamUtil.getString(
			actionRequest, "structureKey");
		String dataLayout = ParamUtil.getString(actionRequest, "dataLayout");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");

		dataDefinition.setDataDefinitionKey(structureKey);
		dataDefinition.setDefaultDataLayout(DataLayout.toDTO(dataLayout));
		dataDefinition.setDescription(
			LocalizedValueUtil.toStringObjectMap(descriptionMap));

		_clearCache(dataDefinitionId);

		dataDefinitionResource.putDataDefinition(
			dataDefinitionId, dataDefinition);
	}

	private void _clearCache(long dataDefinitionId) {
		try {
			DDMStructure ddmStructure =
				_ddmStructureLocalService.getDDMStructure(dataDefinitionId);

			List<JournalArticle> articles =
				_journalArticleLocalService.getStructureArticles(
					new String[] {ddmStructure.getStructureKey()});

			for (JournalArticle journalArticle : articles) {
				JournalArticleUtil.clearCache(journalArticle);
			}
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpdateDataDefinitionMVCActionCommand.class);

	@Reference
	private DataDefinitionResource.Factory _dataDefinitionResourceFactory;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

}