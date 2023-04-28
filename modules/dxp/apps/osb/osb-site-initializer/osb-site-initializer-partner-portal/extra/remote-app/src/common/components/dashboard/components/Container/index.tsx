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

import {ReactNode} from 'react';

interface IProps {
	children?: ReactNode;
	className?: string;
	footer?: ReactNode;
	title?: string;
}

const Container = ({children, className, footer, title}: IProps) => (
	<div
		className={`bg-neutral-0 d-flex flex-column justify-content-between py-4 rounded ${className}`}
	>
		<div>
			<div className="font-weight-semi-bold h5 m-0">{title}</div>

			<div>
				<hr className="mb-4 mt-1" />
			</div>
		</div>

		<div className="align-items-stretch d-flex flex-column">{children}</div>

		<div>
			<hr className="mb-3 mt-1" />
		</div>

		{footer && (
			<div className="d-flex justify-content-end">
				<div>{footer}</div>
			</div>
		)}
	</div>
);

export default Container;
