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

package com.liferay.dynamic.data.mapping.io.exporter;

import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Locale;

/**
 * @author Leonardo Barros
 */
public final class DDMFormInstanceRecordExporterRequest {

	public long getDDMFormInstanceId() {
		return _ddmFormInstanceId;
	}

	public int getEnd() {
		return _end;
	}

	public Locale getLocale() {
		return _locale;
	}

	public OrderByComparator<DDMFormInstanceRecord> getOrderByComparator() {
		return _orderByComparator;
	}

	public int getStart() {
		return _start;
	}

	public int getStatus() {
		return _status;
	}

	public String getType() {
		return _type;
	}

	public static class Builder {

		public static Builder newBuilder(long ddmFormInstanceId, String type) {
			return new Builder(ddmFormInstanceId, type);
		}

		public DDMFormInstanceRecordExporterRequest build() {
			return _ddmFormInstanceRecordExporterRequest;
		}

		public Builder withEnd(int end) {
			_ddmFormInstanceRecordExporterRequest._end = end;

			return this;
		}

		public Builder withLocale(Locale locale) {
			_ddmFormInstanceRecordExporterRequest._locale = locale;

			return this;
		}

		public Builder withOrderByComparator(
			OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

			_ddmFormInstanceRecordExporterRequest._orderByComparator =
				orderByComparator;

			return this;
		}

		public Builder withStart(int start) {
			_ddmFormInstanceRecordExporterRequest._start = start;

			return this;
		}

		public Builder withStatus(int status) {
			_ddmFormInstanceRecordExporterRequest._status = status;

			return this;
		}

		private Builder(long ddmFormInstanceId, String type) {
			_ddmFormInstanceRecordExporterRequest._ddmFormInstanceId =
				ddmFormInstanceId;

			_ddmFormInstanceRecordExporterRequest._type = type;
		}

		private final DDMFormInstanceRecordExporterRequest
			_ddmFormInstanceRecordExporterRequest =
				new DDMFormInstanceRecordExporterRequest();

	}

	private DDMFormInstanceRecordExporterRequest() {
	}

	private long _ddmFormInstanceId;
	private int _end = QueryUtil.ALL_POS;
	private Locale _locale;
	private OrderByComparator<DDMFormInstanceRecord> _orderByComparator;
	private int _start = QueryUtil.ALL_POS;
	private int _status = WorkflowConstants.STATUS_ANY;
	private String _type;

}