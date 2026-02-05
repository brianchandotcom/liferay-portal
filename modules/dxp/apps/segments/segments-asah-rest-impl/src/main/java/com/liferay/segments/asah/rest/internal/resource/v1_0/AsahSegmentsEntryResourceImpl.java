/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.rest.internal.resource.v1_0;

import com.liferay.segments.asah.rest.resource.v1_0.AsahSegmentsEntryResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rachael Koestartyo
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/asah-segments-entry.properties",
	scope = ServiceScope.PROTOTYPE, service = AsahSegmentsEntryResource.class
)
public class AsahSegmentsEntryResourceImpl
	extends BaseAsahSegmentsEntryResourceImpl {
}