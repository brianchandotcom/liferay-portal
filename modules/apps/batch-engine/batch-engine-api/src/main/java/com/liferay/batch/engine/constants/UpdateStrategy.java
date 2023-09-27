/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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