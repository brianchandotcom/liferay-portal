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

package com.liferay.portal.kernel.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Shuyang Zhou
 */
public class CollectionUtil {

	public static <T> Set<T> intersect(Collection<T> c1, Collection<T> c2) {
		if (c1.isEmpty() || c2.isEmpty()) {
			return Collections.emptySet();
		}

		Set<T> s1 = null;

		if (c1 instanceof Set) {
			s1 = (Set<T>)c1;
		}
		else {
			s1 = new HashSet<T>(c1);
		}

		Set<T> s2 = null;

		if (c2 instanceof Set) {
			s2 = (Set<T>)c2;
		}
		else {
			s2 = new HashSet<T>(c2);
		}

		if (s1.size() > s2.size()) {
			s2.retainAll(s1);

			return s2;
		}
		else {
			s1.retainAll(s2);

			return s1;
		}
	}

}