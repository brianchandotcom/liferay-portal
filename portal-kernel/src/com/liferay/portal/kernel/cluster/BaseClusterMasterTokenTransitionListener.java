/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.cluster;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Michael C. Han
 */
public abstract class BaseClusterMasterTokenTransitionListener
	implements ClusterMasterTokenTransitionListener {

	@Override
	public void masterTokenAcquired() {
		try {
			doMasterTokenAcquired();
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to process the token acquired event", exception);
			}
		}
	}

	@Override
	public void masterTokenReleased() {
		try {
			doMasterTokenReleased();
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to process the token released event", exception);
			}
		}
	}

	protected abstract void doMasterTokenAcquired() throws Exception;

	protected abstract void doMasterTokenReleased() throws Exception;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseClusterMasterTokenTransitionListener.class);

}