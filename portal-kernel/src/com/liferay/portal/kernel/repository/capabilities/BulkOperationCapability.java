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

package com.liferay.portal.kernel.repository.capabilities;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.RepositoryModelOperation;

import java.util.Date;

/**
 * @author Adolfo Pérez
 */
public interface BulkOperationCapability extends Capability {

	public void execute(
			Filter<?> filter, RepositoryModelOperation repositoryModelOperation)
		throws PortalException;

	public void execute(RepositoryModelOperation repositoryModelOperation)
		throws PortalException;

	public interface Field<T> {

		public interface CreateDate extends Field<Date> {
		}

	}

	public class Filter<T> {

		public Filter(
			Class<? extends Field<T>> field, Operator operator, T value) {

			_field = field;
			_operator = operator;
			_value = value;
		}

		public Class<? extends Field<T>> getField() {
			return _field;
		}

		public Operator getOperator() {
			return _operator;
		}

		public T getValue() {
			return _value;
		}

		private final Class<? extends Field<T>> _field;
		private final Operator _operator;
		private final T _value;

	}

	public enum Operator {

		EQ, GE, GT, LE, LT

	}

}