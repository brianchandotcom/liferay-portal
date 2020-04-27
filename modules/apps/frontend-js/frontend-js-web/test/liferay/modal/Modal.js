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

import {fireEvent, render} from '@testing-library/react';
import React from 'react';
import {act} from 'react-dom/test-utils';

import {Modal} from '../../../src/main/resources/META-INF/resources/liferay/modal/Modal';

describe('Modal', () => {
	beforeEach(() => {
		jest.useFakeTimers();
	});

	it('renders markup based on given configuration', () => {
		const {baseElement} = render(
			<Modal
				id="abcd"
				size="lg"
				title="My Modal"
				url="https://www.sample.url?p_p_id=com_liferay_MyPortlet"
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(baseElement).toMatchSnapshot();
	});

	it('renders in full screen, if url is set', () => {
		const {baseElement} = render(<Modal url="https://www.sample.url" />);

		expect(baseElement.querySelector('.modal-full-screen')).toBeTruthy();
	});

	it('renders in given size, even if url is set', () => {
		const {baseElement} = render(
			<Modal size="lg" url="https://www.sample.url" />
		);

		expect(baseElement.querySelector('.modal-lg')).toBeTruthy();
	});

	it('closes modal on cancel type button click', () => {
		const onCloseCallback = jest.fn();

		render(
			<Modal
				buttons={[{id: 'myButton', type: 'cancel'}]}
				onClose={onCloseCallback}
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		fireEvent.click(document.getElementById('myButton'));

		expect(onCloseCallback).toBeCalled();
	});

	// We are skipping this test because Jest does not support
	// document.createRange, but will support it in a future version. See more:
	//
	//      https://github.com/liferay/liferay-npm-tools/issues/440

	it.skip('renders given body HTML', () => {
		const sampleId = 'sampleId';

		render(<Modal bodyHTML={`<div id='${sampleId}' />`} />);

		act(() => {
			jest.runAllTimers();
		});

		expect(document.getElementById(sampleId)).toBeTruthy();
	});

	it('renders given header HTML', () => {
		const sampleId = 'sampleId';

		render(<Modal headerHTML={`<div id='${sampleId}' />`} />);

		act(() => {
			jest.runAllTimers();
		});

		expect(document.getElementById(sampleId)).toBeTruthy();
	});
});
