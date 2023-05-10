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

import ClayIcon from '@clayui/icon';
import ClaySticker from '@clayui/sticker';
import {getStatusActivationTag} from '../../index';

const StatusColumn = ({activationKey}) => {
	return (
		<div
			className="w-100"
			title={[getStatusActivationTag(activationKey)?.title]}
		>
			<ClaySticker
				className="bg-transparent"
				displayType={getStatusActivationTag(activationKey)?.color}
				shape="circle"
				size="sm"
			>
				<ClayIcon symbol="circle" />
			</ClaySticker>
		</div>
	);
};

export {StatusColumn};
