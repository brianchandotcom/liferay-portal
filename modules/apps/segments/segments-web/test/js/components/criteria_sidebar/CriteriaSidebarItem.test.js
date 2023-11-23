/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import CriteriaSidebarItem from '../../../../src/main/resources/META-INF/resources/js/components/criteria_sidebar/CriteriaSidebarItem';

describe('CriteriaSidebarItem', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {asFragment} = render(
			<DndProvider backend={HTML5Backend}>
				<CriteriaSidebarItem propertyKey="user" />
			</DndProvider>
		);

		expect(asFragment()).toMatchSnapshot();
	});
});
