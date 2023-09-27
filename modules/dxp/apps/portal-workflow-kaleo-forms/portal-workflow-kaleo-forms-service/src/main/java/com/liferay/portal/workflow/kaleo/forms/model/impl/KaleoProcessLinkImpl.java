/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.forms.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess;
import com.liferay.portal.workflow.kaleo.forms.service.KaleoProcessLocalServiceUtil;

/**
 * @author Marcellus Tavares
 */
public class KaleoProcessLinkImpl extends KaleoProcessLinkBaseImpl {

	@Override
	public KaleoProcess getKaleoProcess() throws PortalException {
		return KaleoProcessLocalServiceUtil.getKaleoProcess(
			getKaleoProcessId());
	}

}