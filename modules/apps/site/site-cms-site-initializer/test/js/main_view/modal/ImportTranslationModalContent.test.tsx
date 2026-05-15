/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';

// eslint-disable-next-line
import {render, screen, waitFor} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import {openToast} from 'frontend-js-components-web';
import React from 'react';

import ApiHelper from '../../../../src/main/resources/META-INF/resources/js/common/services/ApiHelper';
import ImportTranslationModalContent from '../../../../src/main/resources/META-INF/resources/js/main_view/modal/ImportTranslationModalContent';

jest.mock('frontend-js-components-web', () => ({
	...(jest.requireActual('frontend-js-components-web') as object),
	openToast: jest.fn(),
}));

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/common/services/ApiHelper',
	() => ({
		__esModule: true,
		default: {
			postFormData: jest.fn(),
		},
	})
);

const mockModalClose = jest.fn();

const DEFAULT_PROPS = {
	actionLink: '/edit/123',
	itemName: 'My Content',
	onModalClose: mockModalClose,
	translationsAPIURL: '/o/cms/blogs/123/translations',
};

function renderModal() {
	return render(<ImportTranslationModalContent {...DEFAULT_PROPS} />);
}

function createFile(name = 'translations.zip', size = 1024) {
	return new File(['a'.repeat(size)], name, {type: 'application/zip'});
}

async function uploadAndImport(user: ReturnType<typeof userEvent.setup>) {
	const {container} = renderModal();

	const input =
		container.querySelector<HTMLInputElement>('input[type="file"]')!;

	await user.upload(input, createFile());

	await user.click(await screen.findByRole('button', {name: /import/i}));
}

describe('ImportTranslationModalContent', () => {
	afterEach(() => {
		jest.clearAllMocks();
	});

	it('shows the success toast when the import succeeds', async () => {
		(ApiHelper.postFormData as jest.Mock).mockResolvedValue({
			data: {
				failureMessagesJSON: [],
				successMessages: ['translations.zip'],
			},
		});

		await uploadAndImport(userEvent.setup());

		await waitFor(() => {
			expect(openToast).toHaveBeenCalledWith(
				expect.objectContaining({type: 'success'})
			);
		});

		expect(mockModalClose).toHaveBeenCalled();
	});

	it('does not show the success toast when every file fails', async () => {
		(ApiHelper.postFormData as jest.Mock).mockResolvedValue({
			data: {
				failureMessagesJSON: [
					JSON.stringify({
						container: '',
						errorMessage: 'Invalid file',
						fileName: 'translations.zip',
					}),
				],
				successMessages: [],
			},
		});

		await uploadAndImport(userEvent.setup());

		await waitFor(() => {
			expect(ApiHelper.postFormData).toHaveBeenCalled();
		});

		expect(openToast).not.toHaveBeenCalled();
		expect(mockModalClose).not.toHaveBeenCalled();
	});
});
