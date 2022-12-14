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

import fetcher from '../fetcher';
import {Liferay} from '../liferay';
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

	public create(data: Partial<DispatchTrigger>) {
		return fetcher.post<DispatchTrigger>('/dispatch-triggers', {
			...data,
			userId: Liferay.ThemeDisplay.getUserId(),
		});
	}

	public run(dispatchTriggerId: number) {
		return fetcher.post<DispatchTrigger>(
			`/dispatch-triggers/${dispatchTriggerId}/run`
		);
	}
}

const liferayDispatchTriggerImpl = new LiferayDispatchTriggerImpl();

export {liferayDispatchTriggerImpl};
