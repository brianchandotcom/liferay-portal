import ClayLink from '@clayui/link';
import FaroConstants from 'shared/util/constants';
import Label from '@clayui/label';
import React, {useEffect, useState} from 'react';
import {CUSTOM_DATE_FORMAT, formatUTCDate} from './date';
import {toRoute} from './router';

const {cur, delta, deltaValues} = FaroConstants.pagination;

export const pagination = {
	deltas: deltaValues.map(delta => ({label: delta})),
	initialDelta: delta,
	initialPageNumber: cur
};

export const columns = {
	cmsLabelRenderer: ({
		displayType,
		label
	}: {
		displayType: 'danger' | 'info' | 'secondary' | 'success' | 'warning';
		label: React.ReactNode;
	}) => (
		<Label
			className='fds-label font-weight-semi-bold rounded'
			displayType={displayType}
		>
			{label}
		</Label>
	),
	dateRenderer: ({value}) => (
		<div>{value && formatUTCDate(value, CUSTOM_DATE_FORMAT)}</div>
	),
	nameAndLinkRenderer: ({groupId, itemData, route, value}) => {
		const itemTitle = value || itemData.id;

		return (
			<ClayLink
				className='font-weight-semi-bold text-dark'
				href={toRoute(route, {
					groupId,
					id: itemData.id
				})}
			>
				{itemTitle}
			</ClayLink>
		);
	}
};

export function useSnapshots(fdsName: string) {
	if (
		!Liferay.FeatureFlags['LPD-34594'] ||
		!Liferay.FeatureFlags['LPS-164563']
	) {
		return [];
	}

	const [snapshots, setSnapshots] = useState([]);

	useEffect(() => {
		Liferay.Util.fetch(
			`/o/data-set-admin/snapshots?filter=fdsName eq '${fdsName}'`,
			{headers: {'Content-Type': 'application/json'}, method: 'GET'}
		)
			.then(res => res.json())
			.then(data => {
				const formattedSnapshots = data.items.map(
					(item: {
						externalReferenceCode: any;
						label: any;
						viewConfig: any;
					}) => ({
						configuration: item.viewConfig,
						erc: item.externalReferenceCode,
						label: item.label
					})
				);

				setSnapshots(formattedSnapshots);
			})
			.catch(error => {
				// eslint-disable-next-line no-console
				console.error('Failed to fetch snapshots:', error);
			});
	}, [fdsName]);

	return snapshots;
}
