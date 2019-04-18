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

package com.liferay.portal.workflow.metrics.rest.client.dto.v1_0;

import com.liferay.portal.workflow.metrics.rest.client.function.UnsafeSupplier;

import java.util.Date;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class SLA {

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public void setDateModified(
		UnsafeSupplier<Date, Exception> dateModifiedUnsafeSupplier) {

		try {
			dateModified = dateModifiedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date dateModified;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDescription(
		UnsafeSupplier<String, Exception> descriptionUnsafeSupplier) {

		try {
			description = descriptionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String description;

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public void setDuration(
		UnsafeSupplier<Long, Exception> durationUnsafeSupplier) {

		try {
			duration = durationUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long duration;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String name;

	public String[] getPauseNodeKeys() {
		return pauseNodeKeys;
	}

	public void setPauseNodeKeys(String[] pauseNodeKeys) {
		this.pauseNodeKeys = pauseNodeKeys;
	}

	public void setPauseNodeKeys(
		UnsafeSupplier<String[], Exception> pauseNodeKeysUnsafeSupplier) {

		try {
			pauseNodeKeys = pauseNodeKeysUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String[] pauseNodeKeys;

	public Long getProcessId() {
		return processId;
	}

	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	public void setProcessId(
		UnsafeSupplier<Long, Exception> processIdUnsafeSupplier) {

		try {
			processId = processIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long processId;

	public String[] getStartNodeKeys() {
		return startNodeKeys;
	}

	public void setStartNodeKeys(String[] startNodeKeys) {
		this.startNodeKeys = startNodeKeys;
	}

	public void setStartNodeKeys(
		UnsafeSupplier<String[], Exception> startNodeKeysUnsafeSupplier) {

		try {
			startNodeKeys = startNodeKeysUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String[] startNodeKeys;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setStatus(
		UnsafeSupplier<Integer, Exception> statusUnsafeSupplier) {

		try {
			status = statusUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer status;

	public String[] getStopNodeKeys() {
		return stopNodeKeys;
	}

	public void setStopNodeKeys(String[] stopNodeKeys) {
		this.stopNodeKeys = stopNodeKeys;
	}

	public void setStopNodeKeys(
		UnsafeSupplier<String[], Exception> stopNodeKeysUnsafeSupplier) {

		try {
			stopNodeKeys = stopNodeKeysUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String[] stopNodeKeys;

}