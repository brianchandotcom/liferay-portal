/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

export default function Thumbnail({
	active = false,
	adaptiveMediaImageHTMLTag,
	background,
	onClick,
	src,
	title,
}) {
	const cardClasses = classNames(
		'card',
		'card-interactive',
		'card-interactive-primary',
		'overflow-hidden',
		{active}
	);

	return (
		<div className={cardClasses} onClick={onClick} style={{background}}>
			{adaptiveMediaImageHTMLTag ? (
				<div
					className="h-100 w-100"
					dangerouslySetInnerHTML={{
						__html: adaptiveMediaImageHTMLTag,
					}}
				/>
			) : (
				<img alt={title} className="product-img" src={src} />
			)}
		</div>
	);
}

Thumbnail.propTypes = {
	active: PropTypes.bool,
	adaptiveMediaImageHTMLTag: PropTypes.string,
	onClick: PropTypes.func,
	src: PropTypes.string.isRequired,
	title: PropTypes.string.isRequired,
};
