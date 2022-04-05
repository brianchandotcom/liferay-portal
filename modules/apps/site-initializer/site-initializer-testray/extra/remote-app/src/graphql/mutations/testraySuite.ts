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

export const CreateSuite = gql`
	mutation createSuite($data: InputC_Suite!) {
		createSuite(Suite: $data)
			@rest(
				bodyKey: "Suite"
				bodySerializer: "suite"
				method: "POST"
				path: "suites"
				type: "C_Suite"
			) {
			id
		}
	}
`;
export const UpdateSuite = gql`
	mutation updateSuite($data: InputC_Suite!, $id: Long) {
		updateRoutine(Suite: $data, suiteId: $id)
			@rest(
				bodyKey: "Suite"
				bodySerializer: "suite"
				method: "PUT"
				path: "suites/{args.suiteId}"
				type: "C_Suite"
			) {
			id
		}
	}
`;

export const DeleteSuite = gql`
	mutation deleteSuite($suiteId: Long) {
		c {
			deleteSuite(suiteId: $suiteId)
		}
	}
`;
