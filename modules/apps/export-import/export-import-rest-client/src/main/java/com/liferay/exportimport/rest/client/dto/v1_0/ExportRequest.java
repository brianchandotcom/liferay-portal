/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.client.dto.v1_0;

import com.liferay.exportimport.rest.client.function.UnsafeSupplier;
import com.liferay.exportimport.rest.client.serdes.v1_0.ExportRequestSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Date;
import java.util.Objects;

/**
 * @author Petteri Karttunen
 * @generated
 */
@Generated("")
public class ExportRequest implements Cloneable, Serializable {

	public static ExportRequest toDTO(String json) {
		return ExportRequestSerDes.toDTO(json);
	}

	public Boolean getDeletions() {
		return deletions;
	}

	public void setDeletions(Boolean deletions) {
		this.deletions = deletions;
	}

	public void setDeletions(
		UnsafeSupplier<Boolean, Exception> deletionsUnsafeSupplier) {

		try {
			deletions = deletionsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean deletions;

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setEndDate(
		UnsafeSupplier<Date, Exception> endDateUnsafeSupplier) {

		try {
			endDate = endDateUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date endDate;

	public Integer getLast() {
		return last;
	}

	public void setLast(Integer last) {
		this.last = last;
	}

	public void setLast(UnsafeSupplier<Integer, Exception> lastUnsafeSupplier) {
		try {
			last = lastUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer last;

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

	public Boolean getPermissions() {
		return permissions;
	}

	public void setPermissions(Boolean permissions) {
		this.permissions = permissions;
	}

	public void setPermissions(
		UnsafeSupplier<Boolean, Exception> permissionsUnsafeSupplier) {

		try {
			permissions = permissionsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean permissions;

	public Range getRange() {
		return range;
	}

	public String getRangeAsString() {
		if (range == null) {
			return null;
		}

		return range.toString();
	}

	public void setRange(Range range) {
		this.range = range;
	}

	public void setRange(UnsafeSupplier<Range, Exception> rangeUnsafeSupplier) {
		try {
			range = rangeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Range range;

	public RequestPortletDataHandler[] getRequestPortletDataHandlers() {
		return requestPortletDataHandlers;
	}

	public void setRequestPortletDataHandlers(
		RequestPortletDataHandler[] requestPortletDataHandlers) {

		this.requestPortletDataHandlers = requestPortletDataHandlers;
	}

	public void setRequestPortletDataHandlers(
		UnsafeSupplier<RequestPortletDataHandler[], Exception>
			requestPortletDataHandlersUnsafeSupplier) {

		try {
			requestPortletDataHandlers =
				requestPortletDataHandlersUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected RequestPortletDataHandler[] requestPortletDataHandlers;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setStartDate(
		UnsafeSupplier<Date, Exception> startDateUnsafeSupplier) {

		try {
			startDate = startDateUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date startDate;

	@Override
	public ExportRequest clone() throws CloneNotSupportedException {
		return (ExportRequest)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ExportRequest)) {
			return false;
		}

		ExportRequest exportRequest = (ExportRequest)object;

		return Objects.equals(toString(), exportRequest.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ExportRequestSerDes.toJSON(this);
	}

	public static enum Range {

		ALL("all"), DATE_RANGE("dateRange"), LAST("last");

		public static Range create(String value) {
			for (Range range : values()) {
				if (Objects.equals(range.getValue(), value) ||
					Objects.equals(range.name(), value)) {

					return range;
				}
			}

			return null;
		}

		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private Range(String value) {
			_value = value;
		}

		private final String _value;

	}

}
// LIFERAY-REST-BUILDER-HASH:1542603122