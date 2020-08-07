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
import {cleanup, fireEvent, render} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import {BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/backgroundImageFragmentEntryProcessor';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/editableFragmentEntryProcessor';
import {EDITABLE_TYPES} from '../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/editableTypes';
import {StoreAPIContextProvider} from '../../../../../../../src/main/resources/META-INF/resources/page_editor/app/store/index';
import updateEditableValues from '../../../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateEditableValues';
import {ImageSelector} from '../../../../../../../src/main/resources/META-INF/resources/page_editor/common/components/ImageSelector';
import {ImagePropertiesPanel} from '../../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/page-structure/components/item-configuration-panels/ImagePropertiesPanel';

jest.mock(
	'../../../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateEditableValues',
	() => jest.fn(() => () => {})
);

jest.mock(
	'../../../../../../../src/main/resources/META-INF/resources/page_editor/common/components/ImageSelector',
	() => ({
		ImageSelector: jest.fn(({onClearButtonPressed, onImageSelected}) => (
			<>
				<button onClick={() => onClearButtonPressed()} type="button">
					clear-image
				</button>
				<button
					onClick={() =>
						onImageSelected({
							title: 'New title',
							url: 'new-url.jpg',
						})
					}
					type="button"
				>
					select-new-image
				</button>
			</>
		)),
	})
);

jest.mock(
	'../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config',
	() => ({
		config: {
			defaultLanguageId: 'en',
		},
	})
);

const renderComponent = ({
	editableId = 'e-1',
	imageTitle = null,
	imageDescription = '',
	languageId = 'es',
	segmentsExperienceId = '',
	type = EDITABLE_TYPES.image,
}) =>
	render(
		<StoreAPIContextProvider
			getState={() => ({
				fragmentEntryLinks: {
					'f-1': {
						editableValues: {
							[BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR]: {
								'b-1': {
									config: {
										alt: imageDescription,
										imageTitle:
											typeof imageTitle === 'string'
												? imageTitle
												: 'Background Image Title',
									},
									defaultValue: 'default-bg-url.jpg',
									en: 'en-bg-url.jpg',
									es: 'es-bg-url.jpg',
									it: 'default-bg-url.jpg',
								},
							},

							[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {
								'e-1': {
									config: {
										alt: imageDescription,
										imageTitle:
											typeof imageTitle === 'string'
												? imageTitle
												: 'Editable Image Title',
									},
									defaultValue: 'default-url.jpg',
									en: 'en-url.jpg',
									es: 'es-url.jpg',
									it: 'default-url.jpg',
								},
							},
						},
					},
				},

				languageId,
				segmentsExperienceId,
			})}
		>
			<ImagePropertiesPanel
				debounceDelay={0}
				item={{
					editableId,
					fragmentEntryLinkId: 'f-1',
					itemId: 'i-1',
					type,
				}}
			/>
		</StoreAPIContextProvider>
	);

describe('TextField', () => {
	beforeEach(() => {
		cleanup();
		ImageSelector.mockClear();
		updateEditableValues.mockClear();
	});

	it('renders ImageSelector component', () => {
		renderComponent({});

		expect(ImageSelector).toBeCalledWith(
			expect.objectContaining({
				imageTitle: 'Editable Image Title',
				label: 'image',
			}),
			{}
		);
	});

	it('uses backgroundImage processor namespace if specified on type', () => {
		renderComponent({
			editableId: 'b-1',
			type: EDITABLE_TYPES.backgroundImage,
		});

		expect(ImageSelector).toBeCalledWith(
			expect.objectContaining({
				imageTitle: 'Background Image Title',
				label: 'image',
			}),
			{}
		);
	});

	it('uses imageUrl if imageTitle is not provided', () => {
		renderComponent({
			imageTitle: '',
		});

		expect(ImageSelector).toBeCalledWith(
			expect.objectContaining({
				imageTitle: 'es-url.jpg',
				label: 'image',
			}),
			{}
		);
	});

	it('uses empty imageUrl if its the same than the default one', () => {
		renderComponent({
			imageTitle: '',
			languageId: 'it',
		});

		expect(ImageSelector).toBeCalledWith(
			expect.objectContaining({
				imageTitle: '',
				label: 'image',
			}),
			{}
		);
	});

	it('shows image description field if type is image', () => {
		const {getByLabelText} = renderComponent({});

		expect(getByLabelText('image-description')).toBeInTheDocument();
	});

	it('dispatches updateEditableValues when the image description is changed', async () => {
		const {getByLabelText} = renderComponent({});
		const input = getByLabelText('image-description');

		await userEvent.type(input, 'Random description');

		fireEvent.blur(input);

		expect(updateEditableValues).toHaveBeenLastCalledWith(
			expect.objectContaining({
				editableValues: expect.objectContaining({
					[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: expect.objectContaining(
						{
							'e-1': expect.objectContaining({
								config: {
									alt: 'Random description',
									imageTitle: 'Editable Image Title',
								},
							}),
						}
					),
				}),
			})
		);
	});

	it('dispatches updateEditableValues when a new image is selected', async () => {
		const {getByText} = renderComponent({});
		const button = getByText('select-new-image');

		await userEvent.click(button);

		expect(updateEditableValues).toHaveBeenLastCalledWith(
			expect.objectContaining({
				editableValues: expect.objectContaining({
					[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: expect.objectContaining(
						{
							'e-1': expect.objectContaining({
								config: {
									alt: '',
									imageTitle: 'New title',
								},

								es: 'new-url.jpg',
							}),
						}
					),
				}),
			})
		);
	});

	it('dispatches updateEditableValues when the selected image is cleared', async () => {
		const {getByText} = renderComponent({});
		const button = getByText('clear-image');

		await userEvent.click(button);

		expect(updateEditableValues).toHaveBeenLastCalledWith(
			expect.objectContaining({
				editableValues: expect.objectContaining({
					[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: expect.objectContaining(
						{
							'e-1': expect.objectContaining({
								config: {alt: '', imageTitle: ''},
								es: '',
							}),
						}
					),
				}),
			})
		);
	});

	it('resets image description when the image has changed', async () => {
		const {getByText} = renderComponent({
			imageDescription: 'Some description',
		});

		const button = getByText('clear-image');
		await userEvent.click(button);

		expect(updateEditableValues).toHaveBeenLastCalledWith(
			expect.objectContaining({
				editableValues: expect.objectContaining({
					[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: expect.objectContaining(
						{
							'e-1': expect.objectContaining({
								config: {alt: '', imageTitle: ''},
								es: '',
							}),
						}
					),
				}),
			})
		);
	});

	it('uses segmentsExperienceId inside editableValues if any', async () => {
		const {getByText} = renderComponent({segmentsExperienceId: 's-1'});
		const button = getByText('select-new-image');

		await userEvent.click(button);

		expect(updateEditableValues).toHaveBeenLastCalledWith(
			expect.objectContaining({
				editableValues: expect.objectContaining({
					[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: expect.objectContaining(
						{
							'e-1': expect.objectContaining({
								config: {
									alt: '',
									imageTitle: 'New title',
								},

								es: 'new-url.jpg',
							}),
						}
					),
				}),
			})
		);
	});
});
