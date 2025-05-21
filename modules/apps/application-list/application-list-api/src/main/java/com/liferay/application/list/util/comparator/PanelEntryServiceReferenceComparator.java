/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.application.list.util.comparator;

import com.liferay.application.list.PanelApp;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.PanelEntry;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;

import java.util.Comparator;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author Thiago Buarque
 */
public class PanelEntryServiceReferenceComparator<T extends PanelEntry>
	implements Comparator<ServiceReference<T>> {

	public PanelEntryServiceReferenceComparator(
		BundleContext bundleContext, Log log, String propertyKey) {

		_bundleContext = bundleContext;
		_log = log;
		_propertyKey = propertyKey;

		_propertyServiceReferenceComparator =
			new PropertyServiceReferenceComparator<>(propertyKey);
	}

	@Override
	public int compare(
		ServiceReference<T> serviceReference1,
		ServiceReference<T> serviceReference2) {

		int value = _propertyServiceReferenceComparator.compare(
			serviceReference1, serviceReference2);

		if (value != 0) {
			return value;
		}

		T panelEntry1 = _bundleContext.getService(serviceReference1);
		T panelEntry2 = _bundleContext.getService(serviceReference2);

		String key1 = panelEntry1.getKey();
		String key2 = panelEntry2.getKey();

		String message = StringBundler.concat(
			"\" have the same order ",
			serviceReference1.getProperty(_propertyKey), " and category key \"",
			serviceReference1.getProperty("panel.category.key"), "\"");

		if ((panelEntry1 instanceof PanelApp) && _log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"The panel apps \"", key1, "\" and \"", key2, message));
		}
		else if ((panelEntry1 instanceof PanelCategory) &&
				 _log.isWarnEnabled()) {

			_log.warn(
				StringBundler.concat(
					"The panel categories \"", key1, "\" and \"", key2,
					message));
		}

		return key1.compareTo(key2);
	}

	private final BundleContext _bundleContext;
	private final Log _log;
	private final String _propertyKey;
	private final PropertyServiceReferenceComparator<T>
		_propertyServiceReferenceComparator;

}