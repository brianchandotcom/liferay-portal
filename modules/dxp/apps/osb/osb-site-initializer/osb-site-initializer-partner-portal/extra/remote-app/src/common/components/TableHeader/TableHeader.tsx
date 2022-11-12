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

interface IProps {
	children?: JSX.Element | JSX.Element[];
}

const TableHeader = ({children}: IProps) => {
	return (
		<div className="bg-neutral-1 d-flex d-md-flex flex-column-reverse flex-md-row justify-content-between p-3 rounded">
			{children}
		</div>
	);
};
export default TableHeader;
