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

import com.liferay.data.engine.model.DEDataLayout;

/**
 * @author Jeyvison Nascimento
 */
public class DEDataLayoutRequestBuilder {

	public static DEDataLayoutCountRequest.Builder countBuilder() {
		return new DEDataLayoutCountRequest.Builder();
	}

	public static DEDataLayoutDeleteRequest.Builder deleteBuilder() {
		return new DEDataLayoutDeleteRequest.Builder();
	}

	public static DEDataLayoutGetRequest.Builder getBuilder() {
		return new DEDataLayoutGetRequest.Builder();
	}

	public static DEDataLayoutListRequest.Builder listBuilder() {
		return new DEDataLayoutListRequest.Builder();
	}

	public static DEDataLayoutSaveRequest.Builder saveBuilder(
		DEDataLayout deDataLayout) {

		return new DEDataLayoutSaveRequest.Builder(deDataLayout);
	}

}