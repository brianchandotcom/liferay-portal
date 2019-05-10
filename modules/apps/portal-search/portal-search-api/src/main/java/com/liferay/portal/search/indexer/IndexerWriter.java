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

package com.liferay.portal.search.indexer;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.search.batch.BatchIndexingActionable;

import java.util.Collection;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface IndexerWriter<T extends BaseModel<?>> {

	public void delete(long companyId, String uid);

	public void delete(T baseModel);

	public BatchIndexingActionable getBatchIndexingActionable();

	public boolean isEnabled();

	public void reindex(Collection<T> baseModels);

	public void reindex(long classPK);

	public void reindex(String[] ids);

	public void reindex(T baseModel);

	public void setEnabled(boolean enabled);

	public void updatePermissionFields(T baseModel);

}