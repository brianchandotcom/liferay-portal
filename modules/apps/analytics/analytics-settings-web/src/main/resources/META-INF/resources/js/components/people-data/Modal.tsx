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

import Table, {TColumn, TItem} from '../table/Table';

type TRawItem = {
	id: number;
	name: string;
	selected: boolean;
};

export interface ICommonModalProps {
	observer: any;
	onCloseModal: () => void;
}

interface IModalProps {
	columns: TColumn[];
	fetchFn: () => Promise<any>;
	observer: any;
	onAddItems: (items: TItem[]) => void;
	onCloseModal: () => void;
	title: string;
}

const Modal: React.FC<IModalProps> = ({
	columns,
	fetchFn,
	observer,
	onAddItems,
	onCloseModal,
	title,
}) => {
	const [items, setItems] = useState<TItem[]>([]);

	return (
		<ClayModal center observer={observer} size="lg">
			<ClayModal.Header>{title}</ClayModal.Header>

			<ClayModal.Body>
				<Table
					columns={columns}
					emptyStateTitle={Liferay.Language.get(
						'there-are-no-attributes'
					)}
					fetchFn={fetchFn}
					mapperItems={(items: TRawItem[]) => {

						// TODO: when attributes backend is done, check if the returned object on map will have changes.
						// Check what values will be passed instead of empty strings on "columns: [name, '', '']"
						// If changes neccessary, check if mapperItems will need to be passed on the parent component.

						return items.map(({id, name, selected}) => ({
							checked: selected,
							columns: [name, '', ''],
							disabled: false,
							id: String(id),
						}));
					}}
					noResultsTitle={Liferay.Language.get(
						'no-attributes-were-found'
					)}
					onItemsChange={setItems}
				/>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={() => onCloseModal()}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton onClick={() => onAddItems(items)}>
							{Liferay.Language.get('sync')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
};

export default Modal;
