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

import React from 'react';

import {getLocalizedText} from '../utils/utils';
import Icon from './Icon';

function NoMembers(props) {
	return (
		<div className="no-members">
			<p>
				<Icon spritemap={props.spritemap} symbol="close" />
			</p>
			<p>{getLocalizedText('no-members-found')}</p>
		</div>
	);
}

export default NoMembers;
