/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.model.impl;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.cache.CacheField;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.workflow.kaleo.definition.util.WorkflowDefinitionContentUtil;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoTimer;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionLocalServiceUtil;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalServiceUtil;
import com.liferay.portal.workflow.kaleo.service.KaleoNodeLocalServiceUtil;
import com.liferay.portal.workflow.kaleo.service.KaleoTimerLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class KaleoDefinitionVersionImpl extends KaleoDefinitionVersionBaseImpl {

	@Override
	public String getContentAsXML() {
		if (_contentAsXML != null) {
			return _contentAsXML;
		}

		try {
			_contentAsXML = WorkflowDefinitionContentUtil.toXML(getContent());

			contentAsXMLUpdateEntityCacheBiConsumer.accept(this, _contentAsXML);
		}
		catch (WorkflowException workflowException) {
			ReflectionUtil.throwException(workflowException);
		}

		return _contentAsXML;
	}

	@Override
	public KaleoDefinition getKaleoDefinition() throws PortalException {
		return KaleoDefinitionLocalServiceUtil.getKaleoDefinition(
			getKaleoDefinitionId());
	}

	@Override
	public KaleoNode getKaleoStartNode() throws PortalException {
		return KaleoNodeLocalServiceUtil.getKaleoNode(getStartKaleoNodeId());
	}

	@Override
	public boolean isBlockingKaleoTimerExists() {
		if (_blockingKaleoTimerExists == null) {
			_computeKaleoTimerExists();
		}

		return _blockingKaleoTimerExists;
	}

	@Override
	public boolean isKaleoTimerExists() {
		if (_kaleoTimerExists == null) {
			_computeKaleoTimerExists();
		}

		return _kaleoTimerExists;
	}

	@Override
	public boolean isLatest() throws PortalException {
		KaleoDefinitionVersion kaleoDefinitionVersion =
			KaleoDefinitionVersionLocalServiceUtil.
				getLatestKaleoDefinitionVersion(getCompanyId(), getName());

		if (kaleoDefinitionVersion.getKaleoDefinitionVersionId() ==
				getKaleoDefinitionVersionId()) {

			return true;
		}

		return false;
	}

	protected int getVersion(String version) {
		int[] versionParts = StringUtil.split(version, StringPool.PERIOD, 0);

		return versionParts[0];
	}

	private void _computeKaleoTimerExists() {
		boolean blockingKaleoTimerExists = false;
		boolean kaleoTimerExists = false;

		for (KaleoTimer kaleoTimer :
				KaleoTimerLocalServiceUtil.getKaleoDefinitionVersionKaleoTimers(
					KaleoNode.class.getName(), getKaleoDefinitionVersionId())) {

			kaleoTimerExists = true;

			if (kaleoTimer.isBlocking()) {
				blockingKaleoTimerExists = true;

				break;
			}
		}

		_blockingKaleoTimerExists = blockingKaleoTimerExists;
		_kaleoTimerExists = kaleoTimerExists;

		blockingKaleoTimerExistsUpdateEntityCacheBiConsumer.accept(
			this, _blockingKaleoTimerExists);
		kaleoTimerExistsUpdateEntityCacheBiConsumer.accept(
			this, _kaleoTimerExists);
	}

	@CacheField(permanent = true, propagateToInterface = true)
	private Boolean _blockingKaleoTimerExists;

	@CacheField(permanent = true, propagateToInterface = true)
	private String _contentAsXML;

	@CacheField(permanent = true, propagateToInterface = true)
	private Boolean _kaleoTimerExists;

}