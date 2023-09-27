/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.model.uid;

import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;

import java.io.Serializable;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author André de Oliveira
 */
@ProviderType
public interface UIDFactory {

	public String getUID(ClassedModel classedModel);

	public String getUID(com.liferay.portal.kernel.search.Document document);

	public String getUID(Document document);

	public String getUID(
		String modelClassName, Serializable primaryKeyObject,
		long ctCollectionId);

	public void setUID(
		ClassedModel classedModel,
		com.liferay.portal.kernel.search.Document document);

	public void setUID(
		ClassedModel classedModel, DocumentBuilder documentBuilder);

}