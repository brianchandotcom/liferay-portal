/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import Flags from './components/Flags.es';
import FlagsModal from './components/FlagsModal.es';
import useFlags from './hooks/useFlags.es';

export default function ({context, props}) {
	return <Flags namespace={context.namespace} {...props} />;
}

export {FlagsModal, useFlags};
