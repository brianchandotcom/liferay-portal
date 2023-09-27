/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		Collections.sort(entries, _COMPARATOR);

		return new LinkedHashSet<>(entries);
	}

	private static final Comparator<Entry<String, Attrs>> _COMPARATOR =
		new Comparator<Entry<String, Attrs>>() {

			@Override
			public int compare(
				Entry<String, Attrs> entry1, Entry<String, Attrs> entry2) {

				String key1 = entry1.getKey();
				String key2 = entry2.getKey();

				return key1.compareTo(key2);
			}

		};

}