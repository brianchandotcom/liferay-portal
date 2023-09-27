/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import ClayPanel from '@clayui/panel';
import PropTypes from 'prop-types';
import React from 'react';

export default function ImportTranslationResultsPanel({
	defaultExpanded = false,
	files,
	title,
}) {
	return (
		<ClayPanel
			collapsable
			defaultExpanded={defaultExpanded}
			displayTitle={
				<div className="h4 mb-0 text-success">
					<span className="mr-2">
						<ClayIcon symbol="check-circle-full" />
					</span>

					{title}
				</div>
			}
			showCollapseIcon
		>
			<ClayPanel.Body>
				<ClayList className="list-group-no-bordered mb-0">
					{files.map((file) => (
						<ClayList.Item key={file}>
							<ClayList.ItemTitle>{file}</ClayList.ItemTitle>
						</ClayList.Item>
					))}
				</ClayList>
			</ClayPanel.Body>
		</ClayPanel>
	);
}

ImportTranslationResultsPanel.prototypes = {
	defaultExpanded: PropTypes.bool,
	files: PropTypes.arrayOf(PropTypes.string).isRequired,
	title: PropTypes.string.isRequired,
};
