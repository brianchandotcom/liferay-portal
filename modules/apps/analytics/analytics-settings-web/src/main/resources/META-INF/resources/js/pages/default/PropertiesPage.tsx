/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import BasePage from '../../components/BasePage';
import Properties from '../../components/properties/Properties';
import {IGenericPageProps} from './DefaultPage';

const PropertiesPage: React.FC<IGenericPageProps> = ({title}) => (
	<BasePage
		description={Liferay.Language.get('property-description')}
		title={title}
	>
		<Properties />
	</BasePage>
);

export default PropertiesPage;
