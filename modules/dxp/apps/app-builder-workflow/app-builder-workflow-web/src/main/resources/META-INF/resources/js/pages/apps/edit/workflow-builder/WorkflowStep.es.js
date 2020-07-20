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
import {ClayTooltipProvider} from '@clayui/tooltip';
import classNames from 'classnames';
import React from 'react';

import ButtonInfo from '../../../../components/button-info/ButtonInfo.es';

const Arrow = ({addStep, selected}) => {
	return (
		<div className={classNames('arrow ', selected && 'selected')}>
			<ClayIcon className="arrow-point icon" symbol="live" />

			{selected && (
				<div className="arrow-plus-button" onClick={addStep}>
					<ClayIcon className="icon" symbol="plus" />
				</div>
			)}

			<div className="arrow-body">
				<div className="arrow-tail" />
				<ClayIcon className="arrow-head icon" symbol="caret-bottom" />
			</div>
		</div>
	);
};

export default function WorkflowStep({
	addStep,
	badgeLabel,
	errors,
	initial,
	name,
	onClick,
	selected,
	stepInfo,
}) {
	const isInitialOrFinalSteps = initial !== undefined;
	const isFinalStep = isInitialOrFinalSteps && !initial;
	const duplicatedFields = errors?.formViews?.duplicatedFields || [];

	return (
		<>
			<div className="step">
				<div className="step-wrapper">
					<ClayBadge
						className={classNames(
							'step-badge',
							!isInitialOrFinalSteps && 'index-badge'
						)}
						displayType={selected ? 'primary' : 'secondary'}
						label={badgeLabel}
					/>

					<div
						className={classNames(
							'step-card',
							selected && 'selected'
						)}
						onClick={onClick}
					>
						<div>
							{name}

							<ButtonInfo items={stepInfo} />
						</div>

						{duplicatedFields.length > 0 && (
							<ClayTooltipProvider>
								<ClayIcon
									className="tooltip-icon-error"
									data-tooltip-align="bottom"
									data-tooltip-delay="0"
									fontSize="26px"
									symbol="exclamation-full"
									title={`${Liferay.Language.get(
										'error'
									)}: ${Liferay.Language.get(
										'there-are-form-views-with-duplicated-fields'
									)}`}
								/>
							</ClayTooltipProvider>
						)}
					</div>
				</div>
			</div>

			{!isFinalStep && <Arrow addStep={addStep} selected={selected} />}
		</>
	);
}
