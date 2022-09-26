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
import {useEffect, useState} from 'react';

import MDFClaim from '../../../../../../common/interfaces/mdfClaim';
import MDFRequestBudget from '../../../../../../common/interfaces/mdfRequestBudget';
import getIntlNumberFormat from '../../../../../../common/utils/getIntlNumberFormat';

interface IProps {
	budget: MDFRequestBudget;
	cost?: number;
	mdfClaim: MDFClaim;
}

const BudgetButton = ({
	budget,
	cost,
	mdfClaim,
	onClick,
}: IProps & React.HTMLAttributes<HTMLDivElement>) => {
	const [checkInvoice, setCheckInvoice] = useState<boolean>(false);

	useEffect(() => {
		if (mdfClaim.mdfClaimDocuments.budgets.length) {
			const resultDocument = mdfClaim.mdfClaimDocuments.budgets.find(
				(element) => {
					return element.thirdPartyInvoices.budgetId === budget.id;
				}
			);

			if (resultDocument) {
				setCheckInvoice(true);
			}
		}
	}, [mdfClaim.mdfClaimDocuments.budgets, budget.id]);

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
							{budget?.expense?.name}
						</div>

						<div
							className={classNames({
								'text-neutral-7': checkInvoice === false,
								'text-success': checkInvoice === true,
							})}
						>
							<ClayIcon
								className="mr-2"
								symbol="check-circle-full"
							/>

							{checkInvoice ? 'Invoice Added' : 'Pedding Invoice'}
						</div>
					</div>

					<div>
						<div className="font-weight-bold text-neutral-10 text-paragraph">
							{getIntlNumberFormat().format(cost ? cost : 0)}
						</div>
					</div>
				</div>
			</div>
		</div>
	);
};

export default BudgetButton;
