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

package com.liferay.batch.engine.constants;

/**
 * @author Igor Beslic
 */
public enum UpdateStrategy {

	PARTIAL_UPDATE("PARTIAL_UPDATE", "update-changed-record-fields", false),
	UPDATE("UPDATE", "overwrite-records", true);

	public static UpdateStrategy getDefaultUpdateStrategy() {
		for (UpdateStrategy updateStrategy : values()) {
			if (updateStrategy._defaultStrategy) {
				return updateStrategy;
			}
		}

		throw new IllegalStateException();
	}

	public String getDBOperation() {
		return _dbOperation;
	}

	public String getLabel() {
		return _label;
	}

	public boolean isDefaultStrategy() {
		return _defaultStrategy;
	}

	private UpdateStrategy(
		String dbOperation, String label, boolean defaultStrategy) {

		_dbOperation = dbOperation;
		_label = label;
		_defaultStrategy = defaultStrategy;
	}

	private final String _dbOperation;
	private final boolean _defaultStrategy;
	private final String _label;

}