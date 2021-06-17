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

import '@testing-library/jest-dom/extend-expect';
import {cleanup, render} from '@testing-library/react';
import React from 'react';

import Diagram from '../src/main/resources/META-INF/resources/js/Diagram';

describe('Diagram 1 of 5 features', () => {
	const initialProps = {
		imageURL: './assets/lfr-diagrm-engine.png',
	};

	beforeEach(() => {
		jest.resetAllMocks();
	});

	afterEach(() => {
		cleanup();
	});

	it('Diagram image loads', () => {
		const {container} = render(<Diagram {...initialProps} />);
		const image = container.querySelector('image');
		expect(image.href).toBe(initialProps.href);
	});

	it('If Zoom Controller is enabled, will render the component', () => {
		const {container} = render(
			<Diagram
				{...initialProps}
				zoomController={{
					enable: true,
					position: {
						bottom: '0px',
						left: '',
						right: '200px',
						top: '',
					},
				}}
			/>
		);

		const controller = container.querySelector('.zoom-controller');

		expect(controller).toBeTruthy();
	});

	it("If Zoom Controller is disabled, it won't be rendered", () => {
		const {container} = render(
			<Diagram {...initialProps} zoomController={{enable: false}} />
		);

		const controller = container.querySelector('.zoom-controller');

		expect(controller).toBeFalsy();
	});

	it('If Navigation Controller is enabled, will render the component', () => {
		const {container} = render(
			<Diagram
				{...initialProps}
				navigationController={{
					dragStep: 10,
					enable: true,
					enableDrag: false,
					position: {
						bottom: '15px',
						left: '',
						right: '50px',
						top: '',
					},
				}}
			/>
		);

		const controller = container.querySelector('.move-controller');

		expect(controller).toBeTruthy();
	});

	it("If Navigation Controller is disabled, it won't be rendered", () => {
		const {container} = render(
			<Diagram {...initialProps} navigationController={{enable: false}} />
		);

		const controller = container.querySelector('.move-controller');

		expect(controller).toBeFalsy();
	});
});
