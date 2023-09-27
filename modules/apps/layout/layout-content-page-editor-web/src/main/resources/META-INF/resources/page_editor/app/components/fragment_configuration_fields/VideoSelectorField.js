/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React from 'react';

import ItemSelector from '../../../common/components/ItemSelector';
import {ConfigurationFieldPropTypes} from '../../../prop_types/index';
import {config} from '../../config/index';
import itemSelectorValueToVideoItem from '../../utils/item_selector_value/itemSelectorValueToVideoItem';

export function VideoSelectorField({field, onValueSelect, value}) {
	const eventName = `${config.portletNamespace}selectVideo`;

	return (
		<ItemSelector
			eventName={eventName}
			helpText={field.description}
			itemSelectorURL={config.videoItemSelectorURL}
			label={field.label}
			modalProps={{height: '60vh', size: 'lg'}}
			onItemSelect={(video) => onValueSelect(field.name, video)}
			selectedItem={value}
			showMappedItems={false}
			transformValueCallback={itemSelectorValueToVideoItem}
		/>
	);
}

VideoSelectorField.propTypes = {
	field: PropTypes.shape(ConfigurationFieldPropTypes).isRequired,
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.oneOfType([PropTypes.string, PropTypes.object]),
};
