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
import ClayDatePicker from '@clayui/date-picker';
import ClayPopover from '@clayui/popover';
import {useState} from 'react';
import {Button} from '../../../../../../../../common/components';
const StartDateFilter = () => {
	const [onOrAfter, setOnOrAfter] = useState('');
	const [onOrBefore, setOnOrBefore] = useState('');

	// eslint-disable-next-line no-console
	console.log('onOrAfter: ', onOrAfter);
	// eslint-disable-next-line no-console
	console.log('onOrBefore: ', onOrBefore);

	return (
		<div>
			<ClayPopover
				alignPosition="bottom"
				className="cp-popover"
				disableScroll={true}
				header="Start Date"
				trigger={
					<Button
						borderless
						className="btn-secondary p-2"
						prependIcon="filter"
					>
						Filter
					</Button>
				}
			>
				<div className="w-100">
					On or after
					<ClayDatePicker
						dateFormat="MM/dd/yyyy"
						onValueChange={setOnOrAfter}
						placeholder="MM/DD/YYYY"
						value={onOrAfter}
						years={{
							end: 2024,
							start: 1997,
						}}
					/>
				</div>

				<div className="w-100">
					On or before
					<ClayDatePicker
						dateFormat="MM/dd/yyyy"
						onValueChange={setOnOrBefore}
						placeholder="MM/DD/YYYY"
						value={onOrBefore}
						years={{
							end: 2024,
							start: 1997,
						}}
					/>
				</div>

				<div>
					<ClayButton className="w-100" small={true}>
						Apply
					</ClayButton>
				</div>
			</ClayPopover>
		</div>
	);
};
export default StartDateFilter;
