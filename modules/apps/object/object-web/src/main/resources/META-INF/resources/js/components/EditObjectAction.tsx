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

export default function EditObjectAction({
	objectAction: {id, ...values},
	objectActionExecutors,
	objectActionTriggers,
	readOnly,
}: IProps) {
	return (
		<ObjectActionFormBase
			objectAction={values}
			objectActionExecutors={objectActionExecutors}
			objectActionTriggers={objectActionTriggers}
			readOnly={readOnly}
			requestParams={{
				method: 'PUT',
				url: `/o/object-admin/v1.0/object-actions/${id}`,
			}}
			successMessage={Liferay.Language.get(
				'the-object-action-was-updated-successfully'
			)}
			title={Liferay.Language.get('action')}
		/>
	);
}

interface IProps {
	objectAction: ObjectAction;
	objectActionExecutors: CustomItem[];
	objectActionTriggers: CustomItem[];
	readOnly?: boolean;
}
