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
import {useModal} from '@clayui/modal';
import React, {useEffect, useState} from 'react';

import {fetchProperties, updatecommerceSyncEnabled} from '../../utils/api';
import {NOT_FOUND_GIF} from '../../utils/constants';
import {useRequest} from '../../utils/useRequest';
import StateRenderer, {
	EmptyStateComponent,
	ErrorStateComponent,
} from '../StateRenderer';
import AssignModal from './AssignModal';
import CreatePropertyModal from './CreatePropertyModal';
import PropertiesTable from './PropertiesTable';

export type TProperty = {
	channelId: string;
	commerceSyncEnabled: boolean;
	dataSources: TDataSource[] | [];
	name: string;
};

type TDataSource = {
	commerceChannelIds: number[];
	dataSourceId: string;
	siteIds: number[];
};

const Properties: React.FC = () => {
	const [properties, setProperties] = useState<TProperty[]>([]);
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
	const [selectedProperty, setSelectedProperty] = useState<TProperty>(
		properties[0]
	);

	const {data, error, loading, refetch} = useRequest<{
		items: TProperty[];
	}>(fetchProperties);

	useEffect(() => {
		if (data?.items) {
			setProperties(data.items);
		}
	}, [data]);

	const handleCloseModal = async (closeFn: (value: boolean) => void) => {
		const {items} = await fetchProperties();

		setProperties(items);

		Liferay.Util.openToast({
			message: Liferay.Language.get(
				'properties-settings-have-been-saved'
			),
		});

		closeFn(false);
	};

	return (
		<>
			<StateRenderer
				empty={!properties.length}
				error={error}
				loading={loading}
			>
				<StateRenderer.Error>
					<ErrorStateComponent
						className="empty-state-border mb-0 pb-5"
						onClickRefetch={refetch}
					/>
				</StateRenderer.Error>

				<StateRenderer.Empty>
					<EmptyStateComponent
						className="empty-state-border"
						description={Liferay.Language.get(
							'create-a-property-to-add-sites-and-channels'
						)}
						imgSrc={NOT_FOUND_GIF}
						title={Liferay.Language.get('create-a-new-property')}
					>
						<ClayButton
							displayType="secondary"
							onClick={() =>
								onCreatePropertyModalOpenChange(true)
							}
							type="button"
						>
							{Liferay.Language.get('new-property')}
						</ClayButton>
					</EmptyStateComponent>
				</StateRenderer.Empty>

				<StateRenderer.Success>
					<div className="text-right">
						<ClayButton
							displayType="secondary"
							onClick={() =>
								onCreatePropertyModalOpenChange(true)
							}
							type="button"
						>
							{Liferay.Language.get('new-property')}
						</ClayButton>
					</div>

					<PropertiesTable
						onAssign={(index: number) => {
							setSelectedProperty(properties[index]);
							onAssignModalOpenChange(true);
						}}
						onCommerceSwitchChange={async (index: number) => {
							const newProperties = [...properties];
							const {
								channelId,
								commerceSyncEnabled,
							} = newProperties[index];

							const {ok} = await updatecommerceSyncEnabled({
								channelId,
								commerceSyncEnabled: !commerceSyncEnabled,
							});

							if (ok) {
								newProperties[
									index
								].commerceSyncEnabled = !commerceSyncEnabled;

								setProperties(newProperties);
							}
						}}
						properties={properties}
					/>
				</StateRenderer.Success>
			</StateRenderer>

			{assignModalOpen && (
				<AssignModal
					observer={assignModalObserver}
					onCancel={() => onAssignModalOpenChange(false)}
					onSubmit={() => handleCloseModal(onAssignModalOpenChange)}
					property={selectedProperty}
				/>
			)}

			{createPropertyModalOpen && (
				<CreatePropertyModal
					observer={createPropertyModalObserver}
					onCancel={() => onCreatePropertyModalOpenChange(false)}
					onSubmit={() =>
						handleCloseModal(onCreatePropertyModalOpenChange)
					}
				/>
			)}
		</>
	);
};

export default Properties;
