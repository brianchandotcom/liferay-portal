/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDatePicker from '@clayui/date-picker';
import ClayForm from '@clayui/form';
import classNames from 'classnames';
import {format, getYear, isBefore, isEqual} from 'date-fns';
import React, {useEffect, useState} from 'react';
import { IDateFilter, IFilter } from '../../../utils/types';
import { API_URL } from '../../../utils/constants';

function Header() {
	return <>{Liferay.Language.get('new-date-range-filter')}</>;
}

interface IBodyProps {
	filter?: IFilter;
	namespace: string;
	onChange: Function;
	onValidation: Function;
}

function Body({
	filter,
	namespace,
	onChange,
	onValidation,
}: IBodyProps) {
	const fromFormElementId = `${namespace}From`;
	const toFormElementId = `${namespace}To`;

	const [from, setFrom] = useState<string>(
		(filter as IDateFilter)?.from ?? ''
	);
	const [to, setTo] = useState<string>((filter as IDateFilter)?.to ?? '');
	const [isValidDateRange, setIsValidDateRange] = useState<boolean>(true);

	useEffect(() => {
		let isValid = true;

		const dateTo = new Date(to);

		const dateFrom = new Date(from);

		if (to && from) {
			isValid = isBefore(dateFrom, dateTo) || isEqual(dateFrom, dateTo);
		}

		setIsValidDateRange(isValid);
		onValidation(isValid);
		onChange({saveUrl: API_URL.FDS_DATE_FILTERS, bodyData: {from, to}});
	}, [from, to]);

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
					onChange={(value: any) => setFrom(value)}
					placeholder="YYYY-MM-DD"
					value={from ? format(new Date(from), 'yyyy-MM-dd') : ''}
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
					onChange={(value: any) => setTo(value)}
					placeholder="YYYY-MM-DD"
					value={to ? format(new Date(to), 'yyyy-MM-dd') : ''}
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
