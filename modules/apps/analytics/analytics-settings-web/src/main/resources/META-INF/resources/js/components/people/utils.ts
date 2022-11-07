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

import {updatePeopleData} from '../../utils/api';
import {SUCCESS_MESSAGE} from '../../utils/constants';
import {TItem} from '../table/Table';
import {Events, useDispatch} from './Context';

function getIds(items: TItem[]): string[] {
	return items.filter(({checked}) => checked).map(({id}) => id);
}

interface IUseAddItemsProps {
	name: string;
	onCloseModal: () => void;
	syncAllAccounts: boolean;
	syncAllContacts: boolean;
}

export function useAddItems({
	name,
	onCloseModal,
	syncAllAccounts,
	syncAllContacts,
}: IUseAddItemsProps) {
	const dispatch = useDispatch();

	return async (items: TItem[]) => {
		const ids = getIds(items);

		const {ok} = await updatePeopleData({
			[name]: ids,
			syncAllAccounts,
			syncAllContacts,
		});

		if (ok) {
			Liferay.Util.openToast({
				message: SUCCESS_MESSAGE,
			});

			dispatch({
				payload: ids.length,
				type: Events.AccountsCount,
			});

			onCloseModal();
		}
	};
}
