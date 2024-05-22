/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fireEvent, render} from '@testing-library/react';
import React from 'react';

import AttributeFields from '../../src/main/resources/META-INF/resources/js/components/global-js/AttributeFields.tsx';

describe('AttributeFields', () => {
	it('Prevents user from adding space on attributes', () => {
		const onAttributeChange = jest.fn();

		const {getByTestId} = render(
			<AttributeFields
				index={0}
				onAttributeChange={onAttributeChange}
				value=""
			/>
		);

		const attributeField = getByTestId('name');

		fireEvent.change(attributeField, {target: {value: 'name with space'}});

		expect(onAttributeChange).toHaveBeenCalledTimes(1);
		expect(onAttributeChange).toHaveBeenCalledWith(0, {
			name: 'namewithspace',
		});
	});
});
