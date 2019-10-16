/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.segments.asah.connector.internal.client.data.binding;

import com.liferay.segments.asah.connector.internal.client.model.Individual;
import com.liferay.segments.asah.connector.internal.client.model.Rels;
import com.liferay.segments.asah.connector.internal.client.model.Results;

import java.io.IOException;

/**
 * @author David Arques
 */
public class IndividualJSONObjectMapper {

	public Individual map(String json) throws IOException {
		return AsahFaroBackendJSONObjectMapper.map(json, Individual.class);
	}

	public Results<Individual> mapToResults(String json) throws IOException {
		return AsahFaroBackendJSONObjectMapper.mapToResults(
			json, Rels.INDIVIDUAL_SEGMENT_INDIVIDUALS, Individual.class);
	}

}