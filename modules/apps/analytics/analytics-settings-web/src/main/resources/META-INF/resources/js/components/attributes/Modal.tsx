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
import React, {useState} from 'react';

import Table from '../table/Table';
import {TColumn, TFormattedItems, TTableRequestParams} from '../table/types';

type TRawItem = {
	example: string;
	name: string;
	required: boolean;
	selected: boolean;
	source: string;
	type: string;
};
interface IModalProps {
	observer: any;
	onCancel: () => void;
	onSubmit: () => void;
	requestFn: (params: TTableRequestParams) => Promise<any>;
	title: string;
	updateFn: (fields: TRawItem[]) => Promise<any>;
}

const columns: Array<TColumn> = [
	{
		expanded: true,
		label: Liferay.Language.get('attribute'),
		value: 'attribute',
	},

	{
		expanded: true,
		label: Liferay.Language.get('data-type'),
		value: 'dataType',
	},
	{
		expanded: true,
		label: Liferay.Language.get('sample-data'),
		value: 'sampleData',
	},
	{
		expanded: false,
		label: Liferay.Language.get('source'),
		show: false,
		sortable: false,
		value: 'source',
	},
];

const Modal: React.FC<IModalProps> = ({
	observer,
	onCancel,
	onSubmit,
	requestFn,
	title,
	updateFn,
}) => {
	const [items, setItems] = useState<TFormattedItems>({});

	return (
		<ClayModal center observer={observer} size="lg">
			<ClayModal.Header>{title}</ClayModal.Header>

			<ClayModal.Body>
				<Table<TRawItem>
					columns={columns}
					emptyStateTitle={Liferay.Language.get(
						'there-are-no-attributes'
					)}
					mapperItems={(items) => {
						return items.map(
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
									{label: name},
									{label: type},
									{label: example},
									{label: source, show: false},
								],
								disabled: required,
								id: name,
							})
						);
					}}
					noResultsTitle={Liferay.Language.get(
						'no-attributes-were-found'
					)}
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

						<ClayButton
							onClick={async () => {
								const fields: TRawItem[] = getFields(items);
								const {ok} = await updateFn(fields);

								ok && onSubmit();
							}}
						>
							{Liferay.Language.get('sync')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
};

function getFields(items: TFormattedItems): TRawItem[] {
	return Object.values(items).map(
		({
			checked,
			columns: [
				{label: name},
				{label: type},
				{label: example},
				{label: source},
			],
			disabled,
		}) => ({
			example,
			name,
			required: disabled,
			selected: checked,
			source,
			type,
		})
	);
}

export default Modal;
