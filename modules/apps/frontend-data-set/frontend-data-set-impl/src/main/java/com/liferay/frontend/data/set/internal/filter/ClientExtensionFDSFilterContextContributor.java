/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.filter;

import com.liferay.frontend.data.set.filter.BaseClientExtensionFDSFilter;
import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.frontend.data.set.filter.FDSFilterContextContributor;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Iván Zaera Avellón
 */
@Component(
	property = "frontend.data.set.filter.type=clientExtension",
	service = FDSFilterContextContributor.class
)
public class ClientExtensionFDSFilterContextContributor
	implements FDSFilterContextContributor {

	@Override
	public Map<String, Object> getFDSFilterContext(
		FDSFilter fdsFilter, Locale locale) {

		if (fdsFilter instanceof BaseClientExtensionFDSFilter) {
			return _serialize((BaseClientExtensionFDSFilter)fdsFilter);
		}

		return Collections.emptyMap();
	}

	private Map<String, Object> _serialize(
		BaseClientExtensionFDSFilter baseClientExtensionFDSFilter) {

		return HashMapBuilder.<String, Object>put(
			"clientExtensionFilterURL",
			baseClientExtensionFDSFilter.getModuleURL()
		).build();
	}

}