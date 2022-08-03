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

import {FieldArray, Form, Formik} from 'formik';
import {useState} from 'react';

import Button from '../../components/Button';
import CheckBoxList from '../../components/CheckBoxList';
import DatePicker from '../../components/DatePicker';
import InputText from '../../components/InputText';
import Radio from '../../components/Radio';
import Select from '../../components/Select';
import SiteMapCard from '../../components/SiteMapCard';
import LIST_TYPE_ENTRIES from '../../constants/listTypeEntries';
import {useGetListTypeDefinitions} from '../../services/list-type-definitions/useGetListTypeDefinitions';
import getInitialGenerateNewKey from '../utils/constants/getInitialGenerateNewKey';

const ActivitiesForm: any = ({
	generalObject,
	setGeneralObject,
	setStep,
}: {
	generalObject: any;
	setGeneralObject: any;
	setStep: any;
}) => {
	const [startDateValue, setStartDateValue] = useState('');
	const [endDateValue, setEndDateValue] = useState('');

	const typeOfActivity = useGetListTypeDefinitions(
		LIST_TYPE_ENTRIES.typeOfActivity
	);

	const checkLeadFollowStrategy = useGetListTypeDefinitions(
		LIST_TYPE_ENTRIES.leadFollowUpStrategy
	);

	const optionsExpense = useGetListTypeDefinitions(LIST_TYPE_ENTRIES.expense);

	const [updateLeadFollowStrategy, setUpdateLeadFollowStrategy] = useState();

	const handleOnSubmit = (
		formData: any,
		generalObject: any,
		setGeneralObject: any,
		setStep: any,
		updateLeadFollowStrategy: any
	) => {
		const createForm = {
			...formData,
			leadFollowStrategy: updateLeadFollowStrategy,
		};

		generalObject.activities.push(createForm);

		// eslint-disable-next-line no-console
		console.log(`createForm=`, generalObject);
		setGeneralObject(generalObject);
		setStep(1);
	};

	const optionsTactic = [
		{
			key: 'In Person, Industry',
			name: 'In Person, Industry',
		},
		{
			key: 'In Person, Industry 1',
			name: 'In Person, Industry 1',
		},
	];

	const optionsBudget = [
		{
			key: '$1,500.00',
			name: '$1,500.00',
		},
		{
			key: '$2,500.00',
			name: '$2,500.00',
		},
	];

	return (
		<Formik
			initialValues={{
				activityDesription: '',
				activityName: '',
				activityPromotion: '',
				addExpenses: [getInitialGenerateNewKey()],
				detailsOnLead: '',
				endDate: '',
				leadListOutcomeActivity: '',
				liferayBranding: '',
				liferayParticipationRequirements: '',
				sourceSizeInviteList: '',
				startDateValue: '',
				tactic: '',
				targetLeads: '',
				totalMdfRequestedAmount: '',
				typeActivity: '',
				venueName: '',
			}}
			onSubmit={(formData) => {
				handleOnSubmit(
					formData,
					generalObject,
					setGeneralObject,
					setStep,
					updateLeadFollowStrategy
				);
			}}
		>
			{(formik) => (
				<div className="align-items-start d-flex justify-content-center">
					<SiteMapCard
						className="border-1 flex-column m-5 nav shadow-lg sheet sheet-lg"
						visit={1}
					></SiteMapCard>

					<FieldArray
						name="addExpenses"
						render={({push}) => (
							<>
								<Form onSubmit={formik.handleSubmit}>
									<div className="border-0 mt-5 shadow-lg sheet sheet-lg">
										<div>
											<div className="mb-4 sheet-header">
												<h5 className="text-primary">
													ACTIVITIES
												</h5>

												<h2>
													Insurance Industry Lead Gen
												</h2>

												<h5 className="text-secondary">
													ID Nº 1157074
												</h5>

												<h6 className="text-secondary">
													Choose the activities that
													best match your Campaign MDF
													request
												</h6>

												<div className="mb-4 sheet-header">
													<h4>Campaign Activity</h4>
												</div>
											</div>

											<div className="form-group-autofit">
												<div className="form-group-item">
													<InputText
														className="form-control shadow-none"
														disabled={false}
														label="Activity Name"
														name="activityName"
														onChange={
															formik.handleChange
														}
														placeholder="Activity Name"
														type="text"
														value={
															formik.values
																.activityName
														}
													/>
												</div>
											</div>

											<div className="form-group-autofit">
												<div className="form-group-item">
													<Select
														label="Type of Activity"
														name="typeActivity"
														onChange={
															formik.handleChange
														}
														options={typeOfActivity}
													/>
												</div>

												<div className="form-group-item">
													<Select
														label="Tactic"
														name="tactic"
														onChange={
															formik.handleChange
														}
														options={optionsTactic}
													/>
												</div>
											</div>

											<div className="form-group-autofit">
												<div className="form-group-item">
													<InputText
														className="form-control shadow-none"
														disabled={false}
														label="Activity Description"
														name="activityDesription"
														onChange={
															formik.handleChange
														}
														placeholder="Activity Description"
														type="text"
														value={
															formik.values
																.activityDesription
														}
													/>
												</div>
											</div>

											<div className="form-group-autofit">
												<div className="form-group-item">
													<InputText
														className="form-control shadow-none"
														disabled={false}
														label="Venue Name"
														name="venueName"
														onChange={
															formik.handleChange
														}
														placeholder="Venue Name"
														type="text"
														value={
															formik.values
																.venueName
														}
													/>
												</div>
											</div>

											<div className="form-group-autofit">
												<div className="form-group-item">
													<InputText
														className="form-control shadow-none"
														disabled={false}
														label="Liferay Branding"
														name="liferayBranding"
														onChange={
															formik.handleChange
														}
														placeholder="Liferay Branding"
														type="text"
														value={
															formik.values
																.liferayBranding
														}
													/>
												</div>
											</div>

											<div className="form-group-autofit">
												<div className="form-group-item">
													<InputText
														className="form-control shadow-none"
														disabled={false}
														label="Liferay Participation / Requirements"
														name="liferayParticipationRequirements"
														onChange={
															formik.handleChange
														}
														placeholder="Liferay Participation / Requirements"
														type="text"
														value={
															formik.values
																.liferayParticipationRequirements
														}
													/>
												</div>
											</div>

											<div className="form-group-autofit">
												<div className="form-group-item">
													<InputText
														className="form-control shadow-none"
														disabled={false}
														label="Source and Size of Invite List"
														name="sourceSizeInviteList"
														onChange={
															formik.handleChange
														}
														placeholder="Source and Size of Invite List"
														type="text"
														value={
															formik.values
																.sourceSizeInviteList
														}
													/>
												</div>
											</div>

											<div className="form-group-autofit">
												<div className="form-group-item">
													<InputText
														className="form-control shadow-none"
														disabled={false}
														label="Activity Promotion"
														name="activityPromotion"
														onChange={
															formik.handleChange
														}
														placeholder="Activity Promotion"
														type="text"
														value={
															formik.values
																.activityPromotion
														}
													/>
												</div>
											</div>

											<div className="form-group-autofit">
												<div className="form-group-item">
													<DatePicker
														dateFormat="dd/MM/yyyy"
														label="Start Date"
														name="startDate"
														onChange={(
															value: any
														) => {
															formik.setFieldValue(
																'starDate',
																value
															);
															setStartDateValue(
																value
															);
														}}
														placeholder="DD/MM/YYYY"
														value={startDateValue}
														years={{
															end: 2025,
															start: 2000,
														}}
													/>
												</div>

												<div className="form-group-item">
													<DatePicker
														dateFormat="dd/MM/yyyy"
														label="End Date"
														name="endDate"
														onChange={(
															value: any
														) => {
															formik.setFieldValue(
																'endDate',
																value
															);
															setEndDateValue(
																value
															);
														}}
														placeholder="DD/MM/YYYY"
														value={endDateValue}
														years={{
															end: 2025,
															start: 2000,
														}}
													/>
												</div>
											</div>

											<div className="mb-4 sheet-header">
												<div>
													<h4>Budget Breakdown</h4>
												</div>

												<h6 className="text-secondary">
													Add all the expenses that
													best match with your
													Activity to add your Total
													MDF REQUESTED amount
												</h6>
											</div>

											{formik.values.addExpenses.map(
												(_: any, index: any) => (
													<>
														<div className="form-group-autofit">
															<div className="form-group-item">
																<Select
																	label="Expense"
																	name={`addExpenses[${index}].expense`}
																	onChange={(
																		event: any
																	) => {
																		formik.setFieldValue(
																			`addExpenses[${index}].expense`,
																			event
																				.target
																				.value
																		);
																	}}
																	options={
																		optionsExpense
																	}
																/>
															</div>

															<div className="form-group-item">
																<Select
																	label="Budget"
																	name={`addExpenses[${index}].budget`}
																	onChange={(
																		event: any
																	) => {
																		formik.setFieldValue(
																			`addExpenses[${index}].budget`,
																			event
																				.target
																				.value
																		);
																	}}
																	options={
																		optionsBudget
																	}
																/>
															</div>
														</div>
													</>
												)
											)}

											<div>
												<Button
													className="border-primary mb-3 text-primary"
													displayType="secondary"
													icon=""
													label="+ Add Expense"
													onClick={() => {
														push(
															getInitialGenerateNewKey()
														);
													}}
													type="button"
												/>
											</div>

											<div className="form-group-autofit">
												<div className="form-group-item">
													<div>Total Budget</div>
												</div>

												<div className="form-group-item text-right">
													<div>$2,500.00</div>
												</div>
											</div>

											<div className="form-group-autofit">
												<div className="form-group-item">
													<div>Claim Percent</div>
												</div>

												<div className="form-group-item text-right">
													<div>50%</div>
												</div>
											</div>

											<div className="mb-1">
												<div className="form-group-item">
													<Select
														label="Total MDF Requested Amount"
														name="totalMdfRequestedAmount"
														onChange={
															formik.handleChange
														}
														options={optionsBudget}
													/>
												</div>
											</div>

											<div className="mb-4">
												<h6 className="text-secondary">
													Silver Partner can request
													up to 50% of the Total Cost
													of Expenses.
												</h6>
											</div>

											<div className="mb-4 sheet-header">
												<div>
													<h4>Lead List</h4>
												</div>
											</div>

											<div className="form-group-autofit">
												<div className="form-group-item">
													<label>
														Is a lead list an
														outcome of this activity
													</label>

													<div>
														<Radio
															checked={
																formik.values
																	.leadListOutcomeActivity ===
																'yes'
															}
															inline
															label="Yes"
															name="additionalOptions"
															onChange={() => {
																formik.setFieldValue(
																	'leadListOutcomeActivity',
																	'yes'
																);
															}}
															value="leadListOutcomeActivity"
														/>
													</div>

													<div>
														<Radio
															checked={
																formik.values
																	.leadListOutcomeActivity ===
																'no'
															}
															inline
															label="No"
															name="leadListOutcomeActivity"
															onChange={() => {
																formik.setFieldValue(
																	'leadListOutcomeActivity',
																	'no'
																);
															}}
															value="no"
														/>
													</div>
												</div>
											</div>

											<div className="form-group-autofit">
												<div className="form-group-item">
													<InputText
														className="form-control shadow-none"
														disabled={false}
														label="Target # of Leads"
														name="targetLeads"
														onChange={
															formik.handleChange
														}
														placeholder="Target # of Leads"
														type="text"
														value={
															formik.values
																.targetLeads
														}
													/>
												</div>
											</div>

											<div className="form-group-autofit">
												<div className="form-group-item">
													<label>
														Lead Follow Up strategy
														(select all that apply)
													</label>

													<div className="border border-light p-3 rounded">
														<>
															<CheckBoxList
																availableItems={
																	checkLeadFollowStrategy
																}
																formik={formik}
																hasOther={false}
																hasRule={false}
																maxCheckedItems={
																	3
																}
																setCheckBox={
																	setUpdateLeadFollowStrategy
																}
															/>
														</>
													</div>
												</div>
											</div>

											<div className="mb-1">
												<div className="form-group-item">
													<InputText
														className="form-control shadow-none"
														disabled={false}
														label="Details on Lead Follow Up. What to include (i)"
														name="detailsOnLead"
														onChange={
															formik.handleChange
														}
														placeholder=""
														type="text"
														value={
															formik.values
																.detailsOnLead
														}
													/>
												</div>
											</div>

											<div className="mb-4">
												<h6 className="text-secondary">
													(i) Please describe the
													follow-up plan in detail: Do
													you need any assets from
													Liferay (i.e. landing page,
													collateral, content) Will
													Liferay participate in the
													follow up? If so, please
													provide details
												</h6>
											</div>
										</div>

										<div className="d-flex">
											<div className="mr-auto p-2">
												<Button
													className="mr-3"
													displayType="unstyled"
													icon=""
													label="Previous"
													onClick={() => {
														setStep(1);
													}}
													type="button"
												/>

												<Button
													className=""
													displayType="unstyled"
													icon=""
													label="Save as Draft"
													onClick={() => {}}
													type="button"
												/>
											</div>

											<div className="p-2">
												<Button
													className=""
													displayType="secondary"
													icon=""
													label="Cancel"
													onClick={() => {}}
													type="button"
												/>
											</div>

											<div className="p-2">
												<Button
													className=""
													displayType="primary"
													icon=""
													label="Continue"
													onClick={() => {}}
													type="submit"
												/>
											</div>
										</div>
									</div>
								</Form>
							</>
						)}
					/>
				</div>
			)}
		</Formik>
	);
};

export default ActivitiesForm;
