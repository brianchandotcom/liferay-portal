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

import ClayForm from '@clayui/form';
import PropTypes from 'prop-types';
import React from 'react';

import ColorPalette from '../../../common/components/ColorPalette';

const CONTAINER_BACKGROUND_COLOR_IDENTIFIER = 'backgroundColorCssClass';

export const ContainerBackgroundColorConfiguration = ({
	backgroundColor,
	onValueChange
}) => (
	<ClayForm.Group>
		<ColorPalette
			onClear={() =>
				onValueChange({
					[CONTAINER_BACKGROUND_COLOR_IDENTIFIER]: ''
				})
			}
			onColorSelect={value =>
				onValueChange({[CONTAINER_BACKGROUND_COLOR_IDENTIFIER]: value})
			}
			selectedColor={backgroundColor}
		></ColorPalette>
	</ClayForm.Group>
);

ContainerBackgroundColorConfiguration.propTypes = {
	backgroundColor: PropTypes.string,
	onValueChange: PropTypes.func.isRequired
};
