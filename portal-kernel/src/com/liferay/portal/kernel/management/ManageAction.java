/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.management;

import java.io.Serializable;

/**
 * @author Shuyang Zhou
 */
public interface ManageAction<T> extends Serializable {

	public T action() throws ManageActionException;

}