/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
		showIcon,
	});

	if (isFlagEnabled) {
		return <Flags context={flagsContext} props={props} />;
	}

	return null;
};

export default FlagsContainer;
