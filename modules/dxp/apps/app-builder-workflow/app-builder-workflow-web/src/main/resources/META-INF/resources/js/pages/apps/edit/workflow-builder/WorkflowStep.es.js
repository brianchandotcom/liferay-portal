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
import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import EditAppContext from 'app-builder-web/js/pages/apps/edit/EditAppContext.es';
import classNames from 'classnames';
import {sub} from 'data-engine-js-components-web/js/utils/lang.es';
import React, {useContext, useState} from 'react';

import ButtonInfo from '../../../../components/button-info/ButtonInfo.es';
import MissingRequiredFieldsPopover from '../MissingRequiredFieldsPopover.es';

const Arrow = ({addStep, selected}) => {
	return (
		<div className={classNames('arrow ', selected && 'selected')}>
			<ClayIcon
				className={classNames('arrow-point icon')}
				symbol="live"
			/>

			{selected && (
				<div
					className={classNames('arrow-plus-button')}
					data-tooltip-align="left"
					data-tooltip-delay="0"
					onClick={addStep}
					title={Liferay.Language.get('create-new-step')}
				>
					<ClayIcon className="icon" symbol="plus" />
				</div>
			)}

			<div className="arrow-body">
				<div className="arrow-tail" />

				<ClayIcon
					className={classNames('arrow-head icon')}
					symbol="caret-bottom"
				/>
			</div>
		</div>
	);
};

const Card = ({
	actions,
	errors,
	initial,
	isInitialOrFinalSteps,
	name,
	onClick,
	selected,
	stepInfo,
}) => {
	const {
		appId,
		config: {dataObject, formView},
		openFormViewModal,
		state: {app},
		updateFormView,
	} = useContext(EditAppContext);
	const [active, setActive] = useState(false);
	const [showPopover, setShowPopover] = useState(false);

	const duplicatedFields = errors?.formViews?.duplicatedFields || [];
	const {missingRequiredFields: {customField, nativeField} = {}} = formView;

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
			<div>
				{name}

				<ButtonInfo items={stepInfo} />
			</div>

			{duplicatedFields.length > 0 && (
				<ClayIcon
					className="error tooltip-popover-icon"
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
			)}

			{!app.active && appId && initial && (customField || nativeField) && (
				<MissingRequiredFieldsPopover
					alignPosition="right"
					dataObjectName={dataObject.name}
					message={{
						custom: sub(
							Liferay.Language.get(
								'the-form-view-for-this-app-was-modified-and-does-not-contain-all-required-fields-for-the-x-object'
							),
							[dataObject.name]
						),
						native: sub(
							Liferay.Language.get(
								'the-form-view-for-this-app-was-modified-and-does-not-contain-all-native-required-fields-it-cannot-be-used-to-create-new-records-for-the-x-object'
							),
							[dataObject.name]
						),
					}}
					nativeField={nativeField}
					onClick={() => {
						setShowPopover(false);

						openFormViewModal({
							dataDefinitionId: dataObject.id,
							dataLayoutId: formView.id,
							defaultLanguageId: dataObject.defaultLanguageId,
							selectFormView: updateFormView,
						});
					}}
					setShowPopover={setShowPopover}
					showPopover={showPopover}
					triggerClassName="help-cursor step-card-icon"
				/>
			)}

			<div className="d-flex">
				{!isInitialOrFinalSteps && (
					<ClayDropDown
						active={active}
						data-tooltip-align="bottom"
						data-tooltip-delay="0"
						onActiveChange={setActive}
						title={Liferay.Language.get('options')}
						trigger={
							<ClayButtonWithIcon
								className="border-0"
								displayType="secondary"
								symbol="ellipsis-v"
							/>
						}
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
		</div>
	);
};

export default function WorkflowStep({
	actions,
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
						errors={errors}
						initial={initial}
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
