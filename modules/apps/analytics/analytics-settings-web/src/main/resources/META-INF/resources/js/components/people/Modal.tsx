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

import {updateAttributesConfiguration} from '../../utils/api';
import {TEmptyState} from '../table/StateRenderer';
import Table from '../table/Table';
import {TColumn, TFormattedItems, TTableRequestParams} from '../table/types';
import {getIds} from '../table/utils';
import {EPeople} from './People';

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
	syncedIds: {
		[key in EPeople]: string[];
	};
}

interface IModalProps {
	columns: TColumn[];
	emptyState: TEmptyState;
	name: EPeople;
	observer: any;
	onCloseModal: () => void;
	requestFn: (params: TTableRequestParams) => Promise<any>;
	syncAllAccounts: boolean;
	syncAllContacts: boolean;
	syncedIds: {
		[key in EPeople]: string[];
	};
	title: string;
}

const Modal: React.FC<IModalProps> = ({
	columns,
	emptyState,
	name,
	observer,
	onCloseModal,
	requestFn,
	syncAllAccounts,
	syncAllContacts,
	syncedIds,
	title,
}) => {
	const [items, setItems] = useState<TFormattedItems>({});

	return (
		<ClayModal center observer={observer} size="lg">
			<ClayModal.Header>{title}</ClayModal.Header>

			<ClayModal.Body>
				<Table<TRawItem>
					columns={columns}
					emptyState={emptyState}
					mapperItems={(items) => {
						return items.map(({id, name, selected}) => ({
							checked: selected,
							columns: [
								{
									id: 'name',
									value: name,
								},
							],
							disabled: false,
							id: String(id),
						}));
					}}
					onItemsChange={setItems}
					requestFn={requestFn}
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

						<ClayButton
							onClick={async () => {
								const {
									ok,
								} = await updateAttributesConfiguration({
									...syncedIds,
									[name]: getIds(
										items,
										syncedIds[name].map((id) => Number(id))
									),
									syncAllAccounts,
									syncAllContacts,
								});

								if (ok) {
									Liferay.Util.openToast({
										message: Liferay.Language.get(
											'people-data-have-been-saved'
										),
									});

									onCloseModal();
								}
							}}
						>
							{Liferay.Language.get('add')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
};

export default Modal;
