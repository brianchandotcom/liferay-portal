/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.testray.rest.internal.resource.v1_0;

import com.liferay.osb.testray.rest.dto.v1_0.CompareRuns;
import com.liferay.osb.testray.rest.resource.v1_0.CompareRunsResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author José Abelenda
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/compare-runs.properties",
	scope = ServiceScope.PROTOTYPE, service = CompareRunsResource.class
)
public class CompareRunsResourceImpl extends BaseCompareRunsResourceImpl {

	@Override
	public CompareRuns getCompareRuns(Long idTestrayRunA, Long idTestrayRunB)
		throws Exception {

		return new CompareRuns() {
			{
				dueStatuses = new String[] {
					"PASSED", "FAILED", "BLOCKED", "TEST FIX", "DNR"
				};

				values = new int[][] {
					{1, 2, 3, 4, 5}, {1, 2, 3, 4, 5}, {1, 2, 3, 4, 5},
					{1, 2, 3, 4, 5}, {1, 2, 3, 4, 5}
				};
			}
		};
	}

}