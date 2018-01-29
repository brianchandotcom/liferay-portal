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

package com.liferay.portal.workflow.kaleo.forms.web.internal.search;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;

/**
 * @author Lino Alves
 */
public class KaleoDefinitionVersionActivePredicateFilter
	implements PredicateFilter<KaleoDefinitionVersion> {

	public KaleoDefinitionVersionActivePredicateFilter(int status) {
		_status = status;
	}

	@Override
	public boolean filter(KaleoDefinitionVersion kaleoDefinitionVersion) {
		if (_status == WorkflowConstants.STATUS_ANY) {
			return true;
		}

		try {
			KaleoDefinition kaleoDefinition =
				kaleoDefinitionVersion.getKaleoDefinition();

			if (_status == WorkflowConstants.STATUS_APPROVED) {
				return kaleoDefinition.isActive();
			}
			else {
				return !kaleoDefinition.isActive();
			}
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			if (_status == WorkflowConstants.STATUS_DRAFT) {
				return false;
			}
			else {
				return true;
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoDefinitionVersionActivePredicateFilter.class);

	private final int _status;

}