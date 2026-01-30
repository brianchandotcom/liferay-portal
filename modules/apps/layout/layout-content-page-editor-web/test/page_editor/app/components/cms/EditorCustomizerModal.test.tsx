/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, render, screen} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom';

import EditorCustomizerModal from '../../../../../src/main/resources/META-INF/resources/page_editor/app/components/cms/EditorCustomizerModal';

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/page_editor/app/config/index',
	() => ({
		config: {
			imagesPath: '/images',
			isCMS: true,
			portletNamespace: 'test-namespace-',
		},
	})
);

declare const Liferay: any;

describe('EditorCustomizerModal', () => {
	afterEach(() => {
		jest.useRealTimers();
	});

	beforeEach(() => {
		jest.useFakeTimers();

		Liferay.FeatureFlags = Liferay.FeatureFlags || {};
		Liferay.Language = Liferay.Language || {get: (key: string) => key};
		Liferay.Util = {
			...Liferay.Util,
			Session: {
				get: jest.fn(),
				set: jest.fn(),
			},
		};
	});

	it('renders enterprise modal when feature flag is enabled', () => {
		Liferay.FeatureFlags['LPD-74377'] = true;

		render(<EditorCustomizerModal />);

		act(() => {
			jest.runAllTimers();
		});

		expect(
			screen.getByText('upgrade-to-unlock-the-editor-customizer')
		).toBeInTheDocument();

		expect(screen.getByText('enterprise')).toBeInTheDocument();
	});

	it('renders intro modal when feature flag is disabled', async () => {
		Liferay.FeatureFlags['LPD-74377'] = false;
		Liferay.Util.Session.get.mockResolvedValue('false');

		render(<EditorCustomizerModal />);

		await act(async () => {
			jest.runAllTimers();
			await Promise.resolve();
		});

		expect(
			await screen.findByText('introducing-editor-customizer')
		).toBeInTheDocument();
	});
});
