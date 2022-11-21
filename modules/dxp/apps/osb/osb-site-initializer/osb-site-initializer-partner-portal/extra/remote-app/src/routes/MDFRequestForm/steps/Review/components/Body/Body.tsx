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
	children?: React.ReactNode;
	name?: string;
	title?: string;
}

const Body = ({children, name, title}: IProps) => (
	<div className="bg-neutral-0 mt-4 p-md-2 pt-4 px-lg-4 py-1 rounded">
		{name && (
			<div className="font-weight-bold mb-1 text-brand-primary-lighten-2 text-small-caps">
				{name}
			</div>
		)}

		<h5>{title}</h5>

		{children}
	</div>
);

export default Body;
