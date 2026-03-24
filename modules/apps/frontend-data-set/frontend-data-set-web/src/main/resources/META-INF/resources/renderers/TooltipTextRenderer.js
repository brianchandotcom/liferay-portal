/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
