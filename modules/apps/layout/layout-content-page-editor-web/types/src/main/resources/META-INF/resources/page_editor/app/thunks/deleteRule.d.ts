/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {State} from '../../types/State';
import deleteRuleAction from '../actions/deleteRule';
import updateNetwork from '../actions/updateNetwork';
declare type Props = {
	ruleId: string;
};
export default function deleteRule({
	ruleId,
}: Props): (
	dispatch: (
		action: ReturnType<typeof updateNetwork | typeof deleteRuleAction>
	) => void,
	getState: () => State
) => Promise<void>;
export {};
