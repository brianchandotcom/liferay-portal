/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.object.entries.frontend.data.set.data.model;

/**
 * @author Marco Leo
 */
public class RelatedModel {

	public RelatedModel(
		String className, long id, String label, boolean system) {

		_className = className;
		_id = id;
		_label = label;
		_system = system;
	}

	public String getClassName() {
		return _className;
	}

	public long getId() {
		return _id;
	}

	public String getLabel() {
		return _label;
	}

	public Boolean isSystem() {
		return _system;
	}

	private final String _className;
	private final long _id;
	private final String _label;
	private final boolean _system;

}