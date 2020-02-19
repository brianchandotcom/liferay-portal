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
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import ItemSelector from '../../../common/components/ItemSelector';
import {ConfigurationFieldPropTypes} from '../../../prop-types/index';
import {ConfigContext} from '../../config/index';

export const ItemCollectionSelectorField = ({field, onValueSelect, value}) => {
	const config = useContext(ConfigContext);

	const {infoListSelectorURL, portletNamespace} = config;

	const eventName = `${portletNamespace}selectInfoList`;

	return (
		<ClayForm.Group small>
			<ItemSelector
				eventName={eventName}
				itemSelectorURL={infoListSelectorURL}
				label={field.label}
				onItemSelect={item => {
					onValueSelect(field.name, {
						classNameId: item.classNameId,
						classPK: item.classPK,
						infoListProviderKey: item.infoListProviderKey,
						title: item.title
					});
				}}
				selectedItemTitle={value.title}
			/>
		</ClayForm.Group>
	);
};

ItemCollectionSelectorField.propTypes = {
	field: PropTypes.shape(ConfigurationFieldPropTypes).isRequired,
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.string
};
