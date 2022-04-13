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

import {testrayFactorOptionFragment} from '../fragments';

export const CreateFactorOption = gql`
	${testrayFactorOptionFragment}

	mutation createFactorOption($data: InputC_FactorOption!) {
		createFactorOption(FactorOption: $data)
			@rest(
				bodyKey: "FactorOption"
				bodySerializer: "factorOption"
				method: "POST"
				path: "factoroptions"
				type: "C_FactorOption"
			) {
			id: factorOptionId
			name
		}
	}
`;

export const DeleteFactorOption = gql`
	mutation deleteFactorOption($id: Long) {
		c {
			deleteFactorOption(factorOptionId: $id)
		}
	}
`;

export const UpdateFactorOption = gql`
	${testrayFactorOptionFragment}

	mutation updateFactorOption($id: Long, $data: InputC_FactorOption!) {
		updateFactorOption(factorOptionId: $id, FactorOption: $data)
			@rest(
				bodyKey: "FactorOption"
				bodySerializer: "factorOption"
				method: "PUT"
				path: "/factoroptions/{args.factorOptionId}"
				type: "C_FactorOption"
			) {
			id: factorOptionId
			name
		}
	}
`;
