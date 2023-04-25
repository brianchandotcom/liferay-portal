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

import ClayAlert, {DisplayType as AlertDisplayType} from '@clayui/alert';
import ClayButton from '@clayui/button';
import {DisplayType as ButtonDisplayType} from '@clayui/button/lib/Button';
import ClayLabel from '@clayui/label';
import {useNavigate} from 'react-router-dom';
import {useObjectPermission} from '~/hooks/data/useObjectPermission';

import i18n from '../../../../i18n';
import {TestrayTask} from '../../../../services/rest';
import {TaskStatuses} from '../../../../util/statuses';

type AlertProperties = {
	[key: string]: {
		color: string;
		displayType: string;
		label: string;
		text: string;
	};
};

type BuildAlertBarProps = {
	testrayTask: TestrayTask;
};

const alertProperties: AlertProperties = {
	[TaskStatuses.ABANDONED]: {
		color: 'label-secondary',
		displayType: 'secondary',
		label: i18n.translate('abandoned'),
		text: i18n.translate('this-builds-task-has-been-abandoned'),
	},
	[TaskStatuses.COMPLETE]: {
		color: 'label-primary',
		displayType: 'primary',
		label: i18n.translate('complete'),
		text: i18n.translate('this-build-has-been-analyzed'),
	},
	[TaskStatuses.IN_ANALYSIS]: {
		color: 'label-chart-in-analysis',
		displayType: 'warning',
		label: i18n.translate('in-analysis'),
		text: i18n.translate('this-build-is-currently-in-analysis'),
	},
	[TaskStatuses.OPEN]: {
		color: 'label-secondary',
		displayType: 'secondary',
		label: i18n.translate('open'),
		text: i18n.translate('this-build-is-currently-in-open'),
	},
	[TaskStatuses.PROCESSING]: {
		color: 'label-info',
		displayType: 'info',
		label: i18n.translate('processing'),
		text: i18n.translate('this-build-is-currently-in-processing'),
	},
};

const BuildAlertBar: React.FC<BuildAlertBarProps> = ({testrayTask}) => {
	const navigate = useNavigate();

	const taskPermission = useObjectPermission('/tasks');

	if (!testrayTask && taskPermission.canCreate) {
		return (
			<ClayButton
				className="mb-4"
				onClick={() => navigate('testflow/create')}
			>
				{i18n.translate('analyze')}
			</ClayButton>
		);
	}

	const alertProperty = alertProperties[testrayTask?.dueStatus?.key];

	if (!alertProperty) {
		return null;
	}

	return (
		<ClayAlert
			actions={
				<ClayButton
					displayType={alertProperty.displayType as ButtonDisplayType}
					onClick={() => navigate(`/testflow/${testrayTask.id}`)}
					outline
					small
				>
					{i18n.translate('view-task')}
				</ClayButton>
			}
			className="build-alert-bar w-100"
			displayType={alertProperty.displayType as AlertDisplayType}
			title={
				((
					<>
						<ClayLabel
							displayType={
								alertProperty.displayType as AlertDisplayType
							}
						>
							{alertProperty.label}
						</ClayLabel>

						{alertProperty.text}
					</>
				) as unknown) as string
			}
			variant="inline"
		/>
	);
};

export default BuildAlertBar;
