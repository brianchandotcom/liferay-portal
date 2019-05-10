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

package com.liferay.segments.asah.internal.client;

import com.liferay.segments.asah.internal.client.model.Individual;
import com.liferay.segments.asah.internal.client.model.IndividualSegment;
import com.liferay.segments.asah.internal.client.model.Results;
import com.liferay.segments.asah.internal.client.util.OrderByField;

import java.util.List;

/**
 * @author Shinn Lok
 * @author David Arques
 */
public interface AsahFaroBackendClient {

	/**
	 * Returns the ID of the data source connected to the client.
	 *
	 * @return the ID of the data source connected to the client
	 * @review
	 */
	public String getDataSourceId();

	/**
	 * Returns a {@link Results} of {@link Individual} that are members of an
	 * individual segment.
	 *
	 * @param  individualSegmentId the ID of the individual segment
	 * @param  cur the current page (one-based numbering)
	 * @param  delta the page size
	 * @param  orderByFields the sort fields
	 * @return a {@link Results} of {@link Individual} that are members of an
	 *         individual segment
	 * @review
	 */
	public Results<Individual> getIndividualResults(
		String individualSegmentId, int cur, int delta,
		List<OrderByField> orderByFields);

	/**
	 * Returns a {@link Results} of active {@link IndividualSegment} with
	 * members.
	 *
	 * @param  cur the current page (one-based numbering)
	 * @param  delta the page size
	 * @param  orderByFields the sort fields
	 * @return a {@link Results} of active {@link IndividualSegment} with
	 *         members
	 * @review
	 */
	public Results<IndividualSegment> getIndividualSegmentResults(
		int cur, int delta, List<OrderByField> orderByFields);

}