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

package com.liferay.lcs.messaging;

import com.liferay.lcs.sigar.SigarNativeLoader;
import com.liferay.lcs.util.LCSConnectionManager;
import com.liferay.lcs.util.LCSUtil;
import com.liferay.lcs.util.PortletPropsValues;
import com.liferay.portal.kernel.license.messaging.LCSPortletState;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.HotDeployMessageListener;
import com.liferay.portal.kernel.messaging.Message;

/**
 * @author Igor Beslic
 */
public class LCSHotDeployMessageListener extends HotDeployMessageListener {

	public LCSHotDeployMessageListener() {
		super("lcs-portlet");
	}

	public void setLCSConnectionManager(
		LCSConnectionManager lcsConnectionManager) {

		_lcsConnectionManager = lcsConnectionManager;
	}

	@Override
	protected void onDeploy(Message message) throws Exception {
		SigarNativeLoader.load();

		_lcsConnectionManager.onPortletDeployed();

		if (_log.isInfoEnabled()) {
			_log.info(
				"LCS portlet " + PortletPropsValues.LCS_CLIENT_VERSION +
					" deployed");
		}
	}

	@Override
	protected void onUndeploy(Message message) throws Exception {
		LCSUtil.processLCSPortletState(LCSPortletState.PLUGIN_ABSENT);

		SigarNativeLoader.unload();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LCSHotDeployMessageListener.class);

	private LCSConnectionManager _lcsConnectionManager;

}