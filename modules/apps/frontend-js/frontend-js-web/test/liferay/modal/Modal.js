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

import '@testing-library/jest-dom/extend-expect';
import {fireEvent, render} from '@testing-library/react';
import React from 'react';
import {act} from 'react-dom/test-utils';

import {
	Modal,
	openModal,
} from '../../../src/main/resources/META-INF/resources/liferay/modal/Modal';
import openAlertModal from '../../../src/main/resources/META-INF/resources/liferay/modal/commands/OpenAlertModal';
import openConfirmModal from '../../../src/main/resources/META-INF/resources/liferay/modal/commands/OpenConfirmModal';

jest.mock(
	'../../../src/main/resources/META-INF/resources/liferay/modal/Modal',
	() => ({
		...jest.requireActual(
			'../../../src/main/resources/META-INF/resources/liferay/modal/Modal'
		),
		openModal: jest.fn((args) => args),
	})
);

describe('Modal', () => {
	beforeAll(() => {
		Liferay.on = jest.fn(() => {
			return {
				detach: jest.fn(),
			};
		});
	});

	beforeEach(() => {
		jest.useFakeTimers();
	});

	it('renders markup based on given configuration', () => {
		const {baseElement} = render(
			<Modal
				id="abcd"
				iframeProps={{id: 'efgh'}}
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

	it('renders given body component', () => {
		const sampleId = 'sampleId';

		const SampleBodyComponent = () => {
			return <div id={sampleId} />;
		};

		render(<Modal bodyComponent={SampleBodyComponent} />);

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

	it('when providing "autoFocus: true" inside a button configuration, it will make this button focused', () => {
		render(
			<Modal
				buttons={[
					{autoFocus: true, id: 'modal-button-ok', label: 'ok'},
				]}
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(document.getElementById('modal-button-ok')).toHaveFocus();
	});

	describe('openAlertModal', () => {
		afterEach(() => {
			jest.resetAllMocks();
		});

		it('when the custom dialogs are enabled, calling openAlertModal, calls openModal with the proper arguments', () => {
			Liferay.CustomDialogs = {
				enabled: true,
			};

			openAlertModal({message: 'lala'});

			expect(openModal).toHaveBeenCalled();
			expect(openModal).toHaveBeenCalledWith({
				bodyHTML: 'lala',
				buttons: [
					{
						autoFocus: true,
						label: 'ok',
						onClick: expect.anything(),
					},
				],
				center: true,
				disableHeader: true,
			});
		});

		it('when the custom dialogs are disabled, calling openAlertModal, calls native alert', () => {
			Liferay.CustomDialogs = {
				enabled: false,
			};

			window.alert = jest.fn();

			openAlertModal({message: 'lala'});

			expect(window.alert).toHaveBeenCalled();
			expect(window.alert).toHaveBeenCalledWith('lala');
		});
	});

	describe('openConfirmModal', () => {
		afterEach(() => {
			jest.resetAllMocks();
		});

		it('when the custom dialogs are enabled, calling openConfirmModal, calls openModal with the proper arguments', () => {
			Liferay.CustomDialogs = {
				enabled: true,
			};

			openConfirmModal({
				message: 'lala',
				onConfirm: () => {},
				title: 'fiona',
			});

			expect(openModal).toHaveBeenCalled();

			expect(openModal).toHaveBeenCalledWith({
				bodyHTML: 'lala',
				buttons: [
					{
						displayType: 'secondary',
						type: 'cancel',
					},
					{
						autoFocus: true,
						onClick: expect.anything(),
					},
				],
				center: true,
				disableHeader: true,
				onClose: expect.anything(),
				title: 'fiona',
			});
		});

		it('when the custom dialogs are disabled, calling openConfirmModal, calls native confirm', () => {
			Liferay.CustomDialogs = {
				enabled: false,
			};

			window.confirm = jest.fn();

			openConfirmModal({
				message: 'lala2',
				onConfirm: () => {},
				title: 'fiona',
			});

			expect(window.confirm).toHaveBeenCalled();
			expect(window.confirm).toHaveBeenCalledWith('lala2');
		});
	});
});
