/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayButton from '@clayui/button';
import {ClayToggle} from '@clayui/form';
import {useModal} from '@clayui/modal';
import React, {useState} from 'react';

import {fetchProperties, updatecommerceSyncEnabled} from '../../utils/api';
import {OrderBy} from '../../utils/filter';
import TableContext, {Events, useData, useDispatch} from '../table/Context';
import {Table} from '../table/Table';
import {EColumnAlign, TColumn, TItem} from '../table/types';
import AssignModal from './AssignModal';
import CreatePropertyModal from './CreatePropertyModal';

export type TDataSource = {
	commerceChannelIds: number[];
	dataSourceId?: string;
	siteIds: number[];
};

export type TProperty = {
	channelId: string;
	commerceSyncEnabled: boolean;
	dataSources: TDataSource[] | [];
	name: string;
};

enum EColumn {
	AssignButton = 'assignButton',
	CommerceChannelIds = 'commerceChannelIds',
	CreateDate = 'createDate',
	Name = 'name',
	SiteIds = 'siteIds',
	ToggleSwitch = 'toggleSwitch',
}

const columns: TColumn[] = [
	{
		expanded: true,
		id: EColumn.Name,
		label: Liferay.Language.get('available-properties'),
	},
	{
		align: EColumnAlign.Right,
		id: EColumn.CommerceChannelIds,
		label: Liferay.Language.get('channels'),
		sortable: false,
	},
	{
		align: EColumnAlign.Right,
		id: EColumn.SiteIds,
		label: Liferay.Language.get('sites'),
		sortable: false,
	},
	{
		align: EColumnAlign.Right,
		id: EColumn.ToggleSwitch,
		label: Liferay.Language.get('Commerce'),
		sortable: false,
	},
	{
		id: EColumn.CreateDate,
		label: Liferay.Language.get('create-date'),
		show: false,
	},
	{
		align: EColumnAlign.Right,
		id: EColumn.AssignButton,
		label: '',
		sortable: false,
	},
];

const ToggleSwitch = ({
	onToggle,
	toggle: initialToggle,
}: {
	onToggle: (toggle: boolean) => void;
	toggle: boolean;
}) => {
	const [toggle, setToggle] = useState(initialToggle);

	return (
		<ClayToggle
			onToggle={() => {
				setToggle((toggle) => {
					onToggle(!toggle);

					return !toggle;
				});
			}}
			role="toggle-switch"
			toggled={toggle}
			value={EColumn.ToggleSwitch}
		/>
	);
};

const getCommerceChannelIdsValue = (enabled: boolean, ids: number[]): string =>
	enabled ? String(ids.length) : '-';

const getSafeProperty = (
	property: TProperty
): {
	channelId: string;
	commerceSyncEnabled: boolean;
	dataSources: TDataSource[];
	name: string;
} => {
	if (property.dataSources.length) {
		return property;
	}

	return {
		...property,
		dataSources: [
			{
				commerceChannelIds: [],
				siteIds: [],
			},
		],
	};
};

const Properties: React.FC = () => {
	const {reload} = useData();
	const dispatch = useDispatch();

	const {
		observer: assignModalObserver,
		onOpenChange: onAssignModalOpenChange,
		open: assignModalOpen,
	} = useModal();
	const {
		observer: createPropertyModalObserver,
		onOpenChange: onCreatePropertyModalOpenChange,
		open: createPropertyModalOpen,
	} = useModal();

	const [selectedProperty, setSelectedProperty] = useState<TProperty>();

	const toggleSwitch = (
		item: TItem,
		{channelId, dataSources: [{commerceChannelIds}]}: TProperty
	) => (
		<ToggleSwitch
			onToggle={async (commerceSyncEnabled) => {
				const {ok} = await updatecommerceSyncEnabled({
					channelId,
					commerceSyncEnabled,
				});

				if (ok) {
					dispatch({
						payload: {
							id: item.id,
							values: [
								{
									id: EColumn.ToggleSwitch,
									value: commerceSyncEnabled,
								},
								{
									id: EColumn.CommerceChannelIds,
									value: getCommerceChannelIdsValue(
										commerceSyncEnabled,
										commerceChannelIds
									),
								},
							],
						},
						type: Events.ChangeItem,
					});
				}
			}}
			toggle={item.columns[3].value as boolean}
		/>
	);

	const assignButton = (item: TItem, property: TProperty) => (
		<ClayButton
			displayType="secondary"
			onClick={() => {
				setSelectedProperty({
					...property,
					commerceSyncEnabled: item.columns[3].value as boolean,
				});
				onAssignModalOpenChange(true);
			}}
			role="assign-button"
		>
			{Liferay.Language.get('assign')}
		</ClayButton>
	);

	return (
		<>
			<Table<TProperty>
				addItemTitle={Liferay.Language.get('create-new-property')}
				columns={columns}
				emptyStateTitle={Liferay.Language.get(
					'there-are-no-properties'
				)}
				mapperItems={(items) =>
					items.map((property) => {
						const safeProperty = getSafeProperty(property);
						const {
							channelId,
							commerceSyncEnabled,
							dataSources: [{commerceChannelIds, siteIds}],
							name,
						} = safeProperty;

						const commerceChannelIdsValue = getCommerceChannelIdsValue(
							commerceSyncEnabled,
							commerceChannelIds
						);
						const siteIdsValue = String(siteIds.length);

						return {
							columns: [
								{
									id: EColumn.Name,
									value: name,
								},
								{
									id: EColumn.CommerceChannelIds,
									value: commerceChannelIdsValue,
								},
								{
									id: EColumn.SiteIds,
									value: siteIdsValue,
								},
								{
									cellRenderer: (item) =>
										toggleSwitch(item, safeProperty),
									id: EColumn.ToggleSwitch,
									value: commerceSyncEnabled,
								},
								{
									id: EColumn.CreateDate,
									value: 'createDate',
								},
								{
									cellRenderer: (item) =>
										assignButton(item, safeProperty),
									id: EColumn.AssignButton,
									value: 'assignButton',
								},
							],
							id: channelId,
						};
					})
				}
				noResultsTitle={Liferay.Language.get(
					'no-properties-were-found'
				)}
				onAddItem={() => onCreatePropertyModalOpenChange(true)}
				requestFn={fetchProperties}
				showCheckbox={false}
			/>

			{selectedProperty && assignModalOpen && (
				<AssignModal
					observer={assignModalObserver}
					onCancel={() => onAssignModalOpenChange(false)}
					onSubmit={({commerceChannelIds, siteIds}) => {
						Liferay.Util.openToast({
							message: Liferay.Language.get(
								'properties-settings-have-been-saved'
							),
						});

						onAssignModalOpenChange(false);

						dispatch({
							payload: {
								id: selectedProperty?.channelId,
								values: [
									{
										id: EColumn.CommerceChannelIds,
										value: getCommerceChannelIdsValue(
											!!selectedProperty?.commerceSyncEnabled,
											commerceChannelIds
										),
									},
									{
										id: EColumn.SiteIds,
										value: siteIds.length,
									},
								],
							},
							type: Events.ChangeItem,
						});
					}}
					property={getSafeProperty(selectedProperty)}
				/>
			)}

			{createPropertyModalOpen && (
				<CreatePropertyModal
					observer={createPropertyModalObserver}
					onCancel={() => onCreatePropertyModalOpenChange(false)}
					onSubmit={() => {
						Liferay.Util.openToast({
							message: Liferay.Language.get(
								'properties-settings-have-been-saved'
							),
						});

						onCreatePropertyModalOpenChange(false);

						reload();
					}}
				/>
			)}
		</>
	);
};

const PropertiesWrapper = () => (
	<TableContext
		initialFilter={{
			type: OrderBy.Desc,
			value: EColumn.CreateDate,
		}}
	>
		<Properties />
	</TableContext>
);

export default PropertiesWrapper;
