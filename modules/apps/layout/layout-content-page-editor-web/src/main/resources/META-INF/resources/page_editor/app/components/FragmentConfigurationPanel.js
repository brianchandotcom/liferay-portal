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

import {StoreContext} from '../store/index';

const FieldSet = ({fields, label}) => {
	return <>{label && <p class="mb-3 sheet-subtitle">{label}</p>}</>;
};

export const FragmentConfigurationPanel = ({item}) => {
	const {fragmentEntryLinks} = useContext(StoreContext);

	const configuration =
		fragmentEntryLinks[item.config.fragmentEntryLinkId].configuration;

	return (
		<div className="floating-toolbar-configuration-panel">
			{configuration.fieldSets.map((fieldSet, index) => {
				return (
					<FieldSet
						fields={fieldSet.fields}
						key={index}
						label={fieldSet.label}
					/>
				);
			})}
		</div>
	);
};
