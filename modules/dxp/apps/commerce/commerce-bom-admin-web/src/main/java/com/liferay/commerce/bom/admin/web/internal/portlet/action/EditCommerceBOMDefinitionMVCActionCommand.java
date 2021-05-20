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

package com.liferay.commerce.bom.admin.web.internal.portlet.action;

import com.liferay.commerce.bom.constants.CommerceBOMPortletKeys;
import com.liferay.commerce.bom.exception.NoSuchBOMDefinitionException;
import com.liferay.commerce.bom.model.CommerceBOMDefinition;
import com.liferay.commerce.bom.model.CommerceBOMFolder;
import com.liferay.commerce.bom.service.CommerceBOMDefinitionService;
import com.liferay.commerce.product.constants.CPAttachmentFileEntryConstants;
import com.liferay.commerce.product.exception.DuplicateCPAttachmentFileEntryException;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.service.CPAttachmentFileEntryService;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.Callable;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CommerceBOMPortletKeys.COMMERCE_BOM_ADMIN,
		"mvc.command.name=/commerce_bom_admin/edit_commerce_bom_definition"
	},
	service = MVCActionCommand.class
)
public class EditCommerceBOMDefinitionMVCActionCommand
	extends BaseMVCActionCommand {

	protected void deleteCommerceBOMDefinition(ActionRequest actionRequest)
		throws Exception {

		long commerceBOMDefinitionId = ParamUtil.getLong(
			actionRequest, "commerceBOMDefinitionId");

		_commerceBOMDefinitionService.deleteCommerceBOMDefinition(
			commerceBOMDefinitionId);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				Callable<CommerceBOMDefinition> commerceBOMDefinitionCallable =
					new CommerceBOMDefinitionCallable(actionRequest);

				CommerceBOMDefinition commerceBOMDefinition =
					TransactionInvokerUtil.invoke(
						_transactionConfig, commerceBOMDefinitionCallable);

				String redirect = getSaveAndContinueRedirect(
					actionRequest, commerceBOMDefinition);

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommerceBOMDefinition(actionRequest);
			}
		}
		catch (Throwable throwable) {
			if (throwable instanceof NoSuchBOMDefinitionException ||
				throwable instanceof PrincipalException) {

				SessionErrors.add(actionRequest, throwable.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (throwable instanceof
						DuplicateCPAttachmentFileEntryException ||
					 throwable instanceof NoSuchFileEntryException) {

				hideDefaultErrorMessage(actionRequest);

				SessionErrors.add(actionRequest, throwable.getClass());
			}
			else {
				_log.error(throwable, throwable);
			}
		}

		hideDefaultSuccessMessage(actionRequest);
	}

	protected String getSaveAndContinueRedirect(
			ActionRequest actionRequest,
			CommerceBOMDefinition commerceBOMDefinition)
		throws PortalException {

		PortletURL portletURL = PortletURLBuilder.create(
			PortletProviderUtil.getPortletURL(
				actionRequest, CommerceBOMFolder.class.getName(),
				PortletProvider.Action.MANAGE)
		).setMVCRenderCommandName(
			"/commerce_bom_admin/edit_commerce_bom_definition"
		).setParameter(
			"commerceBOMDefinitionId",
			commerceBOMDefinition.getCommerceBOMDefinitionId()
		).setParameter(
			"commerceBOMFolderId",
			commerceBOMDefinition.getCommerceBOMFolderId()
		).build();

		String backURL = ParamUtil.getString(actionRequest, "backURL");

		portletURL.setParameter("backURL", backURL);

		return portletURL.toString();
	}

	protected CommerceBOMDefinition updateCommerceBOMDefinition(
			ActionRequest actionRequest)
		throws Exception {

		long commerceBOMDefinitionId = ParamUtil.getLong(
			actionRequest, "commerceBOMDefinitionId");

		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");
		String name = ParamUtil.getString(actionRequest, "name");

		long classNameId = _classNameLocalService.getClassNameId(
			CommerceBOMDefinition.class.getName());

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPAttachmentFileEntry.class.getName(), actionRequest);

		Calendar calendar = CalendarFactoryUtil.getCalendar();

		calendar.setTime(new Date());

		int month = calendar.get(Calendar.MONTH);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		int year = calendar.get(Calendar.YEAR);
		int hour = calendar.get(Calendar.HOUR);
		int minute = calendar.get(Calendar.MINUTE);
		int amPm = calendar.get(Calendar.AM_PM);

		if (amPm == Calendar.PM) {
			hour += 12;
		}

		CommerceBOMDefinition commerceBOMDefinition = null;

		if (commerceBOMDefinitionId > 0) {
			CommerceBOMDefinition oldCommerceBOMDefinition =
				_commerceBOMDefinitionService.getCommerceBOMDefinition(
					commerceBOMDefinitionId);

			CPAttachmentFileEntry cpAttachmentFileEntry =
				oldCommerceBOMDefinition.fetchCPAttachmentFileEntry();

			if (cpAttachmentFileEntry == null) {
				cpAttachmentFileEntry =
					_cpAttachmentFileEntryService.addCPAttachmentFileEntry(
						serviceContext.getUserId(),
						serviceContext.getScopeGroupId(), classNameId,
						commerceBOMDefinitionId, fileEntryId, month, dayOfMonth,
						year, hour, minute, 0, 0, 0, 0, 0, true,
						Collections.singletonMap(
							serviceContext.getLocale(), name),
						null, 0D, CPAttachmentFileEntryConstants.TYPE_IMAGE,
						serviceContext);
			}
			else {
				FileEntry fileEntry = cpAttachmentFileEntry.getFileEntry();

				if (fileEntry.getFileEntryId() != fileEntryId) {
					cpAttachmentFileEntry =
						_cpAttachmentFileEntryService.
							updateCPAttachmentFileEntry(
								oldCommerceBOMDefinition.
									getCPAttachmentFileEntryId(),
								fileEntryId, month, dayOfMonth, year, hour,
								minute, 0, 0, 0, 0, 0, true,
								Collections.singletonMap(
									serviceContext.getLocale(), name),
								null, 0D,
								CPAttachmentFileEntryConstants.TYPE_IMAGE,
								serviceContext);
				}
			}

			commerceBOMDefinition =
				_commerceBOMDefinitionService.updateCommerceBOMDefinition(
					commerceBOMDefinitionId,
					cpAttachmentFileEntry.getCPAttachmentFileEntryId(), name);
		}
		else {
			long commerceBOMFolderId = ParamUtil.getLong(
				actionRequest, "commerceBOMFolderId");

			long cpAttachmentFileEntryId = 0;

			if (fileEntryId > 0) {
				CPAttachmentFileEntry cpAttachmentFileEntry =
					_cpAttachmentFileEntryService.addCPAttachmentFileEntry(
						serviceContext.getUserId(),
						serviceContext.getScopeGroupId(), classNameId,
						commerceBOMDefinitionId, fileEntryId, month, dayOfMonth,
						year, hour, minute, 0, 0, 0, 0, 0, true,
						Collections.singletonMap(
							serviceContext.getLocale(), name),
						null, 0D, CPAttachmentFileEntryConstants.TYPE_IMAGE,
						serviceContext);

				cpAttachmentFileEntryId =
					cpAttachmentFileEntry.getCPAttachmentFileEntryId();
			}

			commerceBOMDefinition =
				_commerceBOMDefinitionService.addCommerceBOMDefinition(
					serviceContext.getUserId(), commerceBOMFolderId,
					cpAttachmentFileEntryId, name, name);
		}

		return commerceBOMDefinition;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditCommerceBOMDefinitionMVCActionCommand.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CommerceBOMDefinitionService _commerceBOMDefinitionService;

	@Reference
	private CPAttachmentFileEntryService _cpAttachmentFileEntryService;

	private class CommerceBOMDefinitionCallable
		implements Callable<CommerceBOMDefinition> {

		@Override
		public CommerceBOMDefinition call() throws Exception {
			return updateCommerceBOMDefinition(_actionRequest);
		}

		private CommerceBOMDefinitionCallable(ActionRequest actionRequest) {
			_actionRequest = actionRequest;
		}

		private final ActionRequest _actionRequest;

	}

}