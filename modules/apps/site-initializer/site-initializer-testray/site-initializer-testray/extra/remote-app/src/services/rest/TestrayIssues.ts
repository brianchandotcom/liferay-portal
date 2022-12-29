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

import yupSchema from '../../schema/yup';
import {searchUtil} from '../../util/search';
import Rest from './Rest';
import {TestrayIssue} from './types';

type Issue = typeof yupSchema.issue.__outputType;

class TestrayIssuesImpl extends Rest<Issue, TestrayIssue> {
	constructor() {
		super({
			adapter: ({id, name}) => ({
				id,
				name,
			}),
			uri: 'issues',
		});
	}

	public async createIfNotExist(name: string) {
		const response = await this.getAll({
			filter: searchUtil.eq('name', name),
		});

		if ((response?.totalCount ?? 0) > 0) {
			return response?.items[0];
		}

		return this.create({
			id: undefined,
			name,
		});
	}
}

export const testrayIssueImpl = new TestrayIssuesImpl();
