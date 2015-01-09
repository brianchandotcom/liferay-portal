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

package com.liferay.registry.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author Raymond Augé
 */
public class StringPlus {

	public static List<String> asList(Object object) {
		if (object instanceof String) {
			return new ArrayList<>(Collections.singletonList((String)object));
		}
		else if (object instanceof String[]) {
			return new ArrayList<>(Arrays.asList((String[])object));
		}
		else if (object instanceof Collection) {
			Collection<?> collection = (Collection<?>)object;

			if (!collection.isEmpty()) {
				Iterator<?> iterator = collection.iterator();

				Object element = iterator.next();

				if (element instanceof String) {
					return new ArrayList<>((Collection<String>)object);
				}
			}
		}

		return new ArrayList<>();
	}

}