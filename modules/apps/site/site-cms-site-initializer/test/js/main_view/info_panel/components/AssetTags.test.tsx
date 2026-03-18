/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {fireEvent, render, screen, waitFor} from '@testing-library/react';
import React from 'react';

import ApiHelper from '../../../../../src/main/resources/META-INF/resources/js/common/services/ApiHelper';
import AssetTags from '../../../../../src/main/resources/META-INF/resources/js/main_view/info_panel/components/AssetTags';

function MockItemSelector({
	onChange,
	primaryAction,
}: {
	onChange: (value: string) => void;
	primaryAction?: {
		label: string;
		onClick: () => void;
	};
}) {
	return (
		<div data-testid="item-selector">
			<input
				data-testid="item-selector-input"
				onChange={(event) => onChange(event.target.value)}
			/>

			{primaryAction && (
				<button
					data-testid="primary-action"
					onClick={primaryAction.onClick}
				>
					{primaryAction.label}
				</button>
			)}
		</div>
	);
}

function MockItemSelectorItem({children}: {children: React.ReactNode}) {
	return <div>{children}</div>;
}

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/js/common/services/ApiHelper'
);

jest.mock('@liferay/frontend-js-item-selector-web', () => {
	return {
		ItemSelector: Object.assign(MockItemSelector, {
			Item: MockItemSelectorItem,
		}),
	};
});

(global as any).Liferay = {
	Language: {
		get: jest.fn((key: string) => key),
	},
	ThemeDisplay: {
		getPortalURL: () => 'https://www.liferay.com',
	},
};

function renderComponent() {
	return render(
		<AssetTags
			assetLibraryId={123}
			cmsGroupId={456}
			hasUpdatePermission={true}
			objectEntry={
				{
					keywords: ['tag1'],
					scopeId: 123,
				} as any
			}
			updateObjectEntry={jest.fn()}
		/>
	);
}

describe('AssetTags', () => {
	afterEach(() => {
		jest.resetAllMocks();
	});

	it('render primaryAction if hasCreatePermission is true and value is typed', async () => {
		(ApiHelper.get as jest.Mock).mockResolvedValue({
			data: {
				actions: {
					create: true,
				},
			},
			error: null,
		});

		renderComponent();

		await waitFor(() => expect(ApiHelper.get).toHaveBeenCalled());

		const input = screen.getByTestId('item-selector-input');

		fireEvent.change(input, {target: {value: 'new-tag'}});

		expect(screen.getByTestId('primary-action')).toBeInTheDocument();
		expect(screen.getByText('create-new-tag-x')).toBeInTheDocument();
	});

	it('do not render primaryAction if hasCreatePermission is false even if value is typed', async () => {
		(ApiHelper.get as jest.Mock).mockResolvedValue({
			data: {
				actions: {},
			},
			error: null,
		});

		renderComponent();

		await waitFor(() => expect(ApiHelper.get).toHaveBeenCalled());

		const input = screen.getByTestId('item-selector-input');

		fireEvent.change(input, {target: {value: 'new-tag'}});

		expect(screen.queryByTestId('primary-action')).not.toBeInTheDocument();
	});

	it('do not render primaryAction if the typed value is already in the keywords list', async () => {
		(ApiHelper.get as jest.Mock).mockResolvedValue({
			data: {
				actions: {
					create: true,
				},
			},
			error: null,
		});

		renderComponent();

		await waitFor(() => expect(ApiHelper.get).toHaveBeenCalled());

		const input = screen.getByTestId('item-selector-input');

		fireEvent.change(input, {target: {value: 'tag1'}});

		expect(screen.queryByTestId('primary-action')).not.toBeInTheDocument();
	});
});
