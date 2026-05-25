/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom';

import AIAssistantFooterDisclaimer from '../../../../src/main/resources/META-INF/resources/js/AIAssistantChat/components/AIAssistantFooterDisclaimer';

describe('AIAssistantFooterDisclaimer', () => {
	it('renders the disclaimer text', () => {
		render(<AIAssistantFooterDisclaimer />);

		expect(
			screen.getByText('ai-generated-responses-can-be-inaccurate')
		).toBeInTheDocument();
	});
});
