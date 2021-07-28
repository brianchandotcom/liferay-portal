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

import {FIELD_TYPE_FIELDSET} from '../../../src/main/resources/META-INF/resources/js/utils/constants';
import {
	isFieldSetChild,
	localizeField,
} from '../../../src/main/resources/META-INF/resources/js/utils/fieldSupport';

describe('Field Support Utilities', () => {
	describe('isFieldSetChild', () => {
		it('returns true when a field is a child of a FieldSet', () => {
			const pages = [
				{
					rows: [
						{
							columns: [
								{
									fields: [
										{
											ddmStructureId: 123,
											fieldName: 'myFieldSet',
											nestedFields: [
												{
													fieldName: 'fieldSetChild',
												},
											],
											type: FIELD_TYPE_FIELDSET,
										},
										{
											fieldName: 'notAFieldSet',
										},
										{
											fieldName: 'otherFieldset',
											nestedFields: [
												{
													fieldName: 'sectionChild',
												},
											],
											type: FIELD_TYPE_FIELDSET,
										},
									],
								},
							],
						},
					],
				},
			];

			expect(isFieldSetChild(pages, 'fieldSetChild')).toBe(true);
			expect(isFieldSetChild(pages, 'myFieldSet')).toBe(false);
			expect(isFieldSetChild(pages, 'notAFieldSet')).toBe(false);
			expect(isFieldSetChild(pages, 'sectionChild')).toBe(false);
		});
	});

	describe('localizeField', () => {
		it('adds a new entry in localized value with default language value', () => {
			const localizedField = localizeField(
				{
					dataType: 'string',
					fieldName: 'label',
					localizable: true,
					localizedValue: {
						'en-US': 'English Label',
					},
					value: 'English Label',
				},
				'en-US',
				'pt-BR',
				{
					label: {
						'en-US': {
							edited: false,
						},
						'pt-BR': {
							edited: false,
						},
					},
				}
			);

			expect(localizedField.localizedValue['en-US']).toBe(
				'English Label'
			);
			expect(localizedField.localizedValue['pt-BR']).toBe(
				'English Label'
			);
			expect(localizedField.value).toBe('English Label');
		});

		it('changes field value when field is edited on editing language id', () => {
			const localizedField = localizeField(
				{
					dataType: 'string',
					fieldName: 'label',
					localizable: true,
					localizedValue: {
						'en-US': 'English Label',
						'pt-BR': 'Portuguese Label',
					},
					value: 'English Label',
				},
				'en-US',
				'pt-BR',
				{
					label: {
						'en-US': {
							edited: false,
						},
						'pt-BR': {
							edited: true,
						},
					},
				}
			);

			expect(localizedField.localizedValue['en-US']).toBe(
				'English Label'
			);
			expect(localizedField.localizedValue['pt-BR']).toBe(
				'Portuguese Label'
			);
			expect(localizedField.value).toBe('Portuguese Label');
		});

		it('uses default option label when option is not edited on editing language id', () => {
			const localizedField = localizeField(
				{
					dataType: 'ddm-options',
					fieldName: 'label',
					localizable: false,
					localizedValue: {
						'en-US': {
							'en-US': [
								{
									edited: true,
									label: 'English Option',
									value: 'Option123456',
								},
							],
						},
						'pt-BR': {
							'en-US': [
								{
									edited: true,
									label: 'English Option',
									value: 'Option123456',
								},
							],
						},
					},
					value: {
						'en-US': [
							{
								edited: true,
								label: 'English Option',
								value: 'Option123456',
							},
						],
						'pt-BR': [
							{
								edited: false,
								label: '',
								value: 'Option123456',
							},
						],
					},
				},
				'en-US',
				'pt-BR',
				undefined
			);

			const [firstOption] = localizedField.value['pt-BR'];

			expect(firstOption.label).toBe('English Option');

			const [firstLocalizedOption] = localizedField.localizedValue[
				'pt-BR'
			]['pt-BR'];

			expect(firstLocalizedOption.label).toBe('English Option');
		});

		it('changes option localized value according to editing language id', () => {
			const localizedField = localizeField(
				{
					dataType: 'ddm-options',
					fieldName: 'label',
					localizable: false,
					localizedValue: {
						'en-US': {
							'en-US': [
								{
									edited: true,
									label: 'English Option',
									value: 'Option123456',
								},
							],
						},
						'pt-BR': {
							'en-US': [
								{
									edited: true,
									label: 'English Option',
									value: 'Option123456',
								},
							],
						},
					},
					value: {
						'en-US': [
							{
								edited: true,
								label: 'English Option',
								value: 'Option123456',
							},
						],
						'pt-BR': [
							{
								edited: true,
								label: 'Portuguese Option',
								value: 'Option123456',
							},
						],
					},
				},
				'en-US',
				'pt-BR',
				undefined
			);

			const [firstOption] = localizedField.value['pt-BR'];

			expect(firstOption.label).toBe('Portuguese Option');

			const [firstLocalizedOption] = localizedField.localizedValue[
				'pt-BR'
			]['pt-BR'];

			expect(firstLocalizedOption.label).toBe('Portuguese Option');
		});
	});
});
