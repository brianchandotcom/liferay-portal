import ClayLink from '@clayui/link';
import FaroConstants from 'shared/util/constants';
import React from 'react';
import {AssetIcon, MimeTypes} from 'assets/components/AssetsIcon';
import {Routes, toRoute} from './router';
import {Text} from '@clayui/core';
import {toThousands} from './numbers';

const {cur, delta, deltaValues} = FaroConstants.pagination;

export const pagination = {
	deltas: deltaValues.map(delta => ({label: delta})),
	initialDelta: delta,
	initialPageNumber: cur
};

export const columns = {
	assetMetricRenderer: ({value}) => <span>{toThousands(value.value)}</span>,
	assetTitleRenderer: ({channelId, groupId, rangeSelectorParams}) => ({
		itemData,
		value
	}) => {
		const mapRoutes = {
			blog: Routes.ASSETS_BLOGS_OVERVIEW,
			document: Routes.ASSETS_DOCUMENTS_AND_MEDIA_OVERVIEW,
			form: Routes.ASSETS_FORMS_OVERVIEW,
			webContent: Routes.ASSETS_WEB_CONTENT_OVERVIEW
		};

		const assetTitle = value || itemData.id;
		const route =
			mapRoutes?.[itemData.assetType] ??
			Routes.ASSETS_OBJECT_ENTRY_OVERVIEW;

		const URL = `${toRoute(route, {
			assetId: itemData.id,
			channelId,
			groupId,
			touchpoint: 'Any',
			...(assetTitle && {
				title: encodeURIComponent(assetTitle)
			})
		})}?${rangeSelectorParams}`;

		return (
			<div className='align-items-center d-flex'>
				{itemData.mimeType && (
					<div className='mr-2'>
						<AssetIcon mimeType={itemData.mimeType as MimeTypes} />
					</div>
				)}

				<div>
					<ClayLink displayType='tertiary' href={URL}>
						{value || itemData.id}
					</ClayLink>

					<div>
						<Text color='secondary' size={3}>
							{itemData.id}
						</Text>
					</div>
				</div>
			</div>
		);
	}
};

/**
 * Patches window.fetch to base64-encode the `filter` query parameter on every
 * outgoing request. This is necessary because Cloud Armor blocks requests whose
 * filter strings contain OData expressions with parentheses, quotes, and spaces
 * (e.g. "(assetType in ('CMSBasicWebContent')) and (assetTags in ('tag 01'))").
 * Encoding the value with btoa() makes the payload opaque to the WAF rules.
 *
 * The function returns a cleanup callback that restores the original fetch,
 * intended to be used as the return value of a useEffect call.
 *
 * The backend must decode the filter parameter from base64 before processing it.
 */
export function createFetchFilterInterceptor() {
	const originalFetch = window.fetch;

	window.fetch = function (input: any, init: any): Promise<Response> {
		try {
			const rawUrl =
				typeof input === 'string'
					? input
					: input instanceof URL
					? input.href
					: (input as Request).url;

			const urlObj = new URL(rawUrl, window.location.origin);
			const filterParam = urlObj.searchParams.get('filter');

			if (filterParam) {
				urlObj.searchParams.set('filter', btoa(filterParam));

				if (typeof input === 'string') {
					input = urlObj.toString();
				} else if (input instanceof URL) {
					input = new URL(urlObj.toString());
				} else {
					input = new Request(urlObj.toString(), input as Request);
				}
			}
		} catch (_) {
			// proceed with original input if URL parsing fails
		}

		return originalFetch.call(this, input, init);
	};

	return () => {
		window.fetch = originalFetch;
	};
}
