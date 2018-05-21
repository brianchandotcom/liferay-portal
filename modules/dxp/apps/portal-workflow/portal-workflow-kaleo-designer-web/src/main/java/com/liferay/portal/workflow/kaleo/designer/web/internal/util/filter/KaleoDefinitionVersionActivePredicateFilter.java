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

package com.liferay.portal.workflow.kaleo.designer.web.internal.util.filter;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.portal.workflow.kaleo.designer.web.internal.constants.KaleoDefinitionVersionConstants;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;

/**
 * @author Inácio Nery
 */
public class KaleoDefinitionVersionActivePredicateFilter
	implements PredicateFilter<KaleoDefinitionVersion> {

	public KaleoDefinitionVersionActivePredicateFilter(int status) {
		_status = status;
	}

	@Override
	public boolean filter(KaleoDefinitionVersion kaleoDefinitionVersion) {
		if (_status == KaleoDefinitionVersionConstants.STATUS_ALL) {
			return true;
		}

		try {
			KaleoDefinition kaleoDefinition =
				kaleoDefinitionVersion.getKaleoDefinition();

			if (_status == KaleoDefinitionVersionConstants.STATUS_PUBLISHED) {
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

			if (_status == KaleoDefinitionVersionConstants.STATUS_PUBLISHED) {
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