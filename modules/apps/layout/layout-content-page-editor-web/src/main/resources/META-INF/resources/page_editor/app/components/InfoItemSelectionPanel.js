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

import ClayForm, {ClaySelectWithOption} from '@clayui/form';
import React, {useContext} from 'react';

import ItemSelector from '../../common/components/ItemSelector';
import addMappedInfoItem from '../actions/addMappedInfoItem';
import {DispatchContext} from '../reducers/index';

export default function InfoItemSelectionPanel({selectedItem = {}}) {
	const dispatch = useContext(DispatchContext);

	const onItemSelectorChanged = item => {
		dispatch(addMappedInfoItem(item));
	};

	return (
		<>
			<ClayForm.Group small>
				<ItemSelector
					label={Liferay.Language.get('content')}
					onItemSelect={onItemSelectorChanged}
					selectedItem={selectedItem}
				/>
			</ClayForm.Group>
			<ClayForm.Group>
				<label htmlFor="containerBackgroundImageFieldSelect">
					{Liferay.Language.get('field')}
				</label>
				<ClaySelectWithOption
					aria-label={Liferay.Language.get('field')}
					disabled
					id="containerBackgroundImageFieldSelect"
					onChange={({target: {value}}) => value}
					options={[
						{
							label: Liferay.Language.get('manual-selection'),
							value: 'manual_selection'
						},
						{
							label: Liferay.Language.get('content-mapping'),
							value: 'content_mapping'
						}
					]}
				/>
			</ClayForm.Group>
		</>
	);
}
