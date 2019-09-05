/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.workflow.kaleo.model.impl;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionLocalServiceUtil;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceLocalServiceUtil;
import com.liferay.portal.workflow.kaleo.service.KaleoNodeLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class KaleoDefinitionVersionImpl extends KaleoDefinitionVersionBaseImpl {

	public KaleoDefinitionVersionImpl() {
	}

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public KaleoDefinition fetchKaleoDefinition() {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(getCompanyId());

		return KaleoDefinitionLocalServiceUtil.fetchKaleoDefinition(
			getName(), serviceContext);
	}

	@Override
	public KaleoDefinition getKaleoDefinition() throws PortalException {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(getCompanyId());

		return KaleoDefinitionLocalServiceUtil.getKaleoDefinition(
			getName(), serviceContext);
	}

	@Override
	public KaleoNode getKaleoStartNode() throws PortalException {
		return KaleoNodeLocalServiceUtil.getKaleoNode(getStartKaleoNodeId());
	}

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public boolean hasIncompleteKaleoInstances() {
		int count = KaleoInstanceLocalServiceUtil.getKaleoInstancesCount(
			getKaleoDefinitionVersionId(), false);

		if (count > 0) {
			return true;
		}

		return false;
	}

	protected int getVersion(String version) {
		int[] versionParts = StringUtil.split(version, StringPool.PERIOD, 0);

		return versionParts[0];
	}

}