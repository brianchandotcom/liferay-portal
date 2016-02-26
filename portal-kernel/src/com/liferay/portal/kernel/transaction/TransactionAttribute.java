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

/**
 * @author Shuyang Zhou
 */
public interface TransactionAttribute {

	public Isolation getIsolation();

	public Propagation getPropagation();

	public boolean isReadOnly();

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

		private Isolation _isolation = Isolation.DEFAULT;
		private Propagation _propagation = Propagation.REQUIRED;
		private boolean _readOnly;

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

		private DefaultTransactionAttribute(Builder builder) {
			_isolation = builder._isolation;
			_propagation = builder._propagation;
			_readOnly = builder._readOnly;
		}

		private final Isolation _isolation;
		private final Propagation _propagation;
		private final boolean _readOnly;

	}

}