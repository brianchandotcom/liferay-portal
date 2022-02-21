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

package com.liferay.object.constants;

import com.liferay.petra.string.StringBundler;

/**
 * @author Feliphe Marinho
 */
public class ObjectSAPConstants {

	public static final String ALLOWED_SERVICE_SIGNATURES =
		StringBundler.concat(
			ObjectSAPConstants.CLASS_NAME_OBJECT_ENTRY_RESOURCE,
			"#getByExternalReferenceCode\n",
			ObjectSAPConstants.CLASS_NAME_OBJECT_ENTRY_RESOURCE,
			"#getObjectEntriesPage\n",
			ObjectSAPConstants.CLASS_NAME_OBJECT_ENTRY_RESOURCE,
			"#getObjectEntry\n",
			ObjectSAPConstants.CLASS_NAME_OBJECT_ENTRY_RESOURCE,
			"#postObjectEntry\n",
			ObjectSAPConstants.CLASS_NAME_OBJECT_ENTRY_RESOURCE,
			"#postScopeScopeKey");

	public static final String CLASS_NAME_OBJECT_ENTRY_RESOURCE =
		"com.liferay.object.rest.internal.resource.v1_0." +
			"ObjectEntryResourceImpl";

	public static final String SAP_ENTRY_NAME = "OBJECT_DEFAULT";

}