/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.whip.instrument;

/**
 * @author Cristina González
 */
public class SwitchHolder extends JumpHolder {

	public SwitchHolder(int lineNumber, int switchNumber, int branch) {
		super(lineNumber, switchNumber);

		_branch = branch;
	}

	public int getBranch() {
		return _branch;
	}

	private final int _branch;

}