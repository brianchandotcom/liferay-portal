/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import ContentSection from '../../../../../../src/main/resources/META-INF/resources/revamp/js/components/forms/content_selector/ContentSection';
import {PreviewPortletDataHandlerSection} from '../../../../../../src/main/resources/META-INF/resources/revamp/js/types/portletDataHandler';

function makeSection(name: string): PreviewPortletDataHandlerSection {
	return {
		label: name,
		name,
		previewPortletDataHandlers: [{label: 'Handler', name: 'handler'}],
	};
}

describe('ContentSection', () => {
	it('renders a compact scrollable body for sections in COMPACT_SECTION_NAMES', () => {
		const {container} = render(
			<ContentSection
				onChange={jest.fn()}
				section={makeSection('objects')}
				value={undefined}
			/>
		);

		expect(
			container.querySelector('.content-section-scroll')
		).not.toBeNull();
	});

	it('renders a regular body for sections not in COMPACT_SECTION_NAMES', () => {
		const {container} = render(
			<ContentSection
				onChange={jest.fn()}
				section={makeSection('web-content')}
				value={undefined}
			/>
		);

		expect(container.querySelector('.content-section-scroll')).toBeNull();
	});
});
