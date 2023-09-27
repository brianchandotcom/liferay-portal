/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fireEvent, render} from '@testing-library/react';
import React from 'react';

import ItemDropdown from '../../../../src/main/resources/META-INF/resources/js/components/list/ItemDropdown.es';

const HIDE_BUTTON_LABEL = 'hide-result';
const PIN_BUTTON_LABEL = 'pin-result';
const SHOW_BUTTON_LABEL = 'show-result';
const UNPIN_BUTTON_LABEL = 'unpin-result';

const onClickHide = jest.fn();
const onClickPin = jest.fn();

function renderTestItemDropdown(props) {
	return render(
		<ItemDropdown
			onClickHide={onClickHide}
			onClickPin={onClickPin}
			{...props}
		/>
	);
}

describe('ItemDropdown', () => {
	afterEach(() => {
		jest.clearAllMocks();
	});

	it.each`
		hidden   | pinned   | expected              | description
		${false} | ${true}  | ${UNPIN_BUTTON_LABEL} | ${'unpin a pinned result'}
		${false} | ${false} | ${PIN_BUTTON_LABEL}   | ${'pin a visible result'}
		${true}  | ${false} | ${PIN_BUTTON_LABEL}   | ${'pin a hidden result'}
		${false} | ${false} | ${HIDE_BUTTON_LABEL}  | ${'hide a visible result'}
		${true}  | ${false} | ${SHOW_BUTTON_LABEL}  | ${'show a hidden result'}
	`('shows option to $description', ({expected, hidden, pinned}) => {
		const {getByText} = renderTestItemDropdown({
			hidden,
			pinned,
		});

		expect(getByText(expected)).not.toBeNull();
	});

	it.each`
		hidden   | pinned   | itemCount | expected              | description
		${false} | ${true}  | ${2}      | ${UNPIN_BUTTON_LABEL} | ${'unpin multiple pinned results'}
		${false} | ${false} | ${2}      | ${PIN_BUTTON_LABEL}   | ${'pin multiple visible results'}
		${true}  | ${false} | ${2}      | ${PIN_BUTTON_LABEL}   | ${'pin multiple hidden results'}
		${false} | ${false} | ${2}      | ${HIDE_BUTTON_LABEL}  | ${'hide multiple visible results'}
		${true}  | ${false} | ${2}      | ${SHOW_BUTTON_LABEL}  | ${'show multiple hidden results'}
	`(
		'shows option to $description',
		({expected, hidden, itemCount, pinned}) => {
			const {getByText} = renderTestItemDropdown({
				hidden,
				itemCount,
				pinned,
			});

			expect(getByText(`${expected}s`)).not.toBeNull();
		}
	);

	it('does not have option to show/hide when onClickHide is missing', () => {
		const {queryByText} = render(
			<ItemDropdown hidden={false} onClickPin={jest.fn()} pinned={true} />
		);
		expect(queryByText(SHOW_BUTTON_LABEL)).toBeNull();
		expect(queryByText(HIDE_BUTTON_LABEL)).toBeNull();
	});

	it('shows the dropdown buttons when clicked on', () => {
		const {getByText, getByTitle} = renderTestItemDropdown({
			hidden: false,
			pinned: false,
		});

		fireEvent.click(getByTitle('actions'));

		expect(getByText(PIN_BUTTON_LABEL)).not.toBeNull();
		expect(getByText(HIDE_BUTTON_LABEL)).not.toBeNull();
	});

	it.each`
		fcn            | title                | fcnName
		${onClickHide} | ${HIDE_BUTTON_LABEL} | ${'onClickHide'}
		${onClickPin}  | ${PIN_BUTTON_LABEL}  | ${'onClickPin'}
	`(
		'calls the $fcnName function when the button gets clicked on',
		({fcn, title}) => {
			const {getByText} = renderTestItemDropdown({
				hidden: false,
				pinned: false,
			});

			fireEvent.click(getByText(title));

			expect(fcn.mock.calls.length).toBe(1);
		}
	);
});
