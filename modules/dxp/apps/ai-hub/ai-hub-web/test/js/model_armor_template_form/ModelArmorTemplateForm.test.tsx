/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {
	cleanup,
	fireEvent,
	render,
	screen,
	waitFor,
} from '@testing-library/react';
import React from 'react';

import ModelArmorTemplateForm from '../../../src/main/resources/META-INF/resources/js/model_armor_template_form/ModelArmorTemplateForm';

const mockGetModelArmorTemplate = jest.fn();
const mockPostModelArmorTemplate = jest.fn();
const mockPutModelArmorTemplate = jest.fn();
const mockOpenToast = jest.fn();

jest.mock(
	'../../../src/main/resources/META-INF/resources/js/model_armor_template_form/services/ModelArmorTemplateService',
	() => ({
		getModelArmorTemplate: (...args: any[]) =>
			mockGetModelArmorTemplate(...args),
		postModelArmorTemplate: (...args: any[]) =>
			mockPostModelArmorTemplate(...args),
		putModelArmorTemplate: (...args: any[]) =>
			mockPutModelArmorTemplate(...args),
	})
);

jest.mock('@liferay/object-js-components-web', () => ({
	openToast: (...args: any[]) => mockOpenToast(...args),
}));

jest.mock('@clayui/core', () => {
	const React = require('react');

	return {
		Option: ({children}: any) =>
			React.createElement(React.Fragment, null, children),
		Picker: ({id, items, onSelectionChange, selectedKey}: any) =>
			React.createElement(
				'select',
				{
					'data-testid': id,
					id,
					'onChange': (event: any) =>
						onSelectionChange(event.target.value),
					'value': selectedKey || '',
				},
				items.map((item: any) =>
					React.createElement(
						'option',
						{key: item.value, value: item.value},
						item.label
					)
				)
			),
	};
});

jest.mock('frontend-js-components-web', () => {
	const React = require('react');

	return {
		FieldBase: ({
			children,
			errorMessage,
			helpMessage,
			id,
			label,
			required,
		}: any) =>
			React.createElement(
				'div',
				null,
				label &&
					React.createElement(
						'label',
						{htmlFor: id},
						label,
						required && '*'
					),
				children,
				errorMessage && React.createElement('div', null, errorMessage),
				helpMessage && React.createElement('small', null, helpMessage)
			),
		InputLocalized: ({
			id,
			label,
			onChange,
			placeholder,
			translations,
		}: any) =>
			React.createElement(
				React.Fragment,
				null,
				React.createElement('label', {htmlFor: id}, label),
				React.createElement('input', {
					id,
					onChange: (event: any) =>
						onChange({en_US: event.target.value}),
					placeholder,
					value: translations?.en_US || '',
				})
			),
	};
});

(global as any).Liferay = {
	Icons: {spritemap: 'icons.svg'},
	Language: {
		get: (key: string) => key,
	},
};

const defaultProps = {
	accountEntryExternalReferenceCode: 'ACCOUNT',
	backURL: '/back',
	externalReferenceCode: '',
	readOnly: false,
};

describe('ModelArmorTemplateForm', () => {
	beforeEach(() => {
		mockGetModelArmorTemplate.mockReset();
		mockPostModelArmorTemplate.mockReset();
		mockPutModelArmorTemplate.mockReset();
		mockOpenToast.mockReset();
	});

	afterEach(() => {
		cleanup();
	});

	describe('panels', () => {
		it('hydrates panel inputs after the fetch resolves', async () => {
			mockGetModelArmorTemplate.mockResolvedValueOnce({
				active: true,
				externalReferenceCode: 'TEMPLATE_X',
				guardrailType: 'input',
				maliciousUriFilterEnabled: false,
				multilanguageDetectionEnabled: false,
				piAndJailbreakConfidenceLevel: 'mediumAndAbove',
				piAndJailbreakFilterEnabled: false,
				r_accountToAIHubModelArmorTemplates_accountEntryERC: 'ACCOUNT',
				raiDangerousLevel: 'none',
				raiHarassmentLevel: 'none',
				raiHateSpeechLevel: 'none',
				raiSexuallyExplicitLevel: 'none',
				sdpFilterEnabled: false,
				title_i18n: {en_US: 'Loaded From API'},
			});

			render(
				<ModelArmorTemplateForm
					{...defaultProps}
					externalReferenceCode="TEMPLATE_X"
				/>
			);

			await waitFor(() => {
				expect(
					screen.getByDisplayValue('Loaded From API')
				).toBeInTheDocument();
			});
		});

		it('shows only the Details panel when no guardrail type is selected', () => {
			render(<ModelArmorTemplateForm {...defaultProps} />);

			expect(screen.getByText('details')).toBeInTheDocument();
			expect(screen.queryByText('detections')).not.toBeInTheDocument();
			expect(
				screen.queryByText('responsible-ai')
			).not.toBeInTheDocument();
		});

		it('shows Details and Detections when the guardrail type is input', async () => {
			mockGetModelArmorTemplate.mockResolvedValueOnce({
				externalReferenceCode: 'TEMPLATE_X',
				guardrailType: 'input',
			});

			render(
				<ModelArmorTemplateForm
					{...defaultProps}
					externalReferenceCode="TEMPLATE_X"
				/>
			);

			await waitFor(() => {
				expect(screen.getByText('detections')).toBeInTheDocument();
			});

			expect(screen.getByText('details')).toBeInTheDocument();
			expect(
				screen.queryByText('responsible-ai')
			).not.toBeInTheDocument();
		});

		it('shows Details and Responsible AI when the guardrail type is output', async () => {
			mockGetModelArmorTemplate.mockResolvedValueOnce({
				externalReferenceCode: 'TEMPLATE_X',
				guardrailType: 'output',
			});

			render(
				<ModelArmorTemplateForm
					{...defaultProps}
					externalReferenceCode="TEMPLATE_X"
				/>
			);

			await waitFor(() => {
				expect(screen.getByText('responsible-ai')).toBeInTheDocument();
			});

			expect(screen.getByText('details')).toBeInTheDocument();
			expect(screen.queryByText('detections')).not.toBeInTheDocument();
		});
	});

	describe('save', () => {
		it('blocks the submit and surfaces a required-field error when a required field is empty', async () => {
			render(<ModelArmorTemplateForm {...defaultProps} />);

			fireEvent.change(
				screen.getByLabelText(/^external-reference-code/i),
				{target: {value: ''}}
			);
			fireEvent.blur(screen.getByLabelText(/^external-reference-code/i));

			fireEvent.click(screen.getByRole('button', {name: 'save'}));

			await waitFor(() => {
				expect(screen.getAllByText('required').length).toBeGreaterThan(
					0
				);
			});

			expect(mockPostModelArmorTemplate).not.toHaveBeenCalled();
			expect(mockPutModelArmorTemplate).not.toHaveBeenCalled();
		});

		it('submits filled values and shows the success toast', async () => {
			mockPostModelArmorTemplate.mockResolvedValueOnce({
				externalReferenceCode: 'TEMPLATE_X',
			});

			render(<ModelArmorTemplateForm {...defaultProps} />);

			fireEvent.change(screen.getByLabelText(/^title/i), {
				target: {value: 'My Template'},
			});
			fireEvent.change(
				screen.getByLabelText(/^external-reference-code/i),
				{target: {value: 'TEMPLATE-X'}}
			);

			fireEvent.click(screen.getByRole('button', {name: 'save'}));

			await waitFor(() => {
				expect(mockPostModelArmorTemplate).toHaveBeenCalledWith(
					expect.objectContaining({
						externalReferenceCode: 'TEMPLATE-X',
						title_i18n: {en_US: 'My Template'},
					})
				);
			});

			await waitFor(() => {
				expect(mockOpenToast).toHaveBeenCalledWith(
					expect.objectContaining({type: 'success'})
				);
			});
		});
	});

	describe('toolbar', () => {
		it('exposes a Cancel link that points at backURL', () => {
			render(
				<ModelArmorTemplateForm
					{...defaultProps}
					backURL="/back-here"
				/>
			);

			const cancel = screen.getByRole('link', {name: 'cancel'});

			expect(cancel).toHaveAttribute('href', '/back-here');
		});

		it('shows the edit-guardrail title when externalReferenceCode is set', async () => {
			mockGetModelArmorTemplate.mockResolvedValueOnce({
				externalReferenceCode: 'TEMPLATE_X',
				title_i18n: {en_US: 'Loaded Title'},
			});

			render(
				<ModelArmorTemplateForm
					{...defaultProps}
					externalReferenceCode="TEMPLATE_X"
				/>
			);

			expect(screen.getByText('edit-guardrail')).toBeInTheDocument();
		});

		it('shows the new-guardrail title when no externalReferenceCode is set', () => {
			render(<ModelArmorTemplateForm {...defaultProps} />);

			expect(screen.getByText('new-guardrail')).toBeInTheDocument();
		});
	});
});
