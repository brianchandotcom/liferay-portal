/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.reports.engine;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Michael C. Han
 */
public interface ReportResultContainer {

	public OutputStream getOutputStream() throws IOException;

	public ReportGenerationException getReportGenerationException();

	public String getReportName();

	public byte[] getResults();

	public boolean hasError();

	public void setReportGenerationException(
		ReportGenerationException reportGenerationException);

}