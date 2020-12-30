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
}) => (
	<div className="browse-image-controls">
		<div className="drag-drop-label" onClick={(event) => {
			if (event.target.tagName === 'BUTTON') {
				handleClick(event);
			}
		}}>
			{itemSelectorEventName && itemSelectorURL ? (
				Liferay.Browser.isMobile() ? (
					SELECT_FILE_BUTTON
				) : (
					<span
						className="pr-1"
						dangerouslySetInnerHTML={{
							__html: Liferay.Util.sub(
								Liferay.Language.get(
									'drag-and-drop-to-upload-or-x'
								),
								SELECT_FILE_BUTTON
							),
						}}
					></span>
				)
			) : (
				Liferay.Language.get('drag-and-drop-to-upload')
			)}
		</div>
		<div className="file-validation-info">
			{validExtensions && <strong>{validExtensions}</strong>}

			{maxFileSize !== 0 && (
				<span
					className="pl-1"
					dangerouslySetInnerHTML={{
						__html: Liferay.Util.sub(
							Liferay.Language.get('maximum-size-x'),
							Liferay.Util.formatStorage(
								parseInt(maxFileSize, 10)
							)
						),
					}}
				></span>
			)}
		</div>
	</div>
);

export default BrowseImage;