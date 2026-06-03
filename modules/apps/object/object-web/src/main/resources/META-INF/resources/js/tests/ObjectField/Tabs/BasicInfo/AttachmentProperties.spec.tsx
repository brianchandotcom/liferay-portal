/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen} from '@testing-library/react';
import React from 'react';

import {AttachmentProperties} from '../../../../components/ObjectField/Tabs/BasicInfo/AttachmentProperties';

jest.mock(
	'../../../../components/ObjectField/Tabs/BasicInfo/SpacePicker',
	() => ({
		__esModule: true,
		default: ({onChange}: any) => (
			<button onClick={() => onChange('space-id')}>
				Mock SpacePicker
			</button>
		),
	})
);

const defaultProps = {
	errors: {},
	objectFieldSettings: [],
	onSettingsChange: jest.fn(),
	values: {},
};

const renderComponent = (settings: ObjectFieldSetting[]) =>
	render(
		<AttachmentProperties
			{...defaultProps}
			objectFieldSettings={settings}
		/>
	);

beforeAll(() => {
	(global as any).Liferay = {
		Language: {
			get: (key: string) => key,
		},
	};
});

describe('AttachmentProperties', () => {
	beforeEach(() => {
		jest.clearAllMocks();
	});

	it('does not render storage inputs when no storage mode is active', () => {
		renderComponent([{name: 'fileSource', value: 'documentsAndMedia'}]);

		expect(screen.queryByText(/storage-folder/)).not.toBeInTheDocument();

		expect(
			screen.queryByLabelText(/cms-storage-folder/)
		).not.toBeInTheDocument();
	});

	it('renders Documents and Media folder input when showFilesInLibrary is enabled for userComputerToDocumentsAndMedia upload', () => {
		renderComponent([
			{name: 'fileSource', value: 'userComputerToDocumentsAndMedia'},
			{name: 'showFilesInLibrary', value: true},
		]);

		const label = screen.getByText(/storage-folder/);
		const input = label.closest('.form-group')?.querySelector('input');

		expect(input).toBeInTheDocument();
	});

	it('renders depot storage inputs when showFilesInLibrary is enabled for userComputerToCMSBasicDocument upload', () => {
		renderComponent([
			{name: 'fileSource', value: 'userComputerToCMSBasicDocument'},
			{name: 'showFilesInLibrary', value: true},
		]);

		expect(screen.getByText('Mock SpacePicker')).toBeInTheDocument();

		expect(screen.getByLabelText(/cms-storage-folder/)).toBeInTheDocument();
	});

	it('renders the help text for the storage folder field', () => {
		renderComponent([
			{name: 'fileSource', value: 'userComputerToDocumentsAndMedia'},
			{name: 'showFilesInLibrary', value: true},
		]);

		expect(
			screen.getByText(
				/input-the-path-of-the-chosen-folder-in-documents-and-media/
			)
		).toBeInTheDocument();
	});

	it('renders the error when the required storage folder field is empty', () => {
		render(
			<AttachmentProperties
				{...defaultProps}
				errors={{storageDLFolderPath: 'this-field-is-required'}}
				objectFieldSettings={[
					{
						name: 'fileSource',
						value: 'userComputerToDocumentsAndMedia',
					},
					{name: 'showFilesInLibrary', value: true},
				]}
			/>
		);

		expect(screen.getByText('this-field-is-required')).toBeInTheDocument();
	});

	it('renders the accepted file extensions and maximum file size options on the side panel', () => {
		renderComponent([{name: 'fileSource', value: 'documentsAndMedia'}]);

		expect(
			screen.getByText('accepted-file-extensions')
		).toBeInTheDocument();

		expect(screen.getByText('maximum-file-size')).toBeInTheDocument();
	});
});
