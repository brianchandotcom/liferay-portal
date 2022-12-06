/* eslint-disable no-console */
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
			branchRequest: [],
			statusRequest: [],
		},
		resolver: yupResolver(yupSchema.report),
	});
	const onSubmit: SubmitHandler<generateReportsType> = (data) => {
		// eslint-disable-next-line no-console
		console.log('data', data);

		getRequestsByFilter().then((response) =>
			console.log('response', response)
		);
	};

	const branchesWatch = watch('branchRequest') as number[];
	const statusesWatch = watch('statusRequest') as number[];

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
		const value = Number(event.target.value);

		const brachesFiltered = branchesWatch?.includes(value)
			? branchesWatch.filter((branchId) => branchId !== value)
			: [...branchesWatch, value];

		setValue('branchRequest', brachesFiltered);
	};

	const onClickStatus = (event: any) => {
		const value = Number(event.target.value);

		const statusesFiltered = statusesWatch?.includes(value)
			? statusesWatch.filter((statusId) => statusId !== value)
			: [...statusesWatch, value];

		setValue('statusRequest', statusesFiltered);
	};

	useEffect(() => {
		loadPickLists();
	}, []);

	console.log('errors', errors);

	return (
		<>
			<ClayForm>
				<div className="row">
					<div className="col">
						<Form.DatePicker
							{...formProps}
							label="Initial Request Date"
							name="initialRequestDate"
							placeholder="YYYY-MM-DD"
						/>
					</div>

					<div className="col">
						<Form.DatePicker
							{...formProps}
							label="Final Request Date"
							name="finalRequestDate"
							placeholder="YYYY-MM-DD"
						/>
					</div>
				</div>

				<div className="row">
					<div className="col">
						<Form.Input
							{...formProps}
							label="Initial User Name"
							name="initialUserName"
							placeholder="User name"
						/>
					</div>

					<div className="col">
						<Form.Input
							{...formProps}
							label="Final User Name"
							name="finalUserName"
							placeholder="User name"
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
							label="Initial Company Name"
							name="initialCompanyName"
							placeholder="Company Name"
						/>
					</div>

					<div className="col">
						<Form.Input
							{...formProps}
							label="Final Company Name"
							name="finalCompanyName"
							placeholder="Initial Company Name"
						/>
					</div>
				</div>

				<div className="row">
					<div className="col">
						<label>Statuses</label>

						{statuses.map((status: any, index: string) => (
							<div
								className="align-items-center d-flex"
								key={index}
							>
								<Form.Checkbox
									checked={statusesWatch?.includes(status.id)}
									id="statusRequest"
									label={status.name}
									name="statusRequest"
									onChange={onClickStatus}
									value={status.id}
								/>
							</div>
						))}
					</div>

					<div className="col">
						<label>Liferay Branch</label>

						{branches.map((branch: any, index: string) => (
							<div
								className="align-items-center d-flex"
								key={index}
							>
								<Form.Checkbox
									checked={branchesWatch?.includes(branch.id)}
									id="branchRequest"
									label={branch.name}
									name="branchRequest"
									onChange={onClickBranches}
									value={branch.id}
								/>
							</div>
						))}
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
