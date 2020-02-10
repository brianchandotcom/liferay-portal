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
	act,
	cleanup,
	fireEvent,
	getByLabelText,
	render
} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../src/main/resources/META-INF/resources/js/utils/constants';
import LinkPanel from '../../../../src/main/resources/META-INF/resources/page_editor/app/components/floating-toolbar/LinkPanel';
import {ConfigContext} from '../../../../src/main/resources/META-INF/resources/page_editor/app/config/index';
import serviceFetch from '../../../../src/main/resources/META-INF/resources/page_editor/app/services/serviceFetch';
import {StoreAPIContextProvider} from '../../../../src/main/resources/META-INF/resources/page_editor/app/store/index';
import updateEditableValues from '../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateEditableValues';

jest.useFakeTimers();

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/services/serviceFetch',
	() => jest.fn(() => Promise.resolve({fieldValue: 'fieldValue'}))
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateEditableValues',
	() => jest.fn()
);

const getEditableConfig = editableValues => {
	return editableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]['editable-id-0']
		.config;
};

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

function renderLinkPanel(
	{state = getStateWithConfig()} = {},
	dispatch = () => {}
) {
	return render(
		<ConfigContext.Provider value={{}}>
			<StoreAPIContextProvider dispatch={dispatch} getState={() => state}>
				<LinkPanel
					item={{editableId: 'editable-id-0', fragmentEntryLinkId: 0}}
				/>
			</StoreAPIContextProvider>
		</ConfigContext.Provider>,
		{
			baseElement: document.body
		}
	);
}

describe('LinkPanel', () => {
	afterEach(() => {
		cleanup();

		serviceFetch.mockClear();
		updateEditableValues.mockClear();
	});

	it('renders manual selection panel', () => {
		const {getByText, queryByText} = renderLinkPanel();

		expect(getByText('link')).toBeInTheDocument();
		expect(getByText('url')).toBeInTheDocument();
		expect(getByText('target')).toBeInTheDocument();

		expect(queryByText('content')).not.toBeInTheDocument();
	});

	it('shows mapping panel when changing link source', async () => {
		const {getByLabelText, queryByText} = renderLinkPanel();

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
		const {getByLabelText} = renderLinkPanel({
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
			renderLinkPanel({
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
			renderLinkPanel({
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

	it('shows mapped url when is available', async () => {
		serviceFetch.mockImplementation(() =>
			Promise.resolve({fieldValue: 'value'})
		);

		await act(async () => {
			renderLinkPanel({
				state: getStateWithConfig({
					classNameId: 1,
					classPK: 1,
					fieldId: 'text',
					target: '_blank'
				})
			});
		});

		expect(getByLabelText(document.body, 'content')).toBeInTheDocument();
		expect(getByLabelText(document.body, 'url')).toBeInTheDocument();
		expect(getByLabelText(document.body, 'url')).toHaveValue('value');
		expect(getByLabelText(document.body, 'target')).toHaveValue('_blank');
	});

	it('clear the config when changing source type', async () => {
		let editableConfig;
		updateEditableValues.mockImplementation(({editableValues}) => {
			editableConfig = getEditableConfig(editableValues);
		});

		await act(async () => {
			renderLinkPanel({
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

		expect(editableConfig).toEqual({});

		expect(getByLabelText(document.body, 'url')).toHaveValue('');
		expect(getByLabelText(document.body, 'target')).toHaveValue('_blank');
	});

	it('calls dispatch with the href value typed in manual mode', async () => {
		let editableConfig;
		updateEditableValues.mockImplementation(({editableValues}) => {
			editableConfig = getEditableConfig(editableValues);
		});

		const {getByLabelText} = renderLinkPanel({
			state: getStateWithConfig({})
		});

		const hrefInput = getByLabelText('url');

		userEvent.type(hrefInput, 'http://google.com');

		jest.runAllTimers();

		expect(updateEditableValues).toHaveBeenCalled();

		expect(editableConfig).toEqual({href: 'http://google.com'});
	});
});
