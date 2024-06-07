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

export type Type = 'beta' | 'deprecated';

type DisplayType = 'info' | 'warning';

type featureIndicatorNoninteractiveProps = {
	interactive?: false;
	learnResourceContext?: any;
};

type featureIndicatorInteractiveProps = {
	interactive: true;
	learnResourceContext: any;
};

type featureIndicatorProps = (
	| featureIndicatorNoninteractiveProps
	| featureIndicatorInteractiveProps
) & {
	dark?: boolean;
	tooltipAlign?: (typeof ALIGN_POSITIONS)[number];
	type?: Type;
};

export default function FeatureIndicator({
	dark,
	interactive,
	learnResourceContext,
	tooltipAlign = 'top',
	type = 'beta',
}: featureIndicatorProps) {
	const ariaControlsId = useId();

	const [show, setShow] = useState(false);

	let displayType: DisplayType = 'info';
	let label = Liferay.Language.get('beta');
	let learnMessageResourceKey = 'beta-features';
	let popoverText = Liferay.Language.get('this-feature-is-in-testing');
	let popoverTitle = Liferay.Language.get('beta-feature');
	let symbol = 'info-panel-open';
	let tooltipTitle = Liferay.Language.get('open-beta-definition');

	if (type === 'deprecated') {
		displayType = 'warning';
		label = Liferay.Language.get('deprecated');
		learnMessageResourceKey = 'deprecated-features';
		popoverText = Liferay.Language.get('this-feature-is-deprecated');
		popoverTitle = Liferay.Language.get('deprecated-feature');
		symbol = 'warning-full';
		tooltipTitle = Liferay.Language.get('open-deprecated-definition');
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
								displayType={displayType}
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
						{popoverText + ' '}

						<LearnMessage
							resource="frontend-js-components-web"
							resourceKey={learnMessageResourceKey}
						/>
					</ClayPopover>
				</ClayTooltipProvider>
			) : (
				<ClayBadge
					className="text-uppercase"
					dark={dark}
					displayType={displayType}
					label={label}
					translucent
				/>
			)}
		</LearnResourcesContext.Provider>
	);
}
