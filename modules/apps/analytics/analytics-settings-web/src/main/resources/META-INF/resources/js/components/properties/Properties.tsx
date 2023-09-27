/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

const getTotalCommerceChannels = (enabled: boolean, value: string): string =>
	enabled ? value : '-';

const ToggleSwitch = ({
	item,
	property: {channelId},
}: {
	item: TItem;
	property: TProperty;
}) => {
	const [
		,
		{value: totalCommerceChannels},
		,
		{value: commerceSyncEnabled},
	] = item.columns;
	const [toggle, setToggle] = useState<boolean>(
		commerceSyncEnabled as boolean
	);
	const dispatch = useDispatch();

	return (
		<ClayToggle
			onToggle={async () => {
				const newValue = !toggle;
				const {ok} = await updatecommerceSyncEnabled({
					channelId,
					commerceSyncEnabled: newValue,
				});

				if (ok) {
					dispatch({
						payload: {
							columns: [
								{
									column: {
										cellRenderer: () => (
											<span>
												{getTotalCommerceChannels(
													newValue,
													totalCommerceChannels as string
												)}
											</span>
										),
									},
									index: 1,
								},
								{
									column: {
										value: newValue,
									},
									index: 3,
								},
							],
							id: item.id,
						},
						type: Events.ChangeItem,
					});

					setToggle(newValue);
				}
			}}
			role="toggle-switch"
			toggled={toggle}
			value={EColumn.ToggleSwitch}
		/>
	);
};

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

	return (
		<>
			<Table<TProperty>
				addItemTitle={Liferay.Language.get('create-a-new-property')}
				columns={columns}
				emptyState={{
					contentRenderer: () => (
						<ClayButton
							displayType="secondary"
							onClick={() =>
								onCreatePropertyModalOpenChange(true)
							}
						>
							{Liferay.Language.get('new-property')}
						</ClayButton>
					),
					description: Liferay.Language.get(
						'create-a-property-to-add-sites-and-channels'
					),
					noResultsTitle: Liferay.Language.get(
						'no-properties-were-found'
					),
					title: Liferay.Language.get('create-a-new-property'),
				}}
				mapperItems={(items) =>
					items.map((property) => {
						const safeProperty = getSafeProperty(property);
						const {
							channelId,
							commerceSyncEnabled,
							dataSources: [{commerceChannelIds, siteIds}],
							name,
						} = safeProperty;

						return {
							columns: [
								{
									id: EColumn.Name,
									value: name,
								},
								{
									cellRenderer: (item: any) => (
										<span>
											{getTotalCommerceChannels(
												commerceSyncEnabled,
												item.columns[1].value
											)}
										</span>
									),
									id: EColumn.CommerceChannelIds,
									value: commerceChannelIds.length,
								},
								{
									id: EColumn.SiteIds,
									value: siteIds.length,
								},
								{
									cellRenderer: (item) => (
										<ToggleSwitch
											item={item}
											property={property}
										/>
									),
									id: EColumn.ToggleSwitch,
									value: commerceSyncEnabled,
								},
								{
									id: EColumn.CreateDate,
									value: 'createDate',
								},
								{
									cellRenderer: (item) => (
										<ClayButton
											displayType="secondary"
											onClick={() => {
												setSelectedProperty({
													...property,
													commerceSyncEnabled: item
														.columns[3]
														.value as boolean,
												});
												onAssignModalOpenChange(true);
											}}
											role="assign-button"
										>
											{Liferay.Language.get('assign')}
										</ClayButton>
									),
									id: EColumn.AssignButton,
									value: 'assignButton',
								},
							],
							id: channelId,
						};
					})
				}
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
								columns: [
									{
										column: {
											cellRenderer: () => (
												<span>
													{getTotalCommerceChannels(
														selectedProperty?.commerceSyncEnabled,
														String(
															commerceChannelIds.length
														)
													)}
												</span>
											),
											value: commerceChannelIds.length,
										},
										index: 1,
									},
									{
										column: {
											value: siteIds.length,
										},
										index: 2,
									},
								],
								id: selectedProperty?.channelId,
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
