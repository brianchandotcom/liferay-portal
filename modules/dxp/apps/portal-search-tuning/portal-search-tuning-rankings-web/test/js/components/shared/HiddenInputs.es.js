/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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
