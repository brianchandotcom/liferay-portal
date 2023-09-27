/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import 'clay-icon';
import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './FormPortal.soy';

class FormPortal extends Component {}

FormPortal.STATE = {

	/**
	 * @default input
	 * @instance
	 * @memberof FormPortal
	 * @type {?html}
	 */

	contentRenderer: Config.any(),
};

Soy.register(FormPortal, templates);

export default FormPortal;
