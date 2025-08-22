/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {useModal} from '@clayui/modal';
import {IView} from '@liferay/frontend-data-set-web';
import {render, within} from '@testing-library/react';
import {fetch, loadClientExtensions, sub} from 'frontend-js-web';
import React from 'react';

import {ItemSelectorModal} from '../src/main/resources/META-INF/resources';

type TestItem = {
	id: number;
	name: string;
};

const mockFirstItem = {
	id: 1,
	name: 'First Item Name',
};

const mockSecondItem = {
	id: 2,
	name: 'Second Item Name',
};

jest.mock('frontend-js-web', () => {
	const actualPackage = jest.requireActual('frontend-js-web') as any;

	return {
		...actualPackage,
		fetch: jest.fn(() => {
			const headers = new Headers();
			headers.set('Content-Type', 'application/json');

			return Promise.resolve({
				headers,
				json: () =>
					Promise.resolve({
						items: [mockFirstItem, mockSecondItem],
						lastPage: 1,
						page: 1,
					}),
				ok: true,
				status: 200,
			});
		}),
		loadClientExtensions: jest.fn(() => {
			Promise.resolve();
		}),
		sub: jest.fn((...args) => actualPackage.sub(...args)),
	};
});

const mockedFetch = fetch as jest.Mock;
const mockedLoadClientExtensions = loadClientExtensions as jest.Mock;
const mockedSub = sub as jest.Mock;

const ItemSelectorModalWrapper = ({
	defaultOpen,
	selectedItems,
}: {
	defaultOpen: boolean;
	selectedItems: TestItem[];
}) => {
	const {observer, onOpenChange, open} = useModal({defaultOpen});

	return (
		<ItemSelectorModal<TestItem>
			{...{
				fdsProps: {
					apiURL: `${location.origin}/o/headless-delivery/v1.0/test-api-url`,
					id: `itemSelectorModal-test-0001`,
					pagination: {
						deltas: [{label: 20}],
						initialDelta: 20,
					},
					selectionType: 'single',
					views: [
						{
							contentRenderer: 'cards',
							label: 'Cards',
							name: 'cards',
							schema: {
								description: 'description',
								title: 'name',
							},
							thumbnail: 'cards2',
						} as IView,
					],
				},
				itemNameLocator: 'name',
				itemValueLocator: 'id',
				items: selectedItems,
				observer,
				onItemsChange: jest.fn(),
				onOpenChange,
				open,
				type: 'Space',
			}}
		/>
	);
};

describe('ItemSelectorModal component', () => {
	beforeAll(() => {
		Object.assign(Liferay.ThemeDisplay, {
			isImpersonated: () => false,
		});
	});

	afterEach(() => {
		jest.clearAllMocks();
	});

	afterAll(() => {
		Object.assign(Liferay.ThemeDisplay, {
			isImpersonated: undefined,
		});

		jest.restoreAllMocks();
		mockedFetch.mockReset();
		mockedLoadClientExtensions.mockReset();
		mockedSub.mockReset();
	});

	it('renders an open item selector modal, with no selected items', async () => {
		const {findByRole} = render(
			<ItemSelectorModalWrapper defaultOpen={true} selectedItems={[]} />
		);

		const modal = await findByRole('dialog');

		const title = await within(modal).findByRole('heading');

		expect(title).toBeInTheDocument();

		expect(sub).toHaveBeenNthCalledWith(1, 'select-x', 'Space');

		const items = await within(modal).findAllByLabelText(/item name$/gi);

		expect(items).toHaveLength(2);

		const [firstItem, secondItem] = items;

		expect(firstItem).toHaveTextContent(mockFirstItem.name);

		expect(secondItem).toHaveTextContent(mockSecondItem.name);

		const footerActions = await within(modal).findByRole('group');

		const [cancel, select] =
			await within(footerActions).findAllByRole('button');

		expect(cancel).toBeInTheDocument();

		expect(select).toBeInTheDocument();

		expect(select).toBeDisabled();
	});

	it('renders an open single item selector modal, with one selected items', async () => {
		const {findByRole} = render(
			<ItemSelectorModalWrapper
				defaultOpen={true}
				selectedItems={[mockSecondItem]}
			/>
		);

		const modal = await findByRole('dialog');

		const radios = await within(modal).findAllByRole('radio');

		expect(radios).toHaveLength(2);

		const [firstItemRadio, secondItemRadio] = radios;

		expect(firstItemRadio).not.toBeChecked();

		expect(secondItemRadio).toBeChecked();

		const selectedMessage = await within(modal).findByText('x-selected');

		expect(selectedMessage).toBeInTheDocument();

		expect(sub).toHaveBeenNthCalledWith(
			2,
			'x-selected',
			expect.objectContaining({
				props: {
					children: mockSecondItem.name,
				},
				type: 'strong',
			})
		);

		const footerActions = await within(modal).findByRole('group');

		const [cancel, select] =
			await within(footerActions).findAllByRole('button');

		expect(cancel).toBeInTheDocument();

		expect(select).toBeInTheDocument();

		expect(select).toBeEnabled();
	});
});
