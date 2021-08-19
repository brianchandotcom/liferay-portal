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

package com.liferay.commerce.shop.by.diagram.admin.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.exception.NoSuchCPAttachmentFileEntryException;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPAttachmentFileEntryService;
import com.liferay.commerce.shop.by.diagram.constants.CPDefinitionDiagramSettingsConstants;
import com.liferay.commerce.shop.by.diagram.exception.NoSuchCPDefinitionDiagramEntryException;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting;
import com.liferay.commerce.shop.by.diagram.service.CPDefinitionDiagramSettingService;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.Calendar;
import java.util.Collections;
import java.util.concurrent.Callable;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.CP_DEFINITIONS,
		"mvc.command.name=/cp_definitions/edit_cp_definition_diagram_setting"
	},
	service = MVCActionCommand.class
)
public class EditCPDefinitionDiagramSettingMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals("updateCPDefinitionDiagramSetting")) {
				Callable<Object> cpDefinitionDiagramSettingCallable =
					new CPDefinitionDiagramSettingCallable(actionRequest);

				TransactionInvokerUtil.invoke(
					_transactionConfig, cpDefinitionDiagramSettingCallable);
			}
		}
		catch (Throwable throwable) {
			if (throwable instanceof NoSuchCPAttachmentFileEntryException ||
				throwable instanceof NoSuchCPDefinitionDiagramEntryException ||
				throwable instanceof NoSuchFileEntryException ||
				throwable instanceof PrincipalException) {

				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, throwable.getClass());

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else {
				_log.error(throwable, throwable);

				throw new Exception(throwable);
			}
		}
	}

	private CPAttachmentFileEntry _addOrUpdateCPAttachmentFileEntry(
			ActionRequest actionRequest,
			CPDefinitionDiagramSetting cpDefinitionDiagramSetting,
			long cpDefinitionId)
		throws Exception {

		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPAttachmentFileEntry.class.getName(), actionRequest);

		Calendar displayCalendar = CalendarFactoryUtil.getCalendar(
			serviceContext.getTimeZone());
		Calendar expirationCalendar = CalendarFactoryUtil.getCalendar(
			serviceContext.getTimeZone());

		if (cpDefinitionDiagramSetting == null) {
			return _cpAttachmentFileEntryService.addCPAttachmentFileEntry(
				serviceContext.getScopeGroupId(),
				_portal.getClassNameId(CPDefinition.class), cpDefinitionId,
				fileEntryId, false, null, displayCalendar.get(Calendar.MONTH),
				displayCalendar.get(Calendar.DAY_OF_MONTH),
				displayCalendar.get(Calendar.YEAR),
				displayCalendar.get(Calendar.HOUR),
				displayCalendar.get(Calendar.MINUTE),
				expirationCalendar.get(Calendar.MONTH),
				expirationCalendar.get(Calendar.DAY_OF_MONTH),
				expirationCalendar.get(Calendar.YEAR),
				expirationCalendar.get(Calendar.HOUR),
				expirationCalendar.get(Calendar.MINUTE), true,
				Collections.emptyMap(), null, 0D,
				CPDefinitionDiagramSettingsConstants.TYPE_DIAGRAM,
				serviceContext);
		}

		CPAttachmentFileEntry cpAttachmentFileEntry =
			cpDefinitionDiagramSetting.getCPAttachmentFileEntry();

		if (cpAttachmentFileEntry.getFileEntryId() == fileEntryId) {
			return cpAttachmentFileEntry;
		}

		return _cpAttachmentFileEntryService.updateCPAttachmentFileEntry(
			cpAttachmentFileEntry.getCPAttachmentFileEntryId(), fileEntryId,
			false, null, displayCalendar.get(Calendar.MONTH),
			displayCalendar.get(Calendar.DAY_OF_MONTH),
			displayCalendar.get(Calendar.YEAR),
			displayCalendar.get(Calendar.HOUR),
			displayCalendar.get(Calendar.MINUTE),
			expirationCalendar.get(Calendar.MONTH),
			expirationCalendar.get(Calendar.DAY_OF_MONTH),
			expirationCalendar.get(Calendar.YEAR),
			expirationCalendar.get(Calendar.HOUR),
			expirationCalendar.get(Calendar.MINUTE), true,
			Collections.emptyMap(), null, 0D,
			CPDefinitionDiagramSettingsConstants.TYPE_DIAGRAM, serviceContext);
	}

	private CPDefinitionDiagramSetting _updateCPDefinitionDiagramSetting(
			ActionRequest actionRequest)
		throws Exception {

		String type = ParamUtil.getString(actionRequest, "type");

		long cpDefinitionId = ParamUtil.getLong(
			actionRequest, "cpDefinitionId");

		CPDefinitionDiagramSetting cpDefinitionDiagramSetting =
			_cpDefinitionDiagramSettingService.
				fetchCPDefinitionDiagramSettingByCPDefinitionId(cpDefinitionId);

		CPAttachmentFileEntry cpAttachmentFileEntry =
			_addOrUpdateCPAttachmentFileEntry(
				actionRequest, cpDefinitionDiagramSetting, cpDefinitionId);

		if (cpDefinitionDiagramSetting == null) {
			return _cpDefinitionDiagramSettingService.
				addCPDefinitionDiagramSetting(
					cpDefinitionId,
					cpAttachmentFileEntry.getCPAttachmentFileEntryId(), null,
					0D, type);
		}

		return _cpDefinitionDiagramSettingService.
			updateCPDefinitionDiagramSetting(
				cpDefinitionDiagramSetting.getCPDefinitionDiagramSettingId(),
				cpAttachmentFileEntry.getCPAttachmentFileEntryId(), null, 0D,
				type);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditCPDefinitionDiagramSettingMVCActionCommand.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private CPAttachmentFileEntryService _cpAttachmentFileEntryService;

	@Reference
	private CPDefinitionDiagramSettingService
		_cpDefinitionDiagramSettingService;

	@Reference
	private Portal _portal;

	private class CPDefinitionDiagramSettingCallable
		implements Callable<Object> {

		@Override
		public CPDefinitionDiagramSetting call() throws Exception {
			return _updateCPDefinitionDiagramSetting(_actionRequest);
		}

		private CPDefinitionDiagramSettingCallable(
			ActionRequest actionRequest) {

			_actionRequest = actionRequest;
		}

		private final ActionRequest _actionRequest;

	}

}