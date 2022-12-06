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
import ClayLabel from '@clayui/label';
import React, {ReactNode} from 'react';

const BaseWarning: React.FC<{children: ReactNode}> = ({children}) => {
	return (
		<ClayLabel className="label-tonal-danger mt-1 mx-0 p-0 rounded w-100">
			<div className="align-items-center badge d-flex m-0 warning">
				<span className="inline-item inline-item-before">
					<ClayIcon
						className="c-ml-2 c-mr-2"
						symbol="exclamation-full"
					/>
				</span>

				<span className="font-weight-normal text-paragraph">
					{children}
				</span>
			</div>
		</ClayLabel>
	);
};

export default BaseWarning;
