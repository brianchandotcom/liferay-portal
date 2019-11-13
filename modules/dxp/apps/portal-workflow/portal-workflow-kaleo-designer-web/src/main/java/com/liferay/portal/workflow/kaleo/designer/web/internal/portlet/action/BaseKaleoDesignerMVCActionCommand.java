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
import com.liferay.portal.kernel.uuid.PortalUUID;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.workflow.kaleo.definition.parser.WorkflowModelParser;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionLocalService;
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
		catch (WorkflowException we) {
			Throwable rootThrowable = getRootThrowable(we);

			if (_log.isWarnEnabled()) {
				_log.warn(rootThrowable, rootThrowable);
			}

			hideDefaultErrorMessage(actionRequest);

			if (rootThrowable instanceof IllegalArgumentException ||
				rootThrowable instanceof NoSuchRoleException ||
				rootThrowable instanceof
					PrincipalException.MustBeCompanyAdmin ||
				rootThrowable instanceof PrincipalException.MustBeOmniadmin) {

				SessionErrors.add(
					actionRequest, rootThrowable.getClass(), rootThrowable);
			}
			else {
				SessionErrors.add(actionRequest, we.getClass(), we);
			}

			return false;
		}
		catch (PortletException pe) {
			_log.error(pe, pe);

			throw pe;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new PortletException(e);
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

		return ResourceBundleUtil.getBundle(
			themeDisplay.getLocale(), getClass());
	}

	protected Throwable getRootThrowable(Throwable throwable) {
		if (throwable.getCause() == null) {
			return throwable;
		}

		return getRootThrowable(throwable.getCause());
	}

	protected abstract String getSuccessMessage(ActionRequest actionRequest);

	protected String getTitle(Map<Locale, String> titleMap)
		throws WorkflowException {

		if (titleMap == null) {
			return null;
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

		portletURL.setParameter(
			"mvcPath", "/designer/edit_kaleo_definition_version.jsp");

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
	protected KaleoDefinitionLocalService kaleoDefinitionLocalService;

	@Reference
	protected KaleoDefinitionVersionLocalService
		kaleoDefinitionVersionLocalService;

	@Reference
	protected Portal portal;

	@Reference
	protected PortalUUID portalUUID;

	@Reference
	protected WorkflowDefinitionManager workflowDefinitionManager;

	@Reference
	protected WorkflowModelParser workflowModelParser;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseKaleoDesignerMVCActionCommand.class);

}