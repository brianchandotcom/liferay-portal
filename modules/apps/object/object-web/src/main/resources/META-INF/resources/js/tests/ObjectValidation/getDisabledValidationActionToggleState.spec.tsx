/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getDisabledValidationActionToggleState} from '../../components/ObjectValidation/ObjectValidationActiveToggle';

function getObjectValidationRuleSettingsMock(
	value: string
): ObjectValidationRuleSetting[] {
	return [
		{
			name: 'allowActiveStatusUpdate',
			value,
		},
	];
}

describe('The getDisabledValidationActionToggleState function should', () => {
	it('return false when allowActiveStatusUpdate is true, disabled is true and disabledGroovyValidation is true', () => {
		const disabled = getDisabledValidationActionToggleState(
			true,
			true,
			getObjectValidationRuleSettingsMock('true')
		);

		expect(disabled).toBe(false);
	});

	it('return false when allowActiveStatusUpdate is true, disabled is true and disabledGroovyValidation is false', () => {
		const disabled = getDisabledValidationActionToggleState(
			true,
			false,
			getObjectValidationRuleSettingsMock('true')
		);

		expect(disabled).toBe(false);
	});

	it('return false when allowActiveStatusUpdate is true, disabled is false and disabledGroovyValidation is true', () => {
		const disabled = getDisabledValidationActionToggleState(
			false,
			true,
			getObjectValidationRuleSettingsMock('true')
		);

		expect(disabled).toBe(false);
	});

	it('return true when allowActiveStatusUpdate is false, disabled is false and disabledGroovyValidation is false', () => {
		const disabled = getDisabledValidationActionToggleState(
			false,
			false,
			getObjectValidationRuleSettingsMock('false')
		);

		expect(disabled).toBe(true);
	});

	it('return true when allowActiveStatusUpdate is false, disabled is true and disabledGroovyValidation is true', () => {
		const disabled = getDisabledValidationActionToggleState(
			true,
			true,
			getObjectValidationRuleSettingsMock('false')
		);

		expect(disabled).toBe(true);
	});

	it('return true when allowActiveStatusUpdate is false, disabled is false and disabledGroovyValidation is true', () => {
		const disabled = getDisabledValidationActionToggleState(
			false,
			true,
			getObjectValidationRuleSettingsMock('false')
		);

		expect(disabled).toBe(true);
	});

	it('return true when allowActiveStatusUpdate is false, disabled is true and disabledGroovyValidation is false', () => {
		const disabled = getDisabledValidationActionToggleState(
			true,
			false,
			getObjectValidationRuleSettingsMock('false')
		);

		expect(disabled).toBe(true);
	});

	it('return false when allowActiveStatusUpdate is true, disabled is false and disabledGroovyValidation is false', () => {
		const disabled = getDisabledValidationActionToggleState(
			false,
			false,
			getObjectValidationRuleSettingsMock('true')
		);

		expect(disabled).toBe(false);
	});
});
