/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.rule.internal.entry.type;

import com.liferay.commerce.order.rule.entry.type.COREntryTypeJSPContributor;
import com.liferay.commerce.order.rule.entry.type.COREntryTypeJSPContributorRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.function.transform.TransformUtil;

import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = COREntryTypeJSPContributorRegistry.class)
public class COREntryTypeJSPContributorRegistryImpl
	implements COREntryTypeJSPContributorRegistry {

	@Override
	public COREntryTypeJSPContributor getCOREntryTypeJSPContributor(
		String key) {

		return _serviceTrackerMap.getService(key);
	}

	@Override
	public List<COREntryTypeJSPContributor> getCOREntryTypeJSPContributors() {
		return Collections.unmodifiableList(
			TransformUtil.transform(
				_serviceTrackerMap.keySet(),
				key -> _serviceTrackerMap.getService(key)));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, COREntryTypeJSPContributor.class,
			"commerce.order.rule.entry.type.jsp.contributor.key");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, COREntryTypeJSPContributor>
		_serviceTrackerMap;

}