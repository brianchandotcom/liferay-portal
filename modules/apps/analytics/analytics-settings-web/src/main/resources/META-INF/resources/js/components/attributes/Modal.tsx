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

export type TRawItem = {
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
		expanded: true,
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

const Modal: React.FC<IModalProps> = ({
	observer,
	onCancel,
	onSubmit,
	requestFn,
	title,
}) => {
	const [items, setItems] = useState<TFormattedItems>({});

	return (
		<ClayModal center observer={observer} size="lg">
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
		</ClayModal>
	);
};

export default Modal;
