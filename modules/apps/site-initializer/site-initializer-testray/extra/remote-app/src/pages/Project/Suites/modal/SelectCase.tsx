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

import React from 'react';

import i18n from '../../../../i18n';
import {CaseListView} from '../../Cases';

type SelectCaseParametersProps = {
	setState: any;
};

const SelectCaseParameters: React.FC<SelectCaseParametersProps> = ({
	setState,
}) => (
	<CaseListView
		listViewProps={{
			managementToolbarProps: {
				addButton: undefined,
				title: i18n.translate('cases'),
			},
			onContextChange: ({selectedRows}) => setState(selectedRows),
		}}
		tableProps={{
			columns: [
				{key: 'priority', value: i18n.translate('priority')},
				{
					key: 'component',
					render: (component) => component.name,
					value: i18n.translate('component'),
				},
				{key: 'name', value: i18n.translate('name')},
			],
			rowSelectable: true,
		}}
	/>
);

export default SelectCaseParameters;
