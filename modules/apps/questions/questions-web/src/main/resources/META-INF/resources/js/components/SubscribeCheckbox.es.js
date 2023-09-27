/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox} from '@clayui/form';
import React from 'react';

const SubscritionCheckbox = ({checked = false, setChecked}) => (
	<ClayCheckbox
		checked={checked}
		label={Liferay.Language.get('i-want-to-subscribe-to-this-question')}
		onChange={() => setChecked(!checked)}
	/>
);

export default SubscritionCheckbox;
