/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet.filters.authverifier;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ProtectedServletRequest;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.ac.AccessControlUtil;
import com.liferay.portal.security.auth.AccessControlContext;
import com.liferay.portal.security.auth.AuthVerifier;
import com.liferay.portal.security.auth.AuthVerifierPipeline;
import com.liferay.portal.security.auth.AuthVerifierResult;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <p>
 * See http://issues.liferay.com/browse/LPS-27888.
 * </p>
 *
 * @author Tomas Polesovsky
 * @author Raymond Augé
 */
public class AuthVerifierFilter extends BasePortalFilter {

	@Override
	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		AccessControlUtil.initAccessControlContext(request, response);

		AuthVerifierResult.State state = AccessControlUtil.verifyRequest();

		AccessControlContext accessControlContext =
			AccessControlUtil.getAccessControlContext();

		AuthVerifierResult authVerifierResult =
			accessControlContext.getAuthVerifierResult();

		if (_log.isDebugEnabled()) {
			_log.debug("Auth verifier result " + authVerifierResult);
		}

		if (state == AuthVerifierResult.State.INVALID_CREDENTIALS) {
			if (_log.isDebugEnabled()) {
				_log.debug("Result state doesn't allow us to continue.");
			}
		}
		else if (state == AuthVerifierResult.State.NOT_APPLICABLE) {
			_log.error("Internal exception, invalid state " + state);
		}
		else if (state == AuthVerifierResult.State.SUCCESS) {
			long userId = authVerifierResult.getUserId();

			AccessControlUtil.initContextUser(userId);

			Map<String, Object> authSettings =
				accessControlContext.getSettings();

			String authType = GetterUtil.get(
				authSettings.get(AuthVerifierPipeline.AUTH_TYPE),
				StringPool.BLANK);

			if (Validator.isNull(authType)) {
				authType = null;
			}

			ProtectedServletRequest protectedServletRequest =
				new ProtectedServletRequest(
					request, String.valueOf(userId), authType);

			accessControlContext.setRequest(protectedServletRequest);

			processFilter(
				getClass(), protectedServletRequest, response, filterChain);
		}
		else {
			_log.error("Unimplemented state " + state);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		AuthVerifierFilter.class.getName());

}