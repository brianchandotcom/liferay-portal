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
import ClayLabel from '@clayui/label';
import React, {useState} from 'react';

import BasePage from '../../components/BasePage';
import SelectPanels from '../../components/people-step-components/SelectPanels';
import {ESteps, TGenericComponent} from './WizardPage';

interface IStepProps extends TGenericComponent {}

const Step: React.FC<IStepProps> = ({onChangeStep}) => {
	const [syncAll, setSyncAll] = useState(false);

	return (
		<BasePage
			description={Liferay.Language.get('sync-people-description')}
			title={Liferay.Language.get('sync-people')}
		>
			<div className="general-toggle">
				<ClayToggle
					label={Liferay.Language.get(
						'sync-all-contacts-and-accounts'
					)}
					onToggle={() => setSyncAll(!syncAll)}
					toggled={syncAll}
				/>

				<ClayLabel className="ml-4" displayType="info">
					{Liferay.Language.get('recomended')}
				</ClayLabel>
			</div>

			<SelectPanels
				accountsCount={0}
				organizationsCount={0}
				usersCount={0}
			/>

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
