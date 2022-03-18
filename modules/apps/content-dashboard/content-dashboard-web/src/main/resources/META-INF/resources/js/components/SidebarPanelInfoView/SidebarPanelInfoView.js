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

import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayPanel from '@clayui/panel';
import ClaySticker from '@clayui/sticker';
import classnames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

import Sidebar from '../Sidebar';
import CollapsibleSection from './CollapsibleSection';
import FileUrlCopyButton from './FileUrlCopyButton';
import ItemLanguages from './ItemLanguages';
import ItemVocabularies from './ItemVocabularies';
import Preview from './Preview';
import {
	getCategoriesCountFromVocabularies,
	groupVocabulariesBy,
} from './utils/taxonomiesUtils';

const formatDate = (date, languageTag) => {
	return (
		date &&
		languageTag &&
		Intl.DateTimeFormat(languageTag, {
			day: 'numeric',
			hour: 'numeric',
			hour12: true,
			minute: 'numeric',
			month: 'short',
			year: 'numeric',
		}).format(new Date(date))
	);
};

const SidebarPanelInfoView = ({
	classPK,
	createDate,
	description,
	clipboard,
	languageTag = 'en',
	modifiedDate,
	specificFields = {},
	subType,
	tags = [],
	title,
	preview,
	user,
	versions = [],
	viewURLs = [],
	vocabularies = {},
}) => {
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

	const items = Object.values(specificFields);

	const showTaxonomies =
		!!internalCategoriesCount || !!publicCategoriesCount || !!tags?.length;

	const showClipboard = clipboard && Object.keys(clipboard).length !== 0;

	return (
		<>
			<Sidebar.Header title={title} />

			<Sidebar.Body>
				<div className="sidebar-section sidebar-section--compress">
					{showClipboard && (
						<>
							<div className="c-mt-1">
								<FileUrlCopyButton url={clipboard.url} />
							</div>
							<p className="c-mb-1 text-secondary">
								{clipboard.name}
							</p>
						</>
					)}

					<p className="c-mb-1 text-secondary">{subType}</p>

					{versions.map((version) => (
						<div className="c-mt-2" key={version.version}>
							<ClayLabel displayType="info">
								{Liferay.Language.get('version') + ' '}

								{version.version}
							</ClayLabel>

							<ClayLabel displayType={version.statusStyle}>
								{version.statusLabel}
							</ClayLabel>
						</div>
					))}
				</div>

				<div className="sidebar-dl sidebar-section">
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

					<span className="c-ml-2 text-secondary">{user.name}</span>
				</div>

				{preview && preview.url && (
					<Preview
						downloadURL={preview.downloadURL}
						imageURL={preview.imageURL}
						title={title}
						url={preview.url}
					/>
				)}

				{description && (
					<div className="sidebar-section">
						<h5 className="c-mb-1 font-weight-semi-bold">
							{Liferay.Language.get('description')}
						</h5>

						<div
							className="text-secondary"
							dangerouslySetInnerHTML={{__html: description}}
						/>
					</div>
				)}

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

					<CollapsibleSection title={Liferay.Language.get('details')}>
						<div className="sidebar-section">
							{!!items.length &&
								items.map(
									({title, type, value}) =>
										title &&
										value &&
										type && (
											<div
												className="c-mb-4 sidebar-dl sidebar-section"
												key={title}
											>
												<h5 className="c-mb-1 font-weight-semi-bold">
													{title}
												</h5>

												<p className="text-secondary">
													{type === 'Date'
														? formatDate(
																value,
																languageTag
														  )
														: value}
												</p>
											</div>
										)
								)}

							<div
								className="c-mb-4 sidebar-dl sidebar-section"
								key="creation-date"
							>
								<h5 className="c-mb-1 font-weight-semi-bold">
									{Liferay.Language.get('creation-date')}
								</h5>

								<p className="text-secondary">
									{formatDate(createDate, languageTag)}
								</p>
							</div>

							<div
								className="c-mb-4 sidebar-dl sidebar-section"
								key="modified-date"
							>
								<h5 className="c-mb-1 font-weight-semi-bold">
									{Liferay.Language.get('modified-date')}
								</h5>

								<p className="text-secondary">
									{formatDate(modifiedDate, languageTag)}
								</p>
							</div>

							<div
								className="c-mb-4 sidebar-dl sidebar-section"
								key="id"
							>
								<h5 className="c-mb-1 font-weight-semi-bold">
									{Liferay.Language.get('id')}
								</h5>

								<p className="text-secondary">{classPK}</p>
							</div>
						</div>

						{!!viewURLs.length && <ItemLanguages urls={viewURLs} />}
					</CollapsibleSection>
				</ClayPanel.Group>
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
	clipboard: PropTypes.object,
	createDate: PropTypes.string.isRequired,
	description: PropTypes.string,
	modifiedDate: PropTypes.string.isRequired,
	preview: PropTypes.object,
	specificFields: PropTypes.object.isRequired,
	subType: PropTypes.string.isRequired,
	tags: PropTypes.array,
	title: PropTypes.string.isRequired,
	user: PropTypes.object.isRequired,
	versions: PropTypes.array.isRequired,
	viewURLs: PropTypes.array.isRequired,
	vocabularies: PropTypes.object,
};

export default SidebarPanelInfoView;
