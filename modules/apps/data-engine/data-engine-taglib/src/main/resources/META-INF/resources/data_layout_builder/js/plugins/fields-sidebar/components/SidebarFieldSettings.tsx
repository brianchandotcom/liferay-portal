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

import {ClayButtonWithIcon} from '@clayui/button';
import {useConfig, useForm} from 'data-engine-js-components-web';
import React from 'react';

// @ts-ignore

import Sidebar from '../../../components/sidebar/Sidebar.es';

// @ts-ignore

import {EVENT_TYPES} from '../../../eventTypes';

// @ts-ignore

import FieldsSidebarSettingsBody from './FieldsSidebarSettingsBody';

import './SidebarFieldSettings.scss';

const FieldsSidebarSettingsHeader: React.FC<IProps> = ({field}) => {
	const dispatch = useForm();
	const {fieldTypes} = useConfig();
	const {label} = fieldTypes.find(
		({name}) => name === field.type
	) as FieldType;

	return (
		<div className="de__sidebar-field-settings-title">
			<ClayButtonWithIcon
				className="mr-3"
				displayType="secondary"
				monospaced={false}
				onClick={() => dispatch({type: EVENT_TYPES.SIDEBAR.FIELD.BLUR})}
				symbol="angle-left"
			/>
			<Sidebar.Title title={label} />
		</div>
	);
};

const SidebarFieldSettings: React.FC<IProps> = ({field}) => {
	return (
		<Sidebar className="display-settings">
			<Sidebar.Header>
				<FieldsSidebarSettingsHeader field={field} />
			</Sidebar.Header>

			<Sidebar.Body>
				<FieldsSidebarSettingsBody field={field} />
			</Sidebar.Body>
		</Sidebar>
	);
};

export default SidebarFieldSettings;

interface IProps {
	field: Field;
}
