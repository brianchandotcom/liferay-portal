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
	subtitle?: string;
	title: string;
}

const Section = ({children, subtitle, title}: IProps) => (
	<div>
		<div className="border-bottom border-neutral-2 mb-4 py-2">
			<h5 className="font-weight-bold mb-0 text-paragraph">{title}</h5>

			<div className="text-neutral-8 text-paragraph-sm">{subtitle}</div>
		</div>

		{children}
	</div>
);

export default Section;
