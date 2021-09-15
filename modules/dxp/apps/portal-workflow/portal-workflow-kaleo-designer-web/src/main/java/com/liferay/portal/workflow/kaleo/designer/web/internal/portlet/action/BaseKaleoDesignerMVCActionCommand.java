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

package com.liferay.portal.workflow.kaleo.designer.web.internal.portlet.action;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchRoleException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.workflow.kaleo.designer.web.internal.util.KaleoDesignerUtil;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
public abstract class BaseKaleoDesignerMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	public boolean processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		try {
			doProcessAction(actionRequest, actionResponse);

			addSuccessMessage(actionRequest, actionResponse);

			setCloseRedirect(actionRequest);

			sendRedirect(actionRequest, actionResponse);

			return SessionErrors.isEmpty(actionRequest);
		}
		catch (WorkflowException workflowException) {
			Throwable rootThrowable = getRootThrowable(workflowException);

			if (_log.isWarnEnabled()) {
				_log.warn(rootThrowable, rootThrowable);
			}

			hideDefaultErrorMessage(actionRequest);

			SessionErrors.add(
				actionRequest, rootThrowable.getClass(), rootThrowable);

			return false;
		}
		catch (PortletException portletException) {
			_log.error(portletException, portletException);

			throw portletException;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new PortletException(exception);
		}
	}

	@Override
	protected void addSuccessMessage(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		SessionMessages.add(
			actionRequest, "requestProcessed",
			getSuccessMessage(actionRequest));
	}

	protected ResourceBundle getResourceBundle(ActionRequest actionRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return ResourceBundleUtil.getModuleAndPortalResourceBundle(
			themeDisplay.getLocale(), getClass());
	}

	protected Throwable getRootThrowable(Throwable throwable) {
		if ((throwable.getCause() == null) ||
			(!(throwable.getCause() instanceof IllegalArgumentException) &&
			 !(throwable.getCause() instanceof NoSuchRoleException) &&
			 !(throwable.getCause() instanceof
				 PrincipalException.MustBeCompanyAdmin) &&
			 !(throwable.getCause() instanceof
				 PrincipalException.MustBeOmniadmin) &&
			 !(throwable.getCause() instanceof WorkflowException))) {

			return throwable;
		}

		return getRootThrowable(throwable.getCause());
	}

	protected String getSuccessMessage(ActionRequest actionRequest) {
		return LanguageUtil.get(
			getResourceBundle(actionRequest), "workflow-updated-successfully");
	}

	protected String getTitle(
			ActionRequest actionRequest, Map<Locale, String> titleMap)
		throws WorkflowException {

		if (titleMap == null) {
			return StringPool.BLANK;
		}

		String value = StringPool.BLANK;

		for (Locale locale : LanguageUtil.getAvailableLocales()) {
			String languageId = LocaleUtil.toLanguageId(locale);
			String title = titleMap.get(locale);

			if (Validator.isNotNull(title)) {
				value = LocalizationUtil.updateLocalization(
					value, "Title", title, languageId);
			}
			else {
				value = LocalizationUtil.removeLocalization(
					value, "Title", languageId);
			}
		}

		return value;
	}

	protected void setCloseRedirect(ActionRequest actionRequest) {
		String closeRedirect = ParamUtil.getString(
			actionRequest, "closeRedirect");

		if (Validator.isNull(closeRedirect)) {
			return;
		}

		SessionMessages.add(
			actionRequest,
			portal.getPortletId(actionRequest) +
				SessionMessages.KEY_SUFFIX_CLOSE_REDIRECT,
			closeRedirect);
	}

	protected void setRedirectAttribute(
			ActionRequest actionRequest,
			KaleoDefinitionVersion kaleoDefinitionVersion)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		LiferayPortletURL portletURL = PortletURLFactoryUtil.create(
			actionRequest, themeDisplay.getPpid(), PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", KaleoDesignerUtil.getEditJspPath());

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		portletURL.setParameter("redirect", redirect, false);

		String closeRedirect = ParamUtil.getString(
			actionRequest, "closeRedirect");

		portletURL.setParameter("closeRedirect", closeRedirect, false);

		portletURL.setParameter(
			"name", kaleoDefinitionVersion.getName(), false);
		portletURL.setParameter(
			"draftVersion", kaleoDefinitionVersion.getVersion(), false);

		portletURL.setWindowState(actionRequest.getWindowState());

		actionRequest.setAttribute(WebKeys.REDIRECT, portletURL.toString());
	}

	@Reference
	protected KaleoDefinitionVersionLocalService
		kaleoDefinitionVersionLocalService;

	@Reference
	protected Portal portal;

	@Reference
	protected WorkflowDefinitionManager workflowDefinitionManager;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseKaleoDesignerMVCActionCommand.class);

}