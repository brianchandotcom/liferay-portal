/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isNode} from 'react-flow-renderer';

export function detectGroovyScript(elements, setHasGroovyScript) {
	const hasGroovyScript = elements.find((element) => {
		if (isNode(element)) {
			const {data} = element;

			if (
				hasScriptActionsOrAssignments(data) ||
				hasScriptTimerActionOrReassignments(element) ||
				isConditionNode(element)
			) {
				return true;
			}

			return false;
		}
	});

	setHasGroovyScript(hasGroovyScript);

	return hasGroovyScript;
}

function isConditionNode(element) {
	return element.type === 'condition';
}

function hasScriptActionsOrAssignments(data) {
	if (data.actions?.scriptLanguage) {
		return (
			data.actions?.scriptLanguage.includes('groovy') ||
			data.actions?.scriptLanguage.includes('java')
		);
	}

	if (data.assignments?.scriptLanguage) {
		return (
			data.assignments.scriptLanguage.includes('groovy') ||
			data.assignments.scriptLanguage.includes('java')
		);
	}
}

function hasScriptTimerActionOrReassignments(element) {
	if (element.type === 'task' && element.data.taskTimers) {
		const {reassignments, timerActions} = element.data.taskTimers;

		const hasScriptReassignments = !!reassignments.find((reassignment) => {
			if (reassignment.scriptLanguage) {
				return reassignment.scriptLanguage === 'groovy';
			}
		});

		const hasScriptTimerActions = !!timerActions.find((timerAction) => {
			if (timerAction?.scriptLanguage) {
				return (
					timerAction?.scriptLanguage.includes('groovy') ||
					timerAction?.scriptLanguage.includes('java')
				);
			}
		});

		return hasScriptTimerActions || hasScriptReassignments;
	}

	return false;
}
