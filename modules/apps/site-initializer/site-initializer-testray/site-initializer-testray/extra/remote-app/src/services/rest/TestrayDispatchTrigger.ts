/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Rest from '../../core/Rest';
import {PickList, TestrayDispatchTrigger} from './types';

class TestrayDispatchTriggerImpl extends Rest<
	Partial<Omit<TestrayDispatchTrigger, 'dueStatus'> & {dueStatus: string}>,
	TestrayDispatchTrigger & {dueStatus: PickList}
> {
	constructor() {
		super({uri: 'testraydispatchtriggers'});
	}
}

const testrayDispatchTriggerImpl = new TestrayDispatchTriggerImpl();

export {testrayDispatchTriggerImpl};
