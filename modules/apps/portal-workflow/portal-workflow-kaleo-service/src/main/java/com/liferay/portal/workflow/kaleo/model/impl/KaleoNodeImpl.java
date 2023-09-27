/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.workflow.kaleo.model.KaleoTransition;
import com.liferay.portal.workflow.kaleo.service.KaleoTransitionLocalServiceUtil;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class KaleoNodeImpl extends KaleoNodeBaseImpl {

	@Override
	public KaleoTransition getDefaultKaleoTransition() throws PortalException {
		return KaleoTransitionLocalServiceUtil.getDefaultKaleoTransition(
			getKaleoNodeId());
	}

	@Override
	public KaleoTransition getKaleoTransition(String name)
		throws PortalException {

		return KaleoTransitionLocalServiceUtil.getKaleoTransition(
			getKaleoNodeId(), name);
	}

	@Override
	public List<KaleoTransition> getKaleoTransitions() {
		return KaleoTransitionLocalServiceUtil.getKaleoTransitions(
			getKaleoNodeId());
	}

	@Override
	public boolean hasKaleoTransition() {
		int count = KaleoTransitionLocalServiceUtil.getKaleoTransitionsCount(
			getKaleoNodeId());

		if (count > 0) {
			return true;
		}

		return false;
	}

}