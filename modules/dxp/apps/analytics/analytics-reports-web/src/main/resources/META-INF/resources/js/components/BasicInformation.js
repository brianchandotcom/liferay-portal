/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClaySticker from '@clayui/sticker';
import classnames from 'classnames';
import {sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import {StoreStateContext} from '../context/StoreContext';
import Translation from './Translation';

function Author({author: {authorId, name, url}}) {
	return (
		<div className="text-secondary">
			<ClaySticker
				className={classnames('c-mr-2 sticker-user-icon', {
					[`user-icon-color-${parseInt(authorId, 10) % 10}`]: !url,
				})}
				shape="circle"
				size="sm"
			>
				{url ? (
					<img alt={`${name}.`} className="sticker-img" src={url} />
				) : (
					<ClayIcon symbol="user" />
				)}
			</ClaySticker>

			{sub(Liferay.Language.get('authored-by-x'), name)}
		</div>
	);
}

function BasicInformation({
	author,
	canonicalURL,
	onSelectedLanguageClick,
	publishDate,
	title,
	viewURLs,
}) {
	const {languageTag} = useContext(StoreStateContext);

	const formattedPublishDate = Intl.DateTimeFormat(languageTag, {
		day: 'numeric',
		month: 'long',
		year: 'numeric',
	}).format(new Date(publishDate));

	return (
		<div className="sidebar-section">
			<ClayLayout.ContentRow className="mb-2" verticalAlign="center">
				<ClayLayout.ContentCol>
					<div className="inline-item-before">
						<ClayLayout.ContentRow>
							<ClayLayout.ContentCol>
								<Translation
									onSelectedLanguageClick={
										onSelectedLanguageClick
									}
									viewURLs={viewURLs}
								/>
							</ClayLayout.ContentCol>
						</ClayLayout.ContentRow>
					</div>
				</ClayLayout.ContentCol>

				<ClayLayout.ContentCol expand>
					<ClayLayout.ContentRow>
						<span className="font-weight-semi-bold text-truncate-inline">
							<span
								className="text-truncate"
								data-tooltip-align="bottom"
								title={title}
							>
								{title}
							</span>
						</span>
					</ClayLayout.ContentRow>

					<ClayLayout.ContentRow>
						<span
							className="text-truncate text-truncate-reverse"
							data-tooltip-align="bottom"
							title={canonicalURL}
						>
							<bdi className="text-secondary">{canonicalURL}</bdi>
						</span>
					</ClayLayout.ContentRow>
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>

			<ClayLayout.ContentRow className="mb-2">
				<ClayLayout.ContentCol expand>
					<span className="text-secondary">
						{sub(
							Liferay.Language.get('published-on-x'),
							formattedPublishDate
						)}
					</span>
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>

			{author && (
				<ClayLayout.ContentRow>
					<ClayLayout.ContentCol expand>
						<Author author={author} />
					</ClayLayout.ContentCol>
				</ClayLayout.ContentRow>
			)}
		</div>
	);
}

Author.propTypes = {
	author: PropTypes.object.isRequired,
};

BasicInformation.propTypes = {
	author: PropTypes.object,
	canonicalURL: PropTypes.string.isRequired,
	onSelectedLanguageClick: PropTypes.func.isRequired,
	publishDate: PropTypes.string.isRequired,
	title: PropTypes.string.isRequired,
	viewURLs: PropTypes.arrayOf(
		PropTypes.shape({
			default: PropTypes.bool.isRequired,
			languageId: PropTypes.string.isRequired,
			selected: PropTypes.bool.isRequired,
			viewURL: PropTypes.string.isRequired,
		})
	).isRequired,
};

export default BasicInformation;
