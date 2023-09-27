/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.designer.web.internal.util.filter;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.workflow.constants.WorkflowDefinitionConstants;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author Inácio Nery
 */
public class KaleoDefinitionVersionScopePredicate
	implements Predicate<KaleoDefinitionVersion> {

	public KaleoDefinitionVersionScopePredicate(String scope) {
		_scope = scope;
	}

	@Override
	public boolean test(KaleoDefinitionVersion kaleoDefinitionVersion) {
		try {
			KaleoDefinition kaleoDefinition =
				kaleoDefinitionVersion.getKaleoDefinition();

			if (Validator.isNull(kaleoDefinition.getScope())) {
				return true;
			}

			return Objects.equals(_scope, kaleoDefinition.getScope());
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			if (_scope != WorkflowDefinitionConstants.SCOPE_ALL) {
				return false;
			}

			return true;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoDefinitionVersionScopePredicate.class);

	private final String _scope;

}