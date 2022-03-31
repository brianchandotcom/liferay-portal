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

export const CreateRequirement = gql`
	mutation createRequirement($data: InputC_FactorOption!) {
		createRequirement(Requirement: $data)
			@rest(
				bodyKey: "Requirement"
				bodySerializer: "requirement"
				method: "POST"
				path: "requirements"
				type: "C_Requirement"
			) {
			id: requirementId
			name
		}
	}
`;

export const DeleteRequirement = gql`
	mutation deleteRequirement($id: Long) {
		c {
			deleteRequirement(requirementId: $id)
		}
	}
`;

export const UpdateRequirement = gql`
	mutation updateRequirement($data: InputC_FactorOption!, $id: Long) {
		updateRequirement(Requirement: $data, requirementId: $id)
			@rest(
				bodyKey: "Requirement"
				bodySerializer: "requirement"
				method: "PUT"
				path: "requirements/{args.requirementId}"
				type: "C_Requirement"
			) {
			id: requirementId
			name
		}
	}
`;
