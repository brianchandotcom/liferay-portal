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
import ClayLoadingIndicator from '@clayui/loading-indicator';
import classNames from 'classnames';
import {ArrayHelpers, useFormikContext} from 'formik';
import {useCallback, useEffect, useState} from 'react';

import PRMForm from '../../../../common/components/PRMForm';
import PRMFormikPageProps from '../../../../common/components/PRMFormik/interfaces/prmFormikPageProps';
import MDFRequest from '../../../../common/interfaces/mdfRequest';
import isObjectEmpty from '../../../../common/utils/isObjectEmpty';
import {StepType} from '../../enums/stepType';
import MDFRequestStepProps from '../../interfaces/mdfRequestStepProps';
import Form from './components/Form';
import Listing from './components/Listing';
import useGetSummaryActivities from './hooks/useGetSummaryActivities';

interface IProps {
	arrayHelpers: ArrayHelpers;
	isEdit: boolean;
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
		status,
		values,
		...formikHelpers
	} = useFormikContext<MDFRequest>();

	const [currentActivityIndex, setCurrentActivityIndex] = useState<
		number | undefined
	>();
	const [currentActivityIndexEdit, setCurrentActivityIndexEdit] = useState<
		number
	>();

	const [isDraft, setIsDraft] = useState(false);

	const activityErrors =
		currentActivityIndex !== undefined &&
		errors.activities?.[currentActivityIndex];

	const updateEditableActivity = () => {
		if (
			currentActivityIndexEdit !== undefined &&
			currentActivityIndex !== undefined
		) {
			arrayHelpers.swap(currentActivityIndex, currentActivityIndexEdit);

			arrayHelpers.remove(currentActivityIndex);
		}

		setCurrentActivityIndexEdit(undefined);
		setCurrentActivityIndex(undefined);
	};

	const onAdd = () => setCurrentActivityIndex(values.activities.length);

	const {
		maxDateActivity,
		minDateActivity,
		totalCostOfExpense,
		totalMDFRequestAmount,
	} = useGetSummaryActivities(values.activities);

	useEffect(() => {
		setFieldValue('maxDateActivity', maxDateActivity);
		setFieldValue('minDateActivity', minDateActivity);
		setFieldValue('totalCostOfExpense', totalCostOfExpense);
		setFieldValue('totalMDFRequestAmount', totalMDFRequestAmount);
	}, [
		maxDateActivity,
		minDateActivity,
		setFieldValue,
		totalCostOfExpense,
		totalMDFRequestAmount,
	]);

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

		updateEditableActivity();
	};

	const onRemove = (index: number) => {
		setFieldValue(`activities[${index}].removed`, true);
	};

	const hasActivityErrorsByIndex = (index: number): boolean =>
		Boolean(errors.activities?.[index]);

	const onSaveAsDraftForm = () => {
		updateEditableActivity();
		setIsDraft(true);
	};

	useEffect(() => {
		if (isDraft) {
			onSaveAsDraft?.(values, formikHelpers);
			setIsDraft(false);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [isDraft]);

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
					currency={values.currency}
					currentActivity={values.activities[currentActivityIndex]}
					currentActivityIndex={currentActivityIndex}
					setFieldValue={setFieldValue}
				/>
			) : (
				<Listing
					{...arrayHelpers}
					activities={values.activities?.filter(
						(activity) => !activity.removed
					)}
					currency={values.currency}
					hasActivityErrorsByIndex={hasActivityErrorsByIndex}
					onAdd={onAdd}
					onEdit={onEdit}
					onRemove={onRemove}
					overallCampaignName={values.overallCampaignName}
				/>
			)}

			<PRMForm.Footer>
				<div className="d-flex justify-content-between mr-auto">
					<Button
						disabled={status || isSubmitting}
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
						className="inline-item inline-item-after"
						disabled={status || isSubmitting}
						displayType={null}
						onClick={onSaveAsDraftForm}
					>
						Save as Draft
						{isSubmitting && (
							<ClayLoadingIndicator className="inline-item inline-item-after ml-2" />
						)}
					</Button>
				</div>

				<div className="d-flex justify-content-between px-2 px-md-0">
					<Button
						className="mr-4"
						disabled={status || isSubmitting}
						displayType="secondary"
						onClick={onCancel}
					>
						Cancel
					</Button>

					<Button
						className="inline-item inline-item-after"
						disabled={
							currentActivityIndex !== undefined
								? !isObjectEmpty(activityErrors as Object)
								: !isValid
						}
						onClick={onContinueForm}
					>
						Continue
						{isSubmitting && (
							<ClayLoadingIndicator className="inline-item inline-item-after ml-2" />
						)}
					</Button>
				</div>
			</PRMForm.Footer>
		</PRMForm>
	);
};

export default Activities;
