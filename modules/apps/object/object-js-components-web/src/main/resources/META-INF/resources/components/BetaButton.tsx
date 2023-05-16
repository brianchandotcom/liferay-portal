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
import ClayIcon from '@clayui/icon';
import ClayPopover from '@clayui/popover';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React from 'react';

export function BetaButton() {
	return (
		<ClayTooltipProvider>
			<div
				className="tooltip-container"
				data-tooltip-align="top"
				title={Liferay.Language.get('open-beta-definition')}
			>
				<ClayPopover
					alignPosition="top"
					disableScroll
					header={Liferay.Language.get('beta-feature')}
					show={true}
					trigger={
						<ClayButton
							className="rounded-circle"
							displayType="beta"
							size="xs"
						>
							<span className="inline-item">Beta</span>

							<span className="inline-item inline-item-after">
								<ClayIcon symbol="info-panel-open" />
							</span>
						</ClayButton>
					}
				>
					{Liferay.Language.get('this-feature-is-in-testing')}
				</ClayPopover>
			</div>
		</ClayTooltipProvider>
	);
}
