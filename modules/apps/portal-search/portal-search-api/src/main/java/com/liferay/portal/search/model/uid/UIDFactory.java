/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.model.uid;

import com.liferay.portal.kernel.model.ClassedModel;

import java.io.Serializable;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author André de Oliveira
 */
@ProviderType
public interface UIDFactory {

	public String getUID(ClassedModel classedModel);

	public String getUID(
		String modelClassName, Serializable primaryKeyObject,
		long ctCollectionId);

}