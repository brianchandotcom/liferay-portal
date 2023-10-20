/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marco Galluzzi
 */
public class DLFileEntryConfigurationException extends PortalException {

	public static class InvalidMaxNumberOfPagesException
		extends DLFileEntryConfigurationException {

		public InvalidMaxNumberOfPagesException() {
		}

		public InvalidMaxNumberOfPagesException(String msg) {
			super(msg);
		}

	}

	public static class InvalidPreviewableProcessorMaxSizeException
		extends DLFileEntryConfigurationException {

		public InvalidPreviewableProcessorMaxSizeException() {
		}

		public InvalidPreviewableProcessorMaxSizeException(String msg) {
			super(msg);
		}

	}

	private DLFileEntryConfigurationException() {
	}

	private DLFileEntryConfigurationException(String msg) {
		super(msg);
	}

}