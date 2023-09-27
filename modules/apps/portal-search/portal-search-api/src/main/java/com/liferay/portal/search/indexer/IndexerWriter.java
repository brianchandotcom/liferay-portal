/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public default void reindex(T baseModel, boolean notify) {
		reindex(baseModel);
	}

	public void setEnabled(boolean enabled);

	public void updatePermissionFields(T baseModel);

}