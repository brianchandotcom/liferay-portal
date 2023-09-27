/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import NoActionRequired from '../../../../../common/components/action-detail/action-content/no-action-required';
import {CONSTANTS} from '../../../../../common/utils/constants';
import {ClaimStatusType} from '../Types';

import './index.scss';
import NotifyRepairComponent from './notify-repair-component';
import RequestAdditionalInformation from './request-additional-info';

const ClaimActionComponent = ({claimStatus}: ClaimStatusType) => {
	if (claimStatus === CONSTANTS.CLAIM_STATUS['inInvestigation'].NAME) {
		return <RequestAdditionalInformation />;
	}

	if (claimStatus === CONSTANTS.CLAIM_STATUS['approved'].NAME) {
		return <NotifyRepairComponent />;
	}

	return <NoActionRequired />;
};

export default ClaimActionComponent;
