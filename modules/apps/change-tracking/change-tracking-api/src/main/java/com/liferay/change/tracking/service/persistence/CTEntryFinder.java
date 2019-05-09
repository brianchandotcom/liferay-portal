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

package com.liferay.change.tracking.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface CTEntryFinder {

	public int countByCTCollectionId(
		long ctCollectionId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.change.tracking.model.CTEntry> queryDefinition);

	public int countByRelatedCTEntries(
		long ctEntryId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.change.tracking.model.CTEntry> queryDefinition);

	public java.util.List<com.liferay.change.tracking.model.CTEntry>
		findByCTCollectionId(
			long ctCollectionId,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.change.tracking.model.CTEntry> queryDefinition);

	public java.util.List<com.liferay.change.tracking.model.CTEntry>
		findByRelatedCTEntries(
			long ctEntryId,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.change.tracking.model.CTEntry> queryDefinition);

	public java.util.List<com.liferay.change.tracking.model.CTEntry> findByC_C(
		long ctCollectionId, long modelClassNameId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.change.tracking.model.CTEntry> queryDefinition);

	public java.util.List<com.liferay.change.tracking.model.CTEntry> findByC_R(
		long ctCollectionId, long modelResourcePrimKey,
		com.liferay.portal.kernel.dao.orm.QueryDefinition
			<com.liferay.change.tracking.model.CTEntry> queryDefinition);

	public com.liferay.change.tracking.model.CTEntry findByC_C_C(
		long ctCollectionId, long modelClassNameId, long modelClassPK);

}