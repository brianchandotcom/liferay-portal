/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.xstream;

import com.liferay.portal.kernel.model.StagedModel;

import com.thoughtworks.xstream.security.TypePermission;

/**
 * @author Máté Thurzó
 */
public class XStreamStagedModelTypeHierarchyPermission
	implements TypePermission {

	public static final TypePermission STAGED_MODELS =
		new XStreamStagedModelTypeHierarchyPermission();

	@Override
	public boolean allows(Class type) {
		if (type == null) {
			return false;
		}

		return StagedModel.class.isAssignableFrom(type);
	}

}