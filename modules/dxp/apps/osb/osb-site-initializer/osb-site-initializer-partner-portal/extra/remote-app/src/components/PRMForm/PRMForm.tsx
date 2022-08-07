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

import Footer from './components/Footer';
import Group from './components/Group';
import Section from './components/Section';

type IProps = {
	children?: React.ReactNode;
	description?: string;
	name: string;
	title: string;
};

const PRMForm = ({children, description, name, title}: IProps) => (
	<div className="pt-5 px-6 sheet sheet-lg">
		<div>
			<div className="font-weight-bold mb-1 text-primary">
				{name.toUpperCase()}
			</div>

			{description && <div className="sheet-text">{description}</div>}

			<h2 className="mb-4">{title}</h2>
		</div>

		{children}
	</div>
);

PRMForm.Section = Section;
PRMForm.Group = Group;
PRMForm.Footer = Footer;

export default PRMForm;
