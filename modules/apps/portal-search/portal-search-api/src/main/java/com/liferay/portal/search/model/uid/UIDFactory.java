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