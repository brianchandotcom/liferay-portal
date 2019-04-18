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

package com.liferay.headless.admin.user.client.dto.v1_0;

import com.liferay.headless.admin.user.client.function.UnsafeSupplier;

import java.util.Date;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Segment {

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public void setActive(
		UnsafeSupplier<Boolean, Exception> activeUnsafeSupplier) {

		try {
			active = activeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean active;

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public void setCriteria(
		UnsafeSupplier<String, Exception> criteriaUnsafeSupplier) {

		try {
			criteria = criteriaUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String criteria;

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public void setDateCreated(
		UnsafeSupplier<Date, Exception> dateCreatedUnsafeSupplier) {

		try {
			dateCreated = dateCreatedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date dateCreated;

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

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public void setSiteId(
		UnsafeSupplier<Long, Exception> siteIdUnsafeSupplier) {

		try {
			siteId = siteIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long siteId;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setSource(
		UnsafeSupplier<String, Exception> sourceUnsafeSupplier) {

		try {
			source = sourceUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String source;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Segment)) {
			return false;
		}

		Segment segment = (Segment)object;

		return Objects.equals(toString(), segment.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"active\": ");

		if (active == null) {
			sb.append("null");
		}
		else {
			sb.append(active);
		}

		sb.append(", ");

		sb.append("\"criteria\": ");

		if (criteria == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(criteria);
			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"dateCreated\": ");

		if (dateCreated == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(dateCreated);
			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"dateModified\": ");

		if (dateModified == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(dateModified);
			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"id\": ");

		if (id == null) {
			sb.append("null");
		}
		else {
			sb.append(id);
		}

		sb.append(", ");

		sb.append("\"name\": ");

		if (name == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(name);
			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"siteId\": ");

		if (siteId == null) {
			sb.append("null");
		}
		else {
			sb.append(siteId);
		}

		sb.append(", ");

		sb.append("\"source\": ");

		if (source == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(source);
			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

}