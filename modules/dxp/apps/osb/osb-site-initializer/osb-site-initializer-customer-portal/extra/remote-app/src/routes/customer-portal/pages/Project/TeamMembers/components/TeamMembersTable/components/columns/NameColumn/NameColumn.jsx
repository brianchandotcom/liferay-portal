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

import {memo} from 'react';
import i18n from '../../../../../../../../../../common/I18n';
import Avatar from './components/Avatar/Avatar';

const NameColumn = ({gravatarAPI, userAccount}) => (
	<div className="align-items-center d-flex">
		<Avatar
			emailAddress={userAccount.emailAddress}
			gravatarAPI={gravatarAPI}
			userName={userAccount.name}
		/>

		<p className="m-0 ml-2 mr-1 text-truncate">{userAccount.name}</p>

		{userAccount.isLoggedUser && (
			<span className="text-neutral-7">({i18n.translate('me')})</span>
		)}
	</div>
);

export default memo(NameColumn);
