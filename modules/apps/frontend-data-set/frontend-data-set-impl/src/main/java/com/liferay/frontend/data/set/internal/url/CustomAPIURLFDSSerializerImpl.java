/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.url;

import com.liferay.frontend.data.set.internal.serializer.BaseCustomFDSSerializer;
import com.liferay.frontend.data.set.serializer.FDSSerializer;
import com.liferay.frontend.data.set.url.FDSAPIURLBuilder;
import com.liferay.frontend.data.set.url.FDSAPIURLBuilderFactory;
import com.liferay.frontend.data.set.url.FDSAPIURLSerializer;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Sanz
 */
@Component(
	property = "frontend.data.set.serializer.type=" + FDSSerializer.TYPE_CUSTOM,
	service = FDSAPIURLSerializer.class
)
public class CustomFDSAPIURLSerializerImpl
	extends BaseCustomFDSSerializer implements FDSAPIURLSerializer {

	@Override
	public String serialize(
		String fdsName, HttpServletRequest httpServletRequest) {

		Map<String, Object> properties = getDataSetObjectEntryProperties(
			fdsName, httpServletRequest);

		return _addNestedFields(
			getDataSetTableSectionObjectEntries(fdsName, httpServletRequest),
			_fdsAPIURLBuilderFactory.create(
				httpServletRequest,
				String.valueOf(properties.get("restApplication")),
				String.valueOf(properties.get("restEndpoint")),
				String.valueOf(properties.get("restSchema")))
		).build();
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

	@Reference
	private FDSAPIURLBuilderFactory _fdsAPIURLBuilderFactory;

}