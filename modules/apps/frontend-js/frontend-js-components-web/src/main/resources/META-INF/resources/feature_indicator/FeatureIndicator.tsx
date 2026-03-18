/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayBadge from '@clayui/badge';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import ClayPopover, {ALIGN_POSITIONS} from '@clayui/popover';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {sub} from 'frontend-js-web';
import React, {useState} from 'react';

import useId from '../hooks/useId';
import LearnMessage, {
	LearnResourcesContext,
} from '../learn_message/LearnMessage';

export type Type = 'beta' | 'deprecated' | 'enterprise' | 'maintenance';

type DisplayType = 'info' | 'primary' | 'warning';

type featureIndicatorNoninteractiveProps = {
	interactive?: false;
	learnResourceContext?: any;
	type?: Type;
};

type featureIndicatorInteractiveProps = {
	interactive: true;
	learnResourceContext: any;
	type?: Exclude<Type, 'enterprise'>;
};

type featureIndicatorEnterpriseProps = {
	interactive?: boolean;
	learnResourceContext?: undefined;
	type: Extract<Type, 'enterprise'>;
};

type featureIndicatorProps = (
	| featureIndicatorNoninteractiveProps
	| featureIndicatorInteractiveProps
	| featureIndicatorEnterpriseProps
) & {
	dark?: boolean;
	tooltipAlign?: (typeof ALIGN_POSITIONS)[number];
};

const ENTERPRISE_URL = 'https://www.liferay.com/web/lr/cms-upgrade';

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
	let linkMessage;
	let linkUrl;
	let popoverText = Liferay.Language.get('this-feature-is-in-testing');
	let popoverTitle = Liferay.Language.get('beta-feature');
	let symbol = 'info-circle-open';
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

	if (type === 'enterprise') {
		displayType = 'primary';
		label = Liferay.Language.get('enterprise');
		linkMessage = Liferay.Language.get('get-enterprise-details');
		linkUrl = ENTERPRISE_URL;
		popoverText = Liferay.Language.get(
			'this-feature-is-only-available-on-the-enterprise-subscription'
		);
		popoverTitle = Liferay.Language.get('get-more-with-enterprise');
		symbol = 'crown';
		tooltipTitle = Liferay.Language.get('open-enterprise-definition');
	}

	if (type === 'maintenance') {
		displayType = 'info';
		label = Liferay.Language.get('maintenance');
		learnMessageResourceKey = 'maintenance-mode';
		popoverText = Liferay.Language.get(
			'this-feature-is-in-maintenance-mode'
		);
		popoverTitle = Liferay.Language.get('maintenance-mode');
		symbol = 'info-circle-open';
		tooltipTitle = Liferay.Language.get('open-maintenance-mode-definition');
	}

	return (
		<LearnResourcesContext.Provider value={learnResourceContext}>
			{interactive ? (
				<ClayTooltipProvider>
					<ClayPopover
						closeOnClickOutside
						data-tooltip-align={tooltipAlign}
						disableScroll
						header={popoverTitle}
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
								<span className="inline-item text-uppercase">
									{label}
								</span>

								{symbol && (
									<span className="inline-item inline-item-after ml-2">
										<ClayIcon symbol={symbol} />
									</span>
								)}
							</ClayButton>
						}
					>
						{popoverText + ' '}

						{linkMessage && linkUrl ? (
							<ClayLink
								aria-label={sub(
									Liferay.Language.get('x-opens-new-window'),
									linkMessage
								)}
								className="text-decoration-underline"
								href={linkUrl}
								rel="noopener noreferrer"
								target="_blank"
							>
								{linkMessage}
							</ClayLink>
						) : learnMessageResourceKey ? (
							<LearnMessage
								resource="frontend-js-components-web"
								resourceKey={learnMessageResourceKey}
							/>
						) : null}
					</ClayPopover>
				</ClayTooltipProvider>
			) : (
				<ClayBadge
					className="text-uppercase"
					dark={dark}
					displayType={displayType}
					label={label}
					symbol={symbol}
					translucent
				/>
			)}
		</LearnResourcesContext.Provider>
	);
}
