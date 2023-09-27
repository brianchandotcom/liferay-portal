/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.reports.engine;

/**
 * @author Michael C. Han
 */
public class ReportExportException extends ReportGenerationException {

	public ReportExportException() {
	}

	public ReportExportException(String msg) {
		super(msg);
	}

	public ReportExportException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public ReportExportException(Throwable throwable) {
		super(throwable);
	}

}