/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {
	FieldType,
	FieldTypeName,
	useConfig,
	useForm,
} from 'data-engine-js-components-web';
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
				aria-label={Liferay.Language.get('back')}
				className="mr-3"
				displayType="secondary"
				monospaced={false}
				onClick={() => dispatch({type: EVENT_TYPES.SIDEBAR.FIELD.BLUR})}
				symbol="angle-left"
				title={Liferay.Language.get('back')}
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

interface Field {
	name: string;
	type: FieldTypeName;
}
interface IProps {
	field: Field;
}
