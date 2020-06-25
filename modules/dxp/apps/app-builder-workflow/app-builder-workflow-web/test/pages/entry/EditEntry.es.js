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
import {cleanup, fireEvent, render} from '@testing-library/react';
import * as toast from 'app-builder-web/js/utils/toast.es';
import React from 'react';

import EditEntry from '../../../src/main/resources/META-INF/resources/js/pages/entry/EditEntry.es';
import AppContextProviderWrapper from '../../AppContextProviderWrapper.es';
import PermissionsContextProviderWrapper from '../../PermissionsContextProviderWrapper.es';

const context = {
	addEntryURL: 'http://liferay.com/',
	appId: 1,
	basePortletURL: 'portlet_url',
	namespace: 'app_builder',
};

const mockNavigate = jest.fn();
const mockSubmit = jest.fn();
const mockToast = jest.fn();

jest.mock('app-builder-web/js/hooks/withDDMForm.es', () => ({
	__esModule: true,
	default: jest.fn().mockImplementation((component) => component),
	useDDMFormSubmit: jest.fn(),
	useDDMFormValidation: jest.fn().mockImplementation((_, submit) => {
		return () => {
			mockSubmit();
			submit();
		};
	}),
}));

window.Liferay.Util = {
	navigate: (url) => mockNavigate(url),
	ns: jest.fn(),
};

describe('EditEntry', () => {
	afterEach(cleanup);

	beforeAll(() => {
		jest.spyOn(toast, 'successToast').mockImplementation((message) =>
			mockToast(message)
		);
	});

	it('renders on create mode', async () => {
		fetch.mockResponse();

		const {container, queryAllByRole} = render(
			<AppContextProviderWrapper appContext={context}>
				<EditEntry dataRecordId="0" />
			</AppContextProviderWrapper>,
			{wrapper: PermissionsContextProviderWrapper}
		);

		const buttons = queryAllByRole('button');

		expect(
			container.querySelector('.control-menu-level-1-heading')
		).toHaveTextContent('add-entry');

		expect(buttons.length).toBe(2);
		expect(buttons[0]).toHaveTextContent('submit');
		expect(buttons[1]).toHaveTextContent('cancel');

		await fireEvent.click(buttons[0]);

		expect(mockSubmit.mock.calls.length).toBe(1);
		expect(mockToast).toHaveBeenCalledWith('an-entry-was-added');
		expect(mockNavigate).toHaveBeenCalledWith(context.basePortletURL);
	});

	it('renders on edit mode', async () => {
		fetch.mockResponse();

		const {container, queryAllByRole} = render(
			<AppContextProviderWrapper appContext={context}>
				<EditEntry dataRecordId="1" redirect="/home" />
			</AppContextProviderWrapper>,
			{wrapper: PermissionsContextProviderWrapper}
		);

		const buttons = queryAllByRole('button');

		expect(
			container.querySelector('.control-menu-level-1-heading')
		).toHaveTextContent('edit-entry');

		expect(buttons.length).toBe(2);
		expect(buttons[0]).toHaveTextContent('submit');
		expect(buttons[1]).toHaveTextContent('cancel');

		await fireEvent.click(buttons[0]);

		expect(mockSubmit.mock.calls.length).toBe(2);
	});
});
