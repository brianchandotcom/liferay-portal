/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {render, screen, waitFor} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import {fetch} from 'frontend-js-web';
import React from 'react';

import ContentEditorSidePanel from '../../../../src/main/resources/META-INF/resources/js/content_editor/components/ContentEditorSidePanel';

jest.mock('frontend-js-web', () => ({
	...(jest.requireActual('frontend-js-web') as any),
	fetch: jest.fn(() => Promise.resolve({json: () => Promise.resolve({})})),
}));

const renderComponent = ({isSubscribed = false} = {}) => {
	return render(
		<ContentEditorSidePanel
			addCommentURL="addCommentURL"
			comments={[]}
			editorConfig={{}}
			id="contentId"
			isSubscribed={isSubscribed}
			subscribeURL="subscribeURL"
			type="Content Type"
			version="Version 1"
		/>
	);
};

describe('ContentEditorSidePanel', () => {
	beforeEach(() => {
		jest.clearAllMocks();
	});

	it('renders ContentEditorSidePanel', () => {
		renderComponent();

		expect(screen.getByLabelText('general')).toBeInTheDocument();

		expect(screen.getByLabelText('comments')).toBeInTheDocument();
	});

	it('closes the panel pressing the Close button', async () => {
		renderComponent();

		await userEvent.click(screen.getByLabelText('general'));

		await waitFor(() => {
			expect(screen.getByText('general')).toBeInTheDocument();
		});

		await userEvent.click(screen.getByLabelText('close'));

		await waitFor(() => {
			expect(screen.queryByText('general')).not.toBeInTheDocument();
		});
	});

	it('calls the subscribe request', async () => {
		renderComponent();

		await userEvent.click(screen.getByLabelText('comments'));

		await waitFor(() => {
			expect(screen.getByText('comments')).toBeInTheDocument();
		});

		await userEvent.click(screen.getByLabelText('unsubscribe'));

		expect(fetch).toBeCalledWith('subscribeURL', {
			body: expect.any(FormData),
			method: 'POST',
		});

		const formData = (fetch as jest.Mock).mock.calls[0][1].body;
		const formDataObject = Object.fromEntries(formData.entries());

		expect(formDataObject).toEqual({cmd: 'subscribe'});
	});

	it('calls the unsubscribe request', async () => {
		renderComponent({isSubscribed: true});

		await userEvent.click(screen.getByLabelText('comments'));

		await waitFor(() => {
			expect(screen.getByText('comments')).toBeInTheDocument();
		});

		await userEvent.click(screen.getByLabelText('subscribe'));

		expect(fetch).toBeCalledWith('subscribeURL', {
			body: expect.any(FormData),
			method: 'POST',
		});

		const formData = (fetch as jest.Mock).mock.calls[0][1].body;
		const formDataObject = Object.fromEntries(formData.entries());

		expect(formDataObject).toEqual({cmd: 'unsubscribe'});
	});
});
