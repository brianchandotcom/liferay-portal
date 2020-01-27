/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import '@testing-library/jest-dom/extend-expect';
import {
	cleanup,
	render,
	fireEvent,
	act,
	getByLabelText
} from '@testing-library/react';
import React from 'react';

import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../src/main/resources/META-INF/resources/js/utils/constants';
import LinkConfigurationPanel from '../../../../src/main/resources/META-INF/resources/page_editor/app/components/LinkConfigurationPanel';
import {ConfigContext} from '../../../../src/main/resources/META-INF/resources/page_editor/app/config/index';
import serviceFetch from '../../../../src/main/resources/META-INF/resources/page_editor/app/services/serviceFetch';
import {StoreAPIContextProvider} from '../../../../src/main/resources/META-INF/resources/page_editor/app/store/index';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/services/serviceFetch',
	() => jest.fn(() => {})
);

function getStateWithConfig(config = {}) {
	return {
		fragmentEntryLinks: {
			0: {
				editableValues: {
					[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {
						'editable-id-0': {
							config
						}
					}
				}
			}
		},
		mappedInfoItems: [],
		segmentsExperienceId: 0
	};
}

function renderLinkConfigurationPanel({state = getStateWithConfig()} = {}) {
	return render(
		<ConfigContext.Provider value={{}}>
			<StoreAPIContextProvider dispatch={() => {}} getState={() => state}>
				<LinkConfigurationPanel
					item={{editableId: 'editable-id-0', fragmentEntryLinkId: 0}}
				/>
			</StoreAPIContextProvider>
		</ConfigContext.Provider>,
		{
			baseElement: document.body
		}
	);
}

describe('LinkConfigurationPanel', () => {
	afterEach(() => {
		cleanup();
		serviceFetch.mockReset();
	});

	it('renders manual selection panel', () => {
		const {getByText, queryByText} = renderLinkConfigurationPanel();

		expect(getByText('link')).toBeInTheDocument();
		expect(getByText('url')).toBeInTheDocument();
		expect(getByText('target')).toBeInTheDocument();

		expect(queryByText('content')).not.toBeInTheDocument();
	});

	it('shows mapping panel when changing link source', async () => {
		const {getByLabelText, queryByText} = renderLinkConfigurationPanel();

		const sourceTypeInput = getByLabelText('link');

		await act(async () => {
			fireEvent.change(sourceTypeInput, {
				target: {value: 'fromContentField'}
			});
		});

		expect(getByLabelText('content')).toBeInTheDocument();

		expect(queryByText('url')).not.toBeInTheDocument();
	});

	it('shows the url and target values when previously saved', () => {
		const {getByLabelText} = renderLinkConfigurationPanel({
			state: getStateWithConfig({
				href: 'http://liferay.com',
				target: '_blank'
			})
		});

		expect(getByLabelText('url')).toHaveValue('http://liferay.com');
		expect(getByLabelText('target')).toHaveValue('_blank');
	});

	it('shows mapping panel when editable link is mapped', async () => {
		await act(async () => {
			renderLinkConfigurationPanel({
				state: getStateWithConfig({
					classNameId: 1,
					classPK: 1,
					fieldId: 'text',
					target: '_blank'
				})
			});
		});

		expect(getByLabelText(document.body, 'content')).toBeInTheDocument();
		expect(getByLabelText(document.body, 'target')).toHaveValue('_blank');
	});

	it('shows mapping panel when editable link is mapped', async () => {
		await act(async () => {
			renderLinkConfigurationPanel({
				state: getStateWithConfig({
					classNameId: 1,
					classPK: 1,
					fieldId: 'text',
					target: '_blank'
				})
			});
		});

		expect(getByLabelText(document.body, 'content')).toBeInTheDocument();
		expect(getByLabelText(document.body, 'target')).toHaveValue('_blank');
	});

	it('clear the config when changing source type', async () => {
		await act(async () => {
			renderLinkConfigurationPanel({
				state: getStateWithConfig({
					classNameId: 1,
					classPK: 1,
					fieldId: 'text',
					target: '_blank'
				})
			});
		});

		const sourceTypeInput = getByLabelText(document.body, 'link');

		fireEvent.change(sourceTypeInput, {
			target: {value: 'manual'}
		});

		expect(getByLabelText(document.body, 'url')).toHaveValue('');
		expect(getByLabelText(document.body, 'target')).toHaveValue('_blank');
	});
});
