/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.serializer;

import com.liferay.frontend.data.set.SystemFDSEntry;
import com.liferay.frontend.data.set.SystemFDSEntryRegistry;
import com.liferay.frontend.data.set.action.FDSBulkActions;
import com.liferay.frontend.data.set.action.FDSBulkActionsRegistry;
import com.liferay.frontend.data.set.action.FDSCreationMenu;
import com.liferay.frontend.data.set.action.FDSCreationMenuRegistry;
import com.liferay.frontend.data.set.action.FDSItemsActions;
import com.liferay.frontend.data.set.action.FDSItemsActionsRegistry;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.data.set.serializer.FDSSerializer;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;

import java.util.Collections;
import java.util.List;

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
public class SystemFDSSerializer
	extends BaseFDSSerializer implements FDSSerializer {

	@Override
	public String serializeAPIURL(
		String fdsName, HttpServletRequest httpServletRequest) {

		SystemFDSEntry systemFDSEntry =
			_systemFDSEntryRegistry.getSystemFDSEntry(fdsName);

		if (systemFDSEntry == null) {
			return null;
		}

		return createFDSAPIURLBuilder(
			httpServletRequest, systemFDSEntry.getRESTApplication(),
			systemFDSEntry.getRESTEndpoint(), systemFDSEntry.getRESTSchema()
		).addQueryString(
			systemFDSEntry.getAdditionalAPIURLParameters()
		).build();
	}

	@Override
	public List<FDSActionDropdownItem> serializeBulkActions(
		String fdsName, HttpServletRequest httpServletRequest) {

		FDSBulkActions fdsBulkActions =
			_fdsBulkActionsRegistry.getFDSBulkActions(fdsName);

		if (fdsBulkActions == null) {
			return Collections.emptyList();
		}

		return fdsBulkActions.getFDSActionDropdownItems(httpServletRequest);
	}

	@Override
	public CreationMenu serializeCreationMenu(
		String fdsName, HttpServletRequest httpServletRequest) {

		FDSCreationMenu fdsCreationMenu =
			_fdsCreationMenuRegistry.getFDSCreationMenu(fdsName);

		if (fdsCreationMenu == null) {
			return new CreationMenu();
		}

		return fdsCreationMenu.getCreationMenu(httpServletRequest);
	}

	@Override
	public List<FDSActionDropdownItem> serializeItemsActions(
		String fdsName, HttpServletRequest httpServletRequest) {

		FDSItemsActions fdsItemsActions =
			_fdsItemsActionsRegistry.getFDSItemsActions(fdsName);

		if (fdsItemsActions == null) {
			return Collections.emptyList();
		}

		return fdsItemsActions.getFDSActionDropdownItems(httpServletRequest);
	}

	@Reference
	private FDSBulkActionsRegistry _fdsBulkActionsRegistry;

	@Reference
	private FDSCreationMenuRegistry _fdsCreationMenuRegistry;

	@Reference
	private FDSItemsActionsRegistry _fdsItemsActionsRegistry;

	@Reference
	private SystemFDSEntryRegistry _systemFDSEntryRegistry;

}