/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayModal from '@clayui/modal';
import ClaySticker from '@clayui/sticker';
import {openToast} from 'frontend-js-components-web';
import React, {useEffect, useId, useState} from 'react';

import {FieldPicker} from '../../common/components/forms';
import SiteService from '../../common/services/SiteService';
import {Site} from '../../common/types/Site';

const showErrorMessage = (message: string) => {
	openToast({
		message,
		type: 'danger',
	});
};

const SiteActions = ({
	groupId,
	onSiteChange,
	onSiteDisconnected,
	site,
}: {
	groupId: string;
	onSiteChange: (site: Site) => void;
	onSiteDisconnected: (site: Site) => void;
	site: Site;
}) => {
	const {searchable} = site;

	const disconnectSite = async () => {
		const {error} = await SiteService.disconnectSiteFromSpace(
			groupId,
			site.id
		);

		if (error) {
			showErrorMessage(error);

			return;
		}

		onSiteDisconnected?.(site);
	};

	const changeSearchable = async () => {
		const {data, error} = await SiteService.connectSiteToSpace(
			groupId,
			site.id,
			String(!searchable)
		);

		if (data) {
			onSiteChange(data);
		}
		else if (error) {
			showErrorMessage(error);
		}
	};

	const isSearchableLabel = searchable
		? Liferay.Language.get('yes')
		: Liferay.Language.get('no');

	return (
		<div className="align-items-center d-flex">
			<span className="mr-2 text-secondary">
				{`${Liferay.Language.get('searchable-content')}: ${isSearchableLabel}`}
			</span>

			<ClayDropDownWithItems
				items={[
					{
						label: searchable
							? Liferay.Language.get('make-unsearchable')
							: Liferay.Language.get('make-searchable'),
						onClick: changeSearchable,
					},
					{
						label: Liferay.Language.get('disconnect'),
						onClick: disconnectSite,
					},
				]}
				trigger={
					<ClayButtonWithIcon
						aria-label={Liferay.Language.get('site-actions')}
						borderless
						displayType="secondary"
						size="sm"
						symbol="ellipsis-v"
						title={Liferay.Language.get('actions')}
					/>
				}
			/>
		</div>
	);
};

const SitesSelector = ({
	groupId,
	onSiteConnected,
}: {
	groupId: string;
	onSiteConnected: (site: Site) => void;
}) => {
	const [sites, setSites] = useState<Site[]>([]);
	const [siteSelected, setSiteSelected] = useState<string>();

	const connectSiteToSpace = async () => {
		if (siteSelected) {
			const {data, error} = await SiteService.connectSiteToSpace(
				groupId,
				siteSelected
			);

			if (data) {
				onSiteConnected(data);
			}
			else if (error) {
				showErrorMessage(
					error ||
						Liferay.Language.get('unable-to-connect-site-to-space')
				);
			}
		}
	};

	useEffect(() => {
		const fetchSites = async () => {
			const {data} = await SiteService.getAllSites();

			if (data) {
				setSites(data.items);
			}
		};

		fetchSites();
	}, []);

	return (
		<div className="pt-4 px-4">
			<div className="align-items-center autofit-row c-gap-3">
				<div className="autofit-col autofit-col-expand">
					<FieldPicker
						items={sites.map((site) => {
							return {label: site.name, value: site.id};
						})}
						label={Liferay.Language.get('site')}
						name="siteSelector"
						onSelectionChange={(value: string) => {
							setSiteSelected(value);
						}}
						selectedKey={siteSelected}
						title={Liferay.Language.get('select-a-site')}
					/>
				</div>

				<div className="autofit-col">
					<ClayButton onClick={connectSiteToSpace}>
						{Liferay.Language.get('connect')}
					</ClayButton>
				</div>
			</div>
		</div>
	);
};

export default function SpaceSitesModal({
	groupId,
	hasConnectSitesPermission = true,
}: {
	groupId: string;
	hasConnectSitesPermission?: boolean;
}) {
	const [connectedSites, setConnectedSites] = useState<Site[]>([]);
	const listLabelId = useId();

	useEffect(() => {
		const fetchConnectedSitesToSpace = async () => {
			const {data} =
				await SiteService.getConnectedSitesFromSpace(groupId);

			if (data) {
				setConnectedSites(data.items);
			}
		};

		fetchConnectedSitesToSpace();
	}, [groupId]);

	const onSiteConnected = (site: Site) => {
		setConnectedSites((currentConnectedSites) => {
			if (
				currentConnectedSites.some(
					(prevSite) => prevSite.id === site.id
				)
			) {
				return currentConnectedSites;
			}

			return [...currentConnectedSites, site];
		});
	};

	const onSiteDisconnected = (site: Site) => {
		setConnectedSites((currentConnectedSites) =>
			currentConnectedSites.filter(
				(currentSite) => currentSite.id !== site.id
			)
		);
	};

	const onSiteChange = (site: Site) => {
		setConnectedSites((currentConnectedSites) =>
			currentConnectedSites.map((currentSite) =>
				currentSite.id === site.id ? site : currentSite
			)
		);
	};

	return (
		<>
			<ClayModal.Header>
				{Liferay.Language.get('all-sites')}
			</ClayModal.Header>

			{hasConnectSitesPermission && (
				<ClayModal.Item>
					<SitesSelector
						groupId={groupId}
						onSiteConnected={onSiteConnected}
					/>
				</ClayModal.Item>
			)}

			<ClayModal.Body>
				{!connectedSites.length ? (
					<div className="text-center">
						<h2 className="font-weight-semi-bold text-4">
							{Liferay.Language.get('no-sites-are-connected-yet')}
						</h2>

						{hasConnectSitesPermission && (
							<p className="text-3">
								{Liferay.Language.get(
									'connect-sites-to-this-space'
								)}
							</p>
						)}
					</div>
				) : (
					<>
						{hasConnectSitesPermission && (
							<label className="d-block" id={listLabelId}>
								{Liferay.Language.get('connected-sites')}
							</label>
						)}

						<ul
							aria-labelledby={listLabelId}
							className="list-unstyled mb-0"
						>
							{connectedSites.map((site) => {
								return (
									<li className="mb-2" key={site.id}>
										<div className="align-items-center d-flex justify-content-between">
											<div className="align-items-center d-flex">
												<ClaySticker
													className="c-mr-2"
													displayType="secondary"
													shape="circle"
													size="lg"
												>
													<ClaySticker.Image
														alt={site.name}
														src={site.logo}
													/>
												</ClaySticker>

												{site.name}
											</div>

											{hasConnectSitesPermission && (
												<SiteActions
													groupId={groupId}
													onSiteChange={onSiteChange}
													onSiteDisconnected={
														onSiteDisconnected
													}
													site={site}
												/>
											)}
										</div>
									</li>
								);
							})}
						</ul>
					</>
				)}
			</ClayModal.Body>
		</>
	);
}
