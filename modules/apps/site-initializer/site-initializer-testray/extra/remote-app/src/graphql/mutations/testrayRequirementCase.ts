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

export const CreateRequirementCaseBatch = gql`
	mutation createRequirementCaseBatch($data: InputC_RequirementCase!) {
		createRequirementCaseBatch(RequirementCase: $data)
			@rest(
				bodyKey: "RequirementCase"
				bodySerializer: "requirementcase"
				method: "POST"
				path: "requirementscaseses/batch"
				type: "C_RequirementCase"
			) {
			id
		}
	}
`;

export const DeleteRequirementCase = gql`
	mutation deleteRequirementsCases($id: Long) {
		c {
			deleteRequirementsCases(requirementsCasesId: $id)
		}
	}
`;
