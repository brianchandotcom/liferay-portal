/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.internal.resource.v1_0;

import com.liferay.headless.cms.dto.v1_0.DocumentBulkAction;
import com.liferay.headless.cms.dto.v1_0.Task;
import com.liferay.headless.cms.resource.v1_0.BulkActionResource;
import com.liferay.portal.kernel.search.filter.Filter;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Crescenzo Rega
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/bulk-action.properties",
	scope = ServiceScope.PROTOTYPE, service = BulkActionResource.class
)
public class BulkActionResourceImpl extends BaseBulkActionResourceImpl {

	@Override
	public Task postBulkAction(
			String search, Filter filter, DocumentBulkAction documentBulkAction)
		throws Exception {

		return super.postBulkAction(search, filter, documentBulkAction);
	}

}