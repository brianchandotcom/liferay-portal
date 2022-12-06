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
