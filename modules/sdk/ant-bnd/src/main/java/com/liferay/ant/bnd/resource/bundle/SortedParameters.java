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

package com.liferay.ant.bnd.resource.bundle;

import aQute.bnd.header.Attrs;
import aQute.bnd.header.Parameters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Gregory Amerson
 */
public final class SortedParameters extends Parameters {

	public SortedParameters() {
	}

	public SortedParameters(String header) {
		super(header);
	}

	@Override
	public Set<Entry<String, Attrs>> entrySet() {
		List<Entry<String, Attrs>> entries = new ArrayList<>(super.entrySet());

		Comparator<Entry<String, Attrs>> comparator =
			new Comparator<Entry<String, Attrs>>() {

				@Override
				public int compare(
					Entry<String, Attrs> entry1, Entry<String, Attrs> entry2) {

					return entry1.getKey().compareTo(entry2.getKey());
				}

			};

		Collections.sort(entries, comparator);

		return new LinkedHashSet<>(entries);
	}

}