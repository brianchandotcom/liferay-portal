/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.lar;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Julio Camarero
 */
public class MissingReferences {

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
		Map<String, MissingReference> missingReferencesMap,
		MissingReference missingReference) {

		String type = missingReference.getType();

		String key = null;

		if (type.equals(PortletDataContext.REFERENCE_TYPE_DEPENDENCY)) {
			key = missingReference.getDisplayName();
		}
		else if (type.equals(PortletDataContext.REFERENCE_TYPE_WEAK)) {
			key = missingReference.getReferrerClassName();
		}

		MissingReference existingMissingReference = missingReferencesMap.get(
			key);

		if (existingMissingReference != null) {
			existingMissingReference.addReferrers(
				missingReference.getReferrers());
		}
		else {
			missingReferencesMap.put(key, missingReference);
		}
	}

	private Map<String, MissingReference> _dependencyMissingReferences =
		new HashMap<String, MissingReference>();
	private Map<String, MissingReference> _weakMissingReferences =
		new HashMap<String, MissingReference>();

}