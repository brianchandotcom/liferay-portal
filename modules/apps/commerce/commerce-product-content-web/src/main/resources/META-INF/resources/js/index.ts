/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

const {
	default: AvailabilityCPInstanceChangeHandler,
} = require('./AvailabilityCPInstanceChangeHandler');

// @ts-ignore

const {default: CPInstanceChangeHandler} = require('./CPInstanceChangeHandler');

// @ts-ignore

const {default: OptionSelector} = require('./OptionSelector');

// @ts-ignore

const {default: Tabs} = require('./Tabs');

// @ts-ignore

const {default: miniCompare} = require('./miniCompare');

export {TagSelector} from './TagSelector';
export {Rule} from './Rule';
export {CategorySelector} from './CategorySelector';
export {default as AutoField} from './AutoField';

export {
	AvailabilityCPInstanceChangeHandler,
	CPInstanceChangeHandler,
	OptionSelector,
	Tabs,
	miniCompare,
};
