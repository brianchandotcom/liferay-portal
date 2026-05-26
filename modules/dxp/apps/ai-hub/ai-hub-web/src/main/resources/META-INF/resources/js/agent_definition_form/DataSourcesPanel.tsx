/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayMultiSelect from '@clayui/multi-select';
import ClayPanel from '@clayui/panel';
import React from 'react';

import {RelationshipPicker} from './hooks/useRelationshipPicker';
import {ContentRetriever} from './types/ContentRetriever';

interface IProps {
	contentRetrievers: RelationshipPicker<ContentRetriever>;
	readOnly: boolean;
}

const DataSourcesPanel: React.FC<IProps> = ({contentRetrievers, readOnly}) => {
	return (
		<ClayPanel
			className="agent-definition-form-data-sources"
			collapsable={false}
		>
			<ClayPanel.Body>
				<div className="agent-definition-form-header">
					<h2>{Liferay.Language.get('data-sources')}</h2>
				</div>

				<label htmlFor="assignedSources">
					{Liferay.Language.get('assigned-sources')}
				</label>

				<ClayMultiSelect
					allowDuplicateValues={false}
					allowsCustomLabel={false}
					disabled={readOnly}
					inputName="assignedSources"
					items={contentRetrievers.selected}
					locator={{
						label: 'title',
						value: 'externalReferenceCode',
					}}
					onChange={contentRetrievers.setInputValue}
					onItemsChange={(items) =>
						contentRetrievers.setSelected(
							items as ContentRetriever[]
						)
					}
					sourceItems={contentRetrievers.sourceList}
					value={contentRetrievers.inputValue}
				/>
			</ClayPanel.Body>
		</ClayPanel>
	);
};

export default DataSourcesPanel;
