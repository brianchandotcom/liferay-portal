/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	localizeField,
	updateInputMaskProperties,
} from '../../../src/main/resources/META-INF/resources/js/utils/fieldSupport';

describe('Field Support Utilities', () => {
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

	describe('updateInputMaskProperties', () => {
		it('updates input mask properties for predefinedValue and validation fields', () => {
			const field = {
				fieldName: 'numeric',
				settingsContext: {
					pages: [
						{
							rows: [
								{
									columns: [
										{
											fields: [
												{
													fieldName:
														'inputMaskFormat',
													localizedValue: {
														'en-US':
															'(99) 9999-9999',
														'pt-BR':
															'999.999.999.99',
													},
												},
												{
													fieldName:
														'numericInputMask',
													localizedValue: {
														'en-US':
															'{"append":"%","appendType":"suffix"}',
														'pt-BR':
															'{"append":"R$","appendType":"prefix"}',
													},
												},
												{
													fieldName:
														'predefinedValue',
												},
												{
													fieldName: 'validation',
												},
											],
											size: 12,
										},
									],
								},
							],
						},
					],
				},
				type: 'numeric',
			};

			updateInputMaskProperties('pt-BR', field);

			expect(field.append).toBe('R$');
			expect(field.appendType).toBe('prefix');
			expect(field.inputMaskFormat).toBe('999.999.999.99');

			const settingsContextFields =
				field.settingsContext.pages[0].rows[0].columns[0].fields;

			expect(settingsContextFields[2].append).toBe('R$');
			expect(settingsContextFields[2].appendType).toBe('prefix');
			expect(settingsContextFields[2].fieldName).toBe('predefinedValue');
			expect(settingsContextFields[2].inputMaskFormat).toBe(
				'999.999.999.99'
			);

			expect(settingsContextFields[3].append).toBe('R$');
			expect(settingsContextFields[3].appendType).toBe('prefix');
			expect(settingsContextFields[3].fieldName).toBe('validation');
			expect(settingsContextFields[3].inputMaskFormat).toBe(
				'999.999.999.99'
			);
		});
	});
});
