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

import {CustomItem} from '@liferay/object-js-components-web';
import React from 'react';

import ObjectAction from './index';

export default function EditObjectAction({
	ffNotificationTemplates,
	objectAction: {id, ...values},
	objectActionExecutors,
	objectActionTriggers,
	readOnly,
	validateExpressionBuilderContentURL,
}: IProps) {
	return (
		<ObjectAction
			ffNotificationTemplates={ffNotificationTemplates}
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
			validateExpressionBuilderContentURL={
				validateExpressionBuilderContentURL
			}
		/>
	);
}

interface IProps {
	ffNotificationTemplates: boolean;
	objectAction: ObjectAction;
	objectActionExecutors: CustomItem[];
	objectActionTriggers: CustomItem[];
	readOnly?: boolean;
	validateExpressionBuilderContentURL: string;
}
