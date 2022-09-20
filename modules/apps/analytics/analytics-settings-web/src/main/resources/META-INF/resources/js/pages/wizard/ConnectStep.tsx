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
import ClayForm, {ClayInput} from '@clayui/form';
import React, {useContext, useState} from 'react';

import {AppContext} from '../../App';
import BasePage from '../../components/BasePage';
import {fetchConnection} from '../../utils/api';
import {ESteps, TGenericComponent} from './WizardPage';

interface IStepProps extends TGenericComponent {}

const Step: React.FC<IStepProps> = ({onChangeStep}) => {
	const {token: initialToken} = useContext(AppContext);
	const [token, setToken] = useState(initialToken);
	const [submitting, setSubmitting] = useState(false);

	const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
		event.preventDefault();

		const request = async () => {
			setSubmitting(true);

			const result = await fetchConnection(token);

			setSubmitting(false);

			if (result?.ok) {
				onChangeStep(ESteps.Property);

				Liferay.Util.openToast({
					message: Liferay.Language.get('saved-successfully'),
				});
			}
			else {
				Liferay.Util.openToast({
					message: Liferay.Language.get(
						'an-unexpected-system-error-occurred'
					),
					type: 'danger',
				});
			}
		};

		request();
	};

	return (
		<BasePage
			description={Liferay.Language.get(
				'use-the-token-genereted-in-your-analytics-cloud-to-connect-this-workspace'
			)}
			title={Liferay.Language.get('connect-analytics-cloud')}
		>
			<ClayForm onSubmit={handleSubmit}>
				<ClayForm.Group>
					<label htmlFor="inputToken">
						{Liferay.Language.get('analytics-cloud-token')}
					</label>

					<ClayInput
						id="inputToken"
						onChange={({target: {value}}) => setToken(value)}
						type="text"
						value={token}
					/>

					<small className="text-secondary">
						{Liferay.Language.get('analytics-cloud-token-help')}
					</small>
				</ClayForm.Group>

				<BasePage.Footer>
					<ClayButton disabled={!token || submitting} type="submit">
						{submitting && (
							<span className="inline-item inline-item-before">
								<span
									aria-hidden="true"
									className="loading-animation"
								></span>
							</span>
						)}

						{Liferay.Language.get('connect')}
					</ClayButton>
				</BasePage.Footer>
			</ClayForm>
		</BasePage>
	);
};

export default Step;
