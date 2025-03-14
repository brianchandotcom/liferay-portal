/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {fireEvent, render} from '@testing-library/react';
import React from 'react';

import {ModalContent} from '../../../src/main/resources/META-INF/resources/js/components/import/ImportOptionsModal';
import {ImportOptionsModal} from '../../../src/main/resources/META-INF/resources/js/index';
import checkAccessibility from '../../__lib__/checkAccessibility';

const renderComponent = async ({
	onCloseModal = () => null,
	onImport = () => null,
} = {}) => {
	return render(
		<ImportOptionsModal onCloseModal={onCloseModal} onImport={onImport} />
	);
};

describe('ImportOptionsModal', () => {
	afterAll(() => {
		jest.useRealTimers();
	});

	beforeAll(() => {
		jest.useFakeTimers();
	});

	it('renders text informing the user that some items already exist', async () => {
		const {findByText} = await renderComponent();

		expect(
			await findByText(
				'one-or-more-items-from-the-zip-already-exist-in-this-location'
			)
		).toBeInTheDocument();
	});

	it('renders a radio button with 3 options', async () => {
		const {findAllByRole, findByRole} = await renderComponent();

		expect((await findAllByRole('radio')).length).toBe(3);
		expect(
			await findByRole('radio', {name: /do-not-import-existing-items/i})
		).toBeInTheDocument();
		expect(
			await findByRole('radio', {name: /overwrite-existing-items/i})
		).toBeInTheDocument();
		expect(
			await findByRole('radio', {name: /keep-both/i})
		).toBeInTheDocument();
	});

	it('renders cancel and import buttons', async () => {
		const onImport = jest.fn();
		const onCloseModal = jest.fn();

		const {findByRole} = await renderComponent({onCloseModal, onImport});

		const cancelButton = await findByRole('button', {name: /cancel/i});
		const importButton = await findByRole('button', {name: /import/i});

		expect(cancelButton).toBeInTheDocument();
		expect(importButton).toBeInTheDocument();

		fireEvent.click(cancelButton);
		fireEvent.click(importButton);

		jest.advanceTimersByTime(1000);

		expect(onCloseModal).toHaveBeenCalled();
		expect(onImport).toHaveBeenCalled();
	});
});

describe('ImportOptionsModal Accessibility', () => {
	it('checks accesibility of modal content', async () => {
		const {container} = render(
			<ModalContent
				onClickImport={jest.fn()}
				onClose={jest.fn()}
				onRadioChange={jest.fn()}
			/>
		);

		await checkAccessibility({context: container});
	});
});
