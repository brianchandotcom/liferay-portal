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

package com.liferay.akismet.internal.portlet.action;

import com.liferay.akismet.client.AkismetClient;
import com.liferay.message.boards.constants.MBPortletKeys;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.HashMap;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jamie Sammons
 */
@Component(
	enabled = false,
	property = {
		"javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS,
		"javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS_ADMIN,
		"mvc.command.name=/message_boards/edit_message",
		"service.ranking:Integer=100"
	},
	service = MVCActionCommand.class
)
public class AkismetEditMessageMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (cmd.equals("updateStatus")) {
			try {
				_updateStatus(actionRequest);

				String redirect = _portal.escapeRedirect(
					ParamUtil.getString(actionRequest, "redirect"));

				actionResponse.sendRedirect(redirect);
			}
			catch (PrincipalException principalException) {
				throw principalException;
			}
			catch (Exception exception) {
				SessionErrors.add(actionRequest, exception.getClass());
			}
		}

		mvcActionCommand.processAction(actionRequest, actionResponse);
	}

	@Reference(
		target = "(component.name=com.liferay.message.boards.web.internal.portlet.action.EditMessageMVCActionCommand)"
	)
	protected MVCActionCommand mvcActionCommand;

	private void _checkPermission(long scopeGroupId) throws PortalException {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (!permissionChecker.hasPermission(
				scopeGroupId, "com.liferay.message.boards", scopeGroupId,
				ActionKeys.BAN_USER)) {

			throw new PrincipalException();
		}
	}

	private void _updateStatus(ActionRequest actionRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_checkPermission(themeDisplay.getScopeGroupId());

		long messageId = ParamUtil.getLong(actionRequest, "messageId");

		boolean spam = ParamUtil.getBoolean(actionRequest, "spam");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		if (spam) {
			MBMessage message = _mbMessageLocalService.updateStatus(
				themeDisplay.getUserId(), messageId,
				WorkflowConstants.STATUS_DENIED, serviceContext,
				new HashMap<>());

			List<MBMessage> threadMessages =
				_mbMessageLocalService.getThreadMessages(
					message.getThreadId(), 0);

			for (MBMessage threadMessage : threadMessages) {
				if (threadMessage.getParentMessageId() == messageId) {
					threadMessage.setParentMessageId(
						message.getRootMessageId());
				}
			}

			_akismetClient.submitSpam(message);
		}
		else {
			MBMessage message = _mbMessageLocalService.updateStatus(
				themeDisplay.getUserId(), messageId,
				WorkflowConstants.STATUS_APPROVED, serviceContext,
				new HashMap<>());

			_akismetClient.submitHam(message);
		}
	}

	@Reference
	private AkismetClient _akismetClient;

	@Reference
	private MBMessageLocalService _mbMessageLocalService;

	@Reference
	private Portal _portal;

}