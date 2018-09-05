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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.dao.orm.LockMode;

/**
 * @author Brian Wing Shun Chan
 */
public class LockModeTranslator {

	public static org.hibernate.LockMode translate(LockMode lockMode) {
		if (lockMode == LockMode.FORCE) {
			return org.hibernate.LockMode.PESSIMISTIC_FORCE_INCREMENT;
		}
		else if (lockMode == LockMode.NONE) {
			return org.hibernate.LockMode.NONE;
		}
		else if (lockMode == LockMode.OPTIMISTIC) {
			return org.hibernate.LockMode.OPTIMISTIC;
		}
		else if (lockMode == LockMode.OPTIMISTIC_FORCE_INCREMENT) {
			return org.hibernate.LockMode.OPTIMISTIC_FORCE_INCREMENT;
		}
		else if (lockMode == LockMode.PESSIMISTIC_FORCE_INCREMENT) {
			return org.hibernate.LockMode.PESSIMISTIC_FORCE_INCREMENT;
		}
		else if (lockMode == LockMode.PESSIMISTIC_READ) {
			return org.hibernate.LockMode.PESSIMISTIC_READ;
		}
		else if (lockMode == LockMode.PESSIMISTIC_WRITE) {
			return org.hibernate.LockMode.PESSIMISTIC_WRITE;
		}
		else if (lockMode == LockMode.READ) {
			return org.hibernate.LockMode.READ;
		}
		else if (lockMode == LockMode.UPGRADE) {
			return org.hibernate.LockMode.PESSIMISTIC_WRITE;
		}
		else if (lockMode == LockMode.UPGRADE_NOWAIT) {
			return org.hibernate.LockMode.UPGRADE_NOWAIT;
		}
		else if (lockMode == LockMode.WRITE) {
			return org.hibernate.LockMode.WRITE;
		}

		return org.hibernate.LockMode.parse(lockMode.toString());
	}

}