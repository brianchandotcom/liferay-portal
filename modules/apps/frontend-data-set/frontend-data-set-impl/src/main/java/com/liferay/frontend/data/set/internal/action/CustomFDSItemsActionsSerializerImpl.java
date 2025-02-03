/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.action;

import com.liferay.frontend.data.set.action.FDSItemsActionsSerializer;
import com.liferay.frontend.data.set.internal.serializer.BaseCustomFDSSerializer;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.data.set.serializer.FDSSerializer;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Daniel Sanz
 * @author Marko Cikos
 */
@Component(
	property = "frontend.data.set.serializer.type=" + FDSSerializer.TYPE_CUSTOM,
	service = FDSItemsActionsSerializer.class
)
public class CustomFDSItemsActionsSerializerImpl
	extends BaseCustomFDSSerializer implements FDSItemsActionsSerializer {

	@Override
	public List<FDSActionDropdownItem> serialize(
		String fdsName, HttpServletRequest httpServletRequest) {

		return TransformUtil.transform(
			getItemsActionsObjectEntries(fdsName, httpServletRequest),
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

}