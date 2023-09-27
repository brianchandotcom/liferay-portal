/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.client.data.binding;

import com.liferay.segments.asah.connector.internal.client.model.IndividualSegment;
import com.liferay.segments.asah.connector.internal.client.model.Rels;
import com.liferay.segments.asah.connector.internal.client.model.Results;

import java.io.IOException;

/**
 * @author David Arques
 */
public class IndividualSegmentJSONObjectMapper {

	public IndividualSegment map(String json) throws IOException {
		return AsahFaroBackendJSONObjectMapper.map(
			json, IndividualSegment.class);
	}

	public Results<IndividualSegment> mapToResults(String json)
		throws IOException {

		return AsahFaroBackendJSONObjectMapper.mapToResults(
			json, Rels.INDIVIDUAL_SEGMENTS, IndividualSegment.class);
	}

}