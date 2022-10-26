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

import yupSchema from '../../schema/yup';
import Rest from './Rest';
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
