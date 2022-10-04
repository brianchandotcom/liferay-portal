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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {useModal} from '@clayui/modal';
import classNames from 'classnames';
import React, {useContext, useState} from 'react';

import {AppContext, Events} from '../App';
import {ESteps} from '../pages/wizard/WizardPage';
import {fetchConnection} from '../utils/api';
import BasePage from './BasePage';
import DisconnectModal from './DisconnectModal';
import LoadingInline from './LoadingInline';

interface IConnectProps {
	onChangeStep?: (step: ESteps) => void;
	title: string;
}

const Connect: React.FC<IConnectProps> = ({onChangeStep, title}) => {
	const [
		{connected, liferayAnalyticsURL, token: initialToken},
		dispatch,
	] = useContext(AppContext);
	const [token, setToken] = useState(initialToken);
	const {observer, onOpenChange, open} = useModal();
	const [submitting, setSubmitting] = useState(false);

	const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
		event.preventDefault();

		const request = async () => {
			setSubmitting(true);

			const result = await fetchConnection(token);

			setSubmitting(false);

			if (result?.ok) {
				dispatch({
					payload: {
						connected: true,
						token,
					},
					type: Events.Connected,
				});

				onChangeStep && onChangeStep(ESteps.Property);
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
			title={title}
		>
			{connected && (
				<ClayAlert
					displayType="success"
					title={Liferay.Language.get('connected')}
				/>
			)}

			<ClayForm onSubmit={handleSubmit}>
				<ClayForm.Group>
					<label
						className={classNames({
							disabled: connected,
						})}
						htmlFor="inputToken"
					>
						{Liferay.Language.get('analytics-cloud-token')}
					</label>

					<ClayInput
						disabled={connected}
						id="inputToken"
						onChange={({target: {value}}) => setToken(value)}
						type="text"
						value={token}
					/>

					<label
						className={classNames({
							disabled: connected,
						})}
					>
						<small
							className={classNames({
								'text-secondary': !connected,
							})}
						>
							{Liferay.Language.get('analytics-cloud-token-help')}
						</small>
					</label>
				</ClayForm.Group>

				<BasePage.Footer>
					{connected ? (
						<>
							<ClayButton
								className="mr-3"
								displayType="secondary"
								onClick={() => window.open(liferayAnalyticsURL)}
							>
								{Liferay.Language.get('go-to-workspace')}

								<ClayIcon className="ml-2" symbol="shortcut" />
							</ClayButton>

							<ClayButton
								displayType="secondary"
								onClick={() => onOpenChange(true)}
							>
								{Liferay.Language.get('disconnect')}
							</ClayButton>
						</>
					) : (
						<ClayButton
							disabled={!token || submitting}
							type="submit"
						>
							{submitting && <LoadingInline />}

							{Liferay.Language.get('connect')}
						</ClayButton>
					)}
				</BasePage.Footer>
			</ClayForm>

			{open && (
				<DisconnectModal
					observer={observer}
					onOpenChange={onOpenChange}
				/>
			)}
		</BasePage>
	);
};

export default Connect;
