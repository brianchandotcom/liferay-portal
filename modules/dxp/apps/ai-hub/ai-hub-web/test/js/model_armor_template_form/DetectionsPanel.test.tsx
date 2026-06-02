/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {cleanup, fireEvent, render, screen} from '@testing-library/react';
import React from 'react';

import DetectionsPanel from '../../../src/main/resources/META-INF/resources/js/model_armor_template_form/DetectionsPanel';

import type {ModelArmorTemplate} from '../../../src/main/resources/META-INF/resources/js/model_armor_template_form/types/ModelArmorTemplate';

(global as any).Liferay = {
	Language: {
		get: (key: string) => key,
	},
};

const baseValues: ModelArmorTemplate = {
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

function renderPanel(overrides: Partial<ModelArmorTemplate> = {}) {
	const setField = jest.fn();

	render(
		<DetectionsPanel
			setField={setField}
			values={{...baseValues, ...overrides}}
		/>
	);

	return {setField};
}

describe('DetectionsPanel', () => {
	afterEach(() => {
		cleanup();
	});

	it('flips maliciousUriFilterEnabled when its checkbox is clicked', () => {
		const {setField} = renderPanel({maliciousUriFilterEnabled: false});

		fireEvent.click(
			screen.getByRole('checkbox', {name: 'malicious-uri-filter'})
		);

		expect(setField).toHaveBeenCalledWith(
			'maliciousUriFilterEnabled',
			true
		);
	});

	it('flips piAndJailbreakFilterEnabled when its checkbox is clicked', () => {
		const {setField} = renderPanel({piAndJailbreakFilterEnabled: true});

		fireEvent.click(
			screen.getByRole('checkbox', {
				name: 'prompt-injection-and-jailbreak-filter',
			})
		);

		expect(setField).toHaveBeenCalledWith(
			'piAndJailbreakFilterEnabled',
			false
		);
	});

	it('flips sdpFilterEnabled when its checkbox is clicked', () => {
		const {setField} = renderPanel({sdpFilterEnabled: false});

		fireEvent.click(
			screen.getByRole('checkbox', {name: 'sensitive-data-protection'})
		);

		expect(setField).toHaveBeenCalledWith('sdpFilterEnabled', true);
	});

	it('reflects the current checked state from values', () => {
		renderPanel({
			maliciousUriFilterEnabled: true,
			piAndJailbreakFilterEnabled: false,
			sdpFilterEnabled: true,
		});

		expect(
			screen.getByRole('checkbox', {name: 'malicious-uri-filter'})
		).toBeChecked();
		expect(
			screen.getByRole('checkbox', {
				name: 'prompt-injection-and-jailbreak-filter',
			})
		).not.toBeChecked();
		expect(
			screen.getByRole('checkbox', {name: 'sensitive-data-protection'})
		).toBeChecked();
	});

	it('renders each detection description below its checkbox', () => {
		renderPanel();

		expect(
			screen.getByText('malicious-uri-filter-help')
		).toBeInTheDocument();
		expect(
			screen.getByText('prompt-injection-and-jailbreak-filter-help')
		).toBeInTheDocument();
		expect(
			screen.getByText('sensitive-data-protection-help')
		).toBeInTheDocument();
	});

	it('renders the three detection checkboxes with their labels', () => {
		renderPanel();

		expect(
			screen.getByRole('checkbox', {name: 'malicious-uri-filter'})
		).toBeInTheDocument();
		expect(
			screen.getByRole('checkbox', {
				name: 'prompt-injection-and-jailbreak-filter',
			})
		).toBeInTheDocument();
		expect(
			screen.getByRole('checkbox', {name: 'sensitive-data-protection'})
		).toBeInTheDocument();
	});
});
