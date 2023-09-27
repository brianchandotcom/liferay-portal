/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Flags from '@liferay/flags-taglib';
import React from 'react';

import useFlagsContainer from '../hooks/useFlagsContainer.es';

const FlagsContainer = ({
	btnProps = {
		className: 'btn btn-secondary',
		small: false,
	},
	content = {},
	context,
	onlyIcon = true,
	showIcon,
}) => {
	const {flagsContext, isFlagEnabled, props} = useFlagsContainer({
		btnProps,
		content,
		context,
		onlyIcon,
		questionId: 'default-question',
		showIcon,
	});

	if (isFlagEnabled) {
		return <Flags context={flagsContext} props={props} />;
	}

	return null;
};

export default FlagsContainer;
