/* eslint-disable no-unused-vars */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
