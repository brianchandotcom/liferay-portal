/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.xstream;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Set;

/**
 * @author Daniel Kocsis
 */
public class XStreamConverterRegistryUtil {

	public static Set<XStreamConverter> getXStreamConverters() {
		return SetUtil.fromList(_xStreamConverters.toList());
	}

	private XStreamConverterRegistryUtil() {
	}

	private static final ServiceTrackerList<XStreamConverter>
		_xStreamConverters = ServiceTrackerListFactory.open(
			SystemBundleUtil.getBundleContext(), XStreamConverter.class);

}