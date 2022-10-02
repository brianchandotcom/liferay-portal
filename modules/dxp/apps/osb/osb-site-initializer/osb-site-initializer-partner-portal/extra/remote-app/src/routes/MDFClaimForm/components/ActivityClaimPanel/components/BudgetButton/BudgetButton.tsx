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

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';

import MDFClaimBudget from '../../../../../../common/interfaces/mdfClaimBudget';
import getIntlNumberFormat from '../../../../../../common/utils/getIntlNumberFormat';

interface IProps {
	budget: MDFClaimBudget;
	onClick: () => void;
}

const BudgetButton = ({
	budget,
	onClick,
}: IProps & React.HTMLAttributes<HTMLDivElement>) => {
	const hasInvoices = budget.invoices.length;

	return (
		<div
			className={classNames(
				'bg-neutral-0 rounded shadow-lg d-flex justify-content-between p-3 align-items-center mb-2'
			)}
			onClick={onClick}
		>
			<div className="w-100">
				<div className="align-items-center d-flex justify-content-between">
					<div>
						<div className="font-weight-bold text-neutral-10 text-paragraph">
							{budget.expenseName}
						</div>

						<div
							className={classNames({
								'text-neutral-7': !hasInvoices,
								'text-success': hasInvoices,
							})}
						>
							<ClayIcon
								className="mr-2"
								symbol="check-circle-full"
							/>

							{hasInvoices ? 'Invoice Added' : 'Pending Invoice'}
						</div>
					</div>

					<div>
						<div className="font-weight-bold text-neutral-10 text-paragraph">
							{getIntlNumberFormat().format(budget.claimAmount)}
						</div>
					</div>
				</div>
			</div>
		</div>
	);
};

export default BudgetButton;
