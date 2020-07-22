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
import ClayLink from '@clayui/link';
import ClaySticker from '@clayui/sticker';
import ClayTabs from '@clayui/tabs';
import React, {useMemo, useState} from 'react';

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
	subType,
	tags = [],
	title,
	userName,
	userPortraitURL,
	versions = [],
	viewURLs = [],
}) => {
	const [activeTabKeyValue, setActiveTabKeyValue] = useState(0);

	const formattedCreateDate = useMemo(
		() => formatDate(createDate, languageTag),
		[createDate, languageTag]
	);

	const formattedDisplayDate = useMemo(
		() => formatDate(data['display-date']?.value, languageTag),
		[data, languageTag]
	);

	const formattedExpirationDate = useMemo(
		() => formatDate(data['expiration-date']?.value, languageTag),
		[data, languageTag]
	);

	const formattedModifiedDate = useMemo(
		() => formatDate(modifiedDate, languageTag),
		[modifiedDate, languageTag]
	);

	const formattedReviewDate = useMemo(
		() => formatDate(data['review-date']?.value, languageTag),
		[data, languageTag]
	);

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

	return (
		<>
			<Sidebar.Header subtitle={subType} title={title}>
				{versions.map((version) => (
					<div key={version}>
						<ClayLabel displayType="info">
							{`${Liferay.Language.get('version')} ${
								version.version
							}`}
						</ClayLabel>

						<ClayLabel displayType={version.statusStyle}>
							{version.statusLabel}
						</ClayLabel>
					</div>
				))}
			</Sidebar.Header>

			<Sidebar.Body>
				<ClayTabs modern>
					<ClayTabs.Item
						active={activeTabKeyValue === 0}
						innerProps={{
							'aria-controls': 'tabpanel-0',
						}}
						onClick={() => setActiveTabKeyValue(0)}
					>
						{Liferay.Language.get('details')}
					</ClayTabs.Item>
				</ClayTabs>

				<ClayTabs.Content activeIndex={activeTabKeyValue} fade>
					<ClayTabs.TabPane aria-labelledby="tab-1" className="mt-3">
						<div className="mb-4 sidebar-dl sidebar-section">
							<ClaySticker className="sticker-user-icon">
								<div className="sticker-overlay">
									<img
										className="sticker-img"
										src={userPortraitURL}
									/>
								</div>
							</ClaySticker>

							<span className="h5 ml-2">{userName}</span>
						</div>

						{!!sortedViewURLS.length && (
							<div className="mb-4 sidebar-dl sidebar-section">
								<p className="h5 mb-3">
									{Liferay.Language.get(
										'languages-translated-into'
									)}
								</p>

								{sortedViewURLS.map((language) => (
									<div
										className="autofit-row autofit-row-center mb-1"
										key={language.languageId}
									>
										<div className="autofit-col inline-item-before">
											<ClayIcon
												symbol={language.languageId.toLowerCase()}
											/>
										</div>

										<div className="autofit-col autofit-col-expand">
											<div className="autofit-row autofit-row-center">
												<div className="autofit-col inline-item-before small">
													{language.languageId}
												</div>

												<div className="autofit-col">
													{language.default && (
														<ClayLabel
															className="d-inline"
															displayType="info"
														>
															{Liferay.Language.get(
																'default'
															)}
														</ClayLabel>
													)}
												</div>
											</div>
										</div>

										<div className="autofit-col">
											<ClayLink
												borderless
												displayType="secondary"
												href={language.viewURL}
												monospaced
												outline
											>
												<ClayIcon symbol="view" />
											</ClayLink>
										</div>
									</div>
								))}
							</div>
						)}

						{!!tags.length && (
							<div className="mb-4 sidebar-dl sidebar-section">
								<p className="h5">
									{Liferay.Language.get('tags')}
								</p>

								<p>
									{tags.map((tag) => (
										<ClayLabel
											displayType="secondary"
											key={tag}
										>
											{tag}
										</ClayLabel>
									))}
								</p>
							</div>
						)}

						{!!categories.length && (
							<div className="mb-4 sidebar-dl sidebar-section">
								<p className="h5">
									{Liferay.Language.get('categories')}
								</p>

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

						{formattedDisplayDate && (
							<div className="mb-4 sidebar-dl sidebar-section">
								<p className="h5">
									{Liferay.Language.get('display-date')}
								</p>

								<p>{formattedDisplayDate}</p>
							</div>
						)}

						{formattedCreateDate && (
							<div className="mb-4 sidebar-dl sidebar-section">
								<p className="h5">
									{Liferay.Language.get('creation-date')}
								</p>

								<p>{formattedCreateDate}</p>
							</div>
						)}

						{formattedModifiedDate && (
							<div className="mb-4 sidebar-dl sidebar-section">
								<p className="h5">
									{Liferay.Language.get('modified-date')}
								</p>

								<p>{formattedModifiedDate}</p>
							</div>
						)}

						{formattedExpirationDate && (
							<div className="mb-4 sidebar-dl sidebar-section">
								<p className="h5">
									{Liferay.Language.get('expiration-date')}
								</p>

								<p>{formattedExpirationDate}</p>
							</div>
						)}

						{formattedReviewDate && (
							<div className="mb-4 sidebar-dl sidebar-section">
								<p className="h5">
									{Liferay.Language.get('review-date')}
								</p>

								<p>{formattedReviewDate}</p>
							</div>
						)}

						{classPK && (
							<div className="mb-4 sidebar-dl sidebar-section">
								<p className="h5">
									{Liferay.Language.get('id')}
								</p>

								<p>{classPK}</p>
							</div>
						)}
					</ClayTabs.TabPane>
				</ClayTabs.Content>
			</Sidebar.Body>
		</>
	);
};

export default SidebarPanelInfoView;
