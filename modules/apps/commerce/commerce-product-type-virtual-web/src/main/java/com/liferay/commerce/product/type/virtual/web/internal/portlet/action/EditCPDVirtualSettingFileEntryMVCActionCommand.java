/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.type.virtual.exception.CPDefinitionVirtualSettingException;
import com.liferay.commerce.product.type.virtual.exception.CPDefinitionVirtualSettingFileEntryIdException;
import com.liferay.commerce.product.type.virtual.exception.NoSuchCPDefinitionVirtualSettingException;
import com.liferay.commerce.product.type.virtual.model.CPDVirtualSettingFileEntry;
import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;
import com.liferay.commerce.product.type.virtual.service.CPDVirtualSettingFileEntryService;
import com.liferay.commerce.product.type.virtual.service.CPDefinitionVirtualSettingService;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 */
@Component(
	property = {
		"javax.portlet.name=" + CPPortletKeys.CP_DEFINITIONS,
		"mvc.command.name=/cp_definitions/edit_cpd_virtual_setting_file_entry"
	},
	service = MVCActionCommand.class
)
public class EditCPDVirtualSettingFileEntryMVCActionCommand
	extends BaseMVCActionCommand {

	protected void _deleteCPDVirtualSettingFileEntry(
			ActionRequest actionRequest)
		throws Exception {

		long cpdVirtualSettingFileEntryId = ParamUtil.getLong(
			actionRequest, "cpdVirtualSettingFileEntryId");

		CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry =
			_cpdVirtualSettingFileEntryService.getCPDVirtualSettingFileEntry(
				cpdVirtualSettingFileEntryId);

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			cpdVirtualSettingFileEntry.getCPDefinitionVirtualSetting();

		_cpdVirtualSettingFileEntryService.deleteCPDVirtualSettingFileEntry(
			cpDefinitionVirtualSetting.getClassName(),
			cpDefinitionVirtualSetting.getClassPK(),
			cpdVirtualSettingFileEntryId);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				_updateCPDVirtualSettingFileEntry(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				_deleteCPDVirtualSettingFileEntry(actionRequest);
			}
		}
		catch (Exception exception) {
			if (exception instanceof CPDefinitionVirtualSettingException ||
				exception instanceof
					CPDefinitionVirtualSettingFileEntryIdException ||
				exception instanceof
					NoSuchCPDefinitionVirtualSettingException ||
				exception instanceof PrincipalException) {

				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, exception.getClass());
			}
			else {
				throw exception;
			}
		}

		String className = ParamUtil.getString(actionRequest, "className");

		if (className.equals(CPInstance.class.getName())) {
			sendRedirect(
				actionRequest, actionResponse,
				PortletURLBuilder.create(
					PortletProviderUtil.getPortletURL(
						actionRequest, CPDefinition.class.getName(),
						PortletProvider.Action.EDIT)
				).setMVCRenderCommandName(
					"/cp_definitions/edit_cp_instance"
				).setParameter(
					"cpDefinitionId",
					ParamUtil.getLong(actionRequest, "cpDefinitionId")
				).setParameter(
					"cpInstanceId", ParamUtil.getLong(actionRequest, "classPK")
				).setParameter(
					"override", ParamUtil.getBoolean(actionRequest, "override")
				).setParameter(
					"screenNavigationCategoryKey", "virtual-settings"
				).setWindowState(
					LiferayWindowState.POP_UP
				).buildString());
		}
		else {
			String redirect = ParamUtil.getString(actionRequest, "redirect");

			sendRedirect(actionRequest, actionResponse, redirect);
		}
	}

	private CPDVirtualSettingFileEntry _updateCPDVirtualSettingFileEntry(
			ActionRequest actionRequest)
		throws Exception {

		long cpdVirtualSettingFileEntryId = ParamUtil.getLong(
			actionRequest, "cpdVirtualSettingFileEntryId");
		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");
		String url = ParamUtil.getString(actionRequest, "url");
		String version = ParamUtil.getString(actionRequest, "version");

		if (cpdVirtualSettingFileEntryId > 0) {
			return _cpdVirtualSettingFileEntryService.
				updateCPDefinitionVirtualSetting(
					cpdVirtualSettingFileEntryId, fileEntryId, url, version);
		}

		String className = ParamUtil.getString(actionRequest, "className");
		long classPK = ParamUtil.getLong(actionRequest, "classPK");

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			_cpDefinitionVirtualSettingService.fetchCPDefinitionVirtualSetting(
				className, classPK);

		return _cpdVirtualSettingFileEntryService.addCPDefinitionVirtualSetting(
			cpDefinitionVirtualSetting.getGroupId(), className, classPK,
			cpDefinitionVirtualSetting.getCPDefinitionVirtualSettingId(),
			fileEntryId, url, version);
	}

	@Reference
	private CPDefinitionVirtualSettingService
		_cpDefinitionVirtualSettingService;

	@Reference
	private CPDVirtualSettingFileEntryService
		_cpdVirtualSettingFileEntryService;

}