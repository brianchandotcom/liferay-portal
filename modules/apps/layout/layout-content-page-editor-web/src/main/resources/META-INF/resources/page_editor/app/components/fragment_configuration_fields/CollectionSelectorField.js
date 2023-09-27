/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React from 'react';

import CollectionSelector from '../../../common/components/CollectionSelector';
import {ConfigurationFieldPropTypes} from '../../../prop_types/index';

export function CollectionSelectorField({field, onValueSelect, value}) {
	const {typeOptions = {}} = field;

	return (
		<CollectionSelector
			collectionItem={value}
			itemSelectorURL={typeOptions.infoListSelectorURL}
			label={field.label}
			onCollectionSelect={(collection) => {
				onValueSelect(field.name, collection);
			}}
		/>
	);
}

CollectionSelectorField.propTypes = {
	field: PropTypes.shape(ConfigurationFieldPropTypes).isRequired,
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.oneOfType([PropTypes.string, PropTypes.object]),
};
