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

package com.liferay.portal.kernel.transaction;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Shuyang Zhou
 */
@ProviderType
public interface TransactionAttribute {

	public Isolation getIsolation();

	public Propagation getPropagation();

	public boolean isReadOnly();

	public boolean isStrictReadOnly();

	public static class Builder {

		public TransactionAttribute build() {
			return new DefaultTransactionAttribute(this);
		}

		public Builder setIsolation(Isolation isolation) {
			_isolation = isolation;

			return this;
		}

		public Builder setPropagation(Propagation propagation) {
			_propagation = propagation;

			return this;
		}

		public Builder setReadOnly(boolean readOnly) {
			_readOnly = readOnly;

			return this;
		}

		public Builder setStrictReadOnly(boolean strictReadOnly) {
			if (strictReadOnly) {
				_readOnly = true;
			}

			_strictReadOnly = strictReadOnly;

			return this;
		}

		private Isolation _isolation = Isolation.DEFAULT;
		private Propagation _propagation = Propagation.REQUIRED;
		private boolean _readOnly;
		private boolean _strictReadOnly;

	}

	public static class DefaultTransactionAttribute
		implements TransactionAttribute {

		@Override
		public Isolation getIsolation() {
			return _isolation;
		}

		@Override
		public Propagation getPropagation() {
			return _propagation;
		}

		@Override
		public boolean isReadOnly() {
			return _readOnly;
		}

		@Override
		public boolean isStrictReadOnly() {
			return _strictReadOnly;
		}

		private DefaultTransactionAttribute(Builder builder) {
			_isolation = builder._isolation;
			_propagation = builder._propagation;
			_readOnly = builder._readOnly;
			_strictReadOnly = builder._strictReadOnly;
		}

		private final Isolation _isolation;
		private final Propagation _propagation;
		private final boolean _readOnly;
		private final boolean _strictReadOnly;

	}

}