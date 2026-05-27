/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fireContentAcceptedEvent} from '../../../src/main/resources/META-INF/resources/js/WritingAssistant/utils/disclaimerUtils';

Object.assign(global.Liferay, {fire: jest.fn()});

Object.assign(global.Liferay.ThemeDisplay, {
	getUserId: jest.fn(() => 20123),
});

describe('disclaimerUtils', () => {
	describe('fireContentAcceptedEvent', () => {
		beforeEach(() => {
			(global.Liferay.fire as jest.Mock).mockClear();
		});

		it('fires "writingAssistantContentAccepted" via Liferay.fire with the correct shape', () => {
			const before = Date.now();

			fireContentAcceptedEvent();

			const after = Date.now();

			expect(global.Liferay.fire).toHaveBeenCalledTimes(1);

			const [eventName, payload] = (global.Liferay.fire as jest.Mock).mock
				.calls[0];

			expect(eventName).toBe('writingAssistantContentAccepted');

			expect(payload.aiAssisted).toBe(true);

			const acceptedAt = new Date(payload.aiGeneratedAt).getTime();

			expect(acceptedAt).toBeGreaterThanOrEqual(before);
			expect(acceptedAt).toBeLessThanOrEqual(after);

			expect(typeof payload.aiReviewedBy).not.toBe('undefined');
		});
	});
});
