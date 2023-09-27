/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.item;

import com.liferay.petra.string.StringBundler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Lourdes Fernández Besada
 */
public class HierarchicalInfoItemReference extends InfoItemReference {

	public HierarchicalInfoItemReference(
		String className, InfoItemIdentifier infoItemIdentifier) {

		super(className, infoItemIdentifier);
	}

	public HierarchicalInfoItemReference(String className, long classPK) {
		super(className, classPK);
	}

	@Override
	public boolean equals(Object object) {
		if (!super.equals(object) ||
			!(object instanceof HierarchicalInfoItemReference)) {

			return false;
		}

		HierarchicalInfoItemReference hierarchicalInfoItemReference =
			(HierarchicalInfoItemReference)object;

		return Objects.equals(
			_childrenHierarchicalInfoItemReferences,
			hierarchicalInfoItemReference.
				_childrenHierarchicalInfoItemReferences);
	}

	public List<HierarchicalInfoItemReference>
		getChildrenHierarchicalInfoItemReferences() {

		return _childrenHierarchicalInfoItemReferences;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			super.hashCode(), _childrenHierarchicalInfoItemReferences);
	}

	public void setChildrenHierarchicalInfoItemReferences(
		List<HierarchicalInfoItemReference>
			childrenHierarchicalInfoItemReferences) {

		_childrenHierarchicalInfoItemReferences =
			childrenHierarchicalInfoItemReferences;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{childrenHierarchicalInfoItemReferences=",
			_childrenHierarchicalInfoItemReferences, ", infoItemReference=",
			super.toString(), "}");
	}

	private List<HierarchicalInfoItemReference>
		_childrenHierarchicalInfoItemReferences = new ArrayList<>();

}