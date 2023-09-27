/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React, {useEffect} from 'react';

import {LAYOUT_TYPES} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutTypes';
import {config} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/config/index';
import {ControlsProvider} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ControlsContext';
import {
	DisplayPagePreviewItemContextProvider,
	useSelectDisplayPagePreviewItem,
} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/DisplayPagePreviewItemContext';
import usePreviewURL from '../../../../../src/main/resources/META-INF/resources/page_editor/app/hooks/app_hooks/usePreviewURL';
import StoreMother from '../../../../../src/main/resources/META-INF/resources/page_editor/test_utils/StoreMother';

const HookComponent = ({displayPagePreviewItem}) => {
	usePreviewURL();

	const selectDisplayPagePreviewItem = useSelectDisplayPagePreviewItem();

	useEffect(() => {
		if (displayPagePreviewItem) {
			selectDisplayPagePreviewItem(displayPagePreviewItem);
		}
	}, [displayPagePreviewItem, selectDisplayPagePreviewItem]);

	return null;
};

const renderComponent = ({displayPagePreviewItem = null} = {}) =>
	render(
		<StoreMother.Component>
			<ControlsProvider>
				<DisplayPagePreviewItemContextProvider>
					<HookComponent
						displayPagePreviewItem={displayPagePreviewItem}
					/>
				</DisplayPagePreviewItemContextProvider>
			</ControlsProvider>
		</StoreMother.Component>
	);

const renderSampleLink = () => {
	const link = document.createElement('a');

	link.href = 'http://test.com';
	link.dataset.pageEditorLayoutPreviewBaseUrl = link.href;

	document.body.appendChild(link);

	return link;
};

const getURL = (href, parameters) => {
	const url = new URL(href);

	parameters.forEach(([key, value]) => {
		url.searchParams.set(key, value);
	});

	return url.toString();
};

describe('usePreviewURL', () => {
	let defaultLayoutType;

	beforeEach(() => {
		defaultLayoutType = config.layoutType;
	});

	afterEach(() => {
		document
			.querySelectorAll('[data-page-editor-layout-preview-base-url]')
			.forEach((link) => {
				link.parentElement.removeChild(link);
			});

		config.layoutType = defaultLayoutType;
	});

	it('appends selected languageId and segmentsExperienceId', () => {
		const link = renderSampleLink();

		renderComponent();

		expect(link.href).toBe(
			getURL('http://test.com', [
				['languageId', 'en_US'],
				['segmentsExperienceId', '0'],
			])
		);
	});

	it('appends selected display page item if any', () => {
		config.layoutType = LAYOUT_TYPES.display;

		const link = renderSampleLink();

		renderComponent({
			displayPagePreviewItem: {
				data: {classNameId: 'sampleClassNameId', classPK: '1234'},
				label: 'Sample Preview Item',
			},
		});

		expect(link.href).toBe(
			getURL('http://test.com', [
				['languageId', 'en_US'],
				['segmentsExperienceId', '0'],
				['classNameId', 'sampleClassNameId'],
				['classPK', '1234'],
			])
		);
	});
});
