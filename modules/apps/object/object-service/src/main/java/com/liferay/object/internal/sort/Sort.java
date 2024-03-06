/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.sort;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.petra.string.StringBundler;

/**
 * @author Carlos Correa
 */
public class Sort extends com.liferay.portal.kernel.search.Sort {

	public Sort(
		ObjectDefinition objectDefinition,
		com.liferay.portal.kernel.search.Sort sort) {

		super(
			sort.getFieldName(), sort.getFieldPath(), sort.getType(),
			sort.isReverse());

		_objectDefinition = objectDefinition;

		_originalSort = sort;
	}

	public ObjectDefinition getObjectDefinition() {
		return _objectDefinition;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{objectDefinition=", _objectDefinition, ", originalSort=",
			_originalSort);
	}

	private final ObjectDefinition _objectDefinition;
	private final com.liferay.portal.kernel.search.Sort _originalSort;

}