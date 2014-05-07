/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.blogs.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.xmlrpc.Method;
import com.liferay.portal.kernel.xmlrpc.Response;
import com.liferay.portal.kernel.xmlrpc.XmlRpcConstants;
import com.liferay.portal.kernel.xmlrpc.XmlRpcUtil;
import com.liferay.portlet.blogs.pingback.DuplicateCommentException;
import com.liferay.portlet.blogs.pingback.InvalidSourceURIException;
import com.liferay.portlet.blogs.pingback.Pingback;
import com.liferay.portlet.blogs.pingback.PingbackDisabledException;
import com.liferay.portlet.blogs.pingback.PingbackImpl;
import com.liferay.portlet.blogs.pingback.UnavailableSourceURIException;

/**
 * @author Alexander Chow
 */
public class PingbackMethodImpl implements Method {

	public static final int ACCESS_DENIED = 49;

	public static final int GENERIC_FAULT = 0;

	public static final int PINGBACK_ALREADY_REGISTERED = 48;

	public static final int SERVER_ERROR = 50;

	public static final int SOURCE_URI_DOES_NOT_EXIST = 16;

	public static final int SOURCE_URI_INVALID = 17;

	public static final int TARGET_URI_DOES_NOT_EXIST = 32;

	public static final int TARGET_URI_INVALID = 33;

	public PingbackMethodImpl() {
		_pingback = new PingbackImpl();
	}

	@Override
	public Response execute(long companyId) {
		try {
			_pingback.addPingback(companyId);

			return XmlRpcUtil.createSuccess("Pingback accepted");
		}
		catch (DuplicateCommentException dce) {
			return XmlRpcUtil.createFault(
				PINGBACK_ALREADY_REGISTERED, "Pingback previously registered");
		}
		catch (InvalidSourceURIException isue) {
			return XmlRpcUtil.createFault(
				SOURCE_URI_INVALID, isue.getMessage());
		}
		catch (PingbackDisabledException pde) {
			return XmlRpcUtil.createFault(
				XmlRpcConstants.REQUESTED_METHOD_NOT_FOUND, pde.getMessage());
		}
		catch (UnavailableSourceURIException usue) {
			return XmlRpcUtil.createFault(
				SOURCE_URI_DOES_NOT_EXIST, usue.getMessage());
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}

			return XmlRpcUtil.createFault(
				TARGET_URI_INVALID, "Error parsing target URI");
		}
	}

	@Override
	public String getMethodName() {
		return "pingback.ping";
	}

	@Override
	public String getToken() {
		return "pingback";
	}

	@Override
	public boolean setArguments(Object[] arguments) {
		try {
			_pingback.setSourceUri((String)arguments[0]);
			_pingback.setTargetUri((String)arguments[1]);

			return true;
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}

			return false;
		}
	}

	protected PingbackMethodImpl(Pingback pingback) {
		_pingback = pingback;
	}

	private static Log _log = LogFactoryUtil.getLog(PingbackMethodImpl.class);

	private Pingback _pingback;

}