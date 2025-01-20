/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.url;

import com.liferay.frontend.data.set.internal.serializer.CustomFDSSerializerHelper;
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
	property = "frontend.data.set.serializer.type=" + FDSSerializer.CUSTOM,
	service = FDSAPIURLSerializer.class
)
public class CustomFDSAPIURLSerializerImpl implements FDSAPIURLSerializer {

	@Override
	public String serialize(
		String fdsName, HttpServletRequest httpServletRequest) {

		Map<String, Object> properties =
			_customFDSSerializerHelper.getDataSetObjectEntryProperties(
				fdsName, httpServletRequest);

		FDSAPIURLBuilder fdsAPIURLBuilder = _fdsAPIURLBuilderFactory.create(
			httpServletRequest,
			String.valueOf(properties.get("restApplication")),
			String.valueOf(properties.get("restEndpoint")),
			String.valueOf(properties.get("restSchema")));

		return _addNestedFields(
			fdsAPIURLBuilder,
			_customFDSSerializerHelper.getDataSetTableSectionObjectEntries(
				fdsName, httpServletRequest)
		).build();
	}

	private FDSAPIURLBuilder _addNestedFields(
		FDSAPIURLBuilder fdsapiurlBuilder,
		Set<ObjectEntry> dataSetTableSectionObjectEntries) {

		if (dataSetTableSectionObjectEntries == null) {
			return fdsapiurlBuilder;
		}

		String nestedFields = StringPool.BLANK;
		int nestedFieldsDepth = 1;

		for (ObjectEntry fdsFieldObjectEntry :
				dataSetTableSectionObjectEntries) {

			Map<String, Object> properties =
				fdsFieldObjectEntry.getProperties();

			String[] fieldNameList = StringUtil.split(
				StringUtil.replace(
					String.valueOf(properties.get("fieldName")), "[]",
					StringPool.PERIOD),
				CharPool.PERIOD);

			if (fieldNameList.length > 1) {
				String[] fieldsName = new String[fieldNameList.length - 1];

				System.arraycopy(
					fieldNameList, 0, fieldsName, 0, fieldNameList.length - 1);

				for (String fieldName : fieldsName) {
					nestedFields = StringUtil.add(nestedFields, fieldName);
				}

				if (fieldNameList.length > nestedFieldsDepth) {
					nestedFieldsDepth = fieldNameList.length - 1;
				}
			}
		}

		if (nestedFields.equals(StringPool.BLANK)) {
			return fdsapiurlBuilder;
		}

		fdsapiurlBuilder.addParameter(
			"nestedFields",
			StringUtil.replaceLast(
				nestedFields, CharPool.COMMA, StringPool.BLANK));

		if (nestedFieldsDepth > 1) {
			fdsapiurlBuilder.addParameter(
				"nestedFieldsDepth", String.valueOf(nestedFieldsDepth));
		}

		return fdsapiurlBuilder;
	}

	@Reference
	private CustomFDSSerializerHelper _customFDSSerializerHelper;

	@Reference
	private FDSAPIURLBuilderFactory _fdsAPIURLBuilderFactory;

}