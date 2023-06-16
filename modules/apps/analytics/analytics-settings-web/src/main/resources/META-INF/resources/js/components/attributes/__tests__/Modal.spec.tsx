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

import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';

import '@testing-library/jest-dom/extend-expect';
import {act, render, screen} from '@testing-library/react';
import React, {useState} from 'react';

import Table from '../../table/Table';
import {TColumn, TFormattedItems, TTableRequestParams} from '../../table/types';
import {TRawItem} from '../Modal';

const responseEmptyStateModal = {
	actions: {},
	facets: [],
	items: [],
};

const responseModal = {
	actions: {},
	facets: [],
	items: [
		{
			example: 'True',
			name: 'agreedToTermsOfUse',
			required: false,
			selected: true,
			source: 'user',
			type: 'Boolean',
		},
		{
			example: '31st Oct 2008',
			name: 'birthday',
			required: false,
			selected: true,
			source: 'contact',
			type: 'Date',
		},
	],
};

interface IModalProps {
	observer?: any;
	onCancel: () => void;
	onSubmit: (items: TFormattedItems) => void;
	requestFn: (params: TTableRequestParams) => Promise<any>;
	title: string;
}

enum EColumn {
	Name = 'name',
	Type = 'type',
	Example = 'example',
	Source = 'source',
}

const columns: TColumn[] = [
	{
		expanded: true,
		id: EColumn.Name,
		label: Liferay.Language.get('attribute[noun]'),
	},
	{
		id: EColumn.Type,
		label: Liferay.Language.get('data-type'),
	},
	{
		id: EColumn.Example,
		label: Liferay.Language.get('sample-data'),
		sortable: false,
	},
	{
		id: EColumn.Source,
		label: Liferay.Language.get('source'),
		sortable: false,
	},
];

// NOTE: to render properly in the tests, this Component is sligthly different from attributes/Modal.tsx

const Component: React.FC<IModalProps> = ({
	onCancel,
	onSubmit,
	requestFn,
	title,
}) => {
	const [items, setItems] = useState<TFormattedItems>({});

	return (
		<>
			<ClayModal.Header>{title}</ClayModal.Header>

			<ClayModal.Body>
				<Table<TRawItem>
					columns={columns}
					emptyState={{
						noResultsTitle: Liferay.Language.get(
							'no-attributes-were-found'
						),
						title: Liferay.Language.get('there-are-no-attributes'),
					}}
					mapperItems={(items) =>
						items.map(
							({
								example,
								name,
								required,
								selected,
								source,
								type,
							}) => ({
								checked: selected,
								columns: [
									{id: EColumn.Name, value: name},
									{id: EColumn.Type, value: type},
									{
										id: EColumn.Example,
										value: example,
									},
									{id: EColumn.Source, value: source},
								],
								disabled: required,
								id: name + source,
							})
						)
					}
					onItemsChange={setItems}
					requestFn={requestFn}
				/>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton displayType="secondary" onClick={onCancel}>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton onClick={() => onSubmit(items)}>
							{Liferay.Language.get('sync')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</>
	);
};

describe('Modal', () => {
	it('renders Modal component without crashing', async () => {
		await act(async () => {
			render(
				<Component
					onCancel={() => {}}
					onSubmit={() => {}}
					requestFn={async () => responseModal}
					title="Test Modal"
				/>
			);
		});

		expect(screen.getByText(/Test Modal/i)).toBeInTheDocument();

		expect(screen.getByText(/Test Modal/i)).toHaveClass('modal-title');

		expect(screen.getByText('agreedToTermsOfUse')).toBeInTheDocument();

		expect(screen.getByText('Boolean')).toBeInTheDocument();

		expect(screen.getByText('True')).toBeInTheDocument();

		expect(screen.getByText('user')).toBeInTheDocument();

		expect(screen.getByText('birthday')).toBeInTheDocument();

		expect(screen.getByText('Date')).toBeInTheDocument();

		expect(screen.getByText('31st Oct 2008')).toBeInTheDocument();

		expect(screen.getByText('contact')).toBeInTheDocument();
	});

	it('renders Modal component without crashing with empty state', async () => {
		await act(async () => {
			render(
				<Component
					onCancel={() => {}}
					onSubmit={() => {}}
					requestFn={async () => responseEmptyStateModal}
					title="Test Modal"
				/>
			);
		});

		expect(screen.getByText(/Test Modal/i)).toBeInTheDocument();

		expect(screen.getByText(/Test Modal/i)).toHaveClass('modal-title');

		expect(
			document.querySelector('.c-empty-state-title')
		).toBeInTheDocument();

		expect(screen.getByText('there-are-no-attributes')).toBeInTheDocument();
	});
});
