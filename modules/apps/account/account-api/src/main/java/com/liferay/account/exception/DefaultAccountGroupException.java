/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class DefaultAccountGroupException extends PortalException {

	public DefaultAccountGroupException() {
	}

	public DefaultAccountGroupException(String msg) {
		super(msg);
	}

	public DefaultAccountGroupException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public DefaultAccountGroupException(Throwable throwable) {
		super(throwable);
	}

	public static class MustNotDeleteDefaultAccountGroup
		extends DefaultAccountGroupException {

		public MustNotDeleteDefaultAccountGroup(long accountGroupId) {
			super(
				String.format(
					"The default account group %s cannot be deleted",
					accountGroupId));

			this.accountGroupId = accountGroupId;
		}

		public long accountGroupId;

	}

	public static class MustNotDuplicateDefaultAccountGroup
		extends DefaultAccountGroupException {

		public MustNotDuplicateDefaultAccountGroup(long companyId) {
			super(
				String.format(
					"There is already a default account group for company %s",
					companyId));

			this.companyId = companyId;
		}

		public long companyId;

	}

	public static class MustNotUpdateDefaultAccountGroup
		extends DefaultAccountGroupException {

		public MustNotUpdateDefaultAccountGroup(long accountGroupId) {
			super(
				String.format(
					"The default account group %s cannot be updated",
					accountGroupId));

			this.accountGroupId = accountGroupId;
		}

		public long accountGroupId;

	}

}