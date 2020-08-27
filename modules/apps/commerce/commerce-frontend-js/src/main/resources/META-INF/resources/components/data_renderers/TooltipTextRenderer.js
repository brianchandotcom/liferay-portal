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
import {ClayTooltipProvider} from '@clayui/tooltip';
import Proptypes from 'prop-types';
import React from 'react';

function TooltipText({text}) {
	return (
		<div className="bg-dark">
			<p>{text}</p>
		</div>
	);
}

function TooltipTextRenderer({value}) {
	const {iconSymbol, text} = value;

	return (
		<>
			<ClayTooltipProvider
				contentRenderer={() => <TooltipText text={text} />}
				delay={0}
			>
				<span className="tooltip-provider">
					<ClayIcon symbol={iconSymbol} />
				</span>
			</ClayTooltipProvider>
		</>
	);
}

TooltipTextRenderer.defaultProps = {
	value: {
		iconSymbol: 'info-circle',
		text: 'This is a sample text',
	},
};

TooltipTextRenderer.propTypes = {
	value: Proptypes.shape({
		iconSymbol: Proptypes.string,
		text: Proptypes.string,
	}),
};

export default TooltipTextRenderer;
