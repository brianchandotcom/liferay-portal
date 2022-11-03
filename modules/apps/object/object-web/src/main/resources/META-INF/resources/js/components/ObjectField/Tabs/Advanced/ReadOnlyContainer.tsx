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

import {ClayRadio, ClayRadioGroup} from '@clayui/form';
import {Card} from '@liferay/object-js-components-web';
import React from 'react';

interface ReadOnlyContainerProps {
	values: Partial<ObjectField>;
	setValues: (value: Partial<ObjectField>) => void;
}

export function ReadOnlyContainer({setValues, values}: ReadOnlyContainerProps) {
	const readOnlySetting = values.objectFieldSettings?.find(
		(fieldSetting) => fieldSetting.name === 'readOnly'
	);

	const setReadOnly = (value: string) => {
		setValues({
			objectFieldSettings: [
				...(values.objectFieldSettings?.filter(
					(objectFieldSetting) =>
						objectFieldSetting.name !== 'readOnly'
				) as ObjectFieldSetting[]),
				{
					name: 'readOnly',
					value,
				},
			],
		});
	};

	return (
		<Card title={Liferay.Language.get('read-only')}>
			<ClayRadioGroup
				defaultValue={
					readOnlySetting
						? (readOnlySetting?.value as string)
						: values.system ||
						  values.businessType === 'Formula' ||
						  values.businessType === 'Aggregation'
						? 'true'
						: 'false'
				}
			>
				<ClayRadio
					label={Liferay.Language.get('true')}
					onClick={() => setReadOnly('true')}
					value="true"
				/>

				<ClayRadio
					label={Liferay.Language.get('false')}
					onClick={() => setReadOnly('false')}
					value="false"
				/>

				<ClayRadio
					label={Liferay.Language.get('conditional')}
					onClick={() => setReadOnly('conditional')}
					value="conditional"
				/>
			</ClayRadioGroup>
		</Card>
	);
}
