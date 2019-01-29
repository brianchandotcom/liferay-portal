/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.reports.engine.jasper.internal.compiler;

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