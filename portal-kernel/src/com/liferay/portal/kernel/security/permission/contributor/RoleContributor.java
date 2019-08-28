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

package com.liferay.portal.kernel.security.permission.contributor;

/**
 * RoleContributors are invoked during permission checking allowing the roles
 * calculated from persisted assignment and inheritance to be altered
 * dynamically. Implementations must make every attempt to be as efficient as
 * possible or risk potentially dramatic performance degradation.
 *
 * @author Raymond Augé
 *
 * @review
 */
public interface RoleContributor {

	public void contribute(RoleCollection roleCollection);

}