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
	const {context: flagsContext, props: flagsProps} =
		context?.flagsProperties || {};

	const namespace = flagsContext?.namespace;

	const isFlagEnabled = flagsProps?.isFlagEnabled;

	const props = {
		...flagsProps,
		baseData: {
			[`${namespace}className`]: 'com.liferay.message.boards.model.MBMessage',
			[`${namespace}classPK`]:
				content.messageBoardRootMessageId ?? content.id,
			[`${namespace}contentTitle`]:
				content.headline || content.articleBody,
			[`${namespace}contentURL`]: window.location.href,
			[`${namespace}reportedUserId`]: content?.creator?.id,
		},
		btnProps,
		message: Liferay.Language.get('report'),
		onlyIcon,
		showIcon,
		signedIn: Liferay.ThemeDisplay.isSignedIn(),
	};

	if (isFlagEnabled) {
		return <Flags context={flagsContext} props={props} />;
	}

	return null;
};

export default FlagsContainer;
