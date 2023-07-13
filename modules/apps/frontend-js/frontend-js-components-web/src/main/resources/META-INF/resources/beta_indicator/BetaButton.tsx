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
import ClayPopover, {ALIGN_POSITIONS} from '@clayui/popover';
import {useId} from '@clayui/shared';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React, {useState} from 'react';

import LearnMessage, {
	LearnResourcesContext,
} from '../learn_message/LearnMessage';
import BetaBadge, {betaClassNames} from './BetaBadge';

export default function BetaButton({
	learnResourceContext,
	tooltipAlign = 'top',
}: {
	learnResourceContext: object;
	tooltipAlign: typeof ALIGN_POSITIONS[number];
}) {
	const ariaControlsId = useId();

	const [show, setShow] = useState(false);

	return (
		<LearnResourcesContext.Provider value={learnResourceContext}>
			<ClayTooltipProvider>
				<div
					className="tooltip-container"
					data-tooltip-align={tooltipAlign}
					title={Liferay.Language.get('open-beta-definition')}
				>
					<ClayPopover
						closeOnClickOutside
						data-tooltip-align={tooltipAlign}
						disableScroll
						header={Liferay.Language.get('beta-feature')}
						id={ariaControlsId}
						onShowChange={setShow}
						role="dialog"
						trigger={
							<ClayButton
								aria-controls={ariaControlsId}
								aria-expanded={show}
								aria-haspopup="dialog"
								className={betaClassNames}
							>
								<BetaBadge standalone={false} />

								<span className="inline-item inline-item-after">
									<ClayIcon symbol="info-panel-open" />
								</span>
							</ClayButton>
						}
					>
						{Liferay.Language.get('this-feature-is-in-testing')}

						<LearnMessage
							className="pl-1"
							resource="frontend-js-components-web"
							resourceKey="beta-features-page"
						/>
					</ClayPopover>
				</div>
			</ClayTooltipProvider>
		</LearnResourcesContext.Provider>
	);
}
