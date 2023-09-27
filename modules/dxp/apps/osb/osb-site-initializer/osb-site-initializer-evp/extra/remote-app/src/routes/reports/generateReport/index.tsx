/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import dayjs from 'dayjs';
import {useEffect, useState} from 'react';
import {SubmitHandler, useForm} from 'react-hook-form';

import Form from '../../../common/components/Form';
import LoadingIndicator from '../../../common/components/Form/LoadingIndicator';
import yupSchema, {yupResolver} from '../../../common/schema/yup';
import {Liferay} from '../../../common/services/liferay/liferay';
import {getPicklistByName} from '../../../common/services/picklist';
import {getRequestsByFilter} from '../../../common/services/request';
import {
	FIELDSREPORT,
	LiferayBranchType,
	RequestType,
	Statustype,
} from '../../../types/index';

import './index.scss';
import {extractStringFromURL} from '../../../util/replace';

export type generateReportsType = typeof yupSchema.report.__outputType;

const GenerateReport = () => {
	const [statuses, setStatuses] = useState<any>([]);
	const [branches, setBranches] = useState<any>([]);
	const [isLoading, setIsLoading] = useState(true);

	const pathCurrentSite = Liferay.ThemeDisplay.getSiteAdminURL();

	const redirect = `${Liferay.ThemeDisplay.getPortalURL()}/web/${extractStringFromURL(
		pathCurrentSite
	)}/reports`;

	const {
		clearErrors,
		formState: {errors},
		getValues,
		handleSubmit,
		register,
		setError,
		setValue,
		watch,
	} = useForm<generateReportsType>({
		defaultValues: {
			finalRequestDate: '',
			initialRequestDate: '',
			liferayBranch: [],
			requestStatus: [],
		},
		resolver: yupResolver(yupSchema.report),
	});

	const validateDate = (dateInitial: string, dateFinal: string) => {
		const regexValidate = /^[\d]{4}-[\d]{2}-[\d]{2}$/;

		if (dateInitial && dateFinal) {
			if (dateInitial > dateFinal) {
				setError(FIELDSREPORT.INITIALREQUESTDATE, {
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

	const constructionFieldsCsv = (fields: RequestType[]) => {
		let fieldsCsv = '';

		const headers = [
			'Company ID',
			'Company Name',
			'Company Tax ID',
			'User Name',
			'Request Date',
			'Description',
			'Request Type',
			'Grant Type',
			'Value',
			'Service Hours',
			'Start Date',
			'End Date',
		];

		fieldsCsv += `${headers.join(',')}\n`;

		for (const field of fields) {
			const body = [
				field?.r_organization_c_evpOrganizationId,
				field?.r_organization_c_evpOrganization?.organizationName,
				field?.r_organization_c_evpOrganization?.taxId,
				field?.fullName,
				dayjs(field?.dateCreated).format('MM-DD-YYYY'),
				field?.requestDescription,
				field?.requestType?.name,
				field?.grantRequestType?.name,
				field?.grantAmount,
				field?.totalHoursRequested,
				dayjs(field?.startDate).format('MM-DD-YYYY'),
				dayjs(field?.endDate).format('MM-DD-YYYY'),
			];

			fieldsCsv += `${body.join(',')}\n`;
		}

		const hiddenElement = document.createElement('a');
		hiddenElement.href =
			'data:text/csv;charset=utf-8,' + encodeURI(fieldsCsv);

		hiddenElement.target = '_blank';
		hiddenElement.download = 'request-report.csv';
		hiddenElement.click();
	};

	const onSubmit: SubmitHandler<generateReportsType> = async (data: any) => {
		setIsLoading(true);
		const dateCheck = validateDate(
			data.initialRequestDate,
			data.finalRequestDate
		);

		if (dateCheck === false) {
			setIsLoading(false);

			return;
		}

		await getRequestsByFilter(data).then((response) => {
			setTimeout(() => {
				constructionFieldsCsv(response?.items);
				setIsLoading(false);
			}, 1000);
		});
	};

	const branchesWatch = watch('liferayBranch') as string[];
	const statusesWatch = watch('requestStatus') as string[];

	const formProps = {
		errors,
		register,
		required: true,
	};

	const loadPickLists = async () => {
		const statusList = await getPicklistByName(
			'EVP Request: Request Status'
		);
		setStatuses(statusList);

		const branchList = await getPicklistByName(
			'EVP Request: Liferay Branch'
		);
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
				<ClayForm className="mb-9">
					<div className="row">
						<div className="col">
							<Form.DatePicker
								clearErrors={clearErrors}
								errors={formProps.errors}
								id="initialRequestDate"
								label="Initial Request Date"
								{...register('initialRequestDate')}
								name="initialRequestDate"
								placeholder="YYYY-MM-DD"
								setValue={setValue}
								value={
									getValues('initialRequestDate') as string
								}
							/>
						</div>

						<div className="col">
							<Form.DatePicker
								clearErrors={clearErrors}
								errors={formProps.errors}
								id="finalRequestDate"
								label="Final Request Date"
								{...register('finalRequestDate')}
								name="finalRequestDate"
								placeholder="YYYY-MM-DD"
								setValue={setValue}
								value={getValues('finalRequestDate') as string}
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

					<div className="mt-5 row">
						<div className="col">
							<div className="col d-flex justify-content-start p-0">
								<Form.Button
									className="px-4"
									displayType="secondary"
									onClick={() => {
										window.location.href = redirect;
									}}
								>
									Back
								</Form.Button>
							</div>
						</div>

						<div className="col">
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
					</div>
				</ClayForm>
			)}
		</>
	);
};

export default GenerateReport;
