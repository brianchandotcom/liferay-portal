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

package com.liferay.dynamic.data.mapping.expression;

/**
 * @author Leonardo Barros
 */
public final class UpdateFieldPropertyResponse {

	public boolean isUpdated() {
		return _updated;
	}

	public static class Builder {

		public static Builder newBuilder(boolean updated) {
			return new Builder(updated);
		}

		public UpdateFieldPropertyResponse build() {
			return _updateFieldPropertyResponse;
		}

		private Builder(boolean updated) {
			_updateFieldPropertyResponse._updated = updated;
		}

		private final UpdateFieldPropertyResponse _updateFieldPropertyResponse =
			new UpdateFieldPropertyResponse();

	}

	private UpdateFieldPropertyResponse() {
	}

	private boolean _updated;

}