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

/**
 * @author Jeyvison Nascimento
 */
public class DEDataDefinitionRequestBuilder {

	public static DEDataDefinitionCountRequest.Builder countBuilder() {
		return new DEDataDefinitionCountRequest.Builder();
	}

	public static DEDataDefinitionDeleteRequest.Builder deleteBuilder() {
		return new DEDataDefinitionDeleteRequest.Builder();
	}

	public static DEDataDefinitionGetRequest.Builder getBuilder() {
		return new DEDataDefinitionGetRequest.Builder();
	}

	public static DEDataDefinitionListRequest.Builder listBuilder() {
		return new DEDataDefinitionListRequest.Builder();
	}

	public static DEDataDefinitionSaveRequest.Builder saveBuilder(
		DEDataDefinition deDataDefinition) {

		return new DEDataDefinitionSaveRequest.Builder(deDataDefinition);
	}

}