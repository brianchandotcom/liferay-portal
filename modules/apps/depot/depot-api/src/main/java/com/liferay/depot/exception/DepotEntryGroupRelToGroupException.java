/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class DepotEntryGroupRelToGroupException extends PortalException {

	public DepotEntryGroupRelToGroupException() {
	}

	public DepotEntryGroupRelToGroupException(String msg) {
		super(msg);
	}

	public DepotEntryGroupRelToGroupException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public DepotEntryGroupRelToGroupException(Throwable throwable) {
		super(throwable);
	}

	public static class MustBeLocallyStaged extends DepotEntryGroupException {

		public MustBeLocallyStaged() {
			super(
				"A locally staged asset library cannot be connected to a " +
					"remotely staged site");
		}

	}

	public static class MustBeRemotelyStaged extends DepotEntryGroupException {

		public MustBeRemotelyStaged() {
			super(
				"A remotely staged asset library cannot be connected to a " +
					"locally staged site");
		}

	}

	public static class MustBeStaged extends DepotEntryGroupException {

		public MustBeStaged() {
			super(
				"A staged asset library cannot be connected to an unstaged " +
					"site");
		}

	}

	public static class MustNotBeStaged extends DepotEntryGroupException {

		public MustNotBeStaged() {
			super(
				"An unstaged asset library cannot be connected to a staged " +
					"site");
		}

	}

}