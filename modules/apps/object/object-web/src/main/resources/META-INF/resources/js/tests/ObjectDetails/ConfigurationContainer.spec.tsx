/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {render, screen} from '@testing-library/react';
import {renderHook} from '@testing-library/react-hooks';
import React from 'react';

import {ConfigurationContainer} from '../../components/ObjectDetails/ConfigurationContainer';
import {useObjectDetailsForm} from '../../components/ObjectDetails/useObjectDetailsForm';

describe('The ConfigurationContainer component', () => {
	beforeEach(() => {
		global.Liferay = {
			FeatureFlags: {'LPD-17564': true},
			Language: {
				get: jest.fn((key) => key),
			},
		} as any;
	});

	const initialValues: Partial<ObjectDefinition> = {
		active: true,
		defaultLanguageId: 'en_US',
		externalReferenceCode: 'erc',
		id: 1,
		label: {en_US: 'label'},
		name: 'name',
		pluralLabel: {en_US: 'pluralLabel'},
	};

	describe('renders object entry schedule toggle', () => {
		const scheduleToggleLabel =
			'allow-users-to-schedule-a-display-expiration-and-review-date-for-entries';

		const {result} = renderHook(useObjectDetailsForm, {
			initialProps: {initialValues, onSubmit: () => {}},
		});

		const configurationContainer = (isEnableObjectEntrySchedule: boolean) =>
			render(
				<ConfigurationContainer
					hasUpdateObjectDefinitionPermission
					isEnableObjectEntrySchedule={isEnableObjectEntrySchedule}
					isRootDescendantNode={false}
					setValues={result.current.setValues}
					values={result.current.values}
				/>
			);

		it('checked or uncheked according to interactions', () => {
			configurationContainer(false);

			const scheduleToggle = screen.getByRole('switch', {
				name: scheduleToggleLabel,
			});

			scheduleToggle.click();

			expect(scheduleToggle).toBeChecked();

			scheduleToggle.click();

			expect(scheduleToggle).not.toBeChecked();
		});

		it('disabled when isEnableObjectEntrySchedule is true', () => {
			configurationContainer(true);

			expect(
				screen.getByRole('switch', {name: scheduleToggleLabel})
			).toBeDisabled();
		});
	});
});
