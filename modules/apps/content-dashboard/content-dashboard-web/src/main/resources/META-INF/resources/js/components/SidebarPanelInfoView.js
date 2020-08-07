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
import ClayLayout from '@clayui/layout';
import ClayLink from '@clayui/link';
import ClaySticker from '@clayui/sticker';
import {ClayTooltipProvider} from '@clayui/tooltip';
import classnames from 'classnames';
import React, {useMemo} from 'react';

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
	userId,
	userName,
	userPortraitURL,
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

	const stickerColor = parseInt(userId, 10) % 10;

	return (
		<>
			<Sidebar.Header title={Liferay.Language.get('content-info')} />

			<Sidebar.Body>
				<div className="c-mb-4">
					<div className="component-title text-truncate-inline">
						<ClayTooltipProvider>
							<span
								className="text-truncate"
								data-tooltip-align="top"
								title={title}
							>
								{title}
							</span>
						</ClayTooltipProvider>
					</div>

					<p className="component-subtitle font-weight-normal">
						{subType}
					</p>

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

				<div className="c-mb-4 sidebar-dl sidebar-section">
					<ClaySticker
						className={classnames('sticker-user-icon', {
							[`user-icon-color-${stickerColor}`]: !userPortraitURL,
						})}
						shape="circle"
					>
						{userPortraitURL ? (
							<img
								alt={`${userName}.`}
								className="sticker-img"
								src={userPortraitURL}
							/>
						) : (
							<ClayIcon symbol="user" />
						)}
					</ClaySticker>
					<span className="c-ml-2 h5">{userName}</span>
				</div>

				{!!sortedViewURLS.length && (
					<div className="c-mb-4 sidebar-dl sidebar-section">
						<h5>
							{Liferay.Language.get('languages-translated-into')}
						</h5>

						{sortedViewURLS.map((language) => (
							<ClayLayout.ContentRow
								key={language.languageId}
								verticalAlign="center"
							>
								<ClayLayout.ContentCol className="inline-item-before">
									<ClayIcon
										className="c-mt-1"
										symbol={language.languageId.toLowerCase()}
									/>
								</ClayLayout.ContentCol>

								<ClayLayout.ContentCol
									expand={!!language.viewURL}
								>
									<ClayLayout.ContentRow
										key={language.languageId}
										verticalAlign="center"
									>
										<ClayLayout.ContentCol className="inline-item-before small">
											{language.languageId}
										</ClayLayout.ContentCol>

										<ClayLayout.ContentCol>
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
										</ClayLayout.ContentCol>
									</ClayLayout.ContentRow>
								</ClayLayout.ContentCol>

								{language.viewURL && (
									<ClayLayout.ContentCol>
										<ClayTooltipProvider>
											<ClayLink
												borderless
												data-tooltip-align="top"
												displayType="secondary"
												href={language.viewURL}
												monospaced
												outline
												title={Liferay.Language.get(
													'view'
												)}
											>
												<ClayIcon symbol="view" />
											</ClayLink>
										</ClayTooltipProvider>
									</ClayLayout.ContentCol>
								)}
							</ClayLayout.ContentRow>
						))}
					</div>
				)}

				{!!tags.length && (
					<div className="c-mb-4 sidebar-dl sidebar-section">
						<h5>{Liferay.Language.get('tags')}</h5>

						<p>
							{tags.map((tag) => (
								<ClayLabel displayType="secondary" key={tag}>
									{tag}
								</ClayLabel>
							))}
						</p>
					</div>
				)}

				{!!categories.length && (
					<div className="c-mb-4 sidebar-dl sidebar-section">
						<h5>{Liferay.Language.get('categories')}</h5>

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

				{[
					{
						text: formatDate(
							data['display-date']?.value,
							languageTag
						),
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
						text: formatDate(
							data['expiration-date']?.value,
							languageTag
						),
						title: Liferay.Language.get('expiration-date'),
					},
					{
						text: formatDate(
							data['review-date']?.value,
							languageTag
						),
						title: Liferay.Language.get('review-date'),
					},
					{
						text: classPK,
						title: Liferay.Language.get('id'),
					},
				].map(
					({text, title}) =>
						text &&
						title && (
							<div
								className="c-mb-4 sidebar-dl sidebar-section"
								key={title}
							>
								<h5>{title}</h5>

								<p>{text}</p>
							</div>
						)
				)}
			</Sidebar.Body>
		</>
	);
};

export default SidebarPanelInfoView;
