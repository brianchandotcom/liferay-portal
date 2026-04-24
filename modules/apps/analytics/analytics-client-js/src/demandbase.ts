/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Analytics from './analytics';
import {Analytics as AnalyticsType} from './types';
import {
	DEMANDBASE_READY_POLL_INTERVAL,
	DEMANDBASE_READY_TIMEOUT,
} from './utils/constants';
import hash from './utils/hash';
import {getItem, removeItem, setItem} from './utils/storage';

type CompanyProfile = {[key: string]: unknown};

class Demandbase {
	analyticsInstance: Analytics;

	constructor(analyticsInstance: Analytics) {
		this.analyticsInstance = analyticsInstance;
	}

	/**
	 * Resolves with window.Demandbase.IpApi.CompanyProfile once it becomes
	 * available, or null when the timeout hits. Uses Demandbase's native
	 * registerCallback when the API is exposed, and falls back to polling.
	 */
	waitForReadiness(
		timeoutMs: number = DEMANDBASE_READY_TIMEOUT
	): Promise<CompanyProfile | null> {
		try {
			const current = window.Demandbase?.IpApi?.CompanyProfile;

			if (current) {
				return Promise.resolve(current);
			}
		}
		catch {
			return Promise.resolve(null);
		}

		return new Promise((resolve) => {
			const timers: {
				pollId?: ReturnType<typeof setInterval>;
				timeoutId?: ReturnType<typeof setTimeout>;
			} = {};
			let settled = false;

			const done = (value: CompanyProfile | null) => {
				if (settled) {
					return;
				}

				settled = true;

				if (timers.pollId) {
					clearInterval(timers.pollId);
				}

				if (timers.timeoutId) {
					clearTimeout(timers.timeoutId);
				}

				resolve(value);
			};

			try {
				window.Demandbase?.Utilities?.Callbacks?.registerCallback?.(
					() => {
						try {
							done(
								window.Demandbase?.IpApi?.CompanyProfile ?? null
							);
						}
						catch {
							done(null);
						}
					}
				);
			}
			catch {

				// Callback registration failed; rely on polling.

			}

			timers.pollId = setInterval(() => {
				try {
					const profile = window.Demandbase?.IpApi?.CompanyProfile;

					if (profile) {
						done(profile);
					}
				}
				catch {
					done(null);
				}
			}, DEMANDBASE_READY_POLL_INTERVAL);

			timers.timeoutId = setTimeout(() => done(null), timeoutMs);
		});
	}

	/**
	 * Sends Demandbase account data once per userId + profile + email. Waits
	 * for the Demandbase client-side SDK to be available before enqueuing.
	 * If the SDK is unavailable (customer removed the tag), any previously
	 * stored dedup hash and pending messages are cleared.
	 */
	sendAccountMessage(userId: string) {
		this.waitForReadiness().then((profile) => {
			if (this.analyticsInstance._disposed) {
				return;
			}

			if (!profile) {
				removeItem(AnalyticsType.Keys.DemandbaseAccount);

				this.analyticsInstance[
					AnalyticsType.Queues.AccountMessage
				].reset();

				return;
			}

			const {emailAddressHashed} = this.analyticsInstance.config.identity;

			const messageHash = hash({
				companyProfile: profile,
				emailAddressHashed,
				userId,
			});
			const storedHash = getItem<string>(
				AnalyticsType.Keys.DemandbaseAccount
			);

			if (messageHash === storedHash) {
				return;
			}

			setItem(AnalyticsType.Keys.DemandbaseAccount, messageHash);

			this.analyticsInstance[AnalyticsType.Queues.AccountMessage].addItem(
				{
					companyProfile: profile,
					emailAddressHashed,
					id: messageHash,
					userId,
				}
			);
		});
	}
}

export {Demandbase};
export default Demandbase;
