/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.reports.engine.console.jasper.internal.compiler;

import com.liferay.portal.reports.engine.ReportDesignRetriever;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public interface ReportCompiler {

	public JasperReport compile(ReportDesignRetriever reportDesignRetriever)
		throws JRException;

	public JasperReport compile(
			ReportDesignRetriever reportDesignRetriever, boolean force)
		throws JRException;

	public void flush();

}