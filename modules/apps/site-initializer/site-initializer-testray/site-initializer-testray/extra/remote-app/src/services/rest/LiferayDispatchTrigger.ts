/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DispatchTriggerStatuses} from '../../util/statuses';
import fetcher from '../fetcher';
import {Liferay} from '../liferay';
import {testrayDispatchTriggerImpl} from './TestrayDispatchTrigger';
import {APIResponse} from './types';

type DispatchTrigger = {
	active: boolean;
	companyId: number;
	dispatchTaskExecutorType: string;
	dispatchTaskSettings: Object;
	externalReferenceCode: string;
	id: number;
	name: string;
	overlapAllowed: boolean;
	userId: number;
};

class LiferayDispatchTriggerImpl {
	public getAll() {
		return fetcher<APIResponse<DispatchTrigger>>('/dispatch-triggers');
	}

	public async create(data: Partial<DispatchTrigger>) {
		const liferayDispatchTrigger = await fetcher.post<DispatchTrigger>(
			'/dispatch-triggers',
			{
				...data,
				userId: Liferay.ThemeDisplay.getUserId(),
			}
		);

		const testrayDispatchTrigger = await testrayDispatchTriggerImpl.create({
			dispatchTriggerId: liferayDispatchTrigger.id,
			dueStatus: DispatchTriggerStatuses.SCHEDULED,
			name: data.name,
			type: data.dispatchTaskExecutorType,
		});

		return {
			liferayDispatchTrigger,
			testrayDispatchTrigger,
		};
	}

	public run(dispatchTriggerId: number) {
		return fetcher.post<DispatchTrigger>(
			`/dispatch-triggers/${dispatchTriggerId}/run`
		);
	}
}

const liferayDispatchTriggerImpl = new LiferayDispatchTriggerImpl();

export {liferayDispatchTriggerImpl};
