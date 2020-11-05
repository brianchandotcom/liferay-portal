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

package com.liferay.multi.factor.authentication.web.internal.portlet.action;

import com.liferay.multi.factor.authentication.spi.checker.setup.SetupMFAChecker;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.users.admin.constants.UsersAdminPortletKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 * @author Marta Medio
 */
@Component(
	property = {
		"javax.portlet.name=" + UsersAdminPortletKeys.MY_ACCOUNT,
		"javax.portlet.name=" + UsersAdminPortletKeys.USERS_ADMIN,
		"mvc.command.name=/my_account/setup_mfa"
	},
	service = MVCActionCommand.class
)
public class MFAUserAccountSetupMVCActionCommand extends BaseMVCActionCommand {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_mfaCheckerServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, SetupMFAChecker.class, "(service.id=*)",
				new PropertyServiceReferenceMapper<>("service.id"));
	}

	@Deactivate
	protected void deactivate() {
		_mfaCheckerServiceTrackerMap.close();
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long setupMFACheckerServiceId = ParamUtil.getLong(
			actionRequest, "setupMFACheckerServiceId");

		SetupMFAChecker setupMFAChecker =
			_mfaCheckerServiceTrackerMap.getService(setupMFACheckerServiceId);

		if (setupMFAChecker == null) {
			_log.error(
				"Unable to get setup MFA checker " + setupMFACheckerServiceId);

			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay == null) {
			throw new PrincipalException();
		}

		final long userId = themeDisplay.getUserId();

		if (ParamUtil.getBoolean(actionRequest, "mfaRemoveExistingSetup")) {
			setupMFAChecker.removeExistingSetup(userId);
		}
		else if (!setupMFAChecker.setUp(
					_portal.getHttpServletRequest(actionRequest), userId)) {

			SessionErrors.add(actionRequest, "userAccountSetupFailed");
		}

		String redirect = _portal.escapeRedirect(
			ParamUtil.getString(actionRequest, "redirect"));

		if (Validator.isBlank(redirect)) {
			redirect = themeDisplay.getPortalURL();
		}

		actionResponse.sendRedirect(redirect);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MFAUserAccountSetupMVCActionCommand.class);

	private ServiceTrackerMap<Long, SetupMFAChecker>
		_mfaCheckerServiceTrackerMap;

	@Reference
	private Portal _portal;

}