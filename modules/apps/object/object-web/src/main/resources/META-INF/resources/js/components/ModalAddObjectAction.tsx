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

import React from 'react';

import {CustomItem} from './Form/CustomSelect/CustomSelect';
import ObjectActionFormBase from './ObjectActionFormBase';

export default function AddObjectAction({
	apiURL,
	objectActionExecutors = [],
	objectActionTriggers = [],
}: IProps) {
	return (
		<ObjectActionFormBase
			objectAction={{active: true}}
			objectActionExecutors={objectActionExecutors}
			objectActionTriggers={objectActionTriggers}
			requestParams={{
				method: 'POST',
				url: apiURL,
			}}
			successMessage={Liferay.Language.get(
				'the-object-action-was-created-successfully'
			)}
			title={Liferay.Language.get('new-action')}
		/>
	);
}

interface IProps {
	apiURL: string;
	objectActionExecutors: CustomItem[];
	objectActionTriggers: CustomItem[];
}
