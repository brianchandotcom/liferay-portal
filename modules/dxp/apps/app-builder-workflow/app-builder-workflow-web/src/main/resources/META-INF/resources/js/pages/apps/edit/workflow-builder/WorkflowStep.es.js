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
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, {useState} from 'react';

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

const Card = ({
	actions,
	isInitialOrFinalSteps,
	name,
	onClick,
	selected,
	stepInfo,
}) => {
	const [active, setActive] = useState(false);

	const handleOnClick = (event, onClick) => {
		event.preventDefault();
		setActive(false);
		onClick();
	};

	return (
		<div
			className={classNames('step-card', selected && 'selected')}
			onClick={onClick}
		>
			{name}

			<ButtonInfo items={stepInfo} />

			{!isInitialOrFinalSteps && (
				<ClayDropDown
					active={active}
					onActiveChange={setActive}
					trigger={<ClayIcon symbol="ellipsis-v" />}
				>
					<ClayDropDown.ItemList>
						{actions.map(({label, onClick}, index) => (
							<ClayDropDown.Item
								key={index}
								onClick={(event) =>
									handleOnClick(event, onClick)
								}
							>
								{label}
							</ClayDropDown.Item>
						))}
					</ClayDropDown.ItemList>
				</ClayDropDown>
			)}
		</div>
	);
};

export default function WorkflowStep({
	actions,
	addStep,
	badgeLabel,
	initial,
	name,
	onClick,
	selected,
	stepInfo,
}) {
	const isInitialOrFinalSteps = initial !== undefined;
	const isFinalStep = isInitialOrFinalSteps && !initial;

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

					<Card
						actions={actions}
						isInitialOrFinalSteps={isInitialOrFinalSteps}
						name={name}
						onClick={onClick}
						selected={selected}
						stepInfo={stepInfo}
					/>
				</div>
			</div>

			{!isFinalStep && <Arrow addStep={addStep} selected={selected} />}
		</>
	);
}
