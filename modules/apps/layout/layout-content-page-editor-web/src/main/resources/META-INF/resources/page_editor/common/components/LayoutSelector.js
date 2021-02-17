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
import PropTypes from 'prop-types';
import React from 'react';

import {config} from '../../app/config/index';
import ItemSelector from './ItemSelector';

export const LayoutSelector = ({mappedLayout, onLayoutSelect}) => {
	return (
		<div className="mb-3">
			<ItemSelector
				eventName={`${config.portletNamespace}selectLayout`}
				itemSelectorURL={config.layoutItemSelectorURL}
				onItemSelect={(layout) => onLayoutSelect(layout)}
				selectedItemTitle={mappedLayout?.name || ''}
			/>
			<ClayButton
				className="mt-2"
				disabled={!mappedLayout}
				displayType="secondary"
				onClick={() => onLayoutSelect(null)}
				small
			>
				{Liferay.Language.get('clear')}
			</ClayButton>
		</div>
	);
};

LayoutSelector.propTypes = {
	mappedLayout: PropTypes.shape({
		groupId: PropTypes.string.isRequired,
		layoutId: PropTypes.string.isRequired,
		name: PropTypes.string.isRequired,
		privateLayout: PropTypes.bool.isRequired,
	}),
	onLayoutSelect: PropTypes.func.isRequired,
};
