/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render, waitFor} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import ImportOptionsModal, {
	IMPORT_OPTIONS,
	ModalContent,
} from '../../../src/main/resources/META-INF/resources/js/components/import/ImportOptionsModal';
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
	it('renders text informing the user that some items already exist', async () => {
		const {findByText} = await renderComponent();

		expect(await findByText('manage-existing-items')).toBeInTheDocument();
		expect(
			await findByText(
				/one-or-more-items-already-exist.*what-action-do-you-want-to-take/i
			)
		).toBeInTheDocument();
	});

	it('renders a radio button with 3 options', async () => {
		const {findAllByRole, findByRole} = await renderComponent();

		expect((await findAllByRole('radio')).length).toBe(3);
		expect(
			await findByRole('radio', {name: /do-not-add-existing-items/i})
		).toBeInTheDocument();
		expect(
			await findByRole('radio', {name: /overwrite-existing-items/i})
		).toBeInTheDocument();
		expect(
			await findByRole('radio', {name: /keep-both/i})
		).toBeInTheDocument();
	});

	it('renders cancel and save buttons', async () => {
		const onImport = jest.fn();
		const onCloseModal = jest.fn();

		const {findByRole} = await renderComponent({onCloseModal, onImport});

		const cancelButton = await findByRole('button', {name: /cancel/i});
		const saveButton = await findByRole('button', {name: /save/i});

		expect(cancelButton).toBeInTheDocument();
		expect(saveButton).toBeInTheDocument();

		userEvent.click(cancelButton);
		userEvent.click(saveButton);

		await waitFor(() => {
			expect(onCloseModal).toHaveBeenCalled();
			expect(onImport).toHaveBeenCalled();
		});
	});
});

describe('ImportOptionsModal Accessibility', () => {
	it('checks accessibility of modal content', async () => {
		const {container} = render(
			<ModalContent
				onClose={jest.fn()}
				onImport={jest.fn()}
				onOptionChange={jest.fn()}
				selectedOption={IMPORT_OPTIONS[0].value}
			/>
		);

		await checkAccessibility({context: container});
	});
});
