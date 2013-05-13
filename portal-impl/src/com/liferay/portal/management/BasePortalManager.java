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

package com.liferay.portal.management;

import com.liferay.portal.kernel.management.ManageAction;
import com.liferay.portal.kernel.management.ManageActionException;
import com.liferay.portal.kernel.management.PortalManager;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;

/**
 * @author Shuyang Zhou
 */
@DoPrivileged
public class BasePortalManager implements PortalManager {

	@Override
	public <T> T manage(ManageAction<T> manageAction)
		throws ManageActionException {

		return manageAction.action();
	}

}