/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import EdgeInformation from './EdgeInformation';
import NodeInformation from './NodeInformation';
import Actions from './actions/Actions';
import ActionsSummary from './actions/ActionsSummary';
import Assignments from './assignments/Assignments';
import AssignmentsSummary from './assignments/AssignmentsSummary';
import SourceCode from './assignments/SourceCode';
import Notifications from './notifications/Notifications';
import NotificationsSummary from './notifications/NotificationsSummary';
import TimerSourceCode from './timers/TimerSourceCode';
import Timers from './timers/Timers';
import TimersSummary from './timers/TimersSummary';

const sectionComponents = {
	actions: Actions,
	actionsSummary: ActionsSummary,
	assignments: Assignments,
	assignmentsSummary: AssignmentsSummary,
	edgeInformation: EdgeInformation,
	nodeInformation: NodeInformation,
	notifications: Notifications,
	notificationsSummary: NotificationsSummary,
	sourceCode: SourceCode,
	timerSourceCode: TimerSourceCode,
	timers: Timers,
	timersSummary: TimersSummary,
};

export default sectionComponents;
