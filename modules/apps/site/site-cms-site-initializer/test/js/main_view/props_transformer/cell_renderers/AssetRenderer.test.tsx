/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';

// eslint-disable-next-line
import {checkAccessibility} from '@liferay/layout-js-components-web/test/__lib__/index';
import {fireEvent, render, screen} from '@testing-library/react';
import React from 'react';

import AssetRenderer from '../../../../../src/main/resources/META-INF/resources/js/main_view/props_transformer/cell_renderers/AssetRenderer';

jest.mock('frontend-js-web', () => ({
	...(jest.requireActual('frontend-js-web') as Record<string, unknown>),
	sub: (str: string, ...params: string[]) =>
		params.reduce(
			(result, param, index) =>
				result.replace(new RegExp(`\\{${index}\\}`, 'g'), param),
			str
		),
}));

const testActionBase = {
	data: {id: 'update'},
	href: 'http://localhost:8080/o/cms/blogs/12345',
};

const testAdditionalProps = {
	fileMimeTypeCssClasses: {
		default: 'file-icon-color-0',
	},
	fileMimeTypeIcons: {
		default: 'document-default',
	},
	objectDefinitionCssClasses: {
		L_CMS_BLOG: 'content-icon-blog',
		default: 'content-icon-custom-structure',
	},
	objectDefinitionIcons: {
		L_CMS_BLOG: 'blogs',
		default: 'forms',
	},
};

const testBaseProps = {
	actions: [testActionBase],
	additionalProps: testAdditionalProps,
	itemData: {
		actions: {
			update: {
				href: 'http://localhost:8080/o/cms/blogs/12345',
				method: 'PATCH',
			},
		},
		dateModified: '2026-03-24T13:04:42Z',
		embedded: {
			creator: {
				name: 'Test Test',
			},
			systemProperties: {
				objectDefinitionBrief: {
					externalReferenceCode: 'L_CMS_BLOG',
				},
			},
		},
	},
	onViewClick: jest.fn(),
	options: {
		actionId: 'update',
	},
	value: 'blog2',
};

describe('AssetRenderer. Render the value only.', () => {
	it('no update permissions and no onViewClick callback', () => {
		render(
			<AssetRenderer
				{...testBaseProps}
				itemData={{
					...testBaseProps.itemData,
					actions: {update: undefined},
				}}
				onViewClick={undefined}
			/>
		);

		expect(screen.queryByRole('link')).not.toBeInTheDocument();
		expect(screen.getByText(testBaseProps.value)).toBeInTheDocument();
	});

	it('there are no actions and no onViewClick callback', () => {
		render(
			<AssetRenderer
				{...testBaseProps}
				actions={[]}
				onViewClick={undefined}
			/>
		);

		expect(screen.queryByRole('link')).not.toBeInTheDocument();
		expect(screen.getByText(testBaseProps.value)).toBeInTheDocument();
	});
});

describe('AssetRenderer. Render the link.', () => {
	it('renders a direct link when user has update permissions', () => {
		render(<AssetRenderer {...testBaseProps} />);

		const link = screen.getByRole('link', {name: testBaseProps.value});

		expect(link).toHaveAttribute('href', testActionBase.href);
	});

	it('renders the untitled link when title is empty', () => {
		render(<AssetRenderer {...testBaseProps} value="" />);

		expect(
			screen.getByRole('link', {name: 'untitled-asset'})
		).toBeInTheDocument();
	});
});

describe('AssetRenderer. Render the modal.', () => {
	it('renders modal when no update permissions', () => {
		const noPermissionProps = {
			...testBaseProps,
			itemData: {
				...testBaseProps.itemData,
				actions: {
					...testBaseProps.itemData.actions,
					update: undefined,
				},
			},
		};

		render(<AssetRenderer {...noPermissionProps} />);

		const link = screen.getByRole('link', {name: testBaseProps.value});

		expect(link).toHaveAttribute('href', '#');

		fireEvent.click(link);

		expect(testBaseProps.onViewClick).toHaveBeenCalledWith(
			noPermissionProps.itemData
		);
	});
});

describe('AssetRenderer. Show metadata and icons.', () => {
	it('checks the accessibility of component', async () => {
		const {container} = render(<AssetRenderer {...testBaseProps} />);

		await checkAccessibility({bestPractices: true, context: container});
	});

	it('shows the correct sticker class and icon for a Blog', () => {
		const {container} = render(<AssetRenderer {...testBaseProps} />);

		expect(container.querySelectorAll('.sticker')[0]).toHaveClass(
			'content-icon-blog'
		);
		expect(screen.getByRole('presentation', {name: ''})).toHaveClass(
			'lexicon-icon-blogs'
		);
	});
});

describe('AssetRenderer. Modified by user name.', () => {
	beforeAll(() => {
		jest.spyOn(Liferay.Language, 'get').mockImplementation((key) => {
			if (key === 'modified-at-x-by-x') {
				return 'Modified at {0} by {1}';
			}

			return key;
		});
	});

	afterAll(() => {
		jest.restoreAllMocks();
	});

	it('falls back to the creator name when modifiedBy is missing', () => {
		render(<AssetRenderer {...testBaseProps} />);

		expect(screen.getByText(/by Test Test$/)).toBeInTheDocument();
	});

	it('shows modifiedBy name when it is provided', () => {
		render(
			<AssetRenderer
				{...testBaseProps}
				itemData={{
					...testBaseProps.itemData,
					embedded: {
						...testBaseProps.itemData.embedded,
						modifiedBy: {name: 'Admin Moved'},
					},
				}}
			/>
		);

		expect(screen.getByText(/by Admin Moved$/)).toBeInTheDocument();
	});
});
