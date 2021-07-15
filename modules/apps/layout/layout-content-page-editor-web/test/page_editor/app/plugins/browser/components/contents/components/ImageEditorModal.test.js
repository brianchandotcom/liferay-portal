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

import {cleanup} from '@testing-library/react';

import '@testing-library/jest-dom/extend-expect';

import updatePreviewImage from '../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/actions/updatePreviewImage';
import {BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/backgroundImageFragmentEntryProcessor';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/editableFragmentEntryProcessor';
import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/freemarkerFragmentEntryProcessor';
import FragmentService from '../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/services/FragmentService';
import ImageService from '../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/services/ImageService';
import {updateFragmentsPreviewImage} from '../../../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/browser/components/contents/components/ImageEditorModal';

jest.mock(
	'../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/services/FragmentService',
	() => ({
		renderFragmentEntryLinkContent: jest.fn(() =>
			Promise.resolve({
				content: 'new content',
				fragmentEntryLinkId: '40626',
			})
		),
	})
);

jest.mock(
	'../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/services/ImageService',
	() => ({
		getFileEntry: jest.fn(() =>
			Promise.resolve({fileEntryURL: '/new-preview-url'})
		),
	})
);

jest.mock(
	'../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/actions/updatePreviewImage',
	() => jest.fn()
);

const dispatch = () => null;
const fileEntryId = '40571';
const fragmentEntryLinks = {
	40626: {
		content: 'image content 1',
		editableTypes: {
			'image-square': 'image',
		},
		editableValues: {
			[BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR]: {},
			[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {
				'image-square': {
					defaultValue: '/defaultImageURL',
					en_US: {
						classPK: '40571',
						fileEntryId: '40571',
						title: 'image-1.png',
						url: '/imageURL',
					},
				},
			},
			[FREEMARKER_FRAGMENT_ENTRY_PROCESSOR]: {
				imageSize: 'w-100',
			},
		},
		fragmentEntryLinkId: '40626',
		name: 'Image',
	},

	40628: {
		content: 'image content 2',
		editableValues: {
			[BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR]: {},
			[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {},
			[FREEMARKER_FRAGMENT_ENTRY_PROCESSOR]: {
				itemSelector: {
					classPK: '40571',
					itemSubtype: 'Basic Document',
					itemType: 'Document',
					title: 'image-2.png',
				},
			},
		},
		fragmentEntryLinkId: '40628',
		name: 'Content Display',
	},
};
const languageId = 'en_US';

describe('ImageEditorModal', () => {
	describe('updateFragmentsPreviewImage', () => {
		afterEach(() => {
			cleanup();

			FragmentService.renderFragmentEntryLinkContent.mockClear();
			ImageService.getFileEntry.mockClear();
		});

		it('calls the service that returns the updated content of the fragments', async () => {
			await updateFragmentsPreviewImage({
				dispatch,
				fileEntryId,
				fragmentEntryLinks,
				languageId,
			});

			expect(ImageService.getFileEntry).toHaveBeenCalledWith({
				fileEntryId,
			});
		});

		it('calls the service that returns the updated content of the fragments', async () => {
			await updateFragmentsPreviewImage({
				dispatch,
				fileEntryId,
				fragmentEntryLinks,
				languageId,
			});

			expect(
				FragmentService.renderFragmentEntryLinkContent
			).toHaveBeenCalledWith({fragmentEntryLinkId: '40626'});
		});

		it('dispatchs updateFragmentsPreviewImage action when the promise is resolved ', async () => {
			await updateFragmentsPreviewImage({
				dispatch,
				fileEntryId,
				fragmentEntryLinks,
				languageId,
			});

			expect(updatePreviewImage).toHaveBeenCalledWith({
				contents: [
					{
						content: 'new content',
						fragmentEntryLinkId: '40626',
					},
					{
						content: 'new content',
						fragmentEntryLinkId: '40628',
					},
				],
				fileEntryId,
				previewURL: '/new-preview-url',
			});
		});
	});
});
