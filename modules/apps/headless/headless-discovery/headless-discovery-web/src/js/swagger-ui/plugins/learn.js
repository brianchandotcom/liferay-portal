/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import ClayPopover from '@clayui/popover';
import React, {createContext, useContext, useEffect, useState} from 'react';

import Icon from '../../Icon';
import apiFetch from '../../util/apiFetch';

const LearnContext = createContext();

function LearnAppWrapper(props) {
	const [learnResources, setLearnResources] = useState({});

	useEffect(() => {
		apiFetch(
			window.location.pathname.substring(
				0,
				window.location.pathname.indexOf('/o/')
			) + '/o/learn/v1.0/messages/headless-discovery-web.json',
			'get',
			{}
		).then(setLearnResources);
	}, []);

	return (
		<LearnContext.Provider value={learnResources}>
			{props.children}
		</LearnContext.Provider>
	);
}

function LearnInputWrapper(props) {
	const learnResources = useContext(LearnContext);
	const messageDetails = learnResources[props.field]?.['en_US'];
	const [openPopover, setOpenPopover] = useState(false);

	return (
		<>
			{props.children}
			{messageDetails && (
				<ClayPopover
					closeOnClickOutside
					onShowChange={setOpenPopover}
					show={openPopover}
					trigger={
						<button className="btn-unstyled ml-2">
							<Icon symbol="question-circle" />
						</button>
					}
				>
					<ClayLink
						href={messageDetails.url}
						rel="noopener noreferrer"
						target="_blank"
					>
						{messageDetails.message}
					</ClayLink>
				</ClayPopover>
			)}
		</>
	);
}

function learnSwaggerUIPlugin() {
	return {
		wrapComponents: {
			App: (Original) => (props) => {
				return (
					<LearnAppWrapper>
						<Original {...props} />
					</LearnAppWrapper>
				);
			},
			JsonSchema_string: (Original) => (props) => {
				return (
					<LearnInputWrapper field={props.description}>
						<Original {...props} />
					</LearnInputWrapper>
				);
			},
		},
	};
}

export default learnSwaggerUIPlugin;
