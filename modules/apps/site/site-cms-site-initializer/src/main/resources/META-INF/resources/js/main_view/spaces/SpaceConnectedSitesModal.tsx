/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayModal from '@clayui/modal';
import ClaySticker from '@clayui/sticker';
import {ItemSelector} from '@liferay/frontend-js-item-selector-web';
import {openToast} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useEffect, useId, useState} from 'react';

import {InputGroupWithSelect} from '../../common/components/InputGroupWithSelect';
import ConnectedSiteService from '../../common/services/ConnectedSiteService';
import SiteTemplateService from '../../common/services/SiteTemplateService';
import {Site} from '../../common/types/Site';
import {SiteTemplate} from '../../common/types/SiteTemplate';

enum ConnectableKind {
	SITES = 'sites',
	SITE_TEMPLATES = 'site-templates',
}

type Connection =
	| {kind: 'site'; site: Site}
	| {kind: 'site-template'; siteTemplate: SiteTemplate};

const showErrorMessage = (message: string) => {
	openToast({
		message,
		type: 'danger',
	});
};

const showSuccessMessage = (message: string) => {
	openToast({
		message,
		type: 'success',
	});
};

const getConnectionId = (connection: Connection) =>
	connection.kind === 'site'
		? connection.site.id
		: connection.siteTemplate.id;

const getConnectionKey = (connection: Connection) =>
	`${connection.kind}:${getConnectionId(connection)}`;

const getConnectionName = (connection: Connection) =>
	connection.kind === 'site'
		? connection.site.descriptiveName
		: connection.siteTemplate.name;

const getConnectionLabel = (connection: Connection) =>
	connection.kind === 'site'
		? connection.site.descriptiveName
		: `${connection.siteTemplate.name} (${Liferay.Language.get('site-template')})`;

const ConnectableActions = ({
	connection,
	externalReferenceCode,
	onConnectionChange,
	onConnectionDisconnected,
}: {
	connection: Connection;
	externalReferenceCode: string;
	onConnectionChange: (connection: Connection) => void;
	onConnectionDisconnected: (connection: Connection) => void;
}) => {
	const disconnect = async () => {
		const name = getConnectionName(connection);

		if (connection.kind === 'site') {
			const {error} = await ConnectedSiteService.disconnectSiteFromSpace(
				externalReferenceCode,
				connection.site.externalReferenceCode
			);

			if (error) {
				showErrorMessage(error);

				return;
			}

			onConnectionDisconnected(connection);
			showSuccessMessage(
				sub(
					Liferay.Language.get(
						'site-x-was-successfully-disconnected-from-the-space'
					),
					`<strong>${Liferay.Util.escapeHTML(name)}</strong>`
				)
			);

			return;
		}

		const {error} =
			await SiteTemplateService.disconnectSiteTemplateFromSpace(
				connection.siteTemplate.id
			);

		if (error) {
			showErrorMessage(error);

			return;
		}

		onConnectionDisconnected(connection);
		showSuccessMessage(
			sub(
				Liferay.Language.get(
					'site-template-x-was-successfully-disconnected-from-the-space'
				),
				`<strong>${Liferay.Util.escapeHTML(name)}</strong>`
			)
		);
	};

	const changeSearchable = async () => {
		if (connection.kind !== 'site') {
			return;
		}

		const {data, error} = await ConnectedSiteService.connectSiteToSpace(
			externalReferenceCode,
			connection.site.externalReferenceCode,
			String(!connection.site.searchable)
		);

		if (data) {
			onConnectionChange({kind: 'site', site: data});
		}
		else if (error) {
			showErrorMessage(error);
		}
	};

	const items = [];

	if (connection.kind === 'site') {
		items.push({
			label: connection.site.searchable
				? Liferay.Language.get('make-unsearchable')
				: Liferay.Language.get('make-searchable'),
			onClick: changeSearchable,
		});
	}

	items.push({
		label: Liferay.Language.get('disconnect'),
		onClick: disconnect,
	});

	const isSearchableLabel =
		connection.kind === 'site' && connection.site.searchable
			? Liferay.Language.get('yes')
			: Liferay.Language.get('no');

	return (
		<div className="align-items-center d-flex">
			{connection.kind === 'site' && (
				<span className="c-mr-3 text-2 text-secondary">
					{`${Liferay.Language.get('searchable-content')}: ${isSearchableLabel}`}
				</span>
			)}

			<ClayDropDownWithItems
				items={items}
				trigger={
					<ClayButtonWithIcon
						aria-label={Liferay.Language.get('site-actions')}
						borderless
						displayType="secondary"
						size="xs"
						symbol="ellipsis-v"
						title={Liferay.Language.get('actions')}
					/>
				}
			/>
		</div>
	);
};

const ConnectableSelector = ({
	connections,
	externalReferenceCode,
	onConnectionAdded,
}: {
	connections: Connection[];
	externalReferenceCode: string;
	onConnectionAdded: (connection: Connection) => void;
}) => {
	const [kind, setKind] = useState<ConnectableKind>(ConnectableKind.SITES);
	const [site, setSite] = useState<Site>();
	const [siteTemplate, setSiteTemplate] = useState<SiteTemplate>();
	const [disableConnectButton, setDisableConnectButton] =
		useState<boolean>(true);

	const isAlreadyConnected = (id: string) =>
		connections.some((connection) => {
			if (kind === ConnectableKind.SITES) {
				return (
					connection.kind === 'site' &&
					connection.site.externalReferenceCode === id
				);
			}

			return (
				connection.kind === 'site-template' &&
				connection.siteTemplate.id === id
			);
		});

	const resetSelection = () => {
		setSite(undefined);
		setSiteTemplate(undefined);
		setDisableConnectButton(true);
	};

	const connect = async () => {
		if (kind === ConnectableKind.SITES && site) {
			const {data, error} = await ConnectedSiteService.connectSiteToSpace(
				externalReferenceCode,
				site.externalReferenceCode
			);

			if (data) {
				onConnectionAdded({kind: 'site', site: data});
				showSuccessMessage(
					sub(
						Liferay.Language.get(
							'site-x-was-successfully-connected-to-the-space'
						),
						`<strong>${Liferay.Util.escapeHTML(site.descriptiveName)}</strong>`
					)
				);
			}
			else if (error) {
				showErrorMessage(
					error ||
						Liferay.Language.get('unable-to-connect-site-to-space')
				);
			}

			resetSelection();

			return;
		}

		if (kind === ConnectableKind.SITE_TEMPLATES && siteTemplate) {
			const {data, error} =
				await SiteTemplateService.connectSiteTemplateToSpace(
					siteTemplate.id,
					externalReferenceCode
				);

			if (data) {
				onConnectionAdded({kind: 'site-template', siteTemplate: data});
				showSuccessMessage(
					sub(
						Liferay.Language.get(
							'site-template-x-was-successfully-connected-to-the-space'
						),
						`<strong>${Liferay.Util.escapeHTML(siteTemplate.name)}</strong>`
					)
				);
			}
			else if (error) {
				showErrorMessage(
					error ||
						Liferay.Language.get(
							'unable-to-connect-site-template-to-space'
						)
				);
			}

			resetSelection();
		}
	};

	return (
		<div className="p-4">
			<div className="align-items-end autofit-row c-gap-3">
				<div className="autofit-col autofit-col-expand">
					<InputGroupWithSelect
						label={Liferay.Language.get('sites')}
						onSelectChange={(value) => {
							setKind(value as ConnectableKind);
							resetSelection();
						}}
						options={[
							{
								label: Liferay.Language.get('sites'),
								value: ConnectableKind.SITES,
							},
							{
								label: Liferay.Language.get('site-templates'),
								value: ConnectableKind.SITE_TEMPLATES,
							},
						]}
						selectValue={kind}
					>
						{kind === ConnectableKind.SITES ? (
							<ItemSelector
								apiURL={`${location.origin}/o/headless-admin-site/v1.0/sites?active=true`}
								id="connectableSelector"
								items={site ? [site] : []}
								onItemsChange={(items: Site[]) => {
									if (items.length) {
										const item = items[0];
										setSite(item);
										setDisableConnectButton(
											isAlreadyConnected(
												item.externalReferenceCode
											)
										);
									}
									else {
										setSite(undefined);
										setDisableConnectButton(true);
									}
								}}
								placeholder={Liferay.Language.get(
									'select-a-site'
								)}
							>
								{(item: Site) => (
									<ItemSelector.Item
										key={item.id}
										textValue={Liferay.Util.escapeHTML(
											item.descriptiveName
										)}
									>
										{Liferay.Util.escapeHTML(
											item.descriptiveName
										)}
									</ItemSelector.Item>
								)}
							</ItemSelector>
						) : (
							<ItemSelector
								apiURL={`${location.origin}/o/headless-admin-site/v1.0/site-templates?active=true`}
								id="connectableSelector"
								items={siteTemplate ? [siteTemplate] : []}
								onItemsChange={(items: SiteTemplate[]) => {
									if (items.length) {
										const item = items[0];
										setSiteTemplate(item);
										setDisableConnectButton(
											isAlreadyConnected(item.id)
										);
									}
									else {
										setSiteTemplate(undefined);
										setDisableConnectButton(true);
									}
								}}
								placeholder={Liferay.Language.get(
									'select-a-site-template'
								)}
							>
								{(item: SiteTemplate) => (
									<ItemSelector.Item
										key={item.id}
										textValue={Liferay.Util.escapeHTML(
											item.name
										)}
									>
										{Liferay.Util.escapeHTML(item.name)}
									</ItemSelector.Item>
								)}
							</ItemSelector>
						)}
					</InputGroupWithSelect>
				</div>

				<div className="autofit-col">
					<ClayButton
						disabled={disableConnectButton}
						onClick={connect}
					>
						{Liferay.Language.get('connect')}
					</ClayButton>
				</div>
			</div>
		</div>
	);
};

export default function SpaceConnectedSitesModal({
	externalReferenceCode,
	hasConnectSitesPermission = true,
}: {
	externalReferenceCode: string;
	hasConnectSitesPermission?: boolean;
}) {
	const [connections, setConnections] = useState<Connection[]>([]);
	const listLabelId = useId();

	useEffect(() => {
		const fetchConnections = async () => {
			const [sitesResult, templatesResult] = await Promise.all([
				ConnectedSiteService.getConnectedSitesFromSpace(
					externalReferenceCode
				),
				SiteTemplateService.getConnectedSiteTemplates(
					externalReferenceCode
				),
			]);

			const next: Connection[] = [];

			if (sitesResult.data) {
				for (const site of sitesResult.data.items) {
					next.push({kind: 'site', site});
				}
			}

			if (templatesResult.data) {
				for (const siteTemplate of templatesResult.data.items) {
					next.push({kind: 'site-template', siteTemplate});
				}
			}

			next.sort((a, b) =>
				getConnectionName(a).localeCompare(getConnectionName(b))
			);

			setConnections(next);
		};

		fetchConnections();
	}, [externalReferenceCode]);

	const onConnectionAdded = (connection: Connection) => {
		setConnections((current) => {
			if (
				current.some(
					(existing) =>
						getConnectionKey(existing) ===
						getConnectionKey(connection)
				)
			) {
				return current;
			}

			const next = [...current, connection];

			next.sort((a, b) =>
				getConnectionName(a).localeCompare(getConnectionName(b))
			);

			return next;
		});
	};

	const onConnectionDisconnected = (connection: Connection) => {
		setConnections((current) =>
			current.filter(
				(existing) =>
					getConnectionKey(existing) !== getConnectionKey(connection)
			)
		);
	};

	const onConnectionChange = (connection: Connection) => {
		setConnections((current) =>
			current.map((existing) =>
				getConnectionKey(existing) === getConnectionKey(connection)
					? connection
					: existing
			)
		);
	};

	return (
		<>
			<ClayModal.Header
				closeButtonAriaLabel={Liferay.Language.get('close')}
			>
				{Liferay.Language.get('all-sites')}
			</ClayModal.Header>

			<ClayModal.Body>
				<p className="c-mb-4 text-secondary">
					{Liferay.Language.get(
						'connect-sites-and-site-templates-to-this-space'
					)}
				</p>

				{hasConnectSitesPermission && (
					<ConnectableSelector
						connections={connections}
						externalReferenceCode={externalReferenceCode}
						onConnectionAdded={onConnectionAdded}
					/>
				)}

				{!connections.length ? (
					<div className="text-center">
						<h2 className="font-weight-semi-bold text-4">
							{Liferay.Language.get('no-sites-are-connected-yet')}
						</h2>
					</div>
				) : (
					<>
						{hasConnectSitesPermission && (
							<label
								className="c-mb-2 c-mt-n2 d-block"
								id={listLabelId}
							>
								{Liferay.Language.get('connected-sites')}
							</label>
						)}

						<ul
							aria-labelledby={listLabelId}
							className="list-unstyled mb-0"
						>
							{connections.map((connection) => (
								<li
									className="align-items-center c-py-2 d-flex font-weight-semi-bold justify-content-between text-3"
									key={getConnectionKey(connection)}
								>
									<div className="align-items-center d-flex">
										<ClaySticker
											className="c-mr-2"
											displayType="secondary"
											shape="circle"
											size="sm"
										>
											{connection.kind === 'site' ? (
												<ClaySticker.Image
													alt=""
													src={connection.site.logo}
												/>
											) : (
												<ClayIcon symbol="cloud" />
											)}
										</ClaySticker>

										{getConnectionLabel(connection)}
									</div>

									{hasConnectSitesPermission && (
										<ConnectableActions
											connection={connection}
											externalReferenceCode={
												externalReferenceCode
											}
											onConnectionChange={
												onConnectionChange
											}
											onConnectionDisconnected={
												onConnectionDisconnected
											}
										/>
									)}
								</li>
							))}
						</ul>
					</>
				)}
			</ClayModal.Body>
		</>
	);
}
