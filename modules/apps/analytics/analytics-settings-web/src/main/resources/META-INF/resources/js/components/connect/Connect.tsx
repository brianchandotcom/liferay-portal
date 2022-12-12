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
import React, {useEffect, useState} from 'react';

import {Events, useData, useDispatch} from '../../App';
import {fetchConnection} from '../../utils/api';
import BasePage from '../BasePage';
import Loading from '../Loading';
import DisconnectModal from './DisconnectModal';

interface IConnectProps {
	onConnect?: () => void;
	title: string;
}

const Connect: React.FC<IConnectProps> = ({onConnect, title}) => {
	const {connected, liferayAnalyticsURL, token: initialToken} = useData();
	const dispatch = useDispatch();

	const [token, setToken] = useState(initialToken);
	const {observer, onOpenChange, open} = useModal();
	const [submitting, setSubmitting] = useState(false);

	useEffect(() => {
		setToken(initialToken);
	}, [initialToken]);

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

			<ClayForm onSubmit={(event) => event.preventDefault()}>
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
						placeholder={Liferay.Language.get('paste-token-here')}
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
								displayType="primary"
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
							onClick={async () => {
								setSubmitting(true);

								const {ok} = await fetchConnection(token);

								setSubmitting(false);

								if (ok) {
									dispatch({
										payload: {
											connected: true,
											token,
										},
										type: Events.Connect,
									});

									onConnect && onConnect();
								}
							}}
						>
							{submitting && <Loading inline />}

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
