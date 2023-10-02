/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';

import AutoComplete from './AutoComplete';
import {BaseRow, BaseWarning, BaseWrapper} from './Base';
import Checkbox from './Checkbox';
import DualListBox from './DualListBox';
import Footer from './Footer';
import Input from './Input';
import MultiSelect, {MultiSelectCreatable} from './MultiSelect';
import Renderer from './Renderer';
import Select from './Select';

const Form = () => {};

Form.Clay = ClayForm;
Form.AutoComplete = AutoComplete;
Form.BaseRow = BaseRow;
Form.BaseWarning = BaseWarning;
Form.BaseWrapper = BaseWrapper;
Form.Checkbox = Checkbox;
Form.Divider = (props: React.HTMLAttributes<HTMLHRElement>) => (
	<hr {...props} />
);
Form.DualListBox = DualListBox;
Form.Footer = Footer;
Form.Input = Input;
Form.MultiSelect = MultiSelect;
Form.MultiSelectCreatable = MultiSelectCreatable;
Form.Renderer = Renderer;
Form.Select = Select;

export default Form;
