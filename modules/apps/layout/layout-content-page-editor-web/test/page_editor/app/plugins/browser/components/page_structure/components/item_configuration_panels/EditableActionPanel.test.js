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
import {render, screen} from '@testing-library/react';
import React from 'react';

import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/editableFragmentEntryProcessor';
import {EDITABLE_TYPES} from '../../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/editableTypes';
import {StoreAPIContextProvider} from '../../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';
import EditableActionPanel from '../../../../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/browser/components/page_structure/components/item_configuration_panels/EditableActionPanel';

jest.mock('frontend-js-web', () => ({
	...jest.requireActual('frontend-js-web'),
	sub: jest.fn((langKey, arg) => langKey.replace('x', arg)),
}));

function getStateWithConfig(config = {}) {
	return {
		fragmentEntryLinks: {
			0: {
				editableValues: {
					[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {
						'editable-id-0': {
							config,
						},
					},
				},
			},
		},
		languageId: 'en_US',
		layoutData: {items: {'fragment-id': {config: {}}}},
		mappingFields: [],
		pageContents: [],
		segmentsExperienceId: 0,
	};
}

function renderActionPanel(
	{state = getStateWithConfig(), type = EDITABLE_TYPES.text} = {},
	dispatch = () => {}
) {
	return render(
		<StoreAPIContextProvider dispatch={dispatch} getState={() => state}>
			<EditableActionPanel
				item={{
					editableId: 'editable-id-0',
					fragmentEntryLinkId: '0',
					itemId: '',
					parentId: 'fragment-id',
					type,
				}}
			/>
		</StoreAPIContextProvider>,
		{
			baseElement: document.body,
		}
	);
}

describe('EditableActionPanel', () => {
	it('does not render interaction selector when no action is selected', () => {
		renderActionPanel();

		expect(
			screen.queryByText('success-interaction')
		).not.toBeInTheDocument();

		expect(screen.queryByText('error-interaction')).not.toBeInTheDocument();
	});

	it('renders interaction and reload selectors when an action is selected', () => {
		renderActionPanel({
			state: getStateWithConfig({
				mappedAction: {title: 'action'},
			}),
		});

		expect(screen.getByText('success-interaction')).toBeInTheDocument();
		expect(
			screen.getByText('reload-page-after-success')
		).toBeInTheDocument();

		expect(screen.getByText('error-interaction')).toBeInTheDocument();
		expect(screen.getByText('reload-page-after-error')).toBeInTheDocument();
	});

	it('renders text and preview selectors when selecting notification', () => {
		renderActionPanel({
			state: getStateWithConfig({
				mappedAction: {title: 'action'},
				onSuccess: {interaction: 'notification'},
			}),
		});

		expect(screen.getByText('success-text')).toBeInTheDocument();
		expect(
			screen.getByText('preview-success-notification')
		).toBeInTheDocument();
	});

	it('renders layout selector and does not allow to reload when selecting Go to page', () => {
		renderActionPanel({
			state: getStateWithConfig({
				mappedAction: {title: 'action'},
				onSuccess: {interaction: 'page'},
			}),
		});

		expect(screen.getByText('success-page')).toBeInTheDocument();
		expect(
			screen.queryByText('reload-page-after-success')
		).not.toBeInTheDocument();
	});

	it('renders url input and does not allow to reload when selecting External URL', () => {
		renderActionPanel({
			state: getStateWithConfig({
				mappedAction: {title: 'action'},
				onSuccess: {interaction: 'url'},
			}),
		});

		expect(screen.getByText('success-external-url')).toBeInTheDocument();
		expect(
			screen.queryByText('reload-page-after-success')
		).not.toBeInTheDocument();
	});
});
