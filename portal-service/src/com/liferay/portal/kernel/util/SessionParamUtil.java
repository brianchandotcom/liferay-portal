/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.util;

import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 */
public class SessionParamUtil {

	public static boolean getBoolean(HttpServletRequest request, String param) {
		return getBoolean(request, param, GetterUtil.DEFAULT_BOOLEAN);
	}

	public static boolean getBoolean(
		HttpServletRequest request, String param, boolean defaultValue) {

		HttpSession session = request.getSession(false);

		String requestValue = request.getParameter(param);

		if (requestValue != null) {
			boolean value = GetterUtil.getBoolean(requestValue);

			if (session != null) {
				session.setAttribute(param, value);
			}

			return value;
		}

		if (session != null) {
			Boolean sessionValue = (Boolean)session.getAttribute(param);

			if (sessionValue != null) {
				return sessionValue;
			}
		}

		return defaultValue;
	}

	public static boolean getBoolean(
		PortletRequest portletRequest, String param) {

		return getBoolean(portletRequest, param, GetterUtil.DEFAULT_BOOLEAN);
	}

	public static boolean getBoolean(
		PortletRequest portletRequest, String param, boolean defaultValue) {

		PortletSession portletSession = portletRequest.getPortletSession(false);

		String portletRequestValue = portletRequest.getParameter(param);

		if (portletRequestValue != null) {
			boolean value = GetterUtil.getBoolean(portletRequestValue);

			portletSession.setAttribute(param, value);

			return value;
		}

		if (portletSession != null) {
			Boolean portletSessionValue = (Boolean)portletSession.getAttribute(
				param);

			if (portletSessionValue != null) {
				return portletSessionValue;
			}
		}

		return defaultValue;
	}

	public static double getDouble(HttpServletRequest request, String param) {
		return getDouble(request, param, GetterUtil.DEFAULT_DOUBLE);
	}

	public static double getDouble(
		HttpServletRequest request, String param, double defaultValue) {

		HttpSession session = request.getSession(false);

		String requestValue = request.getParameter(param);

		if (requestValue != null) {
			double value = GetterUtil.getDouble(requestValue);

			if (session != null) {
				session.setAttribute(param, value);
			}

			return value;
		}

		if (session != null) {
			Double sessionValue = (Double)session.getAttribute(param);

			if (sessionValue != null) {
				return sessionValue;
			}
		}

		return defaultValue;
	}

	public static double getDouble(
		PortletRequest portletRequest, String param) {

		return getDouble(portletRequest, param, GetterUtil.DEFAULT_DOUBLE);
	}

	public static double getDouble(
		PortletRequest portletRequest, String param, double defaultValue) {

		PortletSession portletSession = portletRequest.getPortletSession(false);

		String portletRequestValue = portletRequest.getParameter(param);

		if (portletRequestValue != null) {
			double value = GetterUtil.getDouble(portletRequestValue);

			portletSession.setAttribute(param, value);

			return value;
		}

		if (portletSession != null) {
			Double portletSessionValue = (Double)portletSession.getAttribute(
				param);

			if (portletSessionValue != null) {
				return portletSessionValue;
			}
		}

		return defaultValue;
	}

	public static int getInteger(HttpServletRequest request, String param) {
		return getInteger(request, param, GetterUtil.DEFAULT_INTEGER);
	}

	public static int getInteger(
		HttpServletRequest request, String param, int defaultValue) {

		HttpSession session = request.getSession(false);

		String requestValue = request.getParameter(param);

		if (requestValue != null) {
			int value = GetterUtil.getInteger(requestValue);

			if (session != null) {
				session.setAttribute(param, value);
			}

			return value;
		}

		if (session != null) {
			Integer sessionValue = (Integer)session.getAttribute(param);

			if (sessionValue != null) {
				return sessionValue;
			}
		}

		return defaultValue;
	}

	public static int getInteger(PortletRequest portletRequest, String param) {
		return getInteger(portletRequest, param, GetterUtil.DEFAULT_INTEGER);
	}

	public static int getInteger(
		PortletRequest portletRequest, String param, int defaultValue) {

		PortletSession portletSession = portletRequest.getPortletSession(false);

		String portletRequestValue = portletRequest.getParameter(param);

		if (portletRequestValue != null) {
			int value = GetterUtil.getInteger(portletRequestValue);

			portletSession.setAttribute(param, value);

			return value;
		}

		if (portletSession != null) {
			Integer portletSessionValue = (Integer)portletSession.getAttribute(
				param);

			if (portletSessionValue != null) {
				return portletSessionValue;
			}
		}

		return defaultValue;
	}

	public static long getLong(HttpServletRequest request, String param) {
		return getLong(request, param, GetterUtil.DEFAULT_LONG);
	}

	public static long getLong(
		HttpServletRequest request, String param, long defaultValue) {

		HttpSession session = request.getSession(false);

		String requestValue = request.getParameter(param);

		if (requestValue != null) {
			long value = GetterUtil.getLong(requestValue);

			if (session != null) {
				session.setAttribute(param, value);
			}

			return value;
		}

		if (session != null) {
			Long sessionValue = (Long)session.getAttribute(param);

			if (sessionValue != null) {
				return sessionValue;
			}
		}

		return defaultValue;
	}

	public static long getLong(PortletRequest portletRequest, String param) {
		return getLong(portletRequest, param, GetterUtil.DEFAULT_LONG);
	}

	public static long getLong(
		PortletRequest portletRequest, String param, long defaultValue) {

		PortletSession portletSession = portletRequest.getPortletSession(false);

		String portletRequestValue = portletRequest.getParameter(param);

		if (portletRequestValue != null) {
			long value = GetterUtil.getLong(portletRequestValue);

			portletSession.setAttribute(param, value);

			return value;
		}

		if (portletSession != null) {
			Long portletSessionValue = (Long)portletSession.getAttribute(param);

			if (portletSessionValue != null) {
				return portletSessionValue;
			}
		}

		return defaultValue;
	}

	public static short getShort(HttpServletRequest request, String param) {
		return getShort(request, param, GetterUtil.DEFAULT_SHORT);
	}

	public static short getShort(
		HttpServletRequest request, String param, short defaultValue) {

		HttpSession session = request.getSession(false);

		String requestValue = request.getParameter(param);

		if (requestValue != null) {
			short value = GetterUtil.getShort(requestValue);

			if (session != null) {
				session.setAttribute(param, value);
			}

			return value;
		}

		if (session != null) {
			Short sessionValue = (Short)session.getAttribute(param);

			if (sessionValue != null) {
				return sessionValue;
			}
		}

		return defaultValue;
	}

	public static short getShort(PortletRequest portletRequest, String param) {
		return getShort(portletRequest, param, GetterUtil.DEFAULT_SHORT);
	}

	public static short getShort(
		PortletRequest portletRequest, String param, short defaultValue) {

		PortletSession portletSession = portletRequest.getPortletSession(false);

		String portletRequestValue = portletRequest.getParameter(param);

		if (portletRequestValue != null) {
			short value = GetterUtil.getShort(portletRequestValue);

			portletSession.setAttribute(param, value);

			return value;
		}

		if (portletSession != null) {
			Short portletSessionValue = (Short)portletSession.getAttribute(
				param);

			if (portletSessionValue != null) {
				return portletSessionValue;
			}
		}

		return defaultValue;
	}

	public static String getString(HttpServletRequest request, String param) {
		return getString(request, param, GetterUtil.DEFAULT_STRING);
	}

	public static String getString(
		HttpServletRequest request, String param, String defaultValue) {

		HttpSession session = request.getSession(false);

		String requestValue = request.getParameter(param);

		if (requestValue != null) {
			String value = GetterUtil.getString(requestValue);

			if (session != null) {
				session.setAttribute(param, value);
			}

			return value;
		}

		if (session != null) {
			String sessionValue = (String)session.getAttribute(param);

			if (sessionValue != null) {
				return sessionValue;
			}
		}

		return defaultValue;
	}

	public static String getString(
		PortletRequest portletRequest, String param) {

		return getString(portletRequest, param, GetterUtil.DEFAULT_STRING);
	}

	public static String getString(
		PortletRequest portletRequest, String param, String defaultValue) {

		PortletSession portletSession = portletRequest.getPortletSession(false);

		String portletRequestValue = portletRequest.getParameter(param);

		if (portletRequestValue != null) {
			String value = GetterUtil.getString(portletRequestValue);

			portletSession.setAttribute(param, value);

			return value;
		}

		if (portletSession != null) {
			String portletSessionValue = (String)portletSession.getAttribute(
				param);

			if (portletSessionValue != null) {
				return portletSessionValue;
			}
		}

		return defaultValue;
	}

}