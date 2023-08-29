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

package com.liferay.multi.factor.authentication.web.internal.filter;

import com.liferay.multi.factor.authentication.web.internal.policy.MFAPolicy;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BaseFilter;
import com.liferay.portal.kernel.servlet.TryFilter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mirna Gama
 */
@Component(
	property = {
		"after-filter=MFA Servlet Filter", "dispatcher=FORWARD",
		"dispatcher=REQUEST", "servlet-context-name=",
		"servlet-filter-name=MFA Filter", "url-pattern=/*"
	},
	service = Filter.class
)
public class MFAFilter extends BaseFilter implements TryFilter {

	@Override
	public Object doFilterTry(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		String path = (String)httpServletRequest.getAttribute(
			WebKeys.INVOKER_FILTER_URI);

		if (path.contains("/c/portal/update_password")) {
			boolean mfaEnabled = _mfaPolicy.isMFAEnabled(
				GetterUtil.getLong(
					httpServletRequest.getAttribute(WebKeys.COMPANY_ID)));

			HttpSession httpSession = httpServletRequest.getSession();

			httpSession.setAttribute(WebKeys.MFA_ENABLED, mfaEnabled);
		}

		return true;
	}

	@Override
	public boolean isFilterEnabled() {
		return true;
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	private static final Log _log = LogFactoryUtil.getLog(MFAFilter.class);

	@Reference
	private MFAPolicy _mfaPolicy;

	@Reference
	private Portal _portal;

}