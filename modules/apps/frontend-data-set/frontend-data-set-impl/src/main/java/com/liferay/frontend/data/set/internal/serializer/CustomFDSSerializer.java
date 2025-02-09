/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.serializer;

import com.liferay.frontend.data.set.internal.url.FDSAPIURLBuilder;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.data.set.serializer.FDSSerializer;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.DefaultObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.DefaultObjectEntryManagerProvider;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Sanz
 */
@Component(
	property = "frontend.data.set.serializer.type=" + FDSSerializer.TYPE_CUSTOM,
	service = FDSSerializer.class
)
public class CustomFDSSerializer
	extends BaseFDSSerializer implements FDSSerializer {

	@Override
	public String serializeAPIURL(
		String fdsName, HttpServletRequest httpServletRequest) {

		Map<String, Object> properties = getDataSetObjectEntryProperties(
			fdsName, httpServletRequest);

		FDSAPIURLBuilder fdsAPIURLBuilder = createFDSAPIURLBuilder(
			httpServletRequest,
			String.valueOf(properties.get("restApplication")),
			String.valueOf(properties.get("restEndpoint")),
			String.valueOf(properties.get("restSchema")));

		List<ObjectEntry> objectEntries = getSortedRelatedObjectEntries(
			fdsName, httpServletRequest, (Predicate)null, "tableSectionsOrder",
			"dataSetToDataSetTableSections");

		if (objectEntries == null) {
			return fdsAPIURLBuilder.build();
		}

		String nestedFields = StringPool.BLANK;
		int nestedFieldsDepth = 1;

		for (ObjectEntry objectEntry : objectEntries) {
			Map<String, Object> objectEntryProperties =
				objectEntry.getProperties();

			String[] fieldNames = StringUtil.split(
				StringUtil.replace(
					String.valueOf(objectEntryProperties.get("fieldName")),
					"[]", StringPool.PERIOD),
				CharPool.PERIOD);

			if (fieldNames.length > 1) {
				for (int i = 0; i < (fieldNames.length - 1); i++) {
					nestedFields = StringUtil.add(nestedFields, fieldNames[i]);
				}

				if (fieldNames.length > nestedFieldsDepth) {
					nestedFieldsDepth = fieldNames.length - 1;
				}
			}
		}

		if (nestedFields.equals(StringPool.BLANK)) {
			return fdsAPIURLBuilder.build();
		}

		fdsAPIURLBuilder.addParameter(
			"nestedFields",
			StringUtil.replaceLast(
				nestedFields, CharPool.COMMA, StringPool.BLANK));

		if (nestedFieldsDepth > 1) {
			fdsAPIURLBuilder.addParameter(
				"nestedFieldsDepth", String.valueOf(nestedFieldsDepth));
		}

		return fdsAPIURLBuilder.build();
	}

	@Override
	public List<FDSActionDropdownItem> serializeBulkActions(
		String fdsName, HttpServletRequest httpServletRequest) {

		// TODO

		return Collections.emptyList();
	}

	@Override
	public CreationMenu serializeCreationMenu(
		String fdsName, HttpServletRequest httpServletRequest) {

		CreationMenu creationMenu = new CreationMenu();

		List<DropdownItem> dropdownItems = TransformUtil.transform(
			getSortedRelatedObjectEntries(
				fdsName, httpServletRequest,
				(ObjectEntry objectEntry) -> Objects.equals(
					_getType(objectEntry), "creation"),
				"creationActionsOrder", "dataSetToDataSetActions"),
			objectEntry -> {
				Map<String, Object> properties = objectEntry.getProperties();

				return DropdownItemBuilder.putData(
					"disableHeader",
					String.valueOf(Validator.isNull(properties.get("title")))
				).putData(
					"permissionKey",
					String.valueOf(properties.get("permissionKey"))
				).putData(
					"size", String.valueOf(properties.get("modalSize"))
				).putData(
					"title", String.valueOf(properties.get("title"))
				).setHref(
					properties.get("url")
				).setIcon(
					String.valueOf(properties.get("icon"))
				).setLabel(
					String.valueOf(properties.get("label"))
				).setTarget(
					String.valueOf(properties.get("target"))
				).build();
			});

		for (DropdownItem dropdownItem : dropdownItems) {
			creationMenu.addPrimaryDropdownItem(dropdownItem);
		}

		return creationMenu;
	}

	@Override
	public List<FDSActionDropdownItem> serializeItemsActions(
		String fdsName, HttpServletRequest httpServletRequest) {

		return TransformUtil.transform(
			getSortedRelatedObjectEntries(
				fdsName, httpServletRequest,
				(ObjectEntry objectEntry) -> Objects.equals(
					_getType(objectEntry), "item"),
				"itemActionsOrder", "dataSetToDataSetActions"),
			objectEntry -> {
				Map<String, Object> properties = objectEntry.getProperties();

				FDSActionDropdownItem fdsActionDropdownItem =
					new FDSActionDropdownItem(
						String.valueOf(properties.get("confirmationMessage")),
						String.valueOf(properties.get("url")),
						String.valueOf(properties.get("icon")),
						objectEntry.getExternalReferenceCode(),
						String.valueOf(properties.get("label")),
						String.valueOf(properties.get("method")),
						String.valueOf(properties.get("permissionKey")),
						String.valueOf(properties.get("target")));

				fdsActionDropdownItem.putData(
					"disableHeader",
					(boolean)Validator.isNull(properties.get("title")));
				fdsActionDropdownItem.putData(
					"errorMessage", properties.get("errorMessage"));
				fdsActionDropdownItem.putData(
					"requestBody", properties.get("requestBody"));
				fdsActionDropdownItem.putData(
					"size", properties.get("modalSize"));
				fdsActionDropdownItem.putData(
					"status", properties.get("confirmationMessageType"));
				fdsActionDropdownItem.putData(
					"successMessage", properties.get("successMessage"));

				return fdsActionDropdownItem;
			});
	}

	protected Map<String, Object> getDataSetObjectEntryProperties(
		String externalReferenceCode, HttpServletRequest httpServletRequest) {

		ObjectEntry objectEntry = _getObjectEntry(
			externalReferenceCode, _getObjectDefinition(httpServletRequest));

		if (objectEntry != null) {
			return objectEntry.getProperties();
		}

		return Collections.emptyMap();
	}

	protected List<ObjectEntry> getSortedRelatedObjectEntries(
		String externalReferenceCode, HttpServletRequest httpServletRequest,
		Predicate<ObjectEntry> predicate, String propertyKey,
		String... relationshipNames) {

		List<ObjectEntry> objectEntries = new ArrayList<>();

		ObjectDefinition objectDefinition = _getObjectDefinition(
			httpServletRequest);

		ObjectEntry objectEntry = _getObjectEntry(
			externalReferenceCode, objectDefinition);

		for (String relationshipName : relationshipNames) {
			objectEntries.addAll(
				_getRelatedObjectEntries(
					objectDefinition, objectEntry, predicate,
					relationshipName));
		}

		objectEntries.sort(
			new ObjectEntryComparator(
				ListUtil.toList(
					ListUtil.fromString(
						MapUtil.getString(
							objectEntry.getProperties(), propertyKey),
						StringPool.COMMA),
					GetterUtil::getLong)));

		return objectEntries;
	}

	private ObjectDefinition _getObjectDefinition(
		HttpServletRequest httpServletRequest) {

		return _objectDefinitionLocalService.fetchObjectDefinition(
			_portal.getCompanyId(httpServletRequest), "DataSet");
	}

	private ObjectEntry _getObjectEntry(
		String externalReferenceCode, ObjectDefinition objectDefinition) {

		ObjectEntry objectEntry = null;

		DTOConverterContext dtoConverterContext =
			new DefaultDTOConverterContext(
				false, null, null, null, null,
				LocaleUtil.getMostRelevantLocale(), null, null);

		DefaultObjectEntryManager defaultObjectEntryManager =
			DefaultObjectEntryManagerProvider.provide(
				_objectEntryManagerRegistry.getObjectEntryManager(
					objectDefinition.getStorageType()));

		try {
			objectEntry = defaultObjectEntryManager.getObjectEntry(
				objectDefinition.getCompanyId(), dtoConverterContext,
				externalReferenceCode, objectDefinition, null);
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
		ObjectDefinition objectDefinition, ObjectEntry objectEntry,
		Predicate<ObjectEntry> predicate, String relationshipName) {

		Collection<ObjectEntry> objectEntries = null;

		DefaultObjectEntryManager defaultObjectEntryManager =
			DefaultObjectEntryManagerProvider.provide(
				_objectEntryManagerRegistry.getObjectEntryManager(
					objectDefinition.getStorageType()));

		try {
			Page<ObjectEntry> relatedObjectEntriesPage =
				defaultObjectEntryManager.getObjectEntryRelatedObjectEntries(
					new DefaultDTOConverterContext(
						false, null, null, null, null,
						LocaleUtil.getMostRelevantLocale(), null, null),
					objectDefinition, objectEntry.getId(), relationshipName,
					Pagination.of(QueryUtil.ALL_POS, QueryUtil.ALL_POS));

			objectEntries = relatedObjectEntriesPage.getItems();

			if (predicate != null) {
				objectEntries.removeIf(
					predicateObjectEntry -> !predicate.test(
						predicateObjectEntry));
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

	private String _getType(ObjectEntry objectEntry) {
		Map<String, Object> properties = objectEntry.getProperties();

		return GetterUtil.getString(properties.get("type"));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CustomFDSSerializer.class);

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryManagerRegistry _objectEntryManagerRegistry;

	@Reference
	private Portal _portal;

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