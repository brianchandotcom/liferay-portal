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

import ClayButton from '@clayui/button';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

const SelectFileButton = ({handleClick}) => (
	<ClayButton displayType="secondary" onClick={handleClick}>
		{Liferay.Language.get('select-file')}
	</ClayButton>
);

const ImageSelector = ({
	draggableImage,
	cropRegion,
	fileEntryId = 0,
	imageURL,
	itemSelectorEventName,
	itemSelectorURL,
	maxFileSize = Liferay.PropsValues.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE,
	portletNamespace,
	paramName,
	validExtensions,
}) => {
	const handleSelectFileClick = () => {
		console.log('¡');
	};

	return (
		<div
			className={classNames(
				'drop-zone',
				{'draggable-image': draggableImage !== 'none'},
				{'drop-enabled': fileEntryId == 0},
				'taglib-image-selector'
			)}
		>
			<input
				name={`${portletNamespace}${paramName}Id`}
				type="hidden"
				value={fileEntryId}
			/>
			<input
				name={`${portletNamespace}${paramName}CropRegion`}
				type="hidden"
				value={cropRegion}
			/>

			{imageURL && (
				<div className="image-wrapper">
					<img
						alt={Liferay.Language.get('current-image')}
						className="current-image"
						id={`${portletNamespace}image`}
						src={imageURL}
					/>
				</div>
			)}

			{fileEntryId == 0 && (
				<div className="browse-image-controls">
					<div className="drag-drop-label">
						{itemSelectorEventName && itemSelectorURL ? (
							Liferay.Browser.isMobile() ? (
								<SelectFileButton
									handleClick={handleSelectFileClick}
								/>
							) : (
								<>
									<span
										className="pr-1"
										dangerouslySetInnerHTML={{
											__html: Liferay.Language.get(
												'drag-and-drop-to-upload-or'
											),
										}}
									></span>
									<SelectFileButton
										handleClick={handleSelectFileClick}
									/>
								</>
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
			)}
		</div>
	);
};

ImageSelector.propTypes = {
	cropRegion: PropTypes.string,
	draggableImage: PropTypes.string,
	fileEntryId: PropTypes.string.isRequired,
	imageURL: PropTypes.string,
	itemSelectorEventName: PropTypes.string,
	itemSelectorURL: PropTypes.string,
	maxFileSize: PropTypes.number,
	paramName: PropTypes.string.isRequired,
	portletNamespace: PropTypes.string.isRequired,
	validExtensions: PropTypes.string,
};

export default ImageSelector;
