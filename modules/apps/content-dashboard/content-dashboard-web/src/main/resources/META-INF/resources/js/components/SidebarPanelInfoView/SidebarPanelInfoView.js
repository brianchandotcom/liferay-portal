/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import ClaySticker from '@clayui/sticker';
import ClayTabs from '@clayui/tabs';
import classnames from 'classnames';
import PropTypes from 'prop-types';
import React, {useCallback, useState} from 'react';

import Sidebar from '../Sidebar';
import Categorization from './Categorization';
import DetailsContent from './DetailsContent';
import ManageCollaborators from './ManageCollaborators';
import Subscribe from './Subscribe';
import VersionsContent from './VersionsContent';

const SidebarPanelInfoView = ({
	classPK,
	createDate,
	description,
	downloadURL,
	getItemVersionsURL,
	languageTag = 'en',
	latestVersions = [],
	modifiedDate,
	specificFields = {},
	subscribe,
	subType,
	tags = [],
	title,
	type,
	preview,
	fetchSharingButtonURL,
	fetchSharingCollaboratorsURL,
	user,
	viewURLs = [],
	vocabularies = {},
}) => {
	const [activeTabKeyValue, setActiveTabKeyValue] = useState(0);

	const showTabs = !!getItemVersionsURL;

	const [error, setError] = useState(false);

	const stickerColor = parseInt(user.userId, 10) % 10;

	const handleError = useCallback(() => {
		setError(true);
	}, []);

	return (
		<>
			<Sidebar.Header
				actionsSlot={subscribe && <Subscribe {...subscribe} />}
				title={title}
			>
				<ClayLayout.ContentRow>
					<div>
						{error && (
							<ClayAlert
								className="mb-3"
								displayType="warning"
								onClose={() => {
									setError(false);
								}}
								variant="stripe"
							>
								{Liferay.Language.get(
									'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
								)}
							</ClayAlert>
						)}

						<div className="sidebar-section sidebar-section--compress">
							<p
								className="c-mb-1 text-secondary"
								data-qa-id="assetTypeInfo"
							>
								{subType ? `${type} - ${subType}` : `${type}`}
							</p>

							{latestVersions.map((latestVersion) => (
								<div
									className="c-mt-2"
									key={latestVersion.version}
								>
									<ClayLabel displayType="info">
										{Liferay.Language.get('version') + ' '}

										{latestVersion.version}
									</ClayLabel>

									<ClayLabel
										displayType={latestVersion.statusStyle}
									>
										{latestVersion.statusLabel}
									</ClayLabel>
								</div>
							))}
						</div>

						<div className="sidebar-section">
							{fetchSharingCollaboratorsURL ? (
								<ManageCollaborators
									fetchSharingCollaboratorsURL={
										fetchSharingCollaboratorsURL
									}
									onError={handleError}
								/>
							) : (
								<>
									<ClaySticker
										className={classnames(
											'sticker-user-icon',
											{
												[`user-icon-color-${stickerColor}`]: !user.url,
											}
										)}
										shape="circle"
									>
										{user.url ? (
											<img
												alt={`${user.name}.`}
												className="sticker-img"
												src={user.url}
											/>
										) : (
											<ClayIcon symbol="user" />
										)}
									</ClaySticker>
									<span className="c-ml-2 text-secondary">
										{user.name}
									</span>
								</>
							)}
						</div>
					</div>
				</ClayLayout.ContentRow>
			</Sidebar.Header>

			<div className="mb-0 sidebar-section">
				{showTabs && activeTabKeyValue !== null && (
					<ClayTabs modern>
						<ClayTabs.Item
							active={activeTabKeyValue === 0}
							innerProps={{
								'aria-controls': 'details',
							}}
							onClick={() => setActiveTabKeyValue(0)}
						>
							{Liferay.Language.get('details')}
						</ClayTabs.Item>

						<ClayTabs.Item
							active={activeTabKeyValue === 1}
							innerProps={{
								'aria-controls': 'versions',
							}}
							onClick={() => setActiveTabKeyValue(1)}
						>
							{Liferay.Language.get('versions')}
						</ClayTabs.Item>

						<ClayTabs.Item
							active={activeTabKeyValue === 2}
							innerProps={{
								'aria-controls': 'categorization',
							}}
							onClick={() => setActiveTabKeyValue(2)}
						>
							{Liferay.Language.get('categorization')}
						</ClayTabs.Item>
					</ClayTabs>
				)}
			</div>

			<Sidebar.Body>
				<div>
					<ClayTabs.Content activeIndex={activeTabKeyValue} fade>
						<ClayTabs.TabPane
							aria-labelledby="tab-1"
							className="flex-shrink-0"
						>
							<DetailsContent
								classPK={classPK}
								createDate={createDate}
								description={description}
								downloadURL={downloadURL}
								fetchSharingButtonURL={fetchSharingButtonURL}
								languageTag={languageTag}
								modifiedDate={modifiedDate}
								preview={preview}
								specificFields={specificFields}
								title={title}
								viewURLs={viewURLs}
							/>
						</ClayTabs.TabPane>

						{showTabs && activeTabKeyValue === 1 && (
							<ClayTabs.TabPane
								aria-labelledby="tab-2"
								className="flex-shrink-0"
							>
								<VersionsContent
									active={activeTabKeyValue === 1}
									getItemVersionsURL={getItemVersionsURL}
									languageTag={languageTag}
									onError={handleError}
								/>
							</ClayTabs.TabPane>
						)}

						{showTabs && activeTabKeyValue === 2 && (
							<ClayTabs.TabPane
								aria-labelledby="tab-2"
								className="flex-shrink-0"
							>
								<Categorization
									tags={tags}
									vocabularies={vocabularies}
								/>
							</ClayTabs.TabPane>
						)}
					</ClayTabs.Content>
				</div>
			</Sidebar.Body>
		</>
	);
};

SidebarPanelInfoView.defaultProps = {
	description: '',
	languageTag: 'en-US',
	propTypes: [],
	vocabularies: {},
};

SidebarPanelInfoView.propTypes = {
	classPK: PropTypes.string.isRequired,
	createDate: PropTypes.string.isRequired,
	description: PropTypes.string,
	fetchSharingButtonURL: PropTypes.string,
	fetchSharingCollaboratorsURL: PropTypes.string,
	getItemVersionsURL: PropTypes.string,
	latestVersions: PropTypes.array.isRequired,
	modifiedDate: PropTypes.string.isRequired,
	preview: PropTypes.object,
	specificFields: PropTypes.object.isRequired,
	subType: PropTypes.string.isRequired,
	tags: PropTypes.array,
	title: PropTypes.string.isRequired,
	user: PropTypes.object.isRequired,
	viewURLs: PropTypes.array.isRequired,
	vocabularies: PropTypes.object,
};

export default SidebarPanelInfoView;
