/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.action;

import com.liferay.frontend.data.set.action.CreationMenuFDSSerializer;
import com.liferay.frontend.data.set.internal.serializer.BaseFDSSerializer;
import com.liferay.frontend.data.set.serializer.FDSSerializer;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemBuilder;
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
	service = FDSSerializer.class
)
public class CreationMenuFDSSerializerImpl
	extends BaseFDSSerializer implements CreationMenuFDSSerializer {

	@Override
	public CreationMenu serialize(
		String fdsName, HttpServletRequest httpServletRequest) {

		CreationMenu creationMenu = new CreationMenu();

		for (DropdownItem dropdownItem :
				_getDropdownItems(fdsName, httpServletRequest)) {

			creationMenu.addPrimaryDropdownItem(dropdownItem);
		}

		return creationMenu;
	}

	private List<DropdownItem> _getDropdownItems(
		String fdsName, HttpServletRequest httpServletRequest) {

		return TransformUtil.transform(
			getCreationMenuObjectEntries(fdsName, httpServletRequest),
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
	}

}