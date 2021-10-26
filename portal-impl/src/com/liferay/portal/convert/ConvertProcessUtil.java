/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.convert;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author Iván Zaera
 */
public class ConvertProcessUtil {

	public static Collection<ConvertProcess> getConvertProcesses() {
		List<ConvertProcess> convertProcesses = new ArrayList<>();

		_convertProcesses.forEach(convertProcesses::add);

		return convertProcesses;
	}

	public static Collection<ConvertProcess> getEnabledConvertProcesses() {
		Collection<ConvertProcess> convertProcesses = new ArrayList<>(
			getConvertProcesses());

		Iterator<ConvertProcess> iterator = convertProcesses.iterator();

		while (iterator.hasNext()) {
			ConvertProcess convertProcess = iterator.next();

			if (!convertProcess.isEnabled()) {
				iterator.remove();
			}
		}

		return convertProcesses;
	}

	private static final ServiceTrackerList<ConvertProcess> _convertProcesses =
		ServiceTrackerListFactory.open(
			SystemBundleUtil.getBundleContext(), ConvertProcess.class);

}