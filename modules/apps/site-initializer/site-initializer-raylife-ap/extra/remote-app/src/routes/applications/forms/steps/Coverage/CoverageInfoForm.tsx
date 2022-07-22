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

import {ClaySelect} from '@clayui/form';
import classNames from 'classnames';

import {
	bodilyInjuryOptions,
	collisionOptions,
	comprehensiveOptions,
	medicalOptions,
	propertyDamageOptions,
	underinsuredInjuredOptions,
	uninsuredDamageOptions,
} from './SelectOptions';

const CoverageInfoForm = () => {
	return (
		<div className="container m-0 p-4">
			<p className="font-weight-bolder mb-4 mt-0 text-paragraph-sm text-uppercase">
				choose your coverages
			</p>

			<hr />

			<div className="font-weight-bolder mb-4 mt-0 text-paragraph-sm text-uppercase">
				<u>Policy Coverages</u>
			</div>

			<div className="row">
				<div
					className={classNames(
						'col-sm filled form-condensed form-group'
					)}
				>
					<ClaySelect
						aria-label="Select Label"
						className="text-left"
						id="mySelectId"
					>
						{bodilyInjuryOptions.map(
							(bodilyInjuryOption, index) => (
								<ClaySelect.Option
									className="font-weight-bold text-center text-primary"
									key={index}
									label={bodilyInjuryOption.label}
									value={bodilyInjuryOption.value}
								/>
							)
						)}
					</ClaySelect>

					<label htmlFor="mySelectId">
						Bodily Injury&nbsp;
						<span className="text-danger-darken-1">*</span>
					</label>
				</div>

				<div
					className={classNames(
						'col-sm filled form-condensed form-group'
					)}
				>
					<ClaySelect aria-label="Select Label" id="propertyDamage">
						{propertyDamageOptions.map(
							(propertyDamageOption, index) => (
								<ClaySelect.Option
									className="font-weight-bold text-center text-primary"
									key={index}
									label={propertyDamageOption.label}
									value={propertyDamageOption.value}
								/>
							)
						)}
						;
					</ClaySelect>

					<label htmlFor="propertyDamage">
						Property Damage&nbsp;
						<span className="text-danger-darken-1">*</span>
					</label>
				</div>
			</div>

			<div className="row">
				<div className="col-sm filled form-condensed form-group">
					<ClaySelect
						aria-label="Select Label"
						id="underinsuredInjured"
					>
						{underinsuredInjuredOptions.map(
							(underinsuredInjuredOption, index) => (
								<ClaySelect.Option
									className="font-weight-bold text-center text-primary"
									key={index}
									label={underinsuredInjuredOption.label}
									value={underinsuredInjuredOption.value}
								/>
							)
						)}
					</ClaySelect>

					<label htmlFor="underinsuredInjured">
						Uninsured / Underinsured Motorist Bodily Injured&nbsp;
						<span className="text-danger-darken-1">*</span>
					</label>
				</div>

				<div className="col-sm filled form-condensed form-group">
					<ClaySelect aria-label="Select Label" id="UninsuredDamage">
						{uninsuredDamageOptions.map(
							(uninsuredDamageOption, index) => (
								<ClaySelect.Option
									className="font-weight-bold text-center text-primary"
									key={index}
									label={uninsuredDamageOption.label}
									value={uninsuredDamageOption.value}
								/>
							)
						)}
					</ClaySelect>

					<label htmlFor="UninsuredDamage">
						Uninsured / Underinsured Motorist Property Damage&nbsp;
						<span className="text-danger-darken-1">*</span>
					</label>
				</div>
			</div>

			<div className="row">
				<div className="col-sm filled form-condensed form-group">
					<ClaySelect aria-label="Select Label" id="Medical">
						{medicalOptions.map((medicalOption, index) => (
							<ClaySelect.Option
								className="font-weight-bold text-center text-primary"
								key={index}
								label={medicalOption.label}
								value={medicalOption.value}
							/>
						))}
					</ClaySelect>

					<label htmlFor="Medical">
						Medical&nbsp;
						<span className="text-danger-darken-1">*</span>
					</label>
				</div>

				<div className="col-sm"></div>
			</div>

			<div className="font-weight-bolder mb-4 mt-3 text-paragraph-sm text-uppercase">
				<u>vehicle 1: 2018 Toyota</u>
			</div>

			<div className="row">
				<div className="col-sm filled form-condensed form-group">
					<ClaySelect aria-label="Select Label" id="Comprehensive">
						{comprehensiveOptions.map(
							(comprehensiveOption, index) => (
								<ClaySelect.Option
									className="font-weight-bold text-center text-primary"
									key={index}
									label={comprehensiveOption.label}
									value={comprehensiveOption.value}
								/>
							)
						)}
					</ClaySelect>

					<label htmlFor="Comprehensive">
						Comprehensive&nbsp;
						<span className="text-danger-darken-1">*</span>
					</label>
				</div>

				<div className="col-sm filled form-condensed form-group">
					<ClaySelect aria-label="Select Label" id="Collision">
						{collisionOptions.map((collisionOption, index) => (
							<ClaySelect.Option
								className="font-weight-bold text-center text-primary"
								key={index}
								label={collisionOption.label}
								value={collisionOption.value}
							/>
						))}
					</ClaySelect>

					<label htmlFor="collision">
						Collision&nbsp;
						<span className="text-danger-darken-1">*</span>
					</label>
				</div>
			</div>
		</div>
	);
};

export default CoverageInfoForm;
