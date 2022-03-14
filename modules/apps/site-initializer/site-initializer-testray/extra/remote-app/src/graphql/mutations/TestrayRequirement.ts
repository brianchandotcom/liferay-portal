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

import {gql} from '@apollo/client';

import {testrayRequirementFragment} from '../fragments';

export const CreateTestrayRequirement = gql`
	${testrayRequirementFragment}

	mutation CreateTestrayRequirement(
		$TestrayRequirement: InputC_TestrayRequirement!
	) {
		c {
			createTestrayRequirement(TestrayRequirement: $TestrayRequirement) {
				...TestrayRequirementFragment
			}
		}
	}
`;

export const DeleteTestrayRequirement = gql`
	mutation deleteTestrayRequirement($testrayRequirementId: Long) {
		c {
			deleteTestrayRequirement(
				testrayRequirementId: $testrayRequirementId
			)
		}
	}
`;

export const UpdateTestrayRequirements = gql`
	mutation updateTestrayRequirement(
		$testrayRequirementId: Long
		$TestrayRequirement: InputC_TestrayRequirement!
	) {
		c {
			updateTestrayRequirement(
				testrayRequirementId: $testrayRequirementId
				TestrayRequirement: $TestrayRequirement
			) {
				...TestrayRequirementFragment
			}
		}
	}
`;
