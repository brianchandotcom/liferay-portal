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

package com.liferay.data.engine.storage;

import com.liferay.data.engine.model.DEDataRecord;

import java.util.Collections;
import java.util.List;

/**
 * Request class used to export data records from a data storage
 *
 * @author Leonardo Barros
 * @review
 */
public final class DEDataRecordExporterApplyRequest {

	/**
	 * Returns the {@link DEDataRecord}s to be exported
	 *
	 * @return The {@link DEDataRecord}s
	 * @review
	 */
	public List<DEDataRecord> getDEDataRecords() {
		return Collections.unmodifiableList(_deDataRecords);
	}

	/**
	 * Returns the export format
	 *
	 * @return The export format
	 * @review
	 */
	public String getFormat() {
		return _format;
	}

	/**
	 * Inner builder that assembles the request
	 * @review
	 */
	public static class Builder {

		/**
		 * Builder constructor that receives the data records as parameter
		 * @param deDataRecords The Data Records to be exported
		 * @review
		 */
		public static Builder newBuilder(List<DEDataRecord> deDataRecords) {
			return new Builder(deDataRecords);
		}

		/**
		 * Builds the request and return the {@link DEDataRecordExporterApplyRequest}
		 * object.
		 * @return the {@link DEDataRecordExporterApplyRequest} object.
		 * @review
		 */
		public DEDataRecordExporterApplyRequest build() {
			return _deDataRecordExporterApplyRequest;
		}

		/**
		 * The export format
		 *
		 * @param format
		 * @return the builder
		 * @review
		 */
		public Builder exportTo(String format) {
			_deDataRecordExporterApplyRequest._format = format;

			return this;
		}

		private Builder(List<DEDataRecord> deDataRecords) {
			_deDataRecordExporterApplyRequest._deDataRecords = deDataRecords;
		}

		private final DEDataRecordExporterApplyRequest
			_deDataRecordExporterApplyRequest =
				new DEDataRecordExporterApplyRequest();

	}

	private DEDataRecordExporterApplyRequest() {
	}

	private List<DEDataRecord> _deDataRecords;
	private String _format = "json";

}