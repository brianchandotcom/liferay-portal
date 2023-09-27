/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import HiddenInputs from '../../../../src/main/resources/META-INF/resources/js/components/shared/HiddenInputs.es';

function getHiddenInputsCount(container) {
	return container.querySelectorAll("input[type='hidden']").length;
}

describe('HiddenInputs', () => {
	it('renders', () => {
		const {container} = render(
			<HiddenInputs valueMap={{testKey: 'testValue'}} />
		);

		expect(container).not.toBeNull();
	});

	it('renders a hidden input', () => {
		const {container} = render(
			<HiddenInputs valueMap={{testKey: 'testValue'}} />
		);

		expect(getHiddenInputsCount(container)).toBe(1);
	});

	it('renders 3 hidden inputs', () => {
		const {container} = render(
			<HiddenInputs
				valueMap={{
					testKey1: 'testValue',
					testKey2: 'testValue',
					testKey3: 'testValue',
				}}
			/>
		);

		expect(getHiddenInputsCount(container)).toBe(3);
	});
});
