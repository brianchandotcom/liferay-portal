/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {render, screen, waitFor} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import ContentEditorSidePanel from '../../../../src/main/resources/META-INF/resources/js/content_editor/components/ContentEditorSidePanel';
import {mockFetch} from '../../__mocks__/frontend-js-web';

const EXPIRATION_DATE = '2025-08-14T00:01';
const REVIEW_DATE = '2025-08-15T00:01';

const renderComponent = ({isSubscribed = false} = {}) => {
	return render(
		<ContentEditorSidePanel
			addCommentURL="addCommentURL"
			comments={[]}
			deleteCommentURL="deleteCommentURL"
			editCommentURL="editCommentURL"
			editorConfig={{}}
			expirationDate={EXPIRATION_DATE}
			id="contentId"
			isSubscribed={isSubscribed}
			reviewDate={REVIEW_DATE}
			subscribeURL="subscribeURL"
			type="Content Type"
			version="Version 1"
		/>
	);
};

describe('ContentEditorSidePanel', () => {
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

		const subscribeButton = screen.getByLabelText('subscribe');

		expect(subscribeButton).toBeEnabled();

		const clickPromise = userEvent.click(subscribeButton);

		await waitFor(() => {
			expect(subscribeButton).toBeDisabled();
		});

		await clickPromise;

		await waitFor(() => {
			expect(subscribeButton).toBeEnabled();
		});

		expect(mockFetch).toBeCalledWith('subscribeURL', {
			body: {
				cmd: 'subscribe',
			},
			method: 'POST',
		});

		await waitFor(() => {
			expect(
				screen.getByText('you-have-successfully-subscribed-to-comments')
			).toBeInTheDocument();
		});
	});

	it('calls the unsubscribe request', async () => {
		renderComponent({isSubscribed: true});

		await userEvent.click(screen.getByLabelText('comments'));

		await waitFor(() => {
			expect(screen.getByText('comments')).toBeInTheDocument();
		});

		const unsubscribeButton = screen.getByLabelText('unsubscribe');

		expect(unsubscribeButton).toBeEnabled();

		const clickPromise = userEvent.click(unsubscribeButton);

		await waitFor(() => {
			expect(unsubscribeButton).toBeDisabled();
		});

		await clickPromise;

		await waitFor(() => {
			expect(unsubscribeButton).toBeEnabled();
		});

		expect(mockFetch).toBeCalledWith('subscribeURL', {
			body: {
				cmd: 'unsubscribe',
			},
			method: 'POST',
		});

		await waitFor(() => {
			expect(
				screen.getByText(
					'you-have-successfully-unsubscribed-from-comments'
				)
			).toBeInTheDocument();
		});
	});

	it('renders the hidden inputs with initial values', async () => {
		renderComponent();

		const expirationInput: HTMLInputElement | null = document.querySelector(
			'[name="expirationDate"]'
		);
		const reviewInput: HTMLInputElement | null = document.querySelector(
			'[name="reviewDate"]'
		);

		expect(expirationInput?.value).toBe(EXPIRATION_DATE);
		expect(reviewInput?.value).toBe(REVIEW_DATE);
	});
});
