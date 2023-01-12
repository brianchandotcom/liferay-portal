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

import ClayForm from '@clayui/form';
import {useEffect, useState} from 'react';
import {SubmitHandler, useForm} from 'react-hook-form';

import Form from '../../../common/components/Form';
import LoadingIndicator from '../../../common/components/Form/LoadingIndicator';
import yupSchema, {yupResolver} from '../../../common/schema/yup';
import {
	downloadContentById,
	exportTask,
	readyToDownload,
} from '../../../common/services/export';
import {getPicklistByName} from '../../../common/services/picklist';
import {getRequestsByFilter} from '../../../common/services/request';
import {
	FIELDSREPORT,
	LiferayBranchType,
	STATUS,
	Statustype,
} from '../../../types/index';

import './index.scss';

export type generateReportsType = typeof yupSchema.report.__outputType;

const GenerateReport = () => {
	const [statuses, setStatuses] = useState<any>([]);
	const [branches, setBranches] = useState<any>([]);
	const [isLoading, setIsLoading] = useState(true);

	const {
		clearErrors,
		formState: {errors},
		handleSubmit,
		register,
		setError,
		setValue,
		watch,
	} = useForm<generateReportsType>({
		defaultValues: {
			liferayBranch: [],
			requestStatus: [],
		},
		resolver: yupResolver(yupSchema.report),
	});

	const validateDate = (dateInitial: string, dateFinal: string) => {
		const regexValidate = /^[\d]{4}-[\d]{2}-[\d]{2}$/;

		if (dateInitial && dateFinal) {
			if (dateInitial > dateFinal) {
				setError('initialRequestDate', {
					message:
						'Initial Request Date cannot be greater than Final Request Date',
					type: 'custom',
				});

				return false;
			}
		}

		if (dateInitial && !regexValidate.test(dateInitial)) {
			setError(FIELDSREPORT.INITIALREQUESTDATE, {
				message:
					'Initial Request Date is not recognized. Please enter a valid date',
				type: 'custom',
			});

			return false;
		}

		if (dateFinal && !regexValidate.test(dateFinal)) {
			setError(FIELDSREPORT.FINALREQUESTDATE, {
				message:
					'Final Request Date is not recognized. Please enter a valid date',
				type: 'custom',
			});

			return false;
		}

		return true;
	};

	const downloadCsvFiltered = async () => {
		setIsLoading(true);
		const task = await exportTask('csv');

		setTimeout(async () => {
			readyToDownload(task.id)
				.then(async (response) => {
					if (response.executeStatus === STATUS.COMPLETED) {
						await downloadContentById(task.id);
					}
					setIsLoading(false);
				})
				.catch(console.error);
		}, 1000);
	};

	const onSubmit: SubmitHandler<generateReportsType> = async (data: any) => {
		const dateCheck = validateDate(
			data.initialRequestDate,
			data.finalRequestDate
		);

		if (dateCheck === false) {
			return;
		}

		await downloadCsvFiltered();
		await getRequestsByFilter(data).then((response) => response);
	};

	const branchesWatch = watch('liferayBranch') as string[];
	const statusesWatch = watch('requestStatus') as string[];

	const formProps = {
		errors,
		register,
		required: true,
	};

	const loadPickLists = async () => {
		const statusList = await getPicklistByName('Request: Request Status');
		setStatuses(statusList);

		const branchList = await getPicklistByName('Request: Liferay Branch');
		setBranches(branchList);
	};

	const fieldsChecked = (
		event: {target: HTMLInputElement},
		fieldWatch: string[],
		field: keyof generateReportsType
	) => {
		const value = event.target.value;

		const fildChecked = fieldWatch?.includes(value)
			? fieldWatch.filter((fieldWatchId) => fieldWatchId !== value)
			: [...fieldWatch, value];

		setValue(field, fildChecked);
	};

	const onClickBranches = (event: {target: HTMLInputElement}) => {
		return fieldsChecked(event, branchesWatch, 'liferayBranch');
	};

	const onClickStatus = (event: {target: HTMLInputElement}) => {
		return fieldsChecked(event, statusesWatch, 'requestStatus');
	};

	useEffect(() => {
		loadPickLists();

		setTimeout(() => setIsLoading(false), 1000);
	}, []);

	return (
		<>
			{isLoading ? (
				<LoadingIndicator />
			) : (
				<ClayForm>
					<div className="row">
						<div className="col">
							<Form.DatePicker
								clearErrors={clearErrors}
								errors={errors}
								id="initialRequestDate"
								label="Initial Request Date"
								{...register('initialRequestDate')}
								name="initialRequestDate"
								placeholder="YYYY-MM-DD"
								setValue={setValue}
							/>
						</div>

						<div className="col">
							<Form.DatePicker
								clearErrors={clearErrors}
								errors={errors}
								id="finalRequestDate"
								label="Final Request Date"
								{...register('finalRequestDate')}
								name="finalRequestDate"
								placeholder="YYYY-MM-DD"
								setValue={setValue}
							/>
						</div>
					</div>

					<div className="row">
						<div className="col">
							<Form.Input
								{...formProps}
								label="Full Name"
								name="fullName"
								placeholder="Full name"
							/>
						</div>
					</div>

					<div className="row">
						<div className="col">
							<Form.Input
								{...formProps}
								label="Initial Company ID"
								min={0}
								name="initialCompanyId"
								placeholder="Company ID"
								type="number"
							/>
						</div>

						<div className="col">
							<Form.Input
								{...formProps}
								label="Final Company ID"
								min={0}
								name="finalCompanyId"
								placeholder="Initial Company ID"
								type="number"
							/>
						</div>
					</div>

					<div className="row">
						<div className="col">
							<Form.Input
								{...formProps}
								label="Company Name"
								name="organizationName"
								placeholder="Company Name"
							/>
						</div>
					</div>

					<div className="row">
						<div className="col">
							<label>Statuses</label>

							{statuses.map(
								(status: Statustype, index: number) => (
									<div
										className="align-items-center d-flex"
										key={index}
									>
										<Form.Checkbox
											checked={statusesWatch?.includes(
												status.key
											)}
											id="requestStatus"
											label={status.name}
											name="requestStatus"
											onChange={onClickStatus}
											value={status.key}
										/>
									</div>
								)
							)}
						</div>

						<div className="col">
							<label>Liferay Branch</label>

							{branches.map(
								(branch: LiferayBranchType, index: number) => (
									<div
										className="align-items-center d-flex"
										key={index}
									>
										<Form.Checkbox
											checked={branchesWatch?.includes(
												branch.key
											)}
											id="liferayBranch"
											label={branch.name}
											name="liferayBranch"
											onChange={onClickBranches}
											value={branch.key}
										/>
									</div>
								)
							)}
						</div>
					</div>

					<div className="mt-4 row">
						<div className="col d-flex justify-content-end">
							<Form.Button
								className="px-4"
								displayType="primary"
								onClick={handleSubmit(onSubmit)}
							>
								Generate
							</Form.Button>
						</div>
					</div>
				</ClayForm>
			)}
		</>
	);
};

export default GenerateReport;
