/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayBadge from '@clayui/badge';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayPopover, {ALIGN_POSITIONS} from '@clayui/popover';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React, {useState} from 'react';

import useId from '../hooks/useId';
import LearnMessage, {
	LearnResourcesContext,
} from '../learn_message/LearnMessage';

export type DisplayType = 'beta' | 'info' | 'warning' | 'deprecated';

export default function FeatureIndicator({
	dark,
	interactive,
	label = Liferay.Language.get('beta'),
	learnMessageResourceKey = 'beta-features',
	learnResourceContext,
	popoverText = Liferay.Language.get('this-feature-is-in-testing'),
	popoverTitle = Liferay.Language.get('beta-feature'),
	symbol = 'info-panel-open',
	tooltipAlign = 'top',
	tooltipTitle = Liferay.Language.get('open-beta-definition'),
	type = 'beta',
}: {
	dark?: boolean;
	interactive?: boolean;
	label?: string;
	learnMessageResourceKey?: string;
	learnResourceContext?: any;
	popoverText?: string;
	popoverTitle?: string;
	symbol?: string;
	tooltipAlign?: typeof ALIGN_POSITIONS[number];
	tooltipTitle?: string;
	type?: DisplayType;
}) {
	const ariaControlsId = useId();

	const [show, setShow] = useState(false);

	if (type === 'beta') {
		type = 'info';
	}
	else if (type === 'deprecated') {
		label = Liferay.Language.get('deprecated');
		learnMessageResourceKey = 'deprecated-features';
		popoverText = Liferay.Language.get('this-feature-is-deprecated');
		popoverTitle = Liferay.Language.get('deprecated-feature');
		symbol = 'warning-full';
		tooltipTitle = Liferay.Language.get('open-deprecated-definition');
		type = 'warning';
	}

	return (
		<LearnResourcesContext.Provider value={learnResourceContext}>
			{interactive ? (
				<ClayTooltipProvider>
					<ClayPopover
						closeOnClickOutside
						data-tooltip-align={tooltipAlign}
						disableScroll
						header={Liferay.Language.get(popoverTitle)}
						id={ariaControlsId}
						onShowChange={setShow}
						role="dialog"
						trigger={
							<ClayButton
								aria-controls={ariaControlsId}
								aria-expanded={show}
								aria-haspopup="dialog"
								dark={dark}
								data-tooltip-align={tooltipAlign}
								displayType={type}
								rounded
								size="xs"
								title={tooltipTitle}
								translucent
							>
								<span className="inline-item">{label}</span>

								{symbol && (
									<span className="inline-item inline-item-after">
										<ClayIcon symbol={symbol} />
									</span>
								)}
							</ClayButton>
						}
					>
						{popoverText}

						<LearnMessage
							className="pl-1"
							resource="frontend-js-components-web"
							resourceKey={learnMessageResourceKey}
						/>
					</ClayPopover>
				</ClayTooltipProvider>
			) : (
				<ClayBadge
					className="text-uppercase"
					dark={dark}
					displayType={type}
					label={label}
					translucent
				/>
			)}
		</LearnResourcesContext.Provider>
	);
}
