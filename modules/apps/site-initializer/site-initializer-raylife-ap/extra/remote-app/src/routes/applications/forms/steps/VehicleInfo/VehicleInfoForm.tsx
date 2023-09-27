/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import {ClayInput, ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {useContext, useEffect, useState} from 'react';

import {
	ACTIONS,
	NewApplicationAutoContext,
} from '../../../context/NewApplicationAutoContextProvider';
import {
	makeOptions,
	ownershipOptions,
	primaryUsageOptions,
	yearsOptions,
} from './SelectOptions';
import {formValidationTypes} from './types';

type FormVehicleInfoTypes = {
	form: any[];
	formIndex: number;
	formNumber: number;
	id: number;
};

const FormVehicleInfo = ({
	form,
	formIndex,
	formNumber,
	id,
}: FormVehicleInfoTypes) => {
	const [_state, dispatch] = useContext(NewApplicationAutoContext);

	const [_makeOptions, setMakeOptions] = useState<any[]>([]);
	const [_yearsOptions, setYearsOptions] = useState<any[]>([]);
	const [_model, setModel] = useState<string>('');
	const [year, setYear] = useState<string>('');
	const [yearSearch, setYearSearch] = useState<string>('');

	enum dropdownAlign {
		topCenter = 0,
		topRight = 1,
		middleRight = 2,
		bottomRight = 3,
		bottomCenter = 4,
		bottomLeft = 5,
		middleLeft = 6,
		topLeft = 7,
	}

	const hasRequiredError: formValidationTypes = {
		annualMileage: false,
		make: false,
		model: false,
		ownership: false,
		primaryUsage: false,
		year: false,
	};

	const [hasError, setHasError] = useState(hasRequiredError);

	const setMakeOption = (value: any) => {
		const newMakeChecked = _makeOptions.map((makeOption) => {
			if (makeOption.name === value) {
				return {
					...makeOption,
					checked: true,
				};
			}

			return {
				...makeOption,
				checked: false,
			};
		});

		setModel('');

		setMakeOptions(newMakeChecked);
	};

	const handleChangeField = (
		fieldName: string,
		value: string,
		id: number
	) => {
		const payload = {
			fieldName,
			id,
			value,
		};

		if (fieldName === 'make') {
			setMakeOption(value);
		}

		dispatch({payload, type: ACTIONS.SET_VEHICLE_INFO_FORM});

		dispatch({payload: true, type: ACTIONS.SET_HAS_FORM_CHANGE});
	};

	const handleRemoveFormClick = (formIndex: number) => {
		const payload = {id: formIndex};
		dispatch({payload, type: ACTIONS.SET_REMOVE_VEHICLE});
	};

	const yearOptionsAddClick = () => {
		return yearsOptions.map((yearsOption) => {
			return {
				...yearsOption,
				id,
				onClick: (event: any) => {
					let value = event.target.textContent || '';

					value = value === 'Choose an option' ? '' : value;
					setYear(value);
					handleChangeField('year', value, id);
					setHasError({
						...hasError,
						year: value === '',
					});
				},
			};
		});
	};

	useEffect(() => {
		setMakeOptions(makeOptions);
		setYearsOptions(yearOptionsAddClick());
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		setYearSearch('');
		setYearsOptions(yearOptionsAddClick());
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [year]);

	useEffect(() => {
		setMakeOptions(
			makeOptions.map((item: any) => {
				if (item.name === form[formNumber - 1].make) {
					return {
						...item,
						checked: true,
					};
				}

				return {
					...item,
					checked: false,
				};
			})
		);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [form]);

	const ErrorMessage = ({text}: any) => (
		<div className="form-feedback-group">
			<div className="form-feedback-item">
				<span className="form-feedback-indicator">
					<ClayIcon symbol="info-circle"></ClayIcon>
				</span>

				{text ? text : 'This field is required'}
			</div>
		</div>
	);

	return (
		<>
			<div className="align-items-center d-flex justify-content-between ml-5 mr-3 row">
				<div className="border-bottom flex-grow-1">
					<span className="font-weight-bolder text-paragraph-sm">
						VEHICLE {formNumber}
					</span>
				</div>

				{form.length > 1 && (
					<div className="">
						<ClayButton
							className="border-white font-weight-bolder text-paragraph-sm"
							displayType="secondary"
							onClick={() => handleRemoveFormClick(formIndex)}
						>
							<ClayIcon symbol="times-circle" />
							&nbsp;REMOVE VEHICLE
						</ClayButton>
					</div>
				)}
			</div>

			<div className="mx-3">
				<hr />
			</div>

			<div className="row">
				<div
					className={classNames(
						'col-12 col-lg-4 col-md-12 col-sm-12 filled form-condensed form-group',
						{
							'has-error': hasError.year,
						}
					)}
				>
					<ClayDropDownWithItems
						alignmentByViewport={true}
						alignmentPosition={dropdownAlign.bottomCenter}
						items={_yearsOptions}
						onSearchValueChange={(value: string) => {
							const filterOptions = yearOptionsAddClick().filter(
								(yearOption) => {
									if (
										yearOption.label
											.toLowerCase()
											.includes(value)
									) {
										return yearOption;
									}
								}
							);

							setYearsOptions(filterOptions);
							setYearSearch(value);
						}}
						searchValue={yearSearch}
						searchable={true}
						trigger={
							<div className="d-flex select-years text-neutral-10">
								<ClaySelect
									className="bg-neutral-0 border-neutral-5 cursor-pointer font-weight-bold select-style text-paragraph-sm text-uppercase"
									disabled
									required
									value={form[formNumber - 1].year}
								>
									<ClaySelect.Option
										className="cursor-pointer font-weight-bold py-4 text-brand-primary text-center text-paragraph-sm text-uppercase"
										label={form[formNumber - 1].year}
										value={form[formNumber - 1].year}
									/>
								</ClaySelect>
							</div>
						}
					/>

					<label htmlFor="year">
						Year&nbsp;
						<span className="text-danger-darken-1">*</span>
					</label>

					{hasError.year && <ErrorMessage />}
				</div>

				<div
					className={classNames(
						'col-12 col-lg-4 col-md-12 col-sm-12 filled form-condensed form-group',
						{
							'has-error': hasError.make,
						}
					)}
				>
					<ClaySelect
						aria-label="Select Label"
						className="bg-neutral-0 font-weight-bold make pl-0 text-paragraph-sm text-uppercase"
						id="make"
						name="make"
						onChange={(event) => {
							let value = event.target.value;
							value = value === 'Choose an option' ? '' : value;
							handleChangeField('make', value, id);
							setHasError({
								...hasError,
								make: value === '',
							});
						}}
						value={form[formNumber - 1].make}
					>
						{makeOptions.map((makeOption, index) => (
							<ClaySelect.Option
								className="font-weight-bold py-6 text-brand-primary text-center text-paragraph-sm text-uppercase"
								key={index}
								label={makeOption.name}
								value={makeOption.name}
							/>
						))}
					</ClaySelect>

					<label htmlFor="make">
						Make&nbsp;
						<span className="text-danger-darken-1">*</span>
					</label>

					{hasError.make && <ErrorMessage />}
				</div>

				<div className="col-12 col-lg-4 col-md-12 col-sm-12 filled form-condensed form-group">
					<ClaySelect
						aria-label="Select Label"
						className="bg-neutral-0 font-weight-bold model pl-0 text-paragraph-sm text-uppercase"
						id="model"
						name="model"
						onChange={(event) => {
							let value = event.target.value;
							value = value === 'Choose an option' ? '' : value;
							setModel(value);
							handleChangeField('model', value, id);
							setHasError({
								...hasError,
								model: value === '',
							});
						}}
						value={form[formNumber - 1].model}
					>
						{_makeOptions
							.filter((makeOption) => makeOption.checked)[0]
							?.model.map((model: any, index: number) => (
								<ClaySelect.Option
									className="font-weight-bold text-brand-primary text-center text-paragraph-sm text-uppercase"
									key={index}
									label={model.name}
									value={model.name}
								/>
							))}
					</ClaySelect>

					<label htmlFor="model">Model</label>
				</div>
			</div>

			<div className="row">
				<div
					className={classNames(
						'col-12 col-lg-4 col-md-12 col-sm-12 filled form-condensed form-group',
						{
							'has-error': hasError.primaryUsage,
						}
					)}
				>
					<ClaySelect
						aria-label="Select Label"
						className="bg-neutral-0 font-weight-bold pl-0 primary-usage text-paragraph-sm text-uppercase"
						id="primary-usage"
						name="primary-usage"
						onChange={(event) => {
							let value = event.target.value;
							value = value === 'Choose an option' ? '' : value;
							handleChangeField('primaryUsage', value, id);
							setHasError({
								...hasError,
								primaryUsage: value === '',
							});
						}}
						value={form[formNumber - 1].primaryUsage}
					>
						{primaryUsageOptions.map(
							(primaryUsageOption, index) => (
								<ClaySelect.Option
									className="font-weight-bold py-4 text-brand-primary text-center text-paragraph-sm text-uppercase"
									key={index}
									label={primaryUsageOption.name}
									value={primaryUsageOption.name}
								/>
							)
						)}
					</ClaySelect>

					<label htmlFor="primary-usage">
						Vehicle Primary Usage&nbsp;
						<span className="text-danger-darken-1">*</span>
					</label>

					{hasError.primaryUsage && <ErrorMessage />}
				</div>

				<div
					className={classNames(
						'col-12 col-lg-4 col-md-12 col-sm-12 filled form-condensed form-group',
						{
							'has-error':
								hasError.annualMileage ||
								(form[formNumber - 1].annualMileage <= 50 &&
									form[formNumber - 1].annualMileage !== ''),
						}
					)}
				>
					<ClayInput
						autoComplete="off"
						className="annual-mileage bg-neutral-0 text-center w-100"
						id="annual-mileage"
						min={0}
						name="annual-mileage"
						onChange={(event) => {
							let value = event.target.value;
							value = value === 'Choose an option' ? '' : value;
							handleChangeField('annualMileage', value, id);
							setHasError({
								...hasError,
								annualMileage: value === '',
							});
						}}
						required
						type="number"
						value={form[formNumber - 1].annualMileage}
					/>

					<label htmlFor="annual-mileage">
						Annual Mileage&nbsp;
						<span className="text-danger-darken-1">*</span>
					</label>

					{hasError.annualMileage && <ErrorMessage />}

					{form[formNumber - 1].annualMileage <= 50 &&
						form[formNumber - 1].annualMileage !== '' && (
							<ErrorMessage text="Please enter a value greater than 50" />
						)}
				</div>

				<div
					className={classNames(
						'col-12 col-lg-4 col-md-12 col-sm-12 filled form-condensed form-group',
						{
							'has-error': hasError.ownership,
						}
					)}
				>
					<ClaySelect
						aria-label="Select Label"
						className="bg-neutral-0 font-weight-bold ownership pl-0 text-paragraph-sm text-uppercase"
						id="ownership"
						name="ownership"
						onChange={(event) => {
							let value = event.target.value;
							value = value === 'Choose an option' ? '' : value;
							handleChangeField('ownership', value, id);
							setHasError({
								...hasError,
								ownership: value === '',
							});
						}}
						value={form[formNumber - 1].ownership}
					>
						{ownershipOptions.map((ownershipOption, index) => (
							<ClaySelect.Option
								className="font-weight-bold py-4 text-brand-primary text-center text-paragraph-sm text-uppercase"
								key={index}
								label={ownershipOption.name}
								value={ownershipOption.name}
							/>
						))}
					</ClaySelect>

					<label htmlFor="email">
						Vehicle Ownership&nbsp;
						<span className="text-danger-darken-1">*</span>
					</label>

					{hasError.ownership && <ErrorMessage />}
				</div>
			</div>
		</>
	);
};

export default FormVehicleInfo;
