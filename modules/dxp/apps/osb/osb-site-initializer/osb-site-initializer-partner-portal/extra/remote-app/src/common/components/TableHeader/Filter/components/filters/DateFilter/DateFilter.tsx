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

import ClayButton from '@clayui/button';
import {ClayInput} from '@clayui/form';
import {useState} from 'react';

interface IProps {
	children?: JSX.Element | JSX.Element[];
	dateFilters: (dates: {endDate: string; startDate: string}) => void;
	filterDescription?: string;
}

const DateFilter = ({children, dateFilters, filterDescription}: IProps) => {
	const [startActivityDate, setStartActivityDate] = useState('');
	const [endActivityDate, setEndActivityDate] = useState('');

	return (
		<div className="p-3 w-100">
			<div className="font-weight-semi-bold pb-3 text-paragraph">
				{filterDescription}
				On Or After
				<ClayInput
					id="basicInputText"
					onChange={(event) => {
						setStartActivityDate(event.target.value);
					}}
					placeholder="mm-dd-yyyye"
					type="date"
					value={startActivityDate}
				/>
			</div>

			<div className="font-weight-semi-bold pb-3 text-paragraph">
				{filterDescription}
				On Or Before
				<ClayInput
					id="basicInputText"
					onChange={(event) => {
						setEndActivityDate(event.target.value);
					}}
					placeholder="mm-dd-yyyy"
					type="date"
					value={endActivityDate}
				/>
			</div>

			{children}

			<div>
				<ClayButton
					className="w-100"
					onClick={() => {
						dateFilters({
							endDate: endActivityDate,
							startDate: startActivityDate,
						});
					}}
					small={true}
				>
					Apply
				</ClayButton>
			</div>
		</div>
	);
};
export default DateFilter;
