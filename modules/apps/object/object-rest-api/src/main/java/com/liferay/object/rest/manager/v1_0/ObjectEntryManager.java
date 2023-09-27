/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.manager.v1_0;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Locale;
import java.util.Map;

/**
 * @author Gabriel Albuquerque
 */
public interface ObjectEntryManager {

	public ObjectEntry addObjectEntry(
			DTOConverterContext dtoConverterContext,
			ObjectDefinition objectDefinition, ObjectEntry objectEntry,
			String scopeKey)
		throws Exception;

	public void deleteObjectEntry(
			long companyId, DTOConverterContext dtoConverterContext,
			String externalReferenceCode, ObjectDefinition objectDefinition,
			String scopeKey)
		throws Exception;

	public Page<ObjectEntry> getObjectEntries(
			long companyId, ObjectDefinition objectDefinition, String scopeKey,
			Aggregation aggregation, DTOConverterContext dtoConverterContext,
			String filterString, Pagination pagination, String search,
			Sort[] sorts)
		throws Exception;

	public ObjectEntry getObjectEntry(
			long companyId, DTOConverterContext dtoConverterContext,
			String externalReferenceCode, ObjectDefinition objectDefinition,
			String scopeKey)
		throws Exception;

	public String getStorageLabel(Locale locale);

	public String getStorageType();

	public default ObjectEntry partialUpdateObjectEntry(
			long companyId, DTOConverterContext dtoConverterContext,
			String externalReferenceCode, ObjectDefinition objectDefinition,
			ObjectEntry objectEntry, String scopeKey)
		throws Exception {

		ObjectEntry existingObjectEntry = getObjectEntry(
			companyId, dtoConverterContext, externalReferenceCode,
			objectDefinition, scopeKey);

		if (objectEntry.getDateCreated() != null) {
			existingObjectEntry.setDateCreated(objectEntry.getDateCreated());
		}

		if (objectEntry.getDateModified() != null) {
			existingObjectEntry.setDateModified(objectEntry.getDateModified());
		}

		if (objectEntry.getKeywords() != null) {
			existingObjectEntry.setKeywords(objectEntry.getKeywords());
		}

		if (objectEntry.getProperties() != null) {
			Map<String, Object> properties =
				existingObjectEntry.getProperties();

			properties.putAll(objectEntry.getProperties());

			existingObjectEntry.setProperties(properties);
		}

		if (objectEntry.getStatus() != null) {
			existingObjectEntry.setStatus(objectEntry.getStatus());
		}

		if (objectEntry.getTaxonomyCategoryIds() != null) {
			existingObjectEntry.setTaxonomyCategoryIds(
				objectEntry.getTaxonomyCategoryIds());
		}

		return updateObjectEntry(
			companyId, dtoConverterContext, externalReferenceCode,
			objectDefinition, existingObjectEntry, scopeKey);
	}

	public ObjectEntry updateObjectEntry(
			long companyId, DTOConverterContext dtoConverterContext,
			String externalReferenceCode, ObjectDefinition objectDefinition,
			ObjectEntry objectEntry, String scopeKey)
		throws Exception;

}