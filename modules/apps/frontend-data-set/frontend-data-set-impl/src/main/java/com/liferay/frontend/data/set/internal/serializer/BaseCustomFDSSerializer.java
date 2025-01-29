/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.serializer;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.DefaultObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.DefaultObjectEntryManagerProvider;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Sanz
 */
public abstract class BaseCustomFDSSerializer {

	public Set<ObjectEntry> getCreationActionObjectEntries(
		String externalReferenceCode, HttpServletRequest httpServletRequest) {

		return _getSortedRelatedObjectEntries(
			getDataSetObjectDefinition(httpServletRequest),
			getDataSetObjectEntry(externalReferenceCode, httpServletRequest),
			"creationActionsOrder",
			(ObjectEntry objectEntry) -> Objects.equals(
				_getType(objectEntry), "creation"),
			"dataSetToDataSetActions");
	}

	public ObjectDefinition getDataSetObjectDefinition(
		HttpServletRequest httpServletRequest) {

		return dataSetObjectDefinitionLocalService.fetchObjectDefinition(
			portal.getCompanyId(httpServletRequest), "DataSet");
	}

	public ObjectEntry getDataSetObjectEntry(
		String externalReferenceCode, HttpServletRequest httpServletRequest) {

		return _getObjectEntry(
			getDataSetObjectDefinition(httpServletRequest),
			externalReferenceCode);
	}

	public Map<String, Object> getDataSetObjectEntryProperties(
		String externalReferenceCode, HttpServletRequest httpServletRequest) {

		ObjectEntry objectEntry = getDataSetObjectEntry(
			externalReferenceCode, httpServletRequest);

		if (objectEntry != null) {
			return objectEntry.getProperties();
		}

		return Collections.emptyMap();
	}

	public Set<ObjectEntry> getDataSetTableSectionObjectEntries(
		String externalReferenceCode, HttpServletRequest httpServletRequest) {

		return _getSortedRelatedObjectEntries(
			getDataSetObjectDefinition(httpServletRequest),
			getDataSetObjectEntry(externalReferenceCode, httpServletRequest),
			"tableSectionsOrder", (Predicate)null,
			"dataSetToDataSetTableSections");
	}

	public Set<ObjectEntry> getItemsActionsObjectEntries(
		String externalReferenceCode, HttpServletRequest httpServletRequest) {

		return _getSortedRelatedObjectEntries(
			getDataSetObjectDefinition(httpServletRequest),
			getDataSetObjectEntry(externalReferenceCode, httpServletRequest),
			"itemActionsOrder",
			(ObjectEntry objectEntry) -> Objects.equals(
				_getType(objectEntry), "item"),
			"dataSetToDataSetActions");
	}

	@Reference
	protected ObjectDefinitionLocalService dataSetObjectDefinitionLocalService;

	@Reference
	protected ObjectEntryManagerRegistry dataSetObjectEntryManagerRegistry;

	@Reference
	protected Portal portal;

	private ObjectEntry _getObjectEntry(
		ObjectDefinition dataSetObjectDefinition,
		String externalReferenceCode) {

		ObjectEntry objectEntry = null;

		DTOConverterContext dtoConverterContext =
			new DefaultDTOConverterContext(
				false, null, null, null, null,
				LocaleUtil.getMostRelevantLocale(), null, null);

		DefaultObjectEntryManager defaultObjectEntryManager =
			DefaultObjectEntryManagerProvider.provide(
				dataSetObjectEntryManagerRegistry.getObjectEntryManager(
					dataSetObjectDefinition.getStorageType()));

		try {
			objectEntry = defaultObjectEntryManager.getObjectEntry(
				dataSetObjectDefinition.getCompanyId(), dtoConverterContext,
				externalReferenceCode, dataSetObjectDefinition, null);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get data set object entry with external " +
						"reference code " + externalReferenceCode,
					exception);
			}
		}

		return objectEntry;
	}

	private Collection<ObjectEntry> _getRelatedObjectEntries(
		ObjectDefinition dataSetObjectDefinition,
		ObjectEntry dataSetObjectEntry, Predicate<ObjectEntry> predicate,
		String relationshipName) {

		Collection<ObjectEntry> objectEntries = null;

		DTOConverterContext dtoConverterContext =
			new DefaultDTOConverterContext(
				false, null, null, null, null,
				LocaleUtil.getMostRelevantLocale(), null, null);

		DefaultObjectEntryManager defaultObjectEntryManager =
			DefaultObjectEntryManagerProvider.provide(
				dataSetObjectEntryManagerRegistry.getObjectEntryManager(
					dataSetObjectDefinition.getStorageType()));

		try {
			Page<ObjectEntry> relatedObjectEntriesPage =
				defaultObjectEntryManager.getObjectEntryRelatedObjectEntries(
					dtoConverterContext, dataSetObjectDefinition,
					dataSetObjectEntry.getId(), relationshipName,
					Pagination.of(QueryUtil.ALL_POS, QueryUtil.ALL_POS));

			objectEntries = relatedObjectEntriesPage.getItems();

			if (predicate != null) {
				objectEntries.removeIf(
					objectEntry -> !predicate.test(objectEntry));
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get related object entries for " +
						relationshipName,
					exception);
			}
		}

		return objectEntries;
	}

	private Set<ObjectEntry> _getSortedRelatedObjectEntries(
		ObjectDefinition dataSetObjectDefinition,
		ObjectEntry dataSetObjectEntry,
		String dataSetObjectEntryComparatorIdsPropertyKey,
		Predicate<ObjectEntry> predicate, String... relationshipNames) {

		Set<ObjectEntry> objectEntries = new TreeSet<>(
			new ObjectEntryComparator(
				ListUtil.toList(
					ListUtil.fromString(
						MapUtil.getString(
							dataSetObjectEntry.getProperties(),
							dataSetObjectEntryComparatorIdsPropertyKey),
						StringPool.COMMA),
					GetterUtil::getLong)));

		for (String relationshipName : relationshipNames) {
			objectEntries.addAll(
				_getRelatedObjectEntries(
					dataSetObjectDefinition, dataSetObjectEntry, predicate,
					relationshipName));
		}

		return objectEntries;
	}

	private String _getType(ObjectEntry objectEntry) {
		Map<String, Object> properties = objectEntry.getProperties();

		return GetterUtil.getString(properties.get("type"));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseCustomFDSSerializer.class);

	private static class ObjectEntryComparator
		implements Comparator<ObjectEntry> {

		public ObjectEntryComparator(List<Long> ids) {
			_ids = ids;
		}

		@Override
		public int compare(
			ObjectEntry dataSetObjectEntry1, ObjectEntry dataSetObjectEntry2) {

			long id1 = dataSetObjectEntry1.getId();
			long id2 = dataSetObjectEntry2.getId();

			int index1 = _ids.indexOf(id1);
			int index2 = _ids.indexOf(id2);

			if ((index1 == -1) && (index2 == -1)) {
				Date date = dataSetObjectEntry1.getDateCreated();

				return date.compareTo(dataSetObjectEntry2.getDateCreated());
			}

			if (index1 == -1) {
				return 1;
			}

			if (index2 == -1) {
				return -1;
			}

			return Long.compare(index1, index2);
		}

		private final List<Long> _ids;

	}

}