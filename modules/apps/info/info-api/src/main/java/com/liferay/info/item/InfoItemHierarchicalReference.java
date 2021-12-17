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

package com.liferay.info.item;

import com.liferay.petra.string.StringBundler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Lourdes Fernández Besada
 */
public class InfoItemHierarchicalReference extends InfoItemReference {

	public InfoItemHierarchicalReference(
		String className, InfoItemIdentifier infoItemIdentifier) {

		super(className, infoItemIdentifier);
	}

	public InfoItemHierarchicalReference(String className, long classPK) {
		super(className, classPK);
	}

	@Override
	public boolean equals(Object object) {
		if (!super.equals(object) ||
			!(object instanceof InfoItemHierarchicalReference)) {

			return false;
		}

		InfoItemHierarchicalReference infoItemHierarchicalReference =
			(InfoItemHierarchicalReference)object;

		return Objects.equals(
			_children, infoItemHierarchicalReference._children);
	}

	public List<InfoItemHierarchicalReference> getChildren() {
		return _children;
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), _children);
	}

	public void setChildren(List<InfoItemHierarchicalReference> children) {
		_children = children;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{infoItemReference=", super.toString(), ", _children=", _children,
			"}");
	}

	private List<InfoItemHierarchicalReference> _children = new ArrayList<>();

}