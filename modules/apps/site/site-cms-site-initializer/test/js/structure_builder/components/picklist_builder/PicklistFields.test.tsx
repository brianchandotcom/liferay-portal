/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {fireEvent, render, screen, waitFor} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import PicklistFields from '../../../../../src/main/resources/META-INF/resources/js/structure_builder/components/picklist_builder/PicklistFields';
import {State} from '../../../../../src/main/resources/META-INF/resources/js/structure_builder/contexts/PicklistBuilderContext';
import {
	DEFAULT_STATE,
	MockStateProvider,
} from '../../mocks/MockPicklistStateProvider';

const renderComponent = (state?: Partial<State>) => {
	return render(
		<MockStateProvider state={state}>
			<PicklistFields />
		</MockStateProvider>
	);
};

describe('PicklistFields', () => {
	beforeEach(() => {
		(global as any).Liferay.Language.direction = {
			en_US: 'rtl',
		};
	});

	it('updates the name when name input changes', async () => {
		renderComponent();

		const {setName} = DEFAULT_STATE;
		const nameInput = screen.getByLabelText('picklist-name');

		await userEvent.clear(nameInput);
		await userEvent.type(nameInput, 'new name');

		fireEvent.blur(nameInput);

		await waitFor(() => {
			expect(setName).toBeCalled();
		});
	});

	it('updates the ERC when ERC input changes', async () => {
		renderComponent();

		const {setErc} = DEFAULT_STATE;
		const nameInput = screen.getByLabelText('erc');

		await userEvent.clear(nameInput);
		await userEvent.type(nameInput, 'new erc');

		fireEvent.blur(nameInput);

		expect(setErc).toBeCalledWith('new erc');
	});
});
