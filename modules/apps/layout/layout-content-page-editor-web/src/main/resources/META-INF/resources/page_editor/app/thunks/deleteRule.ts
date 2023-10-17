/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {State} from '../../types/State';
import deleteRuleAction from '../actions/deleteRule';
import updateNetwork from '../actions/updateNetwork';
import RulesService from '../services/RulesService';

type Props = {
	ruleId: string;
};

export default function deleteRule({ruleId}: Props) {
	return (
		dispatch: (
			action: ReturnType<typeof updateNetwork | typeof deleteRuleAction>
		) => void,
		getState: () => State
	) => {
		const {segmentsExperienceId} = getState();

		return RulesService.deleteRule({
			onNetworkStatus: dispatch,
			ruleId,
			segmentsExperienceId,
		}).then(({layoutData}) => {
			dispatch(deleteRuleAction({layoutData, ruleId}));
		});
	};
}
