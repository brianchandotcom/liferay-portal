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

package com.liferay.change.tracking.rest.client.dto.v1_0;

import com.liferay.change.tracking.rest.client.function.UnsafeSupplier;
import com.liferay.change.tracking.rest.client.serdes.v1_0.CollectionSerDes;

import java.util.Date;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Máté Thurzó
 * @generated
 */
@Generated("")
public class Collection {

	public Long getAdditionCount() {
		return additionCount;
	}

	public void setAdditionCount(Long additionCount) {
		this.additionCount = additionCount;
	}

	public void setAdditionCount(
		UnsafeSupplier<Long, Exception> additionCountUnsafeSupplier) {

		try {
			additionCount = additionCountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long additionCount;

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public void setCompanyId(
		UnsafeSupplier<Long, Exception> companyIdUnsafeSupplier) {

		try {
			companyId = companyIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long companyId;

	public Date getDateStatus() {
		return dateStatus;
	}

	public void setDateStatus(Date dateStatus) {
		this.dateStatus = dateStatus;
	}

	public void setDateStatus(
		UnsafeSupplier<Date, Exception> dateStatusUnsafeSupplier) {

		try {
			dateStatus = dateStatusUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date dateStatus;

	public Long getDeletionCount() {
		return deletionCount;
	}

	public void setDeletionCount(Long deletionCount) {
		this.deletionCount = deletionCount;
	}

	public void setDeletionCount(
		UnsafeSupplier<Long, Exception> deletionCountUnsafeSupplier) {

		try {
			deletionCount = deletionCountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long deletionCount;

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

	public Long getModificationCount() {
		return modificationCount;
	}

	public void setModificationCount(Long modificationCount) {
		this.modificationCount = modificationCount;
	}

	public void setModificationCount(
		UnsafeSupplier<Long, Exception> modificationCountUnsafeSupplier) {

		try {
			modificationCount = modificationCountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long modificationCount;

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

	public String getStatusByUserName() {
		return statusByUserName;
	}

	public void setStatusByUserName(String statusByUserName) {
		this.statusByUserName = statusByUserName;
	}

	public void setStatusByUserName(
		UnsafeSupplier<String, Exception> statusByUserNameUnsafeSupplier) {

		try {
			statusByUserName = statusByUserNameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String statusByUserName;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Collection)) {
			return false;
		}

		Collection collection = (Collection)object;

		return Objects.equals(toString(), collection.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return CollectionSerDes.toJSON(this);
	}

}