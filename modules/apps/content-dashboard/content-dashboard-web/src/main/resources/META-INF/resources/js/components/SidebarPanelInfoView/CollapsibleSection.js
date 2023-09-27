/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import ClayPanel from '@clayui/panel';
import PropTypes from 'prop-types';
import React from 'react';

const SidebarPanelInfoCollapsibleSection = ({children, title}) => {
	return (
		<ClayPanel
			className="c-mb-1"
			collapsable
			defaultExpanded
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
