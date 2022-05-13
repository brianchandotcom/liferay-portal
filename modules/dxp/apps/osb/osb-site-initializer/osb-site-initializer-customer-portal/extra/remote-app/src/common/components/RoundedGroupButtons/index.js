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
import {useState} from 'react';
import {Button} from '../';

const RoundedGroupButtons = ({groupButtons, handleOnChange, id, ...props}) => {
	const [selectedButton, setSelectedButton] = useState(
		groupButtons[0]?.value
	);

	return (
		<div
			className="bg-neutral-1 border border-light btn-group rounded-pill"
			id={id}
			role="group"
		>
			{groupButtons?.map(({label, value}, index) => (
				<Button
					className={classNames('btn px-4 py-1 rounded-pill', {
						'bg-transparent text-neutral-4':
							selectedButton !== value,
						'bg-white border border-primary label-primary text-brand-primary':
							selectedButton === value,
					})}
					key={`${index}-${value}`}
					onClick={(event) => {
						setSelectedButton(event.target.value);
						handleOnChange(event.target.value);
					}}
					value={value}
					{...props}
				>
					{label}
				</Button>
			))}
		</div>
	);
};

export default RoundedGroupButtons;
