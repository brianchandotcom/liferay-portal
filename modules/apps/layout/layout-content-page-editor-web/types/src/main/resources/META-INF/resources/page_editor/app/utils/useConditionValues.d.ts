/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Condition} from '../../plugins/page_rules/components/Condition';
import {ConditionType} from '../../plugins/page_rules/components/RuleBuilderSection';
declare type Props = {
	conditionType?: ConditionType;
	conditions: Condition[];
};
export default function useConditionValues({
	conditionType,
	conditions,
}: Props): {
	condition: string | undefined;
	description: string;
	id: string;
	prefix: string;
	type: string | undefined;
	value: string | undefined;
}[];
export {};
