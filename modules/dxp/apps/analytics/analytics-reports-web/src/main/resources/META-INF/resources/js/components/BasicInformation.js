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
import {ClayTooltipProvider} from '@clayui/tooltip';
import classnames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

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
	languageTag,
	publishDate,
	title,
}) {
	const formattedPublishDate = Intl.DateTimeFormat(languageTag, {
		day: 'numeric',
		month: 'long',
		year: 'numeric',
	}).format(new Date(publishDate));

	return (
		<div className="sidebar-section">
			<ClayLayout.ContentRow>
				<ClayLayout.ContentCol expand>
					<ClayTooltipProvider>
						<span
							className="component-title text-truncate-inline"
							data-tooltip-align="top"
							title={title}
						>
							<span className="text-truncate">{title}</span>
						</span>
					</ClayTooltipProvider>
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>

			<ClayLayout.ContentRow>
				<ClayLayout.ContentCol expand>
					<ClayTooltipProvider>
						<span
							className="text-truncate-inline"
							data-tooltip-align="top"
							title={canonicalURL}
						>
							<span className="c-mb-2 c-mt-1 text-secondary text-truncate text-truncate-reverse">
								{canonicalURL}
							</span>
						</span>
					</ClayTooltipProvider>
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>

			<ClayLayout.ContentRow>
				<ClayLayout.ContentCol expand>
					<p className="text-secondary">
						{Liferay.Util.sub(
							Liferay.Language.get('published-on-x'),
							formattedPublishDate
						)}
					</p>
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>

			<ClayLayout.ContentRow>
				<ClayLayout.ContentCol expand>
					<Author author={author} />
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>
		</div>
	);
}

Author.propTypes = {
	author: PropTypes.object.isRequired,
};

BasicInformation.propTypes = {
	author: PropTypes.object.isRequired,
	canonicalURL: PropTypes.string.isRequired,
	languageTag: PropTypes.string.isRequired,
	publishDate: PropTypes.string.isRequired,
	title: PropTypes.string.isRequired,
};

export default BasicInformation;
