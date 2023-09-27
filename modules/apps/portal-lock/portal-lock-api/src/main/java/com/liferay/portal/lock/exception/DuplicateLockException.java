/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.lock.exception;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.lock.model.Lock;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateLockException extends PortalException {

	public DuplicateLockException(Lock lock) {
		super(lock.toString());

		_lock = lock;
	}

	public Lock getLock() {
		return _lock;
	}

	private final Lock _lock;

}