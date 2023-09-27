/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
