/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClaySticker from '@clayui/sticker';
import classnames from 'classnames';
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
			{Liferay.Util.sub(Liferay.Language.get('authored-by-x'), name)}
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
						{Liferay.Util.sub(
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
