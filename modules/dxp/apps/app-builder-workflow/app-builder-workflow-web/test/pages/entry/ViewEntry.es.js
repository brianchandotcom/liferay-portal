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

import '@testing-library/jest-dom/extend-expect';
import {waitForElementToBeRemoved} from '@testing-library/dom';
import {render} from '@testing-library/react';
import React from 'react';

import ViewEntry from '../../../src/main/resources/META-INF/resources/js/pages/entry/ViewEntry.es';
import AppContextProviderWrapper from '../../AppContextProviderWrapper.es';
import PermissionsContextProviderWrapper from '../../PermissionsContextProviderWrapper.es';
import {ENTRY} from '../../constants.es';

const context = {
	appId: 1,
	dataDefinitionId: 1,
	dataLayoutId: 1,
	showFormView: true,
};

const instances = {
	items: [
		{
			assignees: [{name: 'User'}],
			classPK: 0,
			completed: false,
			taskNames: ['Review'],
		},
	],
	totalCount: 4,
};

describe('ViewEntry', () => {
	it('renders with workflow info', async () => {
		fetch.mockResponseOnce(JSON.stringify(ENTRY.APP_WORKFLOW));
		fetch.mockResponseOnce(JSON.stringify(ENTRY.DATA_DEFINITION));
		fetch.mockResponseOnce(JSON.stringify(ENTRY.DATA_LAYOUT));
		fetch.mockResponseOnce(JSON.stringify(ENTRY.DATA_RECORDS(1)));
		fetch.mockResponseOnce(JSON.stringify(instances));

		const {container} = render(
			<AppContextProviderWrapper appContext={context}>
				<ViewEntry match={{params: {entryIndex: 1}}} />
			</AppContextProviderWrapper>,
			{wrapper: PermissionsContextProviderWrapper}
		);

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);

		expect(
			container.querySelector('.data-record-field-preview')
		).toHaveTextContent('Name Test 0');

		const infoItems = container.querySelectorAll('.info-item');

		expect(infoItems.length).toBe(3);
		expect(infoItems[0]).toHaveTextContent('status: pending');
		expect(infoItems[1]).toHaveTextContent('step: Review');
		expect(infoItems[2]).toHaveTextContent('assignee: User');
	});
});
