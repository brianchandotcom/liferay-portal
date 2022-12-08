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
public enum CreateStrategy {

	CREATE_ONLY("only-add-new-records", false),
	UPDATE_ONLY("only-update-records", false),
	UPSERT("add-or-update-records", true);

	public static CreateStrategy getDefaultCreateStrategy() {
		for (CreateStrategy createStrategy : values()) {
			if (createStrategy._defaultStrategy) {
				return createStrategy;
			}
		}

		throw new IllegalStateException(
			"Unable to find default import strategy");
	}

	public String getLabel() {
		return _label;
	}

	public boolean isDefaultStrategy() {
		return _defaultStrategy;
	}

	private CreateStrategy(String label, boolean defaultStrategy) {
		_label = label;
		_defaultStrategy = defaultStrategy;
	}

	private final boolean _defaultStrategy;
	private final String _label;

}