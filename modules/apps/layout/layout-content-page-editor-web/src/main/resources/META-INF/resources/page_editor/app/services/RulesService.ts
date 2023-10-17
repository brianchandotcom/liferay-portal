/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Action} from '../../plugins/page_rules/components/Action';
import {Condition} from '../../plugins/page_rules/components/Condition';
import {LayoutData} from '../../types/layout_data/LayoutData';
import updateNetwork from '../actions/updateNetwork';
import {config} from '../config/index';
import draftServiceFetch from './draftServiceFetch';
import serviceFetch from './serviceFetch';

/**
 * Add a rule
 */
type AddRuleProps = {
	actions: Action[];
	conditions: Condition[];
	name: string;
	onNetworkStatus: (action: ReturnType<typeof updateNetwork>) => void;
	segmentsExperienceId: string;
};

function addRule({
	actions,
	conditions,
	name,
	onNetworkStatus,
	segmentsExperienceId,
}: AddRuleProps): Promise<{addedRuleId: string; layoutData: LayoutData}> {
	return draftServiceFetch(
		config.addRuleURL,
		{
			body: {
				actions: JSON.stringify(actions),
				conditions: JSON.stringify(conditions),
				name,
				segmentsExperienceId,
			},
		},
		onNetworkStatus
	);
}

/**
 * Delete a rule
 */
type DeleteRuleProps = {
	onNetworkStatus: (action: ReturnType<typeof updateNetwork>) => void;
	ruleId: string;
	segmentsExperienceId: string;
};

function deleteRule({
	onNetworkStatus,
	ruleId,
	segmentsExperienceId,
}: DeleteRuleProps): Promise<{layoutData: LayoutData}> {
	return draftServiceFetch(
		config.deleteRuleURL,
		{
			body: {
				ruleId,
				segmentsExperienceId,
			},
		},
		onNetworkStatus
	);
}

/**
 * Get users
 */
function getUsers(): Promise<Array<{screenName: string; userId: string}>> {
	return serviceFetch(config.getUsersURL, {});
}

/**
 * Update a rule with new name, actions and conditions
 */
type UpdateRuleProps = {
	actions: Action[];
	conditions: Condition[];
	name: string;
	onNetworkStatus: (action: ReturnType<typeof updateNetwork>) => void;
	ruleId: string;
	segmentsExperienceId: string;
};

function updateRule({
	actions,
	conditions,
	name,
	onNetworkStatus,
	ruleId,
	segmentsExperienceId,
}: UpdateRuleProps): Promise<{layoutData: LayoutData}> {
	return draftServiceFetch(
		config.updateRuleURL,
		{
			body: {
				actions: JSON.stringify(actions),
				conditions: JSON.stringify(conditions),
				name,
				ruleId,
				segmentsExperienceId,
			},
		},
		onNetworkStatus
	);
}

export default {addRule, deleteRule, getUsers, updateRule};
