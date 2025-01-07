/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayLink from '@clayui/link';
import ClayPopover from '@clayui/popover';
import React, {
	createContext,
	useContext,
	useEffect,
	useMemo,
	useState,
} from 'react';

import {spritemap} from '../../Icon';
import apiFetch from '../../util/apiFetch';

const LearnContext = createContext();

function LearnAppWrapper(props) {
	const [learnResources, setLearnResources] = useState([]);

	useEffect(() => {
		apiFetch(
			window.location.pathname.substring(
				0,
				window.location.pathname.indexOf('/o/')
			) +
				'/o/language/v1.0/learn-message/headless-discovery-web?languageId=en_US',
			'get',
			{}
		).then((response) => {
			setLearnResources(response.items);
		});
	}, []);

	return (
		<LearnContext.Provider
			value={useMemo(() => {
				if (!Array.isArray(learnResources)) {
					return new Map();
				}

				return new Map(
					learnResources.map(({key, learnMessageDetails}) => [
						key,
						learnMessageDetails,
					])
				);
			}, [learnResources])}
		>
			{props.children}
		</LearnContext.Provider>
	);
}

function LearnInputWrapper(props) {
	const learnResources = useContext(LearnContext);
	const messageDetails = learnResources.get(props.field)?.[0];
	const [openPopover, setOpenPopover] = useState(false);

	return (
		<>
			{props.children}
			{messageDetails && (
				<ClayPopover
					onShowChange={setOpenPopover}
					show={openPopover}
					trigger={
						<ClayButtonWithIcon
							className="ml-2"
							displayType="link"
							size="sm"
							spritemap={spritemap}
							symbol="question-circle"
						/>
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
