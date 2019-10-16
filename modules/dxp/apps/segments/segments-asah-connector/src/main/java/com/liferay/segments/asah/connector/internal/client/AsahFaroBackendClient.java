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

package com.liferay.segments.asah.connector.internal.client;

import com.liferay.segments.asah.connector.internal.client.model.DXPVariants;
import com.liferay.segments.asah.connector.internal.client.model.Experiment;
import com.liferay.segments.asah.connector.internal.client.model.ExperimentSettings;
import com.liferay.segments.asah.connector.internal.client.model.Individual;
import com.liferay.segments.asah.connector.internal.client.model.IndividualSegment;
import com.liferay.segments.asah.connector.internal.client.model.Results;
import com.liferay.segments.asah.connector.internal.client.model.Topic;
import com.liferay.segments.asah.connector.internal.client.util.OrderByField;

import java.util.List;

/**
 * @author Shinn Lok
 * @author David Arques
 * @author Sarai Díaz
 */
public interface AsahFaroBackendClient {

	/**
	 * Adds an {@link Experiment}.
	 *
	 * @param  experiment experiment to be created
	 * @return the created experiment
	 * @review
	 */
	public Experiment addExperiment(Experiment experiment);

	/**
	 * Calculates the estimated duration in days for the experiment
	 * <code>experimentId</code> given a {@link ExperimentSettings}
	 *
	 * @param  experimentId the ID of the experiment
	 * @param  experimentSettings settings for the calculation
	 * @return estimated duration for the experiment in days
	 * @review
	 */
	public Long calculateExperimentEstimatedDaysDuration(
		String experimentId, ExperimentSettings experimentSettings);

	/**
	 * Deletes an {@link Experiment}.
	 *
	 * @param  experimentId the ID of the experiment to be removed
	 * @review
	 */
	public void deleteExperiment(String experimentId);

	/**
	 * Returns the ID of the data source connected to the client.
	 *
	 * @return the ID of the data source connected to the client
	 */
	public String getDataSourceId();

	/**
	 * Returns the individual matching the primary key for the data source
	 * {@link #getDataSourceId()}.
	 *
	 * @param  individualPK the primary key of the individual
	 * @return the individual matching the primary key, or {@code null} if no
	 *         matches were found
	 */
	public Individual getIndividual(String individualPK);

	/**
	 * Returns the results of an individual that is a member of an individual
	 * segment.
	 *
	 * @param  individualSegmentId the individual segment's ID
	 * @param  cur the current page (one-based numbering)
	 * @param  delta the page size
	 * @param  orderByFields the sort fields
	 * @return the results of an individual that is a member of an individual
	 *         segment
	 */
	public Results<Individual> getIndividualResults(
		String individualSegmentId, int cur, int delta,
		List<OrderByField> orderByFields);

	/**
	 * Returns the results of an active individual segment with members.
	 *
	 * @param  cur the current page (one-based numbering)
	 * @param  delta the page size
	 * @param  orderByFields the sort fields
	 * @return the results of an active individual segment with members
	 */
	public Results<IndividualSegment> getIndividualSegmentResults(
		int cur, int delta, List<OrderByField> orderByFields);

	/**
	 * Returns the results for interest terms for the user.
	 *
	 * @param  userId the user's ID
	 * @return the results for interest terms for the user
	 */
	public Results<Topic> getInterestTermsResults(String userId);

	/**
	 * Updates an {@link Experiment}.
	 *
	 * @param  experiment experiment to be updated
	 * @review
	 */
	public void updateExperiment(Experiment experiment);

	/**
	 * Updates a set of {@link DXPVariants}.
	 *
	 * @param  experimentId the experiment ID
	 * @param  dxpVariants list of experiment variants
	 * @review
	 */
	public void updateExperimentDXPVariants(
		String experimentId, DXPVariants dxpVariants);

}