/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayLayout from '@clayui/layout';
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
	onSiteChange?: Function;
	onSiteDisconnected?: Function;
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
		}
		else {
			onSiteDisconnected?.({site});
		}
	};

	const changeSearchable = async () => {
		const {data, error} = await SiteService.connectSiteToSpace(
			groupId,
			site.id,
			String(!searchable)
		);

		if (data) {
			onSiteChange?.({site: data});
		}
		else if (error) {
			showErrorMessage(error);
		}
	};

	return (
		<div className="align-items-center d-flex">
			<span className="mr-2 text-secondary">
				{Liferay.Language.get('searchable-content')}:{' '}

				{searchable
					? Liferay.Language.get('yes')
					: Liferay.Language.get('no')}
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
	onSiteConnected?: Function;
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
				onSiteConnected?.({site: data});
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
				setSites(data.items as Site[]);
			}
		};

		fetchSites();
	}, []);

	return (
		<>
			<ClayLayout.Row>
				<ClayLayout.Col size={9}>
					<FieldPicker
						items={sites.map((site) => {
							return {label: site.name, value: site.id};
						})}
						label={Liferay.Language.get('sites')}
						name="siteSelector"
						onSelectionChange={(value: string) => {
							setSiteSelected(value);
						}}
						selectedKey={siteSelected}
						title={Liferay.Language.get('select-a-site')}
					/>
				</ClayLayout.Col>

				<ClayLayout.Col
					className="align-items-center d-flex justify-content-end"
					size={3}
				>
					<ClayButton onClick={connectSiteToSpace}>
						{Liferay.Language.get('connect')}
					</ClayButton>
				</ClayLayout.Col>
			</ClayLayout.Row>

			<hr className="mb-4" />
		</>
	);
};

const EmptyResult = ({
	groupId,
	isAdmin,
	onSiteConnected,
}: {
	groupId: string;
	isAdmin: boolean;
	onSiteConnected?: Function;
}) => {
	return (
		<>
			{isAdmin && (
				<SitesSelector
					groupId={groupId}
					onSiteConnected={onSiteConnected}
				/>
			)}

			<div className="text-center">
				<h2 className="font-weight-semi-bold text-4">
					{Liferay.Language.get('no-sites-are-connected-yet')}
				</h2>

				{isAdmin && (
					<p className="text-3">
						{Liferay.Language.get('connect-sites-to-this-space')}
					</p>
				)}
			</div>
		</>
	);
};

export default function SpaceSitesModal({
	groupId,
	isAdmin = true,
}: {
	groupId: string;
	isAdmin?: boolean;
}) {
	const [connectedSites, setConnectedSites] = useState<Site[]>([]);
	const listLabelId = useId();

	useEffect(() => {
		const fetchConnectedSitesToSpace = async () => {
			const {data} = await SiteService.getConnectedSitesToSpace(groupId);

			if (data) {
				setConnectedSites(data.items as Site[]);
			}
		};

		fetchConnectedSitesToSpace();
	}, [groupId]);

	const onSiteConnected = ({site}: {site: Site}) => {
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

	const onSiteDisconnected = ({site}: {site: Site}) => {
		setConnectedSites((currentConnectedSites) =>
			currentConnectedSites.filter(
				(currentSite) => currentSite.id !== site.id
			)
		);
	};

	const onSiteChange = ({site}: {site: Site}) => {
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

			<ClayModal.Body>
				{!connectedSites.length ? (
					<EmptyResult
						groupId={groupId}
						isAdmin={isAdmin}
						onSiteConnected={onSiteConnected}
					/>
				) : (
					<>
						{isAdmin && (
							<>
								<SitesSelector
									groupId={groupId}
									onSiteConnected={onSiteConnected}
								/>

								<label className="d-block" id={listLabelId}>
									{Liferay.Language.get('connected-sites')}
								</label>
							</>
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

											{isAdmin && (
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
