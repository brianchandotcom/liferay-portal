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

import {useFlags} from '@liferay/flags-taglib';

const useFlagsContainer = ({
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
		companyName: context.companyName,
		message: Liferay.Language.get('report'),
		onlyIcon,
		showIcon,
		signedIn: Liferay.ThemeDisplay.isSignedIn(),
	};

	const flagsModal = useFlags({
		baseData: props.baseData,
		forceLogin: false,
		namespace: context.namespace,
		...props,
	});

	return {
		flagsContext,
		flagsModal: {...flagsModal, ...props},
		isFlagEnabled,
		props,
	};
};

export default useFlagsContainer;
