/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.plugin.op.connect;

import java.util.Collections;
import java.util.List;

/**
 * @author Michael Hashimoto
 */
public class OPConnectUtil {

	public static List<String> getIgnoredValues() {
		if (_opConnectDescriptor == null) {
			return Collections.emptyList();
		}

		return _opConnectDescriptor.getIgnoredValues();
	}

	public static long getRefreshIntervalMinutes() {
		if (_opConnectDescriptor == null) {
			return 0;
		}

		return _opConnectDescriptor.getRefreshIntervalMinutes();
	}

	public static List<String> getSecretValues() {
		if (_opConnectDescriptor == null) {
			return Collections.emptyList();
		}

		return _opConnectDescriptor.getSecretValues();
	}

	public static void refreshSecretValues() {
		if (_opConnectDescriptor != null) {
			_opConnectDescriptor.refreshSecretValues();
		}
	}

	public static void setOPConnectDescriptor(
		OPConnectDescriptor opConnectDescriptor) {

		_opConnectDescriptor = opConnectDescriptor;
	}

	private static volatile OPConnectDescriptor _opConnectDescriptor;

}