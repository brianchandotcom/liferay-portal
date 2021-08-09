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
import ClaySticker from '@clayui/sticker';
import classnames from 'classnames';
import React, {useMemo} from 'react';

import DocumentLanguages from './DocumentLanguages';
import DocumentPreview from './DocumentPreview';
import Sidebar from './Sidebar';

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
	const sortedViewURLS = useMemo(
		() =>
			viewURLs
				.sort((a, b) => {
					if (a.languageId < b.languageId) {
						return -1;
					}

					if (a.languageId > b.languageId) {
						return 1;
					}

					return 0;
				})
				.sort((a) => {
					if (a.default) {
						return -1;
					}

					return 0;
				}),
		[viewURLs]
	);

	const stickerColor = parseInt(user.userId, 10) % 10;
	const itemIsFile = subType === 'Basic Document';

	const {description, downloadURL, fileName, preview} = specificFields;

	const documentDates = [
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

	return (
		<>
			<Sidebar.Header title={title} />

			<Sidebar.Body>
				<div className="sidebar-section">
					{itemIsFile && (
						<p className="mb-2 text-secondary">{fileName}</p>
					)}

					<p className="text-secondary">{subType}</p>

					{versions.map((version) => (
						<div key={version.version}>
							<ClayLabel displayType="info">
								{Liferay.Language.get('version')}{' '}
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
					<span className="ml-2 text-secondary">{user.name}</span>
				</div>

				{preview && (
					<DocumentPreview
						documentSrc={preview}
						documentTitle={title}
						downloadURL={downloadURL}
					/>
				)}

				{description && (
					<div className="sidebar-section">
						<h5 className="font-weight-semi-bold">
							{Liferay.Language.get('description')}
						</h5>
						<p className="text-secondary">{description}</p>
					</div>
				)}

				{!!categories.length && (
					<div className="c-mb-4 sidebar-dl sidebar-section">
						<h5 className="font-weight-semi-bold">
							{Liferay.Language.get('categories')}
						</h5>

						<p>
							{categories.map((category) => (
								<ClayLabel
									displayType="secondary"
									key={category}
								>
									{category}
								</ClayLabel>
							))}
						</p>
					</div>
				)}

				{!!tags.length && (
					<div className="c-mb-4 sidebar-dl sidebar-section">
						<h5 className="font-weight-semi-bold">
							{Liferay.Language.get('tags')}
						</h5>

						<p>
							{tags.map((tag) => (
								<ClayLabel displayType="secondary" key={tag}>
									{tag}
								</ClayLabel>
							))}
						</p>
					</div>
				)}

				{!!sortedViewURLS.length && (
					<DocumentLanguages urls={sortedViewURLS} />
				)}

				{documentDates.map(
					({text, title}) =>
						text &&
						title && (
							<div
								className="c-mb-4 sidebar-dl sidebar-section"
								key={title}
							>
								<h5 className="font-weight-semi-bold">
									{title}
								</h5>

								<p>{text}</p>
							</div>
						)
				)}
			</Sidebar.Body>
		</>
	);
};

export default SidebarPanelInfoView;
