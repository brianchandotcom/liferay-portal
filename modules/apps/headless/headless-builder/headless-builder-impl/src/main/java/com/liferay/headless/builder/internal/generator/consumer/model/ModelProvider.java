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

package com.liferay.headless.builder.internal.generator.consumer.model;

import java.util.List;

/**
 * @author Luis Miguel Barcos
 * @author Carlos Correa
 */
public interface ModelProvider {

	public <T extends BaseModel> T getModel(
			String apiApplicationBaseURL, Class<T> clazz)
		throws Exception;

	public <T extends BaseModel> T getModelByApiApplicationBaseURL(
			String apiApplicationBaseURL, Class<T> clazz)
		throws Exception;

	public <T extends BaseModel> List<T> getModels(
			String apiApplicationERC, Class<T> clazz)
		throws Exception;

}