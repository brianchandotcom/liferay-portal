/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button from './Button/Button';
import Checkbox from './Checkbox';
import DatePicker from './DatePicker';
import Input from './Input';

const Form = () => {};

Form.Button = Button;
Form.Divider = () => <hr />;
Form.Input = Input;
Form.DatePicker = DatePicker;
Form.Checkbox = Checkbox;

export default Form;
