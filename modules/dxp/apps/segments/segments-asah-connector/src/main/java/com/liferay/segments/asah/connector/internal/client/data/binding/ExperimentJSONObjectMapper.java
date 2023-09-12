/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.client.data.binding;

import com.liferay.segments.asah.connector.internal.client.model.Experiment;

import java.io.IOException;

/**
 * @author Riccardo Ferrari
 */
public class ExperimentJSONObjectMapper {

	public Experiment map(String json) throws IOException {
		return AsahFaroBackendJSONObjectMapper.map(json, Experiment.class);
	}

}