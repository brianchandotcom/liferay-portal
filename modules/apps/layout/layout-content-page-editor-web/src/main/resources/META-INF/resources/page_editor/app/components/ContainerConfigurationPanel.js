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

import React, {useContext} from 'react';

import {ConfigContext} from '../config/index';
import {DispatchContext} from '../reducers/index';
import selectPrefixedSegmentsExperienceId from '../selectors/selectPrefixedSegmentsExperienceId';
import {StoreContext} from '../store/index';
import updateItemConfig from '../thunks/updateItemConfig';
import {ContainerBackgroundColorConfiguration} from './ContainerBackgroundColorConfiguration';
import {ContainerPaddingConfiguration} from './ContainerPaddingConfiguration';
import {ContainerTypeConfiguration} from './ContainerTypeConfiguration';

export const ContainerConfigurationPanel = ({item}) => {
	const config = useContext(ConfigContext);
	const dispatch = useContext(DispatchContext);
	const segmentsExperienceId = selectPrefixedSegmentsExperienceId(
		useContext(StoreContext)
	);

	const handleConfigurationValueChanged = itemConfig => {
		dispatch(
			updateItemConfig({
				config,
				itemConfig,
				itemId: item.itemId,
				segmentsExperienceId
			})
		);
	};

	return (
		<>
			<ContainerTypeConfiguration
				containerType={item.config.type}
				onValueChange={handleConfigurationValueChanged}
			/>
			<ContainerPaddingConfiguration
				onValueChange={handleConfigurationValueChanged}
				paddingHorizontal={item.config.paddingHorizontal}
				paddingVertical={item.config.paddingVertical}
			/>
			<ContainerBackgroundColorConfiguration
				backgroundColor={item.config.backgroundColorCssClass}
				onValueChange={handleConfigurationValueChanged}
			/>
		</>
	);
};
