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

package com.liferay.multi.factor.authentication.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.multi.factor.authentication.spi.checker.setup.SetupMFAChecker;
import com.liferay.multi.factor.authentication.web.internal.constants.MFASetupUserAccountScreenNavigationConstants;
import com.liferay.multi.factor.authentication.web.internal.constants.MFAWebKeys;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.users.admin.constants.UserScreenNavigationEntryConstants;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;

/**
 * @author Marta Medio
 */
public class MFASetupUserAccountScreenNavigationEntry
	implements ScreenNavigationEntry<User> {

	public MFASetupUserAccountScreenNavigationEntry(
		ServiceReference<Object> serviceReference,
		ServletContext servletContext, SetupMFAChecker setupMFAChecker) {

		_serviceReference = serviceReference;
		_servletContext = servletContext;
		_setupMFAChecker = setupMFAChecker;

		_bundle = _serviceReference.getBundle();
		_companyId = GetterUtil.getLong(
			serviceReference.getProperty("companyId"));

		Class<? extends SetupMFAChecker> clazz = _setupMFAChecker.getClass();

		_resourceBundleKey = clazz.getName();
	}

	@Override
	public String getCategoryKey() {
		return MFASetupUserAccountScreenNavigationConstants.CATEGORY_KEY_MFA;
	}

	@Override
	public String getEntryKey() {
		Class<? extends SetupMFAChecker> clazz = _setupMFAChecker.getClass();

		return clazz.getName();
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundleLoader resourceBundleLoader =
			ResourceBundleLoaderUtil.
				getResourceBundleLoaderByBundleSymbolicName(
					_bundle.getSymbolicName());

		if (resourceBundleLoader == null) {
			resourceBundleLoader =
				ResourceBundleLoaderUtil.getPortalResourceBundleLoader();
		}

		return GetterUtil.getString(
			ResourceBundleUtil.getString(
				resourceBundleLoader.loadResourceBundle(locale),
				_resourceBundleKey),
			_resourceBundleKey);
	}

	@Override
	public String getScreenNavigationKey() {
		return UserScreenNavigationEntryConstants.SCREEN_NAVIGATION_KEY_USERS;
	}

	@Override
	public boolean isVisible(User user, User context) {
		if (_companyId == CompanyThreadLocal.getCompanyId()) {
			return true;
		}

		return false;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		httpServletRequest.setAttribute(
			MFAWebKeys.MFA_USER_ACCOUNT_LABEL,
			getLabel(httpServletRequest.getLocale()));
		httpServletRequest.setAttribute(
			MFAWebKeys.SETUP_MFA_CHECKER_SERVICE_ID,
			GetterUtil.getLong(_serviceReference.getProperty("service.id")));

		httpServletRequest.setAttribute(
			SetupMFAChecker.class.getName(), _setupMFAChecker);

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(
				"/my_account/setup_user_account.jsp");

		try {
			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (ServletException servletException) {
			throw new IOException(
				"Unable to render /my_account/setup_user_account.jsp",
				servletException);
		}
	}

	private final Bundle _bundle;
	private final long _companyId;
	private final String _resourceBundleKey;
	private final ServiceReference<Object> _serviceReference;
	private final ServletContext _servletContext;
	private final SetupMFAChecker _setupMFAChecker;

}