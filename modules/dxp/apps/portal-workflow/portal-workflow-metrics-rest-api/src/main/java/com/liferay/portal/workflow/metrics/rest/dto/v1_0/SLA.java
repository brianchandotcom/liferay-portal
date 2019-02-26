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

package com.liferay.portal.workflow.metrics.rest.dto.v1_0;

import com.liferay.petra.function.UnsafeSupplier;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public interface SLA {

	public String getName();

	public void setName(
			String name);

	public void setName(
			UnsafeSupplier<String, Throwable>
				nameUnsafeSupplier);
	public String getDescription();

	public void setDescription(
			String description);

	public void setDescription(
			UnsafeSupplier<String, Throwable>
				descriptionUnsafeSupplier);
	public Long getDuration();

	public void setDuration(
			Long duration);

	public void setDuration(
			UnsafeSupplier<Long, Throwable>
				durationUnsafeSupplier);
	public Long getProcessId();

	public void setProcessId(
			Long processId);

	public void setProcessId(
			UnsafeSupplier<Long, Throwable>
				processIdUnsafeSupplier);
	public String[] getStartNodeNames();

	public void setStartNodeNames(
			String[] startNodeNames);

	public void setStartNodeNames(
			UnsafeSupplier<String[], Throwable>
				startNodeNamesUnsafeSupplier);
	public String[] getPauseNodeNames();

	public void setPauseNodeNames(
			String[] pauseNodeNames);

	public void setPauseNodeNames(
			UnsafeSupplier<String[], Throwable>
				pauseNodeNamesUnsafeSupplier);
	public String[] getStopNodeNames();

	public void setStopNodeNames(
			String[] stopNodeNames);

	public void setStopNodeNames(
			UnsafeSupplier<String[], Throwable>
				stopNodeNamesUnsafeSupplier);

}