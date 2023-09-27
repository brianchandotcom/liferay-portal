/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.list.retriever;

/**
 * @author Eudaldo Alonso
 */
public interface ListObjectReferenceFactoryRegistry {

	public ListObjectReferenceFactory<?> getListObjectReference(String type);

}