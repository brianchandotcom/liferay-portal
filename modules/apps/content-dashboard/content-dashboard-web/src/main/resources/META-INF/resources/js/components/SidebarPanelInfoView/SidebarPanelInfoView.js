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
import React from 'react';

import Sidebar from '../Sidebar';
import CollapsibleSection from './CollapsibleSection';
import DocumentPreview from './DocumentPreview';
import FileUrlCopyButton from './FileUrlCopyButton';
import ItemLanguages from './ItemLanguages';

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
	categories = [],
	className,
	classPK,
	createDate,
	data = {},
	languageTag = 'en',
	modifiedDate,
	specificFields = {},
	subType,
	tags = [],
	title,
	user,
	versions = [],
	viewURLs = [],
}) => {
	const stickerColor = parseInt(user.userId, 10) % 10;

	const {
		description,
		downloadURL,
		extension,
		fileName,
		previewImageURL,
		previewURL,
		size,
		viewURL,
	} = specificFields;

	const itemDates = [
		{
			text: formatDate(data['display-date']?.value, languageTag),
			title: Liferay.Language.get('display-date'),
		},
		{
			text: formatDate(createDate, languageTag),
			title: Liferay.Language.get('creation-date'),
		},
		{
			text: formatDate(modifiedDate, languageTag),
			title: Liferay.Language.get('modified-date'),
		},
		{
			text: formatDate(data['expiration-date']?.value, languageTag),
			title: Liferay.Language.get('expiration-date'),
		},
		{
			text: formatDate(data['review-date']?.value, languageTag),
			title: Liferay.Language.get('review-date'),
		},
		{
			text: classPK,
			title: Liferay.Language.get('id'),
		},
	];

	const isADocument =
		className === 'com.liferay.portal.kernel.repository.model.FileEntry';

	const documentIsAFile =
		isADocument &&
		!!downloadURL &&
		!!extension &&
		parseInt(size?.split(' ')[0], 10) > 0;

	const documentUsesPreview = !!previewImageURL || documentIsAFile;

	const showTaxonomies = !!categories?.length || !!tags?.length;

	return (
		<>
			<Sidebar.Header title={title} />

			<Sidebar.Body>
				<div className="sidebar-section sidebar-section--compress">
					{documentIsAFile && (
						<>
							<div className="c-mt-1">
								<FileUrlCopyButton url={previewURL} />
							</div>
							<p className="c-mb-1 text-secondary">{fileName}</p>
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

				{documentUsesPreview && (
					<DocumentPreview
						documentSrc={previewImageURL}
						documentTitle={title}
						downloadURL={downloadURL}
						isFile={documentIsAFile}
						viewURL={viewURL}
					/>
				)}

				{description && (
					<div className="sidebar-section">
						<h5 className="c-mb-1 font-weight-semi-bold">
							{Liferay.Language.get('description')}
						</h5>

						<p className="text-secondary">{description}</p>
					</div>
				)}

				<ClayPanel.Group className="panel-group-flush panel-group-sm">
					{showTaxonomies && (
						<CollapsibleSection
							expanded={true}
							title={Liferay.Language.get('categorization')}
						>
							{!!categories.length && (
								<div className="c-mb-4 sidebar-dl sidebar-section">
									<h5 className="c-mb-1 font-weight-semi-bold">
										{Liferay.Language.get('categories')}
									</h5>

									<p>
										{categories.map((category) => (
											<ClayLabel
												displayType="secondary"
												key={category}
												large
											>
												{category}
											</ClayLabel>
										))}
									</p>
								</div>
							)}

							{!!tags.length && (
								<div className="c-mb-4 sidebar-dl sidebar-section">
									<h5 className="c-mb-1 font-weight-semi-bold">
										{Liferay.Language.get('tags')}
									</h5>

									<p>
										{tags.map((tag) => (
											<ClayLabel
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
						{documentIsAFile && (
							<div className="sidebar-section">
								<h5 className="c-mb-1 font-weight-semi-bold">
									{Liferay.Language.get('extension')}
								</h5>

								<p className="text-secondary">{extension}</p>

								<h5 className="c-mb-1 font-weight-semi-bold">
									{Liferay.Language.get('size')}
								</h5>

								<p className="text-secondary">{size}</p>
							</div>
						)}

						{!!itemDates.length &&
							itemDates.map(
								({text, title}) =>
									text &&
									title && (
										<div
											className="c-mb-4 sidebar-dl sidebar-section"
											key={title}
										>
											<h5 className="c-mb-1 font-weight-semi-bold">
												{title}
											</h5>

											<p className="text-secondary">
												{text}
											</p>
										</div>
									)
							)}

						{!!viewURLs.length && !isADocument && (
							<ItemLanguages urls={viewURLs} />
						)}
					</CollapsibleSection>
				</ClayPanel.Group>
			</Sidebar.Body>
		</>
	);
};

export default SidebarPanelInfoView;
