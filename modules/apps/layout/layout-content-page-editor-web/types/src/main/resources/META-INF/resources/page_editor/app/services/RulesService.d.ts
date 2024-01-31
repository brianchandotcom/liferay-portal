/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Action} from '../../plugins/page_rules/components/Action';
import {Condition} from '../../plugins/page_rules/components/Condition';
import {ConditionType} from '../../plugins/page_rules/components/RuleBuilderSection';
import {LayoutData} from '../../types/layout_data/LayoutData';
import {OnNetworkStatus} from './draftServiceFetch';

/**
 * Add a rule
 */
declare type AddRuleProps = {
	actions: Action[];
	conditionType: ConditionType;
	conditions: Condition[];
	name: string;
	onNetworkStatus: OnNetworkStatus;
	segmentsExperienceId: string;
};
declare function addRule({
	actions,
	conditionType,
	conditions,
	name,
	onNetworkStatus,
	segmentsExperienceId,
}: AddRuleProps): Promise<{
	addedRuleId: string;
	layoutData: LayoutData;
}>;

/**
 * Delete a rule
 */
declare type DeleteRuleProps = {
	onNetworkStatus: OnNetworkStatus;
	ruleId: string;
	segmentsExperienceId: string;
};
declare function deleteRule({
	onNetworkStatus,
	ruleId,
	segmentsExperienceId,
}: DeleteRuleProps): Promise<{
	layoutData: LayoutData;
}>;

/**
 * Get roles
 */
declare function getRoles(): Promise<
	Array<{
		name: string;
		roleId: string;
	}>
>;

/**
 * Get users
 */
declare function getUsers(): Promise<
	Array<{
		screenName: string;
		userId: string;
	}>
>;

/**
 * Update a rule with new name, actions and conditions
 */
declare type UpdateRuleProps = {
	actions: Action[];
	conditionType: ConditionType;
	conditions: Condition[];
	name: string;
	onNetworkStatus: OnNetworkStatus;
	ruleId: string;
	segmentsExperienceId: string;
};
declare function updateRule({
	actions,
	conditionType,
	conditions,
	name,
	onNetworkStatus,
	ruleId,
	segmentsExperienceId,
}: UpdateRuleProps): Promise<{
	layoutData: LayoutData;
}>;
declare const _default: {
	addRule: typeof addRule;
	deleteRule: typeof deleteRule;
	getRoles: typeof getRoles;
	getUsers: typeof getUsers;
	updateRule: typeof updateRule;
};
export default _default;
