/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoTimer;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceTokenLocalServiceUtil;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskInstanceTokenLocalServiceUtil;
import com.liferay.portal.workflow.kaleo.service.KaleoTimerLocalServiceUtil;

/**
 * @author Marcellus Tavares
 */
public class KaleoTimerInstanceTokenImpl
	extends KaleoTimerInstanceTokenBaseImpl {

	@Override
	public KaleoInstanceToken getKaleoInstanceToken() throws PortalException {
		return KaleoInstanceTokenLocalServiceUtil.getKaleoInstanceToken(
			getKaleoInstanceTokenId());
	}

	@Override
	public KaleoTaskInstanceToken getKaleoTaskInstanceToken() {
		return KaleoTaskInstanceTokenLocalServiceUtil.
			fetchKaleoTaskInstanceToken(getKaleoTaskInstanceTokenId());
	}

	@Override
	public KaleoTimer getKaleoTimer() throws PortalException {
		return KaleoTimerLocalServiceUtil.getKaleoTimer(getKaleoTimerId());
	}

}