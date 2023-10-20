/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.unit;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.util.Map;

/**
 * @author Raymond Augé
 */
public interface BatchEngineUnit {

	public BatchEngineUnitConfiguration getBatchEngineUnitConfiguration()
		throws IOException;

	public default BatchEngineUnitMetaInfo getBatchEngineUnitMetaInfo()
		throws IOException {

		BatchEngineUnitConfiguration batchEngineUnitConfiguration =
			getBatchEngineUnitConfiguration();

		String featureFlagKey = StringPool.BLANK;
		Map<String, Serializable> parameters =
			batchEngineUnitConfiguration.getParameters();

		if (parameters != null) {
			featureFlagKey = GetterUtil.getString(
				parameters.get("featureFlag"));
		}

		return new BatchEngineUnitMetaInfo(
			false, batchEngineUnitConfiguration.getCompanyId(), featureFlagKey,
			batchEngineUnitConfiguration.isMultiCompany(), null);
	}

	public InputStream getConfigurationInputStream() throws IOException;

	public String getDataFileName();

	public InputStream getDataInputStream() throws IOException;

	public String getFileName();

	public boolean isValid();

}