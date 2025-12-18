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
import PromptSummary from './prompt/PromptSummary';
import RAGSummary from './rag/RAGSummary';
import TimerSourceCode from './timers/TimerSourceCode';
import Timers from './timers/Timers';
import TimersSummary from './timers/TimersSummary';
import ToolsSummary from './tools/ToolsSummary';

const sectionComponents = {
	actions: Actions,
	actionsSummary: ActionsSummary,
	assignments: Assignments,
	assignmentsSummary: AssignmentsSummary,
	edgeInformation: EdgeInformation,
	nodeInformation: NodeInformation,
	notifications: Notifications,
	notificationsSummary: NotificationsSummary,
	promptSummary: PromptSummary,
	ragSummary: RAGSummary,
	sourceCode: SourceCode,
	timerSourceCode: TimerSourceCode,
	timers: Timers,
	timersSummary: TimersSummary,
	toolsSummary: ToolsSummary,
};

export default sectionComponents;
