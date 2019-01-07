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

package com.liferay.data.engine.service;

import com.liferay.data.engine.model.DEDataDefinition;

import java.util.List;

/**
 * @author Jeyvison Nascimento
 */
public class DEDataDefinitionListResponse {

	public List<DEDataDefinition> getDEDataDefinitions() {
		return _deDataDefinitions;
	}

	public static final class Builder {

		public static Builder newBuilder(
			List<DEDataDefinition> deDataDefinitions) {

			return new Builder(deDataDefinitions);
		}

		public static DEDataDefinitionListResponse of(
			List<DEDataDefinition> deDataDefinitions) {

			return newBuilder(
				deDataDefinitions
			).build();
		}

		public DEDataDefinitionListResponse build() {
			return _deDataDefinitionListResponse;
		}

		private Builder(List<DEDataDefinition> deDataDefinitions) {
			_deDataDefinitionListResponse._deDataDefinitions =
				deDataDefinitions;
		}

		private final DEDataDefinitionListResponse
			_deDataDefinitionListResponse = new DEDataDefinitionListResponse();

	}

	private DEDataDefinitionListResponse() {
	}

	private List<DEDataDefinition> _deDataDefinitions;

}