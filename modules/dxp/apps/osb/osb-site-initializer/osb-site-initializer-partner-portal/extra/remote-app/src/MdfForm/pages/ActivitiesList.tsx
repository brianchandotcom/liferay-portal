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

import ClayCard from '@clayui/card';
import {Form, Formik} from 'formik';
import {useState} from 'react';

import Alert from '../../components/Alert';
import Button from '../../components/Button';
import SiteMapCard from '../../components/SiteMapCard';

const ActivitiesList: any = ({
	generalObject,
	setStep,
}: {
	generalObject: any;
	setStep: any;
}) => {
	const handleOnSubmit = (formData: any) => {
		// eslint-disable-next-line no-console
		console.log(`fim`, formData);
	};

	const [showAlert, setShowAlert] = useState<boolean>(true);

	return (
		<Formik
			initialValues={{
				additionalOptions: '',
			}}
			onSubmit={(formData) => {
				handleOnSubmit(formData);
			}}
		>
			{(formik) => (
				<div className="align-items-start d-flex justify-content-center">
					<SiteMapCard
						className="border-1 flex-column m-5 nav shadow-lg sheet sheet-lg"
						visit={1}
					></SiteMapCard>

					<Form onSubmit={formik.handleSubmit}>
						<div className="border-0 mt-5 shadow-lg sheet sheet-lg">
							<div>
								<div className="mb-4 sheet-header">
									<h5 className="text-primary">ACTIVITIES</h5>

									<h2>Insurance Industry Lead Gen Request</h2>

									<h5 className="text-secondary">
										ID Nº 1157074
									</h5>

									<h6 className="text-secondary">
										Choose the activities that best match
										your Campaign MDF request
									</h6>
								</div>
							</div>

							{showAlert && !generalObject.activities.length ? (
								<div>
									<Alert
										closeAlert={true}
										displayType="info"
										setShowAlert={setShowAlert}
										title="Info:"
									/>
								</div>
							) : (
								!!generalObject.activities.length &&
								generalObject.activities.map(
									(key: any, items: any) => (
										<>
											<ClayCard>
												<ClayCard.Body>
													<div className="mb-4 sheet-header">
														<h6 className="text-secondary">
															Insurance Industry
															Lead Gen (1157074)
														</h6>

														<h2>
															{items.activityName}
															(1987659)
														</h2>

														<div className="bd-highlight d-flex mb-3">
															<div className="bd-highlight mr-auto">
																<h5 className="text-secondary">
																	MDF
																	Requested:
																</h5>
															</div>

															<div className="bd-highlight">
																<h5 className="text-secondary">
																	$1,250.00
																</h5>
															</div>
														</div>
													</div>
												</ClayCard.Body>
											</ClayCard>
										</>
									)
								)
							)}

							<div>
								<Button
									className="border-primary mb-3 text-primary"
									displayType="secondary"
									icon=""
									label="+ Add Activity"
									onClick={() => {
										setStep(2);
									}}
									type="button"
								/>
							</div>

							<div className="d-flex">
								<div className="mr-auto p-2">
									<Button
										className="mr-3"
										displayType="unstyled"
										icon=""
										label="Previous"
										onClick={() => {
											setStep(0);
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
				</div>
			)}
		</Formik>
	);
};

export default ActivitiesList;
