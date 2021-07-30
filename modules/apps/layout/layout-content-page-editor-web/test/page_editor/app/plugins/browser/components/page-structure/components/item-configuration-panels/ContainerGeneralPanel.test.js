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
import {cleanup, fireEvent, render} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutDataItemTypes';
import {StoreAPIContextProvider} from '../../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';
import updateItemConfig from '../../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateItemConfig';
import ContainerGeneralPanel from '../../../../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/browser/components/page-structure/components/item-configuration-panels/ContainerGeneralPanel';

jest.mock(
	'../../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config',
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
	'../../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateItemConfig',
	() => jest.fn()
);

function renderLinkPanel(itemConfiguration = {}) {
	const state = {
		languageId: 'en_US',
		segmentsExperienceId: '0',
	};

	return render(
		<StoreAPIContextProvider dispatch={() => {}} getState={() => state}>
			<ContainerGeneralPanel
				item={{
					children: [],
					config: itemConfiguration,
					itemId: 'some-container-id',
					type: LAYOUT_DATA_ITEM_TYPES.container,
				}}
			/>
		</StoreAPIContextProvider>,
		{
			baseElement: document.body,
		}
	);
}

describe('ContainerGeneralPanel', () => {
	afterEach(() => {
		cleanup();
	});

	it('opens link in same tab by default', () => {
		const {getByLabelText} = renderLinkPanel();

		expect(getByLabelText('open-in-a-new-tab')).not.toBeChecked();
	});

	it('renders existing target', () => {
		const {getByLabelText} = renderLinkPanel({link: {target: '_blank'}});

		expect(getByLabelText('open-in-a-new-tab')).toBeChecked();
	});

	it('renders exiting href', () => {
		const {getByLabelText} = renderLinkPanel({
			link: {href: {en_US: 'https://liferay.us'}},
		});

		expect(getByLabelText('url')).toHaveValue('https://liferay.us');
	});

	it('stores link target', () => {
		const {getByLabelText} = renderLinkPanel();
		const targetInput = getByLabelText('open-in-a-new-tab');

		userEvent.click(targetInput);

		expect(updateItemConfig).toHaveBeenCalledWith({
			itemConfig: {
				link: {
					href: {en_US: ''},
					target: '_blank',
				},
			},
			itemId: 'some-container-id',
			segmentsExperienceId: '0',
		});
	});

	it('stores link href as localizable', () => {
		const {getByLabelText} = renderLinkPanel();
		const urlInput = getByLabelText('url');

		userEvent.type(urlInput, 'https://liferay.us');
		fireEvent.blur(urlInput);

		expect(updateItemConfig).toHaveBeenCalledWith({
			itemConfig: {
				link: {
					href: {en_US: 'https://liferay.us'},
					target: '',
				},
			},
			itemId: 'some-container-id',
			segmentsExperienceId: '0',
		});
	});
});
