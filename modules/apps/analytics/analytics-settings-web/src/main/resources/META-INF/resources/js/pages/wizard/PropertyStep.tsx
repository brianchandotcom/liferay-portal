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
import ClayEmptyState from '@clayui/empty-state';
import React, {useEffect, useState} from 'react';

import BasePage from '../../components/BasePage';
import PropertiesTable from '../../components/PropertiesTable';
import {fetchProperties} from '../../utils/api';
import {ESteps, TGenericComponent} from '../wizard/WizardPage';

interface IStepProps extends TGenericComponent {}

const Step: React.FC<IStepProps> = ({onChangeStep}) => {
	const [properties, setProperties] = useState([]);

	useEffect(() => {
		const request = async () => {
			const response = await fetchProperties();
			setProperties(response.items);
		};
		request();
	}, []);

	return (
		<BasePage
			description={Liferay.Language.get('property-description')}
			title={Liferay.Language.get('property-assignment')}
		>
			<div className="text-right">
				<ClayButton
					displayType="secondary"

					// TODO: Replace this empty function with modal of LRAC-11980

					onClick={() => {}}
					type="button"
				>
					{Liferay.Language.get('new-property')}
				</ClayButton>
			</div>

			{!!properties.length && <PropertiesTable properties={properties} />}

			{!properties.length && (
				<div className="empty-state-border">
					<ClayEmptyState
						description={Liferay.Language.get(
							'create-a-property-to-add-sites-and-channels'
						)}
						imgProps={{
							alt: Liferay.Language.get('create-a-new-property'),
							title: Liferay.Language.get(
								'create-a-new-property'
							),
						}}
						imgSrc={`${Liferay.ThemeDisplay.getPathThemeImages()}/states/search_state.gif`}
						title={Liferay.Language.get('create-a-new-property')}
					>
						<ClayButton
							displayType="secondary"

							// TODO: Replace this empty function with modal of LRAC-11980

							onClick={() => {}}
							type="button"
						>
							{Liferay.Language.get('new-property')}
						</ClayButton>
					</ClayEmptyState>
				</div>
			)}

			<BasePage.Footer>
				<ClayButton.Group spaced>
					<ClayButton onClick={() => onChangeStep(ESteps.People)}>
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
		</BasePage>
	);
};

export default Step;
