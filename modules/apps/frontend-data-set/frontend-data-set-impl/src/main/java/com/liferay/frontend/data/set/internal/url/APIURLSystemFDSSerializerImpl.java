/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.url;

import com.liferay.frontend.data.set.SystemFDSEntry;
import com.liferay.frontend.data.set.SystemFDSEntryRegistry;
import com.liferay.frontend.data.set.serializer.FDSSerializer;

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
public class APIURLSystemFDSSerializerImpl extends BaseAPIURLFDSSerializer {

	@Override
	public String serialize(
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

	@Reference
	private SystemFDSEntryRegistry _systemFDSEntryRegistry;

}