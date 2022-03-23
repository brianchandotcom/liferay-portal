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

	mutation createFactorOption($FactorOption: InputC_FactorOption!) {
		createFactorOption(FactorOption: $FactorOption)
			@rest(
				type: "C_FactorOption"
				path: "factoroptions"
				method: "POST"
				bodyKey: "FactorOption"
				bodySerializer: "factorOption"
			) {
			id: factorOptionId
			name
		}
	}
`;

export const DeleteFactorOption = gql`
	mutation deleteFactorOption($factorOptionId: Long) {
		c {
			deleteFactorOption(factorOptionId: $factorOptionId)
		}
	}
`;

export const UpdateFactorOption = gql`
	${testrayFactorOptionFragment}

	mutation updateFactorOption(
		$factorOptionId: Long
		$FactorOption: InputC_FactorOption!
	) {
		updateFactorOption(
			factorOptionId: $factorOptionId
			FactorOption: $FactorOption
		)
			@rest(
				type: "C_FactorOption"
				path: "/factoroptions/{args.factorOptionId}"
				method: "PUT"
				bodyKey: "FactorOption"
				bodySerializer: "factorOption"
			) {
			id: factorOptionId
			name
		}
	}
`;
