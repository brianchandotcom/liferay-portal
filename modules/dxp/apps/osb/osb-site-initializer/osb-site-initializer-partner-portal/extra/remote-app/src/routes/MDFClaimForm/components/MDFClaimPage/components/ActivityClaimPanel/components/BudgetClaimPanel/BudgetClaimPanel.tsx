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

import ClayPanel from '@clayui/panel';
import {FormikContextType} from 'formik';

import PRMForm from '../../../../../../../../common/components/PRMForm';
import PRMFormik from '../../../../../../../../common/components/PRMFormik';
import MDFClaim from '../../../../../../../../common/interfaces/mdfClaim';
import MDFClaimBudget from '../../../../../../../../common/interfaces/mdfClaimBudget';
import PanelHeader from '../PanelHeader';

interface IProps {
	activityIndex: number;
	budget: MDFClaimBudget;
	budgetIndex: number;
}

const BudgetClaimPanel = ({
	activityIndex,
	budget,
	budgetIndex,
	setFieldValue,
}: IProps & Pick<FormikContextType<MDFClaim>, 'setFieldValue'>) => {
	const budgetFieldName = `activities[${activityIndex}].budgets[${budgetIndex}]`;

	return (
		<ClayPanel displayType="secondary" showCollapseIcon={true}>
			<PanelHeader expanded={budget.selected}>
				<div className="d-flex">
					<PRMFormik.Field
						component={PRMForm.Checkbox}
						name={`${budgetFieldName}.selected`}
					/>

					<h5 className="ml-3 text-neutral-10">
						{budget.expenseName}
					</h5>
				</div>
			</PanelHeader>

			<ClayPanel.Body>
				<div>
					<PRMFormik.Field
						component={PRMForm.InputCurrency}
						description="Silver Partner can claim up to 50%"
						label="Invoice Amount"
						name={`${budgetFieldName}.invoiceAmount`}
						onAccept={(value: File) =>
							setFieldValue(
								`${budgetFieldName}.invoiceAmount`,
								value
							)
						}
						required={budget.selected}
					/>

					<PRMFormik.Field
						component={PRMForm.InputFile}
						displayType="secondary"
						label="Third Party Invoice"
						name={`${budgetFieldName}.invoice`}
						onAccept={(value: File) =>
							setFieldValue(`${budgetFieldName}.invoice`, value)
						}
						outline
						required={budget.selected}
						small
					/>
				</div>
			</ClayPanel.Body>
		</ClayPanel>
	);
};

export default BudgetClaimPanel;
