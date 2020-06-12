/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayBadge from '@clayui/badge';
import ClayIcon from '@clayui/icon';
import classNames from 'classNames';
import React from 'react';

const Arrow = () => {
	return (
		<div className="arrow">
			<ClayIcon className="arrow-point icon" symbol="live" />

			<div className="arrow-body">
				<div className="arrow-tail" />
				<ClayIcon className="arrow-head icon" symbol="caret-bottom" />
			</div>
		</div>
	);
};

export default function WorkflowStep({initial, name, onClick, selected}) {
	const isInitialOrFinalSteps = initial !== undefined;
	const isFinalStep = isInitialOrFinalSteps && !initial;

	const badgeLabel = isFinalStep
		? Liferay.Language.get('end')
		: Liferay.Language.get('start');

	return (
		<>
			<div className="step">
				<div
					className={classNames(
						'step-wrapper',
						!isInitialOrFinalSteps && 'no-badge'
					)}
				>
					{isInitialOrFinalSteps && (
						<ClayBadge
							className="step-badge"
							displayType={selected ? 'primary' : 'secondary'}
							label={badgeLabel}
						/>
					)}

					<div
						className={classNames(
							'step-card',
							selected && 'selected'
						)}
						onClick={onClick}
					>
						<div className="step-card-row">
							<div className="autofit-row">{name}</div>
						</div>
					</div>
				</div>
			</div>

			{!isFinalStep && <Arrow />}
		</>
	);
}
