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

import ClayLayout from '@clayui/layout';
import ClayPanel from '@clayui/panel';
import PropTypes from 'prop-types';
import React from 'react';

const SidebarPanelInfoCollapsibleSection = ({
	children,
	expanded = false,
	title,
}) => {
	return (
		<ClayPanel
			collapsable
			defaultExpanded={expanded}
			displayTitle={
				<span className="c-inner" tabIndex="-1">
					<ClayPanel.Title>
						<ClayLayout.ContentRow>
							<ClayLayout.ContentCol
								className="align-self-center panel-title"
								expand
							>
								<span
									aria-label={`${Liferay.Language.get(
										'view'
									)} ${title}`}
								>
									{title}
								</span>
							</ClayLayout.ContentCol>
						</ClayLayout.ContentRow>
					</ClayPanel.Title>
				</span>
			}
			displayType="unstyled"
			showCollapseIcon={true}
		>
			<ClayPanel.Body>{children}</ClayPanel.Body>
		</ClayPanel>
	);
};

SidebarPanelInfoCollapsibleSection.propTypes = {
	expanded: PropTypes.bool,
	title: PropTypes.string.isRequired,
};

export default SidebarPanelInfoCollapsibleSection;
