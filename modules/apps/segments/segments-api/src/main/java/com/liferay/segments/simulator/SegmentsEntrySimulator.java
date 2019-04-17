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

package com.liferay.segments.simulator;

/**
 * Provides methods to simulate the membership of an entity to a
 * {@link SegmentsEntry}
 *
 * @author Eduardo García
 * @review
 */
public interface SegmentsEntrySimulator {

	public void deactivateSimulation(long classPK);

	public long[] getSimulatedSegmentsEntryIds(long classPK);

	public boolean isSimulationActive(long classPK);

	public void setSimulatedSegmentsEntryIds(
		long classPK, long[] segmentsEntryIds);

}