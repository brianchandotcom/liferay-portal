/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.serializer;

import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.data.set.serializer.FDSSerializer;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemBuilder;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

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

		return _addNestedFields(
			getSortedRelatedObjectEntries(
				fdsName, "tableSectionsOrder", httpServletRequest,
				(Predicate)null, "dataSetToDataSetTableSections"),
			createFDSAPIURLBuilder(
				httpServletRequest,
				String.valueOf(properties.get("restApplication")),
				String.valueOf(properties.get("restEndpoint")),
				String.valueOf(properties.get("restSchema")))
		).build();
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
				fdsName, "creationActionsOrder", httpServletRequest,
				(ObjectEntry objectEntry) -> Objects.equals(
					getType(objectEntry), "creation"),
				"dataSetToDataSetActions"),
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
				fdsName, "itemActionsOrder", httpServletRequest,
				(ObjectEntry objectEntry) -> Objects.equals(
					getType(objectEntry), "item"),
				"dataSetToDataSetActions"),
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

	private FDSAPIURLBuilder _addNestedFields(
		Set<ObjectEntry> dataSetTableSectionObjectEntries,
		FDSAPIURLBuilder fdsAPIURLBuilder) {

		if (dataSetTableSectionObjectEntries == null) {
			return fdsAPIURLBuilder;
		}

		String nestedFields = StringPool.BLANK;
		int nestedFieldsDepth = 1;

		for (ObjectEntry fdsFieldObjectEntry :
				dataSetTableSectionObjectEntries) {

			Map<String, Object> properties =
				fdsFieldObjectEntry.getProperties();

			String[] fieldNames = StringUtil.split(
				StringUtil.replace(
					String.valueOf(properties.get("fieldName")), "[]",
					StringPool.PERIOD),
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
			return fdsAPIURLBuilder;
		}

		fdsAPIURLBuilder.addParameter(
			"nestedFields",
			StringUtil.replaceLast(
				nestedFields, CharPool.COMMA, StringPool.BLANK));

		if (nestedFieldsDepth > 1) {
			fdsAPIURLBuilder.addParameter(
				"nestedFieldsDepth", String.valueOf(nestedFieldsDepth));
		}

		return fdsAPIURLBuilder;
	}

}