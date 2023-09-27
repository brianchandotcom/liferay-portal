/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.service.KaleoNodeLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class KaleoTransitionImpl extends KaleoTransitionBaseImpl {

	@Override
	public KaleoNode getSourceKaleoNode() throws PortalException {
		return KaleoNodeLocalServiceUtil.getKaleoNode(getSourceKaleoNodeId());
	}

	@Override
	public KaleoNode getTargetKaleoNode() throws PortalException {
		return KaleoNodeLocalServiceUtil.getKaleoNode(getTargetKaleoNodeId());
	}

}