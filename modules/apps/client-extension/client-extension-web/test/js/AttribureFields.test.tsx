/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fireEvent, render} from '@testing-library/react';
import React from 'react';

import AttributeFields from '../../src/main/resources/META-INF/resources/js/components/global-js/AttributeFields';

const onAttributeChange = jest.fn();

const mockAttributeFields = () => (
	<AttributeFields index={0} onAttributeChange={onAttributeChange} value="" />
);

describe('AttributeFields', () => {
	it('Prevents user from adding normal whitespace on attribute field', () => {
		const {getByTestId} = render(mockAttributeFields());

		const attributeField = getByTestId('testId_0');

		fireEvent.change(attributeField, {target: {value: 'name with space'}});

		expect(onAttributeChange).toHaveBeenCalledTimes(1);
		expect(onAttributeChange).toHaveBeenCalledWith(0, {
			name: 'namewithspace',
		});
	});

	it('Prevents user from adding non-breaking-space on attribute field', () => {
		const {getByTestId} = render(mockAttributeFields());

		const attributeField = getByTestId('testId_0');

		fireEvent.change(attributeField, {
			target: {value: 'name\u00A0with\u00A0non\u00A0breaking\u00A0space'},
		});

		expect(onAttributeChange).toHaveBeenCalledTimes(2);
		expect(onAttributeChange).toHaveBeenCalledWith(0, {
			name: 'namewithnonbreakingspace',
		});
	});
});
