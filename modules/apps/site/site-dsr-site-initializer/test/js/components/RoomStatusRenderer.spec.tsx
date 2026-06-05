/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render, screen} from '@testing-library/react';
import React from 'react';

import RoomStatusRenderer, {
	STATUS_DISPLAY_TYPES,
} from '../../../src/main/resources/META-INF/resources/js/components/props_transformer/cell_renderers/RoomStatusRenderer';

function renderStatus(label: string, labelI18n?: string) {
	render(
		<RoomStatusRenderer
			value={{code: 0, label, label_i18n: labelI18n ?? label}}
		/>
	);
}

describe('RoomStatusRenderer', () => {
	it.each(Object.entries(STATUS_DISPLAY_TYPES))(
		'renders the "%s" status as a %s label',
		(label, displayType) => {
			renderStatus(label);

			expect(screen.getByText(label).parentElement).toHaveClass(
				'label',
				`label-inverse-${displayType}`
			);
		}
	);

	it('falls back to a secondary label for an unknown status', () => {
		renderStatus('unknown');

		expect(screen.getByText('unknown').parentElement).toHaveClass(
			'label',
			'label-inverse-secondary'
		);
	});

	it('displays the localized label', () => {
		renderStatus('approved', 'Approved');

		expect(screen.getByText('Approved').parentElement).toHaveClass(
			'label',
			'label-inverse-success'
		);
	});

	it('renders a fallback when the status is missing', () => {
		render(<RoomStatusRenderer value={undefined} />);

		expect(screen.getByText('--')).toBeInTheDocument();
	});
});
