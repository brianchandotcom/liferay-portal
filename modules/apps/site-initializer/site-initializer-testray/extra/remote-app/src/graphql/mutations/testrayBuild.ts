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

export const CreateBuild = gql`
	mutation createBuild($data: InputC_Build!) {
		createBuild(Build: $data)
			@rest(
				bodyKey: "Build"
				bodySerializer: "build"
				method: "POST"
				path: "builds"
				type: "C_Build"
			) {
			id
			name
			description
			gitHash
			name
			productVersionId
		}
	}
`;

export const DeleteBuild = gql`
	mutation deleteBuild($id: Long) {
		c {
			deleteBuild(buildId: $id)
		}
	}
`;

export const UpdateBuild = gql`
	mutation updateBuild($data: InputC_Build!, $id: Long) {
		updateBuild(Build: $data, buildId: $id)
			@rest(
				bodyKey: "Build"
				bodySerializer: "build"
				method: "PUT"
				path: "builds/{args.buildId}"
				type: "C_Build"
			) {
			id
			name
			description
			gitHash
			name
			productVersionId
		}
	}
`;
