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
import classNames from 'classnames';
import {ArrayHelpers, useFormikContext} from 'formik';
import {useState} from 'react';

import PRMForm from '../../../../common/components/PRMForm';
import PRMFormikPageProps from '../../../../common/components/PRMFormik/interfaces/prmFormikPageProps';
import MDFRequest from '../../../../common/interfaces/mdfRequest';
import {StepType} from '../../enums/stepType';
import MDFRequestStepProps from '../../interfaces/mdfRequestStepProps';
import Form from './components/Form';
import Listing from './components/Listing';

interface IProps {
	arrayHelpers: ArrayHelpers;
}

const Activities = ({
	arrayHelpers,
	onCancel,
	onContinue,
	onPrevious,
	onSaveAsDraft,
}: PRMFormikPageProps & MDFRequestStepProps & IProps) => {
	const {
		isSubmitting,
		isValid,
		setFieldValue,
		values,
		...formikHelpers
	} = useFormikContext<MDFRequest>();

	const [isForm, setIsForm] = useState<boolean>(false);
	const [currentActivityIndex, setCurrentActivityIndex] = useState<number>(
		values.activities.length
	);

	const onAdd = () => {
		setCurrentActivityIndex(values.activities.length);

		setIsForm(true);
	};

	const onPreviousForm = () => {
		arrayHelpers.remove(currentActivityIndex);

		setIsForm(false);
	};

	return (
		<PRMForm
			className={classNames({
				'mb-3': !isForm,
				'mb-4': isForm,
			})}
			description="Choose the activities that best match your Campaign MDF request"
			name="Activities"
			title={values.overallCampaignName}
		>
			{isForm ? (
				<Form
					currentActivity={values.activities[currentActivityIndex]}
					currentActivityIndex={currentActivityIndex}
					setFieldValue={setFieldValue}
				/>
			) : (
				<Listing
					{...arrayHelpers}
					activities={values.activities}
					isValid={isValid}
					onAdd={onAdd}
					overallCampaignName={values.overallCampaignName}
				/>
			)}

			<PRMForm.Footer>
				<div className="d-flex mr-auto">
					<Button
						displayType={null}
						onClick={() =>
							isForm
								? onPreviousForm()
								: onPrevious?.(StepType.GOALS)
						}
					>
						Previous
					</Button>

					<Button
						disabled={isSubmitting}
						displayType={null}
						onClick={() => onSaveAsDraft?.(values, formikHelpers)}
					>
						Save as Draft
					</Button>
				</div>

				<div>
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
								: onContinue?.(formikHelpers, StepType.REVIEW)
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
