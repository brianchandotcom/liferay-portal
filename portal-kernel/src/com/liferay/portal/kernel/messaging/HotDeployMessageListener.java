/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.messaging;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Collections;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public class HotDeployMessageListener extends BaseMessageListener {

	public HotDeployMessageListener() {
		this((String[])null);
	}

	public HotDeployMessageListener(String... servletContextNames) {
		if (servletContextNames == null) {
			_servletContextNames = Collections.emptySet();
		}
		else {
			_servletContextNames = SetUtil.fromArray(servletContextNames);
		}
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		String servletContextName = GetterUtil.getString(
			message.getString("servletContextName"));

		if (!_servletContextNames.isEmpty() &&
			!_servletContextNames.contains(servletContextName)) {

			return;
		}

		String command = GetterUtil.getString(message.getString("command"));

		if (command.equals("deploy")) {
			onDeploy(message);
		}
		else if (command.equals("undeploy")) {
			onUndeploy(message);
		}
	}

	protected void onDeploy(Message message) throws Exception {
	}

	protected void onUndeploy(Message message) throws Exception {
	}

	private final Set<String> _servletContextNames;

}