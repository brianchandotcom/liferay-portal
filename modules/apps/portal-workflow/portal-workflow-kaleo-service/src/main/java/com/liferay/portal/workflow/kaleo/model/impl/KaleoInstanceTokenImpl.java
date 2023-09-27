/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceLocalServiceUtil;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceTokenLocalServiceUtil;
import com.liferay.portal.workflow.kaleo.service.KaleoNodeLocalServiceUtil;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class KaleoInstanceTokenImpl extends KaleoInstanceTokenBaseImpl {

	@Override
	public List<KaleoInstanceToken> getChildrenKaleoInstanceTokens() {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(getCompanyId());

		return KaleoInstanceTokenLocalServiceUtil.getKaleoInstanceTokens(
			getKaleoInstanceTokenId(), serviceContext);
	}

	@Override
	public KaleoNode getCurrentKaleoNode() throws PortalException {
		return KaleoNodeLocalServiceUtil.getKaleoNode(getCurrentKaleoNodeId());
	}

	@Override
	public List<KaleoInstanceToken> getIncompleteChildrenKaleoInstanceTokens() {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(getCompanyId());

		return KaleoInstanceTokenLocalServiceUtil.getKaleoInstanceTokens(
			getKaleoInstanceTokenId(), null, serviceContext);
	}

	@Override
	public KaleoInstance getKaleoInstance() throws PortalException {
		return KaleoInstanceLocalServiceUtil.getKaleoInstance(
			getKaleoInstanceId());
	}

	@Override
	public KaleoInstanceToken getParentKaleoInstanceToken()
		throws PortalException {

		return KaleoInstanceTokenLocalServiceUtil.getKaleoInstanceToken(
			getParentKaleoInstanceTokenId());
	}

	@Override
	public boolean hasIncompleteChildrenKaleoInstanceToken() {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(getCompanyId());

		int count =
			KaleoInstanceTokenLocalServiceUtil.getKaleoInstanceTokensCount(
				getKaleoInstanceTokenId(), null, serviceContext);

		if (count > 0) {
			return true;
		}

		return false;
	}

	@Override
	public void setCurrentKaleoNode(KaleoNode kaleoNode)
		throws PortalException {

		setCurrentKaleoNodeId(kaleoNode.getKaleoNodeId());

		KaleoInstanceTokenLocalServiceUtil.updateKaleoInstanceToken(
			getKaleoInstanceTokenId(), kaleoNode.getKaleoNodeId());
	}

}