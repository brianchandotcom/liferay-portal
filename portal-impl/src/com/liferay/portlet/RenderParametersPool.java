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

import com.liferay.portal.kernel.servlet.PortalSessionContext;
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

	public static Map<String, Map<String, String[]>> clear(
		HttpServletRequest request, long plid) {

		if (plid <= 0) {
			return null;
		}

		Map<Long, Map<String, Map<String, String[]>>> pool =
			_getRenderParametersPool(request, false);

		if (pool == null) {
			return null;
		}

		return pool.remove(plid);
	}

	public static Map<String, String[]> clear(
		HttpServletRequest request, long plid, String portletId) {

		Map<String, Map<String, String[]>> plidPool = clear(request, plid);

		if (plidPool == null) {
			return null;
		}

		return plidPool.remove(portletId);
	}

	public static Map<String, Map<String, String[]>> get(
		HttpServletRequest request, long plid) {

		if (plid <= 0) {
			return null;
		}

		Map<Long, Map<String, Map<String, String[]>>> pool =
			_getRenderParametersPool(request, false);

		if (pool == null) {
			return null;
		}

		return pool.get(plid);
	}

	public static Map<String, String[]> get(
		HttpServletRequest request, long plid, String portletId) {

		Map<String, Map<String, String[]>> plidPool = get(request, plid);

		if (plidPool == null) {
			return null;
		}

		return plidPool.get(portletId);
	}

	public static Map<String, Map<String, String[]>> getOrCreate(
		HttpServletRequest request, long plid) {

		if (plid <= 0) {
			return new ConcurrentHashMap<>();
		}

		Map<Long, Map<String, Map<String, String[]>>> pool =
			_getRenderParametersPool(request, true);

		return pool.computeIfAbsent(plid, key -> new ConcurrentHashMap<>());
	}

	public static Map<String, String[]> getOrCreate(
		HttpServletRequest request, long plid, String portletId) {

		Map<String, Map<String, String[]>> plidPool = getOrCreate(
			request, plid);

		return plidPool.computeIfAbsent(portletId, key -> new HashMap<>());
	}

	public static void put(
		HttpServletRequest request, long plid, String portletId,
		Map<String, String[]> params) {

		if (params.isEmpty()) {
			return;
		}

		Map<String, Map<String, String[]>> plidPool = getOrCreate(
			request, plid);

		plidPool.put(portletId, params);
	}

	private static Map<Long, Map<String, Map<String, String[]>>>
		_getRenderParametersPool(
			HttpServletRequest request, boolean createIfAbsent) {

		HttpSession session = request.getSession();

		HttpSession portalSession = PortalSessionContext.get(session.getId());

		if (portalSession != null) {
			session = portalSession;
		}

		Map<Long, Map<String, Map<String, String[]>>> renderParametersPool =
			(Map<Long, Map<String, Map<String, String[]>>>)session.getAttribute(
				WebKeys.PORTLET_RENDER_PARAMETERS);

		if (createIfAbsent && (renderParametersPool == null)) {
			renderParametersPool = new ConcurrentHashMap<>();

			session.setAttribute(
				WebKeys.PORTLET_RENDER_PARAMETERS, renderParametersPool);
		}

		return renderParametersPool;
	}

}