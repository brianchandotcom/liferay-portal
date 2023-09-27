/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useFlags} from '@liferay/flags-taglib';
import {useCallback, useState} from 'react';

const useFlagsContainer = ({
	btnProps = {
		className: 'btn btn-secondary',
		small: false,
	},
	content = {},
	context,
	onlyIcon = true,
	showIcon,
	questionId,
}) => {
	const [reportedThreads, setReportedThreads] = useState({});
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
		namespace,
		...props,
	});

	const handleClickShow = useCallback(() => {
		if (flagsModal.status !== 'report' && !reportedThreads[questionId]) {
			flagsModal.setStatus('report');
		}

		flagsModal.handleClickShow();
	}, [flagsModal, questionId, reportedThreads]);

	const handleSubmitReport = useCallback(
		(event) => {
			flagsModal.handleSubmitReport(event);

			setReportedThreads((prevReportedThreads) => ({
				...prevReportedThreads,
				[questionId]: true,
			}));
		},
		[flagsModal, questionId]
	);

	return {
		flagsContext,
		flagsModal: {...flagsModal, ...props},
		handleClickShow,
		handleSubmitReport,
		isFlagEnabled,
		props,
	};
};

export default useFlagsContainer;
