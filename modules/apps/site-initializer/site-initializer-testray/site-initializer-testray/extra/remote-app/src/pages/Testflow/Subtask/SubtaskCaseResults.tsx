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

import ListView from '../../../components/ListView';
import StatusBadge from '../../../components/StatusBadge';
import i18n from '../../../i18n';
import {
	caseResultsResource,
	testrayCaseResultRest,
} from '../../../services/rest';
import {getStatusLabel} from '../../../util/constants';

const SubtasksCaseResults = () => {
	return (
		<ListView
			managementToolbarProps={{
				visible: false,
			}}
			resource={caseResultsResource}
			tableProps={{
				columns: [
					{
						key: 'case',
						render: (testrayCase) => {
							return testrayCase?.caseNumber;
						},
						value: i18n.translate('run'),
					},
					{
						key: 'case',
						render: (testrayCase) => {
							return testrayCase?.priority;
						},
						value: i18n.translate('priority'),
					},
					{
						clickable: true,
						key: 'component',
						render: (component) => {
							return component?.team?.name;
						},
						value: i18n.translate('team'),
					},
					{
						clickable: true,
						key: 'component',
						render: (component) => {
							return component?.name;
						},
						value: i18n.translate('component'),
					},
					{
						clickable: true,
						key: 'case',
						render: (testrayCase) => testrayCase?.name,
						size: 'md',
						value: i18n.translate('case'),
					},
					{key: 'issues', value: i18n.translate('issues')},

					{
						key: 'dueStatus',
						render: (dueStatus) => {
							return (
								<StatusBadge type={getStatusLabel(dueStatus)}>
									{getStatusLabel(dueStatus)}
								</StatusBadge>
							);
						},
						value: i18n.translate('status'),
					},
				],
				navigateTo: ({build, id}) =>
					`/project/routines/${build?.routine?.id}/build/${build?.id}/case-result/${id}`,
			}}
			transformData={(response) =>
				testrayCaseResultRest.transformDataFromList(response)
			}
		/>
	);
};
export default SubtasksCaseResults;
