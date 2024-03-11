/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/*
 * Using 'require' here since they are .js files and we need to cast to `any`.
 */
const {default: Captcha} = require('./Captcha/Captcha.es');
const {
	default: CheckboxMultiple,
} = require('./CheckboxMultiple/CheckboxMultiple.es');
const {default: ColorPicker} = require('./ColorPicker/ColorPicker.es');
const {default: DatePicker} = require('./DatePicker/DatePicker.es');
const {
	default: DocumentLibrary,
} = require('./DocumentLibrary/DocumentLibrary.es');
const {default: ReactFieldBase} = require('./FieldBase/ReactFieldBase.es');
const {default: FieldSet} = require('./FieldSet/FieldSet.es');
const {default: Geolocation} = require('./Geolocation/Geolocation.es');
const {default: Grid} = require('./Grid/Grid.es');
const {default: HelpText} = require('./HelpText/HelpText.es');
const {default: ImagePicker} = require('./ImagePicker/ImagePicker.es');
const {
	default: LocalizableText,
} = require('./LocalizableText/LocalizableText.es');
const {default: ObjectField} = require('./ObjectField/ObjectField');
const {
	default: OptionFieldKeyValue,
} = require('./OptionFieldKeyValue/OptionFieldKeyValue');
const {default: Options} = require('./Options/Options.es');
const {default: Paragraph} = require('./Paragraph/Paragraph.es');
const {default: Password} = require('./Password/Password.es');
const {default: Radio} = require('./Radio/Radio.es');
const {default: RedirectButton} = require('./RedirectButton/RedirectButton.es');
const {default: RichText} = require('./RichText/RichText.es');
const {default: SearchLocation} = require('./SearchLocation/SearchLocation.es');
const {default: Separator} = require('./Separator/Separator.es');
const {default: Text} = require('./Text/Text.es');
const {default: Validation} = require('./Validation/Validation');

export {default as Checkbox} from './Checkbox/Checkbox';
export {default as Numeric} from './Numeric/Numeric';
export {default as Select} from './Select/Select';
export type {FieldChangeEventHandler} from './types';
export {default as NumericInputMask} from './NumericInputMask/NumericInputMask';

export {
	Captcha,
	CheckboxMultiple,
	ColorPicker,
	DatePicker,
	DocumentLibrary,
	FieldSet,
	Geolocation,
	Grid,
	HelpText,
	ImagePicker,
	OptionFieldKeyValue,
	LocalizableText,
	ReactFieldBase,
	ObjectField,
	Options,
	Paragraph,
	Password,
	Radio,
	RedirectButton,
	RichText,
	SearchLocation,
	Separator,
	Text,
	Validation,
};
