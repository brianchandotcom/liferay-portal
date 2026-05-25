/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Detection} from './detection';
import {log} from './log';
import {store} from './store';

import type {AudiencesDefinition, Handler, Retention} from './index';

interface HandlersMap {
	[audienceId: string]: Handler[];
}

const handlers: HandlersMap = {};

export function clear(retention?: Retention): void {
	store.clear(retention);
}

export function get(): Set<string> {
	return store.getAudienceIds();
}

export async function runDetection(
	audiencesDefinitionURL: string
): Promise<void> {

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const result = await fetch(audiencesDefinitionURL);

	const audiencesDefinition: AudiencesDefinition = await result.json();

	const detection = new Detection(audiencesDefinition);

	const matches = await detection.run();

	const browserAudienceIds = store.getBrowserAudienceIds();
	const pageAudienceIds = store.getPageAudienceIds();
	const tabAudienceIds = store.getTabAudienceIds();

	for (const match of matches) {
		switch (match.retention) {
			case 'BROWSER': {
				browserAudienceIds.add(match.id);
				break;
			}

			case 'PAGE': {
				pageAudienceIds.add(match.id);
				break;
			}

			case 'TAB': {
				tabAudienceIds.add(match.id);
				break;
			}

			default: {
				throw new Error(`Unsupported retention: ${match.retention}`);
			}
		}
	}

	store.setBrowserAudienceIds(browserAudienceIds);
	store.setPageAudienceIds(pageAudienceIds);
	store.setTabAudienceIds(tabAudienceIds);
}

export function on(audienceId: string, handler: Handler): void {
	log(
		`Adding handler '${handler.name ?? 'anonymous'}' for audience '${audienceId}'`
	);

	if (!handlers[audienceId]) {
		handlers[audienceId] = [];
	}

	handlers[audienceId].push(handler);
}

export async function runHandlers(): Promise<void> {
	const audienceIds = get();

	for (const audienceId of audienceIds) {
		if (!handlers[audienceId]) {
			continue;
		}

		for (const handler of handlers[audienceId]) {
			log(
				`Running handler '${handler.name ?? 'anonymous'}' for audience '${audienceId}'`
			);

			await handler.apply(handler);
		}
	}
}

export function setLogEnabled(enabled: boolean) {
	log.enabled = enabled;
}
