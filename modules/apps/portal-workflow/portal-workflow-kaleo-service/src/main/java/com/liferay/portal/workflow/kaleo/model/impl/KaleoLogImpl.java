/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.model.impl;

import com.liferay.portal.kernel.search.IndexableAware;
import com.liferay.portal.workflow.constants.WorkflowDefinitionConstants;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionLocalServiceUtil;

import java.util.Objects;

/**
 * @author Brian Wing Shun Chan
 */
public class KaleoLogImpl extends KaleoLogBaseImpl implements IndexableAware {

	@Override
	public boolean shouldIndex() {
		KaleoDefinition kaleoDefinition =
			KaleoDefinitionLocalServiceUtil.fetchKaleoDefinition(
				getKaleoDefinitionId());

		if ((kaleoDefinition != null) &&
			Objects.equals(
				WorkflowDefinitionConstants.SCOPE_AI,
				kaleoDefinition.getScope())) {

			return false;
		}

		return true;
	}

}