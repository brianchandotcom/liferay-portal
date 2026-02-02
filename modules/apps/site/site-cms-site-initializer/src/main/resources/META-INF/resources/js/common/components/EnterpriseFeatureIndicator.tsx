/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import ClayPopover from '@clayui/popover';
import {sub} from 'frontend-js-web';
import React, {useRef, useState} from 'react';

type EnterpriseFeatureIndicatorProps = {
	showTooltip?: boolean;
};

export default function EnterpriseFeatureIndicator({
	showTooltip,
}: EnterpriseFeatureIndicatorProps) {
	const [show, setShow] = useState(false);
	const timerRef = useRef<number | null>(null);

	const closePopover = () => {
		setShow(false);
	};

	const startCloseTimer = () => {
		if (timerRef.current) {
			clearTimeout(timerRef.current);
		}
		timerRef.current = window.setTimeout(closePopover, 100);
	};

	const cancelCloseTimer = () => {
		if (timerRef.current) {
			clearTimeout(timerRef.current);
			timerRef.current = null;
		}
	};

	const handleMouseOver = () => {
		cancelCloseTimer();
		setShow(true);
	};

	const handleMouseOut = () => {
		startCloseTimer();
	};

	const Trigger = React.forwardRef<HTMLButtonElement, any>((props, ref) => {
		const styles = !showTooltip ? {cursor: 'default'} : {};

		return (
			<ClayButton {...props} ref={ref} style={styles} translucent>
				<span>Enterprise</span>

				<ClayIcon className="ml-2" symbol="crown" />
			</ClayButton>
		);
	});

	if (!showTooltip) {
		return <Trigger />;
	}

	return (
		<ClayPopover
			alignPosition="bottom"
			className="mw-100"
			disableScroll
			header={Liferay.Language.get('get-more-with-enterprise')}
			onMouseEnter={cancelCloseTimer}
			onMouseLeave={startCloseTimer}
			onShowChange={setShow}
			show={show}
			trigger={
				<Trigger
					onMouseLeave={handleMouseOut}
					onMouseOver={handleMouseOver}
				/>
			}
		>
			<div style={{width: 288}}>
				<p className="mb-0">
					{Liferay.Language.get(
						'this-feature-is-only-available-on-the-enterprise-plan'
					)}

					<ClayLink
						aria-label={sub(
							Liferay.Language.get('x-opens-new-window'),
							Liferay.Language.get('get-enterprise-details')
						)}
						className="ml-1 text-decoration-underline"
						href="#" // TODO: Waiting for link
						rel="noopener noreferrer"
						target="_blank"
					>
						{Liferay.Language.get('get-enterprise-details')}
					</ClayLink>
				</p>
			</div>
		</ClayPopover>
	);
}
