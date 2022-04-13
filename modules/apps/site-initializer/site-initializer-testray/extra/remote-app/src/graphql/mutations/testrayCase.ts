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

export const CreateCase = gql`
	mutation createCase($data: InputC_Case!) {
		createCase(Case: $data)
			@rest(
				bodyKey: "Case"
				bodySerializer: "case"
				method: "POST"
				path: "cases"
				type: "C_Case"
			) {
			id
		}
	}
`;

export const DeleteCase = gql`
	mutation deleteCase($id: Long) {
		c {
			deleteCase(caseId: $id)
		}
	}
`;

export const UpdateCase = gql`
	mutation updateCase($data: InputC_Case!, $id: Long) {
		updateCase(Case: $data, caseId: $id)
			@rest(
				bodyKey: "Case"
				bodySerializer: "case"
				method: "PUT"
				path: "cases/{args.caseId}"
				type: "C_Case"
			) {
			id
		}
	}
`;
