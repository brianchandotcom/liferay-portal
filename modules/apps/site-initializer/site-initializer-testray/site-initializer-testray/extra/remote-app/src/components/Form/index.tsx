/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayForm from '@clayui/form';

import AutoComplete from './AutoComplete';
import {BaseRow, BaseWarning, BaseWrapper} from './Base';
import Checkbox from './Checkbox';
import DualListBox from './DualListBox';
import Footer from './Footer';
import Input from './Input';
import MultiSelect from './MultiSelect';
import Renderer from './Renderer';
import Select from './Select';

const Form = () => {};

Form.Clay = ClayForm;
Form.AutoComplete = AutoComplete;
Form.BaseRow = BaseRow;
Form.BaseWarning = BaseWarning;
Form.BaseWrapper = BaseWrapper;
Form.Checkbox = Checkbox;
Form.Divider = () => <hr />;
Form.DualListBox = DualListBox;
Form.Footer = Footer;
Form.Input = Input;
Form.MultiSelect = MultiSelect;
Form.Renderer = Renderer;
Form.Select = Select;

export default Form;
