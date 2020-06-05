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

import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React from 'react';

import TooltipTextRenderer from './TooltipTextRenderer';

function DefaultRenderer(props) {
	switch (true) {
		case typeof props.value === 'number':
			return <>{props.value}</>;
		case !(props.value instanceof Object):
			return <>{props.value || ''}</>;
		case !!props.value.icon:
			return <ClayIcon symbol={props.value.icon} />;
		case !!props.value.iconSymbol && !!props.value.text:
			return <TooltipTextRenderer value={props.value} />;
		case !!props.value.label:
			return <>{props.value.label}</>;
		default:
			throw new Error(
				`The object ${JSON.stringify(
					props.value
				)} doesn't match the template schema`
			);
	}
}

DefaultRenderer.propTypes = {
	value: PropTypes.oneOfType([
		PropTypes.string,
		PropTypes.number,
		PropTypes.shape({
			label: PropTypes.string,
		}),
		PropTypes.shape({
			icon: PropTypes.string,
		}),
		PropTypes.shape({
			iconSymbol: PropTypes.string,
			text: PropTypes.string,
		}),
	]),
};

export default DefaultRenderer;
