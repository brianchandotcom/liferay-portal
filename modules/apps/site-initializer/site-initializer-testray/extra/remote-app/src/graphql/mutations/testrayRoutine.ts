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

export const CreateRoutine = gql`
	mutation createRoutine($data: InputC_Routine!) {
		createRoutine(Routine: $data)
			@rest(
				bodyKey: "Routine"
				bodySerializer: "routine"
				method: "POST"
				path: "routines"
				type: "C_Routine"
			) {
			id
		}
	}
`;

export const DeleteRoutine = gql`
	mutation deleteRoutine($routineId: Long) {
		c {
			deleteRoutine(routineId: $routineId)
		}
	}
`;

export const UpdateRoutine = gql`
	mutation updateRoutine($data: InputC_Routine!, $id: Long) {
		updateRoutine(Routine: $data, routineId: $id)
			@rest(
				bodyKey: "Routine"
				bodySerializer: "routine"
				method: "PUT"
				path: "routines/{args.routineId}"
				type: "C_Routine"
			) {
			id
		}
	}
`;
