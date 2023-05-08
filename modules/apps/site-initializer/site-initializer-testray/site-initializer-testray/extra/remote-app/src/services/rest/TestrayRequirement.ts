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

import Rest from '~/core/Rest';

import yupSchema from '../../schema/yup';
import {TestrayRequirement} from './types';

type Requirement = typeof yupSchema.requirement.__outputType & {
	projectId: number;
};

class TestrayRequirementsImpl extends Rest<Requirement, TestrayRequirement> {
	constructor() {
		super({
			adapter: ({
				componentId: r_componentToRequirements_c_componentId,
				description,
				descriptionType,
				key,
				linkTitle,
				linkURL,
				projectId: r_projectToRequirements_c_projectId,
				summary,
			}) => ({
				description,
				descriptionType,
				key,
				linkTitle,
				linkURL,
				r_componentToRequirements_c_componentId,
				r_projectToRequirements_c_projectId,
				summary,
			}),
			nestedFields:
				'component, team, componentToRequirements.teamToComponents',
			transformData: (testrayRequirement) => ({
				...testrayRequirement,
				component: testrayRequirement.r_componentToRequirements_c_component
					? {
							...testrayRequirement.r_componentToRequirements_c_component,
							team:
								testrayRequirement
									.r_componentToRequirements_c_component
									.r_teamToComponents_c_team,
					  }
					: undefined,
			}),
			uri: 'requirements',
		});
	}
}

const testrayRequirementsImpl = new TestrayRequirementsImpl();

export {testrayRequirementsImpl};
