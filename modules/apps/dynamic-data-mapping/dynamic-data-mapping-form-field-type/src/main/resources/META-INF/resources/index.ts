/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/*
 * Using 'require' here since they are .js files and we need to cast to `any`.
 */
const Captcha: any = require('./Captcha/Captcha.es');
const CheckboxMultiple: any = require('./CheckboxMultiple/CheckboxMultiple.es');
const ColorPicker: any = require('./ColorPicker/ColorPicker.es');
const DatePicker: any = require('./DatePicker/DatePicker.es');
const DocumentLibrary: any = require('./DocumentLibrary/DocumentLibrary.es');
const ReactFieldBase: any = require('./FieldBase/ReactFieldBase.es');
const FieldSet: any = require('./FieldSet/FieldSet.es');
const Geolocation: any = require('./Geolocation/Geolocation.es');
const Grid: any = require('./Grid/Grid.es');
const HelpText: any = require('./HelpText/HelpText.es');
const ImagePicker: any = require('./ImagePicker/ImagePicker.es');
const KeyValue: any = require('./KeyValue/KeyValue.es');
const LocalizableText: any = require('./LocalizableText/LocalizableText.es');
const ObjectField: any = require('./ObjectField/ObjectField');
const Options: any = require('./Options/Options.es');
const Paragraph: any = require('./Paragraph/Paragraph.es');
const Password: any = require('./Password/Password.es');
const Radio: any = require('./Radio/Radio.es');
const RedirectButton: any = require('./RedirectButton/RedirectButton.es');
const RichText: any = require('./RichText/RichText.es');
const SearchLocation: any = require('./SearchLocation/SearchLocation.es');
const Separator: any = require('./Separator/Separator.es');
const Text: any = require('./Text/Text.es');
const Validation: any = require('./Validation/Validation');

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
	KeyValue,
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
