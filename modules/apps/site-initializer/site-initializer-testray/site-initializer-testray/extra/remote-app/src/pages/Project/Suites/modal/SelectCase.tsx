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
import {useParams} from 'react-router-dom';

import i18n from '../../../../i18n';
import {filters} from '../../../../schema/filter';
import {searchUtil} from '../../../../util/search';
import {CaseListView} from '../../Cases';

type SelectCaseParametersProps = {
	displayTitle?: boolean;
	selectedCaseIds?: number[];
	setState: any;
};

const SelectCaseParameters: React.FC<SelectCaseParametersProps> = ({
	selectedCaseIds = [],
	displayTitle = true,
	setState,
}) => {
	const {projectId} = useParams();

	return (
		<CaseListView
			listViewProps={{
				initialContext: {selectedRows: selectedCaseIds},
				managementToolbarProps: {
					addButton: undefined,
					filterFields: filters.case as any,
					title: displayTitle ? i18n.translate('cases') : '',
				},

				onContextChange: ({selectedRows}) => setState(selectedRows),
			}}
			tableProps={{
				columns: [
					{key: 'priority', value: i18n.translate('priority')},
					{
						key: 'component',
						render: (component) => component?.name,
						value: i18n.translate('component'),
					},
					{key: 'name', value: i18n.translate('name')},
				],
				rowSelectable: true,
			}}
			variables={{
				filter: projectId
					? searchUtil.eq('projectId', projectId)
					: null,
			}}
		/>
	);
};

export default SelectCaseParameters;
