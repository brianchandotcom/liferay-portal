/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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