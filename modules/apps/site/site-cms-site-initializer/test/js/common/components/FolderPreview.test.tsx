/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';

// eslint-disable-next-line
import {checkAccessibility} from '@liferay/layout-js-components-web/test/__lib__/index';
import {render, screen} from '@testing-library/react';
import React from 'react';

import FolderPreview from '../../../../src/main/resources/META-INF/resources/js/common/components/FolderPreview';

jest.mock('frontend-js-web', () => ({
	...(jest.requireActual('frontend-js-web') as any),
	sub: (str: string, arg: string) => str.replace('{0}', arg),
}));

const mockLiferayLanguageGet = jest.fn((key: string) => {
	if (key === 'x-file') {
		return '{0} file';
	}
	if (key === 'x-files') {
		return '{0} files';
	}
	if (key === 'x-folder') {
		return '{0} folder';
	}
	if (key === 'x-folders') {
		return '{0} folders';
	}

	return key;
});

(global as any).Liferay = {
	Language: {
		get: mockLiferayLanguageGet,
	},
};

describe('FolderPreview', () => {
	it('renders the folder name', () => {
		render(
			<FolderPreview
				filesLength={0}
				name="My Folder"
				subfoldersLength={0}
			/>
		);

		expect(screen.getByText('My Folder')).toBeInTheDocument();
	});

	it('renders singular labels for one file and one subfolder', () => {
		render(
			<FolderPreview
				filesLength={1}
				name="My Folder"
				subfoldersLength={1}
			/>
		);

		expect(screen.getByText('1 folder · 1 file')).toBeInTheDocument();
	});

	it('renders plural labels for multiple files and subfolders', () => {
		render(
			<FolderPreview
				filesLength={2}
				name="My Folder"
				subfoldersLength={3}
			/>
		);

		expect(screen.getByText('3 folders · 2 files')).toBeInTheDocument();
	});

	it('passes accessibility check', async () => {
		const {container} = render(
			<FolderPreview
				filesLength={2}
				name="My Folder"
				subfoldersLength={3}
			/>
		);

		await checkAccessibility({bestPractices: true, context: container});
	});
});
