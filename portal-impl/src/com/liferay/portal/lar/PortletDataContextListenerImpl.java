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

package com.liferay.portal.lar;

import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataContextListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Raymond Augé
 */
public class PortletDataContextListenerImpl
	implements PortletDataContextListener {

	public PortletDataContextListenerImpl(
		PortletDataContext portletDataContext) {
	}

	@Override
	public void onAddZipEntry(String path) {
		if (_log.isInfoEnabled()) {
			_log.info("Export " + path);
		}
	}

	@Override
	public void onGetZipEntry(String path) {
		if (_log.isInfoEnabled()) {
			_log.info("Import " + path);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortletDataContextListenerImpl.class);

}