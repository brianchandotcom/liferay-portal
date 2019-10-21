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