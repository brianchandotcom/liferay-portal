/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Detection} from './detection';
import {log} from './log';
import {store} from './store';

import type {AudiencesDefinition, Handler, RetentionType} from './index';

interface HandlersMap {
	[audienceId: string]: Handler[];
}

const handlers: HandlersMap = {};

export function clear(retentionType?: RetentionType): void {
	store.clear(retentionType);
}

export function get(): Set<string> {
	return store.getAudienceIds();
}

export async function runDetection(
	audiencesDefinitionURL: string
): Promise<void> {
	let response;

	try {

		// eslint-disable-next-line @liferay/portal/no-global-fetch
		response = await fetch(audiencesDefinitionURL);
	}
	catch (error) {
		throw new Error(
			`Unable to fetch '${audiencesDefinitionURL}': ${getErrorMessage(error)}`
		);
	}

	if (!response.ok) {
		throw new Error(
			`Request to fetch ${audiencesDefinitionURL} returned an error: ` +
				`${response.status} ${response.statusText}`
		);
	}

	let audiencesDefinition: AudiencesDefinition;

	try {
		audiencesDefinition = await response.json();
	}
	catch (error) {
		throw new Error(
			`Unable to parse '${audiencesDefinitionURL}': ${getErrorMessage(error)}`
		);
	}

	const detection = new Detection(audiencesDefinition);

	let matches;

	try {
		matches = await detection.run();
	}
	catch (error) {
		throw new Error(
			`There was an error running audiences detection: ${getErrorMessage(error)}`
		);
	}

	const browserAudienceIds = store.getBrowserAudienceIds();
	const pageAudienceIds = store.getPageAudienceIds();
	const tabAudienceIds = store.getTabAudienceIds();

	for (const match of matches) {
		switch (match.retentionType) {
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
				throw new Error(
					`Unsupported retention type '${match.retentionType}' for audience '${match.id}'`
				);
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
			const handlerName = handler.name ?? 'anonymous';

			log(
				`Running handler '${handlerName}' for audience '${audienceId}'`
			);

			try {
				await handler();
			}
			catch (error) {
				throw new Error(
					`There was an error running handler '${handlerName}' of audience ` +
						`'${audienceId}': ${getErrorMessage(error)}`
				);
			}
		}
	}
}

export function setLogEnabled(enabled: boolean) {
	log.enabled = enabled;
}

function getErrorMessage(error: unknown): string {
	if (error instanceof Error) {
		return error.message;
	}

	return String(error);
}
