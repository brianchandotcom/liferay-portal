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
	syncAllAccounts: boolean;
	syncAllContacts: boolean;
}

interface IModalProps {
	columns: TColumn[];
	emptyStateTitle: string;
	fetchFn: () => Promise<any>;
	noResultsTitle: string;
	observer: any;
	onAddItems: (items: TItem[]) => void;
	onCloseModal: () => void;
	title: string;
}

const Modal: React.FC<IModalProps> = ({
	columns,
	fetchFn,
	noResultsTitle,
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
						'there-are-no-account-groups'
					)}
					fetchFn={fetchFn}
					mapperItems={(items: TRawItem[]) => {
						return items.map(({id, name, selected}) => ({
							checked: selected,
							columns: [name],
							disabled: false,
							id: String(id),
						}));
					}}
					noResultsTitle={noResultsTitle}
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
							{Liferay.Language.get('add')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
};

export default Modal;
