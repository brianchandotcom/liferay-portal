/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Rest from '../../core/Rest';
import yupSchema from '../../schema/yup';
import {TestrayRequirementCase} from './types';

type CaseRequirements = typeof yupSchema.requirement.__outputType & {
	projectId: number;
};

class TestrayCaseRequirementsImpl extends Rest<
	CaseRequirements,
	TestrayRequirementCase
> {
	constructor() {
		super({
			nestedFields: 'case.component,requirement.component.team',
			transformData: (caseRequirements) => ({
				...caseRequirements,
				case: caseRequirements?.r_caseToRequirementsCases_c_case
					? {
							...caseRequirements?.r_caseToRequirementsCases_c_case,
							component: caseRequirements
								?.r_caseToRequirementsCases_c_case
								?.r_componentToCases_c_component
								? {
										...caseRequirements
											?.r_caseToRequirementsCases_c_case
											?.r_componentToCases_c_component,
								  }
								: undefined,
					  }
					: undefined,
				requirement: caseRequirements?.r_requiremenToRequirementsCases_c_requirement
					? {
							...caseRequirements?.r_requiremenToRequirementsCases_c_requirement,
							component: caseRequirements
								?.r_requiremenToRequirementsCases_c_requirement
								?.r_componentToRequirements_c_component
								? {
										...caseRequirements
											?.r_requiremenToRequirementsCases_c_requirement
											?.r_componentToRequirements_c_component,
										team: caseRequirements
											?.r_requiremenToRequirementsCases_c_requirement
											?.r_componentToRequirements_c_component
											?.r_teamToComponents_c_team
											? {
													...caseRequirements
														?.r_requiremenToRequirementsCases_c_requirement
														?.r_componentToRequirements_c_component
														?.r_teamToComponents_c_team,
											  }
											: undefined,
								  }
								: undefined,
							linkURL:
								caseRequirements
									?.r_requiremenToRequirementsCases_c_requirement
									.linkURL,
					  }
					: undefined,
			}),
			uri: 'requirementscaseses',
		});
	}
}

export const testrayCaseRequirementsImpl = new TestrayCaseRequirementsImpl();
