/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {fireEvent, render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/editableFragmentEntryProcessor';
import {StoreAPIContextProvider} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';
import {MappingPanel} from '../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/browser/components/page_structure/components/item_configuration_panels/MappingPanel';

const dateEditableItem = {
	editableId: 'date-time-editable-id',
	editableValueNamespace: EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
	fragmentEntryLinkId: 'fragmentEntryLinkId',
	itemId: '0',
	type: 'date-time',
};

const state = {
	fragmentEntryLinks: {
		fragmentEntryLinkId: {
			editableValues: {
				[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {
					'date-time-editable-id': {
						config: {},
					},
				},
			},
		},
	},
	languageId: 'en_US',
	mappingFields: {},
	pageContents: [],
	permissions: {},
	selectPageContents: {},
};

jest.mock(
	'../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/index',
	() => ({
		config: {
			availableLanguages: {
				en_US: {
					default: false,
					displayName: 'English (United States)',
					languageIcon: 'en-us',
					languageId: 'en_US',
					w3cLanguageId: 'en-US',
				},
			},
		},
	})
);

jest.mock(
	'../../../../../../src/main/resources/META-INF/resources/page_editor/app/services/serviceFetch',
	() => jest.fn(() => Promise.resolve())
);

jest.mock(
	'../../../../../../src/main/resources/META-INF/resources/page_editor/app/selectors/selectEditableValue',
	() =>
		jest.fn(() => ({
			classNameId: 'InfoItemClassNameId',
			classPK: 'infoItemClassPK',
			fieldId: 'infoItemFieldId',
		}))
);

function renderMappingPanel(item) {
	render(
		<StoreAPIContextProvider dispatch={() => {}} getState={() => state}>
			<MappingPanel item={item} />
		</StoreAPIContextProvider>
	);
}

describe('MappingPanel', () => {
	it('displays date format dropdown when type is date-time', () => {
		renderMappingPanel(dateEditableItem);

		expect(screen.getByLabelText('date-format')).toBeInTheDocument();
	});

	it('displays custom date format input when custom is selected in dropdown', () => {
		renderMappingPanel(dateEditableItem);

		const dateFormatSelect = screen.getByLabelText('date-format');

		userEvent.selectOptions(dateFormatSelect, 'custom');
		fireEvent.change(dateFormatSelect);

		expect(screen.getByLabelText('custom-format')).toBeInTheDocument();
	});

	it('Does not show date format dropdown when type is different than date-time', () => {
		renderMappingPanel({
			editableId: 'element-text',
			editableValueNamespace: EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
			fragmentEntryLinkId: 'fragmentEntryLinkId',
			itemId: '0',
			type: 'rich-text',
		});

		expect(screen.queryByText('date-format')).not.toBeInTheDocument();
	});

	it('Does not show custom date format input when custom is not selected in dropdown', () => {
		renderMappingPanel(dateEditableItem);

		const dateFormatSelect = screen.getByLabelText('date-format');

		userEvent.selectOptions(dateFormatSelect, 'dd/MM/yyyy');
		fireEvent.change(dateFormatSelect);

		expect(screen.queryByText('custom-format')).not.toBeInTheDocument();
	});
});
