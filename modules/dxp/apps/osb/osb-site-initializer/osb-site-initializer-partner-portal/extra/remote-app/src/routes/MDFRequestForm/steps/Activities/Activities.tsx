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
import {useCallback, useState} from 'react';

import PRMForm from '../../../../common/components/PRMForm';
import PRMFormikPageProps from '../../../../common/components/PRMFormik/interfaces/prmFormikPageProps';
import MDFRequest from '../../../../common/interfaces/mdfRequest';
import isObjectEmpty from '../../../../common/utils/isObjectEmpty';
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
		errors,
		isSubmitting,
		isValid,
		setFieldValue,
		values,
		...formikHelpers
	} = useFormikContext<MDFRequest>();

	const [currentActivityIndex, setCurrentActivityIndex] = useState<
		number | undefined
	>();
	const [currentActivityIndexEdit, setCurrentActivityIndexEdit] = useState<
		number
	>();

	const activityErrors =
		currentActivityIndex && errors.activities?.[currentActivityIndex];

	const onAdd = () => setCurrentActivityIndex(values.activities.length);

	const onEdit = (index: number) => {
		arrayHelpers.push(values.activities[index]);

		setCurrentActivityIndex(values.activities.length);
		setCurrentActivityIndexEdit(index);
	};

	const onPreviousForm = useCallback(() => {
		if (currentActivityIndex !== undefined) {
			arrayHelpers.remove(currentActivityIndex);

			setCurrentActivityIndex(undefined);
		}

		setCurrentActivityIndexEdit(undefined);
	}, [arrayHelpers, currentActivityIndex]);

	const onContinueForm = () => {
		if (currentActivityIndex === undefined) {
			onContinue?.(formikHelpers, StepType.REVIEW);

			return;
		}

		if (currentActivityIndexEdit !== undefined) {
			arrayHelpers.swap(currentActivityIndex, currentActivityIndexEdit);

			arrayHelpers.remove(currentActivityIndex);
		}

		setCurrentActivityIndexEdit(undefined);
		setCurrentActivityIndex(undefined);
	};

	return (
		<PRMForm
			className={classNames({
				'mb-3': !currentActivityIndex,
				'mb-4': currentActivityIndex,
			})}
			description="Choose the activities that best match your Campaign MDF request"
			name="Activities"
			title={values.overallCampaignName}
		>
			{currentActivityIndex !== undefined ? (
				<Form
					currentActivity={values.activities[currentActivityIndex]}
					currentActivityIndex={currentActivityIndex}
					setFieldValue={setFieldValue}
				/>
			) : (
				<Listing
					{...arrayHelpers}
					activities={values.activities}
					onAdd={onAdd}
					onEdit={onEdit}
					overallCampaignName={values.overallCampaignName}
				/>
			)}

			<PRMForm.Footer>
				<div className="d-flex justify-content-between mr-auto">
					<Button
						displayType={null}
						onClick={() =>
							currentActivityIndex !== undefined
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

				<div className="d-flex justify-content-between px-2 px-md-0">
					<Button
						className="mr-4"
						displayType="secondary"
						onClick={onCancel}
					>
						Cancel
					</Button>

					<Button
						disabled={
							currentActivityIndex !== undefined
								? !isObjectEmpty(activityErrors as Object)
								: !isValid
						}
						onClick={onContinueForm}
					>
						Continue
					</Button>
				</div>
			</PRMForm.Footer>
		</PRMForm>
	);
};

export default Activities;
