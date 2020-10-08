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

import {fireEvent, render} from '@testing-library/react';
import React from 'react';

import ToasterProvider from '../../../../src/main/resources/META-INF/resources/js/shared/components/toaster/ToasterProvider.es';
import {useToaster} from '../../../../src/main/resources/META-INF/resources/js/shared/components/toaster/hooks/useToaster.es';

import '@testing-library/jest-dom/extend-expect';

const ComponentMock = () => {
	const toaster = useToaster();

	const add = () => {
		toaster.danger();
		toaster.info('Alert Info', 'Info');
		toaster.success();
		toaster.warning('Alert Warning', 'Warning');
	};

	const clear = () => {
		toaster.clearAll();
	};

	return (
		<>
			<button onClick={add}>add</button>
			<button onClick={clear}>clear</button>
		</>
	);
};

describe('The Toaster component should', () => {
	test('Render all alerts and clear all', () => {
		const {container, getByText} = render(<ComponentMock />, {
			wrapper: ToasterProvider,
		});

		const alertContainer = container.querySelector('.alert-container');
		const addBtn = getByText('add');
		const clearBtn = getByText('clear');

		fireEvent.click(addBtn);

		let alerts = container.querySelectorAll('.alert-dismissible');

		expect(alerts.length).toBe(4);

		fireEvent.click(alerts[0].children[1]);

		alerts = container.querySelectorAll('.alert-dismissible');

		expect(alerts.length).toBe(3);

		fireEvent.click(clearBtn);

		expect(alertContainer.children[0].children.length).toBe(0);
	});
});
