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

import classNames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

const ImageSelector = ({
    draggableImage,
    cropRegion,
    fileEntryId = 0,
    imageURL,
    portletNamespace,
    paramName
}) => {
    return (
        <div className={classNames(
            'drop-zone',
            {'draggable-image': draggableImage !== 'none'},
            {'drop-enabled': fileEntryId == 0},
            'taglib-image-selector',
        )}>
            <input name={`${portletNamespace}${paramName}Id`} type="hidden" value={fileEntryId} />
	        <input name={`${portletNamespace}${paramName}CropRegion`} type="hidden" value={cropRegion} />

            {imageURL && (
                <div class="image-wrapper">
                    <img 
                        alt={Liferay.Language.get('current-image')}
                        className="current-image"
                        id={`${portletNamespace}image`} src={Liferay.Util.escapeHTML(imageURL)}
                    />
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
    paramName: PropTypes.string.isRequired,
    portletNamespace: PropTypes.string.isRequired,
};

export default ImageSelector;