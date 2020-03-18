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

import resolveEditableValue from '../../../../../src/main/resources/META-INF/resources/page_editor/app/components/fragment-content/resolveEditableValue';
import {BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/backgroundImageFragmentEntryProcessor';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/editableFragmentEntryProcessor';

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/page_editor/app/config',
	() => ({
		config: {
			defaultLanguageId: 'en_US',
		},
	})
);

describe('resolveEditableValue', () => {
	it('return the editable value and the config for the given editable values', async () => {
		const editableValues = {
			[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {
				'editable-id': {
					config: {
						href: 'href',
					},
					defaultValue: 'default',
					'segments-experience-id-0': {
						en_US: 'value',
					},
				},
			},
		};

		const result = resolveEditableValue(
			editableValues,
			'editable-id',
			EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
			'en_US',
			'segments-experience-id-0',
			() => {}
		);

		await expect(result).resolves.toStrictEqual(['value', {href: 'href'}]);
	});

	it('return the editable value of the provided processor', async () => {
		const editableValues = {
			[BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR]: {
				'editable-id': {
					config: {
						href: 'href',
					},
					defaultValue: 'default',
					'segments-experience-id-0': {
						en_US: 'value',
					},
				},
			},
		};

		const result = resolveEditableValue(
			editableValues,
			'editable-id',
			BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR,
			'en_US',
			'segments-experience-id-0',
			() => {}
		);

		await expect(result).resolves.toStrictEqual(['value', {href: 'href'}]);
	});

	it('return the default value when the editable has no value', async () => {
		const editableValues = {
			[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {
				'editable-id': {
					config: {
						href: 'href',
					},
					defaultValue: 'default',
				},
			},
		};

		const result = resolveEditableValue(
			editableValues,
			'editable-id',
			EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
			'en_US',
			'segments-experience-id-0',
			() => {}
		);

		await expect(result).resolves.toStrictEqual([
			'default',
			{href: 'href'},
		]);
	});

	it('calls given function to retrieve the editable config when it is mapped', async () => {
		const editableValues = {
			[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {
				'editable-id': {
					config: {
						alt: 'alt',
						classNameId: 3,
						classPK: 2,
						fieldId: 'field',
					},
					defaultValue: 'default',
				},
			},
		};

		const getField = jest.fn(() => Promise.resolve('mapped'));

		const result = resolveEditableValue(
			editableValues,
			'editable-id',
			EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
			'en_US',
			'segments-experience-id-0',
			getField
		);

		expect(getField).toBeCalledWith(
			expect.objectContaining({
				classNameId: 3,
				classPK: 2,
				fieldId: 'field',
				languageId: 'en_US',
			})
		);

		await expect(result).resolves.toStrictEqual([
			'default',
			expect.objectContaining({alt: 'alt', href: 'mapped'}),
		]);
	});

	it('calls given function to retrieve the editable value when it is mapped', async () => {
		const editableValues = {
			[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {
				'editable-id': {
					classNameId: 3,
					classPK: 2,
					config: {
						href: 'href',
					},
					fieldId: 'field',
				},
			},
		};

		const getField = jest.fn(() => Promise.resolve('mapped'));

		const result = resolveEditableValue(
			editableValues,
			'editable-id',
			EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
			'en_US',
			'segments-experience-id-0',
			getField
		);

		expect(getField).toBeCalledWith(
			expect.objectContaining({
				classNameId: 3,
				classPK: 2,
				fieldId: 'field',
				languageId: 'en_US',
			})
		);

		await expect(result).resolves.toStrictEqual(['mapped', {href: 'href'}]);
	});

	it('does not call given function to retrieve the editable value when it is mapped to a display page', async () => {
		const editableValues = {
			[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {
				'editable-id': {
					config: {
						href: 'href',
					},
					defaultValue: 'default',
					mappedField: 'mappedField',
				},
			},
		};

		const getField = jest.fn();

		const result = resolveEditableValue(
			editableValues,
			'editable-id',
			EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
			'en_US',
			'segments-experience-id-0',
			getField
		);

		expect(getField).not.toBeCalled();

		await expect(result).resolves.toStrictEqual([
			'default',
			{href: 'href'},
		]);
	});

	it('returns the editable value correctly when no segments experience is passed', async () => {
		const editableValues = {
			[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {
				'editable-id': {
					config: {
						href: 'href',
					},
					en_US: 'value',
					mappedField: 'mappedField',
				},
			},
		};

		const getField = jest.fn();

		const result = resolveEditableValue(
			editableValues,
			'editable-id',
			EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
			'en_US',
			null,
			getField
		);

		expect(getField).not.toBeCalled();

		await expect(result).resolves.toStrictEqual(['value', {href: 'href'}]);
	});
});
