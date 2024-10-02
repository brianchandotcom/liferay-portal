/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.frontend.data.set.filter;

import com.liferay.frontend.data.set.filter.FDSFilter;

import org.osgi.service.component.annotations.Component;

/**
 * @author David Truong
 */
@Component(service = FDSFilter.class)
public class CTCollectionStatusSelectionFDSFilter
	extends WorkflowStatusSelectionFDSFilter {

	@Override
	public String getId() {
		return "ctCollectionStatus";
	}

}