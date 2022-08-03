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

import {Form, Formik} from 'formik';

import Button from '../../components/Button';
import SiteMapCard from '../../components/SiteMapCard';

const ActivitiesList: any = ({
	generalObject,
	setStep,
}: {
	generalObject: any;
	setStep: any;
}) => {
	const handleOnSubmit = (generalObject: any) => {
		// eslint-disable-next-line no-console
		console.log(`generalObject=`, generalObject);
	};

	return (
		<Formik
			initialValues={{
				additionalOptions: '',
			}}
			onSubmit={() => {
				handleOnSubmit(generalObject);
			}}
		>
			{(formik) => (
				<div className="align-items-start d-flex justify-content-center">
					<SiteMapCard
						className="border-1 flex-column m-5 nav shadow-lg sheet sheet-lg"
						visit={2}
					></SiteMapCard>

					<Form onSubmit={formik.handleSubmit}>
						<div className="border-0 mt-5 shadow-lg sheet sheet-lg">
							<div>
								<div className="mb-4 sheet-header">
									<h5 className="text-primary">REVIEW</h5>

									<h2>Review Campaign MDF Request</h2>

									<h6 className="text-secondary">
										Please ensure that all the information
										below is accurate before submitting your
										request.
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
				</div>
			)}
		</Formik>
	);
};

export default ActivitiesList;
