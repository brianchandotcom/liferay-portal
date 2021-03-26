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

function Author({author: {authorId, name, url}}) {
	return (
		<div className="c-mt-3 text-secondary">
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

function BasicInformation({author, canonicalURL, publishDate, title}) {
	const {languageTag} = useContext(StoreStateContext);

	const formattedPublishDate = Intl.DateTimeFormat(languageTag, {
		day: 'numeric',
		month: 'long',
		year: 'numeric',
	}).format(new Date(publishDate));

	return (
		<div className="sidebar-section">
			<ClayLayout.ContentRow>
				<span
					className="component-title text-truncate-inline"
					data-tooltip-align="bottom"
					title={title}
				>
					<span className="text-truncate">{title}</span>
				</span>
			</ClayLayout.ContentRow>

			<ClayLayout.ContentRow>
				<span
					className="c-mb-2 c-mt-1 text-truncate text-truncate-reverse"
					data-tooltip-align="bottom"
					title={canonicalURL}
				>
					<bdi className="text-secondary">{canonicalURL}</bdi>
				</span>
			</ClayLayout.ContentRow>

			<ClayLayout.ContentRow>
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
	publishDate: PropTypes.string.isRequired,
	title: PropTypes.string.isRequired,
};

export default BasicInformation;
