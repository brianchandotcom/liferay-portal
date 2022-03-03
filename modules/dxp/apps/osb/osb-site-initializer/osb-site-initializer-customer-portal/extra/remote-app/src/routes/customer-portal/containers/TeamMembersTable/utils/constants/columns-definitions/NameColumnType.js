/* eslint-disable no-unused-vars */
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

import {Liferay} from '../../../../../../../common/services/liferay';
import {getMd5Hash} from '../../../../../utils/getMd5Hash';

const GRAVATAR_URL = 'https://www.gravatar.com/avatar';
const AVATAR_SIZE_IN_PX = 40;

const Avatar = ({emailAddress}) => {
	return (
		<div className="cp-team-members-avatar mr-2">
			<img
				height={AVATAR_SIZE_IN_PX}
				src={`${GRAVATAR_URL}/${getMd5Hash(emailAddress)}`}
				width={AVATAR_SIZE_IN_PX}
			/>
		</div>
	);
};

const NameColumnType = ({userAccount}) => {
	const currentLoggedUserId = Number(Liferay.ThemeDisplay.getUserId());

	return (
		<div className="align-items-center d-flex">
			<Avatar emailAddress={userAccount.emailAddress} />

			<p className="m-0 text-truncate">{userAccount.name}</p>

			{userAccount.id === currentLoggedUserId && (
				<span className="ml-1 text-neutral-7">(me)</span>
			)}
		</div>
	);
};

export {NameColumnType};
