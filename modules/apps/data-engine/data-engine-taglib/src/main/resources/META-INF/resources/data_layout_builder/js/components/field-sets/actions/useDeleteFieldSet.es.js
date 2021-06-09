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

import {useForm, useFormState} from 'data-engine-js-components-web';

import {EVENT_TYPES} from '../../../eventTypes';
import {deleteItem} from '../../../utils/client.es';
import {errorToast, successToast} from '../../../utils/toast.es';

const useDeleteFieldSet = () => {
	const dispatch = useForm();
	const {fieldSets} = useFormState();

	return async (fieldSet) => {
		const endpoint = '/o/data-engine/v2.0/data-definitions/';

		try {
			const {ok} = await deleteItem(`${endpoint}${fieldSet.id}`);

			if (!ok) {
				throw new Error();
			}

			dispatch({
				payload: {
					fieldSets: fieldSets.filter(({id}) => id !== fieldSet.id),
				},
				type: EVENT_TYPES.FIELD_SET.UPDATE_LIST,
			});

			successToast(
				Liferay.Language.get('the-item-was-deleted-successfully')
			);
		}
		catch (error) {
			errorToast(Liferay.Language.get('the-item-could-not-be-deleted'));
		}
	};
};

export default useDeleteFieldSet;
