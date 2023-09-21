/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayPopover, {ALIGN_POSITIONS} from '@clayui/popover';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React, {useState} from 'react';

import useId from '../hooks/useId';
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
							resourceKey="beta-features"
						/>
					</ClayPopover>
				</div>
			</ClayTooltipProvider>
		</LearnResourcesContext.Provider>
	);
}
