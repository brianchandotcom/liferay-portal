/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.testray.rest.internal.resource.v1_0;

import com.liferay.osb.testray.rest.dto.v1_0.CompareRuns;
import com.liferay.osb.testray.rest.resource.v1_0.CompareRunsResource;
import com.liferay.osb.testray.service.CompareRunsService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author José Abelenda
 * @author Felipe Veloso
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/compare-runs.properties",
	scope = ServiceScope.PROTOTYPE, service = CompareRunsResource.class
)
public class CompareRunsResourceImpl extends BaseCompareRunsResourceImpl {

	@Override
	public CompareRuns getCompareRuns(Long idTestrayRunA, Long idTestrayRunB)
		throws Exception {

		String[] dueStatuses = {
			"PASSED", "FAILED", "BLOCKED", "TEST FIX", "DNR"
		};

		int[][] matrix = new int[5][5];
		int i;
		int j;

		for (i = 0; i < dueStatuses.length; i = i + 1) {
			for (j = 0; j < dueStatuses.length; j = j + 1) {
				int entry = _compareRunsService.getComparison(
					idTestrayRunA, idTestrayRunB, dueStatuses[i],
					dueStatuses[j], contextCompany.getCompanyId());

				matrix[i][j] = entry;
			}
		}

		return new CompareRuns() {
			{
				dueStatuses = dueStatuses;
				values = matrix;
			}
		};
	}

	@Reference
	private CompareRunsService _compareRunsService;

}