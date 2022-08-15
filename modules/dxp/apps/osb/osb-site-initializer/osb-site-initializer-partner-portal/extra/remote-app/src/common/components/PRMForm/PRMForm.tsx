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

import CheckboxGroup from './components/fields/CheckboxGroup';
import DatePicker from './components/fields/DatePicker';
import InputCurrency from './components/fields/InputCurrency';
import InputText from './components/fields/InputText';
import RadioGroup from './components/fields/RadioGroup';
import Select from './components/fields/Select';
import Footer from './components/layout/Footer';
import Group from './components/layout/Group';
import Section from './components/layout/Section';

interface IProps {
	children?: React.ReactNode;
	description?: string;
	name: string;
	title: string;
}

const PRMForm = ({
	children,
	className,
	description,
	name,
	title,
}: IProps & React.HTMLAttributes<HTMLDivElement>) => (
	<div className="border-0 pb-3 pt-5 px-6 sheet">
		<div className={className}>
			<div className="font-weight-bold mb-1 text-primary text-small-caps">
				{name}
			</div>

			<h2 className="mb-0">{title}</h2>

			{description && (
				<div className="mt-1 text-paragraph-sm">{description}</div>
			)}
		</div>

		{children}
	</div>
);

PRMForm.Section = Section;
PRMForm.Group = Group;
PRMForm.Footer = Footer;

PRMForm.CheckboxGroup = CheckboxGroup;
PRMForm.InputText = InputText;
PRMForm.InputCurrency = InputCurrency;
PRMForm.RadioGroup = RadioGroup;
PRMForm.Select = Select;
PRMForm.DatePicker = DatePicker;

export default PRMForm;
