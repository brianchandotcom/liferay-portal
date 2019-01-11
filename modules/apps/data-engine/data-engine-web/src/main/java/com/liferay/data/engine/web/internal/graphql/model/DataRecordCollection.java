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

package com.liferay.data.engine.web.internal.graphql.model;

import java.util.List;

/**
 * @author Leonardo Barros
 */
public interface DataRecordCollection {

	public DataDefinition getDataDefinition();

	public String getDataRecordCollectionId();

	public List<LocalizedValueType> getDescriptions();

	public List<LocalizedValueType> getNames();

	public void setDataDefinition(DataDefinition dataDefinition);

	public void setDataRecordCollectionId(String dataRecordCollectionId);

	public void setDescriptions(List<LocalizedValueType> descriptions);

	public void setNames(List<LocalizedValueType> names);

}