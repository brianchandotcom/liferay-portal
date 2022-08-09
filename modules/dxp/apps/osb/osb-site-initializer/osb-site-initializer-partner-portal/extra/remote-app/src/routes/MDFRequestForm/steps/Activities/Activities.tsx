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

import Button from '@clayui/button';
import {ArrayHelpers, useFormikContext} from 'formik';
import {useState} from 'react';

import PRMForm from '../../../../common/components/PRMForm';
import PRMFormikPageProps from '../../../../common/components/PRMFormik/interfaces/prmFormikPageProps';
import MDFRequest from '../../../../common/interfaces/mdfRequest';
import MDFRequestStepProps from '../../interfaces/mdfRequestStepProps';
import Form from './Form';
import Listing from './Listing';

interface IProps {
	arrayHelpers: ArrayHelpers;
}

const Activities = ({
	arrayHelpers,
	onCancel,
	onContinue,
	onPrevious,
	onSaveAsDraft,
}: PRMFormikPageProps & MDFRequestStepProps<MDFRequest> & IProps) => {
	const {isSubmitting, isValid, values, ...formikHelpers} = useFormikContext<
		MDFRequest
	>();
	const [isForm, setIsForm] = useState<boolean>(false);
	const [currentIndex, setCurrentIndex] = useState<number>(
		values.activities.length
	);

	const onAdd = () => {
		setCurrentIndex(values.activities.length);
		setIsForm(true);
	};

	const onPreviousForm = () => {
		arrayHelpers.pop();
		setIsForm(false);
	};

	return (
		<PRMForm
			description="Choose the activities that best match your Campaign MDF request"
			name="Activities"
			title={values.overallCampaign}
		>
			{isForm ? (
				<Form currentIndex={currentIndex} />
			) : (
				<Listing
					{...arrayHelpers}
					activities={values.activities}
					onAdd={onAdd}
				/>
			)}

			<PRMForm.Footer>
				<div className="mr-auto pl-0 py-3">
					<Button
						className="mr-4"
						displayType="secondary"
						onClick={isForm ? () => onPreviousForm() : onPrevious}
					>
						Previous
					</Button>

					<Button
						className="pl-0"
						disabled={isSubmitting}
						displayType={null}
						onClick={() => onSaveAsDraft?.(values, formikHelpers)}
					>
						Save as Draft
					</Button>
				</div>

				<div className="p-2">
					<Button
						className="mr-4"
						displayType="secondary"
						onClick={onCancel}
					>
						Cancel
					</Button>

					<Button
						disabled={!isValid}
						onClick={() =>
							isForm
								? setIsForm(false)
								: onContinue?.(formikHelpers)
						}
					>
						Continue
					</Button>
				</div>
			</PRMForm.Footer>
		</PRMForm>
	);
};

export default Activities;
