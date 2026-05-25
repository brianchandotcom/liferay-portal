/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom';

import ChatbotsCard from '../../../../src/main/resources/META-INF/resources/js/activity_dashboard/components/ChatbotsCard';

(global as any).Liferay = {
	Language: {
		get: (key: string) => key,
	},
};

describe('ChatbotsCard', () => {
	it('renders the chatbots heading and value', () => {
		render(<ChatbotsCard value={4} />);

		expect(
			screen.getByRole('heading', {level: 2, name: 'chatbots'})
		).toBeInTheDocument();
		expect(screen.getByText('4')).toBeInTheDocument();
	});
});
