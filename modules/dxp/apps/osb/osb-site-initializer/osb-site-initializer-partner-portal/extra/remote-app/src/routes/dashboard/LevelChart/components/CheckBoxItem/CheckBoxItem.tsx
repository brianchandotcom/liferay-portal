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
import {ReactNode} from 'react';

interface IPropsCheckBoxItem {
	children?: ReactNode;
	completed: boolean | undefined;
	text?: string;
	title?: string;
}

const CheckBoxItem = ({
	children,
	completed,
	text,
	title,
}: IPropsCheckBoxItem) => {
	const CheckIcon = () => {
		if (completed) {
			return (
				<ClayIcon
					className="m-0 text-brand-primary"
					symbol="check-circle"
				/>
			);
		}

		return <ClayIcon className="m-0 text-danger" symbol="times-circle" />;
	};

	return (
		<div className="d-flex mb-4">
			<div
				className={classNames('d-flex p-0 align-items-center', {
					'col': !children,
					'col-3': children,
				})}
			>
				<CheckIcon />

				<span
					className={classNames(
						'font-weight-bold text-paragraph-sm',
						{
							'col': !text,
							'col-3': text,
						}
					)}
				>
					{title}
				</span>

				{text && <span className="col text-paragraph">{text}</span>}
			</div>

			{children && <div className="col">{children}</div>}
		</div>
	);
};

export default CheckBoxItem;
