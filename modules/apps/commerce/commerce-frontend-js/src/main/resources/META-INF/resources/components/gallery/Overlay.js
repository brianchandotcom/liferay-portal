/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React from 'react';

import Arrows from './Arrows';

export default function Overlay({
	adaptiveMediaImageHTMLTag,
	background,
	onClose,
	onNext,
	onPrev,
	src,
	title,
}) {
	return (
		<div className="gallery-overlay" onClick={onClose} style={{background}}>
			{adaptiveMediaImageHTMLTag ? (
				<div
					dangerouslySetInnerHTML={{
						__html: adaptiveMediaImageHTMLTag,
					}}
				/>
			) : (
				<img alt={title} src={src} />
			)}

			<Arrows onNext={onNext} onPrev={onPrev} />
		</div>
	);
}

Overlay.propTypes = {
	adaptiveMediaImageHTMLTag: PropTypes.string,
	onClose: PropTypes.func,
	onNext: PropTypes.func,
	onPrev: PropTypes.func,
	src: PropTypes.string,
	title: PropTypes.string,
};
