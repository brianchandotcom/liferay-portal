/* eslint-disable sort-keys */
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import '@testing-library/jest-dom/extend-expect';
import {render} from '@testing-library/react';
import React from 'react';

import AnalyticsReports from '../../../src/main/resources/META-INF/resources/js/components/AnalyticsReports';
import APIService from '../../../src/main/resources/META-INF/resources/js/utils/APIService';

jest.mock('../../../src/main/resources/META-INF/resources/js/utils/APIService');
APIService.getAnalyticsReportsData.mockResolvedValue({
	context: {
		analyticsData: {
			isSynced: false,
			hasValidConnection: false,
			cloudTrialURL:
				'https://www.liferay.com/products/analytics-cloud/get-started',
			url: 'https://analytics-uat.liferay.com/workspace/253875',
		},
		hideAnalyticsReportsPanelURL: 'http://example.com',
		languageTag: 'en-US',
		page: {
			plid: 4,
		},
		pathToAssets: '/o/analytics-reports-web',
	},
});

describe('AnalyticsReports', () => {
	afterEach(() => {
		jest.clearAllMocks();
	});

	it('fetches data when the panel is open', () => {
		render(
			<AnalyticsReports
				analyticsReportsDataURL="https://localhost:8080/api"
				hoverOrFocusEventTriggered={false}
				isPanelStateOpen={true}
			/>
		);

		expect(APIService.getAnalyticsReportsData).toHaveBeenCalled();
	});

	it('does not fetch data when the panel is open', async () => {
		render(
			<AnalyticsReports
				analyticsReportsDataURL="https://localhost:8080/api"
				hoverOrFocusEventTriggered={false}
				isPanelStateOpen={false}
			/>
		);

		expect(APIService.getAnalyticsReportsData).not.toHaveBeenCalled();
	});
});
