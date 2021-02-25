/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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