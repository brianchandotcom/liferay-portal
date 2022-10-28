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

import ClayPanel from '@clayui/panel';
import classNames from 'classnames';

interface IProps {
	children?: React.ReactNode;
	expanded: boolean;
	onClick?: React.MouseEventHandler<HTMLDivElement>;
}

const PanelHeader = ({children, expanded, onClick}: IProps) => (
	<ClayPanel.Title
		aria-expanded={expanded}
		className={classNames(
			'panel-header panel-header-link align-items-center d-flex pl-4 pr-5 py-4 card-interactive',
			{
				collapsed: !expanded,
				show: expanded,
			}
		)}
		onClick={onClick}
		role="tab"
	>
		{children}
	</ClayPanel.Title>
);

export default PanelHeader;
