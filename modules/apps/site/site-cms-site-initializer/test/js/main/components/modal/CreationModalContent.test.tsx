/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render, screen} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom/extend-expect';

import CreationModalContent from '../../../../../src/main/resources/META-INF/resources/js/main/components/modal/CreationModalContent';

const defaultProps = {
	action: 'createFolder' as const,
	assetLibraries: [
		{groupId: '123', name: 'Space 1'},
		{groupId: '456', name: 'Space 2'},
	],
	closeModal: () => {},
	onSubmit: () => {},
	title: 'Create Folder',
};

describe('CreationModalContent', () => {
	test('renders the modal title in the header', () => {
		render(<CreationModalContent {...defaultProps} />);
		expect(screen.getByText('Create Folder')).toBeInTheDocument();
	});

	test('shows the name field only when action is "createFolder"', () => {
		const {rerender} = render(<CreationModalContent {...defaultProps} />);
		expect(screen.getByLabelText(/name/i)).toBeInTheDocument();

		rerender(
			<CreationModalContent {...defaultProps} action="createAsset" />
		);
		expect(screen.queryByLabelText('name')).not.toBeInTheDocument();
	});

	test('shows the space picker only when there are multiple asset libraries', () => {
		const {rerender} = render(<CreationModalContent {...defaultProps} />);
		expect(screen.getByLabelText(/space/i)).toBeInTheDocument();

		rerender(
			<CreationModalContent
				{...defaultProps}
				assetLibraries={[{groupId: '123', name: 'Only One Space'}]}
			/>
		);
		expect(screen.queryByLabelText(/space/i)).not.toBeInTheDocument();
	});
});
