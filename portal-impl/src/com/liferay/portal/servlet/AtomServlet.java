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

package com.liferay.portal.servlet;

import com.liferay.portal.atom.AtomProvider;
import com.liferay.portal.atom.AtomUtil;
import com.liferay.portal.kernel.atom.AtomCollectionAdapter;
import com.liferay.portal.kernel.atom.AtomCollectionAdapterRegistryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.abdera.protocol.server.Provider;
import org.apache.abdera.protocol.server.servlet.AbderaServlet;

/**
 * @author Igor Spasic
 */
public class AtomServlet extends AbderaServlet {

	@Override
	protected Provider createProvider() {
		AtomProvider atomProvider = new AtomProvider();

		atomProvider.init(getAbdera(), null);

		List<AtomCollectionAdapter<?>> atomCollectionAdapters =
			AtomCollectionAdapterRegistryUtil.getAtomCollectionAdapters();

		for (AtomCollectionAdapter<?> atomCollectionAdapter :
				atomCollectionAdapters) {

			atomProvider.addCollection(atomCollectionAdapter);
		}

		return atomProvider;
	}

	@Override
	protected void service(
			HttpServletRequest request, HttpServletResponse response)
		throws ServletException {

		try {
			UserResolver userResolver = new UserResolver(request);

			CompanyThreadLocal.setCompanyId(userResolver.getCompanyId());

			User user = userResolver.getUser();

			if (user != null) {
				if (_log.isDebugEnabled()) {
					_log.debug("User " + user.getUserId());
				}

				PermissionChecker permissionChecker =
					PermissionCheckerFactoryUtil.create(user);

				PermissionThreadLocal.setPermissionChecker(permissionChecker);

				AtomUtil.saveUserInRequest(request, user);
			}

			super.service(request, response);
		}
		catch (Exception e) {
			throw new ServletException(e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(AtomServlet.class);

}