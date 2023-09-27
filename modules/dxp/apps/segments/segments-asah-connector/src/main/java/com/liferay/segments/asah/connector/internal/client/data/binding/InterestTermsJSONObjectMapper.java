/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.client.data.binding;

import com.liferay.segments.asah.connector.internal.client.model.Rels;
import com.liferay.segments.asah.connector.internal.client.model.Results;
import com.liferay.segments.asah.connector.internal.client.model.Topic;

import java.io.IOException;

/**
 * @author Sarai Díaz
 */
public class InterestTermsJSONObjectMapper {

	public Topic map(String json) throws IOException {
		return AsahFaroBackendJSONObjectMapper.map(json, Topic.class);
	}

	public Results<Topic> mapToResults(String json) throws IOException {
		return AsahFaroBackendJSONObjectMapper.mapToResults(
			json, Rels.INTEREST_TOPICS, Topic.class);
	}

}