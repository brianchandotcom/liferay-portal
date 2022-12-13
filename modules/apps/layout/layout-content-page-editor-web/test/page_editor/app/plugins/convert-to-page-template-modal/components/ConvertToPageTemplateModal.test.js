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

import {act, fireEvent, render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import {StoreContextProvider} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';
import LayoutService from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/services/LayoutService';
import {PageTemplateModal} from '../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/convert-to-page-template-modal/components/ConvertToPageTemplateModal';

import '@testing-library/jest-dom/extend-expect';

jest.mock(
	'../../../../../../src/main/resources/META-INF/resources/page_editor/app/services/LayoutService',
	() => ({
		createLayoutPageTemplateEntry: jest.fn(() => Promise.resolve()),
		getLayoutPageTemplateCollections: jest.fn(() =>
			Promise.resolve([{name: 'test'}])
		),
	})
);

jest.mock(
	'../../../../../../src/main/resources/META-INF/resources/page_editor/app/config',
	() => ({
		config: {
			portletNamespace: 'portletNamespace_',
		},
	})
);

function renderConvertToPageTemplateModal() {
	return render(
		<StoreContextProvider
			initialState={{
				availableSegmentsExperiences: {},
				segmentsExperienceId: '0',
			}}
		>
			<PageTemplateModal
				observer={{
					dispatch: () => {},
					mutation: [true, true],
				}}
				onClose={jest.fn()}
			/>
		</StoreContextProvider>
	);
}

describe('ConvertToPageTemplateModal', () => {
	beforeAll(() => {
		Liferay.FeatureFlags['LPS-166201'] = true;
	});

	afterEach(() => {
		LayoutService.createLayoutPageTemplateEntry.mockClear();
	});

	describe('Select Page Template Set modal', () => {
		it('renders the Select Page Template Set modal', async () => {
			await act(async () => {
				renderConvertToPageTemplateModal();
			});

			expect(
				screen.getByText('select-page-template-set')
			).toBeInTheDocument();
		});

		it('calls createLayoutPageTemplateEntry when a page template set is selected and the button save is pressed', async () => {
			await act(async () => {
				renderConvertToPageTemplateModal();
			});

			const saveButton = screen.getByText('save');
			const select = screen.getByLabelText('page-template-set');

			userEvent.selectOptions(select, 'test');
			fireEvent.change(select);

			await act(async () => {
				userEvent.click(saveButton);
			});

			expect(
				LayoutService.createLayoutPageTemplateEntry
			).toHaveBeenCalledTimes(1);
			expect(LayoutService.createLayoutPageTemplateEntry).toBeCalledWith(
				expect.objectContaining({
					segmentsExperienceId: '0',
					templateSetId: 'test',
					templateSetName: 'untitled-set',
				})
			);
		});

		it('does not call createLayoutPageTemplateEntry when a page template set is not selected and the Save button is pressed', async () => {
			await act(async () => {
				renderConvertToPageTemplateModal();
			});

			const saveButton = screen.getByText('save');

			userEvent.click(saveButton);

			expect(
				LayoutService.createLayoutPageTemplateEntry
			).toHaveBeenCalledTimes(0);
		});

		it('changes the modal when the Save In New Set Button is pressed', async () => {
			await act(async () => {
				renderConvertToPageTemplateModal();
			});

			const saveInNewSetButton = screen.getByText('save-in-new-set');

			userEvent.click(saveInNewSetButton);

			expect(
				screen.getByText('add-page-template-set')
			).toBeInTheDocument();
		});
	});

	describe('Add Page Template Set modal', () => {
		beforeEach(() => {
			LayoutService.getLayoutPageTemplateCollections.mockImplementation(
				() => Promise.resolve([])
			);
		});

		it('renders the Select Add Template Set modal when there are no sets', async () => {
			await act(async () => {
				renderConvertToPageTemplateModal();
			});

			expect(
				screen.getByText('add-page-template-set')
			).toBeInTheDocument();
		});

		it('calls createLayoutPageTemplateEntry when the Save button is pressed', async () => {
			await act(async () => {
				renderConvertToPageTemplateModal();
			});

			const descriptionInput = screen.getByLabelText('description');
			const saveButton = screen.getByText('save');

			userEvent.type(descriptionInput, 'This is a description');

			await act(async () => {
				userEvent.click(saveButton);
			});

			expect(
				LayoutService.createLayoutPageTemplateEntry
			).toHaveBeenCalledTimes(1);
			expect(LayoutService.createLayoutPageTemplateEntry).toBeCalledWith(
				expect.objectContaining({
					segmentsExperienceId: '0',
					templateSetDescription: 'This is a description',
					templateSetId: null,
					templateSetName: 'untitled-set',
				})
			);
		});

		it('does not call createLayoutPageTemplateEntry when the input name is empty and the Save button is pressed', async () => {
			await act(async () => {
				renderConvertToPageTemplateModal();
			});

			const nameInput = screen.getByLabelText('name');
			const saveButton = screen.getByText('save');

			fireEvent.change(nameInput, {
				target: {value: ''},
			});
			userEvent.click(saveButton);

			expect(
				LayoutService.createLayoutPageTemplateEntry
			).toHaveBeenCalledTimes(0);
		});
	});
});
