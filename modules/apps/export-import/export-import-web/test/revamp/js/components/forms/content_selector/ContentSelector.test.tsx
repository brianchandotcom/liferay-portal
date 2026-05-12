/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen} from '@testing-library/react';
import React from 'react';

import ContentSelector from '../../../../../../src/main/resources/META-INF/resources/revamp/js/components/forms/content_selector/ContentSelector';
import {PortletDataHandlerSection} from '../../../../../../src/main/resources/META-INF/resources/revamp/js/types/portletDataHandler';

const additionsOnlySection: PortletDataHandlerSection = {
	additionCount: 5,
	deletionCount: 0,
	label: 'Additions Only',
	name: 'additions-only',
	portletDataHandlers: [],
};

const deletionsOnlySection: PortletDataHandlerSection = {
	additionCount: 0,
	deletionCount: 3,
	label: 'Deletions Only',
	name: 'deletions-only',
	portletDataHandlers: [],
};

const bothSection: PortletDataHandlerSection = {
	additionCount: 2,
	deletionCount: 4,
	label: 'Both',
	name: 'both',
	portletDataHandlers: [],
};

const noCountsSection: PortletDataHandlerSection = {
	label: 'No Counts',
	name: 'no-counts',
	portletDataHandlers: [],
};

const sections = [
	additionsOnlySection,
	deletionsOnlySection,
	bothSection,
	noCountsSection,
];

function renderSelector(showDeletions: boolean) {
	render(
		<ContentSelector
			name="contentSelection"
			onChange={() => {}}
			sections={sections}
			showDeletions={showDeletions}
			value={undefined}
		/>
	);
}

describe('ContentSelector section visibility', () => {
	it('hides deletions-only sections when showDeletions is false', () => {
		renderSelector(false);

		expect(screen.getByText('Additions Only')).toBeInTheDocument();
		expect(screen.queryByText('Deletions Only')).not.toBeInTheDocument();
		expect(screen.getByText('Both')).toBeInTheDocument();
		expect(screen.getByText('No Counts')).toBeInTheDocument();
	});

	it('shows deletions-only sections when showDeletions is true', () => {
		renderSelector(true);

		expect(screen.getByText('Additions Only')).toBeInTheDocument();
		expect(screen.getByText('Deletions Only')).toBeInTheDocument();
		expect(screen.getByText('Both')).toBeInTheDocument();
		expect(screen.getByText('No Counts')).toBeInTheDocument();
	});
});
