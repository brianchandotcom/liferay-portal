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

import {ACTIVATION_STATUS} from '..';
import ClayIcon from '@clayui/icon';
import ClaySticker from '@clayui/sticker';

const StatusColumn = ({activationKey}) => {
	let activationStatus = ACTIVATION_STATUS.active;

	if (new Date() < new Date(activationKey.startDate)) {
		activationStatus = ACTIVATION_STATUS.notActivated;
	}
	else if (new Date() > new Date(activationKey.expirationDate)) {
		activationStatus = ACTIVATION_STATUS.expired;
	}

	return (
		<div className="w-100" title={[activationStatus.title]}>
			<ClaySticker
				className="bg-transparent"
				displayType={activationStatus.color}
				shape="circle"
				size="sm"
			>
				<ClayIcon symbol="circle" />
			</ClaySticker>
		</div>
	);
};

export {StatusColumn};
