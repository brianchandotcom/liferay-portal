/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.action;

import com.liferay.frontend.data.set.action.CreationMenuFDSSerializer;
import com.liferay.frontend.data.set.action.FDSCreationMenu;
import com.liferay.frontend.data.set.action.FDSCreationMenuRegistry;
import com.liferay.frontend.data.set.serializer.FDSSerializer;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Sanz
 */
@Component(
	property = "frontend.data.set.serializer.type=" + FDSSerializer.TYPE_SYSTEM,
	service = FDSSerializer.class
)
public class CreationMenuSystemFDSSerializerImpl
	implements CreationMenuFDSSerializer {

	@Override
	public CreationMenu serialize(
		String fdsName, HttpServletRequest httpServletRequest) {

		FDSCreationMenu fdsCreationMenu =
			_fdsCreationMenuRegistry.getFDSCreationMenu(fdsName);

		if (fdsCreationMenu == null) {
			return new CreationMenu();
		}

		return fdsCreationMenu.getCreationMenu(httpServletRequest);
	}

	@Reference
	private FDSCreationMenuRegistry _fdsCreationMenuRegistry;

}