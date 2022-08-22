/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {useModal} from '@clayui/core';
import {useCustomerPortal} from '../../../../context';
import {actionTypes} from '../../../../context/reducer';
import {STATUS_TAG_TYPE_NAMES} from '../../../../utils/constants';

export default function useOnCloseSetupModal(
	dataSubscriptionGroups,
	handleOncloseSetupModal,
	setStatusLxcActivation
) {
	const [, dispatch] = useCustomerPortal();
	const {observer, onClose} = useModal({
		onClose: () => handleOncloseSetupModal(),
	});

	const handleSubmitLxcEnvironment = (isSuccess) => {
		onClose();
		if (isSuccess && dataSubscriptionGroups) {
			const items =
				dataSubscriptionGroups?.c?.accountSubscriptionGroups?.items;
			dispatch({
				payload: items,
				type: actionTypes.UPDATE_SUBSCRIPTION_GROUPS,
			});

			setStatusLxcActivation(STATUS_TAG_TYPE_NAMES.inProgress);
		}
	};

	return {handleSubmitLxcEnvironment, observer};
}
