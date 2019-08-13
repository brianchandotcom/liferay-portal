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

import {
	ClayTab,
	ClayTabList,
	ClayTabPanel,
	ClayTabs
} from '../../../../src/main/resources/META-INF/resources/js/components/shared/ClayTabs.es';
import React from 'react';
import {render} from '@testing-library/react';

describe('ClayTabs', () => {
	it('renders', () => {
		const {asFragment} = render(
			<ClayTabs>
				<ClayTabList className="results-ranking-tabs">
					<ClayTab>Tab 1</ClayTab>

					<ClayTab>Tab 2</ClayTab>
				</ClayTabList>

				<ClayTabPanel>Tab Panel 1</ClayTabPanel>
				<ClayTabPanel>Tab Panel 2</ClayTabPanel>
			</ClayTabs>
		);

		expect(asFragment()).toMatchSnapshot();
	});
});
