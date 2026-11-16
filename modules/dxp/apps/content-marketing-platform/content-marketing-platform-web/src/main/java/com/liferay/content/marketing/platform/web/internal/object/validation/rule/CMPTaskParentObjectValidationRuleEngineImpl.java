/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.marketing.platform.web.internal.object.validation.rule;

import com.liferay.object.validation.rule.ObjectValidationRuleEngine;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;

import org.osgi.service.component.annotations.Component;

/**
 * @author Larissa Ribeiro
 */
@Component(service = ObjectValidationRuleEngine.class)
public class CMPTaskParentObjectValidationRuleEngineImpl
	extends BaseCMPTaskObjectValidationRuleEngineImpl {

	@Override
	protected String getName() {
		return "parent";
	}

	@Override
	protected boolean hasValidationCriteriaMet(
		Long originalParentObjectEntryId, long parentObjectEntryId) {

		if (!FeatureFlagManagerUtil.isEnabled(
				CompanyThreadLocal.getCompanyId(), "LPD-107678")) {

			if (parentObjectEntryId == 0) {
				return true;
			}

			return false;
		}

		if ((originalParentObjectEntryId == null) ||
			(parentObjectEntryId == originalParentObjectEntryId)) {

			return true;
		}

		return false;
	}

}