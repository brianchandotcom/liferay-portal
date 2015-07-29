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

package com.liferay.portlet;

import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 */
public class RenderParametersPool {

	public static void clear(HttpServletRequest request, long plid) {
		Map<String, Map<String, String[]>> plidPool = get(request, plid);

		plidPool.clear();
	}

	public static void clear(
		HttpServletRequest request, long plid, String portletId) {

		Map<String, String[]> params = get(request, plid, portletId);

		params.clear();
	}

	public static Map<String, Map<String, String[]>> get(
		HttpServletRequest request, long plid) {

		HttpSession session = request.getSession();

		if (plid <= 0) {
			return new ConcurrentHashMap<>();
		}

		Map<Long, Map<String, Map<String, String[]>>> pool =
			_getRenderParametersPool(session);

		Map<String, Map<String, String[]>> plidPool = pool.get(plid);

		if (plidPool == null) {
			plidPool = new ConcurrentHashMap<>();

			pool.put(plid, plidPool);
		}

		return plidPool;
	}

	public static Map<String, String[]> get(
		HttpServletRequest request, long plid, String portletId) {

		Map<String, Map<String, String[]>> plidPool = get(request, plid);

		Map<String, String[]> params = plidPool.get(portletId);

		if (params == null) {
			params = new HashMap<>();

			plidPool.put(portletId, params);
		}

		return params;
	}

	public static void put(
		HttpServletRequest request, long plid, String portletId,
		Map<String, String[]> params) {

		Map<String, Map<String, String[]>> plidPool = get(request, plid);

		plidPool.put(portletId, params);
	}

	private static Map<Long, Map<String, Map<String, String[]>>>
		_getRenderParametersPool(HttpSession session) {

		Map<Long, Map<String, Map<String, String[]>>> renderParametersPool =
			(Map<Long, Map<String, Map<String, String[]>>>)session.getAttribute(
				WebKeys.PORTLET_RENDER_PARAMETERS);

		if (renderParametersPool == null) {
			renderParametersPool = new ConcurrentHashMap<>();

			session.setAttribute(
				WebKeys.PORTLET_RENDER_PARAMETERS, renderParametersPool);
		}

		return renderParametersPool;
	}

}