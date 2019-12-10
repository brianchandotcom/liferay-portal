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
import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React, {useContext} from 'react';

import {FRAGMENT_CONFIGURATION_FIELD_TYPES} from '../config/constants/fragmentConfigurationFieldTypes';
import {StoreContext} from '../store/index';

const FieldSet = ({fields, label}) => (
	<>
		{label && <p className="mb-3 sheet-subtitle">{label}</p>}

		{fields.map((field, index) => {
			const FieldComponent =
				field.type && FRAGMENT_CONFIGURATION_FIELD_TYPES[field.type];

			return (
				<ClayForm.Group key={index}>
					<FieldComponent field={field}></FieldComponent>
				</ClayForm.Group>
			);
		})}
	</>
);

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
			<RestoreButton></RestoreButton>
		</div>
	);
};

const RestoreButton = () => (
	<ClayButton borderless className="w-100" displayType="secondary" small>
		<ClayIcon symbol="restore" />
		<span className="ml-2">{Liferay.Language.get('restore-values')}</span>
	</ClayButton>
);
