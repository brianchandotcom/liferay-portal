/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayEmptyState from '@clayui/empty-state';
import React from 'react';

import {config} from '../../app/config/index';

export default function MultiSelectMessage() {
	return (
		<ClayEmptyState
			className="mt-6 mx-3"
			description=""
			imgSrc={`${config.imagesPath}/multiselection.svg`}
			imgSrcReducedMotion={null}
			small
			title={Liferay.Language.get('multiple-page-elements-selected')}
		/>
	);
}
