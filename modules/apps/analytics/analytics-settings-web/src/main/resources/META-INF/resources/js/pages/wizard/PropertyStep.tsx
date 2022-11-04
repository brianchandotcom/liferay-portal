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

import BasePage from '../../components/BasePage';
import StateRenderer, {
	EmptyStateComponent,
	ErrorStateComponent,
} from '../../components/StateRenderer';
import AssignModal from '../../components/modals/AssignModal';
import CreatePropertyModal from '../../components/modals/CreatePropertyModal';
import PropertiesTable from '../../components/properties-step/PropertiesTable';
import {fetchProperties} from '../../utils/api';
import {NOT_FOUND_GIF} from '../../utils/constants';
import useFetchData from '../../utils/useFecthData';
import {ESteps, TGenericComponent} from './WizardPage';

interface IStepProps extends TGenericComponent {}

export type TProperty = {
	channelId: string;
	commerceEnabled?: boolean;
	dataSources: Array<TDataSource>;
	name: string;
};

export type TDataSource = {
	commerceChannelIds: Array<number>;
	dataSourceId: string;
	siteIds: Array<number>;
};

const Step: React.FC<IStepProps> = ({onChangeStep}) => {
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
		<BasePage
			description={Liferay.Language.get('property-description')}
			title={Liferay.Language.get('property-assignment')}
		>
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
							const newProperties = properties;

							if (newProperties[index].commerceEnabled) {
								delete newProperties[index].commerceEnabled;
							} else {
								newProperties[
									index
								].commerceEnabled = !newProperties[index]
									.commerceEnabled;
							}

							setProperties([...newProperties]);
						}}
						properties={properties}
					/>

					<BasePage.Footer>
						<ClayButton.Group spaced>
							<ClayButton
								onClick={() => onChangeStep(ESteps.People)}
							>
								{Liferay.Language.get('next')}
							</ClayButton>

							<ClayButton
								displayType="secondary"
								onClick={() => window.location.reload()}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>
						</ClayButton.Group>
					</BasePage.Footer>
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
		</BasePage>
	);
};

export default Step;
