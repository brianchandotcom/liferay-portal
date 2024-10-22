/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/*
 * Using '@ts-ignore' here since they are .js files and we need to cast to `any`.
 */

// @ts-ignore

import Captcha from './Captcha/Captcha.es';

// @ts-ignore

import CheckboxMultiple from './CheckboxMultiple/CheckboxMultiple.es';

// @ts-ignore

import ColorPicker from './ColorPicker/ColorPicker.es';

// @ts-ignore

import DatePicker from './DatePicker/DatePicker.es';

// @ts-ignore

import DocumentLibrary from './DocumentLibrary/DocumentLibrary.es';
import ReactFieldBase from './FieldBase/ReactFieldBase.es';

// @ts-ignore

import FieldSet from './FieldSet/FieldSet.es';

// @ts-ignore

import Geolocation from './Geolocation/Geolocation.es';

// @ts-ignore

import Grid from './Grid/Grid.es';

// @ts-ignore

import HelpText from './HelpText/HelpText.es';

// @ts-ignore

import ImagePicker from './ImagePicker/ImagePicker.es';

// @ts-ignore

import LocalizableText from './LocalizableText/LocalizableText.es';

// @ts-ignore

import ObjectField from './ObjectField/ObjectField';

// @ts-ignore

import OptionFieldKeyValue from './OptionFieldKeyValue/OptionFieldKeyValue';

// @ts-ignore

import Options from './Options/Options.es';

// @ts-ignore

import Paragraph from './Paragraph/Paragraph.es';

// @ts-ignore

import Password from './Password/Password.es';

// @ts-ignore

import Radio from './Radio/Radio.es';

// @ts-ignore

import RedirectButton from './RedirectButton/RedirectButton.es';

// @ts-ignore

import RichText from './RichText/RichText.es';

// @ts-ignore

import SearchLocation from './SearchLocation/SearchLocation.es';

// @ts-ignore

import Separator from './Separator/Separator.es';

// @ts-ignore

import Text from './Text/Text.es';

// @ts-ignore

import Validation from './Validation/Validation';

export {default as Checkbox} from './Checkbox/Checkbox';
export {default as Numeric} from './Numeric/Numeric';
export {default as NumericInputMask} from './NumericInputMask/NumericInputMask';
export {default as MultipleSelection} from './Select/MultipleSelect';
export {default as Select} from './Select/Select';
export type {FieldChangeEventHandler} from './types';

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
