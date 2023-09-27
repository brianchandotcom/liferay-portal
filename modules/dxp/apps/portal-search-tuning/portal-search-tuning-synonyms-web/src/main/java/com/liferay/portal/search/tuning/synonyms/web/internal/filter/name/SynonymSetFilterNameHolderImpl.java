/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.filter.name;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.search.tuning.synonyms.web.internal.configuration.SynonymsConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Adam Brandizzi
 * @author Tibor Lipusz
 */
@Component(
	configurationPid = "com.liferay.portal.search.tuning.synonyms.web.internal.configuration.SynonymsConfiguration",
	service = SynonymSetFilterNameHolder.class
)
public class SynonymSetFilterNameHolderImpl
	implements SynonymSetFilterNameHolder {

	@Override
	public String[] getFilterNames() {
		return _filterNames;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		SynonymsConfiguration synonymsConfiguration =
			ConfigurableUtil.createConfigurable(
				SynonymsConfiguration.class, properties);

		_filterNames = synonymsConfiguration.filterNames();
	}

	private volatile String[] _filterNames;

}