/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, fireEvent, render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import {fetch} from 'frontend-js-web';
import React from 'react';

import PageTemplateModal from '../../../src/main/resources/META-INF/resources/js/components/page_template_modal/PageTemplateModal';

import '@testing-library/jest-dom/extend-expect';

jest.mock('frontend-js-web');

function renderConvertToPageTemplateModal() {
	return render(
		<PageTemplateModal
			createTemplateURL="http://localhost:8080/createLayoutPageTemplate"
			getCollectionsURL="http://localhost:8080/getlayoutPageTemplateCollections"
			hasMultipleSegmentsExperienceIds={false}
			layoutId="0"
			onClose={jest.fn()}
			segmentsExperienceId="0"
		/>
	);
}

describe('ConvertToPageTemplateModal', () => {
	afterEach(() => {
		fetch.mockClear();
	});

	beforeAll(() => {
		jest.useFakeTimers();
	});

	describe('Select Page Template Set modal', () => {
		it('renders the Select Page Template Set modal', async () => {
			fetch.mockReturnValue(
				Promise.resolve(
					new Response(
						JSON.stringify([
							{id: '34286', name: 'Untitled Set'},
							{id: '34112', name: 'Untitled Set 2'},
						])
					)
				)
			);

			await act(async () => {
				renderConvertToPageTemplateModal();
			});

			act(() => {
				jest.runAllTimers();
			});

			expect(
				screen.getByText('select-page-template-set')
			).toBeInTheDocument();
		});

		it('does not call createLayoutPageTemplateEntry when a page template set is not selected and the Save button is pressed', async () => {
			fetch.mockReturnValue(
				Promise.resolve(
					new Response(
						JSON.stringify([
							{id: '34286', name: 'Untitled Set'},
							{id: '34112', name: 'Untitled Set 2'},
						])
					)
				)
			);

			await act(async () => {
				renderConvertToPageTemplateModal();
			});

			act(() => {
				jest.runAllTimers();
			});

			const saveButton = screen.getByText('save');

			userEvent.click(saveButton);

			expect(fetch).toHaveBeenCalledTimes(1);
		});

		it('changes the modal when the Save In New Set Button is pressed', async () => {
			fetch.mockReturnValue(
				Promise.resolve(
					new Response(
						JSON.stringify([
							{id: '34286', name: 'Untitled Set'},
							{id: '34112', name: 'Untitled Set 2'},
						])
					)
				)
			);

			await act(async () => {
				renderConvertToPageTemplateModal();
			});

			act(() => {
				jest.runAllTimers();
			});

			const saveInNewSetButton = screen.getByText('save-in-new-set');

			userEvent.click(saveInNewSetButton);

			expect(
				screen.getByText('add-page-template-set')
			).toBeInTheDocument();
		});
	});

	describe('Add Page Template Set modal', () => {
		it('renders the Select Add Template Set modal when there are no sets', async () => {
			fetch.mockReturnValue(
				Promise.resolve({
					json: () => {
						return [];
					},
				})
			);

			await act(async () => {
				renderConvertToPageTemplateModal();
			});

			act(() => {
				jest.runAllTimers();
			});

			expect(
				screen.getByText('add-page-template-set')
			).toBeInTheDocument();
		});

		it('calls createLayoutPageTemplateEntry when the Save button is pressed', async () => {
			fetch.mockReturnValue(
				Promise.resolve({
					json: () => {
						return [];
					},
				})
			);

			await act(async () => {
				renderConvertToPageTemplateModal();
			});

			act(() => {
				jest.runAllTimers();
			});

			const descriptionInput = screen.getByLabelText('description');
			const saveButton = screen.getByText('save');

			userEvent.type(descriptionInput, 'This is a description');

			await act(async () => {
				userEvent.click(saveButton);
			});

			expect(fetch).toHaveBeenCalledTimes(2);
		});

		it('does not call createLayoutPageTemplateEntry when the input name is empty and the Save button is pressed', async () => {
			fetch.mockReturnValue(
				Promise.resolve(
					new Response(
						JSON.stringify([
							{id: '34286', name: 'Untitled Set'},
							{id: '34112', name: 'Untitled Set 2'},
						])
					)
				)
			);

			await act(async () => {
				renderConvertToPageTemplateModal();
			});

			act(() => {
				jest.runAllTimers();
			});

			const nameInput = screen.getByLabelText('page-template-set');
			const saveButton = screen.getByText('save');

			fireEvent.change(nameInput, {
				target: {value: ''},
			});
			userEvent.click(saveButton);

			expect(fetch).toHaveBeenCalledTimes(1);
		});
	});
});
