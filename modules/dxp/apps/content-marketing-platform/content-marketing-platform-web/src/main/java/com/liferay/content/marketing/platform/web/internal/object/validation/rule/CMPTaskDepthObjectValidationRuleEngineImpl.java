/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.marketing.platform.web.internal.object.validation.rule;

import com.liferay.content.marketing.platform.web.internal.util.CMPObjectEntryUtil;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.validation.rule.ObjectValidationRuleEngine;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Larissa Ribeiro
 */
@Component(service = ObjectValidationRuleEngine.class)
public class CMPTaskDepthObjectValidationRuleEngineImpl
	extends BaseCMPTaskObjectValidationRuleEngineImpl {

	@Override
	protected String getName() {
		return "depth";
	}

	@Override
	protected boolean hasValidationCriteriaMet(
			Long originalParentObjectEntryId, long parentObjectEntryId)
		throws Exception {

		if ((parentObjectEntryId == 0) ||
			!FeatureFlagManagerUtil.isEnabled(
				CompanyThreadLocal.getCompanyId(), "LPD-107678")) {

			return true;
		}

		ObjectEntry parentObjectEntry =
			_objectEntryLocalService.fetchObjectEntry(parentObjectEntryId);

		if (parentObjectEntry == null) {
			return true;
		}

		List<ObjectEntry> cmpTaskAncestorObjectEntries =
			CMPObjectEntryUtil.getCMPTaskAncestorObjectEntries(
				parentObjectEntry, _objectEntryLocalService);

		if ((cmpTaskAncestorObjectEntries.size() < 2) &&
			!cmpTaskAncestorObjectEntries.contains(parentObjectEntry)) {

			return true;
		}

		return false;
	}

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}