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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.Arrays;

/**
 * @author Shuyang Zhou
 */
public class TransactionConfig {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof TransactionConfig)) {
			return false;
		}

		TransactionConfig transactionConfig = (TransactionConfig)obj;

		if ((_isolation == transactionConfig._isolation) &&
			Arrays.equals(
				_noRollbackForClassNames,
				transactionConfig._noRollbackForClassNames) &&
			Arrays.equals(
				_noRollbackForClasses,
				transactionConfig._noRollbackForClasses) &&
			(_propagation == transactionConfig._propagation) &&
			(_readOnly == transactionConfig._readOnly) &&
			Arrays.equals(
				_rollbackForClassNames,
				transactionConfig._rollbackForClassNames) &&
			Arrays.equals(
				_rollbackForClasses, transactionConfig._rollbackForClasses) &&
			(_timeout == transactionConfig._timeout)) {

			return true;
		}

		return false;
	}

	public Isolation getIsolation() {
		return _isolation;
	}

	public Class<?>[] getNoRollbackForClasses() {
		return _noRollbackForClasses;
	}

	public String[] getNoRollbackForClassNames() {
		return _noRollbackForClassNames;
	}

	public Propagation getPropagation() {
		return _propagation;
	}

	public Class<?>[] getRollbackForClasses() {
		return _rollbackForClasses;
	}

	public String[] getRollbackForClassNames() {
		return _rollbackForClassNames;
	}

	public int getTimeout() {
		return _timeout;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _isolation);

		if (_noRollbackForClassNames == null) {
			hash = HashUtil.hash(hash, 0);
		}
		else {
			for (String noRollbackForClassName : _noRollbackForClassNames) {
				hash = HashUtil.hash(hash, noRollbackForClassName);
			}
		}

		if (_noRollbackForClasses == null) {
			hash = HashUtil.hash(hash, 0);
		}
		else {
			for (Class<?> noRollbackForClass : _noRollbackForClasses) {
				hash = HashUtil.hash(hash, noRollbackForClass);
			}
		}

		hash = HashUtil.hash(hash, _propagation);

		hash = HashUtil.hash(hash, _readOnly);

		if (_rollbackForClassNames == null) {
			hash = HashUtil.hash(hash, 0);
		}
		else {
			for (String rollbackForClassName : _rollbackForClassNames) {
				hash = HashUtil.hash(hash, rollbackForClassName);
			}
		}

		if (_rollbackForClasses == null) {
			hash = HashUtil.hash(hash, 0);
		}
		else {
			for (Class<?> rollbackForClass : _rollbackForClasses) {
				hash = HashUtil.hash(hash, rollbackForClass);
			}
		}

		hash = HashUtil.hash(hash, _timeout);

		return hash;
	}

	public boolean isReadOnly() {
		return _readOnly;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(17);

		sb.append("{isolation=");
		sb.append(_isolation);
		sb.append(", noRollbackForClassNames=");
		sb.append(Arrays.toString(_noRollbackForClassNames));
		sb.append(", noRollbackForClasses=");
		sb.append(Arrays.toString(_noRollbackForClasses));
		sb.append(", propagation=");
		sb.append(_propagation);
		sb.append(", readOnly=");
		sb.append(_readOnly);
		sb.append(", rollbackForClassNames=");
		sb.append(Arrays.toString(_rollbackForClassNames));
		sb.append(", rollbackForClasses=");
		sb.append(Arrays.toString(_rollbackForClasses));
		sb.append(", timeout=");
		sb.append(_timeout);
		sb.append(StringPool.CLOSE_CURLY_BRACE);

		return sb.toString();
	}

	public static class Builder {

		public TransactionConfig build() {
			return new TransactionConfig(this);
		}

		public Builder setIsolation(Isolation isolation) {
			_isolation = isolation;

			return this;
		}

		public Builder setNoRollbackForClasses(
			Class<?>... noRollbackForClasses) {

			_noRollbackForClasses = noRollbackForClasses;

			return this;
		}

		public Builder setNoRollbackForClassNames(
			String... noRollbackForClassNames) {

			_noRollbackForClassNames = noRollbackForClassNames;

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

		public Builder setRollbackForClasses(Class<?>... rollbackForClasses) {
			_rollbackForClasses = rollbackForClasses;

			return this;
		}

		public Builder setRollbackForClassNames(
			String... rollbackForClassNames) {

			_rollbackForClassNames = rollbackForClassNames;

			return this;
		}

		public Builder setTimeout(int timeout) {
			_timeout = timeout;

			return this;
		}

		private static final Class<?>[] _EMPTY_CLASS_ARRAY = new Class<?>[0];

		private Isolation _isolation = Isolation.DEFAULT;
		private Class<?>[] _noRollbackForClasses = _EMPTY_CLASS_ARRAY;
		private String[] _noRollbackForClassNames = StringPool.EMPTY_ARRAY;
		private Propagation _propagation = Propagation.REQUIRED;
		private boolean _readOnly;
		private Class<?>[] _rollbackForClasses = _EMPTY_CLASS_ARRAY;
		private String[] _rollbackForClassNames = StringPool.EMPTY_ARRAY;
		private int _timeout = TransactionDefinition.TIMEOUT_DEFAULT;

	}

	public static class Factory {

		public static TransactionConfig create(
			Isolation isolation, Propagation propagation, boolean readOnly,
			int timeout, Class<?>[] rollbackForClasses,
			String[] rollbackForClassNames, Class<?>[] noRollbackForClasses,
			String[] noRollbackForClassNames) {

			Builder builder = new Builder();

			builder.setIsolation(isolation);
			builder.setPropagation(propagation);
			builder.setReadOnly(readOnly);
			builder.setTimeout(timeout);
			builder.setRollbackForClasses(rollbackForClasses);
			builder.setRollbackForClassNames(rollbackForClassNames);
			builder.setNoRollbackForClasses(noRollbackForClasses);
			builder.setNoRollbackForClassNames(noRollbackForClassNames);

			return builder.build();
		}

		public static TransactionConfig create(
			Propagation propagation, Class<?>[] rollbackForClasses,
			Class<?>... noRollbackForClasses) {

			return create(
				Isolation.PORTAL, propagation, false, -1, rollbackForClasses,
				new String[0], noRollbackForClasses, new String[0]);
		}

	}

	private TransactionConfig(Builder builder) {
		_isolation = builder._isolation;
		_noRollbackForClassNames = builder._noRollbackForClassNames;
		_noRollbackForClasses = builder._noRollbackForClasses;
		_propagation = builder._propagation;
		_readOnly = builder._readOnly;
		_rollbackForClassNames = builder._rollbackForClassNames;
		_rollbackForClasses = builder._rollbackForClasses;
		_timeout = builder._timeout;
	}

	private final Isolation _isolation;
	private final Class<?>[] _noRollbackForClasses;
	private final String[] _noRollbackForClassNames;
	private final Propagation _propagation;
	private final boolean _readOnly;
	private final Class<?>[] _rollbackForClasses;
	private final String[] _rollbackForClassNames;
	private final int _timeout;

}