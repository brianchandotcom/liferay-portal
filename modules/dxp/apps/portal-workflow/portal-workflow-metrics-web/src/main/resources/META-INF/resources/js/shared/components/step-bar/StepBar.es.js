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

import {sub} from '../../util/lang.es';

const StepBar = ({current, title, total}) => {
	return (
		<div className="step-of-bar">
			<small>{title}</small>
			<small>
				{sub(Liferay.Language.get('step-x-of-x'), [current, total])}
			</small>
		</div>
	);
};

export default StepBar;
