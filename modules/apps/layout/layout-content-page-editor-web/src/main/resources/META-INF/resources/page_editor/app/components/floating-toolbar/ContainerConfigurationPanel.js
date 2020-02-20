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

import PropTypes from 'prop-types';
import React from 'react';

import {
	BackgroundImagePropTypes,
	getLayoutDataItemPropTypes
} from '../../../prop-types/index';
import {CONTAINER_TYPES} from '../../config/constants/containerTypes';
import {LAYOUT_DATA_ITEM_DEFAULT_CONFIGURATIONS} from '../../config/constants/layoutDataItemDefaultConfigurations';
import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';
import selectPrefixedSegmentsExperienceId from '../../selectors/selectPrefixedSegmentsExperienceId';
import {useDispatch, useSelector} from '../../store/index';
import updateItemConfig from '../../thunks/updateItemConfig';
import {ContainerBackgroundColorConfiguration} from './ContainerBackgroundColorConfiguration';
import {ContainerBackgroundImageConfiguration} from './ContainerBackgroundImageConfiguration';
import {
	ContainerPaddingHorizontalConfiguration,
	ContainerPaddingVerticalConfiguration
} from './ContainerPaddingConfiguration';
import {ContainerTypeConfiguration} from './ContainerTypeConfiguration';

export const ContainerConfigurationPanel = ({item}) => {
	const dispatch = useDispatch();
	const segmentsExperienceId = useSelector(
		selectPrefixedSegmentsExperienceId
	);

	const containerConfig = {
		...LAYOUT_DATA_ITEM_DEFAULT_CONFIGURATIONS[
			LAYOUT_DATA_ITEM_TYPES.container
		],
		...item.config
	};

	const handleConfigurationValueChanged = itemConfig => {
		dispatch(
			updateItemConfig({
				itemConfig,
				itemId: item.itemId,
				segmentsExperienceId
			})
		);
	};

	return (
		<>
			<p className="mb-3 sheet-subtitle">
				{Liferay.Language.get('layout')}
			</p>
			<ContainerTypeConfiguration
				containerType={containerConfig.type}
				onValueChange={handleConfigurationValueChanged}
			/>
			<ContainerPaddingVerticalConfiguration
				onValueChange={handleConfigurationValueChanged}
				paddingBottom={containerConfig.paddingBottom}
				paddingTop={containerConfig.paddingTop}
			/>
			{containerConfig.type === CONTAINER_TYPES.fluid && (
				<ContainerPaddingHorizontalConfiguration
					onValueChange={handleConfigurationValueChanged}
					paddingHorizontal={containerConfig.paddingHorizontal}
				/>
			)}
			<p className="mb-3 sheet-subtitle">
				{Liferay.Language.get('background-color')}
			</p>
			<ContainerBackgroundColorConfiguration
				backgroundColor={containerConfig.backgroundColorCssClass}
				onValueChange={handleConfigurationValueChanged}
			/>
			<p className="mb-3 sheet-subtitle">
				{Liferay.Language.get('background-image')}
			</p>
			<ContainerBackgroundImageConfiguration
				backgroundImage={containerConfig.backgroundImage}
				onValueChange={handleConfigurationValueChanged}
			/>
		</>
	);
};

ContainerConfigurationPanel.propTypes = {
	item: getLayoutDataItemPropTypes({
		config: PropTypes.shape({
			backgroundImage: BackgroundImagePropTypes,
			paddingBottom: PropTypes.number,
			paddingHorizontal: PropTypes.number,
			paddingTop: PropTypes.number
		})
	})
};
