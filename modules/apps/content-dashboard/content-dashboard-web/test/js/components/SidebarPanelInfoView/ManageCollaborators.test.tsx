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

import {cleanup, render, waitFor} from '@testing-library/react';
import {fetch, runScriptsInElement} from 'frontend-js-web';
import React from 'react';

import '@testing-library/jest-dom/extend-expect';

import ManageCollaborators from '../../../../src/main/resources/META-INF/resources/js/components/SidebarPanelInfoView/ManageCollaborators';

jest.mock('frontend-js-web', () => ({
	fetch: jest.fn().mockReturnValue({
		ok: true,
		text: jest
			.fn()
			.mockReturnValue('<button>Manage Collaborators</button>'),
	}),
	runScriptsInElement: jest.fn(),
}));

const fetchSharingCollaboratorsURL =
	'http://localhost:8080/fetch-manage-collaborators-button-url';

const _getComponent = () => {
	return (
		<ManageCollaborators
			fetchSharingCollaboratorsURL={fetchSharingCollaboratorsURL}
			onError={() => {}}
		/>
	);
};

describe('Manage collaborators component', () => {
	afterEach(() => {
		jest.restoreAllMocks();
		cleanup();
	});

	it('call the endpoint and renders', async () => {
		const {getByText} = render(_getComponent());

		expect(fetch).toHaveBeenCalledWith(fetchSharingCollaboratorsURL);

		await waitFor(() => {
			expect(getByText('Manage Collaborators')).toBeInTheDocument();
			expect(runScriptsInElement).toHaveBeenCalled();
		});
	});

	it('handles the API error', async () => {
		(fetch as jest.Mock).mockImplementation(() => {
			return {
				ok: false,
				text: null,
			};
		});

		const {queryByText} = render(_getComponent());

		expect(queryByText('Manage Collaborators')).not.toBeInTheDocument();
	});
});
