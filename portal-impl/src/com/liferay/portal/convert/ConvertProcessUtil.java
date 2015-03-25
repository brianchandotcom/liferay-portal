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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author Iván Zaera
 */
public class ConvertProcessUtil {

	public static Collection<ConvertProcess> getConvertProcesses() {
		try {
			Registry registry = RegistryUtil.getRegistry();

			return registry.getServices(ConvertProcess.class, null);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
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

}