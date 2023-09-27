/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.repository.capabilities;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.RepositoryModelOperation;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Adolfo Pérez
 */
@ProviderType
public interface BulkOperationCapability extends Capability {

	public void execute(
			Filter<?> filter, RepositoryModelOperation repositoryModelOperation)
		throws PortalException;

	public void execute(RepositoryModelOperation repositoryModelOperation)
		throws PortalException;

	public interface Field<T> {

		public interface CreateDate extends Field<Date> {
		}

		public interface FolderId extends Field<Long> {
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