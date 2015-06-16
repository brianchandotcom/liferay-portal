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

package com.liferay.exportimport.lar;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataContextListener;

/**
 * @author     Raymond Augé
 * @deprecated As of 7.0.0, see {@link
 *             com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleEvent}
 */
@Deprecated
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

	private static final Log _log = LogFactoryUtil.getLog(
		PortletDataContextListenerImpl.class);

}