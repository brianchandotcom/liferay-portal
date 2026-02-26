/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen} from '@testing-library/react';

// @ts-ignore

import fetchMock from 'fetch-mock';
import React from 'react';

import {AttachmentFormBase} from '../../components/ObjectField/AttachmentFormBase';

const SPACE_LIBRARIES_URL =
	/\/o\/headless-asset-library\/v1.0\/asset-libraries\?.*type eq 'Space'.*/;

jest.mock('@liferay/object-js-components-web', () => ({
	SingleSelect: ({items, onSelectionChange, selectedKey}: any) => (
		<select
			aria-label="request-files"
			onChange={(event) => onSelectionChange(event.target.value)}
			value={selectedKey}
		>
			{items.map((item: any) => (
				<option key={item.value} value={item.value}>
					{item.label}
				</option>
			))}
		</select>
	),
	Toggle: ({label, onToggle, toggled}: any) => (
		<label>
			<input
				aria-label={label}
				checked={toggled}
				onChange={(event) => onToggle(event.target.checked)}
				type="checkbox"
			/>

			{label}
		</label>
	),
}));

jest.mock('../../utils/fieldSettings', () => ({
	normalizeFieldSettings: (settings: any[]) =>
		settings.reduce((acc, {name, value}) => {
			acc[name] = value;

			return acc;
		}, {}),
}));

beforeAll(() => {
	global.Liferay = {
		...global.Liferay,
		FeatureFlags: {
			...global.Liferay?.FeatureFlags,
			'LPD-74813': true,
		},
	};
});

const renderComponent = ({
	hasDepotEntry = true,
	objectFieldSettings = [],
	objectDefinitionName = 'MyObject',
	setValues = jest.fn(),
	onSubmit = jest.fn(),
}: any = {}) =>
	render(
		<AttachmentFormBase
			hasDepotEntry={hasDepotEntry}
			objectDefinitionName={objectDefinitionName}
			objectFieldSettings={objectFieldSettings}
			onSubmit={onSubmit}
			setValues={setValues}
			values={{}}
		/>
	);

describe('The AttachmentFormBase component', () => {
	afterEach(() => {
		fetchMock.restore();
		jest.restoreAllMocks();
	});

	beforeEach(() => {
		fetchMock.get(SPACE_LIBRARIES_URL, {
			body: {
				items: [],
				totalCount: 0,
			},
		});
	});

	it('does not render library toggle for CMSBasicDocument file source', () => {
		renderComponent({
			objectFieldSettings: [
				{name: 'fileSource', value: 'CMSBasicDocument'},
			],
		});

		expect(
			screen.queryByLabelText('show-uploaded-files-in-library')
		).not.toBeInTheDocument();
	});

	it('does not render library toggle for documentsAndMedia file source', () => {
		renderComponent({
			objectFieldSettings: [
				{name: 'fileSource', value: 'documentsAndMedia'},
			],
		});

		expect(
			screen.queryByLabelText('show-uploaded-files-in-library')
		).not.toBeInTheDocument();
	});

	it('renders library toggle for userComputerToDocumentsAndMedia file source', () => {
		renderComponent({
			objectFieldSettings: [
				{name: 'fileSource', value: 'userComputerToDocumentsAndMedia'},
			],
		});

		expect(
			screen.getByLabelText('show-uploaded-files-in-library')
		).toBeInTheDocument();
	});

	it('renders library toggle for userComputerToCMSBasicDocument file source', () => {
		renderComponent({
			objectFieldSettings: [
				{name: 'fileSource', value: 'userComputerToCMSBasicDocument'},
			],
		});

		expect(
			screen.getByLabelText('show-uploaded-files-in-library')
		).toBeInTheDocument();
	});
});
