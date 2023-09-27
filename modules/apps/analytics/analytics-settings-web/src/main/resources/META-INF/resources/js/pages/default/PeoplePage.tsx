/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import BasePage from '../../components/BasePage';
import People from '../../components/people/People';
import {IGenericPageProps} from './DefaultPage';

const PeoplePage: React.FC<IGenericPageProps> = ({title}) => (
	<BasePage
		description={Liferay.Language.get('sync-people-description')}
		title={title}
	>
		<People />
	</BasePage>
);

export default PeoplePage;
