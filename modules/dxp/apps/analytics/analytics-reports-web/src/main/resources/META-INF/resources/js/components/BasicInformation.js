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
import ClaySticker from '@clayui/sticker';
import PropTypes from 'prop-types';
import React from 'react';

function Author({authorName}) {
	return (
		<div className="mt-2 text-secondary">
			<ClaySticker
				className="mr-2 sticker-user-icon"
				inline={true}
				size="sm"
			>
				<ClayIcon symbol="user" />
			</ClaySticker>
			{Liferay.Util.sub(
				Liferay.Language.get('authored-by-x'),
				authorName
			)}
		</div>
	);
}

function BasicInformation({authorName, languageTag, publishDate, title}) {
	const formattedPublishDate = Intl.DateTimeFormat(languageTag, {
		day: 'numeric',
		month: 'long',
		year: 'numeric',
	}).format(publishDate);

	return (
		<div>
			<h4 className="mb-2">{title}</h4>
			<span className="text-secondary">
				{Liferay.Util.sub(
					Liferay.Language.get('published-on-x'),
					formattedPublishDate
				)}
			</span>
			<Author authorName={authorName} />
		</div>
	);
}

Author.propTypes = {
	authorName: PropTypes.string.isRequired,
};

BasicInformation.propTypes = {
	authorName: PropTypes.string.isRequired,
	publishDate: PropTypes.number.isRequired,
	title: PropTypes.string.isRequired,
};

export default BasicInformation;
