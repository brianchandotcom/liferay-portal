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
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayPanel from '@clayui/panel';
import ClaySticker from '@clayui/sticker';
import classnames from 'classnames';
import PropTypes from 'prop-types';
import React, {useCallback, useState} from 'react';

import Sidebar from '../Sidebar';
import CollapsibleSection from './CollapsibleSection';
import DetailsContent from './DetailsContent';
import ItemVocabularies from './ItemVocabularies';
import ManageCollaborators from './ManageCollaborators';
import PreviewActionsDescriptionSection from './PreviewActionsDescriptionSection';
import Subscribe from './Subscribe';
import formatDate from './utils/formatDate';
import {
	getCategoriesCountFromVocabularies,
	groupVocabulariesBy,
} from './utils/taxonomiesUtils';

const SidebarPanelInfoViewCollapsable = ({
	classPK,
	createDate,
	description,
	downloadURL,
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
	const [error, setError] = useState(false);

	const stickerColor = parseInt(user.userId, 10) % 10;

	const [publicVocabularies, internalVocabularies] = groupVocabulariesBy({
		array: Object.values(vocabularies),
		key: 'isPublic',
		value: true,
	});

	const internalCategoriesCount = getCategoriesCountFromVocabularies(
		internalVocabularies
	);

	const publicCategoriesCount = getCategoriesCountFromVocabularies(
		publicVocabularies
	);

	const showTaxonomies =
		!!internalCategoriesCount || !!publicCategoriesCount || !!tags?.length;

	const handleError = useCallback(() => {
		setError(true);
	}, []);

	return (
		<>
			<Sidebar.Header title={title}>
				{subscribe && <Subscribe {...subscribe} />}
			</Sidebar.Header>

			<Sidebar.Body className="px-0">
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

				<div className="px-3">
					<div className="sidebar-section sidebar-section--compress">
						<p
							className="c-mb-1 text-secondary"
							data-qa-id="assetTypeInfo"
						>
							{subType ? `${type} - ${subType}` : `${type}`}
						</p>

						{latestVersions.map((latestVersion) => (
							<div className="c-mt-2" key={latestVersion.version}>
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

					<div className="sidebar-dl sidebar-section">
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
									className={classnames('sticker-user-icon', {
										[`user-icon-color-${stickerColor}`]: !user.url,
									})}
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

					<PreviewActionsDescriptionSection
						description={description}
						downloadURL={downloadURL}
						fetchSharingButtonURL={fetchSharingButtonURL}
						handleError={handleError}
						preview={preview}
						title={title}
					/>

					<ClayPanel.Group className="panel-group-flush panel-group-sm">
						{showTaxonomies && (
							<CollapsibleSection
								expanded={true}
								title={Liferay.Language.get('categorization')}
							>
								{!!publicCategoriesCount && (
									<ItemVocabularies
										title={Liferay.Language.get(
											'public-categories'
										)}
										vocabularies={publicVocabularies}
									/>
								)}

								{!!internalCategoriesCount && (
									<ItemVocabularies
										cssClassNames="c-mt-4"
										title={Liferay.Language.get(
											'internal-categories'
										)}
										vocabularies={internalVocabularies}
									/>
								)}

								{!!tags.length && (
									<div className="c-mb-4 sidebar-dl sidebar-section">
										<h5 className="c-mb-1 font-weight-semi-bold">
											{Liferay.Language.get('tags')}
										</h5>

										<p>
											{tags.map((tag) => (
												<ClayLabel
													className="c-mb-2 c-mr-2"
													displayType="secondary"
													key={tag}
													large
												>
													{tag}
												</ClayLabel>
											))}
										</p>
									</div>
								)}
							</CollapsibleSection>
						)}

						<DetailsContent
							classPK={classPK}
							createDate={createDate}
							description={description}
							downloadURL={downloadURL}
							fetchSharingButtonURL={fetchSharingButtonURL}
							formatDate={formatDate}
							languageTag={languageTag}
							modifiedDate={modifiedDate}
							preview={preview}
							specificFields={specificFields}
							tags={tags}
							title={title}
							viewURLs={viewURLs}
							vocabularies={vocabularies}
						/>
					</ClayPanel.Group>
				</div>
			</Sidebar.Body>
		</>
	);
};

SidebarPanelInfoViewCollapsable.defaultProps = {
	description: '',
	languageTag: 'en-US',
	propTypes: [],
	vocabularies: {},
};

SidebarPanelInfoViewCollapsable.propTypes = {
	classPK: PropTypes.string.isRequired,
	createDate: PropTypes.string.isRequired,
	description: PropTypes.string,
	fetchSharingButtonURL: PropTypes.string,
	fetchSharingCollaboratorsURL: PropTypes.string,
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

export default SidebarPanelInfoViewCollapsable;
