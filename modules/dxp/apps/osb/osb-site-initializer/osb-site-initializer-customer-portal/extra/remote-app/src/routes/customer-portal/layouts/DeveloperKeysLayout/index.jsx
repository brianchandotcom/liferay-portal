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
import i18n from '../../../../common/I18n';
import DeveloperKeysInputs from './Inputs';

const DeveloperKeysLayouts = ({children}) => {
	return (
		<div>
			<h4 className="m-0 py-3">{i18n.translate('developer-keys')}</h4>

			{children}
		</div>
	);
};

DeveloperKeysLayouts.Inputs = DeveloperKeysInputs;

export default DeveloperKeysLayouts;
