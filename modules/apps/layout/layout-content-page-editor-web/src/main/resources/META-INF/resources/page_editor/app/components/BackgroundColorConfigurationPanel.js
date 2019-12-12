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

import ClayForm from '@clayui/form';
import React, {useContext} from 'react';

import {DispatchContext} from '../../app/reducers/index';
import ColorPalette from '../../common/components/ColorPalette';
import updateItemConfig from '../actions/updateItemConfig';

export const BackgroundColorConfigurationPanel = ({item}) => {
	const dispatch = useContext(DispatchContext);

	const handleSelectValueChanged = (identifier, value) =>
		dispatch(
			updateItemConfig({
				config: {
					[identifier]: value
				},
				itemId: item.itemId
			})
		);

	return (
		<ClayForm.Group>
			<ColorPalette
				clearButton
				label={Liferay.Language.get('background-color')}
				onColorSelect={handleSelectValueChanged}
			></ColorPalette>
		</ClayForm.Group>
	);
};
