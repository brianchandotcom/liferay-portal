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

package com.liferay.portal.upgrade.internal;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Miguel Pastor
 * @author Carlos Sierra Andrés
 */
public class UpgradeInfo {

	public UpgradeInfo(String from, String to, UpgradeProcess upgradeProcess) {
		_from = from;
		_to = to;
		_upgradeProcess = upgradeProcess;
	}

	public int from() {
		return transform(_from);
	}

	public String getFrom() {
		return _from;
	}

	public String getTo() {
		return _to;
	}

	public UpgradeProcess getUpgradeProcess() {
		return _upgradeProcess;
	}

	public int to() {
		return transform(_to);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("Upgrade process from version '");
		sb.append(_from);
		sb.append("' to version '");
		sb.append(_to);
		sb.append("'");

		return sb.toString();
	}

	protected int transform(String s) {
		return Integer.parseInt(s.replace(StringPool.PERIOD, StringPool.BLANK));
	}

	private final String _from;
	private final String _to;
	private final UpgradeProcess _upgradeProcess;

}