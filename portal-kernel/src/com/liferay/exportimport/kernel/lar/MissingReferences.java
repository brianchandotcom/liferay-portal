/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.lar;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Julio Camarero
 */
public class MissingReferences implements Serializable {

	public void add(MissingReference missingReference) {
		String type = missingReference.getType();

		if (type.equals(PortletDataContext.REFERENCE_TYPE_DEPENDENCY)) {
			add(_dependencyMissingReferences, missingReference);
		}
		else if (type.equals(PortletDataContext.REFERENCE_TYPE_WEAK)) {
			add(_weakMissingReferences, missingReference);
		}
	}

	public Map<String, MissingReference> getDependencyMissingReferences() {
		return _dependencyMissingReferences;
	}

	public Map<String, MissingReference> getWeakMissingReferences() {
		return _weakMissingReferences;
	}

	protected void add(
		Map<String, MissingReference> missingReferences,
		MissingReference missingReference) {

		String key = null;

		String type = missingReference.getType();

		if (type.equals(PortletDataContext.REFERENCE_TYPE_DEPENDENCY)) {
			key = missingReference.getDisplayName();
		}
		else if (type.equals(PortletDataContext.REFERENCE_TYPE_WEAK)) {
			key = missingReference.getReferrerClassName();
		}

		MissingReference existingMissingReference = missingReferences.get(key);

		if (existingMissingReference != null) {
			existingMissingReference.addReferrers(
				missingReference.getReferrers());
		}
		else {
			missingReferences.put(key, missingReference);
		}
	}

	private final Map<String, MissingReference> _dependencyMissingReferences =
		new HashMap<>();
	private final Map<String, MissingReference> _weakMissingReferences =
		new HashMap<>();

}