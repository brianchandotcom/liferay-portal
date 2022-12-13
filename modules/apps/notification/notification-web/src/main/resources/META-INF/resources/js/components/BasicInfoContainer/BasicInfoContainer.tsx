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

import {
	Card,
	FormError,
	Input,
	SingleSelect,
} from '@liferay/object-js-components-web';
import React from 'react';

interface BasicInfoContainerProps {
	errors: FormError<NotificationTemplate>;
	setValues: (values: Partial<NotificationTemplate>) => void;
	values: NotificationTemplate;
}

export function BasicInfoContainer({
	errors,
	setValues,
	values,
}: BasicInfoContainerProps) {
	return (
		<Card title={Liferay.Language.get('basic-info')}>
			<Input
				error={errors.name}
				label={Liferay.Language.get('name')}
				name="name"
				onChange={({target}) =>
					setValues({
						...values,
						name: target.value,
					})
				}
				required
				value={values.name}
			/>

			<Input
				component="textarea"
				label={Liferay.Language.get('description')}
				name="description"
				onChange={({target}) =>
					setValues({
						...values,
						description: target.value,
					})
				}
				type="text"
				value={values.description}
			/>

			{!Liferay.FeatureFlags['LPS-162133'] && (
				<SingleSelect
					disabled
					label={Liferay.Language.get('type')}
					options={[]}
					value={Liferay.Language.get('email')}
				/>
			)}
		</Card>
	);
}
