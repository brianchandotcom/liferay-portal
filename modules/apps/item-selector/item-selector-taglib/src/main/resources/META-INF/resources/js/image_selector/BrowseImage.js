/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {formatStorage, sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React from 'react';

const SELECT_FILE_BUTTON = `<button class='btn btn-secondary' type='button'>${Liferay.Language.get(
	'select-file'
)}</button>`;

const BrowseImage = ({
	handleClick,
	itemSelectorEventName,
	itemSelectorURL,
	maxFileSize,
	validExtensions,
}) => {
	const isMobile = Liferay.Browser.isMobile();

	return (
		<div className="browse-image-controls">
			<div
				className="drag-drop-label"
				onClick={(event) => {
					if (event.target.tagName === 'BUTTON') {
						handleClick(event);
					}
				}}
			>
				{itemSelectorEventName && itemSelectorURL ? (
					<span
						className={classNames({
							'pr-1': !isMobile,
						})}
						dangerouslySetInnerHTML={{
							__html: isMobile
								? SELECT_FILE_BUTTON
								: sub(
										Liferay.Language.get(
											'drag-and-drop-to-upload-or-x'
										),
										SELECT_FILE_BUTTON
								  ),
						}}
					></span>
				) : (
					Liferay.Language.get('drag-and-drop-to-upload')
				)}
			</div>

			<div className="file-validation-info">
				{validExtensions && <strong>{validExtensions}</strong>}

				{Number(maxFileSize) !== 0 && (
					<span
						className="pl-1"
						dangerouslySetInnerHTML={{
							__html: sub(
								Liferay.Language.get('maximum-size-x'),
								formatStorage(parseInt(maxFileSize, 10))
							),
						}}
					></span>
				)}
			</div>
		</div>
	);
};

BrowseImage.propTypes = {
	handleClick: PropTypes.func,
	itemSelectorEventName: PropTypes.string,
	itemSelectorURL: PropTypes.string,
	maxFileSize: PropTypes.string,
	validExtensions: PropTypes.string.isRequired,
};

export default BrowseImage;
