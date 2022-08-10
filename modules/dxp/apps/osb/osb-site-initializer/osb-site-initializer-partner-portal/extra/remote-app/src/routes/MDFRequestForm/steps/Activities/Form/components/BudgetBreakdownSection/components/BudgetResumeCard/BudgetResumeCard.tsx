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

import classNames from 'classnames';

interface IProps {
	leftContent: string;
	rightContent: string;
}

const BudgetResumeCard = ({
	className,
	leftContent,
	rightContent,
}: IProps & React.HTMLAttributes<HTMLDivElement>) => (
	<div
		className={classNames(
			'bg-neutral-1 rounded d-flex justify-content-between p-3 align-items-center',
			className
		)}
	>
		<div className="font-weight-semi-bold text-paragraph">
			{leftContent}
		</div>

		<div className="font-weight-semi-bold text-paragraph">
			{rightContent}
		</div>
	</div>
);

export default BudgetResumeCard;
