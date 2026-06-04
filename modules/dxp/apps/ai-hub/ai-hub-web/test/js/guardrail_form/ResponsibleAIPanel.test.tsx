/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {cleanup, fireEvent, render, screen} from '@testing-library/react';
import React from 'react';

import ResponsibleAIPanel from '../../../src/main/resources/META-INF/resources/js/guardrail_form/ResponsibleAIPanel';

import type {Guardrail} from '../../../src/main/resources/META-INF/resources/js/guardrail_form/types/Guardrail';

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

(global as any).Liferay = {
	Language: {
		get: (key: string) => key,
	},
};

const baseValues: Guardrail = {
	active: true,
	description: '',
	externalReferenceCode: '',
	guardrailType: 'input',
	maliciousUriFilterEnabled: false,
	multilanguageDetectionEnabled: false,
	piAndJailbreakConfidenceLevel: 'mediumAndAbove',
	piAndJailbreakFilterEnabled: false,
	raiDangerousLevel: 'none',
	raiHarassmentLevel: 'none',
	raiHateSpeechLevel: 'none',
	raiSexuallyExplicitLevel: 'none',
	sdpFilterEnabled: false,
	title_i18n: {},
};

function renderPanel(overrides: Partial<Guardrail> = {}) {
	const setField = jest.fn();

	render(
		<ResponsibleAIPanel
			readOnly={false}
			setField={setField}
			values={{...baseValues, ...overrides}}
		/>
	);

	return {setField};
}

describe('ResponsibleAIPanel', () => {
	afterEach(() => {
		cleanup();
	});

	it.each([
		['raiDangerousLevel', 'lowAndAbove'],
		['raiHarassmentLevel', 'high'],
		['raiHateSpeechLevel', 'high'],
		['raiSexuallyExplicitLevel', 'mediumAndAbove'],
	])('forwards %s selection (%s) via setField', (field, value) => {
		const {setField} = renderPanel();

		fireEvent.change(screen.getByTestId(field), {target: {value}});

		expect(setField).toHaveBeenCalledWith(field, value);
	});

	it('reflects the current value for each picker', () => {
		renderPanel({
			raiDangerousLevel: 'high',
			raiHarassmentLevel: 'lowAndAbove',
			raiHateSpeechLevel: 'mediumAndAbove',
			raiSexuallyExplicitLevel: 'none',
		});

		expect(screen.getByTestId('raiDangerousLevel')).toHaveValue('high');
		expect(screen.getByTestId('raiHarassmentLevel')).toHaveValue(
			'lowAndAbove'
		);
		expect(screen.getByTestId('raiHateSpeechLevel')).toHaveValue(
			'mediumAndAbove'
		);
		expect(screen.getByTestId('raiSexuallyExplicitLevel')).toHaveValue(
			'none'
		);
	});

	it('renders the four RAI filter labels', () => {
		renderPanel();

		expect(screen.getByText('dangerous')).toBeInTheDocument();
		expect(screen.getByText('harassment')).toBeInTheDocument();
		expect(screen.getByText('hate-speech')).toBeInTheDocument();
		expect(screen.getByText('sexually-explicit')).toBeInTheDocument();
	});
});
