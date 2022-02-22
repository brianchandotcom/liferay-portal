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
import {ClayCheckbox} from '@clayui/form';
import ClayPopover from '@clayui/popover';
import {useState} from 'react';
import {Button} from '../../../../../../../../common/components';

const EnvironmentTypeFilter = () => {
	const [value, setValue] = useState(false);

	return (
		<div>
			<ClayPopover
				alignPosition="bottom"
				closeOnClickOutside={true}
				disableScroll={true}
				header="Environment Type"
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
					<ClayCheckbox
						aria-label="Option 1"
						checked={value}
						label="Production"
						onChange={() => setValue((val) => !val)}
					/>

					<ClayCheckbox
						aria-label="Option 1"
						checked={value}
						label="Non-Production"
						onChange={() => setValue((val) => !val)}
					/>

					<ClayCheckbox
						aria-label="Option 1"
						checked={value}
						label="Development"
						onChange={() => setValue((val) => !val)}
					/>

					<ClayCheckbox
						aria-label="Option 1"
						checked={value}
						label="Backup"
						onChange={() => setValue((val) => !val)}
					/>
				</div>

				<li className="dropdown-divider"></li>

				<div>
					<ClayCheckbox
						aria-label="Option 1"
						checked={value}
						label="Subscription"
						onChange={() => setValue((val) => !val)}
					/>

					<ClayCheckbox
						aria-label="Option 1"
						checked={value}
						label="Complimentary"
						onChange={() => setValue((val) => !val)}
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
export default EnvironmentTypeFilter;
