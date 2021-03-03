/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayLabel from '@clayui/label';
import classNames from 'classnames';
import {concatValues} from 'data-engine-js-components-web/js/utils/utils.es';
import React from 'react';

import '../../../css/WorkflowInfoBar.scss';

export default function WorkflowInfo({
	assignees = [],
	appVersion,
	className,
	completed,
	hideColumns = [],
	taskNames = [],
	tasks = [],
}) {
	const emptyValue = '--';

	let assignee = assignees[0]?.name || emptyValue;

	const status = completed ? (
		<ClayLabel displayType="success">
			{Liferay.Language.get('completed')}
		</ClayLabel>
	) : (
		<ClayLabel displayType="info">
			{Liferay.Language.get('pending')}
		</ClayLabel>
	);

	const stepName = taskNames[0] || emptyValue;

	if (assignees[0]?.id === -1) {
		const {appWorkflowRoleAssignments: roles = []} =
			tasks.find(({name}) => name === stepName) || {};

		const roleNames = roles.map(({roleName}) => roleName);

		assignee = roleNames.length ? concatValues(roleNames) : emptyValue;
	}

	const tooltipProps = {
		'data-tooltip-align': 'bottom',
		'data-tooltip-delay': '0',
	};

	const items = [
		{
			label: Liferay.Language.get('status'),
			show: !hideColumns.includes('status'),
			value: status,
		},
		{
			label: Liferay.Language.get('step'),
			show: !hideColumns.includes('step'),
			value: stepName,
		},
		{
			label: Liferay.Language.get('assignee'),
			show: !hideColumns.includes('assignee'),
			tooltip: {
				title: assignee,
				...tooltipProps,
			},
			value: assignee,
		},
		{
			label: Liferay.Language.get('version'),
			show: !hideColumns.includes('status'),
			tooltip: {
				title: Liferay.Language.get('app-version'),
				...tooltipProps,
			},
			value: appVersion ?? '1.0',
		},
	];

	return (
		<div className={classNames('workflow-info-bar', className)}>
			{items.map(
				({label, show, tooltip = {}, value}, index) =>
					show && (
						<div className="info-item" key={index} {...tooltip}>
							<span className="font-weight-bold text-secondary">
								{`${label}: `}
							</span>

							{value}
						</div>
					)
			)}
		</div>
	);
}
