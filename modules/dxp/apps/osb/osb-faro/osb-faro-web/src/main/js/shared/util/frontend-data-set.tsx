import ClayLink from '@clayui/link';
import FaroConstants from 'shared/util/constants';
import React from 'react';
import {Routes, toRoute} from './router';
import {toThousands} from './numbers';

const {cur, delta, deltaValues} = FaroConstants.pagination;

export const pagination = {
	deltas: deltaValues.map(delta => ({label: delta})),
	initialDelta: delta,
	initialPageNumber: cur
};

export const columns = {
	assetMetricRenderer: ({value}) => <span>{toThousands(value.value)}</span>,
	assetTitleRenderer: ({channelId, groupId}) => ({itemData, value}) => {
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

		return (
			<ClayLink
				displayType='tertiary'
				href={toRoute(route, {
					assetId: itemData.id,
					channelId,
					groupId,
					touchpoint: 'Any',
					...(assetTitle && {
						title: encodeURIComponent(assetTitle)
					})
				})}
			>
				{value || itemData.id}
			</ClayLink>
		);
	}
};
