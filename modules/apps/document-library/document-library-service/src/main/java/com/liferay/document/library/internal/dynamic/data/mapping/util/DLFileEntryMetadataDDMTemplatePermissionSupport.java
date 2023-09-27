/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.util.DDMTemplatePermissionSupport;
import com.liferay.portlet.documentlibrary.constants.DLConstants;

import org.osgi.service.component.annotations.Component;

/**
 * @author Lino Alves
 */
@Component(
	property = {
		"add.template.action.id=ADD_DOCUMENT_TYPE",
		"model.class.name=com.liferay.document.library.kernel.model.DLFileEntryMetadata"
	},
	service = DDMTemplatePermissionSupport.class
)
public class DLFileEntryMetadataDDMTemplatePermissionSupport
	implements DDMTemplatePermissionSupport {

	@Override
	public String getResourceName(long classNameId) {
		return DLConstants.RESOURCE_NAME;
	}

}