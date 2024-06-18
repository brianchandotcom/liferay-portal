/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {act, fireEvent, render, screen} from '@testing-library/react';
import React from 'react';

import DisplayTemplateSelector from '../../src/main/resources/META-INF/resources/js/DisplayTemplateSelector';

jest.mock('frontend-js-web', () => ({
	...jest.requireActual('frontend-js-web'),
	getOpener: jest.fn(() => ({
		Liferay: {
			fire: jest.fn(),
		},
	})),
}));

const DEFAULT_PROPS = {
	displayStyle: 'IconValue',
	displayStyleGroupId: 1,
	displayStyleGroupKey: 'IconGroupKey',
	items: [
		{
			items: [
				{
					groupId: 20119,
					groupKey: '68468904643977',
					label: 'Icon',
					value: 'ddmTemplate_LANGUAGE-ICON-FTL',
				},
				{
					groupId: 20119,
					groupKey: '68468904643977',
					label: 'Icon Menu',
					value: 'ddmTemplate_LANGUAGE-ICON-MENU-FTL',
				},
				{
					groupId: 20119,
					groupKey: '68468904643977',
					label: 'Long Text',
					value: 'ddmTemplate_LANGUAGE-LONG-TEXT-FTL',
				},
			],
			label: 'Global',
		},
	],
};

const selectTemplate = (optionValue) => {
	fireEvent.click(screen.getByLabelText('display-template'));

	act(() => {
		fireEvent.click(
			screen.getByText(optionValue, {
				selector: '[role="option"]',
			})
		);
	});
};

function renderComponent() {
	render(
		<DisplayTemplateSelector namespace="namespace" props={DEFAULT_PROPS} />
	);
}

describe('DisplayTemplateSelector', () => {
	it('calls Liferay.fire with correct value when selecting a template', async () => {
		renderComponent();

		selectTemplate('Icon Menu');

		expect(Liferay.fire).toBeCalledWith(
			'templateSelector:changedTemplate',
			{value: 'ddmTemplate_LANGUAGE-ICON-MENU-FTL'}
		);

		selectTemplate('Long Text');

		expect(Liferay.fire).toBeCalledWith(
			'templateSelector:changedTemplate',
			{value: 'ddmTemplate_LANGUAGE-LONG-TEXT-FTL'}
		);
	});
});
