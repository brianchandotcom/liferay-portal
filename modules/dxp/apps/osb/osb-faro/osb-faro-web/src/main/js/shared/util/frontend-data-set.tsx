import ClayLink from '@clayui/link';
import FaroConstants from 'shared/util/constants';
import Label from '@clayui/label';
import React, {useEffect, useState} from 'react';
import {CUSTOM_DATE_FORMAT, formatUTCDate} from './date';
import {Text} from '@clayui/core';
import {toRoute} from './router';

const {cur, delta, deltaValues} = FaroConstants.pagination;

export const pagination = {
	deltas: deltaValues.map(delta => ({label: delta})),
	initialDelta: delta,
	initialPageNumber: cur
};

type FDSCellProps<TValue = unknown, TItemData = Record<string, unknown>> = {
	itemData: TItemData;
	value: TValue;
};

export const columns = {
	attributeNameAndValue: ({
		attributeName,
		value
	}: {
		attributeName?: string;
		value: string | number;
	}) => (
		<div>
			<p className='mb-0 text-secondary'>{attributeName}</p>
			<Text size={3} weight='semi-bold'>
				{value}
			</Text>
		</div>
	),
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
	dateRenderer: ({value}: FDSCellProps<string | number>) => (
		<div>{value && formatUTCDate(value, CUSTOM_DATE_FORMAT)}</div>
	),
	nameAndLinkRenderer: ({
		channelId,
		groupId,
		itemData,
		route,
		value
	}: {
		channelId: string;
		groupId: string;
		itemData: {id: string | number};
		route: string;
		value: string;
	}) => {
		const itemTitle = value || itemData.id;

		return (
			<ClayLink
				className='font-weight-semi-bold text-dark'
				href={toRoute(route, {
					channelId,
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
