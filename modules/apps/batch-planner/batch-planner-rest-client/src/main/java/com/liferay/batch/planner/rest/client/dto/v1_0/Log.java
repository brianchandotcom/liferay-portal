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

package com.liferay.batch.planner.rest.client.dto.v1_0;

import com.liferay.batch.planner.rest.client.function.UnsafeSupplier;
import com.liferay.batch.planner.rest.client.serdes.v1_0.LogSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Matija Petanjek
 * @generated
 */
@Generated("")
public class Log implements Cloneable, Serializable {

	public static Log toDTO(String json) {
		return LogSerDes.toDTO(json);
	}

	public String getDispatchTriggerExternalReferenceCode() {
		return dispatchTriggerExternalReferenceCode;
	}

	public void setDispatchTriggerExternalReferenceCode(
		String dispatchTriggerExternalReferenceCode) {

		this.dispatchTriggerExternalReferenceCode =
			dispatchTriggerExternalReferenceCode;
	}

	public void setDispatchTriggerExternalReferenceCode(
		UnsafeSupplier<String, Exception>
			dispatchTriggerExternalReferenceCodeUnsafeSupplier) {

		try {
			dispatchTriggerExternalReferenceCode =
				dispatchTriggerExternalReferenceCodeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String dispatchTriggerExternalReferenceCode;

	public String getExportTaskExternalReferenceCode() {
		return exportTaskExternalReferenceCode;
	}

	public void setExportTaskExternalReferenceCode(
		String exportTaskExternalReferenceCode) {

		this.exportTaskExternalReferenceCode = exportTaskExternalReferenceCode;
	}

	public void setExportTaskExternalReferenceCode(
		UnsafeSupplier<String, Exception>
			exportTaskExternalReferenceCodeUnsafeSupplier) {

		try {
			exportTaskExternalReferenceCode =
				exportTaskExternalReferenceCodeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String exportTaskExternalReferenceCode;

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

	public String getImportTaskExternalReferenceCode() {
		return importTaskExternalReferenceCode;
	}

	public void setImportTaskExternalReferenceCode(
		String importTaskExternalReferenceCode) {

		this.importTaskExternalReferenceCode = importTaskExternalReferenceCode;
	}

	public void setImportTaskExternalReferenceCode(
		UnsafeSupplier<String, Exception>
			importTaskExternalReferenceCodeUnsafeSupplier) {

		try {
			importTaskExternalReferenceCode =
				importTaskExternalReferenceCodeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String importTaskExternalReferenceCode;

	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	public void setPlanId(
		UnsafeSupplier<Long, Exception> planIdUnsafeSupplier) {

		try {
			planId = planIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long planId;

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public void setSize(UnsafeSupplier<Integer, Exception> sizeUnsafeSupplier) {
		try {
			size = sizeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer size;

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

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public void setTotal(
		UnsafeSupplier<Integer, Exception> totalUnsafeSupplier) {

		try {
			total = totalUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer total;

	@Override
	public Log clone() throws CloneNotSupportedException {
		return (Log)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Log)) {
			return false;
		}

		Log log = (Log)object;

		return Objects.equals(toString(), log.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return LogSerDes.toJSON(this);
	}

}