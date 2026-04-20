/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Analytics from './analytics';
import {
	ANALYTICS_BATCH_SEGMENT_EXTERNAL_REFERENCE_CODES,
	HEADER_PROJECT_ID,
	THREE_HOURS_IN_MILLISECONDS,
} from './utils/constants';
import {getItem, setItem} from './utils/storage';

export type SegmentCachedData = {
	[key: string]: {
		createDate: number;
		segmentExternalReferenceCodes: string[];
	};
};

export class Segment {
	private instance: Analytics;

	constructor(analytics: Analytics) {
		this.instance = analytics;
	}

	getBatchSegmentExternalReferenceCodes() {
		const individualId = this._getIndividualId();

		const cachedData = getItem<SegmentCachedData>(
			ANALYTICS_BATCH_SEGMENT_EXTERNAL_REFERENCE_CODES
		);

		if (cachedData && cachedData[individualId]) {
			const date = new Date();
			const userData = cachedData[individualId];

			if (
				date.getTime() - userData.createDate <
				THREE_HOURS_IN_MILLISECONDS
			) {
				return Promise.resolve(userData.segmentExternalReferenceCodes);
			}
		}

		return this._fetchSegmentExternalReferenceCodes(
			individualId,
			'batch-segment-external-reference-codes'
		).then((data) => {
			try {
				const date = new Date();

				const allCachedData =
					getItem<SegmentCachedData>(
						ANALYTICS_BATCH_SEGMENT_EXTERNAL_REFERENCE_CODES
					) || {};

				setItem(ANALYTICS_BATCH_SEGMENT_EXTERNAL_REFERENCE_CODES, {
					...allCachedData,
					[individualId]: {
						createDate: date.getTime(),
						segmentExternalReferenceCodes: data,
					},
				});

				return data;
			}
			catch (error) {
				return data;
			}
		});
	}

	getRealTimeSegmentExternalReferenceCodes() {
		return this._fetchSegmentExternalReferenceCodes(
			this._getIndividualId(),
			'real-time-segment-external-reference-codes'
		);
	}

	private _getIndividualId() {
		const {config} = this.instance;

		return (
			config.identity?.emailAddressHashed || this.instance._getUserId()
		);
	}

	private _fetchSegmentExternalReferenceCodes(
		individualId: string,
		endpoint: string
	) {
		const {config} = this.instance;

		const headers = {'Content-Type': 'application/json'};

		if (config.projectId) {
			Object.assign(headers, {
				[HEADER_PROJECT_ID]: config.projectId,
			});
		}

		return fetch(
			`${config.faroBackendUrl}/api/1.0/segment-memberships/${individualId}/${endpoint}`,
			{
				cache: 'default',
				credentials: 'same-origin',
				headers,
				method: 'GET',
				mode: 'cors',
			}
		).then((response) => response.json());
	}
}
