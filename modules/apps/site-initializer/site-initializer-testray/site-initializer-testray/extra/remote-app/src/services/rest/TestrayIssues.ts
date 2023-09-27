/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Rest from '../../core/Rest';
import yupSchema from '../../schema/yup';
import {TestrayIssue} from './types';

type Issue = typeof yupSchema.issue.__outputType;

class TestrayIssuesImpl extends Rest<Issue, TestrayIssue> {
	public DELIMITER = '_';

	constructor() {
		super({
			adapter: ({name}) => ({
				name,
			}),
			uri: 'issues',
		});
	}
}

export const testrayIssueImpl = new TestrayIssuesImpl();
