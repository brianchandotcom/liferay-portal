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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import {useNavigate} from 'react-router-dom';

import {TestrayTask} from '../../../../graphql/queries/testrayTask';
import i18n from '../../../../i18n';
import {SUBTASK_STATUS, SUB_TASK_STATUS} from '../../../../util/constants';

type BuildAlertBarProps = {
	testrayTask: TestrayTask;
};

const alertProperties = {
	[SUB_TASK_STATUS.IN_ANALYSIS]: {
		...SUBTASK_STATUS[SUB_TASK_STATUS.IN_ANALYSIS],
		text: i18n.translate('this-build-is-currently-in-analysis'),
	},
	[SUB_TASK_STATUS.ABANDONED]: {
		...SUBTASK_STATUS[SUB_TASK_STATUS.ABANDONED],
		text: i18n.translate('this-builds-task-has-been-abandoned'),
	},
	[SUB_TASK_STATUS.COMPLETE]: {
		...SUBTASK_STATUS[SUB_TASK_STATUS.COMPLETE],
		text: i18n.translate('this-build-has-been-analyzed'),
	},
};

const BuildAlertBar: React.FC<BuildAlertBarProps> = ({testrayTask}) => {
	const navigate = useNavigate();

	const alertProperty = (alertProperties as any)[testrayTask.dueStatus];

	if (!alertProperty) {
		return null;
	}

	return (
		<ClayAlert
			actions={
				<ClayButton.Group>
					<ClayButton
						alert
						onClick={() => navigate(`/testflow/${testrayTask.id}`)}
					>
						{i18n.translate('view-task')}
					</ClayButton>
				</ClayButton.Group>
			}
			className="w-100"
			displayType={alertProperty.color.replace('label-', '')}
			title={alertProperty.text}
			variant="inline"
		/>
	);
};

export default BuildAlertBar;
