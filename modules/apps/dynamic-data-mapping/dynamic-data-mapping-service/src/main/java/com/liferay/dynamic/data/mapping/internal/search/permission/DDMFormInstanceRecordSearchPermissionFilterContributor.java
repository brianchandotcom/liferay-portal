/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.search.permission;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.portal.search.permission.SearchPermissionFilterContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Lino Alves
 */
@Component(service = SearchPermissionFilterContributor.class)
public class DDMFormInstanceRecordSearchPermissionFilterContributor
	implements SearchPermissionFilterContributor {

	@Override
	public String getParentEntryClassName(String entryClassName) {
		if (entryClassName.equals(DDMFormInstanceRecord.class.getName())) {
			return DDMFormInstance.class.getName();
		}

		return null;
	}

}