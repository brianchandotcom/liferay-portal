/* eslint-disable @liferay/portal/no-react-dom-create-portal */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayPopover from '@clayui/popover';

import './routes/customer-portal/app.scss';
import {useEffect, useMemo, useState} from 'react';
import {createPortal} from 'react-dom';
import i18n from './common/I18n';
import {useAppPropertiesContext} from './common/contexts/AppPropertiesContext';
import {Liferay} from './common/services/liferay';
import {getCurrentSession} from './common/services/okta/rest/getCurrentSession';

const OktaStatus = () => {
	const [status, setStatus] = useState<'active' | 'idle' | 'inactive'>(
		'idle'
	);
	const [popoverOpen, setPopoverOpen] = useState(false);

	const {oktaSessionAPI} = useAppPropertiesContext();
	const oktaSessionURL = new URL(oktaSessionAPI);
	const oktaSessionDomain = `${oktaSessionURL.protocol}/${oktaSessionURL.host}`;

	useEffect(() => {
		getCurrentSession(oktaSessionAPI);
	}, [oktaSessionAPI]);

	const mapColors = useMemo(
		() => ({
			active: {
				color: 'green',
				description: i18n.translate(
					'communication-with-the-okta-login-is-normal'
				),
				label: i18n.translate('active'),
			},
			idle: {
				color: 'gray',
				description: i18n.translate(
					'trying-to-establish-communication-with-okta'
				),
				label: i18n.translate('idle'),
			},
			inactive: {
				color: 'red',
				description: (
					<>
						<span>
							<strong>
								{i18n.translate(
									'communication-with-the-okta-login-has-failed'
								)}
							</strong>

							<p className="my-2">
								{i18n.translate(
									'this-may-affect-your-use-of-the-customer-portal-please-go-to'
								)}
							</p>

							<a
								href={oktaSessionDomain}
								rel="noopener noreferrer"
								target="_blank"
							>
								{oktaSessionDomain}
							</a>
						</span>

						<span className="ml-1">
							{i18n.translate('and-refresh-the-page')}
						</span>
					</>
				),
				label: i18n.translate('inactive'),
			},
		}),
		[oktaSessionDomain]
	);

	const colorMap = mapColors[status];

	const OktaStatusIcon = (
		<div
			className="okta-status-icon"
			style={{backgroundColor: colorMap.color}}
			title={colorMap.label}
		/>
	);

	useEffect(() => {
		const handler = ({
			details,
		}: {
			details: [{status: number; success: boolean}];
		}) => {
			setStatus(details[0].success ? 'active' : 'inactive');
		};

		Liferay.on('okta-status-changed', handler);

		return () => Liferay.detach('okta-status-changed', handler);
	}, []);

	return (
		<div className="okta-status">
			<div>
				<ClayPopover
					alignPosition="top"
					closeOnClickOutside
					disableScroll={false}
					header={
						<div className="align-items-center d-flex">
							{OktaStatusIcon}

							<span className="ml-1">
								{i18n.translate('okta-status')}
							</span>
						</div>
					}
					onShowChange={setPopoverOpen}
					show={popoverOpen}
					trigger={
						<ClayButton
							className="btn-rounded mr-2 mt--1"
							displayType="secondary"
							size="xs"
						>
							{OktaStatusIcon}

							{colorMap.label}
						</ClayButton>
					}
				>
					<p>{colorMap.description}</p>
				</ClayPopover>
			</div>
		</div>
	);
};

const OktaStatusPortal = () => {
	const [container, setContainer] = useState<HTMLElement | null>();

	useEffect(() => {
		setTimeout(() => {
			const element = document.getElementById(
				'customer-portal-okta-status'
			) as HTMLElement;

			setContainer(element);
		}, 1000);
	}, []);

	return <>{container && createPortal(<OktaStatus />, container)}</>;
};

export {OktaStatusPortal};

export default OktaStatus;
