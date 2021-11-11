/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React, {useEffect, useState} from 'react';

const SXP_ELEMENT_CLASSNAME_SUFFIX = 'SXPParameter';

const sxpElementClassNameRegex = new RegExp(
	`([\\w\\.]+\\.)(\\w+)(${SXP_ELEMENT_CLASSNAME_SUFFIX})`
);

function extractType(className = '') {
	return className.split(sxpElementClassNameRegex)[2];
}

function SidebarPanel({
	categoryName,
	expand,
	onVariableClick,
	parameterDefinitions,
}) {
	const [showList, setShowList] = useState(false);

	useEffect(() => {
		setShowList(expand);
	}, [expand]);

	return (
		<div>
			<ClayButton
				className="panel-header sidebar-dt"
				displayType="unstyled"
				onClick={() => setShowList(!showList)}
			>
				<span>{categoryName}</span>

				<span className="sidebar-arrow">
					<ClayIcon
						symbol={showList ? 'angle-down' : 'angle-right'}
					/>
				</span>
			</ClayButton>

			{showList &&
				parameterDefinitions.map((entry) => (
					<dd className="sidebar-dd" key={entry.templateVariable}>
						<ClayTooltipProvider>
							<ClayButton
								className="nav-link"
								displayType="unstyled"
								onClick={() =>
									onVariableClick(entry.templateVariable)
								}
								title={`${Liferay.Language.get(
									'type'
								)}: ${extractType(
									entry.className
								)}\n${Liferay.Language.get('variable')}: ${
									entry.templateVariable
								}`}
							>
								{entry.description}
							</ClayButton>
						</ClayTooltipProvider>
					</dd>
				))}
		</div>
	);
}

export default SidebarPanel;
