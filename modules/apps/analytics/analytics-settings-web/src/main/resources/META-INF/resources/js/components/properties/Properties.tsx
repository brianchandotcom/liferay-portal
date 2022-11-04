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

import {fetchProperties} from '../../utils/api';
import {NOT_FOUND_GIF} from '../../utils/constants';
import useFetchData from '../../utils/useFecthData';
import StateRenderer, {
	EmptyStateComponent,
	ErrorStateComponent,
} from '../StateRenderer';
import AssignModal from '../modals/AssignModal';
import CreatePropertyModal from '../modals/CreatePropertyModal';
import PropertiesTable from './PropertiesTable';

export type TProperty = {
	channelId: string;
	commerceSyncEnabled?: boolean;
	dataSources: Array<TDataSource>;
	name: string;
};

type TDataSource = {
	commerceChannelIds: Array<number>;
	dataSourceId: string;
	siteIds: Array<number>;
};

const Properties: React.FC = () => {
	const [properties, setProperties] = useState<TProperty[]>([]);
	const {
		observer: assignModalObserver,
		onOpenChange: onAssignModalOpenChange,
		open: assignModalOpen,
	} = useModal();
	const {
		observer: propertyModalObserver,
		onOpenChange: onPropertyModalOpenChange,
		open: propertyModalOpen,
	} = useModal();
	const [selectedProperty, setSelectedProperty] = useState<TProperty>(
		properties[0]
	);

	const {data, error, loading, refetch, refetching} = useFetchData(
		fetchProperties
	);

	useEffect(() => {
		if (data?.items) {
			setProperties(data.items);
		}
	}, [data]);

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
						disabled={refetching}
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
							onClick={() => onPropertyModalOpenChange(true)}
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
							onClick={() => onPropertyModalOpenChange(true)}
							type="button"
						>
							{Liferay.Language.get('new-property')}
						</ClayButton>
					</div>

					<PropertiesTable
						onAssignModalButtonClick={(index: number) => {
							setSelectedProperty(properties[index]);
							onAssignModalOpenChange(true);
						}}
						onCommerceSwitchChange={(index: number) => {
							const newProperties = [...properties];

							if (newProperties[index].commerceSyncEnabled) {
								delete newProperties[index].commerceSyncEnabled;
							}
							else {
								newProperties[
									index
								].commerceSyncEnabled = !newProperties[index]
									.commerceSyncEnabled;
							}

							setProperties(newProperties);
						}}
						properties={properties}
					/>
				</StateRenderer.Success>
			</StateRenderer>

			{assignModalOpen && (
				<AssignModal
					observer={assignModalObserver}
					onCloseModal={() => onAssignModalOpenChange(false)}
					property={selectedProperty}
				/>
			)}

			{propertyModalOpen && (
				<CreatePropertyModal
					observer={propertyModalObserver}
					onCloseModal={(updateProperty) => {
						if (updateProperty) {
							const request = async () => {
								const response = await fetchProperties();

								setProperties(response.items);
							};

							request();
						}

						onPropertyModalOpenChange(false);
					}}
				/>
			)}
		</>
	);
};

export default Properties;
