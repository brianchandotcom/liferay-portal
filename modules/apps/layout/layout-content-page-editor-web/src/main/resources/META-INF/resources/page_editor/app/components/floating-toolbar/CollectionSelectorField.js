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
import React from 'react';

import CollectionSelector from '../../../common/components/CollectionSelector';
import {ConfigurationFieldPropTypes} from '../../../prop-types/index';

export const CollectionSelectorField = ({field, onValueSelect, value}) => {
	const {typeOptions = {}} = field;

	return (
		<ClayForm.Group small>
			<CollectionSelector
				collectionTitle={value.title}
				itemSelectorURL={typeOptions.infoListSelectorURL}
				label={field.label}
				onCollectionSelect={collection => {
					onValueSelect(field.name, collection);
				}}
			></CollectionSelector>
		</ClayForm.Group>
	);
};

CollectionSelectorField.propTypes = {
	field: PropTypes.shape(ConfigurationFieldPropTypes).isRequired,
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.oneOfType([PropTypes.string, PropTypes.object]),
};
