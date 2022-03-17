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
import {Button} from '../../../../common/components';

const BadgePill = ({filterName, filterValue, onClick}) => {
	return (
		<div>
			<div className="align-items-center badge badge-light badge-pill bg-white border border-secondary pl-2 text-neutral-8 text-paragraph-sm">
				<p className="font-weight-semi-bold mx-1 my-0">
					{filterName}

					{':'}
				</p>

				<p className="font-weight-normal m-0 pr-1"> {filterValue}</p>

				<Button
					appendIcon="times-small"
					aria-label="close"
					className="align-self-start mr-1"
					displayType="unstyled"
					onClick={onClick}
				/>
			</div>
		</div>
	);
};
export default BadgePill;
