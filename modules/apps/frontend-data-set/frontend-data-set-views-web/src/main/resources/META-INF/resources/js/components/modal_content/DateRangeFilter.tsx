/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDatePicker from '@clayui/date-picker';
import ClayForm from '@clayui/form';
import classNames from 'classnames';
import {format, getYear, isBefore, isEqual} from 'date-fns';
import React, {useEffect} from 'react';

function Header() {
	return <>{Liferay.Language.get('new-date-range-filter')}</>;
}

interface IBodyProps {
	from: string;
	isValidDateRange: boolean;
	namespace: string;
	onFromChange: (val: string) => void;
	onToChange: (val: string) => void;
	onValidDateChange: (val: boolean) => void;
	to: string;
}

function Body({
	from,
	isValidDateRange,
	namespace,
	onFromChange,
	onToChange,
	onValidDateChange,
	to,
}: IBodyProps) {
	const fromFormElementId = `${namespace}From`;
	const toFormElementId = `${namespace}To`;

	useEffect(() => {
		let isValid = true;

		const dateTo = new Date(to);

		const dateFrom = new Date(from);

		if (to && from) {
			isValid = isBefore(dateFrom, dateTo) || isEqual(dateFrom, dateTo);
		}

		onValidDateChange(isValid);
	}, [from, to, onValidDateChange]);

	return (
		<ClayForm.Group className="form-group-autofit">
			<div
				className={classNames('form-group-item', {
					'has-error': !isValidDateRange,
				})}
			>
				<label htmlFor={fromFormElementId}>
					{Liferay.Language.get('from')}
				</label>

				<ClayDatePicker
					inputName={fromFormElementId}
					onChange={onFromChange}
					placeholder="YYYY-MM-DD"
					value={format(
						from ? new Date(from) : new Date(),
						'yyyy-MM-dd'
					)}
					years={{
						end: getYear(new Date()) + 25,
						start: getYear(new Date()) - 50,
					}}
				/>

				{!isValidDateRange && (
					<ClayForm.FeedbackGroup>
						<ClayForm.FeedbackItem>
							<ClayForm.FeedbackIndicator symbol="exclamation-full" />

							{Liferay.Language.get(
								'date-range-is-invalid.-from-must-be-before-to'
							)}
						</ClayForm.FeedbackItem>
					</ClayForm.FeedbackGroup>
				)}
			</div>

			<div className="form-group-item">
				<label htmlFor={toFormElementId}>
					{Liferay.Language.get('to')}
				</label>

				<ClayDatePicker
					inputName={toFormElementId}
					onChange={onToChange}
					placeholder="YYYY-MM-DD"
					value={format(to ? new Date(to) : new Date(), 'yyyy-MM-dd')}
					years={{
						end: getYear(new Date()) + 25,
						start: getYear(new Date()) - 50,
					}}
				/>
			</div>
		</ClayForm.Group>
	);
}

export default {
	Body,
	Header,
};
