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

import React from 'react';

import {fetchPeopleFields, updatePeopleFields} from '../../utils/api';
import Modal, {ICommonModalProps} from './Modal';

const columns = [
	{
		expanded: true,
		label: Liferay.Language.get('attribute'),
		value: 'name',
	},
	{
		expanded: true,
		label: Liferay.Language.get('data-type'),
		sortable: false,
		value: 'type',
	},
	{
		expanded: true,
		label: Liferay.Language.get('sample-data'),
		sortable: false,
		value: 'example',
	},
	{
		expanded: false,
		label: Liferay.Language.get('source'),
		show: false,
		sortable: false,
		value: 'source',
	},
];

const PeopleAttributesModal: React.FC<ICommonModalProps> = ({
	observer,
	onCancel,
	onSubmit,
}) => (
	<Modal
		columns={columns}
		observer={observer}
		onCancel={onCancel}
		onSubmit={async (items) => {
			const {ok} = await updatePeopleFields(items);

			ok && onSubmit();
		}}
		requestFn={fetchPeopleFields}
		title={Liferay.Language.get('sync-people-attributes')}
	/>
);

export default PeopleAttributesModal;
