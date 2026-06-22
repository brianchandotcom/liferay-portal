/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audiences.web.internal.frontend.js.audiences;

import com.liferay.audiences.model.AudiencesEntry;
import com.liferay.audiences.service.AudiencesEntryLocalService;
import com.liferay.frontend.js.audiences.AudiencesDefinition;
import com.liferay.frontend.js.audiences.AudiencesDefinitionProvider;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.frontend.hashed.files.HashedFilesUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = AudiencesDefinitionProvider.class)
public class AudiencesDefinitionProviderImpl
	implements AudiencesDefinitionProvider {

	@Override
	public AudiencesDefinition getAudiencesDefinition(long companyId) {
		if (!FeatureFlagManagerUtil.isEnabled(companyId, "LPD-93951")) {
			return null;
		}

		JSONArray audiencesJSONArray = _jsonFactory.createJSONArray();

		List<AudiencesEntry> audiencesEntries =
			_audiencesEntryLocalService.getAudiencesEntries(
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		for (AudiencesEntry audiencesEntry : audiencesEntries) {
			audiencesJSONArray.put(audiencesEntry);
		}

		String json = JSONUtil.put(
			"audiences", audiencesJSONArray
		).toString();

		return new AudiencesDefinition(HashedFilesUtil.computeHash(json), json);
	}

	@Reference
	private AudiencesEntryLocalService _audiencesEntryLocalService;

	@Reference
	private JSONFactory _jsonFactory;

}