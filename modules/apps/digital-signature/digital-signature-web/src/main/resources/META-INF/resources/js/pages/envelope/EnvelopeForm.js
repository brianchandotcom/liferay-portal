/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {BackButtonPortal} from '../../components/control-menu/ControlMenu';
import DigitalSignatureForm from '../../components/digital-signature-form/DigitalSignatureForm';

const EnvelopeForm = (props) => (
	<>
		<BackButtonPortal />
		<DigitalSignatureForm {...props} />
	</>
);

export default EnvelopeForm;
