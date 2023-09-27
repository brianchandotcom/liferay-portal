/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {useParams} from 'react-router-dom';

import SearchBuilder from '../../../../core/SearchBuilder';
import i18n from '../../../../i18n';
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
					filterSchema: 'cases',
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
					? SearchBuilder.eq('projectId', projectId)
					: null,
			}}
		/>
	);
};

export default SelectCaseParameters;
