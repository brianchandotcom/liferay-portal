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
import yupSchema, {yupResolver} from '../../../common/schema/yup';
import {getPicklistByName} from '../../../common/services/picklist';
import {getRequestsByFilter} from '../../../common/services/request';
import {LiferayBranch, Statuses} from '../../../types/index';

import './index.scss';

type generateReportsType = typeof yupSchema.report.__outputType;

const GenerateReport = () => {
	const [statuses, setStatuses] = useState<any>([]);
	const [branches, setBranches] = useState<any>([]);

	const {
		formState: {errors},
		handleSubmit,
		register,
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
	const onSubmit: SubmitHandler<generateReportsType> = (data: any) => {
		getRequestsByFilter(data).then((response) => response);
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

	const onClickBranches = (event: any) => {
		const value = event.target.value;

		const brachesFiltered = branchesWatch?.includes(value)
			? branchesWatch.filter((branchId) => branchId !== value)
			: [...branchesWatch, value];

		setValue('liferayBranch', brachesFiltered);
	};

	const onClickStatus = (event: any) => {
		const value = event.target.value;
		const statusesFiltered = statusesWatch?.includes(value)
			? statusesWatch.filter((statusId) => statusId !== value)
			: [...statusesWatch, value];

		setValue('requestStatus', statusesFiltered);
	};

	useEffect(() => {
		loadPickLists();
	}, []);

	return (
		<>
			<ClayForm>
				<div className="row">
					<div className="col">
						<Form.DatePicker
							id="initialRequestDate"
							label="Initial Request Date"
							name="initialRequestDate"
							placeholder="YYYY-MM-DD"
							setValue={setValue}
						/>
					</div>

					<div className="col">
						<Form.DatePicker
							id="finalRequestDate"
							label="Final Request Date"
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
							name="initialCompanyId"
							placeholder="Company ID"
						/>
					</div>

					<div className="col">
						<Form.Input
							{...formProps}
							label="Final Company ID"
							name="finalCompanyId"
							placeholder="Initial Company ID"
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

						{statuses.map((status: Statuses, index: string) => (
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
						))}
					</div>

					<div className="col">
						<label>Liferay Branch</label>

						{branches.map(
							(branch: LiferayBranch, index: string) => (
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
		</>
	);
};

export default GenerateReport;
