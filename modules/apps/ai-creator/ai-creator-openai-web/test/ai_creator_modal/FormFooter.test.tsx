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

import {render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import '@testing-library/jest-dom/extend-expect';

import {FormFooter} from '../../src/main/resources/META-INF/resources/ai_creator_modal/FormFooter';

describe('FormFooter', () => {
	it('triggers onAdd callback', () => {
		const onAdd = jest.fn();

		render(
			<FormFooter
				onAdd={onAdd}
				onClose={() => {}}
				showAddButton
				showCreateButton
				showRetryButton
			/>
		);

		userEvent.click(screen.getByRole('button', {name: 'add'}));

		expect(onAdd).toHaveBeenCalledTimes(1);
	});

	it('triggers onClose callback', () => {
		const onClose = jest.fn();

		render(
			<FormFooter
				onAdd={() => {}}
				onClose={onClose}
				showAddButton
				showCreateButton
				showRetryButton
			/>
		);

		userEvent.click(screen.getByRole('button', {name: 'cancel'}));

		expect(onClose).toHaveBeenCalledTimes(1);
	});

	it('has a submit button', () => {
		render(
			<FormFooter
				onAdd={() => {}}
				onClose={() => {}}
				showAddButton
				showCreateButton
				showRetryButton
			/>
		);

		const createButton = screen.getByRole('button', {name: 'create'});
		const retryButton = screen.getByRole('button', {name: 'try-again'});

		expect(createButton.getAttribute('type')).toBe('submit');
		expect(retryButton.getAttribute('type')).toBe('submit');
	});
});
